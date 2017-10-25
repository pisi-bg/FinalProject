package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.DeliveryInfoDao;
import model.dao.OrderDao;
import model.db.DBManager;
import model.pojo.DeliveryInfo;
import model.pojo.Order;
import model.pojo.Product;
import model.pojo.User;

/**
 * Servlet implementation class OrderCreateServlet
 */
@WebServlet("/createOrder")
public class OrderCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phone = request.getParameter("phone");
		String city = request.getParameter("city");
		String address = request.getParameter("address");
		int zip = Integer.parseInt(request.getParameter("zip"));

		String note = request.getParameter("note");

		if (firstName.isEmpty()) {
			response.getWriter().append("First name is empty.");
			return;
		}
		if (lastName.isEmpty()) {
			response.getWriter().append("Last name is empty.");
			return;
		}
		// TODO phone number validation & zip ?

		DeliveryInfo deliveryInfo = new DeliveryInfo(address, zip, city, firstName, lastName, phone, note);
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");
		User user = (User) request.getSession().getAttribute("user");
		Double priceForCart = (Double) request.getSession().getAttribute("priceForCart");

		Connection con = DBManager.getInstance().getConnection();
		ResultSet rs = null;

		try {
			con.setAutoCommit(false);
			DeliveryInfoDao.getInstance().insertDelivInfoOrder(deliveryInfo);
			Order order = new Order(user, LocalDateTime.now(), priceForCart, deliveryInfo.getDeliveryInfoId(), cart);
			OrderDao.getInstance().insertOrderForUser(order);
			long orderId = order.getOrderId();
			OrderDao.getInstance().insertProductsFromOrder(orderId, cart);
			request.getRequestDispatcher("thanks").forward(request, response);

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO error page....
				e1.printStackTrace();
				response.getWriter().append("something in SQL DBManager" + e1.getMessage());
			}
			response.getWriter().append("Error" + e.getMessage());
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
