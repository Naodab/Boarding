package model.dto;

import java.time.LocalDate;

public class UserResponse {
    private String username;
    private LocalDate lastLogin;
    private int userId;
    private String position;
    private String name;
    private boolean active;

    public UserResponse(boolean active, String position, String username, LocalDate lastLogin) {
        this.active = active;
        this.position = position;
        this.username = username;
        this.lastLogin = lastLogin;
    }

    public UserResponse() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
