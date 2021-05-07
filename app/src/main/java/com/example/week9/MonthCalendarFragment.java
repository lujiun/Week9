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
    int max_day;
    int dayOfWeek;
    TextView tt;
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
        Calendar cal = Calendar.getInstance();

        cal.set(mParam1, mParam2, 1);
        max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //해당 월의 마지막 날 구하기
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1; // 첫날 요일구하기, 0부터 시작하기 위해 1을 빼주었다.

        String[] days = new String[42];

        for (int i = 0; i < days.length; i++) {
            if(i<dayOfWeek||i>max_day+dayOfWeek-1) days[i]= "";
            else days[i] = Integer.toString(i-dayOfWeek+1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.day,
                days);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(tt!=null) tt.setBackgroundColor(Color.WHITE); //이전 선택값 흰색으로 돌리기
                if(position>=dayOfWeek&&position<max_day+dayOfWeek) { // 1의 요일보다 앞에 있을 때에는 클릭에 반응하지 않음
                    Toast.makeText(getActivity(),
                            mParam1 + "." + (mParam2 + 1) + "." + (position - dayOfWeek + 1),
                            Toast.LENGTH_SHORT).show();
                    TextView textView = v.findViewById(R.id.day_text);
                    textView.setBackgroundColor(Color.CYAN); //선택된 텍스트 뷰 색깔 변경
                    tt = textView;
                }
            }
        });
        return rootview;
    }
}