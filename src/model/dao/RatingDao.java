package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.db.DBManager;

public class RatingDao {

	private static RatingDao instance;

	private RatingDao() {
	}

	public static synchronized RatingDao getInstance() {
		if (instance == null) {
			instance = new RatingDao();
		}
		return instance;
	}

	public double getProductRating(long productId) throws SQLException {

		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT AVG(r.rating) AS rating FROM pisi.ratings AS r WHERE r.product_id = ?";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = con.prepareStatement(query);){
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			rs.next();			
			return rs.getDouble("rating");
		} catch (SQLException e) {
			throw e;
		}finally {
			if(rs != null){
				rs.close();
			}
		}
		
	}

}
