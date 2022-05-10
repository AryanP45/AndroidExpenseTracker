package com.learntodroid.piechartandroid;

public class rowStructure {
    private   String amount;
    private  String category;
    private  String Date;
    private  String note;
    private  String id;

    public rowStructure(String amount, String category, String date, String note, String id) {
        this.amount = amount;
        this.category = category;
        this.Date = date;
        this.note = note;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
