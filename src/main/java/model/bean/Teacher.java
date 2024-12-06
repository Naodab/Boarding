package model.bean;

import java.sql.Date;

public class Teacher extends Person {
	private int teacher_id;
	private String phoneNumber;
	private String email;
	private int boardingClass_id;

	public Teacher() {
		super();
	}

	public Teacher(int teacher_id, String phoneNumber, String email, int boardingClass_id) {
		super();
		this.teacher_id = teacher_id;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.boardingClass_id = boardingClass_id;
	}

	public Teacher(String name, Date dateOfBirth, String address, Boolean sex) {
		super(name, dateOfBirth, address, sex);
	}

	public Teacher(String name, Date dateOfBirth, String address, Boolean sex, int teacher_id, String phoneNumber,
			String email, int boardingClass_id) {
		super(name, dateOfBirth, address, sex);
		this.teacher_id = teacher_id;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.boardingClass_id = boardingClass_id;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBoardingClass_id() {
		return boardingClass_id;
	}

	public void setBoardingClass_id(int boardingClass_id) {
		this.boardingClass_id = boardingClass_id;
	}
}
