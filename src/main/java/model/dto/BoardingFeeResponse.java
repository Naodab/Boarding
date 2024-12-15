package model.dto;

public class BoardingFeeResponse {
	private int student_id;
	private String studentName;
	private int invoiceId;
	private int status;
	//status == 1: chưa nộp
	//status == 2: đã nộp (nhưng chưa in hóa đơn)
	//status == 3: đã in hóa đơn
	
	public BoardingFeeResponse() {}
	
	public BoardingFeeResponse(int student_id, String studentName, int invoiceId, int status) {
		super();
		this.student_id = student_id;
		this.studentName = studentName;
		this.invoiceId = invoiceId;
		this.status = status;
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
}
