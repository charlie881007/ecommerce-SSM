<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

    <title>商品</title>
    <link href="${pageContext.request.contextPath}/css/listings.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>

</head>
<body>
<jsp:include page="header.jsp"/>

<div class="product-listing">
    <!-- Loop over an array of products and display each one -->
    <c:forEach var="listing" items="${listings}">
        <a href="${pageContext.request.contextPath}/listings/${listing.id}" class="product-card">
            <img src="${pageContext.request.contextPath}/image/${listing.item.id}.png" class="product-image"
                 alt="Product 3">
            <h2 class="product-title">${listing.item.name}</h2>
            <c:choose>
                <c:when test="${listing.discountPrice != null}">
                    <p class="product-price original-price">NT$ ${listing.originalPrice}</p>
                    <p class="product-price">NT$ ${listing.discountPrice}</p>
                </c:when>
                <c:otherwise>
                    <p class="product-price">NT$ ${listing.originalPrice}</p>
                </c:otherwise>
            </c:choose>
            <p class="product-description">${listing.item.description}</p>
        </a>
    </c:forEach>

    <%-- 填充佔位商品卡 --%>
    <c:if test="${listings.size() % 3 != 0}">
        <c:forEach var="i" begin="1" end="${3-(listings.size() % 3)}">
            <a href="#" class="product-card product-hidden">
            </a>
        </c:forEach>
    </c:if>
</div>


<div class="pagination">

    <c:choose>
        <c:when test="${pageInfo.isFirstPage == false}">
            <a href="${pageContext.request.contextPath}/listings?page=${pageInfo.pageNum-1}">&laquo;</a>
        </c:when>
        <c:otherwise>
            <span>&laquo;</span>
        </c:otherwise>
    </c:choose>

    <c:forEach var="pageNum" items="${pageInfo.navigatepageNums}">
        <c:choose>
            <c:when test="${pageNum == pageInfo.pageNum}">
                <a class="active" href="${pageContext.request.contextPath}/listings?page=${pageNum}">${pageNum}</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/listings?page=${pageNum}">${pageNum}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:choose>
        <c:when test="${pageInfo.isLastPage == false}">
            <a href="${pageContext.request.contextPath}/listings?page=${pageInfo.pageNum+1}">&raquo;</a>
        </c:when>
        <c:otherwise>
            <span>&raquo;</span>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>