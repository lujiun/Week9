package com.example.week9;

import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.week9.databinding.ActivityNewScheduleBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.week9.DBHelper.TAG;

public class MonthViewFragment extends Fragment {
    public ActivityNewScheduleBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int mParam2;

    DBHelper dbHelper;
    Cursor cursor;

    String data_d;
    int data_year;
    int data_month;
    int data_day;
    int data_hour;
    int data_minute;

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
                data_year = ((MonthViewActivity) getActivity()).mainYear;
                data_month = ((MonthViewActivity) getActivity()).mainMonth;
                data_day = ((MonthViewActivity) getActivity()).mainDay;
                data_hour = ((MonthViewActivity) getActivity()).mainHour;
                data_minute = ((MonthViewActivity) getActivity()).mainMinute;

                Intent intent = new Intent(getActivity(), NewSchedule.class);
                intent.putExtra("date", data_d);
                intent.putExtra("year", data_year);
                intent.putExtra("month", data_month);
                intent.putExtra("day", data_day);
                intent.putExtra("hour", data_hour);
                intent.putExtra("minute", data_minute);

                String title1 = intent.getStringExtra("title1");
                String title2 = intent.getStringExtra("title2");
                String title3 = intent.getStringExtra("title3");

//                final String[] titles = new String[] {title1, title2, title3};

//                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
//                dlg.setTitle(data_d);
//                dlg.setItems(titles, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String selected = titles[which];
//                        Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dlg.show();

                startActivity(intent);
            }
        });


        return rootview;
    }
}