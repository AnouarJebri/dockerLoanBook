$(document).ready(function() {
    const backendUrl="http://localhost/api/auth"
    const LocalUrl = '/api/frontend';
    $("#registerForm").submit(function(event) {
        event.preventDefault(); // Prevent default form submission

        const formData = {
            nom: $("#nom").val(),
            prenom: $("#prenom").val(),
            email: $("#email").val(),
            password: $("#password").val()
        };

        $.ajax({
            type: "POST",
            url: backendUrl+"/signup",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function(response) {
                alert("Success: " + response);
                window.location.href = LocalUrl+'/login';
            },
            error: function(error) {
                alert("Error: " + error.responseText);
            }
        });
    });
});