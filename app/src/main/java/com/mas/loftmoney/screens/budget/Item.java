package com.mas.loftmoney.screens.budget;

import com.mas.loftmoney.remote.MoneyRemoteItem;

public class Item {

    private String name;
    private double amount;
    private boolean isSelected;
    private int id;

    public Item(String name, double amount, int id) {
        this.name = name;
        this.amount = amount;
        this.isSelected = false;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static Item getInstance(MoneyRemoteItem moneyRemoteItem) {
        return new Item(moneyRemoteItem.getName(), moneyRemoteItem.getPrice(), moneyRemoteItem.getId());
    }
}
