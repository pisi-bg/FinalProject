<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>												
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	
	
	<c:if test="${ sessionScope.animal != null }">
		<a href="category?animal=${sessionScope.animal }&id=1"><button>Food</button></a>
		<a href="category?animal=${sessionScope.animal }&id=2"><button>Accessories</button></a>
		
		<c:if test="${ sessionScope.animal == 2 || sessionScope.animal == 3 }">
			<a href="category?animal=${sessionScope.animal }&id=3"><button>Cosmetics</button></a>
		</c:if>
		
		<c:if test="${ sessionScope.animal == 1 || sessionScope.animal == 2 || sessionScope.animal == 3 }">
			<a href="category?animal=${sessionScope.animal }&id=4"><button>Hygiene</button></a>
		</c:if>
		
		<c:if test="${ sessionScope.animal == 1 || sessionScope.animal == 5 || sessionScope.animal == 6 }">
			<a href="category?animal=${sessionScope.animal }&id=5"><button>Aquariums And Cages</button></a>
		</c:if><br><br>
		
		<jsp:include page="subCategories.jsp"></jsp:include>
		
	</c:if>
	
		
	
</body>
</html>