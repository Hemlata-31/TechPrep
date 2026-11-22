// ===================================================================
// student-progress.js — Progress page with Chart.js visualizations
// ===================================================================

let accuracyDonutChart = null;
let difficultyBarChart = null;
let timelineChart = null;
let categoryBarChart = null;
let topicBarChart = null;

document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (!user) return;
    initLogoutBtn();
    loadProgressData();
});

async function loadProgressData() {
    try {
        // Fetch both endpoints in parallel
        const [progressData, analyticsData] = await Promise.all([
            api.get('/progress'),
            api.get('/performance/analytics').catch(() => null)
        ]);

        renderSummaryCards(progressData);
        renderStrongestTopics(progressData.strongestTopics || []);
        renderWeakestTopics(progressData.weakestTopics || []);
        renderTopicPerformance(progressData.topicPerformance || []);
        renderRecentAttempts(progressData.recentAttempts || []);

        // Build charts
        renderAccuracyDonut(progressData);
        renderCategoryBarChart(progressData.categoryProgress || []);
        renderTopicBarChart(progressData.topicPerformance || []);

        if (analyticsData) {
            renderDifficultyBarChart(analyticsData.difficulty);
            renderTimelineChart(analyticsData.timeline || []);
        } else {
            renderNoDataChart('difficultyBarChart', 'No difficulty data yet');
            renderNoDataChart('timelineChart', 'No session history yet');
        }

    } catch (error) {
        console.error('Failed to load progress data:', error);
    }
}

// ─────────────────────────────────────────────
// SUMMARY CARDS
// ─────────────────────────────────────────────
function renderSummaryCards(data) {
    document.getElementById('statAttempted').textContent = data.questionsAttempted || 0;
    document.getElementById('statAccuracy').textContent = (data.overallAccuracy || 0).toFixed(1) + '%';
    document.getElementById('statTestsCompleted').textContent = data.testsCompleted || 0;
    document.getElementById('statPracticeCompleted').textContent = data.practiceSessionsCompleted || 0;
}

// ─────────────────────────────────────────────
// CHART 1: Accuracy Donut
// ─────────────────────────────────────────────
function renderAccuracyDonut(data) {
    const correct = data.questionsCorrect || 0;
    const wrong = data.questionsWrong || 0;
    const unattempted = data.questionsUnattempted || 0;
    const total = correct + wrong + unattempted;

    const ctx = document.getElementById('accuracyDonutChart').getContext('2d');
    if (accuracyDonutChart) accuracyDonutChart.destroy();

    if (total === 0) {
        showNoDataOnCanvas('accuracyDonutChart', 'Start practicing to see your score breakdown!');
        return;
    }

    accuracyDonutChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Correct', 'Wrong', 'Unattempted'],
            datasets: [{
                data: [correct, wrong, unattempted],
                backgroundColor: ['#10b981', '#ef4444', '#cbd5e1'],
                borderColor: ['#059669', '#dc2626', '#94a3b8'],
                borderWidth: 2,
                hoverOffset: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '65%',
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: ctx => {
                            const pct = total > 0 ? ((ctx.parsed / total) * 100).toFixed(1) : 0;
                            return ` ${ctx.label}: ${ctx.parsed} (${pct}%)`;
                        }
                    }
                }
            }
        },
        plugins: [{
            id: 'centerText',
            beforeDraw(chart) {
                const { width, height, ctx } = chart;
                const acc = total > 0 ? ((correct / total) * 100).toFixed(1) : 0;
                ctx.save();
                ctx.font = `bold ${width / 7}px sans-serif`;
                ctx.fillStyle = '#1e293b';
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                ctx.fillText(acc + '%', width / 2, height / 2 - 10);
                ctx.font = `${width / 14}px sans-serif`;
                ctx.fillStyle = '#64748b';
                ctx.fillText('Accuracy', width / 2, height / 2 + 16);
                ctx.restore();
            }
        }]
    });
}

