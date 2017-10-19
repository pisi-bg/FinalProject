<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

				<!-- AQUARISTICS -->
	<c:if test="${ sessionScope.animal == 1 }">
		<c:if test="${ requestScope.id == 1 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=1&subId=1"><button>E.G. Food</button></a>
		</c:if>
		
		<!-- here we place other sub categories like above when we fill DB with testing info and subId="sub category id in the db" -->		
		
	</c:if>
	
			<!-- CATS -->
	<c:if test="${ sessionScope.animal == 2 }">
					<!-- FOOD  -->
		<c:if test="${ requestScope.id == 1 }">
			<a href="subcategory?animal=${ sessionScope.id }&id=1&subId=6"><button>Canned Food</button></a>
		</c:if>
		<c:if test="${ requestScope.id == 1 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=1&subId=15"><button>Granulated Food</button></a>
		</c:if>
				<!-- ACCESSOARIES -->
		<c:if test="${ requestScope.id == 2 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=2&subId=8"><button>Transport</button></a>
		</c:if>
		<c:if test="${ requestScope.id == 2 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=2&subId=11"><button>Straps</button></a>
		</c:if>
		<c:if test="${ requestScope.id == 2 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=2&subId=12"><button>Clothes</button></a>
		</c:if>
					<!-- COSMETICS -->
		<c:if test="${ requestScope.id == 3 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=3&subId=7"><button>Brushes</button></a>
		</c:if>			<!-- To be listed more -->
				
				<!-- HYGIENE -->
		<c:if test="${ requestScope.id == 4 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=4&subId=56"><button>Toilets</button></a>
		</c:if>						<!-- Toilets is just an example we don't have it in the database -->
		
		
		
	</c:if>
	
			<!-- DOGS -->
	<c:if test="${ sessionScope.animal == 3 }">
					<!-- FOOD  -->
		<c:if test="${ requestScope.id == 1 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=1&subId=6"><button>Canned Food</button></a>
			
		</c:if>
		<c:if test="${ requestScope.id == 1 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=1&subId=15"><button>Granulated Food</button></a>
		</c:if>
				<!-- ACCESSOARIES -->
		<c:if test="${ requestScope.id == 2 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=2&subId=8"><button>Transport</button></a>
		</c:if>
		<c:if test="${ requestScope.id == 2 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=2&subId=11"><button>Straps</button></a>
		</c:if>
		<c:if test="${ requestScope.id == 2 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=2&subId=12"><button>Clothes</button></a>
		</c:if>
					<!-- COSMETICS -->
		<c:if test="${ requestScope.id == 3 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=3&subId=7"><button>Brushes</button></a>
		</c:if>			<!-- To be listed more -->
				
				<!-- HYGIENE -->
		<c:if test="${ sessionScope.id == 4 }">
			<a href="subcategory?animal=${ sessionScope.animal }&id=4&subId=56"><button>Toilets</button></a>
		</c:if>						<!-- Toilets is just an example we don't have it in the database -->
		
	</c:if>
	
</body>
</html>