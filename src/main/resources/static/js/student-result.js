document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    const params = new URLSearchParams(window.location.search);
    const sessionId = params.get('sessionId');

    if (!sessionId) {
        window.location.href = '/student/dashboard.html';
        return;
    }

    loadResult(sessionId);
});

async function loadResult(sessionId) {
    try {
        const res = await api.get(`/practice/sessions/${sessionId}/result`);

        document.getElementById('topicInfo').textContent = `${res.categoryName} > ${res.subCategoryName} > ${res.topicName} (${res.difficulty} Difficulty)`;
        
        document.getElementById('resTotal').textContent = res.totalQuestions;
        document.getElementById('resAttempted').textContent = res.attemptedQuestions;
        document.getElementById('resCorrect').textContent = res.correctAnswers;
        document.getElementById('resWrong').textContent = res.wrongAnswers;
        document.getElementById('resUnattempted').textContent = res.unattemptedQuestions;

        document.getElementById('resScore').textContent = `${res.score} Marks`;
        document.getElementById('resAccuracy').textContent = `${res.accuracy}%`;

        // Format time
        const mins = Math.floor(res.timeTakenSeconds / 60);
        const secs = res.timeTakenSeconds % 60;
        document.getElementById('resTime').textContent = `${mins} min${mins !== 1 ? 's' : ''} ${secs} sec${secs !== 1 ? 's' : ''}`;

        // Retry button link
        document.getElementById('retryBtn').href = `/student/practice.html?topicId=${res.topicId}&topicName=${encodeURIComponent(res.topicName)}`;

    } catch (err) {
        console.error(err);
        alert('Failed to load session result: ' + err.message);
    }
}
