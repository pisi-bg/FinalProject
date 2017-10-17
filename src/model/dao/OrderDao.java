package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import model.db.DBManager;
import model.pojo.Order;
import model.pojo.Product;

public class OrderDao {

	private static OrderDao instance;

	private OrderDao() {
	}

	public static synchronized OrderDao getInstance() {

		if (instance == null) {
			instance = new OrderDao();
		}
		return instance;
	}

//	public ArrayList<Order> getOrdersForClient(long user_id) throws SQLException {
//		Connection con = DBManager.getInstance().getConnection();
//		PreparedStatement stmt = con.prepareStatement(
//				"SELECT o.order_id, o.dateTime_created, o.discount, o.final_price,o.delivery_info_id, p.product_id, p.product_name, p.description, p.price, b.brand_name"
//						+ "FROM orders AS o JOIN orders_has_products AS op USING (order_id) JOIN products AS p USING (product_id)"
//						+ "JOIN brands AS b USING (brand_id) WHERE user_id = ? ORDER BY order_id");
//
//		stmt.setLong(1, user_id);
//		ResultSet rs = stmt.executeQuery();
//
//		ArrayList<Order> orders = new ArrayList<>();
//		long previousOrderId = -1;
//		HashMap<Product, Integer> products = new HashMap<>();
//
//		while (rs.next()) {
//			if (previousOrderId == -1 || rs.getLong("order_id") != previousOrderId) {
//				products = new HashMap<Product, Integer>();
//				previousOrderId = rs.getLong("order_id");
//			}
//			Product currentProduct = null;
//			// TODO quantities to be clear
//			products.put(currentProduct, 1);
//			// TODO in DB discount and final price 
//			orders.add(new Order(rs.getLong("order_id"), user_id, rs.getTimestamp("dateTime_created").toLocalDateTime(),
//					rs.getBigDecimal("discount").doubleValue(), rs.getBigDecimal("finalPrice").doubleValue(),
//					rs.getLong("deliveryInfoId"), products));
//
//		}
//		return orders;
//
//	}

	public boolean insertOrder(Order order) throws SQLException {
		// TODO make time column in DB and remake the datetime mess
		// link for help
		// https://stackoverflow.com/questions/45102667/localdatetime-to-java-sql-date-in-java-8
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(
				"INSERT INTO `pisi`.`orders` ( `user_id`,`dateTime_created` , `discount`, `final_price`, `delivery_info_id`) VALUES (?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, order.getUser().getId());
		// ps.setTimestamp(2,
		// java.sql.Date.valueOf(order.getDateTime().toLocalDate()));
		ps.setDouble(3, order.getDiscount());
		ps.setDouble(4, order.getFinalPrice());
		ps.setLong(5, order.getDeliveryInfoId());

		int result = ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		order.setId(rs.getLong(1));
		return result == 1 ? true : false;
	}

}
