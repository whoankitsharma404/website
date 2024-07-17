document.addEventListener('DOMContentLoaded', function() {
    // Function to get a cookie by name
    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    // On page load, check for the JWT token in cookies
    const token = getCookie('JWT_TOKEN');  // Replace 'JWT_TOKEN' with your actual cookie name
    if (token) {
        fetchDashboardData(token);
    } else {
        window.location.href = '/login';  // Redirect to login if no token
    }
});

// Function to fetch dashboard data
function fetchDashboardData(token) {
    axios.get('/dashboard', {
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => {
        // Handle the dashboard data here
        console.log('Dashboard Data:', response.data);
    })
    .catch(error => {
        console.error('Error fetching dashboard data:', error);
        if (error.response && error.response.status === 401) {
            alert('Session expired. Please log in again.');
            window.location.href = '/login';
        }
    });
}
