package com.mas.loftmoney.screens.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mas.loftmoney.R;
import com.mas.loftmoney.screens.add_item.AddItemActivity;
import com.mas.loftmoney.screens.budget.BudgetFragment;

public class MainActivity extends AppCompatActivity {

    private int currentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPager2 viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new ViewPagerAdapter(this));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.add_button_main);
        Intent intent = new Intent(this, AddItemActivity.class);

        floatingActionButton.setOnClickListener(view -> {
            String type = "0";
            if (currentPosition == 0) {
                type = "expense";
            } else if (currentPosition == 1) {
                type = "income";
            }
            intent.putExtra(BudgetFragment.TYPE, type);
            startActivity(intent);
        });

        final String[] fragmentsTitles = new String[]{getString(R.string.expenses), getString(R.string.incomes)};

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(fragmentsTitles[position])).attach();
    }

    public class ViewPagerAdapter extends FragmentStateAdapter {

        private Fragment fragment;

        public Fragment getFragment() {
            return fragment;
        }

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return BudgetFragment.newInstance(R.color.cell_value_color, getString(R.string.expense));
                case 1:
                    return BudgetFragment.newInstance(R.color.all_green_elements, getString(R.string.income));
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
