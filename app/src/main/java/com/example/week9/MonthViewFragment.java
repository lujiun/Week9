package com.example.week9;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.week9.databinding.ActivityNewScheduleBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MonthViewFragment extends Fragment {
    public ActivityNewScheduleBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int mParam2;

    String data_d;
    int[] start_t;
    int[] end_t;

    public MonthViewFragment() {
        // Required empty public constructor
    }

    public static MonthCalendarFragment newInstance(int year, int month) {
        MonthCalendarFragment fragment = new MonthCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_month_view, container, false);

        GridView day_of_the_week = rootview.findViewById(R.id.day_of_the_week1);
        final String[] dayOfTheWeek = new String[]{"일", "월", "화", "수", "목", "금", "토"};
        ArrayAdapter<String> DOWadapter = new ArrayAdapter<>(getActivity(),
                R.layout.day_of_the_week,
                dayOfTheWeek);
        day_of_the_week.setAdapter(DOWadapter);

        ViewPager2 vpPager = rootview.findViewById(R.id.month_vp);
        FragmentStateAdapter adapter = new MonthCalendarAdapter(getActivity());
        vpPager.setAdapter(adapter);
        int year = ((MonthCalendarAdapter) adapter).year;
        int month = ((MonthCalendarAdapter) adapter).month;
        vpPager.setCurrentItem(50);
        ((MonthViewActivity)getActivity()).getSupportActionBar().setTitle(year + "년 " + (month+1) + "월");
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                    ((MonthViewActivity) getActivity()).getSupportActionBar().setTitle((year + (month + position + 10) / 12 - 5) + "년 " + ((month + position + 10) % 12 + 1) + "월");
                    ((MonthViewActivity) getActivity()).mainDate= "";
            }
        });


        FloatingActionButton fab = rootview.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_d = ((MonthViewActivity) getActivity()).mainDate;
                Intent intent = new Intent(getActivity(), NewSchedule.class);
                intent.putExtra("date", data_d);
                startActivity(intent);
            }
        });


        return rootview;
    }
}