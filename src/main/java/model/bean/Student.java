package model.bean;

import java.sql.Date;
import java.util.List;

public class Student extends Person {
	private int student_id;
	private double weight;
	private double height;
	private int parents_id;
	private int boardingClass_id;
	private boolean subMeal;
	private List<Integer> absence_ids;
	private List<Integer> invoice_ids;

	public Student() {
		super();
	}

	public Student(int student_id, double weight, double height, int parents_id, int boardingClass_id, boolean subMeal,
			List<Integer> absence_ids, List<Integer> invoice_ids) {
		super();
		this.student_id = student_id;
		this.weight = weight;
		this.height = height;
		this.parents_id = parents_id;
		this.boardingClass_id = boardingClass_id;
		this.subMeal = subMeal;
		this.absence_ids = absence_ids;
		this.invoice_ids = invoice_ids;
	}

	public Student(String name, Date dateOfBirth, String address, Boolean sex) {
		super(name, dateOfBirth, address, sex);
	}

	public Student(String name, Date dateOfBirth, String address, Boolean sex, int student_id,
			double weight, double height, int parents_id, int boardingClass_id, boolean subMeal, List<Integer> absence_ids,
			List<Integer> invoice_ids) {
		super(name, dateOfBirth, address, sex);
		this.student_id = student_id;
		this.weight = weight;
		this.height = height;
		this.parents_id = parents_id;
		this.boardingClass_id = boardingClass_id;
		this.subMeal = subMeal;
		this.absence_ids = absence_ids;
		this.invoice_ids = invoice_ids;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

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

	public boolean isSubMeal() {
		return subMeal;
	}

	public void setSubMeal(boolean subMeal) {
		this.subMeal = subMeal;
	}

	public List<Integer> getAbsence_ids() {
		return absence_ids;
	}

	public void setAbsence_ids(List<Integer> absence_ids) {
		this.absence_ids = absence_ids;
	}

	public List<Integer> getInvoice_ids() {
		return invoice_ids;
	}

	public void setInvoice_ids(List<Integer> invoice_ids) {
		this.invoice_ids = invoice_ids;
	}

	@Override
	public String toString() {
		return "Student [student_id=" + student_id + ", weight=" + weight + ", height=" + height + ", parents_id="
				+ parents_id + ", boardingClass_id=" + boardingClass_id + ", subMeal=" + subMeal + "]";
	}
}
