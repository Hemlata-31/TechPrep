// =====================================================================
// student-practice.js — Practice Session with Per-Question Timer
// =====================================================================

let currentSessionId     = null;
let currentQuestionOrder = 1;
let totalQuestions       = 10;
let currentQuestionData  = null;
let selectedOption       = null;
let isBookmarked         = false;

// ── Timer state ──
let timedModeEnabled   = true;    // read from toggle at session start
let questionTimeLimit  = 60;      // seconds for current question
let timerSecondsLeft   = 60;
let timerIntervalId    = null;
const CIRCUMFERENCE    = 2 * Math.PI * 20; // r=20 → 125.66

const DIFFICULTY_TIME = { EASY: 60, MEDIUM: 90, HARD: 120 };

// ── Notes state ──
let activeTopicId      = null;    // set from URL param at DOMContentLoaded
let notesDebounceTimer = null;    // debounce for auto-save

// ─────────────────────────────────────────────
// INIT
// ─────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    const params    = new URLSearchParams(window.location.search);
    const topicId   = params.get('topicId');
    activeTopicId   = topicId;    // keep globally for Notes panel
    const topicName = params.get('topicName');
    const subId     = params.get('subId');
    const subName   = params.get('subName');
    const catId     = params.get('catId');
    const catName   = params.get('catName');

    if (!topicId) {
        window.location.href = '/student/dashboard.html';
        return;
    }

    // Set up breadcrumbs
    const bCat = document.getElementById('breadCat');
    const validCatName = (catName && catName !== 'null' && catName.trim() !== '') ? catName : null;
    const validCatId   = (catId   && catId   !== 'null') ? catId : null;
    if (validCatName && validCatId) {
        bCat.textContent = validCatName;
        bCat.href = `/student/category.html?id=${validCatId}&name=${encodeURIComponent(validCatName)}`;
    } else {
        bCat.textContent = 'Categories';
        bCat.href = '/student/categories.html';
    }

    const bSub = document.getElementById('breadSub');
    const validSubName = (subName && subName !== 'null' && subName.trim() !== '') ? subName : null;
    const validSubId   = (subId   && subId   !== 'null') ? subId : null;
    if (validSubName && validSubId) {
        bSub.textContent = validSubName;
        bSub.href = `/student/subcategory.html?id=${validSubId}&name=${encodeURIComponent(validSubName)}${validCatId ? `&catId=${validCatId}&catName=${encodeURIComponent(validCatName || '')}` : ''}`;
    } else {
        if (bSub.nextElementSibling && bSub.nextElementSibling.classList.contains('fa-chevron-right')) {
            bSub.nextElementSibling.style.display = 'none';
        }
        bSub.style.display = 'none';
    }

    document.getElementById('breadTopic').textContent = topicName;
    document.getElementById('topicTitle').textContent  = `Practice: ${topicName}`;

    // Setup form submit
    document.getElementById('startPracticeForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const difficulty = document.getElementById('difficultySelect').value;
        const qCountEl   = document.getElementById('questionsCount');
        const numQuestions = qCountEl ? parseInt(qCountEl.value) : 10;

        // Read timed-mode toggle
        const toggle = document.getElementById('timedModeToggle');
        timedModeEnabled = toggle ? toggle.checked : true;

        await startSession(topicId, difficulty, numQuestions, topicName);
    });

    // Options click
    document.querySelectorAll('.option-card').forEach(card => {
        card.addEventListener('click', () => {
            if (currentQuestionData && currentQuestionData.isAnswered) return;
            document.querySelectorAll('.option-card').forEach(c => c.classList.remove('selected'));
            card.classList.add('selected');
            selectedOption = card.getAttribute('data-option');
            document.getElementById('submitAnswerBtn').disabled = false;
        });
    });

    // Keyboard shortcut: A/B/C/D to select, Enter to submit
    document.addEventListener('keydown', (e) => {
        if (!currentQuestionData || currentQuestionData.isAnswered) return;
        const key = e.key.toUpperCase();
        if (['A','B','C','D'].includes(key)) {
            const card = document.querySelector(`.option-card[data-option="${key}"]`);
            if (card) card.click();
        }
        if (e.key === 'Enter' && selectedOption) {
            e.preventDefault();
            submitCurrentAnswer();
        }
    });

    // Action listeners
    document.getElementById('submitAnswerBtn').addEventListener('click', submitCurrentAnswer);
    document.getElementById('prevBtn').addEventListener('click',        () => navigateQuestion(-1));
    document.getElementById('nextBtn').addEventListener('click',        () => navigateQuestion(1));
    document.getElementById('finishEarlyBtn').addEventListener('click', finishSession);
    document.getElementById('bookmarkBtn').addEventListener('click',    toggleBookmark);
    document.getElementById('aiExplainBtn').addEventListener('click',   fetchAIExplanation);
});

