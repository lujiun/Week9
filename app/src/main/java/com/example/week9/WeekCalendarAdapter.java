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
    int date = cal.get(Calendar.DATE);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1; // 첫날 요일구하기
    public WeekCalendarAdapter(FragmentActivity fa){
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        int y,m,d,fd;
        cal.set(year,month,date+((position-50)*7));
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        fd = d-dayOfWeek;
        return WeekCalendarFragment.newInstance(y,m,fd);
    }

    @Override
    public int getItemCount() { return NUM_ITEMS; }
}
