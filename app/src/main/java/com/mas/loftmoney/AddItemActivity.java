package com.mas.loftmoney;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mas.loftmoney.remote.MoneyApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddItemActivity extends AppCompatActivity {

    TextInputEditText nameEditText;
    TextInputEditText valueEditText;

    MoneyApi moneyApi;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button button = findViewById(R.id.button_to_add);
        nameEditText = findViewById(R.id.name_of_expenses_main_field);
        valueEditText = findViewById(R.id.edit_expenses_main_field);

        setTextWatcher(nameEditText, button);
        setTextWatcher(valueEditText, button);

        moneyApi = ((LoftApp) getApplication()).moneyApi;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                int price = Integer.parseInt(valueEditText.getText().toString());

                Bundle args = getIntent().getExtras();
                String type = args.getString(BudgetFragment.TYPE);
                Disposable disposable = moneyApi.addMoney(price, name, type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            finish();
                        }, throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        });
                compositeDisposable.add(disposable);
            }
        });
    }

    private void setTextWatcher(TextInputEditText editText, Button button) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!nameEditText.getText().toString().isEmpty() && !valueEditText.getText().toString().isEmpty()) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();

    }
}
