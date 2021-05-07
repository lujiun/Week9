package com.example.week9;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * A simple {@link WeekViewFragment} subclass.
 * Use the {@link WeekViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

        /*TODO: 왜 이렇게 쓰는 지 확인*/
        int year = new WeekCalendarAdapter(getActivity()).year;
        int month = new WeekCalendarAdapter(getActivity()).month;
        int week = new WeekCalendarAdapter(getActivity()).cur_week;

        ((MonthViewActivity)getActivity()).getSupportActionBar().setTitle(year + "년 " + (month+1) + "월 " + (week+1) + "주차");

        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                ((MonthViewActivity) getActivity()).getSupportActionBar().setTitle( (year+((month + 12+ (week+position+4)/6-9)/12)-1) +"년 " + ((month + 12+ (week+position+4)/6 -9 ) %12+1) +"월 " + ((week + position+4)%6+1) +"주차");

            }
        });
        return rootView;
    }
}