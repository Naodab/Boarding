package model.dto;

import java.util.List;

public class SearchResponse<T> {
    private int size;
    private List<T> items;

    public SearchResponse() {}

    public SearchResponse(int size, List<T> items) {
        this.size = size;
        this.items = items;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
