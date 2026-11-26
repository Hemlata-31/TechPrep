let problemData = null;
let editor = null;
let currentLang = 'JAVA';

const MONACO_LANG = {
    JAVA: 'java',
    PYTHON: 'python',
    CPP: 'cpp'
};

document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }

    const params = new URLSearchParams(window.location.search);
    const slug = params.get('slug');
    if (!slug) {
        window.location.href = '/student/coding.html';
        return;
    }

    await loadProblem(slug);
    initMonaco();

    document.getElementById('languageSelect').addEventListener('change', (e) => {
        currentLang = e.target.value;
        updateEditorLanguageAndCode();
    });

    document.getElementById('resetCodeBtn').addEventListener('click', () => {
        if (confirm('Reset editor to starter code?')) {
            updateEditorLanguageAndCode();
        }
    });

    document.getElementById('runCodeBtn').addEventListener('click', () => executeCode(false));
    document.getElementById('submitCodeBtn').addEventListener('click', () => executeCode(true));
});

async function loadProblem(slug) {
    try {
        problemData = await api.get(`/coding/problems/${encodeURIComponent(slug)}`);
        document.getElementById('topbarTitle').textContent = problemData.title;
        document.getElementById('problemTitle').textContent = problemData.title;

        const diffEl = document.getElementById('problemDiff');
        diffEl.textContent = problemData.difficulty;
        diffEl.className = `diff-badge diff-${problemData.difficulty}`;

        document.getElementById('problemTag').textContent = problemData.categoryTag;
        if (problemData.solved) {
            document.getElementById('solvedBadge').style.display = 'inline';
        }

        document.getElementById('problemDescription').textContent = problemData.description;

        if (problemData.constraintsText) {
            document.getElementById('constraintsBox').style.display = 'block';
            document.getElementById('constraintsText').textContent = problemData.constraintsText;
        }

        const samplesDiv = document.getElementById('sampleCasesContainer');
        samplesDiv.innerHTML = problemData.sampleTestCases.map((tc, idx) => `
            <div class="sample-box">
                <strong>Example ${idx + 1}:</strong>
                <div style="margin-top:6px;"><strong>Input:</strong><pre>${escapeHtml(tc.displayInput)}</pre></div>
                <div style="margin-top:6px;"><strong>Output:</strong><pre>${escapeHtml(tc.expectedOutput)}</pre></div>
                ${tc.explanation ? `<div style="margin-top:6px; color:#475569;"><strong>Explanation:</strong> ${escapeHtml(tc.explanation)}</div>` : ''}
            </div>
        `).join('');

    } catch (err) {
        console.error(err);
        alert('Failed to load problem.');
    }
}

