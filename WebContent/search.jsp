<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript">

		function searchProduct() {
			var keyword = document.getElementById("keyword").value;
			var result = documment.getElementById("result");
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					alert(this.responseText);				
					 result.style.display = "block";
					result.innerHTML = "";
					var products = JSON.parse(this.responseText);
					var list = document.createElement("ul");
					for (var i = 0; i < products.length.length; i++) {
						var item = document.createElement("li");
						var link = document.createElement("a");
						link.href = "productdetail?productId="+products[i].id;
						link.innerHTML = products[i].name;
						item.appendChild(link);
					    list.appendChild(item);
					} 
					result.appendChild(item);
				}
			request.open("GET", "http://localhost:8080/FinalProject/search?q="+keyword, true);
			request.send();
		}
	
	</script>
</head>
<body>
	
	<form action="search" method="get" accept-charset="UTF-8 ">
		<input id="keyword" type="text" onkeyup="searchProduct()" placeholder="Keyword to search" name="q">
		<input type="submit" value="Search">
	</form>
	
	<div id="result"></div>
	
</body>
</html>