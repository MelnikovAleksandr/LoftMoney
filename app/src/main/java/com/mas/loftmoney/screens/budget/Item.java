package com.mas.loftmoney.screens.budget;

import com.mas.loftmoney.remote.MoneyRemoteItem;

public class Item {

    private String name;
    private double amount;
    private boolean isSelected;

    public Item(String name, double amount) {
        this.name = name;
        this.amount = amount;
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
        return new Item(moneyRemoteItem.getName(), moneyRemoteItem.getPrice());
    }
}
