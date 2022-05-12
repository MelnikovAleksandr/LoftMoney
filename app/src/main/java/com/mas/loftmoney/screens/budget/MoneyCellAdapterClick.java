package com.mas.loftmoney.screens.budget;

public interface MoneyCellAdapterClick {
    void onCellClick(Item item, int position);

    void onLongCellClick(Item item, int position);
}