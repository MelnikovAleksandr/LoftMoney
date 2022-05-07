package com.mas.loftmoney;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mas.loftmoney.cells.Item;
import com.mas.loftmoney.remote.MoneyApi;
import com.mas.loftmoney.remote.MoneyRemoteItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class BudgetFragment extends Fragment {

    private static final String COLOR_ID = "colorId";
    public static final String TYPE = "fragmentType";

    private ItemsAdapter itemsAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private SwipeRefreshLayout swipeRefreshLayout;

    private MoneyApi moneyApi;
    private String type;


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
        return inflater.inflate(R.layout.recycler_view,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            itemsAdapter = new ItemsAdapter(getArguments().getInt(COLOR_ID));
            type = getArguments().getString(TYPE);
        } else {
            itemsAdapter = new ItemsAdapter(R.color.cell_value_color);
        }
        recyclerView.setAdapter(itemsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        loadItems();
    }

    private void loadItems() {

        Disposable disposable = moneyApi.getMoneyItems(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                    List<Item> itemList = new ArrayList<>();
                    for (MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemsList()) {
                        itemList.add(Item.getInstance(moneyRemoteItem));
                    }
                    itemsAdapter.setData(itemList);
                }, throwable -> {
                    Toast.makeText(getActivity().getApplication(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
