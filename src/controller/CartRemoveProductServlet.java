package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProductDao;
import model.pojo.Product;

/**
 * Servlet implementation class CartRemoveServlet
 */
@WebServlet("/removeFromCart")
public class CartRemoveProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long productId = Long.parseLong(request.getParameter("productId"));
		Product productCurrent = null;
		try {
			productCurrent = ProductDao.getInstance().getProduct(productId);
			request.getSession().setAttribute("productCurrent", productCurrent);
			HashMap<Product, Integer> cart = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");
			cart.remove(productCurrent);
			request.getSession().setAttribute("cart", cart);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append("sql" + e.getMessage());
		}
		request.getRequestDispatcher("cart").forward(request, response);
		Product product = (Product) request.getSession().getAttribute("productCurrent");
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");
		request.getRequestDispatcher("cart").forward(request, response);
	}

}
