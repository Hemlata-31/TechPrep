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

    loadDailyChallenge();
    startDailyCountdown();
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

// =====================================================================
// DAILY CHALLENGE (QUESTION OF THE DAY) ENGINE
// =====================================================================
let currentDailyChallenge = null;
let selectedDcOption = null;
let dcStartTime = Date.now();

async function loadDailyChallenge() {
    const loadingEl = document.getElementById('dcLoading');
    const contentEl = document.getElementById('dcContent');

    try {
        currentDailyChallenge = await api.get('/daily-challenge');
        if (!currentDailyChallenge) {
            loadingEl.innerHTML = '<p style="color:#94a3b8;">No challenge available today.</p>';
            return;
        }

        renderDailyChallenge(currentDailyChallenge);
        loadingEl.style.display = 'none';
        contentEl.style.display = 'block';
        dcStartTime = Date.now();

    } catch (err) {
        console.error('Failed to load daily challenge:', err);
        loadingEl.innerHTML = '<p style="color:#ef4444;"><i class="fas fa-exclamation-triangle"></i> Failed to load today\'s challenge.</p>';
    }
}

function renderDailyChallenge(dc) {
    const topicBadge = document.getElementById('dcTopicBadge');
    const diffBadge = document.getElementById('dcDiffBadge');
    const qText = document.getElementById('dcQuestionText');
    const optA = document.getElementById('dcOptA');
    const optB = document.getElementById('dcOptB');
    const optC = document.getElementById('dcOptC');
    const optD = document.getElementById('dcOptD');
    const submitBtn = document.getElementById('dcSubmitBtn');
    const participants = document.getElementById('dcParticipants');
    const successRate = document.getElementById('dcSuccessRate');

    topicBadge.textContent = `${dc.categoryName || 'General'} • ${dc.topicName}`;
    diffBadge.textContent = dc.difficulty || 'Medium';
    qText.textContent = dc.questionText;

    optA.textContent = dc.optionA;
    optB.textContent = dc.optionB;
    optC.textContent = dc.optionC;
    optD.textContent = dc.optionD;

    participants.textContent = dc.participantsToday || 0;
    successRate.textContent = (dc.successRate || 0) + '%';

    const optionItems = document.querySelectorAll('.dc-option-item');

    if (dc.isAttempted) {
        // Already attempted today
        submitBtn.style.display = 'none';
        optionItems.forEach(item => {
            const opt = item.getAttribute('data-option');
            item.style.cursor = 'default';
            if (opt === dc.correctAnswer) {
                item.classList.add('correct');
            }
            if (opt === dc.userSelectedAnswer && !dc.isCorrect) {
                item.classList.add('wrong');
            }
        });

        displayDailyChallengeResult({
            isCorrect: dc.isCorrect,
            selectedAnswer: dc.userSelectedAnswer,
            correctAnswer: dc.correctAnswer,
            explanation: dc.explanation,
            bonusPointsAwarded: dc.isCorrect ? dc.bonusPoints : 0
        });

    } else {
        // Not yet attempted
        submitBtn.style.display = 'inline-flex';
        submitBtn.disabled = true;

        optionItems.forEach(item => {
            item.className = 'dc-option-item';
            item.style.cursor = 'pointer';

            item.onclick = () => {
                if (currentDailyChallenge && currentDailyChallenge.isAttempted) return;
                optionItems.forEach(i => i.classList.remove('selected'));
                item.classList.add('selected');
                selectedDcOption = item.getAttribute('data-option');
                submitBtn.disabled = false;
            };
        });

        submitBtn.onclick = submitDailyChallenge;
    }
}

