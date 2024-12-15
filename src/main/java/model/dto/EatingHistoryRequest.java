package model.dto;

import java.time.LocalDate;

public class EatingHistoryRequest {
    int menu_id;
    LocalDate eating_day;

    public EatingHistoryRequest(int menu_id, LocalDate eating_date) {
        this.menu_id = menu_id;
        this.eating_day = eating_date;
    }

    public EatingHistoryRequest() {}

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public LocalDate getEating_day() {
        return eating_day;
    }

    public void setEating_day(LocalDate eating_day) {
        this.eating_day = eating_day;
    }
}
