<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="login.jsp">
		</c:redirect>
	</c:if>

	<jsp:include page="header.jsp"></jsp:include>

	<h2>Your previous orders:</h2>

	<a href="sortOrders?sort=desc"><button>Newest to oldest</button></a>
	<a href="sortOrders?sort=asc"><button>Oldest to newest</button></a>
	<hr>
	<c:if test="${ sessionScope.orders != null }">
		<c:forEach items="${ sessionScope.orders }" var="order">
			<h4>${ order.datetime }</h4>
			<table border="1">
				<c:forEach items="${ order.products }" var="productEntry">
					<c:set var="product" value="${productEntry.key}" />
					<tr>
						<td>Снимка</td>
						<td>Име</td>
						<td>Описание</td>
						<td>Марка</td>
						<td>Цена</td>
						<td>Количество</td>
						<td>Обща цена за продукта</td>
						<td>Намаление</td>
					</tr>
					<tr>
						<td>${product.image }</td>
						<td>${product.name }</td>
						<td>${product.description }</td>
						<td>${product.brand }</td>
						<td>${product.price }</td>
						<td>${productEntry.value }</td>
						<td>смятай</td>
						<td>${product.discount }</td>
					</tr>
				</c:forEach>
				<h4>${ order.finalPrice }</h4>
			</table>
			<hr>
		</c:forEach>
	</c:if>

	<h1>Please update all fields:</h1>
	<form action="register" method="post">
		First name<input type="text" name="first_name" required><br>
		Last name<input type="text" name="last_name" required><br>
		Email<input type="email" name="email" required><br>
		Password<input type="password" name="password" required><br>
		Gender: <input type="radio" name="gender" value="true" checked>
		Male <input type="radio" name="gender" value="false"> Female<br>
		<input type="submit" value="Update">
	</form>


</body>
</html>