<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="am.gitc.chat.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="am.gitc.chat.model.Message" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="/css/chat.css" type="text/css">
    <meta charset='UTF-8'>
    <meta name="robots" content="noindex">
    <link rel="shortcut icon" type="image/x-icon"
          href="//production-assets.codepen.io/assets/favicon/favicon-8ea04875e70c4b0bb41da869e81236e54394d63638a1ef12fa558a4a835f1164.ico"/>
    <link rel="mask-icon" type=""
          href="//production-assets.codepen.io/assets/favicon/logo-pin-f2d2b6d2c61838f7e76325261b7195c27224080bc099486ddd6dccb469b8e8e6.svg"
          color="#111"/>
    <link rel="canonical" href="https://codepen.io/emilcarlsson/pen/ZOQZaV?limit=all&page=74&q=contact+"/>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,700,300' rel='stylesheet'
          type='text/css'>

    <script src="https://use.typekit.net/hoy3lrg.js"></script>
    <script src='//production-assets.codepen.io/assets/common/stopExecutionOnTimeout-b2a7b3fe212eaa732349046d8416e00a9dec26eb7fd347590fbced3ab38af52e.js'></script>
    <script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>
    <script src="../js/chat.js"></script>
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css'>
    <link rel='stylesheet prefetch'
          href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.2/css/font-awesome.min.css'>
<body>
<div id="current-user-id" hidden="hidden" user-id='<c:out value="${sessionScope.user.id}"/>'></div>
<div id="current-user-name" hidden="hidden"
     user-name='<c:out value="${sessionScope.user.name.concat(' ').concat(sessionScope.user.surname)}"/>'></div>
<div id="frame">
    <div id="sidepanel">
        <div id="profile">
            <div class="wrap">
                <img id="profile-img" src='<c:out value="${sessionScope.user.imageUrl}"/>' class="online" alt=""/>
                <p><c:out value="${sessionScope.user.name.concat(' ').concat(sessionScope.user.surname)}"/>
                </p>
                <i class="fa fa-chevron-down expand-button" aria-hidden="true"></i>
                <a style="color: red" href="/logout">Log Out</a>
            </div>
        </div>
        <div id="search">
            <label for=""><i class="fa fa-search" aria-hidden="true"></i></label>
            <input type="text" placeholder="Search contacts..."/>
        </div>
        <div id="contacts">
            <ul>
                <c:set var="activeUsers" value="${applicationScope.users_activity}"/>
                <c:forEach items="${requestScope.users}" var="cUser" varStatus="status">
                    <li class="contact"
                        onclick="loadMessages(<c:out value="${cUser.id}"/>,
                                '<c:out value="${cUser.name.concat(' ').concat( cUser.surname)}"/>',
                                '<c:out value="${cUser.imageUrl}"/>')">
                        <div class="wrap">
                            <span class="contact-status  <c:out value="${activeUsers[cUser.id] != null && System.currentTimeMillis() - activeUsers[cUser.id].time < 15 * 60 * 1000 ? 'online': 'busy'}"/>"></span>
                            <img src='<c:out value="${cUser.imageUrl}"/>' alt=""/>
                            <div class="meta">
                                <p class="name">
                                    <c:out value="${cUser.name.concat(' ').concat(cUser.surname)}"/>
                                </p>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div id="bottom-bar">
            <a href="/users/download?type=xlsx">Download Users xlsx</a>
            <a href="/users/download">Download Users csv</a>
        </div>
    </div>
    <div class="content">
        <div class="contact-profile">
            <img src='<c:out value="${sessionScope.user.imageUrl}"/>' alt=""/>
            <p>
                <c:out value="${sessionScope.user.name.concat(' ').concat(sessionScope.user.surname)}"/>
            </p>
        </div>
        <div class="messages">
            <ul>
                <c:forEach items="${requestScope.messages}" var="message" varStatus="status">
                    <li class="replies">
                        <img src='<c:out value="${sessionScope.user.imageUrl}"/>' alt=""/>
                        <p>
                            <c:out value="${message.message}"/>
                        </p>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="message-input">
            <div class="wrap">
                <input type="text" placeholder="Write your message..."/>
                <i class="fa fa-paperclip attachment" aria-hidden="true"></i>
                <button class="submit" onclick="sendMessage()"><i class="fa fa-paper-plane" aria-hidden="true"></i>
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
