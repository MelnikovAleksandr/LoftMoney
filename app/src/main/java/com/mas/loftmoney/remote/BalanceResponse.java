package com.mas.loftmoney.remote;

import com.google.gson.annotations.SerializedName;

public class BalanceResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("total_expenses")
    private int totalExpenses;

    @SerializedName("total_income")
    private int totalIncomes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
