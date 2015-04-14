package com.king.selfbill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zxing.activity.CaptureActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @ViewInject(R.id.listView_main)
    private ListView listView_main;
    @ViewInject(R.id.txt)
    private TextView txt;
    private MySQLiteOpenHelper dbHelper;
    private List<Map<String, Object>> list;
    private ListViewAdapter adapter;
    private String sql;
    private String[] arr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ViewUtils.inject(this);
        dbHelper = new MySQLiteOpenHelper(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new ListViewAdapter(this, list);
        listView_main.setAdapter(adapter);
        sql = "select * from tb_selfbill order by date(date) desc";
        arr = new String[]{};
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadListView(sql, arr);
    }

    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_account:
                Intent intent = new Intent(this, AddBillActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_details:
                Intent intent1 = new Intent(this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) list);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.btn_today:
                sql = "select * from tb_selfbill where date=?";
                arr = new String[]{getDateString()};
                reloadListView(sql, arr);
                break;
            case R.id.btn_week:
                //sql = "select * from tb_selfbill where ";
                //arr = new String[]{};
                // reloadListView(sql,arr);
                Intent intent2 = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent2, 200);
                break;
            case R.id.btn_month:
                sql = "select * from tb_selfbill where date like ? order by date(date) desc";
                arr = new String[]{getDateString().substring(0, 7) + "%"};
                reloadListView(sql, arr);
                break;
            case R.id.btn_year:
                sql = "select * from tb_selfbill where date like ? order by date(date) desc";
                arr = new String[]{getDateString().substring(0, 4) + "%"};
                reloadListView(sql, arr);
                break;
            case R.id.btn_all:
                sql = "select * from tb_selfbill order by date(date) desc";
                arr = new String[]{};
                reloadListView(sql, arr);
                break;
        }
    }

    private void reloadListView(String sql, String[] arr) {

        List<Map<String, Object>> subList = dbHelper.selectList(sql, arr);
        if (subList != null) {
            list.clear();
            list.addAll(subList);
            adapter.notifyDataSetChanged();
        }
    }

    private String getDateString() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            txt.setText(result);
        }
    }
}
