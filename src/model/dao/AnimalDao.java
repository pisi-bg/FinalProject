package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.db.DBManager;

// not sure if needed, just trying to make a loop in jsp with animals name..... 

public class AnimalDao {
	
	private static AnimalDao instance;

	private AnimalDao() {
	}

	public static synchronized AnimalDao getInstance() {
		if (instance == null) {
			instance = new AnimalDao();
		}
		return instance;
	}
	
	public List<String> getAnimal() throws SQLException{
		List<String> animals = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		
		PreparedStatement stmt = con.prepareStatement("SELECT animal_name AS name FROM pisi.animals ORDER BY animal_name ASC");
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			animals.add(rs.getString("name"));
		}
		return animals;
	}

}
