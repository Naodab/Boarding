package model.dto;

public class BoardingClassResponse {
    private int boardingClassId;
    private int teacherId;
    private String name;
    private String room;
    private int numberOfBed;
    private int numberStudent;
    private String teacherName;

    public BoardingClassResponse(int boardingClassId, int teacherId, String name, int numberOfBed, String room) {
        this.room = room;
        this.teacherId = teacherId;
        this.numberOfBed = numberOfBed;
        this.name = name;
        this.boardingClassId = boardingClassId;
    }

    public BoardingClassResponse() {}

    public int getBoardingClassId() {
        return boardingClassId;
    }

    public void setBoardingClassId(int boardingClassId) {
        this.boardingClassId = boardingClassId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public int getNumberStudent() {
        return numberStudent;
    }

    public void setNumberStudent(int numberStudent) {
        this.numberStudent = numberStudent;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
