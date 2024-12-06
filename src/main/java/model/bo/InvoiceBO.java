package model.bo;

import java.util.List;

import model.bean.Invoice;
import model.dao.InvoiceDAO;

public class InvoiceBO {
	private static InvoiceBO _instance;
	private InvoiceBO() {}
	
	public static InvoiceBO getInstance() {
		if (_instance == null) 
			_instance = new InvoiceBO();
		return _instance;
	}
	
	private InvoiceDAO invoiceDAO = InvoiceDAO.getInstance();
	
	public boolean insert(Invoice t) {
		return invoiceDAO.insert(t);
	}

	public boolean delete(Invoice t) {
		return invoiceDAO.delete(t);
	}

	public boolean deleteById(int t) {
		return invoiceDAO.deleteById(t);
	}

	public boolean update(Invoice t) {
		return invoiceDAO.update(t);
	}

	public boolean updateById(Invoice t) {
		return invoiceDAO.updateById(t);
	}

	public List<Invoice> selectAll() {
		return invoiceDAO.selectAll();
	}

	public Invoice selectById(Invoice t) {
		return invoiceDAO.selectById(t);
	}

	public Invoice selectById(int t) {
		return invoiceDAO.selectById(t);
	}

	public void selectStudentIDAndBoardingFee(Invoice t) {
		invoiceDAO.selectStudentIDAndBoardingFee(t);
	}

	public Invoice selectByBoardingFeeIdandStudentID(int boardingFee_id, int student_id) {
		return invoiceDAO.selectByBoardingFeeIdandStudentID(boardingFee_id, student_id);
	}

	public List<Integer> selectByStudentId(int student_id) {
		return invoiceDAO.selectByStudentId(student_id);
	}

	public List<Integer> selectByBoardingFeeId(int boardingFee_id) {
		return invoiceDAO.selectByBoardingFeeId(boardingFee_id);
	}

	public long getTotalMoneyOfBoardingFee(int boardingFee_id) {
		return invoiceDAO.getTotalMoneyOfBoardingFee(boardingFee_id);
	}

	public long getPayedMoneyOfBoardingFee(int boardingFee_id) {
		return invoiceDAO.getPayedMoneyOfBoardingFee(boardingFee_id);
	}

	public int getPayedStudentOfBoardingFee(int boardingFee_id) {
		return invoiceDAO.getPayedStudentOfBoardingFee(boardingFee_id);
	}
}
