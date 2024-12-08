package model.dto;

import java.util.List;

public class SearchStudentResponse {
	private int size;
	private List<StudentResponse> students;

	public SearchStudentResponse() {
		super();
	}

	public SearchStudentResponse(int size, List<StudentResponse> students) {
		super();
		this.size = size;
		this.students = students;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<StudentResponse> getStudents() {
		return students;
	}

	public void setStudents(List<StudentResponse> students) {
		this.students = students;
	}

}
