document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (!user) return;
    initLogoutBtn();

    loadCategories();
});

async function loadCategories() {
    const container = document.getElementById('categoriesContainer');
    try {
        const categories = await api.get('/categories');

        if (!categories || categories.length === 0) {
            container.innerHTML = '<p style="color: #64748b;">No preparation categories available yet.</p>';
            return;
        }

        container.innerHTML = categories.map(cat => {
            let iconClass = 'fa-brain';
            const nameLower = cat.name.toLowerCase();
            if (nameLower.includes('aptitude')) iconClass = 'fa-calculator';
            else if (nameLower.includes('reasoning')) iconClass = 'fa-puzzle-piece';
            else if (nameLower.includes('verbal')) iconClass = 'fa-language';
            else if (nameLower.includes('communication')) iconClass = 'fa-comments';
            else if (nameLower.includes('technical') || nameLower.includes('cs')) iconClass = 'fa-laptop-code';
            else if (nameLower.includes('dsa') || nameLower.includes('coding')) iconClass = 'fa-code';

            return `
                <div class="course-card">
                    <div class="course-icon">
                        <i class="fas ${iconClass}"></i>
                    </div>
                    <h4>${escapeHtml(cat.name)}</h4>
                    <p>${escapeHtml(cat.description || 'Practice questions and mock tests for placement prep.')}</p>
                    <a href="/student/category.html?id=${cat.id}" class="btn btn-outline btn-small" style="text-align: center;">Explore Subjects <i class="fas fa-arrow-right"></i></a>
                </div>
            `;
        }).join('');
    } catch (error) {
        console.error('Failed to load categories:', error);
        container.innerHTML = '<p style="color: #ef4444;">Failed to load categories.</p>';
    }
}

function escapeHtml(str) {
    if (!str) return '';
    return str.replace(/[&<>"']/g, function(m) {
        return {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        }[m];
    });
}
