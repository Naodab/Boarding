package model.dto;

import java.sql.Date;

public class StudentResponse {
	private int student_id;
	private String name;
	private Date dateOfBirth;
	private String address;
	private String sex;
	private String parentName;
	private String boardingClassName;
	private boolean subMeal;

	public StudentResponse(int student_id, String name, Date dateOfBirth, String address, Boolean sex,
			String parentName, String boardingClassName, boolean subMeal) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.sex = sex ? "Nam" : "Nữ";
		this.parentName = parentName;
		this.boardingClassName = boardingClassName;
		this.subMeal = subMeal;
	}
	
	public StudentResponse(int student_id, String name, Date dateOfBirth, String address, Boolean sex, boolean subMeal) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.sex = sex ? "Nam" : "Nữ";
		this.subMeal = subMeal;
	}

	public StudentResponse() {
		super();
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getBoardingClassName() {
		return boardingClassName;
	}

	public void setBoardingClassName(String boardingClassName) {
		this.boardingClassName = boardingClassName;
	}

	public boolean isSubMeal() {
		return subMeal;
	}

	public void setSubMeal(boolean subMeal) {
		this.subMeal = subMeal;
	}
}
