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
import model.pojo.User;

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

		// get product details
		long productId = Long.parseLong(request.getParameter("productId"));
		Product productCurrent = null;
		try {
			productCurrent = ProductDao.getInstance().getProduct(productId);
			request.getSession().setAttribute("productCurrent", productCurrent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append("sql" + e.getMessage());
		}

		// check if product is Favorite
		Object o = request.getSession().getAttribute("user");
		boolean isFavorite = false;
		if (o != null) {
			User u = (User) o;
			isFavorite = u.hasInFavorites(productCurrent);
		}
		// request.setCharacterEncoding("UTF-8");
		request.getSession().setAttribute("isFavorite", new Boolean(isFavorite));
		request.getRequestDispatcher("productdetail.jsp").forward(request, response);

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
