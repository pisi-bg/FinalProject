package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;

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

	public TreeSet<Order> getOrdersForUser(long user_id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT * FROM pisi.orders WHERE user_id =6;";

		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, user_id);
			rs = stmt.executeQuery();
			TreeSet<Order> orders = new TreeSet<>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
					return o2.getDateTime().compareTo(o1.getDateTime());
				}
			});

			while (rs.next()) {
				HashMap<Product, Integer> products = OrderDao.getInstance().getProductsForOrder(rs.getLong("order_id"));
				orders.add(new Order(rs.getLong("order_id"), user_id,
						DateTimeJavaSqlConvertor.sqlToLocalDateTime(rs.getString("dateTime")),
						rs.getBigDecimal("price").doubleValue(), products));
			}
			return orders;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

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
						+ "JOIN pisi.brands AS b USING (brand_id) WHERE op.order_id = ? ORDER BY op.product_quantity");
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

	public void insertOrderForUser(Order order) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "INSERT INTO pisi.orders ( user_id, dateTime_created , final_price, delivery_info_id) "
				+ " VALUES (?,?,?,?)";
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

			ps.setLong(1, order.getUser().getId());
			ps.setString(2, DateTimeJavaSqlConvertor.localDateTimeToSql(order.getDateTime()));
			ps.setDouble(3, order.getFinalPrice());
			ps.setLong(4, order.getDeliveryInfoId());

			ps.executeUpdate();
			con.commit();
			rs = ps.getGeneratedKeys();
			rs.next();
			order.setId(rs.getInt(1));
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public void insertProductsFromOrder(long orderId, HashMap<Product, Integer> cart) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();

		for (Entry<Product, Integer> entry : cart.entrySet()) {
			long productId = entry.getKey().getId();
			int quantity = (int) entry.getValue();

			String query = "INSERT INTO pisi.orders_has_products (product_id, order_id, product_quantity) "
					+ " VALUES (?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);

			ps.setLong(1, productId);
			ps.setLong(2, orderId);
			ps.setInt(3, quantity);

			ps.executeUpdate();
			con.commit();
		}
	}

	public ArrayList<String> getCitiesNames() throws SQLException {
		ArrayList<String> cities = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT city_name FROM pisi.cities;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			rs = stmt.executeQuery();
			while (rs.next()) {
				cities.add(rs.getString("city_name"));
			}
			return cities;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

}
