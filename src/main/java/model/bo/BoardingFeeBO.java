package model.bo;

import java.time.LocalDate;
import java.util.List;

import model.bean.BoardingFee;
import model.dao.BoardingFeeDAO;
import model.dao.InvoiceDAO;
import model.dto.BoardingFeeAdminResponse;

public class BoardingFeeBO {
	private static BoardingFeeBO _instance;
	private BoardingFeeBO() {}
	
	public static BoardingFeeBO getInstance() {
		if (_instance == null) 
			_instance = new BoardingFeeBO();
		return _instance;
	}
	
	private final BoardingFeeDAO boardingFeeDAO = BoardingFeeDAO.getInstance();
	private final InvoiceDAO invoiceDAO = InvoiceDAO.getInstance();
	
	public boolean insert(BoardingFee t) {
		return boardingFeeDAO.insert(t);
	}

	public boolean delete(BoardingFee t) {
		return boardingFeeDAO.delete(t);
	}

	public boolean update(BoardingFee t) {
		return boardingFeeDAO.update(t);
	}

	public List<BoardingFee> selectAll() {
		return boardingFeeDAO.selectAll();
	}

	public BoardingFee selectById(BoardingFee t) {
		return boardingFeeDAO.selectById(t);
	}

	public BoardingFee selectById(int t) {
		return boardingFeeDAO.selectById(t);
	}

	public BoardingFeeAdminResponse getResponseById(int boardingFeeId) {
		BoardingFee boardingFee = boardingFeeDAO.selectByEndMonth(boardingFeeId);
		long payedMoney = invoiceDAO.getPayedMoneyOfBoardingFee(boardingFee.getBoardingFee_id());
		int payedStudent = invoiceDAO.getPayedStudentOfBoardingFee(boardingFee.getBoardingFee_id());
		long totalMoney = invoiceDAO.getTotalMoneyOfBoardingFee(boardingFee.getBoardingFee_id());
		int nonPrintedInvoices = invoiceDAO.getNonPrintedInvoicesByBoardingFeeId(boardingFee.getBoardingFee_id());
		return new BoardingFeeAdminResponse(
			boardingFee.getBoardingFee_id(),
			boardingFee.getStart_day().toLocalDate(),
			boardingFee.getEnd_day().toLocalDate(),
			boardingFee.getMainCosts(),
			boardingFee.getSubCosts(),
			boardingFee.getNumberDays(),
			payedMoney,
			payedStudent,
			nonPrintedInvoices,
			totalMoney
		);
	}

	public boolean existsByMonth(int month) {
		return boardingFeeDAO.selectByEndMonth(month) != null;
	}

	public void createNewFee(BoardingFee boardingFee) {

	}
}
