package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import model.bean.Teacher;
import util.JDBCUtil;

public class TeacherDAO implements DAOInterface<Teacher> {
	private static TeacherDAO _Instance;

	private TeacherDAO() {
	}

	public static TeacherDAO getInstance() {
		if (_Instance == null)
			_Instance = new TeacherDAO();
		return _Instance;
	}

	@Override
	public boolean insert(Teacher t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO teacher (name, dateOfBirth, address, "
					+ "sex,  phoneNumber, email, boardingClass_id)" + " VALUES ( ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setDate(2, t.getDateOfBirth());
			pps.setString(3, t.getAddress());
			pps.setBoolean(4, t.getSex());
			pps.setString(5, t.getPhoneNumber());
			pps.setString(6, t.getEmail());
			pps.setInt(7, t.getBoardingClass_id());
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean delete(Teacher t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM teacher WHERE teacher_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getTeacher_id());
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

	public boolean deleteById(int t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM teacher WHERE teacher_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
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

	@Override
	public boolean update(Teacher t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE teacher SET name = ?, dateOfBirth = ?, address = ?, sex = ?, "
					+ "phoneNumber = ?, email = ?, boardingClass_id = ? WHERE teacher_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setDate(2, t.getDateOfBirth());
			pps.setString(3, t.getAddress());
			pps.setBoolean(4, t.getSex());
			pps.setInt(8, t.getTeacher_id());
			pps.setString(5, t.getPhoneNumber());
			pps.setString(6, t.getEmail());
			pps.setInt(7, t.getBoardingClass_id());

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

	@Override
	public List<Teacher> selectAll() {
		List<Teacher> result = new ArrayList<Teacher>();
		Connection conn = JDBCUtil.getConnection();
		try {

			String sql = "SELECT * FROM teacher";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int teacher_id = rs.getInt("teacher_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				int boardingClass_id = rs.getInt("boardingClass_id");
				result.add(
						new Teacher(name, dateOfBirth, address, sex, teacher_id, phoneNumber, email, boardingClass_id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public Teacher selectById(Teacher t) {
		Connection conn = JDBCUtil.getConnection();
		Teacher result = new Teacher();
		try {
			String sql = "SELECT * FROM Teacher WHERE teacher_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getTeacher_id());
			ResultSet rs = pps.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int teacher_id = rs.getInt("teacher_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				int boardingClass_id = rs.getInt("boardingClass_id");
				result = new Teacher(name, dateOfBirth, address, sex, teacher_id, phoneNumber, email, boardingClass_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public Teacher selectByUsername(String username) {
		Connection conn = JDBCUtil.getConnection();
		Teacher result = new Teacher();
		try {
			String sql = "SELECT * FROM Teacher WHERE phoneNumber=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, username);
			ResultSet rs = pps.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int teacher_id = rs.getInt("teacher_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				int boardingClass_id = rs.getInt("boardingClass_id");
				result = new Teacher(name, dateOfBirth, address, sex, teacher_id, phoneNumber, email, boardingClass_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public List<Teacher> selectPage(int page, int amount, String searchField,
									String search, String sortField, String sortType) {
		List<Teacher> result = new ArrayList<Teacher>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM teacher ";
			if (search != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ? AND teacher_id != 0";
			} else {
				sql += " WHERE teacher_id != 0";
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
				int teacher_id = rs.getInt("teacher_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				int boardingClass_id = rs.getInt("boardingClass_id");
				result.add(
						new Teacher(name, dateOfBirth, address, sex, teacher_id, phoneNumber, email, boardingClass_id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public Teacher selectById(int t) {
		Connection conn = JDBCUtil.getConnection();
		Teacher result = new Teacher();
		try {
			String sql = "SELECT * FROM Teacher WHERE teacher_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int teacher_id = rs.getInt("teacher_id");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				int boardingClass_id = rs.getInt("boardingClass_id");
				result = new Teacher(name, dateOfBirth, address, sex, teacher_id, phoneNumber, email, boardingClass_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public int selectByBoardingClass_id(int boardingClass_id) {
		int result = 0;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT teacher_id FROM teacher WHERE boardingClass_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, boardingClass_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result = rs.getInt("teacher_id");
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
			String sql = "SELECT count(*) AS total FROM teacher";
			if (search != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ? AND teacher_id != 0";
			} else {
				sql += " WHERE teacher_id != 0";
			}
			PreparedStatement pps = conn.prepareStatement(sql);
			if (search != null) {
				pps.setString(1, search);
				pps.setString(2, search);
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
