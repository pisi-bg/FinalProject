package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.db.DBManager;

public class ImageDao {

	private static ImageDao instance;

	private ImageDao() {
	}

	public static synchronized ImageDao getInstance() {
		if (instance == null) {
			instance = new ImageDao();
		}
		return instance;
	}

	// TODO return all images for given product ID
	public List<String> getImagesForProduct(long product_id) throws SQLException {
		List<String> images = new ArrayList<>();
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT image_url AS url FROM pisi.images WHERE product_id = ?";
		ResultSet rs = null;
		try (PreparedStatement ps = con.prepareStatement(query);) {
			ps.setLong(1, product_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				images.add(rs.getString("url"));
			}
			return images;
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		
		
	}

}
