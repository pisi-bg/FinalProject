<%--@page import="model.dao.AnimalDao"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include><br>
	<br>

	<c:if test="${ sessionScope.products != null }">
		<jsp:include page="category.jsp"></jsp:include>
	</c:if>
	<br>



	<h1 style="font-size: 24px;">${ product.name }</h1>
	<br>
	<img id="bigpic" src="${ product.image }" title="${ product.name }"
		alt="oops" width="650" height="650">
	<br>

	<p>
		<label>Производител:</label> ${ product.brand }
	</p>
	<br>
	<img id="bigpic" src="${ product.brandImage }"
		title="${ product.name }" alt="oops" width="650" height="650">
	<br>
	<p>
		<label>Описание:</label> ${ product.description }
	</p>
	<br>
	<p>
		<label>Цена:</label> ${ product.price }
	</p>
	<br>
	<!--p>	<c:import url="RatingStars.html"/> </p-->


	<!--  div style="padding: 10px; border: 3px solid #50be07; background-color: #FCFCFC; float: left; width: 100%;"-->
	<form name="cart" action="" method="post">

		<button type="button" name="addFavorite">Добави в любими</button>
		<br>
		<button type="button" name="addRating">Гласувай</button>
		<br> Количество<input type="text" name="quantity">
		<button type="submit" name="submitInCart">Добави в количка</button>
		<button type="button" name="back">Назад</button>
		<br>

	</form>
	</div>


</body>
</html>