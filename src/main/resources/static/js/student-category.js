document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();
    
    const params = new URLSearchParams(window.location.search);
    const catId = params.get('id');
    const catName = params.get('name');
    
    if (!catId) {
        window.location.href = '/student/dashboard.html';
        return;
    }
    
    document.getElementById('catName').textContent = catName;
    document.getElementById('pageTitle').textContent = catName;
    document.getElementById('pageDesc').textContent = `Master the concepts of ${catName}`;
    
    loadSubCategories(catId, catName);
});

async function loadSubCategories(catId, catName) {
    const container = document.getElementById('subcategoriesContainer');
    try {
        const subcategories = await api.get(`/categories/${catId}/subcategories`);
        container.innerHTML = '';
        
        if (subcategories.length === 0) {
            container.innerHTML = '<p>No subjects found for this category.</p>';
            return;
        }
        
        subcategories.forEach(sub => {
            const card = document.createElement('div');
            card.className = 'course-card';
            card.innerHTML = `
                <div class="course-icon" style="background: #f1f5f9; color: #3b82f6;">
                    <i class="fas fa-bookmark"></i>
                </div>
                <h4>${sub.name}</h4>
                <p>${sub.description || 'Learn ' + sub.name}</p>
                <a href="/student/subcategory.html?id=${sub.id}&name=${encodeURIComponent(sub.name)}&catId=${catId}&catName=${encodeURIComponent(catName)}" class="btn btn-outline">View Chapters</a>
            `;
            container.appendChild(card);
        });
        
    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load subjects.</div>`;
    }
}
