let currentAttemptId = null;
let totalQuestionsCount = 0;
let currentQuestionOrder = 1;
let currentQuestionId = null;
let selectedAnswer = null;
let isMarkedForReview = false;
let questionsState = {}; // order -> { isAnswered, selectedAnswer, isMarkedForReview }
let timerInterval = null;
let remainingSeconds = 0;

document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }

    const params = new URLSearchParams(window.location.search);
    currentAttemptId = params.get('attemptId');
    if (!currentAttemptId) {
        window.location.href = '/student/mock-tests.html';
        return;
    }

    await loadInitialAttemptData();
    setupEventListeners();
});

async function loadInitialAttemptData() {
    try {
        const attemptData = await api.get(`/test-attempts/${currentAttemptId}`);
        if (attemptData.status !== 'IN_PROGRESS') {
            window.location.href = `/student/test-result.html?attemptId=${currentAttemptId}`;
            return;
        }

        document.getElementById('examTitle').innerText = attemptData.testTitle;
        document.getElementById('examCategory').innerText = attemptData.categoryName || 'Placement Test';

        totalQuestionsCount = attemptData.totalQuestions;
        remainingSeconds = attemptData.remainingSeconds;

        startTimer();

        // Populate initial questions state map
        if (attemptData.questions && attemptData.questions.length > 0) {
            attemptData.questions.forEach(q => {
                questionsState[q.questionOrder] = {
                    isAnswered: q.isAnswered,
                    selectedAnswer: q.selectedAnswer,
                    isMarkedForReview: q.isMarkedForReview || false
                };
            });
        }

        renderPalette();
        await loadQuestion(1);

    } catch (err) {
        alert('Failed to load test attempt: ' + err.message);
        window.location.href = '/student/mock-tests.html';
    }
}

