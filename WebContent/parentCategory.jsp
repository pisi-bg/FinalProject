<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>		
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include><br><br>
	<jsp:include page="categories.jsp"></jsp:include>	
		
	<c:forEach items="${sessionScope.productsForCategory }" var="products">
		<h4>${ products.key }</h4>
		<table border="1">
			<c:forEach items="${ products.value }" var="pro">
				<tr>		
					<td><a href="productdetail?productId=${pro.id}">${pro.name }</a></td>		
					<td> ${ pro.description }</td>
					<td> ${ pro.price }</td>
					<c:if test="${pro.rating != 0 }">
						<td>${pro.rating }</td>
					</c:if>
					<c:if test="${pro.rating == 0 }">
						<td>No rating</td>
					</c:if>
					<td> ${ pro.image }</td>
				</tr>
			</c:forEach>	
		</table>
	</c:forEach>
	
</body>
</html>