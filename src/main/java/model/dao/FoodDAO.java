package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Food;
import util.JDBCUtil;

public class FoodDAO implements DAOInterface<Food> {
	private static FoodDAO _Instance;

	private FoodDAO() {
	}

	public static FoodDAO getInstance() {
		if (_Instance == null)
			_Instance = new FoodDAO();
		return _Instance;
	}

	@Override
	public boolean insert(Food t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO food (name, category) VALUES (?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setBoolean(2, t.getCategory());
			int check = pps.executeUpdate();
			if (check > 0)
				result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (t.getMenu_ids() != null)
			for (int menu_id : t.getMenu_ids()) {
				Food_MenuDAO.getInstance().insert(menu_id, t.getFood_id());
			}
		return result;
	}

	@Override
	public boolean delete(Food t) {
		boolean result = false;

		Food_MenuDAO.getInstance().deleteByFoodId(t.getFood_id());

		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM food WHERE food_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getFood_id());
			int check = pps.executeUpdate();
			if (check > 0)
				result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean update(Food t) {
		boolean result = false;
		Food_MenuDAO.getInstance().deleteByFoodId(t.getFood_id());
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE food SET name=?, category=? WHERE food_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setBoolean(2, t.getCategory());
			pps.setInt(3, t.getFood_id());
			int check = pps.executeUpdate();
			if (check > 0)
				result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (t.getMenu_ids() != null)
			for (int menu_id : t.getMenu_ids()) {
				Food_MenuDAO.getInstance().insert(menu_id, t.getFood_id());
			}
		return result;
	}

	@Override
	public List<Food> selectAll() {
		List<Food> result = new ArrayList<Food>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM food";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int food_id = rs.getInt("food_id");
				String name = rs.getString("name");
				boolean category = rs.getBoolean("category");
				result.add(new Food(food_id, name, category, new ArrayList<Integer>()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public Food selectById(Food t) {
		Food result = new Food();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM food WHERE food_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getFood_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.setFood_id(rs.getInt("food_id"));
				result.setName(rs.getString("name"));
				result.setCategory(rs.getBoolean("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		result.setMenu_ids(Food_MenuDAO.getInstance().selectByFoodId(t.getFood_id()));
		return result;
	}

	public Food selectById(int t) {
		Food result = new Food();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM food WHERE food_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.setFood_id(rs.getInt("food_id"));
				result.setName(rs.getString("name"));
				result.setCategory(rs.getBoolean("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		result.setMenu_ids(Food_MenuDAO.getInstance().selectByFoodId(t));
		return result;
	}

	public List<Food> selectByCategory(boolean category) {
		List<Food> result = new ArrayList<Food>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM food WHERE category = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setBoolean(1, category);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int food_id = rs.getInt("food_id");
				String name = rs.getString("name");
				result.add(new Food(food_id, name, category, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
}
