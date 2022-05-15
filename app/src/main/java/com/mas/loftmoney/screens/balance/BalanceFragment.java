package com.mas.loftmoney.screens.balance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mas.loftmoney.R;
import com.mas.loftmoney.remote.LoftApp;
import com.mas.loftmoney.remote.MoneyApi;
import com.mas.loftmoney.screens.budget.BudgetViewModel;
import com.mas.loftmoney.screens.budget.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;


public class BalanceFragment extends Fragment {

    private final List<Balance> balanceList = new ArrayList<>();

    private BalanceViewModel balanceViewModel;
    private MoneyApi moneyApi;
    private TextView myExpenses;
    private TextView myIncomes;
    private TextView totalFinances;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.balance_fragment, null);
        myExpenses = view.findViewById(R.id.expenses_balance);
        myIncomes = view.findViewById(R.id.incomes_balance);
        totalFinances = view.findViewById(R.id.txtDelta);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureViewModel();
    }

    private void configureViewModel() {
        balanceViewModel = new ViewModelProvider(this).get(BalanceViewModel.class);
        balanceViewModel.getBalanceSuccess.observe(getViewLifecycleOwner(), success -> {
            if (success) {
                getBalances();
            }
        });
        balanceViewModel.balanceMutableData.observe(getViewLifecycleOwner(), balanceList -> {
            setDataBalance(balanceList);

        });
    }

    public static BalanceFragment getInstanceBalance() { return new BalanceFragment();}

    private void getBalances() {
        balanceViewModel.getBalances(((LoftApp) getActivity().getApplication())
                .moneyApi, getActivity().getSharedPreferences(getString(R.string.app_name), 0));

    }
    public void setDataBalance(List<Balance> balancesList) {
        balanceList.clear();
        balanceList.addAll(balancesList);
    }
}
