package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.pojo.Cart;
import model.pojo.Product;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// // retrieve all products that have been chosen for purchase
		// Object oCart = request.getSession().getAttribute("cart");
		// HashMap<Product, Integer> cart = null;
		// if (oCart != null) {
		// cart = (HashMap<Product, Integer>) oCart;
		// double priceForCart = Cart.getInstance().calculatePriceForCart(cart);
		// request.setAttribute("priceForCart", priceForCart);
		// }
		// request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// retrieve all products that have been chosen for purchase
		Object oCart = request.getSession().getAttribute("cart");
		HashMap<Product, Integer> cart = null;
		if (oCart != null) {
			cart = (HashMap<Product, Integer>) oCart;
			double priceForCart = Cart.getInstance().calculatePriceForCart(cart);
			request.getSession().setAttribute("priceForCart", priceForCart);
		}
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

}
