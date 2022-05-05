package com.mas.loftmoney.cells;

import com.mas.loftmoney.remote.MoneyRemoteItem;

public class Item {

    private String name;
    private double amount;

    public Item(String name, double amount) {
        this.name = name;
        this.amount = amount;
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
