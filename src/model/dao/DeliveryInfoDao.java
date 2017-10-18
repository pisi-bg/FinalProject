package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.db.DBManager;
import model.pojo.DeliveryInfo;

public class DeliveryInfoDao {

	public long insertDelivInfoOrder(long orderId, DeliveryInfo delivInfo) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();

		String cityName = delivInfo.getCity();
		int cityId = retrieveCityId(cityName);

		PreparedStatement ps = con.prepareStatement(
				"INSERT INTO pisi.deliveries_info_id ( address, zip_code, city_id, reciever_first_name, reciever_last_name, reciever_phone, notes) VALUES (?,?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, delivInfo.getAddress());
		ps.setInt(2, delivInfo.getZipCode());
		ps.setInt(3, cityId);
		ps.setString(4, delivInfo.getRecieverFirstName());
		ps.setString(5, delivInfo.getRecieverLastName());
		ps.setString(6, delivInfo.getRecieverPhone());
		ps.setString(7, delivInfo.getNotes());
		int result = ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		delivInfo.setDeliveryInfoId(rs.getLong(1));
		return rs.getLong(1);
	}

	public int retrieveCityId(String cityName) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT city_id as id FROM pisi.cities WHERE city_name = ?");
		stmt.setString(1, cityName.toUpperCase());
		// TODO upgrate cases in DB
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("id");
		} else
			return -1; // throw exception
	}
}
