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
});

const categoryIcons = {
    'Aptitude': 'fa-calculator',
    'Reasoning': 'fa-brain',
    'Verbal Ability': 'fa-language',
    'Communication': 'fa-comments',
    'Technical': 'fa-laptop-code',
    'DSA': 'fa-project-diagram'
};

async function loadCategories() {
    const container = document.getElementById('categoriesContainer');
    try {
        const categories = await api.get('/categories');
        container.innerHTML = '';
        
        categories.forEach(cat => {
            const icon = categoryIcons[cat.name] || 'fa-book';
            
            // Generate a random progress for visual effect (since real progress is in Phase 6)
            const fakeProgress = Math.floor(Math.random() * 40); 
            
            const card = document.createElement('div');
            card.className = 'course-card';
            card.innerHTML = `
                <div class="course-icon">
                    <i class="fas ${icon}"></i>
                </div>
                <h4>${cat.name}</h4>
                <p>${cat.description || 'Prepare for ' + cat.name}</p>
                <div class="progress-container">
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: ${fakeProgress}%"></div>
                    </div>
                    <div class="progress-text">${fakeProgress}% Completed</div>
                </div>
                <a href="/student/category.html?id=${cat.id}&name=${encodeURIComponent(cat.name)}" class="btn">Explore</a>
            `;
            container.appendChild(card);
        });
        
    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load categories.</div>`;
    }
}