async function submitDailyChallenge() {
    if (!selectedDcOption || !currentDailyChallenge || currentDailyChallenge.isAttempted) return;

    const submitBtn = document.getElementById('dcSubmitBtn');
    submitBtn.disabled = true;
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Submitting...';

    const timeTaken = Math.max(1, Math.round((Date.now() - dcStartTime) / 1000));

    try {
        const response = await api.post('/daily-challenge/submit', {
            selectedAnswer: selectedDcOption,
            timeTakenSeconds: timeTaken
        });

        currentDailyChallenge.isAttempted = true;
        currentDailyChallenge.isCorrect = response.isCorrect;
        currentDailyChallenge.userSelectedAnswer = response.selectedAnswer;
        currentDailyChallenge.correctAnswer = response.correctAnswer;
        currentDailyChallenge.explanation = response.explanation;

        // Visual feedback on option cards
        document.querySelectorAll('.dc-option-item').forEach(item => {
            const opt = item.getAttribute('data-option');
            item.style.cursor = 'default';
            if (opt === response.correctAnswer) {
                item.classList.add('correct');
            }
            if (opt === response.selectedAnswer && !response.isCorrect) {
                item.classList.add('wrong');
            }
        });

        submitBtn.style.display = 'none';

        // Update stats counters
        document.getElementById('dcParticipants').textContent = response.participantsToday || 1;
        document.getElementById('dcSuccessRate').textContent = (response.successRate || 0) + '%';

        // Update streaks on dashboard widget
        if (response.currentStreak) {
            document.getElementById('currentStreak').textContent = response.currentStreak;
        }
        if (response.longestStreak) {
            document.getElementById('longestStreak').textContent = response.longestStreak;
        }

        displayDailyChallengeResult(response);

        // Confetti celebration if solved correctly!
        if (response.isCorrect && typeof confetti === 'function') {
            confetti({
                particleCount: 120,
                spread: 70,
                origin: { y: 0.6 }
            });
        }

    } catch (err) {
        console.error('Error submitting daily challenge:', err);
        alert(err.message || 'Failed to submit daily challenge.');
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<i class="fas fa-paper-plane"></i> Submit Challenge';
    }
}

function displayDailyChallengeResult(res) {
    const resultBox = document.getElementById('dcResultBox');
    const banner = document.getElementById('dcFeedbackBanner');
    const expText = document.getElementById('dcExplanation');

    resultBox.style.display = 'block';

    if (res.isCorrect) {
        banner.style.background = '#ecfdf5';
        banner.style.color = '#065f46';
        banner.style.border = '1px solid #a7f3d0';
        banner.innerHTML = `<i class="fas fa-check-circle" style="font-size:1.2rem; color:#10b981;"></i> 
            <div>
                <div><strong>Outstanding! You solved today's challenge!</strong></div>
                <div style="font-size:0.85rem; font-weight:normal; margin-top:2px;">You earned <span style="font-weight:700; color:#047857;">+${res.bonusPointsAwarded || 15} Bonus Points</span> & kept your streak alive 🔥</div>
            </div>`;
    } else {
        banner.style.background = '#fef2f2';
        banner.style.color = '#991b1b';
        banner.style.border = '1px solid #fecaca';
        banner.innerHTML = `<i class="fas fa-times-circle" style="font-size:1.2rem; color:#ef4444;"></i>
            <div>
                <div><strong>Good try! Correct answer was Option ${res.correctAnswer}.</strong></div>
                <div style="font-size:0.85rem; font-weight:normal; margin-top:2px;">Review the solution below to learn the concept.</div>
            </div>`;
    }

    expText.textContent = res.explanation || 'No detailed explanation provided for this question.';
}

function startDailyCountdown() {
    const countdownEl = document.getElementById('dcCountdown');
    if (!countdownEl) return;

    function update() {
        const now = new Date();
        const midnight = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1, 0, 0, 0);
        const diff = midnight - now;

        if (diff <= 0) {
            countdownEl.textContent = '00:00:00';
            return;
        }

        const hrs = Math.floor(diff / (1000 * 60 * 60));
        const mins = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
        const secs = Math.floor((diff % (1000 * 60)) / 1000);

        countdownEl.textContent = `${String(hrs).padStart(2, '0')}:${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
    }

    update();
    setInterval(update, 1000);
}
