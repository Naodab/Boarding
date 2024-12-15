package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.bean.BoardingClass;
import model.bean.BoardingFee;
import model.bean.Invoice;
import model.bean.Student;
import model.dao.BoardingClassDAO;
import model.dao.BoardingFeeDAO;
import model.dao.InvoiceDAO;
import model.dao.StudentDAO;
import model.dto.InvoiceAdminResponse;
import model.dto.SearchResponse;

public class InvoiceBO {
	private static InvoiceBO _instance;
	private InvoiceBO() {}
	
	public static InvoiceBO getInstance() {
		if (_instance == null) 
			_instance = new InvoiceBO();
		return _instance;
	}
	
	private final InvoiceDAO invoiceDAO = InvoiceDAO.getInstance();
	private final BoardingFeeDAO boardingFeeDAO = BoardingFeeDAO.getInstance();
	private final StudentDAO studentDAO = StudentDAO.getInstance();
	private final BoardingClassDAO boardingClassDAO = BoardingClassDAO.getInstance();
	
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

	public SearchResponse<InvoiceAdminResponse> getPage(int page, int amount, int boardingFeeId,
		String searchField, String search) {
		return new SearchResponse<>(invoiceDAO.count(boardingFeeId, searchField, search),
				invoiceDAO.getPageInvoice(page, amount, boardingFeeId, searchField, search)
						.stream().map(this::toInvoiceAdminResponse).toList());
	}

	public SearchResponse<InvoiceAdminResponse> getPageByClass(int page, int amount,
   		int boardingFeeId, int boardingClassId) {
		List<InvoiceAdminResponse> result = new ArrayList<>();
		List<Integer> studentIds = studentDAO.getPageStudentsByBoardingClassId(page, amount, boardingClassId);
		int count = studentDAO.selectByBoardingClass_id(boardingClassId).size();
		for (int studentId : studentIds) {
			Invoice invoice = invoiceDAO.selectByBoardingFeeIdandStudentID(boardingFeeId, studentId);
			result.add(toInvoiceAdminResponse(invoice));
		}
		return new SearchResponse<>(count, result);
	}

	private InvoiceAdminResponse toInvoiceAdminResponse(Invoice t) {
		InvoiceAdminResponse response = new InvoiceAdminResponse(t.getInvoice_id(), t.getStudent_id(),
				t.getPayment_date().toLocalDate(), t.getMoney());
		Student student = studentDAO.selectById(t.getStudent_id());
		response.setStudentName(student.getName());
		response.setStudentClass(boardingClassDAO
				.selectById(student.getBoardingClass_id()).getName());
		response.setStatus(t.getStatusPayment() == 1
				? "Chưa nộp"
				: (t.getStatusPayment() == 2 ? "Chưa in" : "Đã in"));
		response.setReturnMoney(t.getReturnMoney());
		BoardingFee boardingFee = boardingFeeDAO.selectById(t.getBoardingFee_id());
		response.setSubCosts(student.isSubMeal() ? boardingFee.getSubCosts() : 0);
		response.setMainCosts(boardingFee.getMainCosts());
		return response;
	}

	public void updateStatus(int boardingFeeId) {
		invoiceDAO.updateStatusPaymentOfBoardingFeeId(boardingFeeId);
	}
}
