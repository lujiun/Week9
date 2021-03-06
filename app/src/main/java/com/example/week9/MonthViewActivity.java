package com.example.week9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MonthViewActivity extends AppCompatActivity {
    //메인 액티비티를 경유하여 프래그먼트 데이터 전달
    String mainDate;
    int mainYear;
    int mainMonth;
    int mainDay;
    int mainStartTime;
    int mainHour;
    int mainMinute;
    int mainMeridiem;
    int mainEndTime;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.main_container, new MonthViewFragment()).commit();
    }

    //여기부터 앱바
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.month_view:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MonthViewFragment()).commit();
                return true;

            case R.id.week_view:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new WeekViewFragment()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}