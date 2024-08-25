document.getElementById('signup-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value.trim().toLowerCase();

    // Simple validation for demonstration
    if (name === "" || email === "" || password === "" || role === "") {
        alert("Please fill in all fields.");
    } else {
        alert(`Welcome, ${name}! Your account has been created.`);
        
        // Redirect to the login page after successful signup
        window.location.href = 'login.html';
    }
});

document.getElementById('login-link').addEventListener('click', function(event) {
    event.preventDefault();
    window.location.href = 'login.html';
});
