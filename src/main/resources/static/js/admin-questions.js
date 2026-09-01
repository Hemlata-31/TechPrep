document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role === 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();
    loadCategories();
    
    document.getElementById('catSelect').addEventListener('change', handleCategoryChange);
    document.getElementById('subSelect').addEventListener('change', handleSubCategoryChange);
    document.getElementById('topicSelect').addEventListener('change', handleTopicChange);
    
    document.getElementById('qForm').addEventListener('submit', handleFormSubmit);
});

async function loadCategories() {
    try {
        const categories = await api.get('/categories');
        const select = document.getElementById('catSelect');
        categories.forEach(cat => {
            const opt = document.createElement('option');
            opt.value = cat.id;
            opt.textContent = cat.name;
            select.appendChild(opt);
        });
    } catch (e) {
        console.error(e);
    }
}

async function handleCategoryChange(e) {
    const catId = e.target.value;
    const subSelect = document.getElementById('subSelect');
    const topicSelect = document.getElementById('topicSelect');
    
    subSelect.innerHTML = '<option value="">Select Subject...</option>';
    topicSelect.innerHTML = '<option value="">Select Topic...</option>';
    topicSelect.disabled = true;
    document.getElementById('btnAddQuestion').style.display = 'none';
    document.getElementById('questionsContainer').innerHTML = '<div class="card text-center"><p>Please select a Topic to view or add questions.</p></div>';
    
    if (!catId) {
        subSelect.disabled = true;
        return;
    }
    
    try {
        const subs = await api.get(`/categories/${catId}/subcategories`);
        subs.forEach(sub => {
            const opt = document.createElement('option');
            opt.value = sub.id;
            opt.textContent = sub.name;
            subSelect.appendChild(opt);
        });
        subSelect.disabled = false;
    } catch (err) {
        console.error(err);
    }
}

async function handleSubCategoryChange(e) {
    const subId = e.target.value;
    const topicSelect = document.getElementById('topicSelect');
    
    topicSelect.innerHTML = '<option value="">Select Topic...</option>';
    document.getElementById('btnAddQuestion').style.display = 'none';
    document.getElementById('questionsContainer').innerHTML = '<div class="card text-center"><p>Please select a Topic to view or add questions.</p></div>';
    
    if (!subId) {
        topicSelect.disabled = true;
        return;
    }
    
    try {
        const topics = await api.get(`/subcategories/${subId}/topics`);
        topics.forEach(topic => {
            const opt = document.createElement('option');
            opt.value = topic.id;
            opt.textContent = topic.name;
            topicSelect.appendChild(opt);
        });
        topicSelect.disabled = false;
    } catch (err) {
        console.error(err);
    }
}

async function handleTopicChange(e) {
    const topicId = e.target.value;
    if (!topicId) {
        document.getElementById('btnAddQuestion').style.display = 'none';
        document.getElementById('questionsContainer').innerHTML = '<div class="card text-center"><p>Please select a Topic to view or add questions.</p></div>';
        return;
    }
    
    document.getElementById('btnAddQuestion').style.display = 'inline-block';
    loadQuestions(topicId);
}

let currentQuestions = [];

