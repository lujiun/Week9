package com.example.week9;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static androidx.fragment.app.FragmentKt.setFragmentResult;

public class MonthCalendarFragment extends Fragment {

//    Intent intent = new Intent(getActivity(), NewSchedule.class);

    int max_day;
    int dayOfWeek;
    LinearLayout sel_day;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int mParam2;

    DBHelper dbHelper;

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

        Intent intent = new Intent(getActivity(), NewSchedule.class);

//        Cursor cursor = dbHelper.getAllMemosBySQL();
        int cyear = intent.getIntExtra("year", 0);
        int cmonth = intent.getIntExtra("month", 0);
        int cday = intent.getIntExtra("day", 0);

        GridView gridView = rootview.findViewById(R.id.gridview);
        Calendar cal = Calendar.getInstance();
        cal.set(mParam1, mParam2, 1);
        max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //?????? ?????? ????????? ??? ?????????
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1; // ?????? ???????????????, 0?????? ???????????? ?????? 1??? ????????????.

        String[] days = new String[42];

        for (int i = 0; i < days.length; i++) {
            if(i<dayOfWeek||i>max_day+dayOfWeek-1) days[i]= "";
//            else if (cyear == 0) ;
            else days[i] = Integer.toString(i-dayOfWeek+1);
        }

        MonthGridAdapter adapter = new MonthGridAdapter(getActivity(),
                R.layout.day,
                mParam1,mParam2+1,
                days);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if(position>=dayOfWeek&&position<max_day+dayOfWeek) { // 1??? ???????????? ?????? ?????? ????????? ????????? ???????????? ??????
                    Toast.makeText(getActivity(),
                            mParam1 + "." + (mParam2 + 1) + "." + (position - dayOfWeek + 1),
                            Toast.LENGTH_SHORT).show();
                    if(sel_day!=null) sel_day.setBackgroundColor(Color.WHITE); //?????? ????????? ???????????? ?????????
                    sel_day = v.findViewById(R.id.day_Layout);
                    sel_day.setBackgroundColor(Color.CYAN); //????????? ????????? ??? ?????? ??????

                    ((MonthViewActivity)getActivity()).mainDate = String.format("%d??? %d??? %d???",mParam1,(mParam2 + 1),(position - dayOfWeek + 1));
                    ((MonthViewActivity)getActivity()).mainYear = mParam1;
                    ((MonthViewActivity)getActivity()).mainMonth = mParam2+1;
                    ((MonthViewActivity)getActivity()).mainDay = position - dayOfWeek + 1;
                }
            }
        });
        return rootview;
    }
}