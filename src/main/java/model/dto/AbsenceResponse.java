package model.dto;

import java.time.LocalDate;

public class AbsenceResponse {
	private int student_id;
	private String name;
	private boolean isAbsent;
	private LocalDate absenceDay;
	private int absence_id;
	
	public AbsenceResponse() {}
	
	public AbsenceResponse(int student_id, String name, boolean isAbsent, LocalDate absenceDay, int absence_id) {
		this.student_id = student_id;
		this.name = name;
		this.isAbsent = isAbsent;
		this.absenceDay = absenceDay;
		this.absence_id = absence_id;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAbsent() {
		return isAbsent;
	}

	public void setAbsent(boolean isAbsent) {
		this.isAbsent = isAbsent;
	}

	public LocalDate getAbsenceDay() {
		return absenceDay;
	}

	public void setAbsenceDay(LocalDate absenceDay) {
		this.absenceDay = absenceDay;
	}

	public int getAbsence_id() {
		return absence_id;
	}

	public void setAbsence_id(int absence_id) {
		this.absence_id = absence_id;
	}
}
