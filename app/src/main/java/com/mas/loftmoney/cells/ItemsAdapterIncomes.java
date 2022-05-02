package com.mas.loftmoney.cells;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.loftmoney.R;

import java.util.List;

public class ItemsAdapterIncomes extends RecyclerView.Adapter<ItemsAdapterIncomes.ItemViewHolder2> {

    Context context;
    List<Item> data;

    public ItemsAdapterIncomes(Context context, List<Item> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.cell_money_income, parent, false);
        ItemsAdapterIncomes.ItemViewHolder2 holder2 = new ItemsAdapterIncomes.ItemViewHolder2(v);
        return holder2;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapterIncomes.ItemViewHolder2 holder, int position) {
        holder.titleTextView2.setText(data.get(position).getTitle());
        holder.valueTextView2.setText(data.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    static class ItemViewHolder2 extends RecyclerView.ViewHolder {

        private TextView titleTextView2;
        private TextView valueTextView2;

        public ItemViewHolder2(@NonNull View itemView) {
            super(itemView);

            titleTextView2 = itemView.findViewById(R.id.moneyCellTitleView2);
            valueTextView2 = itemView.findViewById(R.id.moneyCellValueView2);
        }
    }
}