async function loadQuestions(topicId) {
    const container = document.getElementById('questionsContainer');
    container.innerHTML = '<p>Loading questions...</p>';
    
    try {
        // Fetch questions, backend should return a page object
        const response = await api.get(`/topics/${topicId}/questions?size=100`); 
        const questions = response.content || [];
        currentQuestions = questions;
        
        if (questions.length === 0) {
            container.innerHTML = '<div class="card text-center"><p>No questions found for this topic. Click "+ Add Question" to create one.</p></div>';
            return;
        }
        
        let html = '';
        questions.forEach((q, index) => {
            const badgeClass = q.difficulty === 'EASY' ? 'badge-easy' : (q.difficulty === 'MEDIUM' ? 'badge-medium' : 'badge-hard');
            
            html += `
            <div class="question-item">
                <div class="question-header">
                    <div class="question-text">Q${index+1}. ${q.questionText}</div>
                    <div>
                        <button class="btn-outline btn-small" onclick="editQuestion(${q.id})">Edit</button>
                        <button class="btn-danger btn-small" onclick="deleteQuestion(${q.id})" style="color:white; margin-left:5px; padding: 0.5rem 1rem; font-size: 0.9rem; border-radius: 6px; border:none; cursor:pointer;">Delete</button>
                    </div>
                </div>
                <div class="question-meta" style="margin-bottom:1rem;">
                    <span class="badge ${badgeClass}">${q.difficulty}</span>
                    <span class="badge" style="background:#e2e8f0; color:#475569;">${q.marks} Marks</span>
                    ${!q.active ? '<span class="badge" style="background:#fee2e2; color:#991b1b;">INACTIVE</span>' : ''}
                </div>
                
                <ul class="options-list">
                    <li class="${q.correctAnswer === 'A' ? 'correct' : ''}"><strong>A.</strong> ${q.optionA}</li>
                    <li class="${q.correctAnswer === 'B' ? 'correct' : ''}"><strong>B.</strong> ${q.optionB}</li>
                    <li class="${q.correctAnswer === 'C' ? 'correct' : ''}"><strong>C.</strong> ${q.optionC}</li>
                    <li class="${q.correctAnswer === 'D' ? 'correct' : ''}"><strong>D.</strong> ${q.optionD}</li>
                </ul>
                
                ${q.explanation ? `
                <div class="explanation">
                    <strong>Explanation:</strong> ${q.explanation}
                </div>` : ''}
            </div>
            `;
        });
        
        container.innerHTML = html;
        
    } catch (err) {
        console.error(err);
        container.innerHTML = '<div class="alert" style="display:block">Failed to load questions.</div>';
    }
}

const modal = document.getElementById('qModal');
function openModal() {
    document.getElementById('qForm').reset();
    document.getElementById('qId').value = '';
    document.getElementById('modalTitle').textContent = 'Add Question';
    modal.style.display = 'flex';
}

function closeModal() {
    modal.style.display = 'none';
}

function editQuestion(id) {
    const q = currentQuestions.find(q => q.id === id);
    if (!q) return;
    
    document.getElementById('qId').value = q.id;
    document.getElementById('qText').value = q.questionText;
    document.getElementById('optA').value = q.optionA;
    document.getElementById('optB').value = q.optionB;
    document.getElementById('optC').value = q.optionC;
    document.getElementById('optD').value = q.optionD;
    document.getElementById('correctAns').value = q.correctAnswer;
    document.getElementById('qDiff').value = q.difficulty;
    document.getElementById('qMarks').value = q.marks;
    document.getElementById('qExp').value = q.explanation || '';
    document.getElementById('qActive').value = q.active ? 'true' : 'false';
    
    document.getElementById('modalTitle').textContent = 'Edit Question';
    modal.style.display = 'flex';
}

async function handleFormSubmit(e) {
    e.preventDefault();
    const id = document.getElementById('qId').value;
    const topicId = document.getElementById('topicSelect').value;
    
    const payload = {
        topicId: parseInt(topicId),
        questionText: document.getElementById('qText').value,
        optionA: document.getElementById('optA').value,
        optionB: document.getElementById('optB').value,
        optionC: document.getElementById('optC').value,
        optionD: document.getElementById('optD').value,
        correctAnswer: document.getElementById('correctAns').value,
        difficulty: document.getElementById('qDiff').value,
        marks: parseInt(document.getElementById('qMarks').value),
        explanation: document.getElementById('qExp').value,
        active: document.getElementById('qActive').value === 'true'
    };
    
    const btn = document.getElementById('saveBtn');
    btn.disabled = true;
    
    try {
        if (id) {
            await api.put(`/questions/${id}`, payload);
        } else {
            await api.post(`/questions`, payload);
        }
        closeModal();
        loadQuestions(topicId);
    } catch (err) {
        alert(err.message);
    } finally {
        btn.disabled = false;
    }
}

async function deleteQuestion(id) {
    if (!confirm('Are you sure you want to delete this question?')) return;
    
    const topicId = document.getElementById('topicSelect').value;
    try {
        await api.delete(`/questions/${id}`);
        loadQuestions(topicId);
    } catch (err) {
        alert(err.message);
    }
}
