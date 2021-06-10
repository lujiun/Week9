package com.example.week9;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

/**
 * A simple {@link WeekViewFragment} subclass.
 * Use the {@link WeekViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekViewFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    String data_d;
    int data_st;

    public WeekViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekViewFragment newInstance(String param1, String param2) {
        WeekViewFragment fragment = new WeekViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_week_view, container, false);

        GridView day_of_the_week = rootView.findViewById(R.id.day_of_the_week2);
        final String[] dayOfTheWeek = new String[]{"일", "월", "화", "수", "목", "금", "토"};
        ArrayAdapter<String> DOWadapter = new ArrayAdapter<>(getActivity(),
                R.layout.day_of_the_week,
                dayOfTheWeek);
        day_of_the_week.setAdapter(DOWadapter);

        ViewPager2 vpPager = rootView.findViewById(R.id.week_vp);
        FragmentStateAdapter adapter = new WeekCalendarAdapter(getActivity());
        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(50);

        int year = ((WeekCalendarAdapter) adapter).year;
        int month = ((WeekCalendarAdapter) adapter).month;
        int date = ((WeekCalendarAdapter) adapter).date;
        int dayOfWeek = ((WeekCalendarAdapter) adapter).dayOfWeek;

        ((MonthViewActivity)getActivity()).getSupportActionBar().setTitle(year + "년 " + (month+1) + "월 " );

        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                int y,m;
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,date+((position-50)*7)-dayOfWeek);
                y = cal.get(Calendar.YEAR);
                m = cal.get(Calendar.MONTH);
                ((MonthViewActivity) getActivity()).getSupportActionBar().setTitle(y + "년 " + (m+1) + "월 " );
                ((MonthViewActivity) getActivity()).mainDate="";
            }
        });
        //플로팅 액션 바
        FloatingActionButton fab = rootView.findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //값 전달
                data_d = ((MonthViewActivity) getActivity()).mainDate;
                data_st = ((MonthViewActivity) getActivity()).mainStartTime;
                Intent intent = new Intent(getActivity(), NewSchedule.class);
                intent.putExtra("date", data_d);
                intent.putExtra("startTime",data_st);
                startActivity(intent);
            }
        });

        return rootView;
    }
}