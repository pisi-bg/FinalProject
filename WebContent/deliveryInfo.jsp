<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include><br>
	<br>
	
	<h3 align="center">Информация за доставка</h3>
	<br>
	<div align="center">
		<form action="createOrder" method="POST" style="text-align: center;" enctype="multipart/form-data">
			<h3 align="center">Получател</h3>
			<!--  form action="getNamesFromUser" method="POST"
				style="text-align: center;">
				<br> Вземи от профила<input type="radio">
			</form-->
			Име <input type="text" name="firstName"
				value="${ requestScope.user.firstName }" required><br>
			Фамилия <input type="text" name="lastName"
				value="${ requestScope.user.lastName }" required><br>
			Телефон<input type="text" name="phone" required><br>

			<h3 align="center">Адрес</h3>
			Град<select name='city'>
				<c:forEach items="${requestScope.cities}" var="city">
					<option value="${city}">${city}</option>
				</c:forEach>
			</select> <br> ПК<input type="text" name="zip" required><br>
			Адрес<input type="text" name="address" required><br>
			Бележка <input type="text" name="note"> <br> <input
				type="submit" value="Потвърди">
		</form>
	</div>


</body>
</html>