package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.OrderDao;
import model.pojo.Order;
import model.pojo.User;

/**
 * Servlet implementation class ThanksServlet
 */
@WebServlet("/thanks")
public class ThanksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession().setAttribute("cart", null);
		request.getSession().setAttribute("priceForCart", null);
		request.getSession().setAttribute("productCurrent", null);

		User u = (User) request.getSession().getAttribute("user");
		try {
			TreeSet<Order> orders = OrderDao.getInstance().getOrdersForUser(u.getId());
			request.getSession().setAttribute("orders", orders);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("favorites", u.getFavorites());
		request.getRequestDispatcher("thanks.jsp").forward(request, response);
	}

}
