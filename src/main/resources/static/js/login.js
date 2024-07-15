document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const data = { email: email, password: password };

    axios.post('/login', data)
        .then(response => {
            if (response.status === 200) {
                const userData = JSON.stringify(response.data);
                localStorage.setItem("user", userData);
                console.log(response.data);
                window.location.href = '/dashboard';
            } else {
                console.error('Error:', response.statusText);
                if (response.data && response.data.error) {
                    alert(response.data.error);
                } else {
                    alert('Invalid credentials. Please try again.');
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again.');
        });
});
