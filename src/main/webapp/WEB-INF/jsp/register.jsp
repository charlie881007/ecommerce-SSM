<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>註冊</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css">
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/register.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

<h1>註冊</h1>
<form:form onsubmit="return validateForm()" action="${pageContext.request.contextPath}/register" method="post"
           modelAttribute="registerForm">
    Email：<form:input id="email" type="email" name="email" path="email"/><span id="email_error"
                                                                               class="error"><form:errors path="email"
                                                                                                          cssClass="error"/>${emailMsg}</span><span
        id="email_check" style="color: #4CAF50"></span><br/>
    驗證碼：<form:input id="code" type="text" name="code" path="code"/><span id="code_error" class="error"><form:errors
        path="code" cssClass="error"/>${codeMsg}</span><input type="button" value="發送" id="sendCodeBtn"/><br/>
    密碼：<input id="pw1" type="password" name="password"><span id="password_error" class="error"><form:errors
        path="password" cssClass="error"/></span><br/>
    再次輸入密碼：<input id="pw2" type="password"><span id="confirm_password_error" class="error"></span><br/>
    姓：<form:input id="first_name" type="text" name="last_name" path="lastName"/><span id="first_name_error"
                                                                                       class="error"><form:errors
        path="firstName" cssClass="error"/></span><br/>
    名：<form:input id="last_name" type="text" name="first_name" path="firstName"/><span id="last_name_error"
                                                                                        class="error"><form:errors
        path="lastName" cssClass="error"/></span><br/>
    <input type="submit" value="註冊">
</form:form>

</body>

</html>
