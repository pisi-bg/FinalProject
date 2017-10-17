package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.DBManager;
import model.pojo.Product;

public class ProductDAO {

	private static ProductDAO instance;

	private ProductDAO() {
	}

	public static synchronized ProductDAO getInstance() {
		if (instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}

	// hashmap for specific animal type by category for products  ...... to be decided if this will stay map or list !!!!
	public HashMap<String, ArrayList<Product>> getProductsByAnimal(int animalId) throws SQLException {
		HashMap<String, ArrayList<Product>> products = new HashMap<>();

		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT p.product_id AS id, p.product_name AS name , c.category_name AS category , p.price AS price, "
														+ "p.description AS description, pc.category_name AS parent_category, p.image_url AS image "
														+ "FROM pisi.products AS p "
														+ "JOIN pisi.animals AS a ON (p.animal_id = a.animal_id) "
														+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
														+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id) "
														+ "JOIN pisi.brands AS b ON(p.brand_id = b.brand_id) "
														+ "WHERE p.animal_id = ?;");
		stmt.setInt(1, animalId);
		ResultSet rs = stmt.executeQuery();
		String category = null;

		while (rs.next()) {
			category = rs.getString("category");
			if (!products.containsKey(category)) {
				products.put(category, new ArrayList<>());
			}			
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
			products.get(category)
					.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description")
							, rs.getDouble("price"), category,  rating, rs.getString("image")));
		}
		return products;
	}

	// adding product from admin
	public void addProduct(Product p) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("", Statement.RETURN_GENERATED_KEYS);

		// TODO UPDATE and set PS !!!!

		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		p.setId(rs.getLong(1));
	}
	
	// this method returns all product for given animal id and given parent category ID
	List<Product> getProductsByAnimalAndParentCategory(long animalId, long parentCategoryId) throws SQLException{
		List<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , p.price,"
														+ " p.description, pc.category_name as parent_category, p.image_url as image "
														+ "	FROM pisi.products as p"
														+ " JOIN pisi.animals as a ON (p.animal_id = a.animal_id)"
														+ " JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id)"
														+ " JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id)"
														+ " JOIN pisi.brands as b ON(p.brand_id = b.brand_id)"
														+ " WHERE c.parent_category_id = ? AND p.animal_id = ?;");
		stmt.setLong(1, parentCategoryId);
		stmt.setLong(2, animalId);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			//check if there is problem with returning null !!!!
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
			products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getString("category"), rating , rs.getString("image")));
		}
		return products;
	}

	//this method returns list of product for given animal id and sub-category id;
	List<Product> getProductsByAnimalAndSubCategory(long animalId, long categoryId) throws SQLException{
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
													+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image "
													+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
													+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
													+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
													+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) "
													+ "WHERE p.product_category_id = ? AND p.animal_id = ?;");
		stmt.setLong(1, categoryId);
		stmt.setLong(2, animalId);
		
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			// check if there is a problem with returning a null from db for rating !!!
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
			products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
		}
		return products;
	}
	
	// updating in stock quantity for product
	public boolean removeQuantity(long productId, int invokedQuantity) throws SQLException {
		int result = -1;
		Connection con = DBManager.getInstance().getConnection();
		// checks if there is enough quantity
		PreparedStatement stmt = con.prepareStatement("SELECT instock_count FROM pisi.products WHERE product_id = ?");
		stmt.setLong(1, productId);
		ResultSet rs = stmt.executeQuery();
		int currentInstockCount = rs.getInt("instock_count");
		if (invokedQuantity >= currentInstockCount) {
			stmt = con.prepareStatement("UPDATE pisi.products SET instock_count = ? WHERE product_id = ? ");
			int newQuantity = currentInstockCount - invokedQuantity;
			stmt.setLong(1, newQuantity);
			stmt.setLong(2, productId);
			result = stmt.executeUpdate();
			if (newQuantity == 0) {
				// TODO throw update 'Not In stock' in the Web view
			}
		} else {
			// ?throw NotEnoughQuantityException
			return false;
		}

		return result == 1 ? true : false;
	}

	// this method returns a product by its ID;
	public Product getProduct(long productId) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT p.product_id as id , p.product_name as name, p.description as description, AVG(r.raiting) as rating,"
						+ " c.category_name as category, a.animal_name as animal, p.unit as unit, p.price_per_unit as price,"
						+ " m.brand_name as brand, m.description as manufacturerDescription" + " FROM pisi.product as p"
						+ " JOIN pisi.product_category AS c ON (p.product_category_id = c.product_category_id)"
						+ " JOIN pisi.manufacturer AS m ON (p.manufacturer_id = m.manifacture_id)"
						+ " LEFT JOIN pisi.rating as r ON (p.product_id = r. product_id)"
						+ " JOIN pisi.animal as a ON(p.animal_id = a.animal_id)" + " WHERE p.product_id = ?");
		stmt.setLong(1, productId);
		ResultSet rs = stmt.executeQuery();

		Double rating = new Double(rs.getDouble("rating"));
		if (rating.equals(null)) {
			rating = new Double(0);
		}

		return new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"), rs.getInt("price"),
				rs.getString("animal"), rs.getString("category"), rs.getString("brand"),
				rs.getString("manufacturerDescription"), rating, rs.getInt("unit"),
				rs.getString("image"));
	}

	public List<Product> getFavorites(long userId) throws SQLException {
		List<Product> tempList = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT p.product_id as id , p.product_name as name, p.description as description, "
						+ " c.category_name as category, a.animal_name as animal, p.instock_count as unit, p.price as price,"
						+ " m.brand_name as brand FROM pisi.products as p"
						+ " JOIN pisi.product_categories AS c ON (p.product_category_id = c.product_category_id)"
						+ " JOIN pisi.brands AS m ON (p.brand_id = m.brand_id)"
						+ " JOIN pisi.animals as a ON(p.animal_id = a.animal_id)"
						+ "	JOIN pisi.users_has_favorites AS cf ON(p.product_id = cf.product_id)"
						+ " WHERE p.product_id = ?" + " GROUP BY p.product_id");
		stmt.setLong(1, userId);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			// check if there is no rating for this product DB will return null;
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
			
			// remove manifacture info from User and rething constructors !!!!
			
			tempList.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
					rs.getInt("price"), rs.getString("animal"), rs.getString("category"), rs.getString("brand"),
					rs.getString("manufacturerDescription"), rating, rs.getInt("unit"),
					rs.getString("image")));
		}
		return tempList;
	}

}
