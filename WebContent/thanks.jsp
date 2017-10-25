<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta charset="UTF-8">
<title>Thanks</title>
</head>	
	<jsp:include page="header.jsp"></jsp:include>
	<br>
	
	<h1>ПОРЪЧКИ</h1>
	
		<h4>ПОРЪЧКАТА Е ИЗПРАТЕНА УСПЕШНО</h4>
		<h3>Благодарим Ви за доверието! </h3>
		<br>
		
		<h4>ПОРЪЧКИ </h4>
		<c:if test="${not empty sessionScope.orders}">
				<form action="deliveryInfo" method="get" >							
					<c:forEach items="${ sessionScope.orders }" var="order">					
								<table border="1">
									<thead class="">
										<tr>
											<th class="cart_product first_item">Дата</th>
											<th class="cart_description item">Цена</th>											
											<th class="cart_quantity item text-center">Продукти</th>
										</tr>
									</thead>						
									<tbody>			
											<tr>
												<td>${ order.dateTime }</td>
												<td>${order.finalPrice }</td>
												<td>
													<ul style="list-style-type: none">
														<c:forEach items="${ order.products }" var="productEntry">
															<li >	
																	<td>${productEntry.key.name }</td>
																	<td>${productEntry.value }</td>
															</li>
														</c:forEach>														
													</ul>			
												</td>
											</tr>
									</tbody>
								</table>
							<hr>
					</c:forEach>
					
			</form>
		</c:if>
		
	
	

</body>
</html>