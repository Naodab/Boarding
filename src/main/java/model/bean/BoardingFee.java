package model.bean;

import java.sql.Date;
import java.util.List;

public class BoardingFee {
	private int boardingFee_id;
	private long mainCosts;
	private long subCosts;
	private int numberDays;
	private Date start_day;
	private Date end_day;
	private List<Integer> eatingHistory_ids;
	private List<Integer> invoice_ids;

	public BoardingFee() {
		super();
	}

	public BoardingFee(int boardingFee_id, long mainCosts, long subCosts, int numberDays, Date start_day, Date end_day,
			List<Integer> eatingHistory_ids, List<Integer> invoice_ids) {
		super();
		this.boardingFee_id = boardingFee_id;
		this.mainCosts = mainCosts;
		this.subCosts = subCosts;
		this.numberDays = numberDays;
		this.start_day = start_day;
		this.end_day = end_day;
		this.eatingHistory_ids = eatingHistory_ids;
		this.invoice_ids = invoice_ids;
	}

	public int getBoardingFee_id() {
		return boardingFee_id;
	}

	public int getNumberDays() {
		return numberDays;
	}

	public void setNumberDays(int numberDays) {
		this.numberDays = numberDays;
	}

	public void setBoardingFee_id(int boardingFee_id) {
		this.boardingFee_id = boardingFee_id;
	}

	public long getMainCosts() {
		return mainCosts;
	}

	public void setMainCosts(long mainCosts) {
		this.mainCosts = mainCosts;
	}

	public long getSubCosts() {
		return subCosts;
	}

	public void setSubCosts(long subCosts) {
		this.subCosts = subCosts;
	}

	public Date getStart_day() {
		return start_day;
	}

	public void setStart_day(Date start_day) {
		this.start_day = start_day;
	}

	public Date getEnd_day() {
		return end_day;
	}

	public void setEnd_day(Date end_day) {
		this.end_day = end_day;
	}

	public List<Integer> getEatingHistory_ids() {
		return eatingHistory_ids;
	}

	public void setEatingHistory_ids(List<Integer> eatingHistory_ids) {
		this.eatingHistory_ids = eatingHistory_ids;
	}

	public List<Integer> getInvoice_ids() {
		return invoice_ids;
	}

	public void setInvoice_ids(List<Integer> invoice_ids) {
		this.invoice_ids = invoice_ids;
	}
}
