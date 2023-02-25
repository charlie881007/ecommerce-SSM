<%--
  Created by IntelliJ IDEA.
  User: Yu-Hsin Lin
  Date: 2023/2/16
  Time: 下午 09:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
<head>
    <title>訂單歷史</title>

    <link href="${pageContext.request.contextPath}/css/orders.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/orders.js"></script>

</head>
<body>
<jsp:include page="header.jsp"/>

<h1>訂單紀錄</h1>
<table>
    <thead>
    <tr>
        <th>訂單編號</th>
        <th>總金額</th>
        <th>購買日期</th>
        <th>訂單狀態</th>
        <th></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="order" items="${user.orders}">
        <tr>
            <td><a href="${pageContext.request.contextPath}/orders/${order.id}">${order.id}</a></td>
            <td>${order.amount}</td>
            <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${order.status}</td>
            <td>
            <c:if test="${order.status == 'CREATED' or order.status == 'PREPARING'}">
                    <button class="cancel-btn">取消</button>
            </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>

</html>
