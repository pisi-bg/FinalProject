package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DBManager;

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
	
	public double getProductRating(long produc_id) throws SQLException{
		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT AVG(r.rating) AS rating FROM pisi.ratings AS r WHERE r.product_id = ?");
		stmt.setLong(1, produc_id);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		double rating = rs.getDouble("rating");
										// check if there is exception when DB return null !!!
		return rating;
	}

}
