document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();
    
    const params = new URLSearchParams(window.location.search);
    const subId = params.get('id');
    const subName = params.get('name');
    const catId = params.get('catId');
    const catName = params.get('catName');
    
    if (!subId || !catId) {
        window.location.href = '/student/dashboard.html';
        return;
    }
    
    document.getElementById('subcatName').textContent = subName;
    document.getElementById('pageTitle').textContent = subName;
    document.getElementById('pageDesc').textContent = `Explore topics and practice questions for ${subName}`;
    
    const backLink = document.getElementById('backToCat');
    backLink.textContent = catName;
    backLink.href = `/student/category.html?id=${catId}&name=${encodeURIComponent(catName)}`;
    
    loadTopics(subId, catId, catName, subName);
});

async function loadTopics(subId, catId, catName, subName) {
    const container = document.getElementById('topicsContainer');
    try {
        const topics = await api.get(`/subcategories/${subId}/topics`);
        container.innerHTML = '';
        
        if (topics.length === 0) {
            container.innerHTML = '<p>No topics found for this subject.</p>';
            return;
        }
        
        // Fetch question counts in parallel
        const topicsData = await Promise.all(topics.map(async (topic, index) => {
            let questionCount = 0;
            try {
                questionCount = await api.get(`/topics/${topic.id}/questions/count`);
            } catch(e) { console.error("Error fetching count", e); }
            return { topic, index, questionCount };
        }));

        topicsData.forEach(({ topic, index, questionCount }) => {
            const item = document.createElement('div');
            item.className = 'chapter-item';
            
            const startPracticeUrl = `/student/practice.html?topicId=${topic.id}&topicName=${encodeURIComponent(topic.name)}&subId=${subId}&subName=${encodeURIComponent(subName)}&catId=${catId}&catName=${encodeURIComponent(catName)}`;

            item.innerHTML = `
                <div class="chapter-info">
                    <h4>${index + 1}. ${topic.name}</h4>
                    <p><i class="fas fa-list-ol"></i> ${questionCount} Questions Available</p>
                </div>
                <div class="chapter-actions">
                    <a href="${startPracticeUrl}" class="btn btn-outline btn-small" ${questionCount === 0 ? 'style="pointer-events:none;opacity:0.5"' : ''}>Start Practice</a>
                </div>
            `;
            container.appendChild(item);
        });
        
    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load topics.</div>`;
    }
}