// ─────────────────────────────────────────────
// START SESSION
// ─────────────────────────────────────────────
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
        totalQuestions   = session.totalQuestions;

        document.getElementById('topicBadge').textContent = topicName;
        document.getElementById('diffBadge').textContent  = session.difficulty;
        document.getElementById('totalQCount').textContent = totalQuestions;

        // Set per-difficulty time
        questionTimeLimit = DIFFICULTY_TIME[difficulty.toUpperCase()] || 60;

        document.getElementById('setupContainer').style.display    = 'none';
        document.getElementById('practiceContainer').style.display = 'block';

        currentQuestionOrder = 1;
        await loadQuestion(currentQuestionOrder);

        // Load existing note for this topic (non-blocking)
        loadNoteForTopic();

    } catch (err) {
        console.error(err);
        alertBox.textContent   = err.message || 'Failed to start practice session';
        alertBox.style.display = 'block';
    }
}

// ─────────────────────────────────────────────
// LOAD QUESTION
// ─────────────────────────────────────────────
async function loadQuestion(order) {
    stopTimer();

    selectedOption = null;
    document.getElementById('submitAnswerBtn').disabled = true;
    document.getElementById('explanationBox').style.display = 'none';
    document.getElementById('aiExplainBtn').style.display = 'inline-block';
    document.getElementById('aiExplanationLoading').style.display = 'none';
    document.getElementById('aiExplanationResult').style.display  = 'none';
    document.getElementById('aiExplanationError').style.display   = 'none';

    document.querySelectorAll('.option-card').forEach(c => { c.className = 'option-card'; });

    try {
        currentQuestionData = await api.get(`/practice/sessions/${currentSessionId}/questions/${order}`);

        document.getElementById('currentQIndex').textContent = order;
        document.getElementById('questionText').textContent  = currentQuestionData.questionText;
        document.getElementById('optA').textContent = currentQuestionData.optionA;
        document.getElementById('optB').textContent = currentQuestionData.optionB;
        document.getElementById('optC').textContent = currentQuestionData.optionC;
        document.getElementById('optD').textContent = currentQuestionData.optionD;

        document.getElementById('qDifficulty').textContent = `Difficulty: ${currentQuestionData.difficulty}`;
        document.getElementById('qMarks').innerHTML = `<i class="fas fa-star"></i> ${currentQuestionData.marks} Mark${currentQuestionData.marks > 1 ? 's' : ''}`;

        // Progress bar
        document.getElementById('sessionProgressFill').style.width = `${(order / totalQuestions) * 100}%`;

        // Nav buttons
        document.getElementById('prevBtn').disabled = (order <= 1);
        const nextBtn = document.getElementById('nextBtn');
        nextBtn.innerHTML = order >= totalQuestions
            ? 'Finish Session <i class="fas fa-flag-checkered"></i>'
            : 'Next <i class="fas fa-chevron-right"></i>';

        if (currentQuestionData.isAnswered) {
            displayAnswerResult({
                selectedAnswer: currentQuestionData.selectedAnswer,
                correctAnswer:  currentQuestionData.correctAnswer,
                isCorrect:      currentQuestionData.isCorrect,
                explanation:    currentQuestionData.explanation
            });
            hideTimer(); // already answered — no timer needed
        } else {
            startTimer(currentQuestionData.difficulty);
        }

        // Bookmark status
        try {
            const status = await api.get(`/student/bookmarks/${currentQuestionData.questionId}/status`);
            updateBookmarkBtn(status.bookmarked);
        } catch (err) {
            document.getElementById('bookmarkBtn').style.display = 'none';
        }

    } catch (err) {
        console.error(err);
        alert('Failed to load question #' + order);
    }
}

