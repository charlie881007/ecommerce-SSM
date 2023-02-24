<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css">
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/login.js"></script>

    <title>登入</title>

</head>
<body>

<jsp:include page="header.jsp"/>

<h1>登入</h1>
<c:choose>
    <c:when test="${redirectURL == null}">
        <form:form onsubmit="return validateForm()" action="${pageContext.request.contextPath}/login" method="post"
                   modelAttribute="loginForm">
            Email：<form:input id="email" type="email" name="email" path="email"/><br/>
            密碼：<input id="password" type="password" name="password"/><br/>
            <input type="checkbox" id="remember"/> 記住我
            <input type="submit" value="登入"><span class="error">${errorMsg}</span>
        </form:form>
    </c:when>
    <c:otherwise>
        <form:form onsubmit="return validateForm()"
                   action="${pageContext.request.contextPath}/login?redirect=${redirectURL}" method="post"
                   modelAttribute="loginForm">
            Email：<form:input id="email" type="email" name="email" path="email"/><br/>
            密碼：<input id="password" type="password" name="password"/><br/>
            <input type="checkbox" id="remember"/> 記住我
            <input type="submit" value="登入"><span class="error">${errorMsg}</span>
        </form:form>
    </c:otherwise>
</c:choose>

<%--    <form:form onsubmit="return validateForm()" action="${pageContext.request.contextPath}/login" method="post" modelAttribute="loginForm">--%>
<%--        Email：<form:input id="email" type="email" name="email" path="email"/><br/>--%>
<%--        密碼：<input id="password" type="password" name="password"/><br/>--%>
<%--        <input type="checkbox" id="remember"/> 記住我--%>
<%--        <input type="submit" value="登入"><span class="error">${errorMsg}</span>--%>
<%--    </form:form>--%>
</body>
</html>
