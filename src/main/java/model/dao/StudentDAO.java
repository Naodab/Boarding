package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Student;
import util.JDBCUtil;

public class StudentDAO implements DAOInterface<Student> {
	private static StudentDAO _Instance;

	private StudentDAO() {
	}

	public static StudentDAO getInstance() {
		if (_Instance == null)
			_Instance = new StudentDAO();
		return _Instance;
	}

	@Override
	public boolean insert(Student t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO student(name, dateOfBirth, address, sex, weight, height, parents_id, boardingClass_id, subMeal)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setDate(2, t.getDateOfBirth());
			pps.setString(3, t.getAddress());
			pps.setBoolean(4, t.getSex());
			pps.setDouble(5, t.getWeight());
			pps.setDouble(6, t.getHeight());
			pps.setInt(7, t.getParents_id());
			pps.setInt(8, t.getBoardingClass_id());
			pps.setBoolean(9, t.isSubMeal());
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
	public boolean delete(Student t) {
		boolean result = false;
		for (int absence_id : t.getAbsence_ids())
			if (!AbsenceDAO.getInstance().deleteById(absence_id))
				return false;
		for (int invoice_id : t.getInvoice_ids())
			if (!InvoiceDAO.getInstance().deleteById(invoice_id))
				return false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM student WHERE student_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getStudent_id());
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

	public boolean deleteByID(int t) {
		boolean result = false;
		Student std = StudentDAO.getInstance().selectById(t);
		for (int absence_id : std.getAbsence_ids())
			if (!AbsenceDAO.getInstance().deleteById(absence_id))
				return false;
		for (int invoice_id : std.getInvoice_ids())
			if (!InvoiceDAO.getInstance().deleteById(invoice_id))
				return false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM student WHERE student_id = ?";
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
	public boolean update(Student t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE student SET name = ?, dateOfBirth = ?, address = ?, sex = ?, weight = ?, "
					+ "height = ?, parents_id = ?, boardingClass_id = ?, subMeal = ? " + "WHERE student_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setDate(2, t.getDateOfBirth());
			pps.setString(3, t.getAddress());
			pps.setBoolean(4, t.getSex());
			pps.setDouble(5, t.getWeight());
			pps.setDouble(6, t.getHeight());
			pps.setInt(7, t.getParents_id());
			pps.setInt(8, t.getBoardingClass_id());
			pps.setBoolean(9, t.isSubMeal());
			pps.setInt(10, t.getStudent_id());
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
	public List<Student> selectAll() {
		List<Student> result = new ArrayList<Student>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM student";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int student_id = rs.getInt("student_id");
				double weight = rs.getDouble("weight");
				double height = rs.getDouble("height");
				int parents_id = rs.getInt("parents_id");
				int boardingClass_id = rs.getInt("boardingClass_id");
				boolean subMeal = rs.getBoolean("subMeal");
				result.add(new Student(name, dateOfBirth, address, sex, student_id, weight, height, parents_id,
						boardingClass_id, subMeal, null, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
//		for (Student std : result) {
//			std.setAbsence_ids(AbsenceDAO.getInstance().selectByStudentId(std.getStudent_id()));
//			std.setInvoice_ids(InvoiceDAO.getInstance().selectByStudentId(std.getStudent_id()));
//		}
		return result;
	}

	@Override
	public Student selectById(Student t) {
		Student result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM student WHERE student_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getStudent_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int student_id = rs.getInt("student_id");
				double weight = rs.getDouble("weight");
				double height = rs.getDouble("height");
				int parents_id = rs.getInt("parents_id");
				int boardingClass_id = rs.getInt("boardingClass_id");
				boolean subMeal = rs.getBoolean("subMeal");
				result = new Student(name, dateOfBirth, address, sex, student_id, weight, height, parents_id,
						boardingClass_id, subMeal, null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		result.setInvoice_ids(InvoiceDAO.getInstance().selectByStudentId(result.getStudent_id()));
		result.setAbsence_ids(AbsenceDAO.getInstance().selectByStudentId(result.getStudent_id()));
		return result;
	}

	public Student selectById(int t) {
		Student result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM student WHERE student_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int student_id = rs.getInt("student_id");
				double weight = rs.getDouble("weight");
				double height = rs.getDouble("height");
				int parents_id = rs.getInt("parents_id");
				int boardingClass_id = rs.getInt("boardingClass_id");
				boolean subMeal = rs.getBoolean("subMeal");
				result = new Student(name, dateOfBirth, address, sex, student_id, weight, height, parents_id,
						boardingClass_id, subMeal, null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		result.setInvoice_ids(InvoiceDAO.getInstance().selectByStudentId(result.getStudent_id()));
		result.setAbsence_ids(AbsenceDAO.getInstance().selectByStudentId(result.getStudent_id()));
		return result;
	}

	public List<Integer> selectByBoardingClass_id(int boardingClass_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT student_id FROM student WHERE boardingClass_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, boardingClass_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt("student_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<Integer> selectByParents_id(int parents_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT student_id FROM student WHERE parents_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, parents_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt("student_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<Student> selectByBoardingClass_id2(int boardingClass_id) {
		List<Student> result = new ArrayList<Student>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM student WHERE boardingClass_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, boardingClass_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int student_id = rs.getInt("student_id");
				double weight = rs.getDouble("weight");
				double height = rs.getDouble("height");
				int parents_id = rs.getInt("parents_id");
				boolean subMeal = rs.getBoolean("subMeal");
				result.add(new Student(name, dateOfBirth, address, sex, student_id, weight, height, parents_id,
						boardingClass_id, subMeal, null, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public List<Student> getPageStudents(int page, int amount) {
		List<Student> result = new ArrayList<Student>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM student LIMIT ? OFFSET ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, amount);
			pps.setInt(2, page * amount);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				String address = rs.getString("address");
				Boolean sex = rs.getBoolean("sex");
				int student_id = rs.getInt("student_id");
				double weight = rs.getDouble("weight");
				double height = rs.getDouble("height");
				int parents_id = rs.getInt("parents_id");
				int boardingClass_id = rs.getInt("boardingClass_id");
				boolean subMeal = rs.getBoolean("subMeal");
				result.add(new Student(name, dateOfBirth, address, sex, student_id, weight, height, parents_id,
						boardingClass_id, subMeal, null, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
}
