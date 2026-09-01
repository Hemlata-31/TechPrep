document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    loadHistory();
});

async function loadHistory() {
    const container = document.getElementById('historyContainer');
    try {
        const history = await api.get('/practice/history');
        container.innerHTML = '';

        if (!history || history.length === 0) {
            container.innerHTML = '<div class="card" style="padding: 2rem; text-align: center; color: #64748b;">You have not completed any practice sessions yet.<br><br><a href="/student/dashboard.html" class="btn btn-small" style="width:auto; display:inline-block;">Start Practicing Now</a></div>';
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
                    <a href="/student/result.html?sessionId=${sess.id}" class="btn btn-outline btn-small">View Result</a>
                </div>
            `;
            container.appendChild(item);
        });

    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load practice history.</div>`;
    }
}
