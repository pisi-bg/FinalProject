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
import model.pojo.User;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// check if logged
		Object o = request.getSession().getAttribute("user");
		if (o == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		User u = (User) o;

		// retrieve all products that have been chosen for purchase
		Object oCart = request.getSession().getAttribute("cart");
		HashMap<Product, Integer> cart = null;
		if (oCart != null) {
			cart = (HashMap<Product, Integer>) oCart;
		}

		request.setAttribute("priceForCart", Double.valueOf(Cart.calculatePriceForCart(cart)));
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// check if logged
		// create an order with all chosen product in session
		// save to db
	}

}
