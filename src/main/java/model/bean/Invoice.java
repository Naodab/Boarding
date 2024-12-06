package model.bean;

import java.sql.Date;

public class Invoice {
	private int invoice_id;
	private Date payment_date;
	private int student_id;
	private int boardingFee_id;
	private byte statusPayment;
	private long returnMoney;
	private long money;

	public Invoice() {
		super();
	}

	public Invoice(int invoice_id, Date payment_date, int student_id, int boardingFee_id, byte statusPayment,
			long returnMoney, long money) {
		super();
		this.invoice_id = invoice_id;
		this.payment_date = payment_date;
		this.student_id = student_id;
		this.boardingFee_id = boardingFee_id;
		this.statusPayment = statusPayment;
		this.returnMoney = returnMoney;
		this.money = money;
	}

	public int getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(int invoice_id) {
		this.invoice_id = invoice_id;
	}

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public int getBoardingFee_id() {
		return boardingFee_id;
	}

	public void setBoardingFee_id(int boardingFee_id) {
		this.boardingFee_id = boardingFee_id;
	}

	public byte getStatusPayment() {
		return statusPayment;
	}

	public void setStatusPayment(byte statusPayment) {
		this.statusPayment = statusPayment;
	}

	public long getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(long returnMoney) {
		this.returnMoney = returnMoney;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}
}
