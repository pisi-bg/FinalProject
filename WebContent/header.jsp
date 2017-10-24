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



	<div class="nav">
		<nav>
		<h4 style="display: inline-block; text-transform:">
			<a href="contacts.jsp" title="Контакти"
				class="nav_user" style="text-decoration: none">КОНТАКТИ</a> &nbsp;
			<c:if test="${sessionScope.user == null}">
				<a href="login.jsp" title="LogIn" class="nav_user"
					style="text-decoration: none">ВХОД</a> &nbsp;
				<a href="regiser.html" title="Register" class="nav_user"
					style="text-decoration: none">РЕГИСТРАЦИЯ</a> &nbsp;
				<a href="lostpass.jsp" title="LostPass" class="nav_user"
					style="text-decoration: none">ЗАБРАВЕНА ПАРОЛА</a> &nbsp;
			</c:if>
			<c:if test="${sessionScope.user != null}">
				<a href="logout.jsp" title="LogOut" class="nav_user"
					style="text-decoration: none">ИЗХОД</a> &nbsp;
				<a href="profile.jsp" title="Profile" class="nav_user"
					style="text-decoration: none">ПРОФИЛ</a> &nbsp;
				<a href="favorites.jsp" title="Favorites" class="nav_user"
					style="text-decoration: none">ЛЮБИМИ</a> &nbsp;
			</c:if>
		</h4>
		</nav>
	</div>
	
	<div class="img">
		<nav>
		<h3 style="display: inline-block; text-transform:">
			<a href="index.jsp"><img src="D:\images\buttons\Logo2.png"
				alt="Home" title="Logo" width="20%" height="auto"></a>

			<form action="cart" method="post" style="display: inline;">
				<input type="image" name="submit" width="15%" height="auto"
					src="D:\images\buttons\catCart2_black.png" alt="КОЛИЧКА"
					title="Cart" align="right" style="display: inline;">
			</form>
		</h3>
		</nav>
	</div>

	<div class="products">
		<nav>
		<h3 style="display: inline-block;">
					   <a href="products?animal=1" title="Aquaristics" class="products" style="text-decoration: none">АКВАРИСТИКА</a> 
				&nbsp; <a href="products?animal=2" title="Cats" class="products" style="text-decoration: none">КОТКИ</a>
				&nbsp; <a href="products?animal=3" title="Dogs" class="products" style="text-decoration: none">КУЧЕТА</a>
				&nbsp; <a href="products?animal=4" title="LittleFellows" class="products" style="text-decoration: none">МАЛКИ ЖИВОТНИ</a>
				&nbsp; <a href="products?animal=5" title="Birds" class="products" style="text-decoration: none">ПТИЦИ</a> 
				&nbsp; <a href="products?animal=6" title="Reptiles" class="products" style="text-decoration: none">ТЕРАРИСТИКА</a> &nbsp;
		</h3>
		</nav>
	</div>
</body>
</html>