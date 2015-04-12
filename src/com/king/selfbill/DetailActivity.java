package com.king.selfbill;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.king.customview.SectorView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * User: king
 * Date: 2015/4/10
 */
public class DetailActivity extends Activity {

    private List<Map<String, Object>> list;
    private float[] in;
    private float[] out;
    @ViewInject(R.id.sectorView1)
    private SectorView sectorView1;
    @ViewInject(R.id.sectorView2)
    private SectorView sectorView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ViewUtils.inject(this);

        Bundle bundle = getIntent().getExtras();
        list = (List<Map<String, Object>>) bundle.getSerializable("list");

        initData(list);

        sectorView1.setData(in);
        sectorView2.setData(out);

    }

    private void initData(List<Map<String, Object>> list) {
        // String payment = "";
        String type = "";
        float cost_in = 0;
        float salary = 0;
        float extra = 0;
        float eat = 0;
        float close = 0;
        float live = 0;
        float travel = 0;
        float use = 0;
        for (int i = 0; i < list.size(); i++) {
            //payment = list.get(i).get("payment").toString();
            cost_in = getFloat(list.get(i).get("cost_in").toString());
            //Log.i("detailactivity","---------->"+cost_in);
            type = list.get(i).get("typename").toString();

            if (type.equals("工资")) {
                salary += cost_in;
            } else if (type.equals("外快")) {
                extra += cost_in;
            } else if (type.equals("吃")) {
                eat += cost_in;
            } else if (type.equals("穿")) {
                close += cost_in;
            } else if (type.equals("住")) {
                live += cost_in;
            } else if (type.equals("行")) {
                travel += cost_in;
            } else if (type.equals("用")) {
                use += cost_in;
            }
        }
        Log.i("detailactivity", "-------->" + eat + " " + close + " " + live + " " + travel + " " + use);
        in = new float[]{salary, extra};
        out = new float[]{eat, close, live, travel, use};
    }

    private float getFloat(String str) {
        return Math.abs(Float.parseFloat(str));
    }
}
