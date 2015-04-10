package com.king.selfbill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @ViewInject(R.id.listView_main)
    private ListView listView_main;
    private MySQLiteOpenHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dbHelper = new MySQLiteOpenHelper(this);

    }

    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_account:
                Intent intent = new Intent(this,AddBillActivity.class);
                startActivity(intent);
                break;
        }
    }
}
