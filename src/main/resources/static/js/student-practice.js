let currentSessionId = null;
let currentQuestionOrder = 1;
let totalQuestions = 10;
let currentQuestionData = null;
let selectedOption = null;

document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    const params = new URLSearchParams(window.location.search);
    const topicId = params.get('topicId');
    const topicName = params.get('topicName');
    const subId = params.get('subId');
    const subName = params.get('subName');
    const catId = params.get('catId');
    const catName = params.get('catName');

    if (!topicId) {
        window.location.href = '/student/dashboard.html';
        return;
    }

    // Set up breadcrumbs
    const bCat = document.getElementById('breadCat');
    bCat.textContent = catName || 'Category';
    bCat.href = `/student/category.html?id=${catId}&name=${encodeURIComponent(catName || '')}`;

    const bSub = document.getElementById('breadSub');
    bSub.textContent = subName || 'Subject';
    bSub.href = `/student/subcategory.html?id=${subId}&name=${encodeURIComponent(subName || '')}&catId=${catId}&catName=${encodeURIComponent(catName || '')}`;

    document.getElementById('breadTopic').textContent = topicName;
    document.getElementById('topicTitle').textContent = `Practice: ${topicName}`;

    // Setup form submit listener
    const form = document.getElementById('startPracticeForm');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const difficulty = document.getElementById('difficultySelect').value;
        const numQuestions = parseInt(document.getElementById('questionsCount').value);

        await startSession(topicId, difficulty, numQuestions, topicName);
    });

    // Options click listener
    const optionCards = document.querySelectorAll('.option-card');
    optionCards.forEach(card => {
        card.addEventListener('click', () => {
            if (currentQuestionData && currentQuestionData.isAnswered) {
                return; // Already submitted answer
            }
            optionCards.forEach(c => c.classList.remove('selected'));
            card.classList.add('selected');
            selectedOption = card.getAttribute('data-option');
            document.getElementById('submitAnswerBtn').disabled = false;
        });
    });

    // Action listeners
    document.getElementById('submitAnswerBtn').addEventListener('click', submitCurrentAnswer);
    document.getElementById('prevBtn').addEventListener('click', () => navigateQuestion(-1));
    document.getElementById('nextBtn').addEventListener('click', () => navigateQuestion(1));
    document.getElementById('finishEarlyBtn').addEventListener('click', finishSession);
    document.getElementById('bookmarkBtn').addEventListener('click', toggleBookmark);
});

async function startSession(topicId, difficulty, numberOfQuestions, topicName) {
    const alertBox = document.getElementById('setupAlert');
    alertBox.style.display = 'none';

    try {
        const session = await api.post('/practice/start', {
            topicId: parseInt(topicId),
            difficulty,
            numberOfQuestions
        });

        currentSessionId = session.id;
        totalQuestions = session.totalQuestions;

        document.getElementById('topicBadge').textContent = topicName;
        document.getElementById('diffBadge').textContent = session.difficulty;
        document.getElementById('totalQCount').textContent = totalQuestions;

        document.getElementById('setupContainer').style.display = 'none';
        document.getElementById('practiceContainer').style.display = 'block';

        currentQuestionOrder = 1;
        await loadQuestion(currentQuestionOrder);

    } catch (err) {
        console.error(err);
        alertBox.textContent = err.message || 'Failed to start practice session';
        alertBox.style.display = 'block';
    }
}

async function loadQuestion(order) {
    selectedOption = null;
    document.getElementById('submitAnswerBtn').disabled = true;
    document.getElementById('explanationBox').style.display = 'none';

    // Reset option card classes
    const optionCards = document.querySelectorAll('.option-card');
    optionCards.forEach(c => {
        c.className = 'option-card';
    });

    try {
        currentQuestionData = await api.get(`/practice/sessions/${currentSessionId}/questions/${order}`);
        
        document.getElementById('currentQIndex').textContent = order;
        document.getElementById('questionText').textContent = currentQuestionData.questionText;
        document.getElementById('optA').textContent = currentQuestionData.optionA;
        document.getElementById('optB').textContent = currentQuestionData.optionB;
        document.getElementById('optC').textContent = currentQuestionData.optionC;
        document.getElementById('optD').textContent = currentQuestionData.optionD;

        document.getElementById('qDifficulty').textContent = `Difficulty: ${currentQuestionData.difficulty}`;
        document.getElementById('qMarks').innerHTML = `<i class="fas fa-star"></i> ${currentQuestionData.marks} Mark${currentQuestionData.marks > 1 ? 's' : ''}`;

        // Update progress bar
        const pct = (order / totalQuestions) * 100;
        document.getElementById('sessionProgressFill').style.width = `${pct}%`;

        // Navigation state
        document.getElementById('prevBtn').disabled = (order <= 1);
        const nextBtn = document.getElementById('nextBtn');
        if (order >= totalQuestions) {
            nextBtn.innerHTML = 'Finish Session <i class="fas fa-flag-checkered"></i>';
        } else {
            nextBtn.innerHTML = 'Next <i class="fas fa-chevron-right"></i>';
        }

        // If already answered
        if (currentQuestionData.isAnswered) {
            displayAnswerResult({
                selectedAnswer: currentQuestionData.selectedAnswer,
                correctAnswer: currentQuestionData.correctAnswer,
                isCorrect: currentQuestionData.isCorrect,
                explanation: currentQuestionData.explanation
            });
        }

        // Check bookmark status
        try {
            const status = await api.get(`/student/bookmarks/${currentQuestionData.questionId}/status`);
            updateBookmarkBtn(status.bookmarked);
        } catch (err) {
            console.error('Failed to get bookmark status', err);
            document.getElementById('bookmarkBtn').style.display = 'none';
        }

    } catch (err) {
        console.error(err);
        alert('Failed to load question #' + order);
    }
}

