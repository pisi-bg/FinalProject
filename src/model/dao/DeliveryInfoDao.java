package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.db.DBManager;
import model.pojo.DeliveryInfo;

public class DeliveryInfoDao {

	private static DeliveryInfoDao instance;

	private DeliveryInfoDao() {
	}

	public static synchronized DeliveryInfoDao getInstance() {
		if (instance == null) {
			instance = new DeliveryInfoDao();
		}
		return instance;
	}

	public void insertDelivInfoOrder(DeliveryInfo delivInfo) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "INSERT INTO pisi.deliveries_info_id ( address, zip_code, city_id, reciever_first_name, reciever_last_name, reciever_phone, notes) VALUES (?,?,?,?,?,?,?)";
		String cityName = delivInfo.getCity();
		int cityId = retrieveCityId(cityName);
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, delivInfo.getAddress());
			ps.setInt(2, delivInfo.getZipCode());
			ps.setInt(3, cityId);
			ps.setString(4, delivInfo.getRecieverFirstName());
			ps.setString(5, delivInfo.getRecieverLastName());
			ps.setString(6, delivInfo.getRecieverPhone());
			ps.setString(7, delivInfo.getNotes());
			int result = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			delivInfo.setDeliveryInfoId(rs.getLong(1));
		} catch (SQLException e) {
			throw e;
		} finally {
			rs.close();
		}
	}

	public ArrayList<DeliveryInfo> getListDeliveryInfosForUser(long userId) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT delivery_info_id FROM pisi.orders WHERE user_id =?;";
		ResultSet rs = null;
		ArrayList<DeliveryInfo> listDeliveries = null;
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();
			listDeliveries = new ArrayList<>();
			while (rs.next()) {
				listDeliveries.add(DeliveryInfoDao.getInstance().getDeliveryInfo(rs.getInt("delivery_info_id")));
			}
			return listDeliveries;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public DeliveryInfo getDeliveryInfo(long deliveryInfoId) throws SQLException {

		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT *  FROM pisi.deliveries_info_id JOIN pisi.cities AS c USING (city_id) WHERE delivery_info_id=?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, deliveryInfoId);
			rs = stmt.executeQuery();
			rs.next();
			DeliveryInfo delInfo = new DeliveryInfo(deliveryInfoId, rs.getString("address"), rs.getInt("zip_code"),
					rs.getString("city_name"), rs.getString("reciever_first_name"), rs.getString("reciever_last_name"),
					rs.getString("reciever_phone"), rs.getString("notes"));
			return delInfo;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public int retrieveCityId(String cityName) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String query = "SELECT city_id as id FROM pisi.cities WHERE city_name = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, cityName.toUpperCase());
			// TODO upgrate cases in DB
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			} else
				return -1; // throw exception
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}
}
