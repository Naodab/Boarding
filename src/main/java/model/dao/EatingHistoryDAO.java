package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.EatingHistory;
import util.JDBCUtil;

public class EatingHistoryDAO implements DAOInterface<EatingHistory> {
	private static EatingHistoryDAO _Instance;

	private EatingHistoryDAO() {
	}

	public static EatingHistoryDAO getInstance() {
		if (_Instance == null)
			_Instance = new EatingHistoryDAO();
		return _Instance;
	}

	@Override
	public boolean insert(EatingHistory t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO eatinghistory (menu_id, eating_day, boardingFee_id)" + " VALUES (?, ?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getMenu_id());
			pps.setDate(2, t.getEating_day());
			pps.setInt(3, t.getBoardingFee_id());
			int check = pps.executeUpdate();
			result = check > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean delete(EatingHistory t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM eatinghistory WHERE eatingHistory_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getEatingHistory_id());
			int check = pps.executeUpdate();
			result = check > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public boolean update(EatingHistory t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE eatinghistory SET menu_id=?, eating_day=?, boardingFee_id=?"
					+ " WHERE eatingHistory_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getMenu_id());
			pps.setDate(2, t.getEating_day());
			pps.setInt(3, t.getBoardingFee_id());
			pps.setInt(4, t.getEatingHistory_id());
			int check = pps.executeUpdate();
			result = check > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public List<EatingHistory> selectAll() {
		List<EatingHistory> result = new ArrayList<EatingHistory>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM eatinghistory";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int eatingHistory_id = rs.getInt("eatingHistory_id");
				int menu_id = rs.getInt("menu_id");
				Date eating_day = rs.getDate("eating_day");
				int boardingFee_id = rs.getInt("boardingFee_id");
				result.add(new EatingHistory(eatingHistory_id, menu_id, eating_day, boardingFee_id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public EatingHistory selectById(EatingHistory t) {
		EatingHistory result = new EatingHistory();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM eatinghistory WHERE eatinghistory_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getEatingHistory_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int eatingHistory_id = rs.getInt("eatingHistory_id");
				int menu_id = rs.getInt("menu_id");
				Date eating_day = rs.getDate("eating_day");
				int boardingFee_id = rs.getInt("boardingFee_id");
				result = new EatingHistory(eatingHistory_id, menu_id, eating_day, boardingFee_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public EatingHistory selectById(int t) {
		EatingHistory result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM eatinghistory WHERE eatinghistory_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int eatingHistory_id = rs.getInt("eatingHistory_id");
				int menu_id = rs.getInt("menu_id");
				Date eating_day = rs.getDate("eating_day");
				int boardingFee_id = rs.getInt("boardingFee_id");
				result = new EatingHistory(eatingHistory_id, menu_id, eating_day, boardingFee_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<Integer> selectByMenu_id(int menu_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT eatingHistory_id FROM eatinghistory WHERE menu_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, menu_id);
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				result.add(rs.getInt("eatingHistory_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public List<Integer> selectByBoardingFee_id(int boardingFee_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT eatingHistory_id FROM eatinghistory WHERE boardingFee_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, boardingFee_id);
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				result.add(rs.getInt("eatingHistory_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public List<EatingHistory> selectBetweenDays (String startDay, String endDay) {
		List<EatingHistory> list = new ArrayList<EatingHistory>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM eatinghistory WHERE eating_day BETWEEN ? AND ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setString(1, startDay);
			pps.setString(2, endDay);
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				int eatingHistory_id = rs.getInt("eatingHistory_id");
				int menu_id = rs.getInt("menu_id");
				Date eating_day = rs.getDate("eating_day");
				int boardingFee_id = rs.getInt("boardingFee_id");
				list.add(new EatingHistory(eatingHistory_id, menu_id, eating_day, boardingFee_id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return list;
	}
}
