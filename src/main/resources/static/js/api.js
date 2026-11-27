const BASE_URL = 'http://localhost:8080/api';

const api = {
    async request(endpoint, options = {}) {
        const token = localStorage.getItem('token');
        const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` }),
            ...options.headers
        };

        try {
            const cleanEndpoint = endpoint.startsWith('/api/') ? endpoint.substring(4) : endpoint;
            const response = await fetch(`${BASE_URL}${cleanEndpoint.startsWith('/') ? cleanEndpoint : '/' + cleanEndpoint}`, {
                ...options,
                headers
            });

            if (response.status === 401 || response.status === 403) {
                // Unauthorized, redirect to login
                localStorage.removeItem('token');
                window.location.href = '/login.html';
                throw new Error('Unauthorized');
            }

            const isJson = response.headers.get('content-type')?.includes('application/json');
            const data = isJson ? await response.json() : await response.text();

            if (!response.ok) {
                const errorMessage = data.message || (typeof data === 'object' ? Object.values(data)[0] : data) || 'An error occurred';
                throw new Error(errorMessage);
            }

            return data;
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    },

    get(endpoint) {
        return this.request(endpoint, { method: 'GET' });
    },
    post(endpoint, body) {
        return this.request(endpoint, { method: 'POST', body: JSON.stringify(body) });
    },
    put(endpoint, body) {
        return this.request(endpoint, { method: 'PUT', body: JSON.stringify(body) });
    },
    delete(endpoint) {
        return this.request(endpoint, { method: 'DELETE' });
    }
};

async function checkAuth() {
    const token = localStorage.getItem('token');
    if (!token) {
        if (!window.location.pathname.endsWith('login.html') && !window.location.pathname.endsWith('register.html') && window.location.pathname !== '/') {
            window.location.href = '/login.html';
        }
        return null;
    }

    try {
        const user = await api.get('/users/me');
        return user;
    } catch (e) {
        return null;
    }
}

function logout() {
    localStorage.removeItem('token');
    window.location.href = '/login.html';
}

function initLogoutBtn() {
    const btn = document.getElementById('logoutBtn');
    if (btn) btn.addEventListener('click', (e) => {
        e.preventDefault();
        logout();
    });
}

// Theme Management
function initTheme() {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-theme');
    }
    
    const themeBtn = document.getElementById('themeToggleBtn');
    if (themeBtn) {
        updateThemeBtn(themeBtn);
        themeBtn.addEventListener('click', (e) => {
            e.preventDefault();
            document.body.classList.toggle('dark-theme');
            const isDark = document.body.classList.contains('dark-theme');
            localStorage.setItem('theme', isDark ? 'dark' : 'light');
            updateThemeBtn(themeBtn);
        });
    }
}

function updateThemeBtn(btn) {
    const isDark = document.body.classList.contains('dark-theme');
    btn.innerHTML = isDark ? '<i class="fas fa-sun"></i> Light Mode' : '<i class="fas fa-moon"></i> Dark Mode';
}

// Unified Student Sidebar across all /student/ pages
function initStudentSidebar() {
    const path = window.location.pathname;
    if (!path.startsWith('/student/')) return;
    const navList = document.querySelector('.sidebar nav ul');
    if (!navList) return;

    const isCategoriesActive = path.includes('/categories.html') || path.includes('/category.html')
        || path.includes('/subcategory.html') || path.includes('/practice.html') || path.includes('/result.html');
    const isCodingActive = path.includes('/coding.html') || path.includes('/coding-problem.html');
    const isMockActive = path.includes('/mock-tests.html') || path.includes('/test-instructions.html') || path.includes('/test-result.html');

    const items = [
        { href: '/student/dashboard.html', icon: 'fas fa-home', label: 'Dashboard', active: path.includes('/dashboard.html') },
        { href: '/student/categories.html', icon: 'fas fa-th-list', label: 'Categories', active: isCategoriesActive },
        { href: '/student/coding.html', icon: 'fas fa-code', label: 'Coding Arena', active: isCodingActive },
        { href: '/student/notes.html', icon: 'fas fa-book-open', label: 'Study Notes', active: path.includes('/notes.html') },
        { href: '/student/progress.html', icon: 'fas fa-chart-line', label: 'Progress', active: path.includes('/progress.html') },
        { href: '/student/leaderboard.html', icon: 'fas fa-trophy', label: 'Leaderboard', active: path.includes('/leaderboard.html') },
        { href: '/student/mock-tests.html', icon: 'fas fa-laptop-code', label: 'Mock Tests', active: isMockActive },
        { href: '/student/history.html', icon: 'fas fa-history', label: 'Practice History', active: path.includes('/history.html') },
        { href: '/student/saved-questions.html', icon: 'fas fa-bookmark', label: 'Saved Questions', active: path.includes('/saved-questions.html') },
        { href: '#', id: 'themeToggleBtn', icon: 'fas fa-moon', label: 'Dark Mode', active: false },
        { href: '#', id: 'logoutBtn', icon: 'fas fa-sign-out-alt', label: 'Logout', active: false }
    ];

    navList.innerHTML = items.map(item => `
        <li><a href="${item.href}"${item.id ? ` id="${item.id}"` : ''}${item.active ? ' class="active"' : ''}><i class="${item.icon}"></i> ${item.label}</a></li>
    `).join('');

    initLogoutBtn();
}

// Unified Admin Sidebar across all /admin/ pages
function initAdminSidebar() {
    const path = window.location.pathname;
    if (!path.startsWith('/admin/')) return;
    const navList = document.querySelector('.sidebar nav ul');
    if (!navList) return;

    const items = [
        { href: '/admin/dashboard.html', icon: 'fas fa-home', label: 'Dashboard', active: path.includes('/dashboard.html') },
        { href: '/admin/categories.html', icon: 'fas fa-th-list', label: 'Categories & Topics', active: path.includes('/categories.html') || path.includes('/questions.html') },
        { href: '/admin/tests.html', icon: 'fas fa-laptop-code', label: 'Manage Mock Tests', active: path.includes('/tests.html') },
        { href: '/admin/content-upload.html', icon: 'fas fa-cloud-upload-alt', label: 'Upload PDFs & DSA', active: path.includes('/content-upload.html') },
        { href: '/admin/ai-generator.html', icon: 'fas fa-robot', label: 'AI Generator', active: path.includes('/ai-generator.html') },
        { href: '#', id: 'themeToggleBtn', icon: 'fas fa-moon', label: 'Dark Mode', active: false },
        { href: '#', id: 'logoutBtn', icon: 'fas fa-sign-out-alt', label: 'Logout', active: false }
    ];

    navList.innerHTML = items.map(item => `
        <li><a href="${item.href}"${item.id ? ` id="${item.id}"` : ''}${item.active ? ' class="active"' : ''}><i class="${item.icon}"></i> ${item.label}</a></li>
    `).join('');

    initLogoutBtn();
}

// Call on load
document.addEventListener('DOMContentLoaded', () => {
    initStudentSidebar();
    initAdminSidebar();
    initTheme();
});
