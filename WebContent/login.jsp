<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1 style="background-color: purple" align="center">Welcome to pisi.bg</h1><br>
		<form action="login" method= "POST" style="text-align: center;">
			<h3 align="center">Please login:</h3>
			Email<input type="email" name = "email" required><br>
			Password<input type="password" name ="password" required><br>
			<input type="submit" value="Login"> 
		</form>
	<form action="register.html" method="POST"  style="text-align: center;">
		<input type="submit" value="Register">
	</form>
	
	<a href="index.jsp" ><button>Home</button></a>
</body>
</html>