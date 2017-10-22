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

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getDouble("rating");
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	public boolean productHasRatingFromUser(long productId, long userId) throws SQLException {

		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT rating FROM pisi.ratings WHERE product_id = ? AND user_id = ? ;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			stmt.setLong(2, userId);
			rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public boolean addProductRating(long productId, long userId, double rating) throws SQLException {

		Connection con = DBManager.getInstance().getConnection();
		String query = "INSERT INTO pisi.ratings (product_id, user_id, rating) VALUES (?,?,?)";
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query);) {
			ps.setInt(1, (int) productId);
			ps.setInt(2, (int) userId);
			ps.setDouble(3, rating);
			int result = ps.executeUpdate();
			return result == 1 ? true : false;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

}