// ─────────────────────────────────────────────
// CHART 2: Difficulty Accuracy Bar
// ─────────────────────────────────────────────
function renderDifficultyBarChart(difficulty) {
    const ctx = document.getElementById('difficultyBarChart').getContext('2d');
    if (difficultyBarChart) difficultyBarChart.destroy();

    if (!difficulty) {
        showNoDataOnCanvas('difficultyBarChart', 'No difficulty data available');
        return;
    }

    const easy   = difficulty.easy   ? difficulty.easy.accuracyPercentage   : 0;
    const medium = difficulty.medium ? difficulty.medium.accuracyPercentage : 0;
    const hard   = difficulty.hard   ? difficulty.hard.accuracyPercentage   : 0;

    const easyAttempted   = difficulty.easy   ? difficulty.easy.totalAttempted   : 0;
    const mediumAttempted = difficulty.medium ? difficulty.medium.totalAttempted : 0;
    const hardAttempted   = difficulty.hard   ? difficulty.hard.totalAttempted   : 0;

    difficultyBarChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [
                `Easy\n(${easyAttempted} attempted)`,
                `Medium\n(${mediumAttempted} attempted)`,
                `Hard\n(${hardAttempted} attempted)`
            ],
            datasets: [{
                label: 'Accuracy (%)',
                data: [
                    parseFloat(easy.toFixed(1)),
                    parseFloat(medium.toFixed(1)),
                    parseFloat(hard.toFixed(1))
                ],
                backgroundColor: ['#10b98133', '#f59e0b33', '#ef444433'],
                borderColor: ['#10b981', '#f59e0b', '#ef4444'],
                borderWidth: 2,
                borderRadius: 8,
                borderSkipped: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 100,
                    grid: { color: '#f1f5f9' },
                    ticks: { callback: v => v + '%', font: { size: 11 } }
                },
                x: {
                    grid: { display: false },
                    ticks: { font: { size: 11 } }
                }
            },
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: ctx => ` Accuracy: ${ctx.parsed.y}%`
                    }
                }
            }
        }
    });
}

// ─────────────────────────────────────────────
// CHART 3: Performance Timeline (Line)
// ─────────────────────────────────────────────
function renderTimelineChart(timeline) {
    const ctx = document.getElementById('timelineChart').getContext('2d');
    if (timelineChart) timelineChart.destroy();

    if (!timeline || timeline.length === 0) {
        showNoDataOnCanvas('timelineChart', 'Complete some sessions to see your timeline!');
        return;
    }

    // Take last 15 entries
    const recent = timeline.slice(-15);
    const labels  = recent.map(p => p.formattedDate || (p.date ? p.date.substring(0, 10) : '?'));
    const accData = recent.map(p => parseFloat((p.accuracy || 0).toFixed(1)));

    // Color points: practice = blue, mock = purple
    const pointColors = recent.map(p => p.type === 'MOCK_TEST' ? '#8b5cf6' : '#3b82f6');

    timelineChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels,
            datasets: [{
                label: 'Accuracy (%)',
                data: accData,
                borderColor: '#3b82f6',
                backgroundColor: 'rgba(59,130,246,0.08)',
                tension: 0.4,
                fill: true,
                pointBackgroundColor: pointColors,
                pointBorderColor: pointColors,
                pointRadius: 5,
                pointHoverRadius: 8
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 100,
                    grid: { color: '#f1f5f9' },
                    ticks: { callback: v => v + '%', font: { size: 11 } }
                },
                x: {
                    grid: { display: false },
                    ticks: { font: { size: 10 }, maxRotation: 35 }
                }
            },
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        title: (items) => {
                            const p = recent[items[0].dataIndex];
                            return (p.type === 'MOCK_TEST' ? '🟣 Mock Test' : '🔵 Practice') + ' — ' + labels[items[0].dataIndex];
                        },
                        label: ctx => ` Accuracy: ${ctx.parsed.y}%`,
                        afterLabel: (ctx) => {
                            const p = recent[ctx.dataIndex];
                            return ` Score: ${p.score} | Attempted: ${p.attemptedQuestions}`;
                        }
                    }
                }
            }
        }
    });
}

// ─────────────────────────────────────────────
// CHART 4: Category-wise Accuracy Bar
// ─────────────────────────────────────────────
function renderCategoryBarChart(categories) {
    const ctx = document.getElementById('categoryBarChart').getContext('2d');
    if (categoryBarChart) categoryBarChart.destroy();

    if (!categories || categories.length === 0) {
        showNoDataOnCanvas('categoryBarChart', 'No category data available yet');
        return;
    }

    const palettes = ['#3b82f6','#10b981','#f59e0b','#8b5cf6','#ef4444','#06b6d4','#ec4899','#84cc16'];
    const labels = categories.map(c => c.categoryName);
    const values = categories.map(c => parseFloat((c.accuracy || 0).toFixed(1)));
    const colors = labels.map((_, i) => palettes[i % palettes.length]);

    categoryBarChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels,
            datasets: [{
                label: 'Accuracy (%)',
                data: values,
                backgroundColor: colors.map(c => c + '33'),
                borderColor: colors,
                borderWidth: 2,
                borderRadius: 8,
                borderSkipped: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            indexAxis: 'y',
            scales: {
                x: {
                    beginAtZero: true,
                    max: 100,
                    grid: { color: '#f1f5f9' },
                    ticks: { callback: v => v + '%', font: { size: 11 } }
                },
                y: {
                    grid: { display: false },
                    ticks: { font: { size: 11 } }
                }
            },
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: ctx => {
                            const cat = categories[ctx.dataIndex];
                            return ` ${ctx.parsed.x}% (${cat.totalCorrect}/${cat.totalAttempted} correct)`;
                        }
                    }
                }
            }
        }
    });
}

