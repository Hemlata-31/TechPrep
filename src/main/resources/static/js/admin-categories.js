document.addEventListener('DOMContentLoaded', async () => {
    const user = await checkAuth();
    if (user && user.role !== 'ADMIN') {
        window.location.href = '/login.html';
        return;
    }
    initLogoutBtn();
    await loadHierarchy();
    initModalEvents();
});

async function loadHierarchy() {
    try {
        const container = document.getElementById('hierarchyContainer');
        container.innerHTML = '<p>Loading...</p>';
        const categories = await api.get('/categories');
        
        let html = '<ul class="hierarchy-tree">';
        for (const cat of categories) {
            html += `<li>
                <span class="tree-node"><strong>${cat.name}</strong></span>
                <div class="tree-actions">
                    <button class="btn btn-small" onclick="openModal('edit', 'category', ${cat.id}, '${cat.name}', '${cat.description || ''}')">Edit</button>
                    <button class="btn btn-small btn-danger" onclick="deleteItem('category', ${cat.id})">Delete</button>
                    <button class="btn btn-small" onclick="openModal('add', 'subcategory', null, null, null, ${cat.id})">+ Sub</button>
                </div>`;
            
            const subCats = await api.get(`/categories/${cat.id}/subcategories`);
            if (subCats.length > 0) {
                html += '<ul>';
                for (const sub of subCats) {
                    html += `<li>
                        <span class="tree-node">${sub.name}</span>
                        <div class="tree-actions">
                            <button class="btn btn-small" onclick="openModal('edit', 'subcategory', ${sub.id}, '${sub.name}', '${sub.description || ''}', ${cat.id})">Edit</button>
                            <button class="btn btn-small btn-danger" onclick="deleteItem('subcategory', ${sub.id})">Delete</button>
                            <button class="btn btn-small" onclick="openModal('add', 'topic', null, null, null, ${sub.id})">+ Topic</button>
                        </div>`;
                    
                    const topics = await api.get(`/subcategories/${sub.id}/topics`);
                    if (topics.length > 0) {
                        html += '<div style="margin-left: 20px; color: #555; margin-top: 5px;">&rarr; ';
                        
                        html += topics.map(t => `
                            <span class="tree-node" style="font-size: 0.9em; padding: 2px 5px; margin-bottom: 5px;">
                                ${t.name}
                                <a href="#" onclick="openModal('edit', 'topic', ${t.id}, '${t.name}', '${t.description || ''}', ${sub.id}); return false;">[E]</a>
                                <a href="#" onclick="deleteItem('topic', ${t.id}); return false;" style="color: red;">[D]</a>
                            </span>
                        `).join(' ');
                        
                        html += '</div>';
                    }
                    html += `</li>`;
                }
                html += '</ul>';
            }
            html += `</li>`;
        }
        html += '</ul>';
        container.innerHTML = html;
    } catch (err) {
        console.error(err);
        document.getElementById('hierarchyContainer').innerHTML = '<p class="alert" style="display:block">Error loading hierarchy.</p>';
    }
}

const modal = document.getElementById('itemModal');
const itemForm = document.getElementById('itemForm');
const closeBtn = document.getElementById('closeModal');
const modalTitle = document.getElementById('modalTitle');
const itemIdInput = document.getElementById('itemId');
const itemTypeInput = document.getElementById('itemType');
const parentIdInput = document.getElementById('parentId');
const itemNameInput = document.getElementById('itemName');
const itemDescInput = document.getElementById('itemDesc');

function initModalEvents() {
    document.getElementById('btnAddCategory').addEventListener('click', () => {
        openModal('add', 'category');
    });

    closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    itemForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = itemIdInput.value;
        const type = itemTypeInput.value;
        const parentId = parentIdInput.value;
        const name = itemNameInput.value;
        const desc = itemDescInput.value;
        
        const payload = { name, description: desc };

        try {
            if (id) {
                // Edit
                if (type === 'category') await api.put(`/categories/${id}`, payload);
                else if (type === 'subcategory') await api.put(`/subcategories/${id}`, payload);
                else if (type === 'topic') await api.put(`/topics/${id}`, payload);
            } else {
                // Add
                if (type === 'category') await api.post(`/categories`, payload);
                else if (type === 'subcategory') await api.post(`/subcategories/category/${parentId}`, payload);
                else if (type === 'topic') await api.post(`/topics/subcategory/${parentId}`, payload);
            }
            modal.style.display = 'none';
            loadHierarchy();
        } catch (err) {
            alert('Error: ' + err.message);
        }
    });
}

function openModal(action, type, id = null, name = '', desc = '', parentId = null) {
    modalTitle.textContent = `${action === 'add' ? 'Add' : 'Edit'} ${type}`;
    itemIdInput.value = id || '';
    itemTypeInput.value = type;
    parentIdInput.value = parentId || '';
    itemNameInput.value = name;
    itemDescInput.value = desc;
    modal.style.display = 'flex';
}

async function deleteItem(type, id) {
    if (!confirm(`Are you sure you want to delete this ${type}?`)) return;
    try {
        if (type === 'category') await api.delete(`/categories/${id}`);
        else if (type === 'subcategory') await api.delete(`/subcategories/${id}`);
        else if (type === 'topic') await api.delete(`/topics/${id}`);
        loadHierarchy();
    } catch (err) {
        alert('Error: ' + err.message);
    }
}