function startTimer() {
    const timerDisplay = document.getElementById('timerDisplay');
    const timerBox = document.getElementById('timerBox');

    function updateDisplay() {
        if (remainingSeconds <= 0) {
            clearInterval(timerInterval);
            timerDisplay.innerText = "00:00";
            alert("Time is up! Your test is being automatically submitted.");
            executeFinalSubmit();
            return;
        }

        const mins = Math.floor(remainingSeconds / 60);
        const secs = remainingSeconds % 60;
        timerDisplay.innerText = `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;

        if (remainingSeconds < 300) { // last 5 mins
            timerBox.classList.add('timer-warning');
        }
        remainingSeconds--;
    }

    updateDisplay();
    timerInterval = setInterval(updateDisplay, 1000);
}

async function loadQuestion(order) {
    try {
        const q = await api.get(`/test-attempts/${currentAttemptId}/questions/${order}`);
        currentQuestionOrder = order;
        currentQuestionId = q.questionId;

        document.getElementById('qNumber').innerText = q.questionOrder;
        document.getElementById('qTotal').innerText = q.totalQuestions;
        document.getElementById('qMarks').innerText = `Marks: ${q.marks || 2}`;
        document.getElementById('qText').innerText = q.questionText;

        const optionsContainer = document.getElementById('optionsContainer');
        optionsContainer.innerHTML = '';

        const options = [
            { key: 'A', text: q.optionA },
            { key: 'B', text: q.optionB },
            { key: 'C', text: q.optionC },
            { key: 'D', text: q.optionD }
        ];

        selectedAnswer = q.selectedAnswer || null;
        isMarkedForReview = q.isMarkedForReview || false;

        options.forEach(opt => {
            const btn = document.createElement('button');
            btn.className = `option-btn ${selectedAnswer === opt.key ? 'selected' : ''}`;
            btn.innerHTML = `<strong style="margin-right: 12px;">${opt.key}.</strong> <span>${escapeHtml(opt.text)}</span>`;
            btn.addEventListener('click', () => selectOption(opt.key));
            optionsContainer.appendChild(btn);
        });

        // Update Review button state
        const reviewBtn = document.getElementById('markReviewBtn');
        if (isMarkedForReview) {
            reviewBtn.style.background = '#a855f7';
            reviewBtn.style.color = '#ffffff';
        } else {
            reviewBtn.style.background = 'transparent';
            reviewBtn.style.color = '#a855f7';
        }

        // Navigation button states
        document.getElementById('prevBtn').disabled = (currentQuestionOrder === 1);
        document.getElementById('nextBtn').innerText = (currentQuestionOrder === totalQuestionsCount) ? 'Review / Save' : 'Next ›';

        // Update local state map
        questionsState[order] = {
            isAnswered: selectedAnswer != null && selectedAnswer !== "",
            selectedAnswer: selectedAnswer,
            isMarkedForReview: isMarkedForReview
        };

        renderPalette();

        // Check bookmark status
        try {
            const status = await api.get(`/student/bookmarks/${currentQuestionId}/status`);
            updateBookmarkBtn(status.bookmarked);
        } catch (err) {
            console.error('Failed to get bookmark status', err);
            document.getElementById('bookmarkBtn').style.display = 'none';
        }

    } catch (err) {
        console.error(err);
        alert('Failed to load question #' + order + ': ' + err.message);
    }
}

async function selectOption(optionKey) {
    selectedAnswer = optionKey;
    // Highlight UI immediately
    document.querySelectorAll('.option-btn').forEach(btn => {
        if (btn.querySelector('strong').innerText.startsWith(optionKey)) {
            btn.classList.add('selected');
        } else {
            btn.classList.remove('selected');
        }
    });

    await saveAnswerState();
}

async function saveAnswerState() {
    try {
        await api.post(`/test-attempts/${currentAttemptId}/questions/${currentQuestionOrder}/answer`, {
            selectedAnswer: selectedAnswer || "",
            markForReview: isMarkedForReview
        });

        questionsState[currentQuestionOrder] = {
            isAnswered: selectedAnswer != null && selectedAnswer !== "",
            selectedAnswer: selectedAnswer,
            isMarkedForReview: isMarkedForReview
        };
        renderPalette();
    } catch (err) {
        console.error("Failed saving answer state:", err);
    }
}

function renderPalette() {
    const grid = document.getElementById('paletteGrid');
    grid.innerHTML = '';

    for (let i = 1; i <= totalQuestionsCount; i++) {
        const state = questionsState[i] || { isAnswered: false, selectedAnswer: null, isMarkedForReview: false };
        const btn = document.createElement('button');
        
        let stateClass = 'unanswered';
        if (state.isMarkedForReview) {
            stateClass = 'review';
        } else if (state.isAnswered) {
            stateClass = 'answered';
        }

        if (i === currentQuestionOrder) {
            stateClass += ' current';
        }

        btn.className = `palette-btn ${stateClass}`;
        btn.innerText = i;
        btn.addEventListener('click', () => loadQuestion(i));
        grid.appendChild(btn);
    }
}

function setupEventListeners() {
    document.getElementById('prevBtn').addEventListener('click', () => {
        if (currentQuestionOrder > 1) {
            loadQuestion(currentQuestionOrder - 1);
        }
    });

    document.getElementById('nextBtn').addEventListener('click', () => {
        if (currentQuestionOrder < totalQuestionsCount) {
            loadQuestion(currentQuestionOrder + 1);
        }
    });

    document.getElementById('clearAnswerBtn').addEventListener('click', async () => {
        selectedAnswer = null;
        document.querySelectorAll('.option-btn').forEach(btn => btn.classList.remove('selected'));
        await saveAnswerState();
    });

    document.getElementById('markReviewBtn').addEventListener('click', async () => {
        isMarkedForReview = !isMarkedForReview;
        await saveAnswerState();
        loadQuestion(currentQuestionOrder);
    });

    document.getElementById('bookmarkBtn').addEventListener('click', toggleBookmark);

    // Submit Modal events
    const modal = document.getElementById('confirmModal');
    document.getElementById('submitTestModalTriggerBtn').addEventListener('click', () => {
        let answered = 0, unanswered = 0, review = 0;
        for (let i = 1; i <= totalQuestionsCount; i++) {
            const st = questionsState[i] || {};
            if (st.isMarkedForReview) review++;
            if (st.isAnswered) answered++;
            else unanswered++;
        }
        document.getElementById('modalAnswered').innerText = answered;
        document.getElementById('modalUnanswered').innerText = unanswered;
        document.getElementById('modalReview').innerText = review;
        modal.style.display = 'flex';
    });

    document.getElementById('cancelModalBtn').addEventListener('click', () => {
        modal.style.display = 'none';
    });

    document.getElementById('confirmSubmitBtn').addEventListener('click', () => {
        executeFinalSubmit();
    });
}

async function executeFinalSubmit() {
    try {
        clearInterval(timerInterval);
        const result = await api.post(`/test-attempts/${currentAttemptId}/submit`);
        window.location.href = `/student/test-result.html?attemptId=${result.attemptId}`;
    } catch (err) {
        alert("Submission failed: " + err.message);
    }
}

function escapeHtml(str) {
    if (!str) return '';
    return str.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
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
    if (!currentQuestionId) return;
    const btn = document.getElementById('bookmarkBtn');
    btn.disabled = true;

    try {
        if (isBookmarked) {
            await api.delete(`/student/bookmarks/${currentQuestionId}`);
            updateBookmarkBtn(false);
        } else {
            await api.post(`/student/bookmarks/${currentQuestionId}`);
            updateBookmarkBtn(true);
        }
    } catch (error) {
        console.error('Bookmark toggle failed', error);
        alert('Failed to update bookmark.');
    } finally {
        btn.disabled = false;
    }
}
