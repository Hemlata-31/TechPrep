let allProblems = [];

document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    await loadProblems();

    document.getElementById('searchInput').addEventListener('input', renderProblems);
    document.getElementById('difficultyFilter').addEventListener('change', renderProblems);
    document.getElementById('tagFilter').addEventListener('change', renderProblems);
});

async function loadProblems() {
    try {
        allProblems = await api.get('/coding/problems');
        populateTags();
        updateStats();
        renderProblems();
    } catch (err) {
        console.error(err);
        document.getElementById('problemsTableBody').innerHTML =
            `<tr><td colspan="5" style="text-align:center; color:#ef4444; padding:2rem;">Failed to load coding problems.</td></tr>`;
    }
}

function populateTags() {
    const select = document.getElementById('tagFilter');
    const tags = [...new Set(allProblems.map(p => p.categoryTag))].sort();
    tags.forEach(t => {
        const opt = document.createElement('option');
        opt.value = t;
        opt.textContent = t;
        select.appendChild(opt);
    });
}

function updateStats() {
    const solved = allProblems.filter(p => p.solved).length;
    document.getElementById('solvedStat').textContent = `${solved} / ${allProblems.length}`;
}

function renderProblems() {
    const q = document.getElementById('searchInput').value.trim().toLowerCase();
    const diff = document.getElementById('difficultyFilter').value;
    const tag = document.getElementById('tagFilter').value;

    const filtered = allProblems.filter(p => {
        const matchQ = !q || p.title.toLowerCase().includes(q) || p.categoryTag.toLowerCase().includes(q);
        const matchD = !diff || p.difficulty === diff;
        const matchT = !tag || p.categoryTag === tag;
        return matchQ && matchD && matchT;
    });

    const tbody = document.getElementById('problemsTableBody');
    if (filtered.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" style="text-align:center; padding:2rem; color:#64748b;">No matching problems found.</td></tr>`;
        return;
    }

    tbody.innerHTML = filtered.map(p => `
        <tr>
            <td>
                ${p.solved
                    ? '<span class="status-solved" title="Solved"><i class="fas fa-check-circle"></i></span>'
                    : '<span class="status-todo" title="Todo"><i class="far fa-circle"></i></span>'}
            </td>
            <td>
                <a href="/student/coding-problem.html?slug=${encodeURIComponent(p.slug)}" style="font-weight:600; color:#1e293b; text-decoration:none;">
                    ${escapeHtml(p.title)}
                </a>
            </td>
            <td><span class="tag-pill">${escapeHtml(p.categoryTag)}</span></td>
            <td><span class="diff-${p.difficulty}">${p.difficulty}</span></td>
            <td style="text-align:right;">
                <a href="/student/coding-problem.html?slug=${encodeURIComponent(p.slug)}" class="btn btn-small">
                    Solve <i class="fas fa-arrow-right"></i>
                </a>
            </td>
        </tr>
    `).join('');
}

function escapeHtml(str) {
    return String(str).replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]));
}

