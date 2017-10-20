package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.OrderDao;
import model.pojo.Order;
import model.pojo.User;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sort = request.getParameter("sort");
		// for test only
		long userId = Long.parseLong(request.getParameter("userId"));

		// check if logged
		Object oUser = request.getSession().getAttribute("user");
		// if (oUser == null) {
		// request.getRequestDispatcher("login.html").forward(request,
		// response);
		// return;
		// }
		User u = (User) oUser;

		// show all orders for the logged
		ArrayList<Order> orders;
		try {
			// orders = OrderDao.getInstance().getOrdersForClient(u.getId());
			orders = OrderDao.getInstance().getOrdersForClient(userId);
			request.getSession().setAttribute("orders", orders);
			request.getRequestDispatcher("profile.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append("sql" + e.getMessage());
		}

	}

}
