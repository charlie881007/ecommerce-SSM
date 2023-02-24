<%--
  Created by IntelliJ IDEA.
  User: Yu-Hsin Lin
  Date: 2023/2/21
  Time: 下午 06:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet">


<c:choose>
    <c:when test="${user != null}">
        <div class="navbar">
            <div class="navbar-left">
                <a href="${pageContext.request.contextPath}">Ecommerce</a>
            </div>
            <div class="navbar-right">
                <a href="${pageContext.request.contextPath}/cart">購物車</a>
                &nbsp; &nbsp; &nbsp;
                <a href="${pageContext.request.contextPath}/orders">查看訂單</a>
                &nbsp; &nbsp; &nbsp;
                <a href="${pageContext.request.contextPath}/logout">登出</a>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="navbar">
            <div class="navbar-left">
                <a href="${pageContext.request.contextPath}">Ecommerce</a>
            </div>
            <div class="navbar-right">
                <a href="${pageContext.request.contextPath}/login">登入</a>
                &nbsp; &nbsp; &nbsp;
                <a href="${pageContext.request.contextPath}/register">註冊</a>
            </div>
        </div>
    </c:otherwise>
</c:choose>
