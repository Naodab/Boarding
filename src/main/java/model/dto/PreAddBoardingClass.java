package model.dto;

import java.util.List;

public class PreAddBoardingClass {
    private int nextId;
    private List<NameAndIdResponse> teachers;

    public PreAddBoardingClass(int nextId, List<NameAndIdResponse> teachers) {
        this.nextId = nextId;
        this.teachers = teachers;
    }

    public PreAddBoardingClass() {}

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public List<NameAndIdResponse> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<NameAndIdResponse> teachers) {
        this.teachers = teachers;
    }
}
