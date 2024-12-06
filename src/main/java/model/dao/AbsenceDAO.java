package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Absence;
import util.JDBCUtil;

public class AbsenceDAO implements DAOInterface<Absence> {
	private static AbsenceDAO _Instance;

	private AbsenceDAO() {
	}

	public static AbsenceDAO getInstance() {
		if (_Instance == null)
			_Instance = new AbsenceDAO();
		return _Instance;
	}
	
	public Absence checkExist(int t, Date time) {
		Absence result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM absence WHERE student_id = ? AND absence_day = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			pps.setDate(2, time);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int absence_id = rs.getInt("absence_id");
				Date absence_day = rs.getDate("absence_day");
				int student_id = rs.getInt("student_id");
				result = new Absence(absence_id, absence_day, student_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean insert(Absence t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO absence (absence_day, student_id) " + "VALUES (?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setDate(1, t.getDayOfAbsence());
			pps.setInt(2, t.getStudent_id());
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
	public boolean delete(Absence t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM absence WHERE absence_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getAbsence_id());
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
	
	public boolean deleteById(int t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM absence WHERE absence_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
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
	public boolean update(Absence t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE absence SET absence_day = ?, student_id = ? WHERE absence_id = ?";

			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(3, t.getAbsence_id());
			pps.setDate(1, t.getDayOfAbsence());
			pps.setInt(2, t.getStudent_id());
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
	public List<Absence> selectAll() {
		List<Absence> result = new ArrayList<Absence>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM absence";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int absence_id = rs.getInt("absence_id");
				Date absence_day = rs.getDate("absence_day");
				int student_id = rs.getInt("student_id");
				result.add(new Absence(absence_id, absence_day, student_id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public Absence selectById(Absence t) {
		Absence result = new Absence();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM absence WHERE absence_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getAbsence_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int absence_id = rs.getInt("absence_id");
				Date absence_day = rs.getDate("absence_day");
				int student_id = rs.getInt("student_id");
				result = new Absence(absence_id, absence_day, student_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public Absence selectById(int t) {
		Absence result = new Absence();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM absence WHERE absence_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int absence_id = rs.getInt("absence_id");
				Date absence_day = rs.getDate("absence_day");
				int student_id = rs.getInt("student_id");
				result = new Absence(absence_id, absence_day, student_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public List<Integer> selectByStudentId(int student_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT absence_id FROM absence WHERE student_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, student_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {            
				result.add(rs.getInt("absence_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
}
