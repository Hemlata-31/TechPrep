document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'ADMIN' && user.role !== 'INSTRUCTOR') {
        window.location.href = '/login.html';
        return;
    }

    prefillCodeTemplates();
    addTestCaseRow(true);
    addTestCaseRow(false);

    document.getElementById('dsaTitle').addEventListener('input', (e) => {
        const slug = e.target.value
            .replace(/^\d+\.\s*/, '')
            .toLowerCase()
            .replace(/[^a-z0-9]+/g, '-')
            .replace(/^-|-$/g, '');
        document.getElementById('dsaSlug').value = slug;
    });

    document.getElementById('uploadPdfForm').addEventListener('submit', handlePdfUpload);
    document.getElementById('uploadDsaForm').addEventListener('submit', handleDsaUpload);

    await loadPublishedNotes();
    await loadPublishedProblems();
});

function switchUploadTab(tab) {
    const isPdf = tab === 'pdf';
    document.getElementById('pdfPane').style.display = isPdf ? 'block' : 'none';
    document.getElementById('dsaPane').style.display = isPdf ? 'none' : 'block';
    document.getElementById('tabPdfBtn').classList.toggle('active', isPdf);
    document.getElementById('tabDsaBtn').classList.toggle('active', !isPdf);
}

function showMsg(id, text, ok) {
    const el = document.getElementById(id);
    el.textContent = text;
    el.className = `status-msg ${ok ? 'status-ok' : 'status-err'}`;
    el.style.display = 'block';
}