// ─────────────────────────────────────────────
// ── TIMER ENGINE ──
// ─────────────────────────────────────────────
function startTimer(difficulty) {
    if (!timedModeEnabled) {
        hideTimer();
        return;
    }

    const seconds = DIFFICULTY_TIME[(difficulty || 'EASY').toUpperCase()] || questionTimeLimit;
    timerSecondsLeft  = seconds;
    questionTimeLimit = seconds;

    const wrapper   = document.getElementById('questionTimerWrapper');
    const arc       = document.getElementById('timerArc');
    const label     = document.getElementById('timerLabel');
    const timerText = document.getElementById('timerText');
    wrapper.style.display = 'flex';

    updateTimerUI(arc, label, timerText, wrapper, seconds, seconds);

    timerIntervalId = setInterval(() => {
        timerSecondsLeft--;

        if (timerSecondsLeft <= 0) {
            stopTimer();
            handleTimerExpiry(arc, label, timerText, wrapper);
            return;
        }

        updateTimerUI(arc, label, timerText, wrapper, timerSecondsLeft, questionTimeLimit);
    }, 1000);
}

function updateTimerUI(arc, label, timerText, wrapper, secondsLeft, totalSeconds) {
    // Arc progress
    const fraction = secondsLeft / totalSeconds;
    const offset   = CIRCUMFERENCE * (1 - fraction);
    arc.style.strokeDashoffset = offset;

    // Color classes
    arc.classList.remove('warning','danger');
    wrapper.classList.remove('warning','danger');

    if (secondsLeft <= 10) {
        arc.classList.add('danger');
        wrapper.classList.add('danger');
    } else if (secondsLeft <= Math.ceil(totalSeconds * 0.33)) {
        arc.classList.add('warning');
        wrapper.classList.add('warning');
    }

    label.textContent    = secondsLeft;
    timerText.textContent = `${secondsLeft}s`;
}

function stopTimer() {
    if (timerIntervalId) {
        clearInterval(timerIntervalId);
        timerIntervalId = null;
    }
}

function hideTimer() {
    stopTimer();
    const wrapper = document.getElementById('questionTimerWrapper');
    if (wrapper) wrapper.style.display = 'none';
}

async function handleTimerExpiry(arc, label, timerText, wrapper) {
    arc.style.strokeDashoffset = CIRCUMFERENCE;
    label.textContent    = '0';
    timerText.textContent = '0s';

    // Flash the question card to signal auto-submit
    const card = document.querySelector('.practice-card');
    if (card) {
        card.classList.add('auto-submit-flash');
        setTimeout(() => card.classList.remove('auto-submit-flash'), 1500);
    }

    hideTimer();

    if (!currentQuestionData || currentQuestionData.isAnswered) return;

    // Auto-submit: if an option was selected, submit it; otherwise submit blank (counts as wrong)
    const answerToSubmit = selectedOption || null;

    if (answerToSubmit) {
        // submit the selected answer
        await submitCurrentAnswer(true);
    } else {
        // No option selected — submit a blank (backend marks as wrong; we pick no visible option)
        // Show a timed-out banner without submitting a real wrong answer
        showTimedOutBanner();
    }
}

