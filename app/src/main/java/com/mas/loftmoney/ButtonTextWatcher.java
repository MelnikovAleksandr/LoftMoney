package com.mas.loftmoney;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class ButtonTextWatcher implements TextWatcher {

    View v;
    TextInputEditText[] edList;

    public ButtonTextWatcher(TextInputEditText[] edList, Button v) {
        this.v = v;
        this.edList = edList;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        for (TextInputEditText editText : edList) {
            if (editText.getText().toString().trim().length() <= 0) {
                v.setEnabled(false);
                break;
            } else v.setEnabled(true);
        }
    }
}

