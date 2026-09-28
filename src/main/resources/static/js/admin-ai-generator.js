document.addEventListener('DOMContentLoaded', async () => {
    // Auth guard
    const user = await checkAuth();
    if (user && user.role === 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    // Dropdowns
    const categorySelect    = document.getElementById('categorySelect');
    const subCategorySelect = document.getElementById('subCategorySelect');
    const topicSelect       = document.getElementById('topicSelect');

    // Controls
    const generateBtn      = document.getElementById('generateBtn');
    const genAlert         = document.getElementById('genAlert');
    const generatingLoader = document.getElementById('generatingLoader');
    const loaderNote       = document.getElementById('loaderNote');
    const resultCard       = document.getElementById('resultCard');
    const savedCountEl     = document.getElementById('savedCount');
    const resultNote       = document.getElementById('resultNote');

    // ==========================================
    // Init
    // ==========================================
    loadCategories();

    // ==========================================
    // Cascade dropdowns
    // ==========================================
    categorySelect.addEventListener('change', () => {
        const catId = categorySelect.value;
        subCategorySelect.innerHTML = '<option value="">-- Select SubCategory --</option>';
        topicSelect.innerHTML = '<option value="">-- Select Topic --</option>';
        topicSelect.disabled = true;
        resultCard.style.display = 'none';

        if (catId) {
            subCategorySelect.disabled = false;
            loadSubCategories(catId);
        } else {
            subCategorySelect.disabled = true;
        }
    });

    subCategorySelect.addEventListener('change', () => {
        const subId = subCategorySelect.value;
        topicSelect.innerHTML = '<option value="">-- Select Topic --</option>';
        resultCard.style.display = 'none';

        if (subId) {
            topicSelect.disabled = false;
            loadTopics(subId);
        } else {
            topicSelect.disabled = true;
        }
    });

    generateBtn.addEventListener('click', generateQuestions);

    // ==========================================
    // Load dropdown data – uses existing public APIs
    // ==========================================

    async function loadCategories() {
        try {
            // Reuses the existing /api/categories endpoint (no admin prefix)
            const categories = await api.get('/categories');
            categories.forEach(cat => {
                const option = document.createElement('option');
                option.value = cat.id;
                option.textContent = cat.name;
                categorySelect.appendChild(option);
            });
        } catch (error) {
            showAlert('Failed to load categories. Please refresh the page.', 'error');
        }
    }

    async function loadSubCategories(categoryId) {
        try {
            // Reuses /api/categories/{id}/subcategories
            const subCategories = await api.get(`/categories/${categoryId}/subcategories`);
            subCategories.forEach(sub => {
                const option = document.createElement('option');
                option.value = sub.id;
                option.textContent = sub.name;
                subCategorySelect.appendChild(option);
            });
        } catch (error) {
            showAlert('Failed to load subcategories.', 'error');
        }
    }

    async function loadTopics(subCategoryId) {
        try {
            // Reuses /api/subcategories/{id}/topics
            const topics = await api.get(`/subcategories/${subCategoryId}/topics`);
            topics.forEach(top => {
                const option = document.createElement('option');
                option.value = top.id;
                option.textContent = top.name;
                topicSelect.appendChild(option);
            });
        } catch (error) {
            showAlert('Failed to load topics.', 'error');
        }
    }

    // ==========================================
    // Generate & Save
    // ==========================================

    async function generateQuestions() {
        const topicId         = topicSelect.value;
        const difficulty      = document.getElementById('difficultySelect').value;
        const numberOfQuestions = parseInt(document.getElementById('numQuestions').value, 10);

        if (!topicId) {
            showAlert('Please select a Topic first.', 'error');
            return;
        }

        genAlert.style.display = 'none';
        resultCard.style.display = 'none';
        generateBtn.disabled = true;

        // Estimate time for loader message
        if (numberOfQuestions > 10) {
            loaderNote.textContent = `Generating ${numberOfQuestions} questions in batches — this may take ${Math.ceil(numberOfQuestions / 10) * 15}–${Math.ceil(numberOfQuestions / 10) * 25} seconds`;
        } else {
            loaderNote.textContent = 'This may take 10–20 seconds';
        }
        generatingLoader.style.display = 'block';

        try {
            const response = await api.post('/ai/generate', {
                topicId: parseInt(topicId, 10),
                difficulty: difficulty,
                numberOfQuestions: numberOfQuestions
            });

            generatingLoader.style.display = 'none';
            generateBtn.disabled = false;

            const saved = response.savedCount ?? 0;

            if (saved > 0) {
                savedCountEl.textContent = saved;
                resultNote.textContent = response.message + ' Students can practice them immediately.';
                resultCard.style.display = 'block';
                showAlert(`✅ ${saved} question(s) added to the question bank.`, 'success');
            } else {
                showAlert('⚠️ No questions were saved — all may have been duplicates or the AI response was invalid. Try again.', 'error');
            }
        } catch (error) {
            generatingLoader.style.display = 'none';
            generateBtn.disabled = false;
            showAlert('❌ ' + (error.message || 'Failed to generate questions. Check your Gemini API key.'), 'error');
        }
    }

    // ==========================================
    // Helpers
    // ==========================================

    function showAlert(msg, type) {
        genAlert.textContent = msg;
        genAlert.className = `alert alert-${type === 'error' ? 'danger' : 'success'}`;
        genAlert.style.display = 'block';
        if (type === 'success') {
            setTimeout(() => genAlert.style.display = 'none', 6000);
        }
    }
});
