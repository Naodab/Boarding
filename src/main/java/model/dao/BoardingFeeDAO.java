package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.BoardingFee;
import util.JDBCUtil;

public class BoardingFeeDAO implements DAOInterface<BoardingFee> {
	private static BoardingFeeDAO _Instance;

	private BoardingFeeDAO() {
	}

	public static BoardingFeeDAO getInstance() {
		if (_Instance == null)
			_Instance = new BoardingFeeDAO();
		return _Instance;
	}

	@Override
	public boolean insert(BoardingFee t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO boardingfee (mainCosts, subCosts, numberDays, start_day, end_day)"
					+ "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setLong(1, t.getMainCosts());
			pps.setLong(2, t.getSubCosts());
			pps.setInt(3, t.getNumberDays());
			pps.setDate(4, t.getStart_day());
			pps.setDate(5, t.getEnd_day());
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
	public boolean delete(BoardingFee t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "DELETE FROM boardingfee WHERE boardingFee_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getBoardingFee_id());
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
	public boolean update(BoardingFee t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "UPDATE boardingFee SET mainCosts = ?, subCosts = ?, numberDays = ?, start_day = ?, end_day = ?"
					+ " WHERE boardingFee_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setLong(1, t.getMainCosts());
			pps.setLong(2, t.getSubCosts());
			pps.setInt(3, t.getNumberDays());
			pps.setDate(4, t.getStart_day());
			pps.setDate(5, t.getEnd_day());
			pps.setInt(6, t.getBoardingFee_id());
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
	public List<BoardingFee> selectAll() {
		List<BoardingFee> result = new ArrayList<BoardingFee>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM boardingfee";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int boardingFee_id = rs.getInt("boardingFee_id");
				long mainCosts = rs.getLong("mainCosts");
				long subCosts = rs.getLong("subCosts");
				int numberDays = rs.getInt("numberDays");
				Date start_day = rs.getDate("start_day");
				Date end_day = rs.getDate("end_day");
				result.add(new BoardingFee(boardingFee_id, mainCosts, subCosts, numberDays, start_day, end_day, null, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
//		for (BoardingFee bdf : result) {
//			bdf.setEatingHistory_ids(EatingHistoryDAO.getInstance().selectByBoardingFee_id(bdf.getBoardingFee_id()));
//			bdf.setInvoice_ids(InvoiceDAO.getInstance().selectByBoardingFeeId(bdf.getBoardingFee_id()));
//		}
		return result;
	}

	@Override
	public BoardingFee selectById(BoardingFee t) {
		BoardingFee result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM boardingfee where boardingFee_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getBoardingFee_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int boardingFee_id = rs.getInt("boardingFee_id");
				long mainCosts = rs.getLong("mainCosts");
				long subCosts = rs.getLong("subCosts");
				int numberDays = rs.getInt("numberDays");
				Date start_day = rs.getDate("start_day");
				Date end_day = rs.getDate("end_day");
				result = new BoardingFee(boardingFee_id, mainCosts, subCosts, numberDays, start_day, end_day, null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (result != null) {
			result.setEatingHistory_ids(
					EatingHistoryDAO.getInstance().selectByBoardingFee_id(result.getBoardingFee_id()));
			result.setInvoice_ids(InvoiceDAO.getInstance().selectByBoardingFeeId(result.getBoardingFee_id()));
		}
		return result;
	}

	public BoardingFee selectById(int t) {
		BoardingFee result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM boardingfee where boardingFee_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int boardingFee_id = rs.getInt("boardingFee_id");
				long mainCosts = rs.getLong("mainCosts");
				long subCosts = rs.getLong("subCosts");
				int numberDays = rs.getInt("numberDays");
				Date start_day = rs.getDate("start_day");
				Date end_day = rs.getDate("end_day");
				result = new BoardingFee(boardingFee_id, mainCosts, subCosts, numberDays, start_day, end_day, null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		if (result != null) {
			result.setEatingHistory_ids(EatingHistoryDAO.getInstance().selectByBoardingFee_id(t));
			result.setInvoice_ids(InvoiceDAO.getInstance().selectByBoardingFeeId(t));
		}
		return result;
	}
}