async function handlePdfUpload(e) {
    e.preventDefault();
    const fileInput = document.getElementById('pdfFile');
    if (!fileInput.files[0]) {
        showMsg('pdfAlert', 'Please choose a PDF file.', false);
        return;
    }

    const fd = new FormData();
    fd.append('title', document.getElementById('pdfTitle').value.trim());
    fd.append('category', document.getElementById('pdfCategory').value);
    fd.append('description', document.getElementById('pdfDesc').value.trim());
    fd.append('highlights', document.getElementById('pdfHighlights').value.trim());
    fd.append('file', fileInput.files[0]);

    try {
        const token = localStorage.getItem('token');
        const resp = await fetch('http://localhost:8080/api/admin/study-notes', {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${token}` },
            body: fd
        });
        if (!resp.ok) throw new Error(await resp.text() || 'Upload failed');
        showMsg('pdfAlert', 'Study PDF uploaded successfully!', true);
        e.target.reset();
        await loadPublishedNotes();
    } catch (err) {
        showMsg('pdfAlert', 'Error: ' + err.message, false);
    }
}

async function loadPublishedNotes() {
    const container = document.getElementById('publishedNotesList');
    try {
        const notes = await api.get('/study-notes');
        container.innerHTML = notes.map(n => `
            <div style="display:flex; justify-content:space-between; align-items:center; padding:0.75rem 0; border-bottom:1px solid var(--border);">
                <div>
                    <strong>${escapeHtml(n.title)}</strong>
                    <span class="badge" style="margin-left:8px;">${escapeHtml(n.category)}</span>
                    <div style="font-size:0.82rem; color:var(--text-secondary);">${escapeHtml(n.fileName || '')}</div>
                </div>
            </div>
        `).join('');
    } catch (err) {
        container.innerHTML = '<p style="color:#ef4444;">Failed to load study notes.</p>';
    }
}

let tcCounter = 0;
function addTestCaseRow(isSample = false) {
    tcCounter++;
    const wrap = document.getElementById('testCasesContainer');
    const div = document.createElement('div');
    div.className = 'tc-row';
    div.innerHTML = `
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:0.6rem;">
            <strong>Test Case #${tcCounter}</strong>
            <div style="display:flex; align-items:center; gap:12px;">
                <label style="font-size:0.85rem; cursor:pointer;">
                    <input type="checkbox" class="tc-sample" ${isSample ? 'checked' : ''}> Sample (Visible to Student)
                </label>
                <button type="button" class="btn btn-danger btn-small" onclick="this.closest('.tc-row').remove()">Remove</button>
            </div>
        </div>
        <div class="two-col">
            <div class="form-group">
                <label>Stdin Input *</label>
                <textarea class="tc-input code-box" rows="2" required placeholder="e.g., 5"></textarea>
            </div>
            <div class="form-group">
                <label>Expected Output *</label>
                <textarea class="tc-output code-box" rows="2" required placeholder="e.g., 120"></textarea>
            </div>
        </div>
        <div class="two-col">
            <div class="form-group">
                <label>Display Input (optional, for Samples)</label>
                <input type="text" class="tc-display" placeholder="e.g., n = 5">
            </div>
            <div class="form-group">
                <label>Explanation (optional)</label>
                <input type="text" class="tc-expl" placeholder="e.g., 5! = 120">
            </div>
        </div>
    `;
    wrap.appendChild(div);
}

async function handleDsaUpload(e) {
    e.preventDefault();
    const rows = document.querySelectorAll('#testCasesContainer .tc-row');
    if (rows.length === 0) {
        showMsg('dsaAlert', 'Add at least one test case.', false);
        return;
    }

    const testCases = Array.from(rows).map(r => ({
        input: r.querySelector('.tc-input').value,
        expectedOutput: r.querySelector('.tc-output').value.trim(),
        displayInput: r.querySelector('.tc-display').value.trim() || null,
        explanation: r.querySelector('.tc-expl').value.trim() || null,
        sample: r.querySelector('.tc-sample').checked
    }));

    const payload = {
        title: document.getElementById('dsaTitle').value.trim(),
        slug: document.getElementById('dsaSlug').value.trim(),
        difficulty: document.getElementById('dsaDifficulty').value,
        categoryTag: document.getElementById('dsaTag').value.trim(),
        description: document.getElementById('dsaDesc').value.trim(),
        constraintsText: document.getElementById('dsaConstraints').value.trim(),
        starterCodeJava: document.getElementById('starterJava').value,
        driverCodeJava: document.getElementById('driverJava').value,
        starterCodePython: document.getElementById('starterPython').value,
        driverCodePython: document.getElementById('driverPython').value,
        starterCodeCpp: document.getElementById('starterCpp').value,
        driverCodeCpp: document.getElementById('driverCpp').value,
        testCases
    };

    try {
        await api.post('/admin/coding-problems', payload);
        showMsg('dsaAlert', 'Coding problem published to DSA Coding Arena!', true);
        e.target.reset();
        prefillCodeTemplates();
        await loadPublishedProblems();
    } catch (err) {
        showMsg('dsaAlert', 'Error: ' + err.message, false);
    }
}

async function loadPublishedProblems() {
    const container = document.getElementById('publishedProblemsList');
    try {
        const list = await api.get('/coding/problems');
        container.innerHTML = list.map(p => `
            <div style="display:flex; justify-content:space-between; align-items:center; padding:0.7rem 0; border-bottom:1px solid var(--border);">
                <div>
                    <strong>${escapeHtml(p.title)}</strong>
                    <span class="badge" style="margin-left:8px;">${escapeHtml(p.categoryTag)}</span>
                </div>
                <span class="badge">${p.difficulty}</span>
            </div>
        `).join('');
    } catch (err) {
        container.innerHTML = '<p style="color:#ef4444;">Failed to load coding problems.</p>';
    }
}

function prefillCodeTemplates() {
    document.getElementById('starterJava').value = `class Solution {\n    public int solve(int n) {\n        // Write your code here\n        return 0;\n    }\n}`;
    document.getElementById('driverJava').value = `import java.util.*;\n// {{SOLUTION}}\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int n = sc.nextInt();\n        System.out.println(new Solution().solve(n));\n    }\n}`;
    document.getElementById('starterPython').value = `class Solution:\n    def solve(self, n: int) -> int:\n        # Write your code here\n        pass`;
    document.getElementById('driverPython').value = `import sys\n# {{SOLUTION}}\nif __name__ == "__main__":\n    n = int(sys.stdin.read().strip())\n    print(Solution().solve(n))`;
    document.getElementById('starterCpp').value = `class Solution {\npublic:\n    int solve(int n) {\n        // Write your code here\n        return 0;\n    }\n};`;
    document.getElementById('driverCpp').value = `#include <bits/stdc++.h>\nusing namespace std;\n// {{SOLUTION}}\nint main() {\n    int n; cin >> n;\n    cout << Solution().solve(n) << endl;\n    return 0;\n}`;
}

function escapeHtml(str) {
    return String(str ?? '').replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]));
}
