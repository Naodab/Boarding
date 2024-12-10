package model.dto;

import java.time.LocalDate;

public class ParentsResponse {
    private int parents_id;
    private String name;
    private boolean sex;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String email;
	private int numberChildren;

    public ParentsResponse(int parents_id, String name, boolean sex,
						   LocalDate dateOfBirth, String address,
						   String phone, String email) {
        this.parents_id = parents_id;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

	public ParentsResponse() {}

	public int getParents_id() {
		return parents_id;
	}

	public String getName() {
		return name;
	}

	public boolean isSex() {
		return sex;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public void setParents_id(int parents_id) {
		this.parents_id = parents_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumberChildren() {
		return numberChildren;
	}

	public void setNumberChildren(int numberChildren) {
		this.numberChildren = numberChildren;
	}
}
