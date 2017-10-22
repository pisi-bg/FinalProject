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
 * Servlet implementation class FavoritServletAdd
 */
@WebServlet("/addFavorite")
public class FavoritServletAdd extends HttpServlet {
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
			boolean inserted = false;
			try {
				inserted = UserDao.getInstance().insertFavorite(u, p.getId());
				if (inserted) {
					u.addToFavorites(p);
					request.getSession().setAttribute("isFavorite", true);

				} else {
					// TODO throw couldn't do that
					System.out.println("NOT inserted");
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
