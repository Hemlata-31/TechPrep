let currentTimeFrame = 'OVERALL';
let currentCategoryId = null;

document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (!user) return;
    initLogoutBtn();

    await loadCategories();
    setupEventListeners();
    await loadLeaderboard();
});

async function loadCategories() {
    try {
        const categories = await api.get('/categories');
        const select = document.getElementById('categoryFilter');
        categories.forEach(cat => {
            const option = document.createElement('option');
            option.value = cat.id;
            option.textContent = cat.name;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Failed to load categories for filter:', error);
    }
}

function setupEventListeners() {
    // Timeframe tab switching
    const tabBtns = document.querySelectorAll('.tab-btn');
    tabBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            tabBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            currentTimeFrame = btn.getAttribute('data-timeframe');
            loadLeaderboard();
        });
    });

    // Category filter dropdown
    const categorySelect = document.getElementById('categoryFilter');
    categorySelect.addEventListener('change', (e) => {
        currentCategoryId = e.target.value ? parseInt(e.target.value) : null;
        loadLeaderboard();
    });
}

async function loadLeaderboard() {
    try {
        let endpoint = `/leaderboard?timeFrame=${currentTimeFrame}`;
        if (currentCategoryId) {
            endpoint += `&categoryId=${currentCategoryId}`;
        }

        const data = await api.get(endpoint);
        renderCurrentStudentBanner(data.currentUserEntry);
        renderLeaderboardTable(data.leaderboardEntries || []);
    } catch (error) {
        console.error('Failed to load leaderboard:', error);
        document.getElementById('leaderboardTbody').innerHTML = `
            <tr>
                <td colspan="5" style="text-align: center; color: #ef4444; padding: 2rem;">Failed to load leaderboard data.</td>
            </tr>
        `;
    }
}

function renderCurrentStudentBanner(userEntry) {
    if (!userEntry) {
        document.getElementById('myStudentName').textContent = 'Your Ranking';
        document.getElementById('myRank').textContent = '#--';
        document.getElementById('myScore').textContent = '0';
        document.getElementById('myAccuracy').textContent = '0%';
        document.getElementById('myAttempted').textContent = '0';
        return;
    }

    document.getElementById('myStudentName').textContent = userEntry.studentName || 'Your Ranking';
    document.getElementById('myRank').textContent = '#' + (userEntry.rank || '--');
    document.getElementById('myScore').textContent = userEntry.totalScore || 0;
    document.getElementById('myAccuracy').textContent = (userEntry.accuracy || 0) + '%';
    document.getElementById('myAttempted').textContent = userEntry.questionsAttempted || 0;
}

function renderLeaderboardTable(entries) {
    const tbody = document.getElementById('leaderboardTbody');
    if (!entries || entries.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="text-align: center; color: #64748b; padding: 2rem;">No student attempt records found for this view.</td>
            </tr>
        `;
        return;
    }

    tbody.innerHTML = entries.map(e => {
        const isCurrentUser = e.isCurrentUser ? 'highlight-user' : '';
        let rankBadgeClass = 'rank-other';
        if (e.rank === 1) rankBadgeClass = 'rank-1';
        else if (e.rank === 2) rankBadgeClass = 'rank-2';
        else if (e.rank === 3) rankBadgeClass = 'rank-3';

        return `
            <tr class="${isCurrentUser}">
                <td>
                    <span class="rank-badge ${rankBadgeClass}">${e.rank}</span>
                </td>
                <td style="font-weight: 500; color: #1e293b;">
                    ${escapeHtml(e.studentName)} ${e.isCurrentUser ? '<span class="badge badge-success" style="margin-left: 8px;">You</span>' : ''}
                </td>
                <td style="font-weight: 600; color: #3b82f6;">${e.totalScore}</td>
                <td>
                    <span style="font-weight: 500; color: ${e.accuracy >= 70 ? '#166534' : (e.accuracy >= 40 ? '#b45309' : '#991b1b')};">
                        ${e.accuracy}%
                    </span>
                </td>
                <td>${e.questionsAttempted}</td>
            </tr>
        `;
    }).join('');
}

function escapeHtml(str) {
    if (!str) return '';
    return str.replace(/[&<>"']/g, function(m) {
        return {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        }[m];
    });
}
