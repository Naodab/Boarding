package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;

public class Food_MenuDAO {
	private static Food_MenuDAO _Instance;
	
	private Food_MenuDAO () {
	}
	
	public static Food_MenuDAO getInstance() {
		if(_Instance == null) _Instance = new Food_MenuDAO();
		return _Instance;
	}
	
	public boolean insert(int menu_id, int food_id) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO food_menu (menu_id, food_id) VALUES (?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, menu_id);
			pps.setInt(2, food_id);
			System.out.println(pps.toString());
			int check = pps.executeUpdate();
			if (check > 0) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public boolean deleteByFoodId(int food_id) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM food_menu WHERE food_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, food_id);
			int check = pps.executeUpdate();
			if (check > 0) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public boolean deleteByMenuId(int menu_id) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM food_menu WHERE menu_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, menu_id);
			int check = pps.executeUpdate();
			if (check > 0) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public List<Integer> selectByFoodId (int food_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT menu_id FROM food_menu WHERE food_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, food_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt("menu_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public List<Integer> selectByMenuId (int menu_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT food_id FROM food_menu WHERE menu_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, menu_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt("food_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
}
