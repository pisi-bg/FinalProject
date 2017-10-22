package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.pojo.Product;

/**
 * Servlet implementation class CartAddServlet
 */
@WebServlet("/addInCart")
public class CartAddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object o = request.getSession().getAttribute("productCurrent");
		Product product = (Product) o;

		// check if session already stores a cart
		Object oCart = request.getSession().getAttribute("cart");
		HashMap<Product, Integer> cart = null;
		if (oCart == null) {
			cart = new HashMap<Product, Integer>();
		} else {
			cart = (HashMap<Product, Integer>) oCart;
		}

		// add product in map; if already exists adding 1 quanitity
		cart.put(product, (cart.getOrDefault(product, 0) + 1));
		request.getSession().setAttribute("cart", cart);
		request.getRequestDispatcher("productdetail.jsp").forward(request, response);
	}

}
