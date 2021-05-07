package com.example.week9;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class WeekCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS = 100;

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int cur_week = cal.get(Calendar.WEEK_OF_MONTH)-1;
    public WeekCalendarAdapter(FragmentActivity fa){
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        return WeekCalendarFragment.newInstance(year+(month+(cur_week+position+4)/6-9)/12, (month+(cur_week+position+4)/6-9), (cur_week+position+4)%6); //수정해야됨
    }

    @Override
    public int getItemCount() { return NUM_ITEMS; }
}
