package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Menu;
import util.JDBCUtil;

public class MenuDAO implements DAOInterface<Menu>{
	private static MenuDAO _Instance;
	
	private MenuDAO() {
	}
	
	public static MenuDAO getInstance() {
		if (_Instance == null) _Instance = new MenuDAO();
		return _Instance;
	}

	@Override
	public boolean insert(Menu t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO menu (active) VALUES (?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setBoolean(1, t.isActive());
			int check = pps.executeUpdate();
			if (check > 0) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		for (int food_id : t.getFood_ids()) {
			Food_MenuDAO.getInstance().insert(t.getMenu_id(), food_id);
		}
		return result;
	}

	@Override
	public boolean delete(Menu t) {
		boolean result = false;
		Food_MenuDAO.getInstance().deleteByMenuId(t.getMenu_id());
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM menu WHERE menu_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getMenu_id());
			int check = pps.executeUpdate();
			if (check > 0)
				result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		Food_MenuDAO.getInstance().deleteByMenuId(t.getMenu_id());
		return result;
	}

	@Override
	public boolean update(Menu t) {
		boolean result = Food_MenuDAO.getInstance().deleteByMenuId(t.getMenu_id());
		if (!result)
			return result;
		for (int food_id : t.getFood_ids()) {
			result = Food_MenuDAO.getInstance().insert(t.getMenu_id(), food_id);
			if (!result)
				return result;
		}
		result = updateActive(t.getMenu_id(), t.isActive());
		return result;
	}
	
	public boolean updateActive(int menu_id, boolean active) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE menu SET active = ? WHERE menu_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setBoolean(1, active);
			pps.setInt(2, menu_id);
			System.out.println(pps.toString());
			int check = pps.executeUpdate();
			if (check > 0) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public List<Menu> selectAll() {
		List<Menu> result = new ArrayList<Menu>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM menu";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int menu_id = rs.getInt("menu_id");
				boolean active = rs.getBoolean("active");
				result.add(new Menu(menu_id, active, null, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		for (Menu menu : result) {
			menu.setEatingHistory_ids(EatingHistoryDAO.getInstance().selectByMenu_id(menu.getMenu_id()));
			menu.setFood_ids(Food_MenuDAO.getInstance().selectByMenuId(menu.getMenu_id()));
		}
		return result;
	}

	@Override
	public Menu selectById(Menu t) {
		Menu result = new Menu();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM menu WHERE menu_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getMenu_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int menu_id = rs.getInt("menu_id");
				boolean active = rs.getBoolean("active");
				result = new Menu(menu_id, active, null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		result.setEatingHistory_ids(EatingHistoryDAO.getInstance().selectByMenu_id(t.getMenu_id()));
		result.setFood_ids(Food_MenuDAO.getInstance().selectByMenuId(t.getMenu_id()));
		return result;
	}

	public Menu selectById(int t) {
		Menu result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM menu WHERE menu_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int menu_id = rs.getInt("menu_id");
				boolean active = rs.getBoolean("active");
				result = new Menu(menu_id, active, null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (result != null) {
			result.setEatingHistory_ids(EatingHistoryDAO.getInstance().selectByMenu_id(t));
			result.setFood_ids(Food_MenuDAO.getInstance().selectByMenuId(t));
		}
		return result;
	}

	public List<Menu> getPage(int page, int amount, String searchField, String search) {
		List<Menu> result = new ArrayList<>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM menu";
			if (searchField != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ?";
			}
			sql += " LIMIT ? OFFSET ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			int index = 1;
			if (search != null) {
				pps.setString(index++, search);
			}
			pps.setInt(index++, amount);
			pps.setInt(index, page * amount);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int menu_id = rs.getInt("menu_id");
				boolean active = rs.getBoolean("active");
				result.add(new Menu(menu_id, active, null, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		for (Menu menu : result) {
			menu.setEatingHistory_ids(EatingHistoryDAO.getInstance().selectByMenu_id(menu.getMenu_id()));
			menu.setFood_ids(Food_MenuDAO.getInstance().selectByMenuId(menu.getMenu_id()));
		}
		return result;
	}

	public int count(String searchField, String search) {
		int result = -1;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT count(*) AS total FROM menu";
			if (searchField != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ?";
			}
			PreparedStatement pps = conn.prepareStatement(sql);
			if (search != null) {
				pps.setString(1, search);
			}
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
}
