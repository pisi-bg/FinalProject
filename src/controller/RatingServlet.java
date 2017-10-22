package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.RatingDao;
import model.pojo.Product;
import model.pojo.User;

/**
 * Servlet implementation class RatingServlet
 */
@WebServlet("/addrating")
public class RatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object o = request.getSession().getAttribute("user");
		if (o == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		User u = (User) o;

		o = request.getSession().getAttribute("productCurrent");
		if (o != null) {
			Product p = (Product) o;
			try {
				if (RatingDao.getInstance().productHasRatingFromUser(p.getId(), u.getId())) {
					// TODO throw you already voted for this product
				} else {
					double rating = Double.parseDouble(request.getParameter("rating"));
					if (RatingDao.getInstance().addProductRating(p.getId(), u.getId(), rating)) {
						// TODO throw rating added
					} else {
						// TODO throw rating not added
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().append("sql" + e.getMessage());
			}
		}

		request.getRequestDispatcher("productdetail.jsp").forward(request, response);
	}

}
