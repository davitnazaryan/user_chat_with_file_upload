function doLogin() {
    event.preventDefault();
    var isOk = true;
    var emailInput = $('#email-input');
    if (isEmpty(emailInput.val())) {
        emailInput.addClass('danger-placeholder');
        emailInput.attr('placeholder', 'Email is Required!');
        isOk = false;
    } else if (!validateEmail(emailInput.val().trim())) {
        emailInput.prev('p').removeAttr('hidden');
        isOk = false;
    }

    var passwordInput = $('#password-input');
    if (isEmpty(passwordInput.val())) {
        passwordInput.addClass('danger-placeholder');
        passwordInput.attr('placeholder', 'Password is Required!');
        isOk = false;
    }
    if (isOk) {
        $('#login-form').submit();
    }
}

function doRegister() {
    event.preventDefault();
    var isOk = true;
    var nameInput = $('#name-input');
    if (isEmpty(nameInput.val())) {
        nameInput.addClass('danger-placeholder');
        nameInput.attr('placeholder', 'Name is Required!');
        isOk = false;
    }

    var surnameInput = $('#surname-input');
    if (isEmpty(surnameInput.val())) {
        surnameInput.addClass('danger-placeholder');
        surnameInput.attr('placeholder', 'Surname is Required!');
        isOk = false;
    }

    var emailInput = $('#email-input');
    if (isEmpty(emailInput.val())) {
        emailInput.addClass('danger-placeholder');
        emailInput.attr('placeholder', 'Email is Required!');
        isOk = false;
    } else if (!validateEmail(emailInput.val().trim())) {
        emailInput.prev('p').removeAttr('hidden');
        isOk = false;
    } else {
        emailInput.prev('p').attr('hidden', 'hidden');
    }

    var passwordInput = $('#password-input');
    if (isEmpty(passwordInput.val())) {
        passwordInput.addClass('danger-placeholder');
        passwordInput.attr('placeholder', 'Password is Required!');
        isOk = false;
    } else {
        var confirmPasswordInput = $('#confirm-password-input');
        if (passwordInput.val() !== confirmPasswordInput.val()) {
            confirmPasswordInput.addClass('danger-placeholder');
            confirmPasswordInput.attr('placeholder', 'Password Do Not Match!');
            confirmPasswordInput.val('');
            isOk = false;
        }
    }

    if (isOk) {
        passwordInput.parents('form').submit();
    }
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

function isEmpty(text) {
    return !text || text.trim().length === 0;
}


function sendMessage(senderId, receiverId, userName) {
    var message = $('#text-area').val().trim();
    $('#text-area').val('');
    $.ajax({
        type: "POST",
        url: '/createMessage',
        data: {senderId: senderId, receiverId: receiverId, message: message},
        success: function (data) {
            loadMessages(senderId, receiverId, userName);
        }
    });
}
