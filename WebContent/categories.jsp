<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>												
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body>
	
	
	<c:if test="${ sessionScope.animal != null }">
	
		<a href="category?animal=${sessionScope.animal }&id=1" style="text-decoration: none">ХРАНА</a>  &nbsp;
		<a href="category?animal=${sessionScope.animal }&id=2" style="text-decoration: none">АКСЕСОАРИ</a>  &nbsp;
		
		<c:if test="${ sessionScope.animal == 2 || sessionScope.animal == 3 }">
			<a href="category?animal=${sessionScope.animal }&id=3" style="text-decoration: none">КОЗМЕТИКА</a>  &nbsp;
		</c:if>
		
		<c:if test="${ sessionScope.animal == 1 || sessionScope.animal == 2 || sessionScope.animal == 3 }">
			<a href="category?animal=${sessionScope.animal }&id=4" style="text-decoration: none">ХИГИЕНА</a>  &nbsp;
		</c:if>
		
		<c:if test="${ sessionScope.animal == 1 || sessionScope.animal == 5 || sessionScope.animal == 6 }">
			<a href="category?animal=${sessionScope.animal }&id=5" style="text-decoration: none">АКВАРИУМИ И КЛЕТКИ</a>  &nbsp;
		</c:if><br><br>
		
		<jsp:include page="subCategories.jsp"></jsp:include>
		
	</c:if>
	
		
	
</body>
</html>