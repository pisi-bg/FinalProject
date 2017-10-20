package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 
public class DBManager {

	private static DBManager instance;
	
	private Connection con;
	private Connection adminCon;

	// constructor
	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found or failed to load. Check your libraries");
		}

		final String DB_IP = "localhost";
		final String DB_PORT = "3306";
		final String DB_DBNAME = "pisi";
		final String DB_USER = "root";
		final String DB_PASS = "balonche1";

		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_DBNAME, DB_USER,
					DB_PASS);
		} catch (SQLException e) {
			// TODO handle exception
			System.out.println("Ops" + e.getMessage());
		}

	}
	
	// return only instance of this class
	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	// return connection to the database
	public Connection getConnection() {
		return con;
	}
	
	// return admin connection to the database
	public Connection getAdminCon() {
		return adminCon;
	}

	// close connection to the database when server is shutdown
	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO handle exception
				e.printStackTrace();
			}
		}
	}

}
