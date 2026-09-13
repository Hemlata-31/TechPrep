document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'STUDENT') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();

    const params = new URLSearchParams(window.location.search);
    const attemptId = params.get('attemptId');
    if (!attemptId) {
        window.location.href = '/student/mock-tests.html';
        return;
    }

    try {
        const result = await api.get(`/test-attempts/${attemptId}`);
        renderScorecard(result);
    } catch (err) {
        alert('Failed to load scorecard report: ' + err.message);
        window.location.href = '/student/mock-tests.html';
    }
});

function renderScorecard(res) {
    document.getElementById('resTitle').innerText = res.testTitle;
    document.getElementById('resCategory').innerText = `${res.categoryName} • Status: ${res.status}`;

    document.getElementById('resScore').innerText = `${res.score} / ${res.totalMarks}`;
    document.getElementById('resAccuracy').innerText = `${res.accuracy}%`;
    document.getElementById('resPercentage').innerText = `${res.percentage}%`;

    const mins = Math.floor(res.timeTakenSeconds / 60);
    const secs = res.timeTakenSeconds % 60;
    document.getElementById('resTimeTaken').innerText = `${mins}m ${secs}s`;

    document.getElementById('resTotalQ').innerText = res.totalQuestions;
    document.getElementById('resCorrect').innerText = res.correctAnswers;
    document.getElementById('resWrong').innerText = res.wrongAnswers;
    document.getElementById('resUnattempted').innerText = res.unattemptedQuestions;

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
            badgeHtml = '<span class="badge badge-success"><i class="fas fa-check"></i> Correct (+' + (q.marks || 2) + ')</span>';
        } else {
            badgeHtml = '<span class="badge badge-danger"><i class="fas fa-times"></i> Incorrect (0)</span>';
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
                <h4 style="margin:0; color:#1e293b;">Question ${q.questionOrder}</h4>
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
