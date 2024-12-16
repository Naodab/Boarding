package model.dto;

import java.util.Date;
import java.util.List;

public class ParentResponse {
	private String parentName;
	private Date dateOfBirth;
	private int parentId;
	private boolean sex;
	private String address;
	private String phoneNumber;
	private String email;
	private List<Integer> studentIdList;
	private List<Integer> teacherIdList;
	private List<String> studentNameList;
	private List<String> teacherNameList;
	
	public ParentResponse() {}

	public ParentResponse(String parentName, Date dateOfBirth, int parentId, boolean sex, String address,
			String phoneNumber, String email, List<Integer> studentIdList, List<Integer> teacherIdList,
			List<String> studentNameList, List<String> teacherNameList) {
		this.parentName = parentName;
		this.dateOfBirth = dateOfBirth;
		this.parentId = parentId;
		this.sex = sex;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.studentIdList = studentIdList;
		this.teacherIdList = teacherIdList;
		this.studentNameList = studentNameList;
		this.teacherNameList = teacherNameList;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<Integer> getStudentIdList() {
		return studentIdList;
	}

	public void setStudentIdList(List<Integer> studentIdList) {
		this.studentIdList = studentIdList;
	}

	public List<Integer> getTeacherIdList() {
		return teacherIdList;
	}

	public void setTeacherIdList(List<Integer> teacherIdList) {
		this.teacherIdList = teacherIdList;
	}

	public List<String> getStudentNameList() {
		return studentNameList;
	}

	public void setStudentNameList(List<String> studentNameList) {
		this.studentNameList = studentNameList;
	}

	public List<String> getTeacherNameList() {
		return teacherNameList;
	}

	public void setTeacherNameList(List<String> teacherNameList) {
		this.teacherNameList = teacherNameList;
	}
}
