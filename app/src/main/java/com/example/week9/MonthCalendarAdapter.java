package com.example.week9;

import android.app.ActionBar;
import android.content.Intent;
import android.view.View;
import android.view.LayoutInflater;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;

public class MonthCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=100;

    public MonthCalendarAdapter(FragmentActivity fragment) {
        super(fragment);
    }

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int current_year = year;
    int current_month = month;

    public Fragment createFragment(int position) {
        return MonthCalendarFragment.newInstance(current_year + ((current_month+position)/12), (current_month + position)%12);
    }

    public int getItemCount() {
        return NUM_ITEMS;
    }
}
