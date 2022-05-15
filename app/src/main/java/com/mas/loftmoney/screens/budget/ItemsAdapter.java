package com.mas.loftmoney.screens.budget;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final List<Item> itemList = new ArrayList<>();
    private int colorId;
    public MoneyCellAdapterClick moneyCellAdapterClick;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public ItemsAdapter() {
    }

    public void updateItem(Item moneyItem) {
        int itemPosition = itemList.indexOf(moneyItem);
        itemList.set(itemPosition, moneyItem);
        notifyItemChanged(itemPosition);
    }

    public void setColor(int colorId) {
        this.colorId = colorId;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.cell_money, null);
        return new ItemViewHolder(itemView, colorId, moneyCellAdapterClick);
    }

    public void setMoneyCellAdapterClick(MoneyCellAdapterClick moneyCellAdapter) {
        moneyCellAdapterClick = moneyCellAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindItem(itemList.get(position), selectedItems.get(position));
        holder.setMoneyCellAdapterClick(moneyCellAdapterClick, itemList.get(position), position);


    }

    public void setData(List<Item> itemsList) {
        itemList.clear();
        itemList.addAll(itemsList);
        notifyDataSetChanged();
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView valueTextView;
        public MoneyCellAdapterClick moneyCellAdapterClick;


        public ItemViewHolder(@NonNull View view, int colorId, MoneyCellAdapterClick moneyCellAdapterClick) {
            super(view);

            this.moneyCellAdapterClick = moneyCellAdapterClick;
            titleTextView = itemView.findViewById(R.id.moneyCellTitleView);
            valueTextView = itemView.findViewById(R.id.moneyCellValueView);
            valueTextView.setTextColor(ContextCompat.getColor(valueTextView.getContext(), colorId));
        }

        public void bindItem(@NonNull final Item item, final boolean isSelected) {

            itemView.setSelected(isSelected);
            titleTextView.setText(item.getName());
            valueTextView.setText(valueTextView.getContext().getString(R.string.currency_sign, String.valueOf(item.getAmount())));
            item.setSelected(isSelected);
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),
                    item.isSelected() ? R.color.selected_cell_menu : R.color.white));

        }

        public void setMoneyCellAdapterClick(MoneyCellAdapterClick moneyCellAdapterClick, final Item item, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (moneyCellAdapterClick != null) {
                        moneyCellAdapterClick.onCellClick(item, position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (moneyCellAdapterClick != null) {
                        moneyCellAdapterClick.onLongCellClick(item, position);
                    }
                    return true;
                }
            });
        }
    }

    public void toggleItem(final int position) {
        selectedItems.put(position, !selectedItems.get(position));
        notifyDataSetChanged();
    }

    public int getSelectedSize() {
        int result = 0;
        for (int i = 0; i < itemList.size(); i++) {
            if (selectedItems.get(i)) {
                result++;
            }
        }
        return result;
    }
    public List<Item> getMoneyItemList() {
        return itemList;
    }

}
