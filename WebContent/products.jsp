<%@page import="model.dao.AnimalDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
	<body>		
				
		<a href="products?animal=aqua"><button>Aquaristics</button></a>
		<a href="products?animal=cat"><button>Cats</button></a>
		<a href="products?animal=dog"><button>Dogs</button></a>
		<a href="products?animal=little"><button>Little Fellows </button></a>
		<a href="products?animal=bird"><button>Birds</button></a>
		<a href="products?animal=reptile"><button>Reptiles</button></a>
		
		<c:if test="${ sessionScope.products != null }">
			<c:forEach items="${ sessionScope.products }" var="products">
				<h4> ${ products.key } </h4>
				<table border="1">
					<c:forEach items="${ products.value }" var="pro">
						<tr>
							<td>${pro.name }</td>
							<td>${pro.description }</td>
							<td>${pro.price }</td>
							<td>${pro.brand }</td>
							<td>${pro.rating }</td>
						</tr>
					</c:forEach>
				</table>
				<hr>
			</c:forEach>
		</c:if>
		
	</body>
</html>