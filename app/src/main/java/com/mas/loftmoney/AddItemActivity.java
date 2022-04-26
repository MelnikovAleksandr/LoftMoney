package com.mas.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText textInputEditText1;
    TextInputEditText textInputEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button button = findViewById(R.id.button_to_add);
        button.setEnabled(false);
        TextInputEditText costIncomes = findViewById(R.id.edit_expenses_main_field);
        TextInputEditText textIncomes = findViewById(R.id.name_of_expenses_main_field);

        TextInputEditText[] edList = {costIncomes, textIncomes};
        ButtonTextWatcher textWatcher = new ButtonTextWatcher(edList, button);
        for (TextInputEditText editText : edList) editText.addTextChangedListener(textWatcher);

        textInputEditText1 = (TextInputEditText) findViewById(R.id.name_of_expenses_main_field);
        textInputEditText2 = (TextInputEditText) findViewById(R.id.edit_expenses_main_field);
        button = (Button) findViewById(R.id.button_to_add);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,ItemActivity.class);
        intent.putExtra("name",textInputEditText1.getText().toString());
        intent.putExtra("cost",textInputEditText2.getText().toString());
        startActivity(intent);
    }
}