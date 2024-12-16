package model.dto;

import java.time.LocalDate;

public class StudentResponse {
	private int student_id;
	private String name;
	private LocalDate dateOfBirth;
	private String address;
	private String sex;
	private int parents_id;
	private String parentName;
	private int boardingClass_id;

	public int getParents_id() {
		return parents_id;
	}

	public void setParents_id(int parents_id) {
		this.parents_id = parents_id;
	}

	public int getBoardingClass_id() {
		return boardingClass_id;
	}

	public void setBoardingClass_id(int boardingClass_id) {
		this.boardingClass_id = boardingClass_id;
	}

	private String boardingClassName;
	private boolean subMeal;

	public StudentResponse(int student_id, String name, LocalDate dateOfBirth, String address, Boolean sex,
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

	public StudentResponse(int student_id, String name, LocalDate dateOfBirth, String address, Boolean sex,
			boolean subMeal, int parents_id, int boardingClass_id) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.sex = sex ? "Nam" : "Nữ";
		this.subMeal = subMeal;
		this.parents_id = parents_id;
		this.boardingClass_id = boardingClass_id;
	}
	
	private double height;
	private double weight;
	private int numberOfAbsences;
	
	public StudentResponse(int student_id, String name, LocalDate dateOfBirth, String address, Boolean sex,
			boolean subMeal, int parents_id, int boardingClass_id, double height, double weight, int numberOfAbsences) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.sex = sex ? "Nam" : "Nữ";
		this.subMeal = subMeal;
		this.parents_id = parents_id;
		this.boardingClass_id = boardingClass_id;
		this.height = height;
		this.weight = weight;
		this.numberOfAbsences = numberOfAbsences;
	}
	
	public StudentResponse(int student_id, String name, LocalDate dateOfBirth, String address, Boolean sex,
			int boardingClass_id, double height, double weight) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.sex = sex ? "Nam" : "Nữ";
		this.height = height;
		this.weight = weight;
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
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

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getNumberOfAbsences() {
		return numberOfAbsences;
	}

	public void setNumberOfAbsences(int numberOfAbsences) {
		this.numberOfAbsences = numberOfAbsences;
	}
}
