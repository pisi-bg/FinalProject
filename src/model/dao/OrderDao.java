package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.db.DBManager;
import model.pojo.Order;
import model.pojo.Product;
import utils.DateTimeJavaSqlConvertor;

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

	public ArrayList<Order> getOrdersForClient(long user_id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT o.order_id AS order_id, o.dateTime_created AS dateTime , o.final_price AS price ,"
						+ "p.product_id AS product_id, p.product_name AS name,"
						+ "p.price AS price, p.discount AS discount, p.description AS description, c.category_name AS category,"
						+ " an.animal_name AS animal, p.image_url AS image, b.brand_name AS brand"
						+ "FROM pisi.orders AS o" + "JOIN pisi.orders_has_products AS op USING (order_id)"
						+ "JOIN pisi.products AS p USING (product_id)"
						+ "JOIN pisi.animals AS an ON (p.animal_id = an.animal_id)"
						+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id)"
						+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id)"
						+ "JOIN pisi.brands AS b USING (brand_id) WHERE user_id = ? ORDER BY order_id");
		stmt.setLong(1, user_id);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Order> orders = new ArrayList<>();
		while (rs.next()) {
			HashMap<Product, Integer> products = OrderDao.getInstance().getProductsForOrder(rs.getLong("order_id"));
			orders.add(new Order(rs.getLong("order_id"), user_id,
					DateTimeJavaSqlConvertor.sqlToLocalDateTime(rs.getString("dateTime")),
					rs.getBigDecimal("discount").doubleValue(), rs.getBigDecimal("price").doubleValue(), products));
		}
		return orders;
	}

	public HashMap<Product, Integer> getProductsForOrder(long orderId) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con
				.prepareStatement("SELECT p.product_id AS product_id,  p.product_name AS name, p.price AS price,"
						+ "p.discount AS discount, p.description AS description, c.category_name AS category, "
						+ "an.animal_name AS animal, p.image_url AS image, b.brand_name AS brand, op.product_quantity as quantity "
						+ "FROM pisi.orders_has_products AS op " + "JOIN pisi.products AS p USING (product_id)"
						+ "JOIN pisi.animals AS an ON (p.animal_id = an.animal_id) "
						+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands AS b USING (brand_id) WHERE op.order_id = 3 ORDER BY op.product_quantity");
		ps.setLong(1, orderId);
		ResultSet rs = ps.executeQuery();
		HashMap<Product, Integer> productsForOrder = new HashMap<Product, Integer>();
		while (rs.next()) {
			long id = rs.getLong("product_id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			double price = rs.getDouble("price");
			int discount = rs.getInt("discount");
			String animal = rs.getString("animal");
			String category = rs.getString("category");
			String image = rs.getString("image");
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("product_id"));
			String brand = rs.getString("brand");
			productsForOrder.put(
					new Product(id, name, description, price, discount, animal, category, image, rating, brand),
					rs.getInt("quantity"));
		}
		return productsForOrder;
	}

	public boolean insertOrderForUser(Order order) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement(
				"INSERT INTO pisi.orders ( user_id, dateTime_created , final_price ) VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, order.getUser().getId());
		ps.setString(2, DateTimeJavaSqlConvertor.localDateTimeToSql(order.getDateTime()));
		ps.setDouble(3, order.getFinalPrice());
		int result = ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		order.setId(rs.getLong(1));
		// TODO add products from order in DB; think about get delivery_info_id
		return result == 1 ? true : false;
	}

	public double calculatePriceForOrder(HashMap<Product, Integer> products) {
		double price = 0;
		for (Entry<Product, Integer> entry : products.entrySet()) {
			Product product = entry.getKey();
			int quantity = (int) entry.getValue();
			double productPrice = product.getPrice();
			if (product.getDiscount() != 0) {
				productPrice = ProductDao.getInstance().calcDiscountedPrice(product);
			}
			price += product.getPrice() * quantity;
		}
		return price;
	}

}
