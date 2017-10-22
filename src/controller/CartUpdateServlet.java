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
 * Servlet implementation class CartUpdateQuantity
 */
@WebServlet("/updateCart")
public class CartUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Product product = (Product) request.getSession().getAttribute("productCurrent");
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");
		int quantity = Integer.parseInt(request.getParameter("count"));
		cart.replace(product, quantity);
		request.getSession().setAttribute("cart", cart);
		request.getRequestDispatcher("cart").forward(request, response);

	}

}
