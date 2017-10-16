package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DBManager;

public class ImageDAO {

	private static ImageDAO instance;
	private ImageDAO(){}
	public static synchronized ImageDAO getInstance(){
		if(instance == null){
			instance = new ImageDAO();
		}
		return instance;
	}
		
	// TODO return all images for given product ID
	public List<String> getImagesForProduct(long product_id) throws SQLException {
		List<String> images = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT image_url AS url FROM pisi.images WHERE product_id = ?");
		ps.setLong(1, product_id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			images.add(rs.getString("url"));
		}
				
		return images;
	}

}
