document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    if (user) {
        document.getElementById('welcomeMsg').textContent = `Welcome, ${user.name}`;
    }
    initLogoutBtn();
    loadCategories();
    loadStudentStats();
});

const categoryIcons = {
    'Aptitude': 'fa-calculator',
    'Reasoning': 'fa-brain',
    'Verbal Ability': 'fa-language',
    'Communication': 'fa-comments',
    'Technical': 'fa-laptop-code',
    'DSA': 'fa-project-diagram'
};

async function loadStudentStats() {
    try {
        const stats = await api.get('/practice/stats');
        document.getElementById('statAttempted').textContent = stats.totalQuestionsAttempted || 0;
        document.getElementById('statAccuracy').textContent = (stats.overallAccuracy || 0) + '%';
        document.getElementById('statCompleted').textContent = stats.totalSessionsCompleted || 0;

        const container = document.getElementById('recentActivityContainer');
        if (!stats.recentSessions || stats.recentSessions.length === 0) {
            container.innerHTML = '<div class="card" style="padding: 1rem; color: #64748b;">No recent practice activity yet. Select a category below to start practicing!</div>';
            return;
        }

        container.innerHTML = '';
        stats.recentSessions.forEach(sess => {
            const dateStr = sess.completedAt ? new Date(sess.completedAt).toLocaleDateString() : new Date(sess.startedAt).toLocaleDateString();
            const item = document.createElement('div');
            item.className = 'chapter-item';
            item.innerHTML = `
                <div class="chapter-info">
                    <h4>${sess.topicName} <span class="badge badge-${sess.status === 'COMPLETED' ? 'success' : 'warning'}">${sess.status}</span></h4>
                    <p><i class="fas fa-layer-group"></i> ${sess.categoryName} &gt; ${sess.subCategoryName} &nbsp;|&nbsp; <i class="fas fa-chart-line"></i> Score: ${sess.score} &nbsp;|&nbsp; Accuracy: ${sess.accuracy}%</p>
                </div>
                <div class="chapter-actions">
                    <a href="/student/result.html?sessionId=${sess.id}" class="btn btn-outline btn-small">View Result</a>
                </div>
            `;
            container.appendChild(item);
        });

    } catch (e) {
        console.error('Error loading stats:', e);
    }
}

async function loadCategories() {
    const container = document.getElementById('categoriesContainer');
    try {
        const categories = await api.get('/categories');
        container.innerHTML = '';
        
        categories.forEach(cat => {
            const icon = categoryIcons[cat.name] || 'fa-book';
            
            const card = document.createElement('div');
            card.className = 'course-card';
            card.innerHTML = `
                <div class="course-icon">
                    <i class="fas ${icon}"></i>
                </div>
                <h4>${cat.name}</h4>
                <p>${cat.description || 'Prepare for ' + cat.name}</p>
                <a href="/student/category.html?id=${cat.id}&name=${encodeURIComponent(cat.name)}" class="btn">Explore Subjects</a>
            `;
            container.appendChild(card);
        });
        
    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load categories.</div>`;
    }
}
