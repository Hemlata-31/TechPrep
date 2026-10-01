document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    const params = new URLSearchParams(window.location.search);
    const sessionId = params.get('sessionId');

    if (!sessionId) {
        window.location.href = '/student/dashboard.html';
        return;
    }

    loadResult(sessionId);
});

async function loadResult(sessionId) {
    try {
        const res = await api.get(`/practice/sessions/${sessionId}/result`);

        document.getElementById('topicInfo').textContent = `${res.categoryName} > ${res.subCategoryName} > ${res.topicName} (${res.difficulty} Difficulty)`;
        
        document.getElementById('resTotal').textContent = res.totalQuestions;
        document.getElementById('resAttempted').textContent = res.attemptedQuestions;
        document.getElementById('resCorrect').textContent = res.correctAnswers;
        document.getElementById('resWrong').textContent = res.wrongAnswers;
        document.getElementById('resUnattempted').textContent = res.unattemptedQuestions;

        document.getElementById('resScore').textContent = `${res.score} Marks`;
        document.getElementById('resAccuracy').textContent = `${res.accuracy}%`;

        // Format time
        const mins = Math.floor(res.timeTakenSeconds / 60);
        const secs = res.timeTakenSeconds % 60;
        document.getElementById('resTime').textContent = `${mins} min${mins !== 1 ? 's' : ''} ${secs} sec${secs !== 1 ? 's' : ''}`;

        // Retry button link
        document.getElementById('retryBtn').href = `/student/practice.html?topicId=${res.topicId}&topicName=${encodeURIComponent(res.topicName)}`;

        renderScorecard(res);

    } catch (err) {
        console.error(err);
        alert('Failed to load session result: ' + err.message);
    }
}

function renderScorecard(res) {
    const container = document.getElementById('solutionsContainer');
    container.innerHTML = '';

    if (!res.questions || res.questions.length === 0) {
        container.innerHTML = '<p>No question breakdown available.</p>';
        return;
    }

    res.questions.forEach(q => {
        const qCard = document.createElement('div');
        qCard.style.cssText = 'border: 1px solid #e2e8f0; border-radius: 10px; padding: 1.5rem; margin-bottom: 1.5rem; background: #ffffff;';

        let badgeHtml = '';
        if (!q.isAnswered) {
            badgeHtml = '<span class="badge" style="background:#e2e8f0; color:#475569;">Unattempted</span>';
        } else if (q.isCorrect) {
            badgeHtml = '<span class="badge badge-success"><i class="fas fa-check"></i> Correct</span>';
        } else {
            badgeHtml = '<span class="badge badge-danger"><i class="fas fa-times"></i> Incorrect</span>';
        }

        const renderOpt = (key, text) => {
            let style = 'padding: 0.75rem 1rem; border-radius: 6px; border: 1px solid #e2e8f0; margin-bottom: 0.5rem; font-size: 0.95rem;';
            let icon = '';
            
            if (key === q.correctAnswer) {
                style = 'padding: 0.75rem 1rem; border-radius: 6px; border: 2px solid #22c55e; background: #f0fdf4; color: #166534; font-weight: 600; margin-bottom: 0.5rem;';
                icon = ' <i class="fas fa-check-circle" style="color:#22c55e;"></i> (Correct Answer)';
            } else if (key === q.selectedAnswer && !q.isCorrect) {
                style = 'padding: 0.75rem 1rem; border-radius: 6px; border: 2px solid #ef4444; background: #fef2f2; color: #991b1b; font-weight: 600; margin-bottom: 0.5rem;';
                icon = ' <i class="fas fa-times-circle" style="color:#ef4444;"></i> (Your Choice)';
            } else if (key === q.selectedAnswer) {
                icon = ' (Your Choice)';
            }

            return `<div style="${style}"><strong>${key}.</strong> ${escapeHtml(text)}${icon}</div>`;
        };

        qCard.innerHTML = `
            <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom: 1rem;">
                <h4 style="margin:0; color:#1e293b;">Question ${q.questionIndex}</h4>
                ${badgeHtml}
            </div>
            <p style="font-size: 1.05rem; color:#0f172a; margin-bottom: 1.25rem;">${escapeHtml(q.questionText)}</p>
            
            <div style="margin-bottom: 1.25rem;">
                ${renderOpt('A', q.optionA)}
                ${renderOpt('B', q.optionB)}
                ${renderOpt('C', q.optionC)}
                ${renderOpt('D', q.optionD)}
            </div>

            <div style="background: #f8fafc; border-left: 4px solid #3b82f6; padding: 1rem; border-radius: 0 8px 8px 0; font-size: 0.95rem;">
                <strong style="color:#1e293b;"><i class="fas fa-lightbulb" style="color:#f59e0b;"></i> Explanation:</strong>
                <p style="margin-top: 5px; color:#475569; line-height: 1.6;">${escapeHtml(q.explanation || 'No explanation provided.')}</p>
            </div>
        `;
        container.appendChild(qCard);
    });
}

function escapeHtml(str) {
    if (!str) return '';
    return str.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
}
