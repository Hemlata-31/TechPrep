let allGuides = [];
let personalNotesLoaded = false;

document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }

    await loadStudyGuides();

    document.getElementById('guideSearch').addEventListener('input', renderGuides);
    document.getElementById('guideCategoryFilter').addEventListener('change', renderGuides);
});

async function loadStudyGuides() {
    try {
        allGuides = await api.get('/study-notes');
        const sel = document.getElementById('guideCategoryFilter');
        const cats = [...new Set(allGuides.map(g => g.category))].sort();
        cats.forEach(c => {
            const opt = document.createElement('option');
            opt.value = c;
            opt.textContent = c;
            sel.appendChild(opt);
        });
        renderGuides();
    } catch (err) {
        console.error(err);
        document.getElementById('guidesGrid').innerHTML =
            '<p style="color:#ef4444;">Failed to load study notes.</p>';
    }
}

function renderGuides() {
    const q = document.getElementById('guideSearch').value.trim().toLowerCase();
    const cat = document.getElementById('guideCategoryFilter').value;

    const filtered = allGuides.filter(g => {
        const matchQ = !q || g.title.toLowerCase().includes(q)
            || (g.description && g.description.toLowerCase().includes(q))
            || (g.highlights && g.highlights.toLowerCase().includes(q));
        const matchC = !cat || g.category === cat;
        return matchQ && matchC;
    });

    const grid = document.getElementById('guidesGrid');
    if (filtered.length === 0) {
        grid.innerHTML = '<p style="color:var(--text-secondary);">No matching study guides found.</p>';
        return;
    }

    grid.innerHTML = filtered.map(g => `
        <div class="guide-card">
            <div>
                <span class="cat-badge">${escapeHtml(g.category)}</span>
                <h3 style="margin:0 0 0.45rem; font-size:1.08rem;">${escapeHtml(g.title)}</h3>
                <p style="font-size:0.88rem; color:var(--text-secondary);">${escapeHtml(g.description || '')}</p>
                ${g.highlights ? `
                    <div class="formula-strip">
                        <strong style="color:var(--text-primary);"><i class="fas fa-bolt" style="color:#f59e0b;"></i> Key Formulas:</strong><br>
                        ${escapeHtml(g.highlights)}
                    </div>
                ` : ''}
            </div>
            <div class="guide-actions">
                <button class="btn btn-outline btn-small" onclick="openPdf(${g.id}, '${escapeAttr(g.downloadUrl)}', '${escapeAttr(g.fileName || 'study-note.pdf')}', false)">
                    <i class="fas fa-eye"></i> View PDF
                </button>
                <button class="btn btn-small" onclick="openPdf(${g.id}, '${escapeAttr(g.downloadUrl)}', '${escapeAttr(g.fileName || 'study-note.pdf')}', true)">
                    <i class="fas fa-download"></i> Download
                </button>
            </div>
        </div>
    `).join('');
}

async function openPdf(id, url, fileName, download) {
    // Static /notes/*.pdf can be opened directly; uploaded /api/study-notes/{id}/pdf needs Bearer token
    if (url.startsWith('/notes/')) {
        if (download) {
            const a = document.createElement('a');
            a.href = url;
            a.download = fileName;
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            window.open(url, '_blank');
        }
        return;
    }

    try {
        const token = localStorage.getItem('token');
        const resp = await fetch(`http://localhost:8080${url}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (!resp.ok) throw new Error('Failed to fetch PDF');
        const blob = await resp.blob();
        const blobUrl = URL.createObjectURL(blob);
        if (download) {
            const a = document.createElement('a');
            a.href = blobUrl;
            a.download = fileName;
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            window.open(blobUrl, '_blank');
        }
    } catch (err) {
        alert('Could not open PDF: ' + err.message);
    }
}

function switchNotesTab(tab) {
    const isGuides = tab === 'guides';
    document.getElementById('guidesPane').style.display = isGuides ? 'block' : 'none';
    document.getElementById('myNotesPane').style.display = isGuides ? 'none' : 'block';
    document.getElementById('tabGuidesBtn').classList.toggle('active', isGuides);
    document.getElementById('tabMyNotesBtn').classList.toggle('active', !isGuides);
    if (!isGuides && !personalNotesLoaded) {
        loadPersonalNotes();
    }
}

async function loadPersonalNotes() {
    const container = document.getElementById('myNotesContainer');
    try {
        const notes = await api.get('/notes');
        personalNotesLoaded = true;
        if (!notes || notes.length === 0) {
            container.innerHTML = `
                <div class="card" style="text-align:center; padding:2.5rem;">
                    <i class="fas fa-sticky-note" style="font-size:2.2rem; color:var(--text-secondary); margin-bottom:0.75rem;"></i>
                    <h3>No Practice Notes Yet</h3>
                    <p style="color:var(--text-secondary); margin-top:6px;">Open any topic in Categories and use the "My Notes for this Topic" panel while practicing!</p>
                </div>
            `;
            return;
        }
        container.innerHTML = notes.map(n => `
            <div class="personal-note-card">
                <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:0.5rem;">
                    <h3 style="margin:0; font-size:1.05rem;">${escapeHtml(n.topicName)}</h3>
                    <span style="font-size:0.8rem; color:var(--text-secondary);">Updated: ${new Date(n.updatedAt).toLocaleString()}</span>
                </div>
                <textarea id="noteText-${n.topicId}">${escapeHtml(n.content || '')}</textarea>
                <div style="display:flex; justify-content:flex-end; align-items:center; gap:0.75rem; margin-top:0.65rem;">
                    <span id="noteSaved-${n.topicId}" style="color:#10b981; font-size:0.82rem; display:none;"><i class="fas fa-check-circle"></i> Saved</span>
                    <button class="btn btn-small" onclick="saveTopicNote(${n.topicId})"><i class="fas fa-save"></i> Save Changes</button>
                </div>
            </div>
        `).join('');
    } catch (err) {
        container.innerHTML = '<p style="color:#ef4444;">Failed to load personal notes.</p>';
    }
}

async function saveTopicNote(topicId) {
    const content = document.getElementById(`noteText-${topicId}`).value;
    try {
        await api.put(`/notes/${topicId}`, { content });
        const msg = document.getElementById(`noteSaved-${topicId}`);
        msg.style.display = 'inline';
        setTimeout(() => { msg.style.display = 'none'; }, 2500);
    } catch (err) {
        alert('Failed to save note.');
    }
}

function escapeHtml(str) {
    return String(str ?? '').replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]));
}

function escapeAttr(str) {
    return String(str ?? '').replace(/'/g, "\\'");
}

