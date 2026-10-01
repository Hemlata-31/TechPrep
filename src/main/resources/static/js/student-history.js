document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    const tabPractice = document.getElementById('tabPractice');
    const tabMockTest = document.getElementById('tabMockTest');
    
    const activeStyle = 'border: none; background: #3b82f6; color: white;';
    const inactiveStyle = 'border: none; background: transparent; color: #64748b;';

    tabPractice.addEventListener('click', () => {
        tabPractice.style.cssText = activeStyle;
        tabMockTest.style.cssText = inactiveStyle;
        loadPracticeHistory();
    });

    tabMockTest.addEventListener('click', () => {
        tabMockTest.style.cssText = activeStyle;
        tabPractice.style.cssText = inactiveStyle;
        loadMockTestHistory();
    });

    // Load default
    loadPracticeHistory();
});

async function loadPracticeHistory() {
    const container = document.getElementById('historyContainer');
    container.innerHTML = '<p>Loading practice history...</p>';
    try {
        const history = await api.get('/practice/history');
        container.innerHTML = '';

        if (!history || history.length === 0) {
            container.innerHTML = '<div class="card" style="padding: 2rem; text-align: center; color: #64748b;">You have not completed any practice sessions yet.<br><br><a href="/student/dashboard.html" class="btn btn-small" style="width:auto; display:inline-block; margin-top:1rem;">Start Practicing Now</a></div>';
            return;
        }

        history.forEach(sess => {
            const dateStr = new Date(sess.startedAt).toLocaleString();
            const item = document.createElement('div');
            item.className = 'chapter-item';
            
            const badgeClass = sess.status === 'COMPLETED' ? 'badge-success' : 'badge-warning';

            item.innerHTML = `
                <div class="chapter-info">
                    <h4>${sess.topicName} <span class="badge ${badgeClass}">${sess.status}</span></h4>
                    <p><i class="fas fa-folder"></i> ${sess.categoryName} &gt; ${sess.subCategoryName} &nbsp;|&nbsp; <i class="fas fa-signal"></i> ${sess.difficulty} &nbsp;|&nbsp; <i class="fas fa-calendar-alt"></i> ${dateStr}</p>
                    <p style="margin-top: 5px; color: #3b82f6; font-size: 0.85rem;">
                        <i class="fas fa-check-circle"></i> ${sess.correctAnswers}/${sess.totalQuestions} Correct &nbsp;•&nbsp; 
                        <i class="fas fa-bullseye"></i> ${sess.accuracy}% Accuracy &nbsp;•&nbsp; 
                        <i class="fas fa-star"></i> Score: ${sess.score}
                    </p>
                </div>
                <div class="chapter-actions">
                    <a href="/student/result.html?sessionId=${sess.id}" class="btn btn-outline btn-small"><i class="fas fa-eye"></i> Review</a>
                </div>
            `;
            container.appendChild(item);
        });

    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load practice history.</div>`;
    }
}

async function loadMockTestHistory() {
    const container = document.getElementById('historyContainer');
    container.innerHTML = '<p>Loading mock test history...</p>';
    try {
        const history = await api.get('/test-attempts');
        container.innerHTML = '';

        if (!history || history.length === 0) {
            container.innerHTML = '<div class="card" style="padding: 2rem; text-align: center; color: #64748b;">You have not attempted any mock tests yet.<br><br><a href="/student/mock-tests.html" class="btn btn-small" style="width:auto; display:inline-block; margin-top:1rem;">Take a Mock Test</a></div>';
            return;
        }

        history.forEach(att => {
            const dateStr = new Date(att.startTime).toLocaleString();
            const item = document.createElement('div');
            item.className = 'chapter-item';
            
            let badgeClass = 'badge-warning';
            if (att.status === 'SUBMITTED' || att.status === 'EXPIRED') {
                badgeClass = 'badge-success';
            }

            // Note: History page shows 'Review' instead of 'View Result' per requirements.
            let actionBtn = `<a href="/student/test-result.html?attemptId=${att.id}" class="btn btn-outline btn-small"><i class="fas fa-eye"></i> Review</a>`;
            if (att.status === 'IN_PROGRESS') {
                actionBtn = `<a href="/student/exam-interface.html?attemptId=${att.id}" class="btn btn-small"><i class="fas fa-play"></i> Resume</a>`;
            }

            item.innerHTML = `
                <div class="chapter-info">
                    <h4>${att.testTitle} <span class="badge ${badgeClass}">${att.status}</span></h4>
                    <p><i class="fas fa-clock"></i> Duration: ${att.durationMinutes} mins &nbsp;|&nbsp; <i class="fas fa-calendar-alt"></i> ${dateStr}</p>
                    <p style="margin-top: 5px; color: #3b82f6; font-size: 0.85rem;">
                        <i class="fas fa-star"></i> Score: ${att.score}/${att.totalMarks} &nbsp;•&nbsp; 
                        <i class="fas fa-bullseye"></i> ${att.accuracy}% Accuracy &nbsp;•&nbsp;
                        <i class="fas fa-percentage"></i> ${att.percentage}% Total
                    </p>
                </div>
                <div class="chapter-actions">
                    ${actionBtn}
                </div>
            `;
            container.appendChild(item);
        });

    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load mock test history.</div>`;
    }
}
