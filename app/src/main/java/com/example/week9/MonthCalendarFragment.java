package com.example.week9;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarFragment extends Fragment {

    int dayOfWeek;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int mParam2;

    public MonthCalendarFragment() {
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
        View rootview = inflater.inflate(R.layout.fragment_month_calendar, container, false);
        GridView gridView = rootview.findViewById(R.id.gridview);

        ArrayList<String> days = new ArrayList<String>();
        ArrayList<String> day28 = new ArrayList<String>();
        ArrayList<String> day29 = new ArrayList<String>();
        ArrayList<String> day30 = new ArrayList<String>();
        ArrayList<String> day31 = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();

        cal.set(mParam1, mParam2, 1);
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1; // 첫날 요일구하기, 0부터 시작하기 위해 1을 빼주었다.

        for (int i = 1; i <= 28; i++) day28.add("" + i);
        for (int i = 1; i <= 29; i++) day29.add("" + i);
        for (int i = 1; i <= 30; i++) day30.add("" + i);
        for (int i = 1; i <= 31; i++) day31.add("" + i); // 각각의 배열에 28~31일만큼 숫자 할당해줌

        for (int i = 0; i < dayOfWeek; i++) days.add(""); // 1일의 요일을 구해서 그 값만큼 격자 앞쪽에 빈칸 만들어줌

        if (mParam2 == 1 && mParam1 % 4 != 0) days.addAll(day28); // 2월 - 28일
        if (mParam2 == 1 && mParam1 % 4 == 0) days.addAll(day29); // 2월/윤달 - 29일
        if (mParam2 == 3 || mParam2 == 5 || mParam2 == 8 || mParam2 == 10) days.addAll(day30); // 4,6,9,11월 - 30일
        if (mParam2 == 0 || mParam2 == 2 || mParam2 == 4 || mParam2 == 6 || mParam2 == 7 || mParam2 == 9 || mParam2 == 11) days.addAll(day31); // 1,3,5,7,8,10,12월 - 31일

        for (int i = days.size(); i<42; i++) days.add("");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.day,
                days);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position>=dayOfWeek) { // 1의 요일보다 앞에 있을 때에는 클릭에 반응하지 않음
                    Toast.makeText(getActivity(),
                            mParam1 + "." + (mParam2 + 1) + "." + (position - dayOfWeek + 1),
                            Toast.LENGTH_SHORT).show();
//                    textView.setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        });
        return rootview;
    }
}