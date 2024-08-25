document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value.trim().toLowerCase();

    // Simple validation for demonstration

    //responses

    if(role=='developer'){
        window.location.href = 'Developer.html';
    }
    else{
        if(role=='tester'){
            window.location.href = 'tester2.html';   
        }
        else{
            window.location.href = 'Manager.html';
        }
    }


    

    // Simple validation for demonstration


    // if (email === "" || password === "") {
    //     alert("Please fill in all fields.");
    // } else {
    //     alert("Login successful.");
    //     // Here, you can add logic to send the data to the server or redirect.
    // }
});

document.getElementById('signup-link').addEventListener('click', function(event) {
    event.preventDefault();
    // alert("Redirecting to sign-up page...");
    window.location.href = 'signup.html';

    // Here, you would add logic to redirect to the sign-up page.
});
