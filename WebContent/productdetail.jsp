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
		<h1 style="font-size: 24px;">${ product.name }</h1>
		<br />
		<div class="big_view">
			<img src="${ product.image }" alt="oops no image here" width="311"
				height="319" /><br /> <span> <fmt:formatNumber
					type="number" pattern="#####.##" value="${ product.price }" /> лв.
			</span>
		</div>

	</div>
	<div class="description">
		<label>Описание:</label>
		<p>${ product.description }</p>

		<form name="cart" action="" method="post">
			<br> <br> Количество<input type="text" name="quantity">
			<button type="submit" name="submitInCart" >Добави в количка</button>
			<button type="submit" name = "addInFavorite">Добави в любими</button>
			<button type="submit" name = "addRaiting">Добави рейтинг</button>
		</form>
		<br>
		<button type="button" name="back">Назад</button>
		<br>

	</div>



</body>
</html>