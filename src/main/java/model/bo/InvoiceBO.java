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
	
	private InvoiceDAO dao = InvoiceDAO.getInstance();
	
	public boolean insert(Invoice t) {
		return dao.insert(t);
	}

	public boolean delete(Invoice t) {
		return dao.delete(t);
	}

	public boolean deleteById(int t) {
		return dao.deleteById(t);
	}

	public boolean update(Invoice t) {
		return dao.update(t);
	}

	public boolean updateById(Invoice t) {
		return dao.updateById(t);
	}

	public List<Invoice> selectAll() {
		return dao.selectAll();
	}

	public Invoice selectById(Invoice t) {
		return dao.selectById(t);
	}

	public Invoice selectById(int t) {
		return dao.selectById(t);
	}

	public void selectStudentIDAndBoardingFee(Invoice t) {
		dao.selectStudentIDAndBoardingFee(t);
	}

	public Invoice selectByBoardingFeeIdandStudentID(int boardingFee_id, int student_id) {
		return dao.selectByBoardingFeeIdandStudentID(boardingFee_id, student_id);
	}

	public List<Integer> selectByStudentId(int student_id) {
		return dao.selectByStudentId(student_id);
	}

	public List<Integer> selectByBoardingFeeId(int boardingFee_id) {
		return dao.selectByBoardingFeeId(boardingFee_id);
	}

	public long getTotalMoneyOfBoardingFee(int boardingFee_id) {
		return dao.getTotalMoneyOfBoardingFee(boardingFee_id);
	}

	public long getPayedMoneyOfBoardingFee(int boardingFee_id) {
		return dao.getPayedMoneyOfBoardingFee(boardingFee_id);
	}

	public int getPayedStudentOfBoardingFee(int boardingFee_id) {
		return dao.getPayedStudentOfBoardingFee(boardingFee_id);
	}
}
