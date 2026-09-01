document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');
    const errorMsg = document.getElementById('errorMsg');

    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const btn = loginForm.querySelector('button');
            btn.disabled = true;
            btn.textContent = 'Loading...';

            try {
                const response = await api.post('/auth/login', { email, password });
                localStorage.setItem('token', response.token);
                redirectBasedOnRole(response.role);
            } catch (err) {
                errorMsg.textContent = err.message;
                errorMsg.style.display = 'block';
                btn.disabled = false;
                btn.textContent = 'Login';
            }
        });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const name = document.getElementById('name').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const role = document.getElementById('role').value;
            const btn = registerForm.querySelector('button');
            btn.disabled = true;
            btn.textContent = 'Loading...';

            try {
                const response = await api.post('/auth/register', { name, email, password, role });
                localStorage.setItem('token', response.token);
                redirectBasedOnRole(response.role);
            } catch (err) {
                errorMsg.textContent = err.message;
                errorMsg.style.display = 'block';
                btn.disabled = false;
                btn.textContent = 'Register';
            }
        });
    }
});

function redirectBasedOnRole(role) {
    if (role === 'ADMIN') {
        window.location.href = '/admin/dashboard.html';
    } else if (role === 'INSTRUCTOR') {
        window.location.href = '/instructor/dashboard.html';
    } else {
        window.location.href = '/student/dashboard.html';
    }
}
