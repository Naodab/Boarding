package model.dto;

import java.time.LocalDate;

public class TeacherResponse {
    private int teacher_id;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String email;
    private String boardingClass;
    private boolean sex;

    public TeacherResponse() {}

    public TeacherResponse(int teacher_id, String name, LocalDate dateOfBirth,
                           String address, String phone, String email, boolean sex) {
        this.teacher_id = teacher_id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBoardingClass() {
        return boardingClass;
    }

    public void setBoardingClass(String boardingClass) {
        this.boardingClass = boardingClass;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
