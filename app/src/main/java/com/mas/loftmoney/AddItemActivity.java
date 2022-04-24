package com.mas.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button button = findViewById(R.id.button_to_add);
        button.setEnabled(false);
        TextInputEditText costIncomes = findViewById(R.id.edit_expenses_main_field);
        TextInputEditText textIncomes = findViewById(R.id.name_of_expenses_main_field);

        TextInputEditText [] edList = {costIncomes, textIncomes};
        ButtonTextWatcher textWatcher = new ButtonTextWatcher(edList, button);
        for (TextInputEditText editText : edList) editText.addTextChangedListener(textWatcher);

    }
}