package com.example.week9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week9.databinding.ActivityNewScheduleBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewSchedule extends AppCompatActivity implements OnMapReadyCallback {
    public ActivityNewScheduleBinding binding;
    private FusedLocationProviderClient mFusedLocationClient;
    GoogleMap mGoogleMap = null;
    private Geocoder geocoder;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewScheduleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //전달받은 값 가져오기
        Intent intent = getIntent();
        EditText ed1 = findViewById(R.id.ns_ed1);
        ed1.setText(intent.getStringExtra("date"));
        EditText ed2 = findViewById(R.id.ns_ed2);
        EditText ed3 = findViewById(R.id.ns_ed3);


        //NumberPicker 설정
        NumberPicker start1 = findViewById(R.id.ns_np1);
        NumberPicker start2 = findViewById(R.id.ns_np2);
        NumberPicker start3 = findViewById(R.id.ns_np3);
        NumberPicker end1 = findViewById(R.id.ns_np4);
        NumberPicker end2 = findViewById(R.id.ns_np5);
        NumberPicker end3 = findViewById(R.id.ns_np6);
        String str[] = {"AM","PM"};
        start1.setMinValue(1); start1.setMaxValue(12);
        start2.setMaxValue(59); start3.setMaxValue(1);start3.setDisplayedValues(str);
        end1.setMinValue(1); end1.setMaxValue(12);
        end2.setMaxValue(59);end3.setMaxValue(1);end3.setDisplayedValues(str);
        start2.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        end2.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        int data_st = intent.getIntExtra("startTime",8);
        start1.setValue(data_st%12); end1.setValue((data_st+1)%12);
        start3.setValue(data_st/12); end3.setValue((data_st+1)%24/12);
        
        //SQLite 이용
        dbHelper = new DBHelper(this);
        Button save_btn = findViewById(R.id.ns_bt2);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewSchedule.this,"저장한다", Toast.LENGTH_SHORT).show();
                dbHelper.insertMemoBySQL(
                        ed1.getText().toString(),
                        start1.getValue(), start2.getValue(), start3.getValue(),
                        end1.getValue(), end2.getValue(), end3.getValue(),
                        ed2.getText().toString(),
                        ed3.getText().toString()
                        );
                viewAllToTextView();
            }
        });
        Button cancel_btn = findViewById(R.id.ns_bt3);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewSchedule.this,"나 나간다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        //실험용 editText라 구현할때 없애면 됨
        EditText _id = findViewById(R.id.sql_id);
        //삭제시 다이얼 로그 구현
        AlertDialog.Builder dlg = new AlertDialog.Builder(NewSchedule.this);
        dlg.setMessage("정말 삭제하시겠습니까?");
        dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteMemoBySQL(_id.getText().toString());
                viewAllToTextView();
            }
        });
        dlg.setPositiveButton("취소",null);

        Button delete_btn = findViewById(R.id.ns_bt4);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.show();
            }
        });

        //지도 이용
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button map_btn = findViewById(R.id.ns_bt1);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getLocation();
            }
        });
    }
    //SQLite 관련 함수
    //저장 삭제 확인용으로 지워도 무방
    private void viewAllToTextView() {
        TextView result = (TextView)findViewById(R.id.result);

        Cursor cursor = dbHelper.getAllMemosBySQL();

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getInt(0)+" \t");
            buffer.append(cursor.getString(1)+" \t");
            buffer.append(cursor.getString(2)+" \t");
            buffer.append(cursor.getString(3)+" \t");
            buffer.append(cursor.getString(4)+" \t");
            buffer.append(cursor.getString(5)+" \t");
            buffer.append(cursor.getString(6)+" \t");
            buffer.append(cursor.getString(7)+" \t");
            buffer.append(cursor.getString(8)+" \t");
            buffer.append(cursor.getString(9)+"\n");
        }
        result.setText(buffer);
    }

    //지도 관련 함수
    private void getLocation() {
        EditText map_text = findViewById(R.id.ns_ed2);
        String search_address = map_text.getText().toString();
        try {
            List<Address> addresses = geocoder.getFromLocationName(search_address,5);
            if (addresses.size() >0) {
                Address bestResult = (Address) addresses.get(0);
                LatLng location = new LatLng(bestResult.getLatitude(),bestResult.getLongitude());
                mGoogleMap.addMarker(new MarkerOptions().
                        position(location).
                        title("검색 위치").
                        alpha(0.8f));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
            }
        } catch (IOException e) {
            Log.e(getClass().toString(),"Failed in using Geocoder.", e);
            return;
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        geocoder = new Geocoder(this, Locale.KOREA);
        //여기에 선택된 인자 받아서 처음 들어왔을 때 저장했던 위치 뜨게 할 거임.

        // move the camera
        mGoogleMap.setOnMarkerClickListener(new MyMarkerClickListener());
    }
    class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {
        @Override
        public boolean onMarkerClick(Marker marker) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),15));
            return false;
        }
    }
}