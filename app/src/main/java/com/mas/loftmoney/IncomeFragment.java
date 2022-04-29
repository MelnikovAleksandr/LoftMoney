package com.mas.loftmoney;

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
import com.mas.loftmoney.cells.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment extends Fragment {

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
        ItemsAdapter itemsAdapter = new ItemsAdapter(getContext(), firstFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        return v;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstFragment = new ArrayList<>();
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));
        firstFragment.add(new Item("salary", "1000$"));




    }
}
