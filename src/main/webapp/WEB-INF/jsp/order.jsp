<%--
  Created by IntelliJ IDEA.
  User: Yu-Hsin Lin
  Date: 2023/2/15
  Time: 下午 06:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <title>訂單詳情</title>

    <link href="${pageContext.request.contextPath}/css/order.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>

<h1>訂單詳情</h1>
<h2>訂單編號：${order.id}</h2>
<table>
    <tr>
        <th>物品</th>
        <th>單價</th>
        <th>數量</th>
        <th>金額</th>
    </tr>

    <c:forEach var="cartDetail" items="${order.cart.cartDetails}">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/listings/${cartDetail.listing.id}">${cartDetail.listing.item.name}</a>
            </td>
            <td>NT$${cartDetail.listing.price}</td>
            <td>${cartDetail.quantity}</td>
            <td>NT$${cartDetail.subtotal}</td>
        </tr>
    </c:forEach>
    <tr>
        <td></td>
        <td></td>
        <td class="total">總金額：</td>
        <td class="total">NT$${order.cart.totalPrice}</td>
    </tr>
</table>
</body>
</html>