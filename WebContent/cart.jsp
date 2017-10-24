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

	<c:if test="${ sessionScope.cart == null || empty sessionScope.cart }">
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
						<td class="cart_description"><p class="product-name"
								align="center">
								<a href="productdetail?productId=${product.id}">${product.description }</a>
						<td class="cart_unit"><c:if test="${ product.discount == 0 }">
								<ul style="list-style-type: none">
									<li class="price regular-price"><span> <fmt:formatNumber
												type="number" pattern="#####.##" value="${ product.price }" />лв.
									</span></li>
								</ul>
							</c:if> <c:if test="${  product.discount != 0 }">
								<ul style="list-style-type: none">
									<li class="price regular-price"><span> <strike>
												<fmt:formatNumber type="number" pattern="#####.##"
													value="${ product.price }" /> лв.
										</strike>
									</span></li>
									<li style="color: red">нова цена</li>
									<li class="price special-price"><span> <fmt:formatNumber
												type="number" pattern="#####.##"
												value="${product.calcDiscountedPrice() }" /> лв.
									</span></li>
								</ul>
							</c:if></td>
						<td>
							<form action="updateCart" method="post">
								<input type="hidden" value="${ product.id }" name=productId>
								<input type="text"
									style="width: 35px; height: 35px; font-size: 14px; border: 1px solid #C0C0C0;"
									name="count" size="2" value="${productEntry.value}"
									maxlength="2"> <input type="image" name="submit"
									src="D:\images\buttons\update.png" alt="Update" title="Update">
							</form>
						</td>
						<td>
							<form action="removeFromCart" method="post">
								<input type="image" name="submit" width="25" height="auto"
									src="D:\images\buttons\icon_trash.png" alt="Remove"
									title="Remove"> <input type="hidden"
									value="${ product.id }" name="productId">
							</form>
						</td>
						<td align="center"><c:if test="${ product.discount == 0 }">
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

		<tr class="cart_total_price">
			<td rowspan="4" colspan="2"
				style="border: 1px solid #ccc; color: #000; background-color: #FFF">
				<h3 style="text-transform: uppercase">ПОРЪЧКИ</h3> <c:if
					test="${ empty sessionScope.user }">
					<br> ПОРЪЧКИ СЕ ПРИЕМАТ САМО ОТ РЕГИСТРИРАНИ ПОТРЕБИТЕЛИ  <br>
				</c:if>
			</td>
			<td colspan="3" class="total_price_container text-right">
				<p class="cart_navigation  clearfix inner-top" style="float: right">
					<c:if test="${not empty sessionScope.user}">
						<form action="deliveryInfo" method="get" >
							<button type="submit" name="submit_fast_registration">
								Потвърди поръчката</button>
						</form>
					</c:if>
					<c:if test="${ empty sessionScope.user }">
						<a href="login.jsp" title="LogIn" class="nav_user"
							style="text-decoration: none">ВХОД</a> &nbsp;
						<a href="regiser.html" title="Register" class="nav_user"
							style="text-decoration: none">РЕГИСТРАЦИЯ</a> &nbsp;
						<a href="lostpass.jsp" title="LostPass" class="nav_user"
							style="text-decoration: none">ЗАБРАВЕНА ПАРОЛА</a> &nbsp;											
					</c:if>
				</p>
			</td>
	</c:if>
</body>
</html>