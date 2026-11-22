document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    if (user) {
        document.getElementById('welcomeMsg').textContent = `Welcome back, ${user.name} 👋`;
    }
    initLogoutBtn();

    document.getElementById('editGoalBtn').addEventListener('click', async () => {
        const newGoal = prompt('Enter your new Daily Goal (number of questions):', '');
        if (newGoal && !isNaN(newGoal) && parseInt(newGoal) > 0) {
            try {
                await api.put(`/users/me/daily-goal?goal=${parseInt(newGoal)}`);
                loadDashboard();
            } catch (err) {
                alert('Failed to update daily goal: ' + err.message);
            }
        } else if (newGoal) {
            alert('Please enter a valid number greater than 0.');
        }
    });

    loadDashboard();
});

async function loadDashboard() {
    try {
        const progress = await api.get('/progress');
        renderGoalAndStreak(progress);
        renderOverallStats(progress);
        renderDashCategoryMini(progress);
        renderTopicAnalysis(progress);
        renderImprovementTips(progress);
        renderRecentActivity(progress);
    } catch (e) {
        console.error('Dashboard load error:', e);
    }
}

function renderGoalAndStreak(progress) {
    const dailyGoal = progress.dailyGoalQuestions || 10;
    const attemptedToday = progress.questionsAttemptedToday || 0;
    const currentStreak = progress.currentStreak || 0;
    const longestStreak = progress.longestStreak || 0;

    document.getElementById('currentStreak').textContent = currentStreak;
    document.getElementById('longestStreak').textContent = longestStreak;
    document.getElementById('dailyGoalText').textContent = `${attemptedToday} / ${dailyGoal} Questions`;

    let pct = Math.min((attemptedToday / dailyGoal) * 100, 100);
    document.getElementById('dailyGoalProgress').style.width = `${pct}%`;

    if (attemptedToday >= dailyGoal) {
        document.getElementById('dailyGoalMessage').innerHTML = '<i class="fas fa-check-circle" style="color:#22c55e;"></i> Daily goal achieved! Excellent work!';
        document.getElementById('dailyGoalProgress').style.background = 'linear-gradient(90deg, #22c55e, #4ade80)';
    } else {
        const remaining = dailyGoal - attemptedToday;
        document.getElementById('dailyGoalMessage').innerHTML = `<i class="fas fa-info-circle" style="color:#3b82f6;"></i> ${remaining} more question${remaining !== 1 ? 's' : ''} to hit today's goal.`;
        document.getElementById('dailyGoalProgress').style.background = 'linear-gradient(90deg, #3b82f6, #60a5fa)';
    }
}

let _dashDonut = null;

