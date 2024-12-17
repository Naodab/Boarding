package model.dto;

public class BoardingFeeResponse {
	private int student_id;
	private String studentName;
	private int invoiceId;
	private int status;
	//status == 1: chưa nộp
	//status == 2: đã nộp (nhưng chưa in hóa đơn)
	//status == 3: đã in hóa đơn
	private int boardingFeeId;
	private String timeToSubmit;
	private Long moneyToSubmit;
	
	public BoardingFeeResponse() {}
	
	public BoardingFeeResponse(int student_id, String studentName, int invoiceId, int status, int boardingFeeId,
			String timeToSubmit, Long moneyToSubmit) {
		this.student_id = student_id;
		this.studentName = studentName;
		this.invoiceId = invoiceId;
		this.status = status;
		this.boardingFeeId = boardingFeeId;
		this.timeToSubmit = timeToSubmit;
		this.moneyToSubmit = moneyToSubmit;
	}

	public BoardingFeeResponse(int student_id, String studentName, int invoiceId, int status, int boardingFeeId) {
		this.student_id = student_id;
		this.studentName = studentName;
		this.invoiceId = invoiceId;
		this.status = status;
		this.boardingFeeId = boardingFeeId;
	}
	
	public String getTimeToSubmit() {
		return timeToSubmit;
	}

	public void setTimeToSubmit(String timeToSubmit) {
		this.timeToSubmit = timeToSubmit;
	}

	public Long getMoneyToSubmit() {
		return moneyToSubmit;
	}

	public void setMoneyToSubmit(Long moneyToSubmit) {
		this.moneyToSubmit = moneyToSubmit;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public int getInvoiceId() {
		return invoiceId;
	}
	
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getBoardingFeeId() {
		return boardingFeeId;
	}
	
	public void setBoardingFeeId(int boardingFeeId) {
		this.boardingFeeId = boardingFeeId;
	}
}
