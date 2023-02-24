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
    <title>購物車</title>
    <link href="${pageContext.request.contextPath}/css/cart.css" rel="stylesheet">

    <script type="text/javascript">
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/cart.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

<h1>購物車</h1>
<h2>${msg}</h2>
<button class="remove-item" onclick="confirmRemoveClosedListings()" style="float: right; margin-right: 30px">
    刪除已下架商品
</button>
<table>
    <tr>
        <th>物品</th>
        <th>單價</th>
        <th>可購買數量</th>
        <th>數量</th>
        <th>金額</th>
        <th>備註</th>
        <th></th>
    </tr>

    <c:forEach var="cartDetail" items="${user.cart.cartDetails}">
        <c:choose>
            <c:when test="${cartDetail.listing.status == 'OPEN'}">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/listings/${cartDetail.listing.id}">${cartDetail.listing.item.name}</a>
                    </td>
                    <td>NT$${cartDetail.listing.price}</td>
                    <td>${cartDetail.listing.volume}</td>
                    <td><input type="number" value="${cartDetail.quantity}" min="1" max="${cartDetail.listing.volume}"
                               id="listing-${cartDetail.listing.id}"></td>
                    <td class="subtotal">NT$${cartDetail.subtotal}</td>
                    <td>
                        <c:if test="${cartDetail.listing.status == 'CLOSED'}">已下架</c:if>
                    </td>
                    <td>
                        <button class="remove-item" onclick="confirmChoice(${cartDetail.listing.id})">刪除</button>
                    </td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="opacity: 20%">
                    <td>
                        <a href="${pageContext.request.contextPath}/listings/${cartDetail.listing.id}">${cartDetail.listing.item.name}</a>
                    </td>
                    <td>NT$${cartDetail.listing.price}</td>
                    <td>${cartDetail.listing.volume}</td>
                    <td><input readonly type="number" class="closed" value="${cartDetail.quantity}" min="1"
                               max="${cartDetail.listing.volume}" id="listing-${cartDetail.listing.id}"></td>
                    <td class="subtotal">NT$${cartDetail.subtotal}</td>
                    <td>
                        <c:if test="${cartDetail.listing.status == 'CLOSED'}">已下架</c:if>
                    </td>
                    <td>
                        <button class="remove-item" onclick="confirmChoice(${cartDetail.listing.id})">刪除</button>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td class="total">總金額：</td>
        <td class="total">NT$${user.cart.totalPrice}</td>
        <td></td>
    </tr>
</table>
<form method="post" action="${pageContext.request.contextPath}/cart/checkout">
    <c:choose>
        <c:when test="${user.cart.cartDetails.size() != 0}">
            <button>結帳</button>
        </c:when>
        <c:otherwise>
            <button disabled style="background: #cccccc">結帳</button>
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>