package com.mas.loftmoney;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.loftmoney.cells.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final List<Item> itemList = new ArrayList<>();
    private final int colorId;

    public ItemsAdapter(int colorId) {
        this.colorId = colorId;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.cell_money, null);
        return new ItemViewHolder(itemView, colorId);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindItem(itemList.get(position));

    }

    public void setData(List<Item> itemsList) {
        itemList.clear();
        itemList.addAll(itemsList);
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        itemList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView valueTextView;

        public ItemViewHolder(@NonNull View view, int colorId) {
            super(view);

            titleTextView = itemView.findViewById(R.id.moneyCellTitleView);
            valueTextView = itemView.findViewById(R.id.moneyCellValueView);
            valueTextView.setTextColor(ContextCompat.getColor(valueTextView.getContext(), colorId));
        }

        public void bindItem(@NonNull final Item item) {
            titleTextView.setText(item.getName());
            valueTextView.setText(String.valueOf(item.getAmount()) + " $");

        }
    }


}
