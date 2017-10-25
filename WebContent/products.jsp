<%@page import="model.dao.AnimalDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include><br>
	<br>

	<c:if test="${ sessionScope.products != null }">

		<jsp:include page="categories.jsp"></jsp:include>

		<c:forEach items="${ sessionScope.products }" var="products">
			<h4>${ products.key }</h4>
			<table border="1">
				<c:forEach items="${ products.value }" var="pro">

					<tr>
						<td><a href="productdetail?productId=${pro.id}">${pro.name }</a></td>
						<td>${pro.description }</td>
						<td>${pro.price }лв.</td>
						<c:if test="${pro.rating != 0 }">
							<td>${pro.rating }</td>
						</c:if>
						<c:if test="${pro.rating == 0 }">
							<td>No rating</td>
						</c:if>
						<td>${pro.rating }</td>
					</tr>

				</c:forEach>
			</table>
			<hr>
		</c:forEach>

	</c:if>

</body>
</html>