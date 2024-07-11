const registerForm = document.getElementById('registerForm');
    registerForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = {
            name: document.getElementById('name').value,
            age: document.getElementById('age').value,
            password: document.getElementById('register-password').value,
            email: document.getElementById('register-email').value
        };

        axios.post('/register', formData)
            .then(response => {
                alert('Registration successful');
               window.location.href = '/';
            })
            .catch(error => {
                alert('Registration failed:', error);
            });
    });