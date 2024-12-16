package model.bean;

import java.sql.Date;
import java.time.DayOfWeek;

public class EatingHistory {
	private int eatingHistory_id;
	private int menu_id;
	private Date eating_day;
	private int boardingFee_id;

	public EatingHistory() {
		super();
	}

	public EatingHistory(int eatingHistory_id, int menu_id, Date eating_day, int boardingFee_id) {
		super();
		this.eatingHistory_id = eatingHistory_id;
		this.menu_id = menu_id;
		this.eating_day = eating_day;
		this.boardingFee_id = boardingFee_id;
	}

	public int getEatingHistory_id() {
		return eatingHistory_id;
	}

	public void setEatingHistory_id(int eatingHistory_id) {
		this.eatingHistory_id = eatingHistory_id;
	}

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public Date getEating_day() {
		return eating_day;
	}

	public void setEating_day(Date eating_day) {
		this.eating_day = eating_day;
	}

	public int getBoardingFee_id() {
		return boardingFee_id;
	}

	public void setBoardingFee_id(int boardingFee_id) {
		this.boardingFee_id = boardingFee_id;
	}

	public static int getDaysOfWeek(DayOfWeek dow) {
		int result = 0;
		switch (dow) {
			case MONDAY:
				result = 1;
				break;
			case TUESDAY:
				result = 2;
				break;
			case WEDNESDAY:
				result = 3;
				break;
			case THURSDAY:
				result = 4;
				break;
			case FRIDAY:
				result = 5;
			default:
				break;
		}
		return result;
	}
}