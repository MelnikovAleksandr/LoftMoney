package com.mas.loftmoney;


import static com.mas.loftmoney.AddItemActivity.KEY_AMOUNT;
import static android.app.Activity.RESULT_OK;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mas.loftmoney.cells.Item;


public class BudgetFragment extends Fragment {

    private static final int ADD_ITEM_REQUEST_CODE = 100;
    private static final String COLOR_ID = "colorId";
    private static final String TYPE = "fragmentType";


    private ItemsAdapter itemsAdapter;

    public static BudgetFragment newInstance (final int colorId, String type) {
        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COLOR_ID, colorId);
        bundle.putString(TYPE, type);
        budgetFragment.setArguments(bundle);
        return budgetFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.add_button_main);
        floatingActionButton.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), AddItemActivity.class), ADD_ITEM_REQUEST_CODE));

        RecyclerView recyclerView = view.findViewById(R.id.itemsViewExpensesRecycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        if (getArguments() != null) {
            itemsAdapter = new ItemsAdapter(getArguments().getInt(COLOR_ID));
        } else {
            itemsAdapter = new ItemsAdapter(R.color.cell_value_color);
        }
        recyclerView.setAdapter(itemsAdapter);

        itemsAdapter.addItem(new Item("Milk", 100));

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == RESULT_OK) {
            if(data != null)
                itemsAdapter.addItem(
                        new Item(
                                data.getStringExtra(AddItemActivity.KEY_NAME),
                                Integer.parseInt(data.getStringExtra(KEY_AMOUNT))
                        )
                );
        }
    }
}