async function submitCurrentAnswer() {
    if (!selectedOption || !currentQuestionData) return;

    try {
        const response = await api.post(`/practice/sessions/${currentSessionId}/attempts/${currentQuestionData.attemptId}/submit`, {
            selectedAnswer: selectedOption
        });

        currentQuestionData.isAnswered = true;
        currentQuestionData.selectedAnswer = response.selectedAnswer;
        currentQuestionData.correctAnswer = response.correctAnswer;
        currentQuestionData.isCorrect = response.isCorrect;
        currentQuestionData.explanation = response.explanation;

        displayAnswerResult(response);

    } catch (err) {
        console.error(err);
        alert('Failed to submit answer: ' + err.message);
    }
}

function displayAnswerResult(result) {
    document.getElementById('submitAnswerBtn').disabled = true;

    const optionCards = document.querySelectorAll('.option-card');
    optionCards.forEach(card => {
        const opt = card.getAttribute('data-option');
        if (opt === result.correctAnswer) {
            card.classList.add('correct');
        }
        if (opt === result.selectedAnswer && !result.isCorrect) {
            card.classList.add('wrong');
        }
    });

    const expBox = document.getElementById('explanationBox');
    const feedbackBanner = document.getElementById('resultFeedback');

    if (result.isCorrect) {
        feedbackBanner.className = 'feedback-banner banner-success';
        feedbackBanner.innerHTML = '<i class="fas fa-check-circle"></i> <strong>Correct Answer!</strong> Well done.';
    } else {
        feedbackBanner.className = 'feedback-banner banner-error';
        feedbackBanner.innerHTML = `<i class="fas fa-times-circle"></i> <strong>Incorrect.</strong> Correct answer is Option ${result.correctAnswer}.`;
    }

    document.getElementById('explanationText').textContent = result.explanation || 'No detailed explanation provided.';
    expBox.style.display = 'block';
}

async function navigateQuestion(direction) {
    if (direction === 1 && currentQuestionOrder >= totalQuestions) {
        await finishSession();
        return;
    }

    const targetOrder = currentQuestionOrder + direction;
    if (targetOrder >= 1 && targetOrder <= totalQuestions) {
        currentQuestionOrder = targetOrder;
        await loadQuestion(currentQuestionOrder);
    }
}

async function finishSession() {
    if (confirm('Are you sure you want to finish this practice session and view your results?')) {
        try {
            await api.post(`/practice/sessions/${currentSessionId}/complete`, {});
            window.location.href = `/student/result.html?sessionId=${currentSessionId}`;
        } catch (err) {
            console.error(err);
            window.location.href = `/student/result.html?sessionId=${currentSessionId}`;
        }
    }
}

let isBookmarked = false;

function updateBookmarkBtn(bookmarked) {
    isBookmarked = bookmarked;
    const btn = document.getElementById('bookmarkBtn');
    const icon = document.getElementById('bookmarkIcon');
    const text = document.getElementById('bookmarkText');
    
    btn.style.display = 'inline-block';
    if (bookmarked) {
        icon.className = 'fas fa-bookmark';
        icon.style.color = '#ef4444';
        text.textContent = 'Saved';
        btn.style.borderColor = '#ef4444';
        btn.style.color = '#ef4444';
    } else {
        icon.className = 'far fa-bookmark';
        icon.style.color = '';
        text.textContent = 'Save';
        btn.style.borderColor = '';
        btn.style.color = '';
    }
}

async function toggleBookmark() {
    if (!currentQuestionData) return;
    const btn = document.getElementById('bookmarkBtn');
    btn.disabled = true;

    try {
        if (isBookmarked) {
            await api.delete(`/student/bookmarks/${currentQuestionData.questionId}`);
            updateBookmarkBtn(false);
        } else {
            await api.post(`/student/bookmarks/${currentQuestionData.questionId}`);
            updateBookmarkBtn(true);
        }
    } catch (error) {
        console.error('Bookmark toggle failed', error);
        alert('Failed to update bookmark.');
    } finally {
        btn.disabled = false;
    }
}
