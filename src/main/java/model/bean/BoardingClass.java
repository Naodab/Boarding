package model.bean;

import java.util.List;

public class BoardingClass {
	private int boardingClass_id;
	private String name;
	private int numberOfBed;
	private String room;
	private int teacher_id;
	private List<Integer> student_ids;

	public BoardingClass() {
		super();
	}

	public BoardingClass(int boardingClass_id, String name, int numberOfBed, String room, int teacher_id,
			List<Integer> student_ids) {
		super();
		this.boardingClass_id = boardingClass_id;
		this.name = name;
		this.numberOfBed = numberOfBed;
		this.room = room;
		this.teacher_id = teacher_id;
		this.student_ids = student_ids;
	}

	public int getBoardingClass_id() {
		return boardingClass_id;
	}

	public void setBoardingClass_id(int boardingClass_id) {
		this.boardingClass_id = boardingClass_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfBed() {
		return numberOfBed;
	}

	public void setNumberOfBed(int numberOfBed) {
		this.numberOfBed = numberOfBed;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public List<Integer> getStudent_ids() {
		return student_ids;
	}

	public void setStudent_ids(List<Integer> student_ids) {
		this.student_ids = student_ids;
	}
}
