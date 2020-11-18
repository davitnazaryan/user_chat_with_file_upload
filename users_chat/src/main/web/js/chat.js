$(document).ready(function () {
    $(".messages").animate({
        scrollTop: $(document).height()
    }, "fast");

    currentSenderId = Number($('#current-user-id').attr('user-id'));
    currentReceiverId = Number($('#current-user-id').attr('user-id'));
    currentUserName = $('#current-user-name').attr('user-name');
    setInterval(function () {
        updateUsers();
    }, 3000);
    setInterval(function () {
        loadMessages(currentReceiverId, currentUserName, currentImageUrl)
    }, 3000);
});

function createSenderMessageTemplate(message, imageUrl) {
    var template = '<li class="replies">\n' +
        '             <img src="' + imageUrl + '" alt=""/>\n' +
        '             <p>' + message + '</p>\n' +
        '           </li>';
    return template;
}

function createReceiverMessageTemplate(message, imageUrl) {
    var template = '<li class="sent">\n' +
        '             <img src="' + imageUrl + '" alt=""/>\n' +
        '             <p>' + message + '</p>\n' +
        '           </li>';
    return template;
}

var currentSenderId;
var currentReceiverId;
var currentUserName;
var currentImageUrl;

function loadMessages(receiverId, userName, imageUrl) {
    if (event) {
        event.stopPropagation();
    }
    currentReceiverId = receiverId;
    currentUserName = userName;
    currentImageUrl = imageUrl;
    $.ajax({
        type: "POST",
        url: '/messages',
        data: {senderId: currentSenderId, receiverId: receiverId},
        success: function (data) {
            fillMessageArea(data, userName, imageUrl, receiverId);
        }
    });
    return false;
}

function sendMessage() {
    var message = $('.message-input').find('input').val().trim();
    $('.message-input').find('input').val('');
    if (message.length > 0) {
        $.ajax({
            type: "POST",
            url: '/createMessage',
            data: {senderId: currentSenderId, receiverId: currentReceiverId, message: message},
            success: function (data) {
                loadMessages(currentReceiverId, currentUserName, currentImageUrl);
            }
        });
    }
}

function fillMessageArea(data, userName, receiverImgUrl) {
    var senderImgUrl = $('#profile-img').attr('src');
    $('.contact-profile').find('img').attr('src', receiverImgUrl);
    $('.contact-profile').find('p').text(userName);

    var messagesContainer = $('.messages').find('ul');
    messagesContainer.empty();
    data.forEach(function (value) {
        if (value['sender_id'] === currentSenderId) {
            messagesContainer.append(createSenderMessageTemplate(value['message'], senderImgUrl));
        } else {
            messagesContainer.append(createReceiverMessageTemplate(value['message'], receiverImgUrl));
        }
    });
}

function updateUsers() {
    var usersContainer = $('#contacts').find('ul');
    $.ajax({
        type: "POST",
        url: '/usersList',
        success: function (data) {
            var template = '';
            data.forEach(function (userData) {
                template += createUserViewTemplate(userData);
            });
            usersContainer.empty();
            usersContainer.append(template);
        }
    });
}

function createUserViewTemplate(userData) {
    var template = ' <li class="contact"  onclick="loadMessages(' + userData['sender_id'] + ',\'' + userData['full_name'] + '\',\'' + userData['image_url'] + '\')">\n' +
        '                    <div class="wrap">\n' +
        '                        <span class="contact-status ' + (userData['active'] ? 'online' : 'busy') + '"></span>\n' +
        '                        <img src="' + userData["image_url"] + '" alt=""/>\n' +
        '                        <div class="meta">\n' +
        '                            <p class="name">\n' +
        userData['full_name'] +
        '                            </p>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </li>';
    return template;
}



