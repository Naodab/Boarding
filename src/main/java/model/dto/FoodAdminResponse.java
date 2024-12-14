package model.dto;

public class FoodAdminResponse {
    private int food_id;
    private String name;
    private boolean category;
    private boolean canDelete;

    public FoodAdminResponse() {}

    public FoodAdminResponse(int food_id, String name, boolean category, boolean canDelete) {
        this.food_id = food_id;
        this.name = name;
        this.category = category;
        this.canDelete = canDelete;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCategory() {
        return category;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}
