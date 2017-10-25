package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.DeliveryInfoDao;
import model.dao.OrderDao;
import model.pojo.DeliveryInfo;
import model.pojo.User;

/**
 * Servlet implementation class DeliverInfoServlet
 */
@WebServlet("/deliveryInfo")
public class DeliverInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// check if logged
		Object oUser = request.getSession().getAttribute("user");
		if (oUser == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		User u = (User) oUser;
		ArrayList<String> cities = null;
		try {
			cities = OrderDao.getInstance().getCitiesNames();
			ArrayList<DeliveryInfo> deliveries = DeliveryInfoDao.getInstance().getListDeliveryInfosForUser(u.getId());
			if (deliveries != null && !deliveries.isEmpty()) {
				request.setAttribute("deliveries", deliveries);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("cities", cities);
		request.getRequestDispatcher("/deliveryInfo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
