package com.mas.loftmoney.screens.balance;

import com.mas.loftmoney.remote.BalanceResponse;

public class Balance {
    private int totalExpenses;
    private int totalIncomes;

    public Balance(int totalExpenses, int totalIncomes) {
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public int getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(int totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public static Balance getInstanceBalance(BalanceResponse balanceResponse) {
        return new Balance(balanceResponse.getTotalExpenses(), balanceResponse.getTotalIncomes());
    }
}
