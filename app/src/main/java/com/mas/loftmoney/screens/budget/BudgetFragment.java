package com.mas.loftmoney.screens.budget;

import android.os.Bundle;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mas.loftmoney.remote.LoftApp;
import com.mas.loftmoney.R;
import com.mas.loftmoney.remote.MoneyApi;
import com.mas.loftmoney.screens.main.EditModeListener;


public class BudgetFragment extends Fragment {

    private static final String COLOR_ID = "colorId";
    public static final String TYPE = "fragmentType";

    private ItemsAdapter itemsAdapter = new ItemsAdapter();

    private SwipeRefreshLayout swipeRefreshLayout;

    private MoneyApi moneyApi;
    private String type;

    private BudgetViewModel budgetViewModel;

    private ActionMode mActionMode;

    public static BudgetFragment newInstance(final int colorId, final String type) {
        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COLOR_ID, colorId);
        bundle.putString(TYPE, type);
        budgetFragment.setArguments(bundle);
        return budgetFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapter.setMoneyCellAdapterClick(new MoneyCellAdapterClick() {

            @Override
            public void onCellClick(Item item) {
                showToast("Click " + item.getName());
            }

            @Override
            public void onLongCellClick(Item item) {

                showToast("LongClick " + item.getName());

                if(!budgetViewModel.isEditMode.getValue()) {
                budgetViewModel.isEditMode.postValue(true);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureViewModel();

        moneyApi = ((LoftApp) getActivity().getApplication()).moneyApi;
        RecyclerView recyclerView = view.findViewById(R.id.itemsViewRecycler);
        swipeRefreshLayout = view.findViewById(R.id.mainSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(itemsAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        if (getArguments() != null) {
            itemsAdapter.setColor(getArguments().getInt(COLOR_ID));
            type = getArguments().getString(TYPE);
        } else {
            itemsAdapter.setColor(R.color.cell_value_color);
        }
        recyclerView.setAdapter(itemsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        loadItems();
    }

    private void loadItems() {

        budgetViewModel.loadItemsData(((LoftApp) getActivity().getApplication())
                .moneyApi, type, getActivity().getSharedPreferences(getString(R.string.app_name),0));

    }
    private void configureViewModel() {
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        budgetViewModel.moneyItemList.observe(getViewLifecycleOwner(), itemList -> {
            itemsAdapter.setData(itemList);
        });

        budgetViewModel.isEditMode.observe(getViewLifecycleOwner(),isEditMode -> {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onEditModeChanged(isEditMode);
            } else {

            }
        });
        budgetViewModel.messageString.observe(getViewLifecycleOwner(), message -> {
            if(!message.equals("")) {
                showToast(message);
            }
        });
        budgetViewModel.messageInt.observe(getViewLifecycleOwner(), message -> {
            if (message > 0 ) {
                showToast(getString(message));
            }
        });
    }

    private void showToast (String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
