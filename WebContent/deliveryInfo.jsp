<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
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
	 <!-- will be made with AJAX -->>
			<c:if test="${requestScope.deliveries != null &&  not empty requestScope.deliveries }">
				<form action="createOrder" method="post">
					Предишни доставки <select name='deliveries'>
											<c:forEach items="${requestScope.deliveries}" var="delivery">
												<option value="${delivery.deliveryInfoId}">${delivery.address}</option>
											</c:forEach>
									 </select> <br> 
									 <!--  input type="submit" name="Потвърди"-->
									
				</form>
			</c:if>
			
	
	<div align="center">
		<form action="createOrder" method="post">
			<h3 align="center">Получател</h3>
			<!--  form action="getNamesFromUser" method="POST"
				style="text-align: center;">
				<br> Вземи от профила<input type="radio">
			</form-->
			Име <input type="text" name="firstName" value="${ delivery.recieverFirstName }" required><br>
			Фамилия <input type="text" name="lastName" value="${ delivery.recieverLastName }" required><br>
			Телефон <input type="text" name="phone" value="${ delivery.recieverPhone }" required><br>

			<h3 align="center">Адрес</h3>
				Град <select name='city'>
						<c:forEach items="${requestScope.cities}" var="city">
							<option value="${city}">${city}</option>
						</c:forEach>
					</select> <br> 
				Пощенски код <input type ="number" name="zip" min="1000" max ="9999" value="${ delivery.zipCode }" required> <br> 				
				Адрес <input type ="text" name="address" value="${ delivery.address }" required> <br>
				Бележка <input type="text" name="note" value="${ delivery.notes }"> <br> 
			<input type="submit" value="Потвърди">
		</form>
	</div>


</body>
</html>