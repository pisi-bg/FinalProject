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
 * Servlet implementation class CartRemoveServlet
 */
@WebServlet("/removeFromCart")
public class CartRemoveProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Product product = (Product) request.getSession().getAttribute("productCurrent");
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");

		cart.remove(product);
		request.getSession().setAttribute("cart", cart);
		request.getRequestDispatcher("cart").forward(request, response);
	}

}
