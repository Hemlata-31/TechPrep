document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    const tabAvailable = document.getElementById('tabAvailable');
    const tabHistory = document.getElementById('tabHistory');
    const availableSection = document.getElementById('availableTestsSection');
    const historySection = document.getElementById('historySection');

    tabAvailable.addEventListener('click', () => {
        tabAvailable.className = 'btn btn-small';
        tabHistory.className = 'btn btn-outline btn-small';
        availableSection.style.display = 'block';
        historySection.style.display = 'none';
    });

    tabHistory.addEventListener('click', () => {
        tabHistory.className = 'btn btn-small';
        tabAvailable.className = 'btn btn-outline btn-small';
        historySection.style.display = 'block';
        availableSection.style.display = 'none';
        loadTestHistory();
    });

    loadAvailableTests();
});

async function loadAvailableTests() {
    const grid = document.getElementById('testsGrid');
    try {
        const tests = await api.get('/tests');
        grid.innerHTML = '';

        if (!tests || tests.length === 0) {
            grid.innerHTML = '<div class="card" style="padding: 2rem; text-align: center; color: #64748b;">No active mock tests available right now. Please check back later.</div>';
            return;
        }

        tests.forEach(test => {
            const card = document.createElement('div');
            card.className = 'course-card';
            card.innerHTML = `
                <div style="font-size: 2rem; color: #3b82f6; margin-bottom: 1rem;"><i class="fas fa-file-signature"></i></div>
                <h3>${test.title}</h3>
                <p style="margin-top: 5px; color: #64748b; font-size: 0.9rem;">${test.description || 'Full placement mock test'}</p>
                <div style="margin: 1rem 0; display: flex; flex-wrap: wrap; gap: 0.5rem;">
                    <span class="badge badge-outline"><i class="fas fa-clock"></i> ${test.durationMinutes} mins</span>
                    <span class="badge badge-outline"><i class="fas fa-question-circle"></i> ${test.totalQuestions} Qs</span>
                    <span class="badge badge-outline"><i class="fas fa-trophy"></i> ${test.totalMarks} Marks</span>
                    <span class="badge badge-warning">${test.selectedDifficulty || test.difficulty}</span>
                </div>
                <a href="/student/test-instructions.html?testId=${test.id}" class="btn btn-small" style="text-align: center;">Take Mock Test</a>
            `;
            grid.appendChild(card);
        });

    } catch (err) {
        console.error(err);
        grid.innerHTML = `<div class="alert" style="display:block">Failed to load mock tests: ${err.message}</div>`;
    }
}

async function loadTestHistory() {
    const container = document.getElementById('testHistoryList');
    try {
        const history = await api.get('/test-attempts');
        container.innerHTML = '';

        if (!history || history.length === 0) {
            container.innerHTML = '<div class="card" style="padding: 2rem; text-align: center; color: #64748b;">You have not taken any mock test attempts yet.</div>';
            return;
        }

        history.forEach(att => {
            const dateStr = new Date(att.startTime).toLocaleString();
            const item = document.createElement('div');
            item.className = 'chapter-item';
            
            const badgeClass = att.status === 'SUBMITTED' ? 'badge-success' : 'badge-warning';

            item.innerHTML = `
                <div class="chapter-info">
                    <h4>${att.testTitle} <span class="badge ${badgeClass}">${att.status}</span></h4>
                    <p><i class="fas fa-calendar-alt"></i> Date: ${dateStr} &nbsp;|&nbsp; <i class="fas fa-clock"></i> ${att.durationMinutes} Mins</p>
                    <p style="margin-top: 5px; color: #3b82f6; font-size: 0.9rem;">
                        <i class="fas fa-star"></i> Score: ${att.score}/${att.totalMarks} &nbsp;•&nbsp; 
                        <i class="fas fa-percent"></i> Percentage: ${att.percentage}% &nbsp;•&nbsp; 
                        <i class="fas fa-bullseye"></i> Accuracy: ${att.accuracy}%
                    </p>
                </div>
                <div class="chapter-actions">
                    <a href="/student/test-result.html?attemptId=${att.id}" class="btn btn-outline btn-small">View Report</a>
                </div>
            `;
            container.appendChild(item);
        });

    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load attempt history: ${err.message}</div>`;
    }
}
