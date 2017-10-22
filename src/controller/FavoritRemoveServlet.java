package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UserDao;
import model.pojo.Product;
import model.pojo.User;

/**
 * Servlet implementation class FavoritServletRemove
 */
@WebServlet("/removeFavorit")
public class FavoritRemoveServlet extends HttpServlet {
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
			boolean removed = false;
			try {
				removed = UserDao.getInstance().removeFavorite(u, p.getId());
				if (removed) {
					u.removeFromFavorites(p);
					request.getSession().setAttribute("isFavorite", false);
				} else {
					// TODO throw couldn't do that
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