// ─────────────────────────────────────────────
// CHART 5: Top 8 Topics Horizontal Bar
// ─────────────────────────────────────────────
function renderTopicBarChart(topics) {
    const ctx = document.getElementById('topicBarChart').getContext('2d');
    if (topicBarChart) topicBarChart.destroy();

    if (!topics || topics.length === 0) {
        showNoDataOnCanvas('topicBarChart', 'Practice topics to see rankings!');
        return;
    }

    // Sort by accuracy desc, pick top 8 with attempts
    const attempted = topics.filter(t => (t.totalAttempted || 0) > 0);
    const top8 = [...attempted].sort((a, b) => (b.accuracy || 0) - (a.accuracy || 0)).slice(0, 8);

    const labels = top8.map(t => t.topicName.length > 20 ? t.topicName.substring(0, 18) + '…' : t.topicName);
    const values = top8.map(t => parseFloat((t.accuracy || 0).toFixed(1)));
    const bgColors = values.map(v => v >= 70 ? '#10b98133' : v >= 40 ? '#f59e0b33' : '#ef444433');
    const borderColors = values.map(v => v >= 70 ? '#10b981' : v >= 40 ? '#f59e0b' : '#ef4444');

    topicBarChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels,
            datasets: [{
                label: 'Accuracy (%)',
                data: values,
                backgroundColor: bgColors,
                borderColor: borderColors,
                borderWidth: 2,
                borderRadius: 6,
                borderSkipped: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            indexAxis: 'y',
            scales: {
                x: {
                    beginAtZero: true,
                    max: 100,
                    grid: { color: '#f1f5f9' },
                    ticks: { callback: v => v + '%', font: { size: 11 } }
                },
                y: {
                    grid: { display: false },
                    ticks: { font: { size: 11 } }
                }
            },
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: ctx => {
                            const t = top8[ctx.dataIndex];
                            return ` ${ctx.parsed.x}% (${t.totalCorrect}/${t.totalAttempted} correct)`;
                        }
                    }
                }
            }
        }
    });
}

// ─────────────────────────────────────────────
// HELPER: show no-data message on a canvas
// ─────────────────────────────────────────────
function showNoDataOnCanvas(canvasId, message) {
    const canvas = document.getElementById(canvasId);
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    const parent = canvas.parentElement;
    if (parent && !parent.querySelector('.no-data-msg')) {
        const div = document.createElement('div');
        div.className = 'no-data-msg';
        div.innerHTML = `<i class="fas fa-chart-simple"></i><span>${message}</span>`;
        parent.appendChild(div);
        canvas.style.display = 'none';
    }
}

function renderNoDataChart(canvasId, msg) {
    showNoDataOnCanvas(canvasId, msg);
}

// ─────────────────────────────────────────────
// TOPIC LISTS (Strongest / Weakest)
// ─────────────────────────────────────────────
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

// ─────────────────────────────────────────────
// TOPIC PERFORMANCE TABLE
// ─────────────────────────────────────────────
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

// ─────────────────────────────────────────────
// RECENT ATTEMPTS
// ─────────────────────────────────────────────
function renderRecentAttempts(attempts) {
    const container = document.getElementById('recentAttemptsContainer');
    if (!attempts || attempts.length === 0) {
        container.innerHTML = '<p style="color: #64748b;">No recent attempts found.</p>';
        return;
    }
    container.innerHTML = attempts.map(item => {
        const isMock = item.type === 'MOCK_TEST';
        const link = isMock ? `/student/test-result.html?attemptId=${item.id}` : `/student/result.html?sessionId=${item.id}`;
        return `
            <div class="chapter-item">
                <div class="chapter-info">
                    <div style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 0.25rem;">
                        <span class="badge" style="background: ${isMock ? '#f3e8ff' : '#e0e7ff'}; color: ${isMock ? '#7e22ce' : '#1d4ed8'};">${isMock ? 'Mock Test' : 'Practice'}</span>
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

// ─────────────────────────────────────────────
// UTILITY
// ─────────────────────────────────────────────
function escapeHtml(str) {
    if (!str) return '';
    return str.replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#039;'}[m]));
}
