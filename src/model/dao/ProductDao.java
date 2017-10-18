package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.db.DBManager;
import model.pojo.Product;

public class ProductDao {

	private static ProductDao instance;

	private ProductDao() {
	}

	public static synchronized ProductDao getInstance() {
		if (instance == null) {
			instance = new ProductDao();
		}
		return instance;
	}

	// hashmap for specific animal type by category for products ...... to be
	// decided if this will stay map or list !!!!
	public HashMap<String, ArrayList<Product>> getProductsByAnimal(int animalId) throws SQLException {
		HashMap<String, ArrayList<Product>> products = new HashMap<>();

		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT p.product_id AS id, p.product_name AS name , c.category_name AS category , p.price AS price, "
													+ "p.description AS description, pc.category_name AS parent_category, p.image_url AS image "
													+ "FROM pisi.products AS p " + "JOIN pisi.animals AS a ON (p.animal_id = a.animal_id) "
													+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
													+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id) "
													+ "JOIN pisi.brands AS b ON(p.brand_id = b.brand_id) " + "WHERE p.animal_id = ?;");
		stmt.setInt(1, animalId);
		ResultSet rs = stmt.executeQuery();
		String category = null;

		while (rs.next()) {
			category = rs.getString("category");
			if (!products.containsKey(category)) {
				products.put(category, new ArrayList<>());
			}
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
			products.get(category).add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
					rs.getDouble("price"), category, rating, rs.getString("image")));
		}
		return products;
	}

	// this method returns all product for given animal id and given parent
	// category ID
	List<Product> getProductsByAnimalAndParentCategory(long animalId, long parentCategoryId) throws SQLException {
		List<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , p.price,"
						+ " p.description, pc.category_name as parent_category, p.image_url as image "
						+ "	FROM pisi.products as p" + " JOIN pisi.animals as a ON (p.animal_id = a.animal_id)"
						+ " JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id)"
						+ " JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id)"
						+ " JOIN pisi.brands as b ON(p.brand_id = b.brand_id)"
						+ " WHERE c.parent_category_id = ? AND p.animal_id = ?;");
		stmt.setLong(1, parentCategoryId);
		stmt.setLong(2, animalId);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			// check if there is problem with returning null !!!!
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
			products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
					rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
		}
		return products;
	}

	// this method returns list of product for given animal id and sub-category
	// id;
	List<Product> getProductsByAnimalAndSubCategory(long animalId, long categoryId) throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
						+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image "
						+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) "
						+ "WHERE p.product_category_id = ? AND p.animal_id = ?;");
		stmt.setLong(1, categoryId);
		stmt.setLong(2, animalId);

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			// check if there is a problem with returning a null from db for
			// rating !!!
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
			products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
					rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
		}
		return products;
	}

	// this method returns a product by its ID;
	public Product getProduct(long productId) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT p.product_id as id , p.product_name as name, p.description as description, AVG(r.raiting) as rating,"
						+ " c.category_name as category, a.animal_name as animal, p.unit as unit, p.price_per_unit as price, p.discount as discount"
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
				rs.getString("manufacturerDescription"), rating, rs.getInt("unit"), rs.getString("image"),
				rs.getInt("discount"));
	}

	public List<Product> getProductsByBrand(int brand_id) {
		return null;
		// TODO
	}

	public List<Product> getProductsInPromotion() throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
						+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image "
						+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) " + "WHERE NOT (p.discount =0);");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			// check if there is a problem with returning a null from db for
			// rating !!!
			double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
			products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
					rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
		}
		return products;
	}

	public List<Product> getFavorites(long userId) throws SQLException {
		List<Product> tempList = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con
				.prepareStatement("SELECT p.product_id as id , p.product_name as name, p.description as description, "
						+ " c.category_name as category, a.animal_name as animal, p.instock_count as unit, p.price as price,"
						+ " m.brand_name as brand, m.logo_image as brandImage, p.image_url as image, p.discount as discount  FROM pisi.products as p"
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

			long id = rs.getLong("id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			double price = rs.getLong("price");
			String animal = rs.getString("animal");
			String category = rs.getString("category");
			String brand = rs.getString("brand");
			String brandImage = rs.getString("brandImage");
			int isStock = rs.getInt("unit");
			String image = rs.getString("image");
			int discount = rs.getInt("discount");
			tempList.add(new Product(id, name, description, price, animal, category, brand, brandImage, rating, isStock,
					image, discount));
		}
		return tempList;
	}

	public double calcDiscountedPrice(Product p) {
		double newPrice = p.getPrice() * ((100 - p.getDiscount()) / 100.0);
		return newPrice;
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

	// ***ADMIN operations***

	// make a discount of product price
	public boolean setInPromotion(long productId, int percentDiscount) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE pisi.products SET discount = ? WHERE product_id = ? ",
				Statement.RETURN_GENERATED_KEYS);
		stmt.setLong(1, percentDiscount);
		stmt.setLong(2, productId);
		return stmt.executeUpdate() == 1 ? true : false;
	}

	// adding quantity from admin
	public boolean addQuantity(long product_id, int quantity) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT instock_count FROM pisi.products WHERE product_id=?;");
		stmt.setLong(1, product_id);
		ResultSet rs = stmt.executeQuery();
		int currentQuantity = 0;
		if (rs.next()) {
			currentQuantity = rs.getInt("instock_count");
			int newQuantity = currentQuantity + quantity;
			stmt = con.prepareStatement("UPDATE pisi.products SET instock_count=? WHERE product_id=?;");
			stmt.setInt(1, newQuantity);
			stmt.setLong(2, product_id);
			return stmt.executeUpdate() == 1 ? true : false;
		} else {
			// throw noSuchProductException
			return false;
		}
	}

	// adding product from admin
	public void addProduct(Product p) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		con.setAutoCommit(false);
		try {

			int brandId = getBrandId(p.getBrand());
			if (brandId == -1) {
				brandId = insertBrand(p.getBrand(), p.getBrandImage());
			}
			int animalId = retrieveAnimalId(p.getAnimal());
			int categoryId = retrieveCategoryId(p.getCategory());

			if (categoryId == -1 || animalId == -1 || brandId <= 0) {
				// throws IvalidInputDataException
			}
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO pisi.products (product_name, animal_id, product_category_id, price, description, brand_id, instock_count, discount, image_url) VALUES (?,?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getName());
			ps.setInt(2, animalId);
			ps.setInt(3, categoryId);
			ps.setDouble(4, p.getPrice());
			ps.setString(5, p.getDescription());
			ps.setInt(6, brandId);
			ps.setInt(7, p.getIsStock());
			ps.setInt(8, p.getDiscount());
			ps.setString(9, p.getImage());
			ps.executeUpdate();
			con.commit();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			p.setId(rs.getLong(1));
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// remove product from admin
	public void removeProduct(Product p) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		con.setAutoCommit(false);
		try {

			PreparedStatement ps = con.prepareStatement("DELETE FROM pisi.products WHERE product_id=?;");
			ps.setLong(1, p.getId());
			ps.executeUpdate();
			ps = con.prepareStatement("DELETE FROM pisi.ratings WHERE product_id=?;");
			ps.setLong(1, p.getId());
			ps.executeUpdate();
			ps = con.prepareStatement("DELETE FROM pisi.users_has_favorites WHERE product_id=?;");
			ps.setLong(1, p.getId());
			ps.executeUpdate();
			ps = con.prepareStatement("DELETE FROM pisi.products_has_animals WHERE product_id=?;");
			ps.setLong(1, p.getId());
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// this method returns the id of brand if it exists in the database
	public int getBrandId(String brandName) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT brand_id as id FROM pisi.brands WHERE brand_name = ?");
		stmt.setString(1, brandName);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("id");
		} else
			return -1;
	}

	// inserts new brand, used when inserting new product if necessary
	public int insertBrand(String brandName, String brandImgUrl) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO pisi.brands (brand_name, brand_image) VALUES (?,?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, brandName);
		ps.setString(2, brandImgUrl);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}

	// this method returns the animal id
	public int retrieveAnimalId(String animalName) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT animal_id as id FROM pisi.animals WHERE animal_name = ?");
		stmt.setString(1, animalName.toUpperCase());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("id");
		} else
			return -1;
	}

	// this method returns the animal id
	public int retrieveCategoryId(String categoryName) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT product_category_id as id FROM pisi.product_categories WHERE category_name = ?");
		stmt.setString(1, categoryName.toUpperCase());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("id");
		} else
			return -1;
	}

}
