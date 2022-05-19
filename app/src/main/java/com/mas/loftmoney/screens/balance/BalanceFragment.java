package com.mas.loftmoney.screens.balance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mas.loftmoney.R;
import com.mas.loftmoney.remote.LoftApp;
import com.mas.loftmoney.remote.MoneyApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BalanceFragment extends Fragment {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView myExpenses;
    private TextView myIncomes;
    private TextView totalFinances;
    private BalanceView myBalanceView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.balance_fragment, null);
        myExpenses = view.findViewById(R.id.expenses_balance);
        myIncomes = view.findViewById(R.id.incomes_balance);
        totalFinances = view.findViewById(R.id.txtDelta);
        myBalanceView = view.findViewById(R.id.pie_diagram);
        reloadBalance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static BalanceFragment getInstanceBalance() {
        return new BalanceFragment();
    }

    public void getBalances(MoneyApi moneyApi, SharedPreferences sharedPreferences) {
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        compositeDisposable.add(moneyApi.getBalance(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balanceResponse -> {
                    if (balanceResponse.getStatus().equals("success")) {

                        final int EXPENSES = balanceResponse.getTotalExpenses();
                        final int INCOMES = balanceResponse.getTotalIncomes();
                        myExpenses.setText(myExpenses.getContext().getString(R.string.currency_sign, String.valueOf(EXPENSES)));
                        myIncomes.setText(myIncomes.getContext().getString(R.string.currency_sign, String.valueOf(INCOMES)));
                        totalFinances.setText(totalFinances.getContext().getString(R.string.currency_sign, String.valueOf(INCOMES - EXPENSES)));
                        myBalanceView.update(EXPENSES, INCOMES);
                    }
                }, Throwable::getLocalizedMessage));
    }

    public void reloadBalance() {
        getBalances(((LoftApp) getActivity().getApplication()).moneyApi, getActivity().getSharedPreferences(getString(R.string.app_name), 0));
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadBalance();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
            reloadBalance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
