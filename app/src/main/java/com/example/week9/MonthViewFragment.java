package com.example.week9;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MonthViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int mParam2;

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

        ViewPager2 vpPager = rootview.findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new MonthCalendarAdapter(getActivity());
        vpPager.setAdapter(adapter);
        int a = new MonthCalendarAdapter(getActivity()).year;
        int b = new MonthCalendarAdapter(getActivity()).month;
        vpPager.setCurrentItem(50);
        ((MonthViewActivity)getActivity()).getSupportActionBar().setTitle(a + "년 " + (b+1) + "월");

        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                    ((MonthViewActivity) getActivity()).getSupportActionBar().setTitle((a + (b + position + 10) / 12 - 5) + "년 " + ((b + position + 10) % 12 + 1) + "월");

            }
        });



        return rootview;
    }
}