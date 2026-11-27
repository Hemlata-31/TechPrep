let allMistakes = [];
let filteredMistakes = [];
let retryMode = false;
let retryIndex = 0;

document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    await loadMistakes();
    document.getElementById('catFilter').addEventListener('change', renderMistakes);
    document.getElementById('diffFilter').addEventListener('change', renderMistakes);
});

async function loadMistakes() {
    try {
        allMistakes = await api.get('/mistakes');
        const sel = document.getElementById('catFilter');
        [...new Set(allMistakes.map(m => m.categoryName))].sort().forEach(c => {
            const opt = document.createElement('option');
            opt.value = c;
            opt.textContent = c;
            sel.appendChild(opt);
        });
        renderMistakes();
    } catch (err) {
        console.error(err);
        document.getElementById('mistakesList').innerHTML =
            '<p style="color:#ef4444;">Failed to load mistake journal.</p>';
    }
}

function renderMistakes() {
    const cat = document.getElementById('catFilter').value;
    const diff = document.getElementById('diffFilter').value;

    filteredMistakes = allMistakes.filter(m =>
        (!cat || m.categoryName === cat) && (!diff || m.difficulty === diff)
    );

    document.getElementById('mistakeCountText').textContent =
        `${filteredMistakes.length} mistake${filteredMistakes.length === 1 ? '' : 's'} to review`;

    const retryBtn = document.getElementById('retryModeBtn');
    retryBtn.style.display = filteredMistakes.length > 0 ? 'inline-flex' : 'none';

    const list = document.getElementById('mistakesList');
    if (filteredMistakes.length === 0) {
        list.innerHTML = `
            <div class="card" style="text-align:center; padding:2.5rem;">
                <i class="fas fa-check-circle" style="font-size:2.4rem; color:#10b981; margin-bottom:0.75rem;"></i>
                <h3>Clean Slate!</h3>
                <p style="color:var(--text-secondary); margin-top:6px;">No wrong answers found for the selected filters.</p>
            </div>
        `;
        return;
    }

    list.innerHTML = filteredMistakes.map(m => {
        const opts = [['A', m.optionA], ['B', m.optionB], ['C', m.optionC], ['D', m.optionD]].map(([k, txt]) => {
            let cls = 'm-opt';
            let tag = '';
            if (k === m.correctAnswer) { cls += ' correct'; tag = ' ✓ Correct'; }
            else if (k === m.selectedAnswer) { cls += ' wrong'; tag = ' ✗ Your Choice'; }
            return `<div class="${cls}"><strong>${k}.</strong> ${escapeHtml(txt)}${tag}</div>`;
        }).join('');

        return `
            <div class="mistake-card">
                <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:0.5rem; margin-bottom:0.75rem;">
                    <div>
                        <span class="badge">${escapeHtml(m.categoryName)} · ${escapeHtml(m.topicName)}</span>
                        <span class="badge badge-outline" style="margin-left:6px;">${m.difficulty}</span>
                    </div>
                    <span style="font-size:0.78rem; color:var(--text-secondary);">From ${m.source === 'MOCK_TEST' ? 'Mock Test' : 'Practice'}</span>
                </div>
                <h4 style="margin:0 0 0.9rem; font-size:1.02rem;">${escapeHtml(m.questionText)}</h4>
                ${opts}
                <div class="m-expl">
                    <strong style="color:var(--text-primary);"><i class="fas fa-lightbulb" style="color:#f59e0b;"></i> Explanation:</strong>
                    <div style="margin-top:4px;">${escapeHtml(m.explanation || 'No explanation provided.')}</div>
                </div>
            </div>
        `;
    }).join('');
}

function toggleRetryMode() {
    retryMode = !retryMode;
    document.getElementById('reviewSection').style.display = retryMode ? 'none' : 'block';
    document.getElementById('retrySection').style.display = retryMode ? 'block' : 'none';
    document.getElementById('retryModeBtn').innerHTML = retryMode
        ? '<i class="fas fa-list"></i> View Mistake List'
        : '<i class="fas fa-redo"></i> Practice My Mistakes';
    if (retryMode) {
        retryIndex = 0;
        renderRetryQuestion();
    }
}

function renderRetryQuestion() {
    const m = filteredMistakes[retryIndex];
    document.getElementById('retryCounter').textContent = `Question ${retryIndex + 1} of ${filteredMistakes.length}`;
    document.getElementById('retryTopicBadge').textContent = `${m.topicName} · ${m.difficulty}`;
    document.getElementById('retryQuestionText').textContent = m.questionText;
    document.getElementById('retryFeedback').style.display = 'none';

    const nextBtn = document.getElementById('retryNextBtn');
    nextBtn.innerHTML = retryIndex >= filteredMistakes.length - 1
        ? '<i class="fas fa-check"></i> Finish Review'
        : 'Next <i class="fas fa-chevron-right"></i>';

    const optsEl = document.getElementById('retryOptions');
    optsEl.innerHTML = [['A', m.optionA], ['B', m.optionB], ['C', m.optionC], ['D', m.optionD]].map(([k, txt]) => `
        <div class="option-card" onclick="selectRetryOption(this, '${k}')">
            <span class="option-label">${k}</span>
            <span class="option-text">${escapeHtml(txt)}</span>
        </div>
    `).join('');
}

function selectRetryOption(card, chosen) {
    const m = filteredMistakes[retryIndex];
    const cards = document.querySelectorAll('#retryOptions .option-card');
    cards.forEach(c => {
        c.onclick = null;
        const opt = c.querySelector('.option-label').textContent;
        if (opt === m.correctAnswer) c.classList.add('correct');
        else if (opt === chosen && chosen !== m.correctAnswer) c.classList.add('wrong');
    });

    const fb = document.getElementById('retryFeedback');
    fb.style.display = 'block';
    const isOk = chosen === m.correctAnswer;
    fb.innerHTML = `
        <div class="feedback-banner ${isOk ? 'banner-success' : 'banner-error'}">
            <i class="fas ${isOk ? 'fa-check-circle' : 'fa-times-circle'}"></i>
            <strong>${isOk ? 'Got it right this time!' : `Incorrect — Correct answer is Option ${m.correctAnswer}.`}</strong>
        </div>
        <div><strong>Explanation:</strong> ${escapeHtml(m.explanation || 'No explanation provided.')}</div>
    `;
}

function nextRetryQuestion() {
    if (retryIndex >= filteredMistakes.length - 1) {
        toggleRetryMode();
        return;
    }
    retryIndex++;
    renderRetryQuestion();
}

function escapeHtml(str) {
    return String(str ?? '').replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]));
}
