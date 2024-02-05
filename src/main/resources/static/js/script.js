/////REQUIRED/////////////////////////////////////////////////////////
pwShowHide = document.querySelectorAll(".eye-icon"),
    links = document.querySelectorAll(".link");
pwShowHide.forEach(eyeIcon => {
    eyeIcon.addEventListener("click", () => {
        let pwFields = eyeIcon.parentElement.parentElement.querySelectorAll(".password");

        pwFields.forEach(password => {
            if (password.type === "password") {
                password.type = "text";
                eyeIcon.classList.replace("bx-hide", "bx-show");
                return;
            }
            password.type = "password";
            eyeIcon.classList.replace("bx-show", "bx-hide");
        })

    })
})

function togglePasswordVisibility(passwordFieldId) {
    var passwordField = document.getElementById(passwordFieldId);

    if (passwordField.type === "password") {
        passwordField.type = "text";
    } else {
        passwordField.type = "password";
    }
}

document.getElementById('password1').addEventListener('blur', function () {
    this.type = 'password';
});

document.getElementById('password2').addEventListener('blur', function () {
    this.type = 'password';
});


function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value != $("#password").val()) {
        fieldConfirmPassword.setCustomValidity("Passwords do not match!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}

//////////////////////////////////////////