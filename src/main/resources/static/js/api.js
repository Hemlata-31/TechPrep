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
            const response = await fetch(`${BASE_URL}${endpoint}`, {
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
