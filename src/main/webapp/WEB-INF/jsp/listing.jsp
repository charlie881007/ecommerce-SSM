<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <link href="${pageContext.request.contextPath}/css/listing.css" rel="stylesheet">

    <script type="text/javascript">
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/listing.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>


<c:if test="${listing.status == 'CLOSED'}">
    <h1 style="color: #cc0000">此商品已下架</h1>
</c:if>

<div class="item-detail">
    <img src="${pageContext.request.contextPath}/image/${listing.item.id}.png" class="item-image" alt="Item 1">
    <div class="item-info">
        <h1 class="item-title">${listing.item.name}</h1>
        <p class="item-description">${listing.item.description}</p>

        <c:choose>
            <c:when test="${listing.discountPrice != null}">
                <p class="product-price original-price">NT$ ${listing.originalPrice}</p>
                <p class="product-price">NT$ ${listing.discountPrice}</p>
            </c:when>
            <c:otherwise>
                <p class="product-price">NT$ ${listing.originalPrice}</p>
            </c:otherwise>
        </c:choose>

        <%--    <form class="item-form" method="post" action="${pageContext.request.contextPath}/addToCart">--%>
        <div class="item-form">
            <c:if test="${listing.status != 'CLOSED'}">
                <input type="hidden" value="${listing.id}" id="listingId">
                <input type="number" value="1" min="1" max="${listing.volume}" id="qtyInput"
                       class="item-quantity"/><span>還剩<span id="maxQty">${listing.volume}</span>件</span>&nbsp&nbsp
                <button class="item-button" id="submitBtn">加入購物車</button>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>