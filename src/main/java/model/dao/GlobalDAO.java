package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Food;
import model.bean.Menu;
import util.JDBCUtil;

public class GlobalDAO {
	private static GlobalDAO _Instance;

	private GlobalDAO() {
	}

	public static GlobalDAO getInstance() {
		if (_Instance == null)
			_Instance = new GlobalDAO();
		return _Instance;
	}

	public List<String> search(String table, String column, String condition, int amount) {
		List<String> result = new ArrayList<String>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT " + column + " FROM " + table;
			if (condition != null)
				if (condition.length() != 0)
					sql += " WHERE " + condition;
			if (amount > 0)
				sql += " LIMIT " + amount;
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next())
				result.add(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<String> sort(String table, String column, String condition, int amount, String isDESC) {
		List<String> result = new ArrayList<String>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String id = table + "_id";
			if (table.equals("User"))
				id = "username";
			String sql = "SELECT " + id + " FROM " + table;
			if (condition.length() != 0)
				sql += " WHERE " + condition;
			sql += " ORDER BY " + column + isDESC;
			if (amount > 0)
				sql += " LIMIT " + amount;
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public int getSizeOf(String table, String condition) {
		int result = 0;
		String id = table + "_id";
		if (table.equals("User"))
			id = "username";
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT COUNT(" + id + ") FROM " + table;
			if (condition != null)
				if (condition.length() != 0)
					sql += " WHERE " + condition;
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public int getLastIDOf(String table) {
		int result = 0;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT " + table + "_id FROM " + table + " ORDER BY " + table + "_id DESC LIMIT 1";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public int getFirstIDOf(String table) {
		int result = 0;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT " + table + "_id FROM " + table + " ORDER BY " + table + "_id LIMIT 1";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public boolean updateNULLForeignKey(String table, String column, String condition) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE " + table + " SET " + column + "=NULL WHERE " + condition;
			PreparedStatement pps = conn.prepareStatement(sql);
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public int getAuto_IncrementOf(String table) {
		int result = 0;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME=? AND TABLE_SCHEMA=\'boarding\'";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, table);
			ResultSet rs = pps.executeQuery();
			while (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<String> getSortNumberOfStudents(String condition, int amount, String isDESC) {
		List<String> result = new ArrayList<String>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT B.boardingClass_id, COUNT(S.Student_id) AS C "
					+ "FROM student AS S RIGHT JOIN boardingClass AS B " + "ON S.boardingClass_id = B.boardingClass_id";
			if (condition != null)
				if (condition.length() != 0)
					sql += " WHERE " + condition;
			sql += " GROUP BY boardingClass_id ORDER BY C" + isDESC;
			if (amount > 0)
				sql += " LIMIT " + amount;
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next())
				result.add(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<String> getSortNumberOfMenu(String condition, int amount, String isDESC) {
		List<String> result = new ArrayList<String>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT F.food_id, COUNT(M.menu_id) AS C FROM food AS F LEFT JOIN Food_Menu AS M "
					+ "ON F.food_id = M.food_id ";
			if (condition != null)
				if (condition.length() != 0)
					sql += "WHERE " + condition;
			sql += " GROUP BY F.food_id ORDER BY C " + isDESC;
			if (amount > 0)
				sql += " LIMIT " + amount;
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<String> searchMainFoodInMenu(String condition) {
		List<String> result = new ArrayList<String>();
		List<Menu> allMenu = MenuDAO.getInstance().selectAll();
		for (Menu menu : allMenu) {
			String strfood = "";
			for (int food_id : menu.getFood_ids()) {
				Food food = FoodDAO.getInstance().selectById(food_id);
				if (food.getCategory())
					strfood += food.getName();
			}
			if (strfood.contains(condition)) {
				result.add(menu.getMenu_id() + "");
			}
		}
		return result;
	}

	public List<String> searchSubFoodInMenu(String condition) {
		List<String> result = new ArrayList<String>();
		List<Menu> allMenu = MenuDAO.getInstance().selectAll();
		for (Menu menu : allMenu) {
			String strfood = "";
			for (int food_id : menu.getFood_ids()) {
				Food food = FoodDAO.getInstance().selectById(food_id);
				if (!food.getCategory())
					strfood += food.getName();
			}
			if (strfood.contains(condition)) {
				result.add(menu.getMenu_id() + "");
			}
		}
		return result;
	}
}
