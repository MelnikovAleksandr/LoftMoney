package com.mas.loftmoney.screens.budget;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mas.loftmoney.remote.LoftApp;
import com.mas.loftmoney.remote.MoneyApi;
import com.mas.loftmoney.remote.MoneyRemoteItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BudgetViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Item>> moneyItemList = new MutableLiveData<>();
    public MutableLiveData<String> messageString= new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt= new MutableLiveData<>(-1);
    public MutableLiveData<Boolean> isEditMode = new MutableLiveData<>(false);

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void loadItemsData (MoneyApi moneyApi, String type, SharedPreferences sharedPreferences) {
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY,"");

        compositeDisposable.add(moneyApi.getMoneyItems(type,authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(unsortedList -> {
                    List<MoneyRemoteItem> sortedList = new ArrayList<>(unsortedList);
                    Collections.sort(sortedList, new Comparator<MoneyRemoteItem>() {
                        @Override
                        public int compare(MoneyRemoteItem t1, MoneyRemoteItem t2) {
                            if (t1.getDate() == null || t2.getDate() ==null)
                                return 0;
                            return t1.getDate().compareTo(t2.getDate());
                        }
                    });
                    Collections.reverse(sortedList);
                    return sortedList;
                })
                .subscribe(moneyRemoteItems -> {
                    List<Item> itemList = new ArrayList<>();
                    for (MoneyRemoteItem moneyRemoteItem : moneyRemoteItems) {
                        itemList.add(Item.getInstance(moneyRemoteItem));
                    }
                    moneyItemList.postValue(itemList);
                }, throwable -> {
                    messageString.postValue(throwable.getLocalizedMessage());
                }));
    }
}
