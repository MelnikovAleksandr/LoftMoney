package com.mas.loftmoney;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.mas.loftmoney.cells.ItemsAdapter;
import com.mas.loftmoney.cells.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemActivity extends AppCompatActivity {

    private RecyclerView itemsView;

    private ItemsAdapter itemsAdapter = new ItemsAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        configureRecyclerView();

        generateMoney();

        DividerItemDecoration divider = new DividerItemDecoration(itemsView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider)));
        itemsView.addItemDecoration(divider);


    }

    private void generateMoney() {
        List<Item> items = new ArrayList<>();
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String cost = intent.getStringExtra("cost");
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));
        items.add(new Item(name, cost));

        itemsAdapter.setData(items);
    }

    private void configureRecyclerView() {
        itemsView = findViewById(R.id.itemsView);
        itemsView.setAdapter(itemsAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);

        itemsView.setLayoutManager(layoutManager);
    }



}