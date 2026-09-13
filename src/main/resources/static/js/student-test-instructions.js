document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    const params = new URLSearchParams(window.location.search);
    const testId = params.get('testId');
    if (!testId) {
        window.location.href = '/student/mock-tests.html';
        return;
    }

    try {
        const test = await api.get(`/tests/${testId}`);
        document.getElementById('testTitle').innerText = test.title;
        document.getElementById('testCategory').innerText = test.categoryName || 'General Mock Test';
        document.getElementById('testDuration').innerText = test.durationMinutes;
        document.getElementById('testTotalQ').innerText = test.totalQuestions;
        document.getElementById('testTotalMarks').innerText = test.totalMarks;
        document.getElementById('testDifficulty').innerText = test.selectedDifficulty || test.difficulty;

        document.getElementById('startTestBtn').addEventListener('click', async () => {
            try {
                const attempt = await api.post(`/tests/${testId}/start`);
                window.location.href = `/student/exam-interface.html?attemptId=${attempt.attemptId}`;
            } catch (err) {
                alert('Error starting test: ' + err.message);
            }
        });

    } catch (err) {
        alert('Failed to load test details: ' + err.message);
        window.location.href = '/student/mock-tests.html';
    }
});
