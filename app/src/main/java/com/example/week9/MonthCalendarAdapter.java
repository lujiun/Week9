package com.example.week9;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MonthCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=100;
    public MonthCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int year = position;
        int month = position+1;
        return MonthCalendarFragment.newInstance(year, month);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