function showTimedOutBanner() {
    // Mark UI as answered without submitting — user sees "Time Up" but can still navigate
    document.getElementById('submitAnswerBtn').disabled = true;

    const expBox        = document.getElementById('explanationBox');
    const feedbackBanner = document.getElementById('resultFeedback');
    feedbackBanner.className = 'feedback-banner banner-error';
    feedbackBanner.innerHTML = '<i class="fas fa-clock"></i> <strong>Time\'s Up!</strong> You didn\'t answer in time.';

    document.getElementById('explanationText').textContent = currentQuestionData.explanation
        ? `Correct answer was Option ${currentQuestionData.correctAnswer}. ${currentQuestionData.explanation}`
        : `Correct answer was Option ${currentQuestionData.correctAnswer}.`;

    // Highlight correct answer
    document.querySelectorAll('.option-card').forEach(card => {
        if (card.getAttribute('data-option') === currentQuestionData.correctAnswer) {
            card.classList.add('correct');
        }
    });

    expBox.style.display = 'block';
    currentQuestionData.isAnswered = true; // prevent double submit
}

// ─────────────────────────────────────────────
// SUBMIT ANSWER
// ─────────────────────────────────────────────
async function submitCurrentAnswer(fromTimer = false) {
    if (!currentQuestionData) return;
    if (!fromTimer && !selectedOption)  return;

    stopTimer();
    hideTimer();

    try {
        const response = await api.post(`/practice/sessions/${currentSessionId}/attempts/${currentQuestionData.attemptId}/submit`, {
            selectedAnswer: selectedOption || 'X'   // 'X' is never A/B/C/D → counts as wrong on backend
        });

        currentQuestionData.isAnswered    = true;
        currentQuestionData.selectedAnswer = response.selectedAnswer;
        currentQuestionData.correctAnswer  = response.correctAnswer;
        currentQuestionData.isCorrect      = response.isCorrect;
        currentQuestionData.explanation    = response.explanation;

        displayAnswerResult(response);

    } catch (err) {
        console.error(err);
        alert('Failed to submit answer: ' + err.message);
    }
}

// ─────────────────────────────────────────────
// DISPLAY RESULT
// ─────────────────────────────────────────────
function displayAnswerResult(result) {
    document.getElementById('submitAnswerBtn').disabled = true;

    document.querySelectorAll('.option-card').forEach(card => {
        const opt = card.getAttribute('data-option');
        if (opt === result.correctAnswer) card.classList.add('correct');
        if (opt === result.selectedAnswer && !result.isCorrect) card.classList.add('wrong');
    });

    const feedbackBanner = document.getElementById('resultFeedback');
    if (result.isCorrect) {
        feedbackBanner.className = 'feedback-banner banner-success';
        feedbackBanner.innerHTML = '<i class="fas fa-check-circle"></i> <strong>Correct Answer!</strong> Well done.';
    } else {
        feedbackBanner.className = 'feedback-banner banner-error';
        feedbackBanner.innerHTML = `<i class="fas fa-times-circle"></i> <strong>Incorrect.</strong> Correct answer is Option ${result.correctAnswer}.`;
    }

    document.getElementById('explanationText').textContent = result.explanation || 'No detailed explanation provided.';
    document.getElementById('explanationBox').style.display = 'block';
}

// ─────────────────────────────────────────────
// NAVIGATION
// ─────────────────────────────────────────────
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
        stopTimer();
        try {
            await api.post(`/practice/sessions/${currentSessionId}/complete`, {});
        } catch (err) { /* continue to result page anyway */ }
        window.location.href = `/student/result.html?sessionId=${currentSessionId}`;
    }
}

