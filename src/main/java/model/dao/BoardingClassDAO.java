package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.BoardingClass;
import model.bean.Teacher;
import util.JDBCUtil;

public class BoardingClassDAO implements DAOInterface<BoardingClass> {

	private static BoardingClassDAO _Instance;

	private BoardingClassDAO() {
	}

	public static BoardingClassDAO getInstance() {
		if (_Instance == null)
			_Instance = new BoardingClassDAO();
		return _Instance;
	}

	@Override
	public boolean insert(BoardingClass t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO boardingclass (name, numberOfBed, room)" + " VALUES (?, ?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setInt(2, t.getNumberOfBed());
			pps.setString(3, t.getRoom());
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (t.getTeacher_id() != 0) {
			Teacher tc = TeacherDAO.getInstance().selectById(t.getTeacher_id());
			tc.setBoardingClass_id(t.getBoardingClass_id());
			TeacherDAO.getInstance().update(tc);
		}
		return result;
	}

	@Override
	public boolean delete(BoardingClass t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM boardingclass WHERE boardingClass_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getBoardingClass_id());
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (t.getTeacher_id() != 0) {
			Teacher tc = TeacherDAO.getInstance().selectById(t.getTeacher_id());
			tc.setBoardingClass_id(0);
			TeacherDAO.getInstance().update(tc);
		}
		return result;
	}

	@Override
	public boolean update(BoardingClass t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE boardingclass SET name = ?, numberOfBed = ?, room = ? " + "WHERE boardingClass_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, t.getName());
			pps.setInt(2, t.getNumberOfBed());
			pps.setString(3, t.getRoom());
			pps.setInt(4, t.getBoardingClass_id());
			int check = pps.executeUpdate();
			if (check > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (t.getTeacher_id() != 0) {
			Teacher tc = TeacherDAO.getInstance().selectById(t.getTeacher_id());
			if (t.getBoardingClass_id() != tc.getBoardingClass_id()) {
				tc.setBoardingClass_id(t.getBoardingClass_id());
				TeacherDAO.getInstance().update(tc);
			}
		}
		return result;
	}

	@Override
	public List<BoardingClass> selectAll() {
		List<BoardingClass> result = new ArrayList<BoardingClass>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM boardingclass";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int boardingClass_id = rs.getInt("boardingClass_id");
				String name = rs.getString("name");
				int numberOfBed = rs.getInt("numberOfBed");
				String room = rs.getString("room");
				result.add(new BoardingClass(boardingClass_id, name, numberOfBed, room, -1, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		for (BoardingClass bdl : result) {
			bdl.setTeacher_id(TeacherDAO.getInstance().selectByBoardingClass_id(bdl.getBoardingClass_id()));
			bdl.setStudent_ids(StudentDAO.getInstance().selectByBoardingClass_id(bdl.getBoardingClass_id()));
		}
		return result;
	}

	@Override
	public BoardingClass selectById(BoardingClass t) {
		BoardingClass result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM boardingclass WHERE boardingClass_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getBoardingClass_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int boardingClass_id = rs.getInt("boardingClass_id");
				String name = rs.getString("name");
				int numberOfBed = rs.getInt("numberOfBed");
				String room = rs.getString("room");
				result = new BoardingClass(boardingClass_id, name, numberOfBed, room, -1, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		result.setTeacher_id(TeacherDAO.getInstance().selectByBoardingClass_id(result.getBoardingClass_id()));
		result.setStudent_ids(StudentDAO.getInstance().selectByBoardingClass_id(result.getBoardingClass_id()));
		return result;
	}

	public BoardingClass selectById(int t) {
		BoardingClass result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM boardingclass WHERE boardingClass_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int boardingClass_id = rs.getInt("boardingClass_id");
				String name = rs.getString("name");
				int numberOfBed = rs.getInt("numberOfBed");
				String room = rs.getString("room");
				result = new BoardingClass(boardingClass_id, name, numberOfBed, room, -1, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (result != null) {
			result.setTeacher_id(TeacherDAO.getInstance().selectByBoardingClass_id(result.getBoardingClass_id()));
			result.setStudent_ids(StudentDAO.getInstance().selectByBoardingClass_id(result.getBoardingClass_id()));
		}
		return result;
	}
}
