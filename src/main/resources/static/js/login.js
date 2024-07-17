document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const data = { email: email, password: password };

        axios.post('/login', data)
            .then(response => {
                if (response.status === 200) {
                    const token = response.data.token;  // Get the token from response
                    localStorage.setItem("token", token);  // Store the token
                    axios.get('/dashboard', {
                            headers: {
                                'Authorization': 'Bearer ' + token
                            }
                        })
                        .then(response => {
                            console.log('Received dashboard HTML:', response.data);
                            window.location.href = '/dashboard';

                        })
                        .catch(error => {
                            console.error('Error fetching dashboard data:', error);
                            alert('An error occurred while fetching dashboard data.');
                        });
                } else {
                    console.error('Error:', response.statusText);
                    alert(response.data.error || 'Invalid credentials. Please try again.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred. Please try again.');
            });
    });
});
