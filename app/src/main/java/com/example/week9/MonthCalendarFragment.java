package com.example.week9;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthCalendarFragment extends Fragment {

    int year;
    int month;
    int dayOfWeek;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

    public MonthCalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters

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

//        Button prevBtn = rootview.findViewById(R.id.previous_button);
//        Button nextBtn = rootview.findViewById(R.id.next_button);

        String[] dayss = {"1","2","3"};
        ArrayList<String> days = new ArrayList<String>();
        ArrayList<String> day28 = new ArrayList<String>();
        ArrayList<String> day29 = new ArrayList<String>();
        ArrayList<String> day30 = new ArrayList<String>();
        ArrayList<String> day31 = new ArrayList<String>();
        TextView yearMonthTV = rootview.findViewById(R.id.year_month);
        Calendar cal = Calendar.getInstance();
        Intent intent = getActivity().getIntent();
        year = intent.getIntExtra("year", -1);
        month = intent.getIntExtra("month", -1);

        if (year == -1 || month == -1){
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
        }

        cal.set(year, month, 1);
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1; // 첫날 요일구하기, 0부터 시작하기 위해 1을 빼주었다.
        ((MonthViewActivity)getActivity()).getSupportActionBar().setTitle(year + "년 " + (month+1) + "월");

        for(int i = 1; i <= 28; i++) day28.add("" + i);
        for(int i = 1; i <= 29; i++) day29.add("" + i);
        for(int i = 1; i <= 30; i++) day30.add("" + i);
        for(int i = 1; i <= 31; i++) day31.add("" + i); // 각각의 배열에 28~31일만큼 숫자 할당해줌


        for (int i = 0; i < dayOfWeek; i++) days.add(""); // 1일의 요일을 구해서 그 값만큼 격자 앞쪽에 빈칸 만들어줌


        if (month == 1 && year % 4 != 0) days.addAll(day28); // 2월 - 28일
        if (month == 1 && year % 4 == 0) days.addAll(day29); // 2월/윤달 - 29일
        if (month == 3 || month == 5 || month == 8 || month == 10) days.addAll(day30); // 4,6,9,11월 - 30일
        if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) days.addAll(day31); // 1,3,5,7,8,10,12월 - 31일

        yearMonthTV.setText(year + "년 " + (month+1) + "월");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.day,
                days);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position>=dayOfWeek) { // 1의 요일보다 앞에 있을 때에는 클릭에 반응하지 않음
                    Toast.makeText(getActivity(),
                            year + "." + (month + 1) + "." + (position - dayOfWeek + 1),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

//        prevBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent bt_Intent = new Intent(getActivity().getApplicationContext(), MonthViewActivity.class);
//                if(month<1) {
//                    bt_Intent.putExtra("year", year-1);
//                    bt_Intent.putExtra("month", 11);
//                }
//                else {
//                    bt_Intent.putExtra("year", year);
//                    bt_Intent.putExtra("month", (month-1));
//                }
//                startActivity(bt_Intent);
//                getActivity().finish();
//            }
//        });
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent bt_Intent = new Intent(getActivity().getApplicationContext(), MonthViewActivity.class);
//                if(month>10) {
//                    bt_Intent.putExtra("year", year+1);
//                    bt_Intent.putExtra("month", 0);
//                }
//                else {
//                    bt_Intent.putExtra("year", year);
//                    bt_Intent.putExtra("month", (month+1));
//                }
//                startActivity(bt_Intent);
//                getActivity().finish();
//            }
//        });

        return rootview;
    }


}