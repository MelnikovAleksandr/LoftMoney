package com.mas.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.loftmoney.cells.Item;
import com.mas.loftmoney.cells.ItemsAdapterIncomes;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment extends Fragment {

    public static final int ADD_ITEM_REQUEST_CODE = 100;
    View v;
    private RecyclerView recyclerView;
    private List<Item> firstFragment;
    public IncomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.recycler_view_income, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.itemsViewIncomesRecycler);
        ItemsAdapterIncomes itemsAdapter = new ItemsAdapterIncomes(getContext(), firstFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        return v;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstFragment = new ArrayList<>();

    }
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final String price = data.getStringExtra("price");
            final String name = data.getStringExtra("name");

            firstFragment.add(new Item(name, price+ " ₽"));


        }
    }
}
