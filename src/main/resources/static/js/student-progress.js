document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (!user) return;
    initLogoutBtn();

    loadProgressData();
});

async function loadProgressData() {
    try {
        const data = await api.get('/progress');
        renderSummaryCards(data);
        renderStrongestTopics(data.strongestTopics || []);
        renderWeakestTopics(data.weakestTopics || []);
        renderCategoryProgress(data.categoryProgress || []);
        renderTopicPerformance(data.topicPerformance || []);
        renderRecentAttempts(data.recentAttempts || []);
    } catch (error) {
        console.error('Failed to load progress data:', error);
    }
}

function renderSummaryCards(data) {
    document.getElementById('statAttempted').textContent = data.questionsAttempted || 0;
    document.getElementById('statAccuracy').textContent = (data.overallAccuracy || 0) + '%';
    document.getElementById('statTestsCompleted').textContent = data.testsCompleted || 0;
    document.getElementById('statPracticeCompleted').textContent = data.practiceSessionsCompleted || 0;
}

function renderStrongestTopics(topics) {
    const container = document.getElementById('strongestTopicsContainer');
    if (!topics || topics.length === 0) {
        container.innerHTML = '<p style="color: #64748b; font-size: 0.9rem;">No topic data recorded yet.</p>';
        return;
    }

    container.innerHTML = topics.map(t => `
        <div style="margin-bottom: 1rem;">
            <div style="display: flex; justify-content: space-between; margin-bottom: 0.25rem; font-size: 0.9rem;">
                <span style="font-weight: 600; color: #1e293b;">${escapeHtml(t.topicName)}</span>
                <span style="color: #166534; font-weight: 600;">${t.accuracy}% Accuracy</span>
            </div>
            <div class="progress-bar">
                <div class="progress-fill" style="width: ${t.accuracy}%; background: #10b981;"></div>
            </div>
            <div style="font-size: 0.8rem; color: #64748b;">Category: ${escapeHtml(t.categoryName)} | ${t.totalCorrect}/${t.totalAttempted} Correct</div>
        </div>
    `).join('');
}

function renderWeakestTopics(topics) {
    const container = document.getElementById('weakestTopicsContainer');
    if (!topics || topics.length === 0) {
        container.innerHTML = '<p style="color: #64748b; font-size: 0.9rem;">No topic data recorded yet.</p>';
        return;
    }

    container.innerHTML = topics.map(t => `
        <div style="margin-bottom: 1rem;">
            <div style="display: flex; justify-content: space-between; margin-bottom: 0.25rem; font-size: 0.9rem;">
                <span style="font-weight: 600; color: #1e293b;">${escapeHtml(t.topicName)}</span>
                <span style="color: #991b1b; font-weight: 600;">${t.accuracy}% Accuracy</span>
            </div>
            <div class="progress-bar">
                <div class="progress-fill" style="width: ${t.accuracy}%; background: #ef4444;"></div>
            </div>
            <div style="font-size: 0.8rem; color: #64748b;">Category: ${escapeHtml(t.categoryName)} | ${t.totalCorrect}/${t.totalAttempted} Correct</div>
        </div>
    `).join('');
}

function renderCategoryProgress(categories) {
    const container = document.getElementById('categoryProgressContainer');
    if (!categories || categories.length === 0) {
        container.innerHTML = '<p style="color: #64748b;">No category progress available.</p>';
        return;
    }

    container.innerHTML = categories.map(c => `
        <div style="margin-bottom: 1.25rem;">
            <div style="display: flex; justify-content: space-between; margin-bottom: 0.35rem; font-weight: 500;">
                <span>${escapeHtml(c.categoryName)}</span>
                <span>${c.totalCorrect} / ${c.totalAttempted} Correct (${c.accuracy}%)</span>
            </div>
            <div class="progress-bar" style="height: 10px;">
                <div class="progress-fill" style="width: ${c.accuracy}%; background: #3b82f6;"></div>
            </div>
        </div>
    `).join('');
}

function renderTopicPerformance(topics) {
    const container = document.getElementById('topicPerformanceContainer');
    if (!topics || topics.length === 0) {
        container.innerHTML = '<p style="color: #64748b;">No topic performance available.</p>';
        return;
    }

    container.innerHTML = `
        <div style="overflow-x: auto;">
            <table style="width: 100%; border-collapse: collapse; text-align: left; font-size: 0.95rem;">
                <thead>
                    <tr style="border-bottom: 2px solid #e2e8f0; color: #475569;">
                        <th style="padding: 0.75rem 0.5rem;">Topic</th>
                        <th style="padding: 0.75rem 0.5rem;">Category</th>
                        <th style="padding: 0.75rem 0.5rem;">Attempted</th>
                        <th style="padding: 0.75rem 0.5rem;">Correct</th>
                        <th style="padding: 0.75rem 0.5rem; width: 30%;">Accuracy</th>
                    </tr>
                </thead>
                <tbody>
                    ${topics.map(t => `
                        <tr style="border-bottom: 1px solid #f1f5f9;">
                            <td style="padding: 0.75rem 0.5rem; font-weight: 500; color: #1e293b;">${escapeHtml(t.topicName)}</td>
                            <td style="padding: 0.75rem 0.5rem; color: #64748b;">${escapeHtml(t.categoryName)}</td>
                            <td style="padding: 0.75rem 0.5rem;">${t.totalAttempted}</td>
                            <td style="padding: 0.75rem 0.5rem; color: #166534; font-weight: 500;">${t.totalCorrect}</td>
                            <td style="padding: 0.75rem 0.5rem;">
                                <div style="display: flex; align-items: center; gap: 0.75rem;">
                                    <div class="progress-bar" style="flex: 1; margin-bottom: 0;">
                                        <div class="progress-fill" style="width: ${t.accuracy}%; background: ${t.accuracy >= 70 ? '#10b981' : (t.accuracy >= 40 ? '#f59e0b' : '#ef4444')};"></div>
                                    </div>
                                    <span style="font-size: 0.85rem; font-weight: 600; min-width: 45px;">${t.accuracy}%</span>
                                </div>
                            </td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        </div>
    `;
}

function renderRecentAttempts(attempts) {
    const container = document.getElementById('recentAttemptsContainer');
    if (!attempts || attempts.length === 0) {
        container.innerHTML = '<p style="color: #64748b;">No recent attempts found.</p>';
        return;
    }

    container.innerHTML = attempts.map(item => {
        const isMock = item.type === 'MOCK_TEST';
        const badgeClass = isMock ? 'badge-purple' : 'badge-blue';
        const badgeText = isMock ? 'Mock Test' : 'Practice';
        const link = isMock ? `/student/test-result.html?attemptId=${item.id}` : `/student/result.html?sessionId=${item.id}`;

        return `
            <div class="chapter-item">
                <div class="chapter-info">
                    <div style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 0.25rem;">
                        <span class="badge ${badgeClass}" style="background: ${isMock ? '#f3e8ff' : '#e0e7ff'}; color: ${isMock ? '#7e22ce' : '#1d4ed8'};">${badgeText}</span>
                        <h4 style="margin-bottom:0; display: inline;">${escapeHtml(item.title)}</h4>
                    </div>
                    <p style="margin-bottom:0;">Date: ${item.date} | Score: <strong>${item.score}</strong> | Accuracy: <strong>${item.accuracy}%</strong></p>
                </div>
                <div class="chapter-actions">
                    <a href="${link}" class="btn btn-outline btn-small">View Summary</a>
                </div>
            </div>
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
