package com.mas.loftmoney;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.text.TextWatcher;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class AddItemActivity extends AppCompatActivity {

    public static final String KEY_NAME = "name";
    public static final String KEY_AMOUNT = "amount";

    TextInputEditText nameEditText;
    TextInputEditText valueEditText;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button button = findViewById(R.id.button_to_add);
        nameEditText = findViewById(R.id.name_of_expenses_main_field);
        valueEditText = findViewById(R.id.edit_expenses_main_field);

        setTextWatcher(nameEditText, button);
        setTextWatcher(valueEditText,button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String prise = valueEditText.getText().toString();

                Intent intent = new Intent();
                intent.putExtra(KEY_AMOUNT, prise);
                intent.putExtra(KEY_NAME, name);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
    private void setTextWatcher (TextInputEditText editText, Button button) {
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
}
