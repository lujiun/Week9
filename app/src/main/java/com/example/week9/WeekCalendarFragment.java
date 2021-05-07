package com.example.week9;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.Arrays;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekCalendarFragment extends Fragment {
    int max_day, dayOfWeek, cur_week;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private int mParam1;
    private int mParam2;
    private int mParam3;

    public WeekCalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeekCalendarFragment.
     */
    public static WeekCalendarFragment newInstance(int param1, int param2, int param3) {
        WeekCalendarFragment fragment = new WeekCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week_calendar, container, false);
        GridView day = rootView.findViewById(R.id.week_day);
        ExpandableHeightGridView time = (ExpandableHeightGridView) rootView.findViewById(R.id.time);
        ExpandableHeightGridView timeB = (ExpandableHeightGridView) rootView.findViewById(R.id.time_blank);
        time.setExpanded(true);
        timeB.setExpanded(true);

        Calendar cal = Calendar.getInstance();

        cal.set(mParam1, mParam2, 1);
        max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //해당 월의 마지막 날 구하기
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1; // 첫날 요일구하기
        cur_week = mParam3;

        String[] day_s = new String[7];
        String[] time_s = new String[24];
        String[] timeB_s = new String[24*7];

        for(int i=cur_week*7+1-dayOfWeek, count=0;count<7;i++,count++)
        {//현재 주차*7 +1(그 다음 첫번째 날을 가기 위함)- 첫날 요일까지 빼기
            if(i<1||i>max_day) day_s[count] = "";
            else day_s[count] = i+"";
        }

        for(int i=0;i<24;i++) time_s[i] = i+"";
        Arrays.fill(timeB_s,"");


        ArrayAdapter<String> Dadapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.weekday,
                day_s);
        day.setAdapter(Dadapter);

        ArrayAdapter<String> Tadapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.time,
                time_s);
        time.setAdapter(Tadapter);

        ArrayAdapter<String> TBadapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.timeb,
                timeB_s);
        timeB.setAdapter(TBadapter);
        // Inflate the layout for this fragment
        return rootView;
    }
}