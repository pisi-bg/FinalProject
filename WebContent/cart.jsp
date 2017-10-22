<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


	<jsp:include page="header.jsp"></jsp:include><br>
	<br>

	<h1 id="cart_title" class="page-heading">Количка за пазаруване</h1>

	<c:if test="${ sessionScope.cart == null }">
		<h4>Няма добавени продукти в количката</h4>
	</c:if>



	<c:if
		test="${ sessionScope.cart != null &&  not empty sessionScope.cart}">
		<table id="cart_summary"
			class="table table-bordered stock-management-on">
			<thead class="">
				<tr>
					<th class="cart_product first_item">Снимка</th>
					<th class="cart_description item">Продукт</th>
					<th class="cart_unit item text-right">Цена</th>
					<th class="cart_quantity item text-center">Количество</th>
					<th class="cart_delete last_item">&nbsp;</th>
					<th class="cart_total item text-right">Общо</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${ sessionScope.cart }" var="productEntry">
					<c:set var="product" value="${productEntry.key}" />
					<tr>
						<td class="cart_product"><a
							href="productdetail?productId=${product.id}"><img
								src="${product.image }" alt="${product.description }" width="98"
								height="98"></a></td>
						<td class="cart_description"><p class="product-name">
								<a href="productdetail?productId=${product.id}">${product.description }</a>
						<td class="cart_unit"><ul>
								<li class="price regular-price"><span> <fmt:formatNumber
											type="number" pattern="#####.##" value="${ product.price }" />лв.
								</span></li>
								<c:if
									test="${ product.discount != null && product.discount != 0 }">
									<li class="price special-price"><span>нова цена <fmt:formatNumber
												type="number" pattern="#####.##"
												value="${product.calcDiscountedPrice() }" />лв.
									</span></li>
								</c:if>
							</ul></td>
						<td>
							<form action="updateCart" method="post"
								onsubmit="<c:set var="productCurrent" value="${product }" /> "
								onclick="setCurrentProduct()">
								<c:set var="productCurrent" value="${product }" />
									<script>
											function setCurrentProduct() {
												sessionStorage.setItem("productCurrent",productCurrent);		
											}
									</script>
								<input type="text"
									style="width: 35px; height: 35px; font-size: 14px; border: 1px solid #C0C0C0;"
									name="count" size="2" value="${productEntry.value}"
									maxlength="2">

									<button type="submit">обнови</button>
								<c:set var="productCurrent" value="${product }" />
									<script>
											function setCurrentProduct() {
												sessionStorage.setItem("productCurrent",productCurrent);
											}
									</script>
							</form>
						</td>
						<td><form action="removeFromCart" method="post">
								<button type="submit">изтрий</button>
								<c:set var="productCurrent" scope="session" value="${product}">
								</c:set>
							</form></td>
						<td><c:if test="${ product.discount == 0 }">
								<fmt:formatNumber type="number" pattern="#####.##"
									value="${ product.price * productEntry.value }" />лв.									
							</c:if> <c:if test="${ product.discount != 0 }">
								<fmt:formatNumber type="number" pattern="#####.##"
									value="${ product.calcDiscountedPrice()  *  productEntry.value }" />лв.									 
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot class="">
				<tr class="cart_total_price">
					<td rowspan="4" colspan="2"
						style="border: 1px solid #ccc; color: #000; background-color: #FFF">
						<h3 style="text-transform: uppercase">Заплащане</h3> <br>
						Заплащането се извършва с наложен платеж при получаване на
						доставката <br>
					</td>
				</tr>
				<tr class="cart_total_price">
					<td colspan="3" class="total_price_container text-right"><span>Обща
							цена: </span></td>
					<td class="price" id="total_price_container"><span
						id="total_price" style="font-size: 24px;"> <fmt:formatNumber
								type="number" pattern="#####.##"
								value="${ requestScope.priceForCart }" />лв.
					</span></td>
				</tr>
			</tfoot>

		</table>
		<hr>
		<p class="cart_navigation  clearfix inner-top" style="float: right">
		
		
		
		
		<form action="deliveryInfo" method="get">
			<button type="submit" name="submit_fast_registration">
				Потвърди поръчката</button>
		</form>
		</p>
	</c:if>
	<!-- end order-detail-content -->
</body>
</html>