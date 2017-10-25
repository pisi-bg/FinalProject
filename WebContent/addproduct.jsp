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
	 <c:if test="${ !sessionScope.user.isAdmin()}">	 
		<c:redirect url="index.jsp"></c:redirect>
	</c:if> 
	
	<jsp:include page="header.jsp"></jsp:include>

	<form action="addProduct" method="post" enctype="multipart/form-data" accept-charset="UTF-8 ">
		Product name: <input type= "text" name= "name"  required><br>
		
		Animal type: 
		<input type="radio" name="animal" value="АКВАРИСТИКА">Акваристика
		<input type="radio" name="animal" value="КОТКИ">Котки
		<input type="radio" name="animal" value="КУЧЕТА">Кучета
		<input type="radio" name="animal" value="МАЛКИ ЖИВОТНИ">Малки животни
		<input type="radio" name="animal" value="ПТИЦИ">Птици
		<input type="radio" name="animal" value="ТЕРАРИСТИКА">Тераристика <br>
			
		Product Category <select name="category"  required>
		
					<!-- More to be added -->
					
			<option value="Консервна храна" >Консервна храна</option>
			<option value="Суха храна">Суха храна</option>
			<option value="Четки и гребени" >Четки и гребени</option>
			<option value="Транспорт" >Транспорт</option>
			<option value="Поводи и каишки">Поводи и каишки</option>
			<option value="Дрехи">Дрехи</option>
		</select><br>
		
		Price: <input type="number" min="0.00" max="10000.00" step="0.01" name="price" required /><br>
		
		Description<input type="text" name="description" required><br>
		
		Brand<select name="brand"  required>
			<option value="Royal Canin">Royal Canin</option>
			<option value="Dog Chow">Dog Chow</option>
			<option value="Animals">Animals</option>
			<option value="Biozoo">Biozoo</option>
			<option value="Furminator">Furminator</option>
			<option value="Nike">Nike</option>
		</select><br>
		
		Instock: <input type="number" min="0" step="1" name="instock_count" required/><br>
		Discount: <input type="number" min="0" max="99" step="1" name="discount" required/><br>
		
		Image: <input type="file" name="image" accept=".jpg, .png, .jpeg" required><br>
				
		<input type="submit" value="Добави">
	</form>
	
	
	
</body>
</html>