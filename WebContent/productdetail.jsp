<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

	<c:if test="${ sessionScope.products != null } ">
		<jsp:include page="categories.jsp"></jsp:include>
	</c:if>
	<br>


	<div id="item">
		<h1 style="font-size: 24px;">${ productCurrent.name }</h1>
		<br /> <img src="${ productCurrent.image }" alt="oops no image here"
			width="311" height="319" /><br /> <span> <fmt:formatNumber
				type="number" pattern="#####.##" value="${ productCurrent.price }" />лв.
		</span>
	</div>
	<div class="description">
		<label>Описание:</label>
		<p>${ productCurrent.description }</p>
		<br>

		<c:if
			test="${( (sessionScope.user == null) || (sessionScope.user != null && (!sessionScope.isFavorite)))}">
			<form action="addFavorite" method="post">
				<button type="submit" name='addFavorit'>Добави в любими</button>
			</form>
		</c:if>
		<c:if test="${ sessionScope.user != null &&  sessionScope.isFavorite}">
			<form action="removeFavorit" method="post">
				<button type="submit" name="removeFavorit">Премахни от
					любими</button>
			</form>
		</c:if>

		<form action="addrating" method="post">
			Rating<select name='rating'>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select>
			<button type="submit" />
			Гласувай
			</button>
		</form>
		<span style="padding-left: 68px;"></span>
		<form action="addInCart" method="post">
			<button type="submit" name="productId">Добави в количка</button>
			<!-- input type="image" width="40" height="40"
				src="D:\pisi_images\buttons\shopping_cart_racing.png"
				alt="Submit Form" name='addInCart' /-->
		</form>

	</div>



</body>
</html>