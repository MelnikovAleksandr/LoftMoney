package com.mas.loftmoney.screens.balance;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mas.loftmoney.remote.BalanceResponse;
import com.mas.loftmoney.remote.LoftApp;
import com.mas.loftmoney.remote.MoneyApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BalanceViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Balance>> balanceMutableData = new MutableLiveData<>();
    public MutableLiveData<Boolean> getBalanceSuccess = new MutableLiveData<>(false);

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void getBalances(MoneyApi moneyApi, SharedPreferences sharedPreferences) {
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        compositeDisposable.add(moneyApi.getBalance(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balanceResponse -> {
                    List<Balance> balanceList1 = new ArrayList<>();
                    for (BalanceResponse balanceResponse1 : balanceResponse) {
                        balanceList1.add(Balance.getInstanceBalance(balanceResponse1));
                    }
                    getBalanceSuccess.postValue(true);
                    balanceMutableData.postValue(balanceList1);
                }, error -> {
                    getBalanceSuccess.postValue(false);
                    //messageString.postValue(error.getLocalizedMessage());
                }));
    }
}
