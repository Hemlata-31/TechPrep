document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'ADMIN' && user.role !== 'INSTRUCTOR') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    await loadCategoriesDropdown();
    await loadAdminTests();

    document.getElementById('createTestForm').addEventListener('submit', handleCreateTest);
});

async function loadCategoriesDropdown() {
    const catSelect = document.getElementById('testCategory');
    try {
        const categories = await api.get('/categories');
        catSelect.innerHTML = '<option value="">Select Category</option>';
        categories.forEach(cat => {
            const opt = document.createElement('option');
            opt.value = cat.id;
            opt.textContent = cat.name;
            catSelect.appendChild(opt);
        });
    } catch (err) {
        console.error("Failed to load categories:", err);
    }
}

async function loadAdminTests() {
    const container = document.getElementById('testsTableContainer');
    try {
        const tests = await api.get('/tests');
        if (!tests || tests.length === 0) {
            container.innerHTML = '<p style="color: #64748b;">No mock tests created yet.</p>';
            return;
        }

        let html = `
            <table style="width:100%; border-collapse: collapse; text-align: left;">
                <thead>
                    <tr style="border-bottom: 2px solid #e2e8f0; color: #475569;">
                        <th style="padding: 0.75rem;">Title</th>
                        <th style="padding: 0.75rem;">Category</th>
                        <th style="padding: 0.75rem;">Duration</th>
                        <th style="padding: 0.75rem;">Questions</th>
                        <th style="padding: 0.75rem;">Status</th>
                        <th style="padding: 0.75rem;">Actions</th>
                    </tr>
                </thead>
                <tbody>
        `;

        tests.forEach(t => {
            const statusBadge = t.active 
                ? '<span class="badge badge-success">Active</span>' 
                : '<span class="badge badge-danger">Inactive</span>';

            html += `
                <tr style="border-bottom: 1px solid #f1f5f9;">
                    <td style="padding: 0.75rem;"><strong>${t.title}</strong></td>
                    <td style="padding: 0.75rem;">${t.categoryName || 'All'}</td>
                    <td style="padding: 0.75rem;">${t.durationMinutes} mins</td>
                    <td style="padding: 0.75rem;">${t.totalQuestions}</td>
                    <td style="padding: 0.75rem;">${statusBadge}</td>
                    <td style="padding: 0.75rem;">
                        <button onclick="toggleActive(${t.id})" class="btn btn-outline btn-small" style="margin-right: 0.5rem;">
                            ${t.active ? 'Deactivate' : 'Activate'}
                        </button>
                        <button onclick="deleteTest(${t.id})" class="btn btn-small" style="background:#ef4444;">Delete</button>
                    </td>
                </tr>
            `;
        });

        html += '</tbody></table>';
        container.innerHTML = html;

    } catch (err) {
        console.error(err);
        container.innerHTML = `<div class="alert" style="display:block">Failed to load tests: ${err.message}</div>`;
    }
}

async function handleCreateTest(e) {
    e.preventDefault();
    const title = document.getElementById('testTitle').value.trim();
    const categoryId = document.getElementById('testCategory').value;
    const durationMinutes = parseInt(document.getElementById('testDuration').value);
    const totalQuestions = parseInt(document.getElementById('testQuestionsCount').value);
    const selectedDifficulty = document.getElementById('testDifficulty').value;
    const description = document.getElementById('testDesc').value.trim();

    try {
        await api.post('/tests', {
            title,
            categoryId: categoryId ? parseInt(categoryId) : null,
            durationMinutes,
            totalQuestions,
            selectedDifficulty,
            description,
            active: true
        });

        alert("Mock Test created successfully!");
        document.getElementById('createTestForm').reset();
        await loadAdminTests();
    } catch (err) {
        alert("Failed to create test: " + err.message);
    }
}

async function toggleActive(testId) {
    try {
        await api.patch(`/tests/${testId}/toggle-active`);
        await loadAdminTests();
    } catch (err) {
        alert("Failed to toggle status: " + err.message);
    }
}

async function deleteTest(testId) {
    if (!confirm("Are you sure you want to delete this mock test?")) return;
    try {
        await api.delete(`/tests/${testId}`);
        await loadAdminTests();
    } catch (err) {
        alert("Failed to delete test: " + err.message);
    }
}
