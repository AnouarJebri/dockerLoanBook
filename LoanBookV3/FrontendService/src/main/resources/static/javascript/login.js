$(document).ready(function() {
    const backendUrl="http://localhost/api/auth";
    $("#loginForm").on('submit', function(event) {
        event.preventDefault(); // Prevent default form submission

        const email = $('#email').val();
        const password = $('#password').val();

        const loginData = {
            email: email,
            password: password
        };

        $.ajax({
            url: backendUrl+"/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(loginData),
            success: function(response) {
                const message = response.message;
                const role = response.role;
                const name = response.name;
                const userId = response.id;
                console.log(message);
                // Store the JWT token and the username in localStorage
                localStorage.setItem('userName', name);
                localStorage.setItem('id', userId);
                localStorage.setItem('role',role);
                document.cookie = "userId=" + userId + "; path=/";
                // Redirect based on role
                if (role === 'ADMIN') {
                    window.location.href = 'homeA';
                } else if (role === 'SUBSCRIBER') {
                    window.location.href = 'homeS';
                } else {
                    $('#login-message').text(message);
                }
            },
            error: function(xhr, status, error) {
                $('#login-message').text('Login failed. Please try again.');
            }
        });
    });
});