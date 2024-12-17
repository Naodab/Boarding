package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Parents;
import util.JDBCUtil;

public class ParentsDAO implements DAOInterface<Parents> {

	private static ParentsDAO _Instance;

	private ParentsDAO() {
	}

	public static ParentsDAO getInstance() {
		if (_Instance == null)
			_Instance = new ParentsDAO();
		return _Instance;
	}

	@Override
	public boolean insert(Parents t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO parents (name, dateOfBirth, address, sex, phoneNumber, email)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setDate(2, t.getDateOfBirth());
			pps.setString(3, t.getAddress());
			pps.setBoolean(4, t.getSex());
			pps.setString(5, t.getPhoneNumber());
			pps.setString(6, t.getEmail());
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean delete(Parents t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM parents WHERE parents_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getParents_id());
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public boolean deleteByID(int t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM parents WHERE parents_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean update(Parents t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE parents SET name = ?, dateOfBirth = ?, address = ?, sex = ?, phoneNumber = ?, email = ?"
					+ " WHERE parents_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setDate(2, t.getDateOfBirth());
			pps.setString(3, t.getAddress());
			pps.setBoolean(4, t.getSex());
			pps.setString(5, t.getPhoneNumber());
			pps.setString(6, t.getEmail());
			pps.setInt(7, t.getParents_id());
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public List<Parents> selectAll() {
		List<Parents> result = new ArrayList<Parents>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM parents";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int parents_id = rs.getInt("parents_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				result.add(new Parents(name, dateOfBirth, address, sex, parents_id, phoneNumber, email, null));
			}
			rs.close();
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		for (Parents pa : result) {
			pa.setStudent_id(StudentDAO.getInstance().selectByParents_id(pa.getParents_id()));
		}
		return result;
	}

	@Override
	public Parents selectById(Parents t) {
		Parents result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM parents WHERE parents_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getParents_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int parents_id = rs.getInt("parents_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				result = new Parents(name, dateOfBirth, address, sex, parents_id, phoneNumber, email, null);
			}
			rs.close();
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		result.setStudent_id(StudentDAO.getInstance().selectByParents_id(result.getParents_id()));
		return result;
	}
	
	public List<Parents> getPageParents(int page, int amount, String searchField, String search,
			String sortField, String sortType) {
		List<Parents> result = new ArrayList<Parents>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM parents ";
			if (search != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ? AND parents_id != 0";
			} else {
				sql += " WHERE parents_id != 0";
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
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int parents_id = rs.getInt("parents_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				result.add(new Parents(name, dateOfBirth, address, sex, parents_id, phoneNumber, email, null));
			}
			rs.close();
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public Parents selectById(int t) {
		Parents result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM parents WHERE parents_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int parents_id = rs.getInt("parents_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				result = new Parents(name, dateOfBirth, address, sex, parents_id, phoneNumber, email, null);
			}
			rs.close();
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (result != null)
			result.setStudent_id(StudentDAO.getInstance().selectByParents_id(result.getParents_id()));
		return result;
	}

	public Parents selectByUsername(String username) {
		Parents result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM parents WHERE phoneNumber = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, username);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int parents_id = rs.getInt("parents_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				result = new Parents(name, dateOfBirth, address, sex, parents_id, phoneNumber, email, null);
			}
			rs.close();
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (result != null)
			result.setStudent_id(StudentDAO.getInstance().selectByParents_id(result.getParents_id()));
		return result;
	}

	public int count(String searchField, String search) {
		int result = -1;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT count(*) AS total FROM parents";
			if (search != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ? AND parents_id != 0";
			} else {
				sql += " WHERE parents_id != 0";
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
