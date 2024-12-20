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
			System.out.println(t.getBoardingClass_id());
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

	public boolean deleteById(int id) {
		BoardingClass bc = selectById(id);
		if (bc.getTeacher_id() != 0) {
			Teacher tc = TeacherDAO.getInstance().selectById(bc.getTeacher_id());
			tc.setBoardingClass_id(0);
			TeacherDAO.getInstance().update(tc);
		}

		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM boardingclass WHERE boardingClass_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, id);
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
			bdl.setTeacher_id(TeacherDAO.getInstance()
					.selectByBoardingClass_id(bdl.getBoardingClass_id()));
			bdl.setStudent_ids(StudentDAO.getInstance()
					.selectByBoardingClass_id(bdl.getBoardingClass_id()));
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
			result.setTeacher_id(TeacherDAO.getInstance()
					.selectByBoardingClass_id(result.getBoardingClass_id()));
			result.setStudent_ids(StudentDAO.getInstance()
					.selectByBoardingClass_id(result.getBoardingClass_id()));
		}
		return result;
	}

	public List<BoardingClass> getPageBoardingClass(int page, int amount,
		String searchField, String search, String sortField, String sortType) {
		List<BoardingClass> result = new ArrayList<>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM boardingClass ";
			if (search != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ? AND boardingClass_id != 0";
			} else {
				sql += " WHERE boardingClass_id != 0";
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
				int boardingClass_id = rs.getInt("boardingClass_id");
				String name = rs.getString("name");
				int numberOfBed = rs.getInt("numberOfBed");
				String room = rs.getString("room");
				result.add(new BoardingClass(boardingClass_id, name, numberOfBed,
						room, -1, null));
			}
		} catch (Exception ignored) {}
		JDBCUtil.closeConnection(conn);
		for (BoardingClass bdl : result) {
			bdl.setTeacher_id(TeacherDAO.getInstance()
					.selectByBoardingClass_id(bdl.getBoardingClass_id()));
		}
		return result;
	}

	public int count(String searchField, String search) {
		int result = -1;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT count(*) AS total FROM boardingClass ";
			if (search != null) {
				search = "%" + search + "%";
				sql += " WHERE " + searchField + " LIKE ? AND boardingClass_id != 0";
			} else {
				sql += " WHERE boardingClass_id != 0";
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

	public boolean existByRoom(String room) {
		int result = -1;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT count(*) AS total FROM boardingClass WHERE room = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, room);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result = rs.getInt("total");
			}
		} catch (SQLException ignored) {}
		JDBCUtil.closeConnection(conn);
		return result > 0;
	}
}
