package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProductDao;
import model.pojo.Product;

/***
 * Servlet implementation
 * 
 * class ProductDetail
 */
@WebServlet("/productdetail")
public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long productId = Long.parseLong(request.getParameter("productId"));

		try {
			Product product = ProductDao.getInstance().getProduct(productId);
			request.setAttribute("product", product);
			request.getRequestDispatcher("productdetail.jsp").forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append("sql" + e.getMessage());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		Object o = request.getSession().getAttribute("cart");
		HashSet<Product> cart; // TODO override hash
		if (o == null) {
			cart = new HashSet<Product>();
			request.getSession().setAttribute("cart", cart);
		} else {
			cart = (HashSet<Product>) o;
		}

	}

}
