<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="../css/main.css" type="text/css">
    <script src="../js/jquery.js" type="text/javascript"></script>
    <script src="../js/main.js" type="text/javascript"></script>
</head>
<body>
<div class="login-page">
    <div class="form">
        <form id="login-form" method="post" action="login">
            <p hidden="hidden" class="danger">Wrong Email Format!</p>
            <c:if test="${sessionScope.registered != null}">
                <p style="color:blue">You are Successfully Registered!</p>
                <c:remove scope="session" var="registered"/>
            </c:if>
            <c:if test="${requestScope.wrongEmailPassword != null}">
                <p class="danger">
                    <c:out value="${requestScope.wrongEmailPassword}"/>
                </p>
            </c:if>
            <c:if test="${requestScope.globalError != null}">
                <p class="danger">
                    Server can't process your request, Please try again!
                </p>
            </c:if>
            <input id="email-input" type="text"
                   placeholder="<c:out value="${requestScope.errorEmail != null ? requestScope.errorEmail : 'Email'}"/>"
                   name="email"
                   class="<c:out value="${requestScope.errorEmail != null ? 'danger-placeholder' : ''}"/>"
                   required/>
            <input id="password-input" type="password"
                   placeholder="<c:out value="${requestScope.errorPassword != null ? requestScope.errorPassword : 'Password'}"/>"
                   name="password"
                   class="<c:out value="${requestScope.errorPassword != null ? 'danger-placeholder' : ''}"/>"
                   required/>
            <button onclick="doLogin()">Login</button>
            <p class="message">Not registered? <a href="register">Create an account</a></p>
        </form>
    </div>
</div>
</body>
</html>
