package com.mobillium.bukoliandroidsdk.models;

/**
 * Created by oguzhandongul on 06/05/16.
 */
public class Pagination {
    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private int first_item;
    private int last_item;

    public Pagination(int total, int per_page, int current_page, int last_page, int first_item, int last_item) {
        this.total = total;
        this.per_page = per_page;
        this.current_page = current_page;
        this.last_page = last_page;
        this.first_item = first_item;
        this.last_item = last_item;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getFirst_item() {
        return first_item;
    }

    public void setFirst_item(int first_item) {
        this.first_item = first_item;
    }

    public int getLast_item() {
        return last_item;
    }

    public void setLast_item(int last_item) {
        this.last_item = last_item;
    }
}
