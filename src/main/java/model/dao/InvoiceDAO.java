package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Invoice;
import util.JDBCUtil;

public class InvoiceDAO implements DAOInterface<Invoice>{
	private static InvoiceDAO _Instance;
	
	private InvoiceDAO() {
	}
	
	public static InvoiceDAO getInstance() {
		if (_Instance == null) _Instance = new InvoiceDAO();
		return _Instance;
	}

	@Override
	public boolean insert(Invoice t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "INSERT INTO invoice (payment_day, student_id, boardingFee_id, statusPayment, returnMoney, money)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setDate(1, t.getPayment_date());
			pps.setInt(2, t.getStudent_id());
			pps.setInt(3, t.getBoardingFee_id());
			pps.setByte(4, t.getStatusPayment());
			pps.setLong(5, t.getReturnMoney());
			pps.setLong(6, t.getMoney());
			
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
	public boolean delete(Invoice t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {		
			String sql = "DELETE FROM invoice WHERE invoice_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getInvoice_id());
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
			String sql = "DELETE FROM invoice WHERE invoice_id = ?";
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
	public boolean update(Invoice t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {		
			String sql = "UPDATE invoice SET payment_day = ?, student_id = ?, boardingFee_id = ?, " 
					+ "statusPayment = ?, returnMoney = ?, money = ? WHERE invoice_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setDate(1, t.getPayment_date());
			pps.setInt(2, t.getStudent_id());
			pps.setInt(3, t.getBoardingFee_id());
			pps.setByte(4, t.getStatusPayment());
			pps.setLong(5, t.getReturnMoney());
			pps.setLong(6, t.getMoney());
			pps.setInt(7, t.getInvoice_id());
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
	
	public boolean updateById(Invoice t) {
		boolean result = false;
		Connection conn = JDBCUtil.getConnection();
		try {		
			String sql = "UPDATE invoice SET payment_day = ?, statusPayment = ? " 
					+ "WHERE invoice_id = ? and student_id = ? and boardingFee_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setDate(1, t.getPayment_date());
			pps.setInt(2, t.getStatusPayment());
			pps.setInt(3, t.getInvoice_id());
			pps.setInt(4, t.getStudent_id());
			pps.setInt(5, t.getBoardingFee_id());
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
	public List<Invoice> selectAll() {
		List<Invoice> result = new ArrayList<Invoice>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM invoice";
			PreparedStatement pps = conn.prepareStatement(sql);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int invoice_id = rs.getInt("invoice_id");
				Date payment_day = rs.getDate("payment_day");
				int student_id = rs.getInt("student_id");
				int boardingFee_id = rs.getInt("boardingFee_id");
				byte statusPayment = rs.getByte("statusPayment");
				long returnMoney = rs.getLong("returnMoney");
				long money = rs.getLong("money");
				result.add(new Invoice(invoice_id, payment_day, student_id, boardingFee_id, statusPayment, returnMoney, money));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	@Override
	public Invoice selectById(Invoice t) {
		Invoice result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM invoice WHERE invoice_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getInvoice_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int invoice_id = rs.getInt("invoice_id");
				Date payment_day = rs.getDate("payment_day");
				int student_id = rs.getInt("student_id");
				int boardingFee_id = rs.getInt("boardingFee_id");
				byte statusPayment = rs.getByte("statusPayment");
				long returnMoney = rs.getLong("returnMoney");
				long money = rs.getLong("money");
				result = new Invoice(invoice_id, payment_day, student_id, boardingFee_id, statusPayment, returnMoney, money);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public Invoice selectById(int t) {
		Invoice result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM invoice WHERE invoice_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int invoice_id = rs.getInt("invoice_id");
				Date payment_day = rs.getDate("payment_day");
				int student_id = rs.getInt("student_id");
				int boardingFee_id = rs.getInt("boardingFee_id");
				byte statusPayment = rs.getByte("statusPayment");
				long returnMoney = rs.getLong("returnMoney");
				long money = rs.getLong("money");
				result = new Invoice(invoice_id, payment_day, student_id, boardingFee_id, statusPayment, returnMoney, money);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public void selectStudentIDAndBoardingFee(Invoice t) {
		Invoice result = t;
		int student = 0;
		int boardingFee = 0;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT student_id, boardingFee_id FROM invoice WHERE invoice_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, t.getInvoice_id());
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				student = rs.getInt("student_id");
				boardingFee = rs.getInt("boardingFee_id");
			}
			JDBCUtil.closeConnection(conn);
			result.setStudent_id(student);
			result.setBoardingFee_id(boardingFee);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Invoice selectByBoardingFeeIdandStudentID(int boardingFee_id, int student_id) {
		Invoice result = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT * FROM invoice WHERE student_id = ? AND boardingFee_id = ?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, student_id);
			pps.setInt(2, boardingFee_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				int invoice_id = rs.getInt("invoice_id");
				Date payment_day = rs.getDate("payment_day");
				byte statusPayment = rs.getByte("statusPayment");
				long returnMoney = rs.getLong("returnMoney");
				long money = rs.getLong("money");
				result = new Invoice(invoice_id, payment_day, student_id, boardingFee_id, statusPayment, returnMoney, money);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}

	public List<Integer> selectByStudentId(int student_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT invoice_id FROM invoice WHERE student_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, student_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt("invoice_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public List<Integer> selectByBoardingFeeId(int boardingFee_id) {
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT invoice_id FROM invoice WHERE boardingFee_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, boardingFee_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt("invoice_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public long getTotalMoneyOfBoardingFee(int boardingFee_id) {
		long result = 0;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT SUM(money) FROM invoice WHERE boardingFee_id=?";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, boardingFee_id);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public long getPayedMoneyOfBoardingFee(int boardingFee_id) {
		long result = 0;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT SUM(money) FROM invoice WHERE boardingFee_id=? AND (statusPayment=? OR statusPayment=?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, boardingFee_id);
			pps.setByte(2, (byte)2);
			pps.setByte(3, (byte)3);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
	
	public int getPayedStudentOfBoardingFee(int boardingFee_id) {
		int result = 0;
		Connection conn = JDBCUtil.getConnection();
		try {
			String sql = "SELECT COUNT(student_id) FROM invoice WHERE boardingFee_id=? AND (statusPayment=? OR statusPayment=?)";
			PreparedStatement pps = conn.prepareStatement(sql);
			pps.setInt(1, boardingFee_id);
			pps.setByte(2, (byte)2);
			pps.setByte(3, (byte)3);
			ResultSet rs = pps.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.closeConnection(conn);
		return result;
	}
}