// ─────────────────────────────────────────────
// BOOKMARK
// ─────────────────────────────────────────────
function updateBookmarkBtn(bookmarked) {
    isBookmarked = bookmarked;
    const btn  = document.getElementById('bookmarkBtn');
    const icon = document.getElementById('bookmarkIcon');
    const text = document.getElementById('bookmarkText');
    btn.style.display = 'inline-block';
    if (bookmarked) {
        icon.className = 'fas fa-bookmark'; icon.style.color = '#ef4444';
        text.textContent = 'Saved';
        btn.style.borderColor = '#ef4444'; btn.style.color = '#ef4444';
    } else {
        icon.className = 'far fa-bookmark'; icon.style.color = '';
        text.textContent = 'Save';
        btn.style.borderColor = ''; btn.style.color = '';
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

// ─────────────────────────────────────────────
// AI EXPLANATION
// ─────────────────────────────────────────────
async function fetchAIExplanation() {
    if (!currentQuestionData) return;
    const btn     = document.getElementById('aiExplainBtn');
    const loading = document.getElementById('aiExplanationLoading');
    const result  = document.getElementById('aiExplanationResult');
    const errorDiv = document.getElementById('aiExplanationError');

    btn.style.display     = 'none';
    loading.style.display = 'block';
    result.style.display  = 'none';
    errorDiv.style.display = 'none';

    try {
        const response = await api.post('/ai/explain', {
            questionText:  currentQuestionData.questionText,
            optionA:       currentQuestionData.optionA,
            optionB:       currentQuestionData.optionB,
            optionC:       currentQuestionData.optionC,
            optionD:       currentQuestionData.optionD,
            correctAnswer: currentQuestionData.correctAnswer,
            explanation:   currentQuestionData.explanation
        });
        loading.style.display = 'none';
        document.getElementById('aiExplanationText').textContent = response.aiExplanation;
        result.style.display = 'block';
    } catch (err) {
        loading.style.display = 'none';
        document.getElementById('aiErrorMsg').textContent = err.message || 'Failed to get AI explanation.';
        errorDiv.style.display   = 'block';
        btn.style.display = 'inline-block';
    }
}

// ─────────────────────────────────────────────
// NOTES PANEL
// ─────────────────────────────────────────────

function toggleNotesPanel() {
    const body = document.getElementById('notesBody');
    const icon = document.getElementById('notesToggleIcon');
    const isOpen = body.classList.contains('open');
    body.classList.toggle('open', !isOpen);
    icon.classList.toggle('open', !isOpen);
}

async function loadNoteForTopic() {
    if (!activeTopicId) return;
    try {
        const note = await api.get(`/api/notes/${activeTopicId}`);
        if (note && note.content) {
            document.getElementById('notesTextarea').value = note.content;
        }
    } catch (err) {
        // 204 No Content or error — textarea stays empty, that's fine
        if (err.status !== 204) {
            console.debug('No existing note for this topic or error loading note:', err);
        }
    }

    // Attach debounce auto-save listener (once)
    const textarea = document.getElementById('notesTextarea');
    textarea.addEventListener('input', () => {
        clearTimeout(notesDebounceTimer);
        showNoteStatus('saving');
        notesDebounceTimer = setTimeout(saveNote, 1500);
    });
}

async function saveNote() {
    if (!activeTopicId) return;
    clearTimeout(notesDebounceTimer);
    const content = document.getElementById('notesTextarea').value;
    showNoteStatus('saving');
    try {
        await api.put(`/api/notes/${activeTopicId}`, { content });
        showNoteStatus('saved');
        setTimeout(() => hideNoteStatus(), 2500);
    } catch (err) {
        console.error('Failed to save note:', err);
        hideNoteStatus();
    }
}

function showNoteStatus(state) {
    const savedMsg  = document.getElementById('notesSavedMsg');
    const savingMsg = document.getElementById('notesSavingMsg');
    if (state === 'saved') {
        savingMsg.style.display = 'none';
        savedMsg.style.display  = 'inline';
    } else {
        savedMsg.style.display  = 'none';
        savingMsg.style.display = 'inline';
    }
}

function hideNoteStatus() {
    document.getElementById('notesSavedMsg').style.display  = 'none';
    document.getElementById('notesSavingMsg').style.display = 'none';
}
