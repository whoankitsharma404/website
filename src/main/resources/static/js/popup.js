function openPopup(id) {
        document.getElementById(id).style.display = 'flex';
    }

    function closePopup(id) {
        document.getElementById(id).style.display = 'none';
    }

    function switchToRegister() {
        closePopup('login-popup');
        openPopup('register-popup');
    }

    function switchToLogin() {
        closePopup('register-popup');
        openPopup('login-popup');
    }