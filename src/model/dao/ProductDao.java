package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.swing.internal.plaf.synth.resources.synth;

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
		String query = "SELECT p.product_id AS id, p.product_name AS name , c.category_name AS category , p.price AS price, "
				+ "p.description AS description, pc.category_name AS parent_category, p.image_url AS image "
				+ "FROM pisi.products AS p " + "JOIN pisi.animals AS a ON (p.animal_id = a.animal_id) "
				+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
				+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id) "
				+ "JOIN pisi.brands AS b ON(p.brand_id = b.brand_id) " + "WHERE p.animal_id = ?;";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, animalId);
			rs = stmt.executeQuery();
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
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
		
	}

	// this method returns all product for given animal id and given parent
	// category ID
	public Map<String, List<Product>> getProductsByAnimalAndParentCategory(long animalId, long parentCategoryId)
			throws SQLException {
		Map<String, List<Product>> temp = new HashMap<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , p.price,"
				+ " p.description, pc.category_name as parent_category, p.image_url as image "
				+ "	FROM pisi.products as p" + " JOIN pisi.animals as a ON (p.animal_id = a.animal_id)"
				+ " JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id)"
				+ " JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id)"
				+ " JOIN pisi.brands as b ON(p.brand_id = b.brand_id)"
				+ " WHERE c.parent_category_id = ? AND p.animal_id = ?;";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);){
			stmt.setLong(1, parentCategoryId);
			stmt.setLong(2, animalId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String category = rs.getString("category");

				if (!temp.containsKey(category)) {
					temp.put(category, new ArrayList<>());
				}
				double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
				Product p = new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
						rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image"));
				temp.get(category).add(p);

			}
			return temp;
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
		
	}

	// this method returns list of product for given animal id and sub-category
	// id;
	public List<Product> getProductsByAnimalAndSubCategory(long animalId, long categoryId) throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
				+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image "
				+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
				+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
				+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
				+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) "
				+ "WHERE p.product_category_id = ? AND p.animal_id = ?;";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, categoryId);
			stmt.setLong(2, animalId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				// check if there is a problem with returning a null from db for
				// rating !!!
				double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
				products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
						rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
			}
			return products;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
	}

	// this method returns a product by its ID;
	public Product getProduct(long productId) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT p.product_id as id , p.product_name as name, p.description as description, p.instock_count as unit, "
				+ " c.category_name as category, a.animal_name as animal, p.price as price, p.discount as discount,"
				+ " b.brand_name as brand, b.logo_image as brandlogo, p.image_url as image , AVG(r.rating) as rating "
				+ "	FROM pisi.products as p"
				+ " JOIN pisi.product_categories AS c ON (p.product_category_id = c.product_category_id)"
				+ " JOIN pisi.brands AS b ON (p.brand_id = b.brand_id)"
				+ " LEFT JOIN pisi.ratings as r ON (p.product_id = r. product_id) "
				+ " JOIN pisi.animals as a ON(p.animal_id = a.animal_id) WHERE p.product_id = ?;";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				Double rating = new Double(rs.getDouble("rating"));
				if (rating.equals(null)) {
					rating = new Double(0);
				}

				return new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"), rs.getInt("price"),
						rs.getString("animal"), rs.getString("category"), rs.getString("brand"), rs.getString("brandlogo"),
						rating, rs.getInt("unit"), rs.getString("image"), rs.getInt("discount"));
			} else {
				// TODO throw InvalidDataException
				return null;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
		
	}

	public List<Product> getProductsByBrand(int brand_id) throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
				+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image "
				+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
				+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
				+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
				+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) " + "WHERE p.brand_id =?;";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, brand_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				// check if there is a problem with returning a null from db for
				// rating !!!
				double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
				products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
						rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
			}
			return products;
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
		
	}

	public List<Product> getProductsInPromotion() throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query ="SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
				+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image "
				+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
				+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
				+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
				+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) " + "WHERE NOT (p.discount =0);";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);){
			rs = stmt.executeQuery();
			while (rs.next()) {
				// check if there is a problem with returning a null from db for
				// rating !!!
				double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
				products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
						rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
			}
			return products;
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
		
	}

	public List<Product> getFavorites(long userId) throws SQLException {
		List<Product> tempList = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT p.product_id as id , p.product_name as name, p.description as description, "
				+ " c.category_name as category, a.animal_name as animal, p.instock_count as unit, p.price as price,"
				+ " m.brand_name as brand, m.logo_image as brandImage, p.image_url as image, p.discount as discount  FROM pisi.products as p"
				+ " JOIN pisi.product_categories AS c ON (p.product_category_id = c.product_category_id)"
				+ " JOIN pisi.brands AS m ON (p.brand_id = m.brand_id)"
				+ " JOIN pisi.animals as a ON(p.animal_id = a.animal_id)"
				+ "	JOIN pisi.users_has_favorites AS cf ON(p.product_id = cf.product_id)"
				+ " WHERE p.product_id = ?" + " GROUP BY p.product_id";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();

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
		} catch (SQLException e) {
			throw e;
		} finally {
			
		}
		
		
	}

	public List<Product> searchProductByWord(String[] word) throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
				+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image "
				+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
				+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
				+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
				+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id)"
				+ "WHERE p.product_name LIKE ? OR p.description LIKE ? ";
		
		if(word.length != 1){
			for (int i = 0; i < word.length-1; i++) {
				query = query.concat(" or p.product_name LIKE ? OR p.description LIKE ?");
			}
		}
		query = query.concat(";");
		
		ResultSet rs = null;
		
		
		
		System.out.println(query);
		
		
		
		
		try (PreparedStatement stmt = con.prepareStatement(query);){
			int control = 1;
			for (int i = 0; i < word.length; i++) {
				stmt.setString(control++, "%"+word[i]+"%");
				stmt.setString(control++, "%"+word[i]+"%");
			}			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));				
				Product p = new Product().setName(rs.getString("name")).setDescription(rs.getString("description"))
						.setPrice(rs.getDouble("price")).setCategory(rs.getString("category")).setRating(rating)
						.setImage(rs.getString("image"));
				p.setId(rs.getLong("id"));				
				products.add(p);
			}
			return products;
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
	}

	public List<Product> getTopSoldProducts(int countLimit) throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT  op.product_id as id, p.product_name as name , a.animal_name as animal, "
				+ "c.category_name as category , p.price as price, p.description as description, "
				+ "p.image_url as image, SUM(op.product_quantity) as countSold"
				+ "FROM orders_has_products as op" + "JOIN pisi.products as p ON(op.product_id = p.product_id)"
				+ "JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
				+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
				+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
				+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id)"
				+ "GROUP by op.product_id ORDER BY SUM(op.product_quantity) desc LIMIT ?;";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, countLimit);
			rs = stmt.executeQuery();
			while (rs.next()) {
				// check if there is a problem with returning a null from db for
				// rating !!!
				double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
				products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
						rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
			}
			return products;
		} catch (SQLException e) {
			throw e;
		} finally {
			if( rs != null){
				rs.close();
			}
		}		
	}

	public List<Product> getTopSoldProductsByAnimal(int countLimit, int animalId) throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT  op.product_id as product_id, p.product_name as name , a.animal_name as animal,"
				+ "c.category_name as category , p.price, p.description, pc.category_name as parent_category,"
				+ "p.image_url as image, a.animal_id as animal, SUM(op.product_quantity) as countSold"
				+ "FROM orders_has_products as op" + "JOIN pisi.products as p ON(op.product_id = p.product_id)"
				+ "JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
				+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id)"
				+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
				+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id)"
				+ "GROUP by op.product_id HAVING a.animal_id=?"
				+ "ORDER BY SUM(op.product_quantity)  desc  LIMIT ? ;";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);){
			stmt.setInt(1, animalId);
			stmt.setInt(1, countLimit);
			rs = stmt.executeQuery();
			while (rs.next()) {
				// check if there is a problem with returning a null from db for
				// rating !!!
				double rating = RatingDao.getInstance().getProductRating(rs.getLong("id"));
				products.add(new Product(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
						rs.getDouble("price"), rs.getString("category"), rating, rs.getString("image")));
			}
			return products;
		} catch (SQLException e) {
			throw e;
		} finally {
			if( rs != null){
				rs.close();
			}
		}		
	}

	public double calcDiscountedPrice(Product p) {
		double newPrice = p.getPrice() * ((100 - p.getDiscount()) / 100.0);
		return newPrice;
	}

	// updating in stock quantity for product
	public synchronized boolean removeQuantity(long productId, int quantityToRemove) throws SQLException {
		int result = -1;
		Connection con = DBManager.getInstance().getConnection();
		String selectQuery = "SELECT instock_count FROM pisi.products WHERE product_id = ?";
		String updateQuery = "UPDATE pisi.products SET instock_count = ? WHERE product_id = ? ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// checks if there is enough quantity
			stmt = con.prepareStatement(selectQuery);
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			int currentInstockCount = rs.getInt("instock_count");
			if (quantityToRemove >= currentInstockCount) {
				stmt = con.prepareStatement(updateQuery);    // check later if there is problem with this !!!
				int newQuantity = currentInstockCount - quantityToRemove;
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
		} catch (SQLException e) {
			throw e;
		} finally {
			if( stmt != null){
				stmt.close();
			}
			if(rs != null){
				rs.close();
			}
		}		
	}

	// ***ADMIN operations***

	// add discount of product price
	public boolean setInPromotion(long productId, int percentDiscount) throws SQLException {
		Connection con = DBManager.getInstance().getAdminCon();
		String query = "UPDATE pisi.products SET discount = ? WHERE product_id = ? ";
		
		try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setLong(1, percentDiscount);
			stmt.setLong(2, productId);
			return stmt.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			throw e;
		}		
	}

	// adding quantity from admin
	public boolean addQuantity(long product_id, int quantity) throws SQLException {
		Connection con = DBManager.getInstance().getAdminCon();
		String query = "UPDATE pisi.products SET instock_count= instock_count + ? WHERE product_id=?;";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {				
			stmt.setInt(1, quantity);
			stmt.setLong(2, product_id);
			return stmt.executeUpdate() == 1 ? true : false;			
		} catch (SQLException e) {
			throw e;
		} finally {			
			if(rs != null){
				rs.close();
			}
		}		
	}
	

	// adding product from admin
	public synchronized void addProduct(Product p) throws SQLException {
		Connection con = DBManager.getInstance().getAdminCon();
		String query = "INSERT INTO pisi.products (product_name, animal_id, product_category_id, price, description, brand_id, instock_count, discount, image_url)"
				+ " VALUES (?,?,?,?,?,?,?,?,?)";
		ResultSet rs = null;
		
		con.setAutoCommit(false);
		try (PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);){

			int brandId = getBrandId(p.getBrand());
			
			// maybe not needed because we in-code all brand in our html
			if (brandId == -1) {
				brandId = insertBrand(p.getBrand(), p.getBrandImage());
			}
			
			int animalId = retrieveAnimalId(p.getAnimal());
			
			int categoryId = retrieveCategoryId(p.getCategory());
			
			if (categoryId == -1 || animalId == -1 || brandId <= 0) {
				// throws IvalidInputDataException
			}
			
			ps.setString(1, p.getName());
			ps.setInt(2, animalId);
			ps.setInt(3, categoryId);
			ps.setDouble(4, p.getPrice());
			ps.setString(5, p.getDescription());
			ps.setInt(6, brandId);
			ps.setInt(7, p.getInStock());
			ps.setInt(8, p.getDiscount());
			ps.setString(9, p.getImage());
			ps.executeUpdate();
			con.commit();
			rs = ps.getGeneratedKeys();
			rs.next();
			p.setId(rs.getLong(1));
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO error page....
				e1.printStackTrace();
			}
			throw e;
		} finally {
			con.setAutoCommit(true);
			if (rs != null){
				rs.close();
			}
		}
	}

	// remove product from admin
	public synchronized void removeProduct(Product p) throws SQLException {
		Connection con = DBManager.getInstance().getAdminCon();
		String queryOne = "DELETE FROM pisi.products WHERE product_id=?;";
		String queryTwo = "DELETE FROM pisi.ratings WHERE product_id=?;";
		String queryThree = "DELETE FROM pisi.users_has_favorites WHERE product_id=?;";
		String queryFour = "DELETE FROM pisi.products_has_animals WHERE product_id=?;";
		

		con.setAutoCommit(false);
		
		try (PreparedStatement psOne = con.prepareStatement(queryOne);
				PreparedStatement psTwo = con.prepareStatement(queryTwo);
				PreparedStatement psThree = con.prepareStatement(queryThree);
				PreparedStatement psFour = con.prepareStatement(queryFour)){
			
			psOne.setLong(1, p.getId());
			psOne.executeUpdate();
						
			psTwo.setLong(1, p.getId());
			psTwo.executeUpdate();
						
			psThree.setLong(1, p.getId());
			psThree.executeUpdate();
			
			psFour.setLong(1, p.getId());
			psFour.executeUpdate();
			
			con.commit();			
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO to error page
				e1.printStackTrace();
			}
			throw e;
		} finally {
			con.setAutoCommit(true);			
		}
	}

	// this method returns the id of brand if it exists in the database
	public int getBrandId(String brandName) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT brand_id as id FROM pisi.brands WHERE brand_name = ? ";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1,  brandName);
			rs = stmt.executeQuery();
			if (rs.next()) {			
				return rs.getInt("id");
			} 
			return -1;			
		} catch (SQLException e) {
			throw e;
		}finally {
			if(rs != null){
				rs.close();
			}
		}	
	}

	// inserts new brand, used when inserting new product if necessary
	public int insertBrand(String brandName, String brandImgUrl) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "INSERT INTO pisi.brands (brand_name, brand_image) VALUES (?,?)";
		ResultSet rs = null;
		try {
			PreparedStatement ps = con.prepareStatement( query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, brandName);
			ps.setString(2, brandImgUrl);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
	}

	// this method returns the animal id
	public int retrieveAnimalId(String animalName) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT animal_id as id FROM pisi.animals WHERE animal_name = ?";
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(query);){
			stmt.setString(1,  animalName.toUpperCase());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
			return -1;
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
		
	}

	// this method returns the animal id
	public int retrieveCategoryId(String categoryName) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT product_category_id as id FROM pisi.product_categories WHERE category_name = ?";
		ResultSet rs = null;
		
		try(PreparedStatement stmt = con.prepareStatement(query);){
			stmt.setString(1, categoryName.toUpperCase() );
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			} 
			return -1;
		} catch (SQLException e) {
			throw e;
		}finally {
			if(rs != null){
				rs.close();
			}
		}
		
	}

}
