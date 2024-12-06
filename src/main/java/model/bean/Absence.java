package model.bean;

import java.sql.Date;

public class Absence {
	private int absence_id;
	private Date dayOfAbsence;
	private int student_id;

	public Absence() {
		super();
	}

	public Absence(int absence_id, Date dayOfAbsence, int student_id) {
		super();
		this.absence_id = absence_id;
		this.dayOfAbsence = dayOfAbsence;
		this.student_id = student_id;
	}

	public int getAbsence_id() {
		return absence_id;
	}

	public void setAbsence_id(int absence_id) {
		this.absence_id = absence_id;
	}

	public Date getDayOfAbsence() {
		return dayOfAbsence;
	}

	public void setDayOfAbsence(Date dayOfAbsence) {
		this.dayOfAbsence = dayOfAbsence;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

}
