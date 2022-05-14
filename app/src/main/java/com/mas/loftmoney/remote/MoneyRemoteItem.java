package com.mas.loftmoney.remote;

import com.google.gson.annotations.SerializedName;

public class MoneyRemoteItem {
    @SerializedName("id")
    private int ItemId;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private double price;
    @SerializedName("type")
    private String type;
    @SerializedName("date")
    private String date;

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