function initMonaco() {
    require.config({ paths: { vs: 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.44.0/min/vs' } });
    require(['vs/editor/editor.main'], function () {
        editor = monaco.editor.create(document.getElementById('monacoContainer'), {
            value: getStarterCode('JAVA'),
            language: 'java',
            theme: 'vs-dark',
            automaticLayout: true,
            fontSize: 14,
            minimap: { enabled: false },
            scrollBeyondLastLine: false
        });
    });
}

function getStarterCode(lang) {
    if (!problemData) return '';
    if (lang === 'JAVA') return problemData.starterCodeJava;
    if (lang === 'PYTHON') return problemData.starterCodePython;
    return problemData.starterCodeCpp;
}

function updateEditorLanguageAndCode() {
    if (!editor) return;
    monaco.editor.setModelLanguage(editor.getModel(), MONACO_LANG[currentLang]);
    editor.setValue(getStarterCode(currentLang));
}

async function executeCode(isSubmit) {
    if (!problemData || !editor) return;

    const runBtn = document.getElementById('runCodeBtn');
    const subBtn = document.getElementById('submitCodeBtn');
    const consoleEl = document.getElementById('consolePanel');

    runBtn.disabled = true;
    subBtn.disabled = true;
    consoleEl.innerHTML = `<div style="color:#2563eb;"><i class="fas fa-spinner fa-spin"></i> ${isSubmit ? 'Submitting & running all test cases...' : 'Running sample test cases...'}</div>`;

    try {
        const endpoint = isSubmit
            ? `/coding/problems/${problemData.id}/submit`
            : `/coding/problems/${problemData.id}/run`;

        const res = await api.post(endpoint, {
            language: currentLang,
            sourceCode: editor.getValue()
        });

        renderExecutionResult(res, isSubmit);

        if (isSubmit && res.status === 'ACCEPTED') {
            document.getElementById('solvedBadge').style.display = 'inline';
            if (typeof confetti === 'function') {
                confetti({ particleCount: 90, spread: 70, origin: { y: 0.6 } });
            }
        }
    } catch (err) {
        consoleEl.innerHTML = `<div class="verdict-API_ERROR"><i class="fas fa-exclamation-triangle"></i> Error: ${escapeHtml(err.message || 'Execution failed')}</div>`;
    } finally {
        runBtn.disabled = false;
        subBtn.disabled = false;
    }
}

function renderExecutionResult(res, isSubmit) {
    const consoleEl = document.getElementById('consolePanel');
    const labelMap = {
        ACCEPTED: isSubmit ? 'Accepted!' : 'Sample Tests Passed!',
        WRONG_ANSWER: 'Wrong Answer',
        COMPILATION_ERROR: 'Compilation Error',
        RUNTIME_ERROR: 'Runtime Error',
        API_ERROR: 'Runner Error'
    };

    let html = `
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:8px;">
            <span class="verdict-${res.status}">${labelMap[res.status] || res.status}</span>
            <span style="font-size:0.85rem; color:#475569; font-weight:600;">${res.passedCount} / ${res.totalCount} test cases passed</span>
        </div>
    `;

    if (res.errorMessage) {
        html += `<pre style="background:#fef2f2; color:#991b1b; padding:0.75rem; border-radius:6px; font-size:0.82rem; white-space:pre-wrap; margin-bottom:8px;">${escapeHtml(res.errorMessage)}</pre>`;
    }

    if (res.results && res.results.length > 0) {
        html += `<div>` + res.results.map(tc => `
            <span class="tc-pill ${tc.passed ? 'tc-pass' : 'tc-fail'}">
                <i class="fas ${tc.passed ? 'fa-check' : 'fa-times'}"></i> Case ${tc.testCaseNumber}
            </span>
        `).join('') + `</div>`;

        const firstFail = res.results.find(r => !r.passed && r.sample);
        const preview = firstFail || res.results[0];
        if (preview && preview.sample) {
            html += `
                <div class="sample-box" style="margin-top:8px;">
                    <div><strong>Input:</strong> <pre>${escapeHtml(preview.input)}</pre></div>
                    <div style="margin-top:4px;"><strong>Expected:</strong> <pre>${escapeHtml(preview.expectedOutput)}</pre></div>
                    <div style="margin-top:4px;"><strong>Your Output:</strong> <pre>${escapeHtml(preview.actualOutput || '(empty)')}</pre></div>
                </div>
            `;
        }
    }

    consoleEl.innerHTML = html;
}

function switchLeftTab(tab) {
    const isDesc = tab === 'desc';
    document.getElementById('descPane').style.display = isDesc ? 'block' : 'none';
    document.getElementById('subsPane').style.display = isDesc ? 'none' : 'block';
    document.getElementById('tabDescBtn').classList.toggle('active', isDesc);
    document.getElementById('tabSubBtn').classList.toggle('active', !isDesc);
    if (!isDesc) loadSubmissions();
}

async function loadSubmissions() {
    if (!problemData) return;
    const container = document.getElementById('submissionsList');
    try {
        const subs = await api.get(`/coding/problems/${problemData.id}/submissions`);
        if (subs.length === 0) {
            container.innerHTML = '<p>No submissions yet for this problem.</p>';
            return;
        }
        container.innerHTML = subs.map(s => `
            <div style="border:1px solid #e2e8f0; border-radius:8px; padding:0.75rem; margin-bottom:0.65rem;">
                <div style="display:flex; justify-content:space-between;">
                    <span class="verdict-${s.status}" style="font-size:0.9rem;">${s.status}</span>
                    <span style="font-size:0.8rem; color:#64748b;">${s.language}</span>
                </div>
                <div style="font-size:0.82rem; color:#475569; margin-top:4px;">
                    Passed ${s.passedTestCases} / ${s.totalTestCases} · ${new Date(s.submittedAt).toLocaleString()}
                </div>
            </div>
        `).join('');
    } catch (err) {
        container.innerHTML = '<p style="color:#ef4444;">Failed to load submissions.</p>';
    }
}

function escapeHtml(str) {
    return String(str ?? '').replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]));
}

