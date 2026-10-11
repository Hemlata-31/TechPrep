document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();
    loadSavedQuestions();

    document.getElementById('categoryFilter').addEventListener('change', renderQuestions);
    document.getElementById('topicFilter').addEventListener('change', renderQuestions);
    document.getElementById('difficultyFilter').addEventListener('change', renderQuestions);
});

let allSavedQuestions = [];

async function loadSavedQuestions() {
    const container = document.getElementById('savedQuestionsContainer');
    container.innerHTML = '<p style="color:#64748b;">Loading saved questions...</p>';
    try {
        const questions = await api.get('/student/bookmarks');
        allSavedQuestions = questions;
        populateFilters(questions);
        renderQuestions();
    } catch (error) {
        container.innerHTML = `<div class="card" style="padding:2rem; text-align:center; color:#64748b;">
            <i class="fas fa-bookmark" style="font-size:2rem; margin-bottom:1rem; display:block;"></i>
            <p>Failed to load saved questions. Please try again.</p>
        </div>`;
    }
}

function populateFilters(questions) {
    const categoryFilter = document.getElementById('categoryFilter');
    const topicFilter = document.getElementById('topicFilter');

    // Clear existing options except first
    while (categoryFilter.options.length > 1) categoryFilter.remove(1);
    while (topicFilter.options.length > 1) topicFilter.remove(1);

    const categories = new Set();
    const topics = new Set();

    questions.forEach(q => {
        if (q.categoryName) categories.add(q.categoryName);
        if (q.topicName) topics.add(q.topicName);
    });

    categories.forEach(c => {
        const option = document.createElement('option');
        option.value = c;
        option.textContent = c;
        categoryFilter.appendChild(option);
    });

    topics.forEach(t => {
        const option = document.createElement('option');
        option.value = t;
        option.textContent = t;
        topicFilter.appendChild(option);
    });
}

function renderQuestions() {
    const container = document.getElementById('savedQuestionsContainer');
    const categoryFilter = document.getElementById('categoryFilter').value;
    const topicFilter = document.getElementById('topicFilter').value;
    const difficultyFilter = document.getElementById('difficultyFilter').value;

    let filtered = allSavedQuestions;

    if (categoryFilter) filtered = filtered.filter(q => q.categoryName === categoryFilter);
    if (topicFilter) filtered = filtered.filter(q => q.topicName === topicFilter);
    if (difficultyFilter) filtered = filtered.filter(q => q.difficulty === difficultyFilter);

    if (allSavedQuestions.length === 0) {
        container.innerHTML = `<div class="card" style="padding:2rem; text-align:center; color:#64748b;">
            <i class="fas fa-bookmark" style="font-size:2.5rem; margin-bottom:1rem; display:block; color:#e2e8f0;"></i>
            <h3 style="margin-bottom:0.5rem; color:#1e293b;">No Saved Questions Yet</h3>
            <p>Bookmark questions during practice sessions to review them here.</p>
            <a href="/student/categories.html" class="btn" style="width:auto; display:inline-block; margin-top:1rem;">Start Practicing</a>
        </div>`;
        return;
    }

    if (filtered.length === 0) {
        container.innerHTML = '<div class="card" style="padding:1.5rem; color:#64748b; text-align:center;">No saved questions found matching your criteria.</div>';
        return;
    }

    container.innerHTML = '';
    filtered.forEach(q => {
        const diffColor = q.difficulty === 'EASY' ? '#10b981' : (q.difficulty === 'MEDIUM' ? '#f59e0b' : '#ef4444');
        const diffBg = q.difficulty === 'EASY' ? '#ecfdf5' : (q.difficulty === 'MEDIUM' ? '#fffbeb' : '#fef2f2');

        const item = document.createElement('div');
        item.className = 'chapter-item';
        item.id = `question-card-${q.questionId}`;
        item.style.cssText = 'flex-direction: column; gap: 10px; align-items: flex-start;';
        item.innerHTML = `
            <div style="display: flex; justify-content: space-between; width: 100%; align-items: flex-start; gap: 1rem;">
                <div style="flex-grow: 1;">
                    <div style="display: flex; gap: 8px; margin-bottom: 8px; flex-wrap: wrap;">
                        <span class="badge" style="background:#ede9fe; color:#5b21b6;"><i class="fas fa-folder"></i> ${q.categoryName || 'General'}</span>
                        <span class="badge" style="background:#e0f2fe; color:#0369a1;"><i class="fas fa-tag"></i> ${q.topicName || 'General'}</span>
                        <span class="badge" style="background:${diffBg}; color:${diffColor};">${q.difficulty || 'MEDIUM'}</span>
                        <span class="badge" style="background:#f1f5f9; color:#64748b;"><i class="fas fa-star"></i> ${q.marks || 1} mark</span>
                    </div>
                    <p style="margin: 0; color: #1e293b; font-size: 0.98rem; line-height: 1.6;">${q.questionText}</p>
                </div>
                <button onclick="removeBookmark(${q.questionId})" 
                    style="background:none; border:1px solid #fca5a5; color:#ef4444; border-radius:8px; padding:6px 12px; cursor:pointer; white-space:nowrap; font-size:0.85rem; transition: all 0.2s;"
                    onmouseover="this.style.background='#fef2f2'" onmouseout="this.style.background='none'">
                    <i class="fas fa-trash-alt"></i> Remove
                </button>
            </div>
        `;
        container.appendChild(item);
    });
}

async function removeBookmark(questionId) {
    if (confirm('Remove this question from your saved list?')) {
        try {
            await api.delete(`/student/bookmarks/${questionId}`);
            allSavedQuestions = allSavedQuestions.filter(q => q.questionId !== questionId);
            populateFilters(allSavedQuestions);
            renderQuestions();
        } catch (error) {
            alert('Failed to remove bookmark: ' + error.message);
        }
    }
}
