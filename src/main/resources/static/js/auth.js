const loginTab = document.getElementById('loginTab');
const registerTab = document.getElementById('registerTab');
const loginContent = document.getElementById('loginContent');
const registerContent = document.getElementById('registerContent');

function activateLoginTab() {
    loginTab.classList.add('active');
    registerTab.classList.remove('active');
    loginContent.classList.add('active');
    registerContent.classList.remove('active');
}

function activateRegisterTab() {
    registerTab.classList.add('active');
    loginTab.classList.remove('active');
    registerContent.classList.add('active');
    loginContent.classList.remove('active');
}

loginTab.addEventListener('click', activateLoginTab);
registerTab.addEventListener('click', activateRegisterTab);