function renderOverallStats(progress) {
    document.getElementById('statAttempted').textContent = progress.questionsAttempted || 0;
    document.getElementById('statAccuracy').textContent = (progress.overallAccuracy || 0).toFixed(1) + '%';
    document.getElementById('statCompleted').textContent = progress.practiceSessionsCompleted || 0;

    // Mini donut on dashboard
    const correct    = progress.questionsCorrect    || 0;
    const wrong      = progress.questionsWrong      || 0;
    const unattempted= progress.questionsUnattempted|| 0;

    const correctEl = document.getElementById('dashCorrectCount');
    const wrongEl   = document.getElementById('dashWrongCount');
    if (correctEl) correctEl.textContent = correct;
    if (wrongEl)   wrongEl.textContent   = wrong;

    const canvas = document.getElementById('dashDonutChart');
    if (!canvas) return;
    if (_dashDonut) { _dashDonut.destroy(); _dashDonut = null; }

    const total = correct + wrong + unattempted;
    _dashDonut = new Chart(canvas.getContext('2d'), {
        type: 'doughnut',
        data: {
            labels: ['Correct', 'Wrong', 'Unattempted'],
            datasets: [{
                data: total > 0 ? [correct, wrong, unattempted] : [1, 0, 0],
                backgroundColor: total > 0
                    ? ['#10b981', '#ef4444', '#cbd5e1']
                    : ['#e2e8f0', '#e2e8f0', '#e2e8f0'],
                borderWidth: 0,
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '72%',
            plugins: { legend: { display: false }, tooltip: { enabled: total > 0 } }
        },
        plugins: [{
            id: 'center',
            beforeDraw(chart) {
                const { width, height, ctx } = chart;
                const acc = total > 0 ? ((correct / total) * 100).toFixed(0) : 0;
                ctx.save();
                ctx.font = `bold ${width / 5}px sans-serif`;
                ctx.fillStyle = '#1e293b';
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                ctx.fillText(acc + '%', width / 2, height / 2);
                ctx.restore();
            }
        }]
    });
}

function renderDashCategoryMini(progress) {
    const container = document.getElementById('dashCategoryMini');
    if (!container) return;
    const cats = (progress.categoryProgress || []).slice(0, 4);
    if (cats.length === 0) {
        container.innerHTML = '<p style="color:#94a3b8; font-size:0.85rem;">No category data yet.</p>';
        return;
    }
    container.innerHTML = cats.map(c => {
        const pct = parseFloat(c.accuracy || 0).toFixed(0);
        const color = pct >= 70 ? '#10b981' : pct >= 40 ? '#f59e0b' : '#ef4444';
        return `
            <div style="margin-bottom: 0.6rem;">
                <div style="display:flex; justify-content:space-between; font-size:0.82rem; margin-bottom:3px;">
                    <span style="color:#374151; font-weight:500;">${c.categoryName}</span>
                    <span style="color:${color}; font-weight:600;">${pct}%</span>
                </div>
                <div style="background:#e2e8f0; border-radius:99px; height:5px;">
                    <div style="background:${color}; width:${pct}%; height:5px; border-radius:99px; transition:width 0.5s;"></div>
                </div>
            </div>
        `;
    }).join('');
}

function renderTopicAnalysis(progress) {
    renderTopicList('weakTopicsContainer', progress.weakestTopics, 'weak');
    renderTopicList('hardTopicsContainer', progress.hardTopics, 'hard');
    renderTopicList('strongTopicsContainer', progress.strongestTopics, 'strong');
}

function renderTopicList(containerId, topics, type) {
    const container = document.getElementById(containerId);

    if (!topics || topics.length === 0) {
        const msgs = {
            weak: 'No weak topics found yet. Keep practicing!',
            hard: 'No hard topics detected yet. Good job!',
            strong: 'No strong topics yet. Complete practice sessions!'
        };
        container.innerHTML = `<p style="color:#94a3b8; font-size:0.88rem; text-align:center; padding:1rem 0;">${msgs[type]}</p>`;
        return;
    }

    const colorMap = {
        weak: { bar: '#ef4444', badge: '#fef2f2', text: '#ef4444' },
        hard: { bar: '#f59e0b', badge: '#fffbeb', text: '#d97706' },
        strong: { bar: '#22c55e', badge: '#f0fdf4', text: '#16a34a' }
    };
    const colors = colorMap[type];

    container.innerHTML = '';
    topics.forEach(t => {
        const pct = t.accuracy || 0;
        const row = document.createElement('div');
        row.className = 'topic-row';
        row.innerHTML = `
            <div style="flex:1; min-width:0;">
                <div style="font-size:0.88rem; font-weight:600; color:#1e293b; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${t.topicName}">${t.topicName}</div>
                <div style="font-size:0.78rem; color:#94a3b8;">${t.categoryName || 'General'} &bull; ${t.totalAttempted} Q</div>
            </div>
            <div style="display:flex; align-items:center; gap:8px; flex-shrink:0; margin-left:8px;">
                <div class="topic-bar-wrap">
                    <div class="topic-bar" style="width:${pct}%; background:${colors.bar};"></div>
                </div>
                <span style="font-size:0.82rem; font-weight:700; color:${colors.text}; min-width:38px; text-align:right;">${pct}%</span>
            </div>
        `;
        
        // Link wrapper if not strong
        if (type !== 'strong') {
            const link = document.createElement('a');
            // Normally you need subject/cat ids for practice.html breadcrumbs, but passing topic is enough to start
            link.href = `/student/practice.html?topicId=${t.topicId}&topicName=${encodeURIComponent(t.topicName)}&catName=${encodeURIComponent(t.categoryName || 'General')}`;
            link.style.textDecoration = 'none';
            link.appendChild(row);
            
            // Add a subtle hover effect
            link.onmouseover = function() { row.style.background = '#f8fafc'; };
            link.onmouseout = function() { row.style.background = 'transparent'; };
            row.style.transition = 'background 0.2s ease';
            row.style.padding = '0.6rem 0.5rem';
            row.style.borderRadius = '6px';
            row.style.margin = '0 -0.5rem';
            
            container.appendChild(link);
        } else {
            container.appendChild(row);
        }
    });

    if (type !== 'strong') {
        const link = document.createElement('a');
        link.href = '/student/categories.html';
        link.style.cssText = 'display:block; text-align:center; margin-top:1rem; font-size:0.85rem; font-weight:600; color:#3b82f6;';
        link.innerHTML = 'Browse All Topics <i class="fas fa-arrow-right"></i>';
        container.appendChild(link);
    }
}

function renderImprovementTips(progress) {
    const container = document.getElementById('tipsContainer');
    const tips = progress.improvementTips;

    if (!tips || tips.length === 0) {
        container.innerHTML = '<p style="color:#94a3b8; font-size:0.9rem;">No tips available yet. Start practicing to get personalized recommendations!</p>';
        return;
    }

    const icons = ['💡', '🎯', '⚡', '🔥', '✅', '📝', '🚀', '📚'];
    const bgColors = ['#fffbeb', '#fef2f2', '#f0fdf4', '#fff7ed', '#f5f3ff', '#f0f9ff', '#fdf4ff', '#eff6ff'];

    container.innerHTML = '';
    tips.forEach((tip, i) => {
        // Find icon in tip text if exists, or use array fallback
        const emojiMatch = tip.match(/^([\uD800-\uDBFF][\uDC00-\uDFFF]|\S)\s/);
        let iconHtml = icons[i % icons.length];
        let tipText = tip;
        
        if (emojiMatch) {
            iconHtml = emojiMatch[1];
            tipText = tip.replace(emojiMatch[0], '');
        }
        
        const div = document.createElement('div');
        div.className = 'tip-item';
        div.innerHTML = `
            <span class="tip-icon" style="background:${bgColors[i % bgColors.length]}; font-size:1rem;">${iconHtml}</span>
            <span style="padding-top: 4px;">${tipText}</span>
        `;
        container.appendChild(div);
    });
}

function renderRecentActivity(progress) {
    const container = document.getElementById('recentActivityContainer');
    const attempts = progress.recentAttempts;

    if (!attempts || attempts.length === 0) {
        container.innerHTML = '<div class="card" style="padding: 1rem; color: #64748b;">No recent activity yet. <a href="/student/categories.html">Start practicing now!</a></div>';
        return;
    }

    container.innerHTML = '';
    attempts.slice(0, 5).forEach(att => {
        const isPractice = att.type === 'PRACTICE';
        const badgeClass = (att.status === 'COMPLETED' || att.status === 'SUBMITTED') ? 'badge-success' : 'badge-warning';
        const icon = isPractice ? 'fa-dumbbell' : 'fa-laptop-code';
        const typeLabel = isPractice ? 'Practice' : 'Mock Test';
        const linkHref = isPractice
            ? `/student/result.html?sessionId=${att.id}`
            : `/student/test-result.html?attemptId=${att.id}`;

        const item = document.createElement('div');
        item.className = 'chapter-item';
        item.innerHTML = `
            <div class="chapter-info">
                <h4><i class="fas ${icon}" style="margin-right:6px; color:#64748b;"></i>${att.title} 
                    <span class="badge" style="background:#e0e7ff; color:#4338ca; font-size:0.75rem;">${typeLabel}</span>
                    <span class="badge ${badgeClass}" style="font-size:0.75rem;">${att.status}</span>
                </h4>
                <p style="margin-top:4px;">
                    <i class="fas fa-star" style="color:#f59e0b;"></i> Score: ${att.score || 0} &nbsp;|&nbsp;
                    <i class="fas fa-bullseye" style="color:#3b82f6;"></i> Accuracy: ${att.accuracy || 0}% &nbsp;|&nbsp;
                    <i class="fas fa-calendar-alt"></i> ${att.date}
                </p>
            </div>
            <div class="chapter-actions">
                <a href="${linkHref}" class="btn btn-outline btn-small">Review</a>
            </div>
        `;
        container.appendChild(item);
    });
}
