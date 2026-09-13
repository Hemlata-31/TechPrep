document.addEventListener('DOMContentLoaded', () => {
    requireStudent();
    loadSavedQuestions();

    // Set up filter event listeners
    document.getElementById('categoryFilter').addEventListener('change', renderQuestions);
    document.getElementById('topicFilter').addEventListener('change', renderQuestions);
    document.getElementById('difficultyFilter').addEventListener('change', renderQuestions);
});

let allSavedQuestions = [];

async function loadSavedQuestions() {
    const container = document.getElementById('savedQuestionsContainer');
    try {
        const questions = await apiCall('/api/student/bookmarks');
        allSavedQuestions = questions;
        populateFilters(questions);
        renderQuestions();
    } catch (error) {
        container.innerHTML = '<div class="alert alert-danger">Failed to load saved questions.</div>';
    }
}

function populateFilters(questions) {
    const categoryFilter = document.getElementById('categoryFilter');
    const topicFilter = document.getElementById('topicFilter');
    
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

    if (categoryFilter) {
        filtered = filtered.filter(q => q.categoryName === categoryFilter);
    }
    if (topicFilter) {
        filtered = filtered.filter(q => q.topicName === topicFilter);
    }
    if (difficultyFilter) {
        filtered = filtered.filter(q => q.difficulty === difficultyFilter);
    }

    if (filtered.length === 0) {
        container.innerHTML = '<div class="alert alert-info">No saved questions found matching your criteria.</div>';
        return;
    }

    let html = '';
    filtered.forEach(q => {
        let diffColor = q.difficulty === 'EASY' ? '#10b981' : (q.difficulty === 'MEDIUM' ? '#f59e0b' : '#ef4444');
        
        html += `
            <div class="chapter-item" style="display: flex; flex-direction: column; gap: 10px;" id="question-card-${q.questionId}">
                <div style="display: flex; justify-content: space-between; width: 100%;">
                    <div style="flex-grow: 1;">
                        <div style="display: flex; gap: 10px; margin-bottom: 5px;">
                            <span class="badge" style="background-color: #e0e7ff; color: #4338ca;">${q.categoryName}</span>
                            <span class="badge" style="background-color: #f3f4f6; color: #4b5563;">${q.topicName}</span>
                            <span class="badge" style="background-color: ${diffColor}; color: white;">${q.difficulty}</span>
                        </div>
                        <h4 style="margin: 10px 0;">${q.questionText}</h4>
                    </div>
                    <div>
                        <button class="btn btn-outline" style="color: #ef4444; border-color: #ef4444;" onclick="removeBookmark(${q.questionId})">
                            <i class="fas fa-bookmark"></i> Remove
                        </button>
                    </div>
                </div>
            </div>
        `;
    });

    container.innerHTML = html;
}

async function removeBookmark(questionId) {
    if (confirm('Are you sure you want to remove this bookmark?')) {
        try {
            await apiCall(`/api/student/bookmarks/${questionId}`, 'DELETE');
            allSavedQuestions = allSavedQuestions.filter(q => q.questionId !== questionId);
            renderQuestions();
        } catch (error) {
            alert('Failed to remove bookmark.');
        }
    }
}
