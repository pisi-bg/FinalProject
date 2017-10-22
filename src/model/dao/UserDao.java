package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import model.db.DBManager;
import model.pojo.User;

public class UserDao {

	private static UserDao instance;

	private UserDao() {
	}

	public static synchronized UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	// this method insert a product to user favorites
	public boolean insertFavorite(User u, long product_id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "INSERT INTO pisi.users_has_favorites (user_id, product_id) VALUES (?,?)";
		int result = 0;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, u.getId());
			stmt.setLong(2, product_id);
			result = stmt.executeUpdate();
			// add product to the User POJO to be keep in session
			// u.addToFavorites(ProductDao.getInstance().getProduct(product_id));

		} catch (SQLException e) {
			throw e;
		}

		return result == 1 ? true : false;

	}

	// this method removes product from user favorites
	public boolean removeFavorite(User u, long product_id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "DELETE FROM pisi.users_has_favorites WHERE user_id = ? and product_id = ? ";
		int result = 0;

		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setLong(1, u.getId());
			stmt.setLong(2, product_id);
			result = stmt.executeUpdate();

			// remove product from the User POJO to be keep in session
			// u.removeFromFavorites(ProductDao.getInstance().getProduct(product_id));
		} catch (SQLException e) {
			throw e;
		}
		return result == 1 ? true : false;
	}

	// this method insert user info to database
	public void insertUser(User u) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "INSERT INTO pisi.users (first_name,last_name,email,password,gender, isAdmin) VALUES (?,?,?,?,?,?)";
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getPassword());
			if (u.isMale()) {
				ps.setInt(5, 1);
			} else {
				ps.setInt(5, 0);
			}
			if (u.isAdmin()) {
				ps.setInt(6, 1);
			} else {
				ps.setInt(6, 0);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			u.setId(rs.getLong(1));
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	// this method checks if this is a valid email
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	// this method check if user exists in the database
	public boolean userExist(User u) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT first_name as name FROM pisi.users WHERE email = ? AND password = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, u.getEmail());
			stmt.setString(2, u.getPassword());
			rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// return user by email
	public User getUser(String email) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT user_id as id, first_name , last_name, password, gender, isAdmin as admin"
				+ " FROM pisi.users WHERE email = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			rs.next();
			User u = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), email,
					rs.getString("password"), rs.getBoolean("gender"), rs.getBoolean("admin"),
					ProductDao.getInstance().getFavorites(rs.getLong("id")));
			return u;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	// update user data, no need to return User because we will already have it
	// in the servlet
	public boolean updateUser(User u) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "UPDATE pisi.users SET first_name = ?, last_name = ?, email = ?, password = ?, isAdmin = ?, gender= ? WHERE users_id= ?;";

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, u.getFirstName());
			stmt.setString(2, u.getLastName());
			stmt.setString(3, u.getEmail());
			stmt.setString(4, u.getPassword());
			stmt.setBoolean(5, u.isAdmin());
			stmt.setBoolean(6, u.isMale());
			stmt.setLong(7, u.getId());
			return stmt.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			throw e;
		}

	}
}