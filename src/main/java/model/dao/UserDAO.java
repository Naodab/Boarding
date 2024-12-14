package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.bean.User;
import util.JDBCUtil;

public class UserDAO implements DAOInterface<User> {
	private static UserDAO _Instance;

	private UserDAO() {
	}

	public static UserDAO getInstance() {
		if (_Instance == null)
			_Instance = new UserDAO();
		return _Instance;
	}

	@Override
	public boolean insert(User t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO user (username, position, active, lastLogin)" + " VALUES (?, ?, ?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getUsername());
			pps.setString(2, t.getPosition());
			pps.setBoolean(3, t.isAvtive());
			pps.setDate(4, t.getLastLogin());
			result = pps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean delete(User t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM user WHERE username=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getUsername());
			result = pps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public boolean deleteByUsername(String t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM user WHERE username=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t);
			result = pps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean update(User t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE user SET password=?, position=?, active=?, lastLogin=? WHERE username=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getPassword());
			pps.setString(2, t.getPosition());
			pps.setBoolean(3, t.isAvtive());
			pps.setDate(4, t.getLastLogin());
			pps.setString(5, t.getUsername());
			result = pps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public boolean updatePassword(User t, String password) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE user SET password = ? WHERE username=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, password);
			pps.setString(2, t.getUsername());
			result = pps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public boolean updatePassword(String username, String password) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE user SET password = ? WHERE username=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, password);
			pps.setString(2, username);
			result = pps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public List<User> selectAll() {
		List<User> result = new ArrayList<User>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM user";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String position = rs.getString("position");
				boolean active = rs.getBoolean("active");
				Date lastLogin = rs.getDate("lastLogin");
				result.add(new User(username, password, position, active, lastLogin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public User selectById(User t) {
		User result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM user WHERE username = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getUsername());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String password = rs.getString("password");
				String position = rs.getString("position");
				boolean active = rs.getBoolean("active");
				Date lastLogin = rs.getDate("lastLogin");
				result = new User(t.getUsername(), password, position, active, lastLogin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public User selectByUserName(String username) {
		User result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM user WHERE username = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, username);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String password = rs.getString("password");
				String position = rs.getString("position");
				boolean active = rs.getBoolean("active");
				Date lastLogin = rs.getDate("lastLogin");
				result = new User(username, password, position, active, lastLogin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public boolean alterDefaultPassword(String password) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "ALTER TABLE user CHANGE password password VARCHAR(50) " + "NOT NULL DEFAULT \'" + password
					+ "\'";
			Statement stmt = conn.createStatement();
			result = stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public String getDefaultPassword() {
		String result = "";
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT COLUMN_DEFAULT " + "FROM INFORMATION_SCHEMA.COLUMNS " + "WHERE TABLE_NAME = \'user\' "
					+ "  AND COLUMN_NAME = \'password\' " + "  AND TABLE_SCHEMA = \'boarding\'";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next())
				result = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public boolean updateUsername(String oldUser, String newUser) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE user SET username=? WHERE username=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, newUser);
			pps.setString(2, oldUser);
			result = pps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<User> getPageUsers(int page, int amount,
    	String searchField, String search, String sortField, String sortType) {
		List<User> result = new ArrayList<>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM user ";
			if (search != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ?";
			}
			if (sortType != null) {
				sql += " ORDER BY " + sortField + " " + sortType;
			}
			sql += " LIMIT ? OFFSET ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			int index = 1;
			if (search != null) {
				pps.setString(index++, search);
			}
			pps.setInt(index++, amount);
			pps.setInt(index++, page * amount);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String position = rs.getString("position");
				boolean active = rs.getBoolean("active");
				Date lastLogin = rs.getDate("lastLogin");
				result.add(new User(username, password, position, active, lastLogin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public int count(String searchField, String search) {
		int result = -1;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT count(*) AS total FROM user";
			if (search != null) {
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