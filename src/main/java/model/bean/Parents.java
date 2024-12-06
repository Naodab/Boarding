package model.bean;

import java.sql.Date;
import java.util.List;

public class Parents extends Person {
	private int parents_id;
	private String phoneNumber;
	private String email;
	private List<Integer> student_id;

	public Parents() {
		super();
	}

	public Parents(int parents_id, String phoneNumber, String email, List<Integer> student_id) {
		super();
		this.parents_id = parents_id;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.student_id = student_id;
	}

	public Parents(String name, Date dateOfBirth, String address, Boolean sex) {
		super(name, dateOfBirth, address, sex);
	}

	public Parents(String name, Date dateOfBirth, String address, Boolean sex, int parents_id,
			String phoneNumber, String email, List<Integer> student_id) {
		super(name, dateOfBirth, address, sex);
		this.parents_id = parents_id;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.student_id = student_id;
	}

	public int getParents_id() {
		return parents_id;
	}

	public void setParents_id(int parents_id) {
		this.parents_id = parents_id;
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

	public List<Integer> getStudent_id() {
		return student_id;
	}

	public void setStudent_id(List<Integer> student_id) {
		this.student_id = student_id;
	}
}
