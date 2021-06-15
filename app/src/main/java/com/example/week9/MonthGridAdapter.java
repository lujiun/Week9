package com.example.week9;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MonthGridAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private String[] mItems;
    private DBHelper DB;
    private Cursor c;
    private int year, month;
    public MonthGridAdapter(Context context, int resource,int y, int m, String[] items) {
        mContext = context;
        mItems = items;
        mResource = resource;
        DB = new DBHelper(context);
        year = y;
        month = m;
    }

    // MyAdapter 클래스가 관리하는 항목의 총 개수를 반환
    @Override
    public int getCount() {
        return mItems.length;
    }

    // MyAdapter 클래스가 관리하는 항목의 중에서 position 위치의 항목을 반환
    @Override
    public Object getItem(int position) {
        return mItems[position];
    }

    // 항목 id를 항목의 위치로 간주함
    @Override
    public long getItemId(int position) {
        return position;
    }

    // position 위치의 항목에 해당되는 항목뷰를 반환하는 것이 목적임
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) { // 해당 항목 뷰가 이전에 생성된 적이 없는 경우
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 항목 뷰를 정의한 xml 리소스(여기서는 mResource 값)으로부터 항목 뷰 객체를 메모리로 로드
            convertView = inflater.inflate(mResource, parent,false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.day_text);
        name.setText(mItems[position]);
        ListView lv = convertView.findViewById(R.id.day_list);
        c = DB.selectMemosBySQL(year,month, mItems[position]);
        ArrayList<String> schedule = new ArrayList<String>();
        AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
        dlg.setTitle(String.format("%d.%d.%s일",year,month,mItems[position]));

        if(c!=null){
            while(c.moveToNext())
            {
                schedule.add(c.getString(0));
            }
        }
        String[] sche2 = schedule.toArray(new String[schedule.size()]);
        dlg.setItems(sche2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ArrayAdapter adapter = new ArrayAdapter(mContext,R.layout.schedule,schedule);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dlg.show();
            }
        });




        return convertView;
    }
}
