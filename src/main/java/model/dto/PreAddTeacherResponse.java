package model.dto;

import java.util.List;

public class PreAddTeacherResponse {
    private int nextId;
    private List<NameAndIdResponse> classes;

    public PreAddTeacherResponse(int nextId, List<NameAndIdResponse> nameAndIdResponses) {
        this.nextId = nextId;
        this.classes = nameAndIdResponses;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public List<NameAndIdResponse> getClasses() {
        return classes;
    }

    public void setClasses(List<NameAndIdResponse> classes) {
        this.classes = classes;
    }
}
