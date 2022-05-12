package com.mas.loftmoney.screens.budget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


public class BudgetFragment extends Fragment implements MoneyCellAdapterClick, ActionMode.Callback {

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
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
            itemsAdapter.setMoneyCellAdapterClick(this);
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
                .moneyApi, type, getActivity().getSharedPreferences(getString(R.string.app_name), 0));

    }

    private void configureViewModel() {
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        budgetViewModel.moneyItemList.observe(getViewLifecycleOwner(), itemList -> {
            itemsAdapter.setData(itemList);
        });
        budgetViewModel.messageString.observe(getViewLifecycleOwner(), message -> {
            if (!message.equals("")) {
                showToast(message);
            }
        });
        budgetViewModel.messageInt.observe(getViewLifecycleOwner(), message -> {
            if (message > 0) {
                showToast(getString(message));
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        mActionMode = actionMode;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getActivity());
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.remove) {
            new AlertDialog.Builder(getContext(), R.style.MyDialogTheme)
                    .setMessage(R.string.confirm)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            mActionMode.finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mActionMode = null;
        itemsAdapter.clearSelections();
    }

    @Override
    public void onCellClick(Item item, int position) {
        if (mActionMode != null) {
            item.setSelected(!item.isSelected());
            itemsAdapter.updateItem(item);
            itemsAdapter.toggleItem(position);
            mActionMode.setTitle(getString(R.string.selected, String.valueOf(itemsAdapter.getSelectedSize())));
        }
    }

    @Override
    public void onLongCellClick(Item item, int position) {
        item.setSelected(true);
        itemsAdapter.updateItem(item);
        budgetViewModel.isEditMode.postValue(true);
        if (mActionMode == null) {
            getActivity().startActionMode(this);
        }
        itemsAdapter.toggleItem(position);
        if (mActionMode != null) {
            mActionMode.setTitle(getString(R.string.selected, String.valueOf(itemsAdapter.getSelectedSize())));
        }
    }
}
