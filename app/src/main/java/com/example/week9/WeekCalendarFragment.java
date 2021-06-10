package com.example.week9;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekCalendarFragment extends Fragment {
    int max_day,y,sub_y,m,sub_m,d,sub_d;
    TextView sel_timeB;
    TextView sel_day;

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

        cal.set(mParam1, mParam2, mParam3); //주 의 첫날 받았음
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        sub_m = m; sub_y =y;
        d = cal.get(Calendar.DATE);
        max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //해당 월의 마지막 날 구하기


        String[] day_s = new String[7];
        String[] time_s = new String[24];
        String[] timeB_s = new String[24*7];

        for(int i=d, count=0;count<7;i++,count++)
        {//현재 주차*7 +1(그 다음 첫번째 날을 가기 위함)- 첫날 요일까지 빼기
            if(i>max_day) day_s[count] = (i%max_day)+"";
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
        day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sel_day!=null) sel_day.setBackgroundColor(Color.WHITE);
                if(sel_timeB!=null)sel_timeB.setBackgroundColor(Color.WHITE);
                sel_day = view.findViewById(R.id.day_text);
                sel_day.setBackgroundColor(Color.CYAN);
                // 선택된 날짜의 값에 따라 월을 구분하기 위함
                sub_d = Integer.parseInt(sel_day.getText().toString());
                if(sub_d<d) {sub_m=m+1; if(sub_m>11) {sub_m = 0; sub_y=y+1;}}
                else {sub_y=y;sub_m=m;}
                ((MonthViewActivity)getActivity()).mainDate = String.format("%d년 %d월 %d일",sub_y,sub_m+1,sub_d);
                Toast.makeText(getActivity(), sub_d+"일", Toast.LENGTH_SHORT).show();
            }
        });
        timeB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sel_day!=null) sel_day.setBackgroundColor(Color.WHITE);
                if(sel_timeB!=null)sel_timeB.setBackgroundColor(Color.WHITE);
                sel_timeB = view.findViewById(R.id.timeb_text);
                sel_timeB.setBackgroundColor(Color.CYAN);
                sel_day = day.getChildAt(position%7).findViewById(R.id.day_text);
                sel_day.setBackgroundColor(Color.CYAN);
                // 선택된 날짜의 값에 따라 월을 구분하기 위함
                sub_d = Integer.parseInt(sel_day.getText().toString());
                if(sub_d<d) {sub_m=m+1; if(sub_m>11) {sub_m = 0; sub_y=y+1;}}
                else {sub_y=y;sub_m=m;}
                ((MonthViewActivity)getActivity()).mainDate = String.format("%d년 %d월 %d일 %d시",sub_y,sub_m+1,sub_d,position/7);
                ((MonthViewActivity)getActivity()).mainStartTime = position/7;
                Toast.makeText(getActivity(),sel_day.getText()+"일 "+position/7+"시", Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
}