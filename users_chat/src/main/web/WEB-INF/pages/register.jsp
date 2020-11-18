<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="/css/main.css" type="text/css">
    <script src="/js/jquery.js" type="text/javascript"></script>
    <script src="/js/main.js" type="text/javascript"></script>
</head>
<body>
<div class="login-page">
    <div class="form">
        <form method="post" action="register" enctype="multipart/form-data">
            <input id="name-input" type="text"
                   placeholder="<c:out value="${requestScope.errorName != null ? requestScope.errorName : 'Name'}"/>"
                   name="name"
                   class="<c:out value="${requestScope.errorName != null ? 'danger-placeholder' : ''}"/>"
                   required/>
            <input id="surname-input" type="text"
                   placeholder="<c:out value="${requestScope.errorSurname != null ? requestScope.errorSurname : 'Surname'}"/>"
                   class="<c:out value="${requestScope.errorSurname != null ? 'danger-placeholder' : ''}"/>"
                   name="surname" required/>
            <p hidden="hidden" class="danger">Wrong Email Format!</p>
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
            <input id="confirm-password-input" type="password"
                   placeholder="<c:out value="${requestScope.errorConfirmPassword != null ? requestScope.errorConfirmPassword : 'Confirm Password'}"/>"
                   name="confirmPassword"
                   class="<c:out value="${requestScope.errorConfirmPassword != null ? 'danger-placeholder' : ''}"/>"
                   required/>
            <p>Select Profile Image</p>
            <input type="file" name="file" accept="image/*">
            <button onclick="doRegister()">create</button>
            <c:if test="${requestScope.userExist != null}">
                <p class="danger">
                    <c:out value="${requestScope.userExist}"/>
                </p>
            </c:if>
            <c:if test="${requestScope.globalError != null}">
                <p class="danger">
                    Server can't process your request, Please try again!
                </p>
            </c:if>
            <p class="message">Already registered? <a href="login">Sign In</a></p>
        </form>
    </div>
</div>
</body>
</html>
