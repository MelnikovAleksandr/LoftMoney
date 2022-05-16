package com.mas.loftmoney.screens.balance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.mas.loftmoney.R;

public class BalanceView extends View {

    private int expenses = 5400;
    private int incomes = 7400;

    private int expensesColor, incomesColor;

    private Paint expensePaint = new Paint();
    private Paint incomePaint = new Paint();

    public BalanceView(Context context) {
        super(context);
        init();
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.PieTheme, 0, 0);
        expensesColor = a.getInteger(R.styleable.PieTheme_expenses_pie_color, 0);
        incomesColor = a.getInteger(R.styleable.PieTheme_incomes_pie_color, 0);
        init();
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void update(int expenses, int incomes) {
        this.expenses = expenses;
        this.incomes = incomes;

        invalidate();
    }

    private void init() {

        expensePaint.setColor(expensesColor);
        incomePaint.setColor(incomesColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float total = expenses + incomes;

        float expenseAngle = 360 * expenses / total;
        float incomesAngle = 360 * incomes / total;

        int space = 10;
        int size = Math.min(getWidth(), getHeight()) - space * 2;
        int xMargin = (getWidth() - size) / 2;
        int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin,
                getWidth() - xMargin - space,
                getHeight() - yMargin, 180 - expenseAngle / 2,
                expenseAngle, true, expensePaint);

        canvas.drawArc(xMargin + space, yMargin,
                getWidth() - xMargin + space,
                getHeight() - yMargin, 360 - incomesAngle / 2,
                incomesAngle, true, incomePaint);
    }
}