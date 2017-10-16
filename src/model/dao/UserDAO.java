package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import model.DBManager;
import model.pojo.Product;
import model.pojo.User;

public class UserDAO {
	private static UserDAO instance;
	private UserDAO(){}
	public static synchronized UserDAO getInstance(){
		if(instance == null){
			instance = new UserDAO();
		}
		return instance;
	}
	
	//this method insert a product to user favorites
	public boolean insertFavorite(User u, long product_id) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO pisi.client_has_favorites (client_id, product_id) VALUES (?,?)");
		stmt.setLong(1, u.getId());
		stmt.setLong(2, product_id);
		int result = stmt.executeUpdate();
		
		//add product to the User POJO to be keep in session
		u.addToFavorites(ProductDAO.getInstance().getProduct(product_id));
		
		return result == 1 ? true : false;
	}
	
	//this method removes product from user favorites
	public boolean removeFavorite(User u, long product_id) throws SQLException{		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM pisi.client_has_favorites WHERE client_id = ? and product_id = ? ");
		stmt.setLong(1, u.getId());
		stmt.setLong(2, product_id);
		int result = stmt.executeUpdate();
		
		//remove product from the User POJO to be keep in session
		u.removeFromFavorites(ProductDAO.getInstance().getProduct(product_id));
		
		return result == 1 ? true : false;
		
	}
		
	// this method insert user info to database
	public void insertUser(User u) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO pisi.client (first_name,last_name,email,password,gender, isAdmin) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, u.getFirstName());
		ps.setString(2, u.getLastName());
		ps.setString(3, u.getEmail());
		ps.setString(4, u.getPassword());
		if(u.isMale()){
			ps.setInt(5, 1);
		}else {
			ps.setInt(5, 0);
		}
		if(u.isAdmin()){
			ps.setInt(6, 1);
		}else {
			ps.setInt(6, 0);
		}
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		u.setId(rs.getLong(1));
		
	}
	
	//this method check if user exists in the database
	public boolean userExist(User u) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT first_name as name FROM pisi.client WHERE email = ?");
		stmt.setString(1, u.getEmail());
		ResultSet rs = stmt.executeQuery();		
		return rs.next();
	}
	
	//this method checks if this i a valid email 
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
	
	//this method checks if this password is right for this email
	public boolean verifiedPassword(User u) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT first_name as name FROM pisi.client WHERE email = ? AND password = ?");
		stmt.setString(1, u.getEmail());
		stmt.setString(2, u.getPassword());
		ResultSet rs = stmt.executeQuery();		
		return rs.next();
	}
	
	//return user by email
	public User getUser(String email) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();		
		PreparedStatement stmt = con.prepareStatement("SELECT client_id as id, first_name , last_name, password, gender, isAdmin as admin  FROM pisi.client WHERE email = ?");
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		User u = new User(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), email, rs.getString("password"), rs.getBoolean("gender"), rs.getBoolean("admin"), ProductDAO.getInstance().getFavorites(rs.getLong("id")));
		return u;
	}
	
	// update user data, no need to return User because we will already have it in the servlet
	public boolean updateUser(User u) throws SQLException{		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE pisi.client SET first_name = ?, last_name = ?, email = ?, password = ?, isAdmin = ?, gender= ? WHERE client_id= ?;");
		stmt.setString(1, u.getFirstName());
		stmt.setString(2, u.getLastName());
		stmt.setString(3, u.getEmail());
		stmt.setString(4, u.getPassword());
		stmt.setBoolean(5, u.isAdmin());
		stmt.setBoolean(6, u.isMale());
		stmt.setLong(7, u.getId());
		return stmt.executeUpdate() == 1 ? true : false;		
	}

}
