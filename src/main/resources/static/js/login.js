document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const data = { email: email, password: password };
        console.log(data);

        axios.post('/login', data)
            .then(response => {
                if (response.status === 200) {
                    window.location.href = '/';
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