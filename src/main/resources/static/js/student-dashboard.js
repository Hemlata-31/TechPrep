document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    if (user) {
        document.getElementById('welcomeMsg').textContent = `Welcome, ${user.name}`;
    }
    initLogoutBtn();
    
    document.getElementById('editGoalBtn').addEventListener('click', async () => {
        const newGoal = prompt("Enter your new Daily Goal (number of questions):", "");
        if (newGoal && !isNaN(newGoal) && parseInt(newGoal) > 0) {
            try {
                await api.put(`/users/me/daily-goal?goal=${parseInt(newGoal)}`);
                alert('Daily goal updated successfully!');
                loadStudentStats();
            } catch (err) {
                alert('Failed to update daily goal: ' + err.message);
            }
        } else if (newGoal) {
            alert('Please enter a valid number greater than 0.');
        }
    });

    loadStudentStats();
});

const categoryIcons = {
    'Aptitude': 'fa-calculator',
    'Reasoning': 'fa-brain',
    'Verbal Ability': 'fa-language',
    'Communication': 'fa-comments',
    'Technical': 'fa-laptop-code',
    'DSA': 'fa-project-diagram'
};

async function loadStudentStats() {
    try {
        const stats = await api.get('/practice/stats');
        document.getElementById('statAttempted').textContent = stats.totalQuestionsAttempted || 0;
        document.getElementById('statAccuracy').textContent = (stats.overallAccuracy || 0) + '%';
        document.getElementById('statCompleted').textContent = stats.totalSessionsCompleted || 0;

        // Populate Streaks & Goals
        const dailyGoal = stats.dailyGoalQuestions || 10;
        const attemptedToday = stats.questionsAttemptedToday || 0;
        const currentStreak = stats.currentStreak || 0;
        const longestStreak = stats.longestStreak || 0;

        document.getElementById('currentStreak').textContent = currentStreak;
        document.getElementById('longestStreak').textContent = longestStreak;
        document.getElementById('dailyGoalText').textContent = `${attemptedToday} / ${dailyGoal} Questions`;

        let progressPct = (attemptedToday / dailyGoal) * 100;
        if (progressPct > 100) progressPct = 100;
        document.getElementById('dailyGoalProgress').style.width = `${progressPct}%`;

        if (attemptedToday >= dailyGoal) {
            document.getElementById('dailyGoalMessage').innerHTML = '<i class="fas fa-check-circle" style="color:#22c55e;"></i> Goal achieved! Great job today.';
            document.getElementById('dailyGoalProgress').style.background = 'linear-gradient(90deg, #22c55e, #4ade80)';
        } else {
            document.getElementById('dailyGoalMessage').innerHTML = `Keep practicing! ${dailyGoal - attemptedToday} more to hit your goal.`;
            document.getElementById('dailyGoalProgress').style.background = 'linear-gradient(90deg, #3b82f6, #60a5fa)';
        }

        const container = document.getElementById('recentActivityContainer');
        if (!stats.recentSessions || stats.recentSessions.length === 0) {
            container.innerHTML = '<div class="card" style="padding: 1rem; color: #64748b;">No recent practice activity yet. Select Categories from the sidebar to start practicing!</div>';
            return;
        }

        container.innerHTML = '';
        stats.recentSessions.forEach(sess => {
            const dateStr = sess.completedAt ? new Date(sess.completedAt).toLocaleDateString() : new Date(sess.startedAt).toLocaleDateString();
            const item = document.createElement('div');
            item.className = 'chapter-item';
            item.innerHTML = `
                <div class="chapter-info">
                    <h4>${sess.topicName} <span class="badge badge-${sess.status === 'COMPLETED' ? 'success' : 'warning'}">${sess.status}</span></h4>
                    <p><i class="fas fa-layer-group"></i> ${sess.categoryName} &gt; ${sess.subCategoryName} &nbsp;|&nbsp; <i class="fas fa-chart-line"></i> Score: ${sess.score} &nbsp;|&nbsp; Accuracy: ${sess.accuracy}%</p>
                </div>
                <div class="chapter-actions">
                    <a href="/student/result.html?sessionId=${sess.id}" class="btn btn-outline btn-small">View Result</a>
                </div>
            `;
            container.appendChild(item);
        });

    } catch (e) {
        console.error('Error loading stats:', e);
    }
}
