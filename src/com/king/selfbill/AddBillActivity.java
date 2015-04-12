package com.king.selfbill;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.king.bean.AccountSubType;
import com.king.bean.AccountType;
import com.king.bean.Payment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: king
 * Date: 2015/4/10
 */
public class AddBillActivity extends Activity {

    @ViewInject(R.id.spn_payments)
    private Spinner spn_pahyment;
    @ViewInject(R.id.spn_type)
    private Spinner spn_type;
    @ViewInject(R.id.spn_subtype)
    private Spinner spn_subtype;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.et_money)
    private EditText et_money;
    @ViewInject(R.id.layout_container)
    private LinearLayout layout_container;

    private Calendar calendar;
    private List<Payment> payments;

    private String[] paymentName = new String[]{"收入", "支出"};
    private String[][] types = new String[][]{{"工资", "外快"}, {"吃", "穿", "住", "行", "用"}};
    private String[][] subtypes = new String[][]{{"日常花销", "请客", "烟酒"},
            {"自用", "赠送"}, {"房租", "水电费"}, {"公共交通", "出租"}, {"学习用品", "生活用品"}};

    private ArrayAdapter<Payment> payment_adapter;
    private ArrayAdapter<AccountType> type_adapter;
    private ArrayAdapter<AccountSubType> subtype_adapter;

    private MySQLiteOpenHelper dbHelper;

    private int currentPayment = 0;
    private int currentType = 0;
    private int currentSuptype = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbill);
        ViewUtils.inject(this);

        initView();

        spn_pahyment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    layout_container.setVisibility(View.VISIBLE);
                    subtype_adapter = new ArrayAdapter<AccountSubType>(AddBillActivity.this,
                            android.R.layout.simple_spinner_item, android.R.id.text1,
                            payments.get(1).getTypes().get(0).getSubTypes());
                    subtype_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_subtype.setAdapter(subtype_adapter);
                } else if (position == 0) {
                    layout_container.setVisibility(View.GONE);
                }
                type_adapter = new ArrayAdapter<AccountType>(AddBillActivity.this,
                        android.R.layout.simple_spinner_item, android.R.id.text1,
                        payments.get(position).getTypes());
                type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_type.setAdapter(type_adapter);
                currentPayment = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subtype_adapter = new ArrayAdapter<AccountSubType>(AddBillActivity.this,
                        android.R.layout.simple_spinner_item, android.R.id.text1,
                        payments.get(1).getTypes().get(position).getSubTypes());
                subtype_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_subtype.setAdapter(subtype_adapter);
                currentType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_subtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSuptype = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 初始化view方法
     */
    private void initView() {
        calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        tv_time.setText(year + "-" + (month + 1) + "-" + day);
        payments = getPayment();

        layout_container.setVisibility(View.GONE);

        payment_adapter = new ArrayAdapter<Payment>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1, payments);
        payment_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_pahyment.setAdapter(payment_adapter);
        type_adapter = new ArrayAdapter<AccountType>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1,
                payments.get(0).getTypes());
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_type.setAdapter(type_adapter);
//        subtype_adapter = new ArrayAdapter<AccountSubType>(this,android.R.layout.simple_spinner_item,
//                android.R.id.text1,payments.get(1).getTypes().get(0).getSubTypes());
//        spn_subtype.setAdapter(subtype_adapter);
        dbHelper = new MySQLiteOpenHelper(this);
    }


    /**
     * 点击事件回调方法
     *
     * @param view
     */
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_timeselect:

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_time.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
                break;
            case R.id.btn_determine:
                String payment = "";
                String typename = "";
                String subtype = "";
                String cost_in = "";
                String date = "";
                Date nowdate = null;
                boolean flag = false;
                if (currentPayment == 0) {
                    payment = payments.get(currentPayment).getPayment();
                    typename = payments.get(currentPayment).getTypes().get(currentType).getTypeName();
                    cost_in = et_money.getText().toString();
                    date = tv_time.getText().toString();
                    Log.i("","--------->"+cost_in);
                    if (cost_in == "" || cost_in.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示：");
                        builder.setMessage("金额不能为空！！");
                        builder.setNegativeButton("确定", null);
                        builder.show();
                    } else {
                        try {
                            nowdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        date = new SimpleDateFormat("yyyy-MM-dd").format(nowdate);
                        flag = dbHelper.execData("insert into tb_selfbill (payment,typename,cost_in,date) values (?,?,?,?)",
                                new String[]{payment, typename, cost_in, date});
                        et_money.setText("");
                    }
                } else if (currentPayment == 1) {
                    payment = payments.get(currentPayment).getPayment();
                    typename = payments.get(currentPayment).getTypes().get(currentType).getTypeName();
                    subtype = payments.get(currentPayment).getTypes().get(currentType).getSubTypes().get(currentSuptype).getSubTypeName();
                    cost_in = "-" + et_money.getText().toString();
                    date = tv_time.getText().toString();
                    if (cost_in == "-" || cost_in.equals("-")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示：");
                        builder.setMessage("金额不能为空！！");
                        builder.setNegativeButton("确定", null);
                        builder.show();
                    } else {
                        try {
                            nowdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        date = new SimpleDateFormat("yyyy-MM-dd").format(nowdate);
                        flag = dbHelper.execData("insert into tb_selfbill (payment,typename,subtype,cost_in,date) values (?,?,?,?,?)",
                                new String[]{payment, typename, subtype, cost_in, date});
                        et_money.setText("");
                    }
                }

                //Toast.makeText(this,""+flag,Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 获取List<Payment>
     *
     * @return
     */
    private List<Payment> getPayment() {
        List<Payment> list = new ArrayList<Payment>();

        for (int i = 0; i < paymentName.length; i++) {
            Payment payment = new Payment();
            payment.setPayment(paymentName[i]);
            List<AccountType> acc = new ArrayList<AccountType>();
            for (int j = 0; j < types[i].length; j++) {
                AccountType accbean = new AccountType();
                accbean.setTypeName(types[i][j]);
                List<AccountSubType> subacc = new ArrayList<AccountSubType>();
                if (i == 1) {
                    for (int k = 0; k < subtypes[j].length; k++) {
                        AccountSubType subaccbean = new AccountSubType();
                        subaccbean.setSubTypeName(subtypes[j][k]);
                        subacc.add(subaccbean);
                        accbean.setSubTypes(subacc);
                    }
                }
                acc.add(accbean);
                payment.setTypes(acc);
            }
            list.add(payment);
        }

        return list;
    }
}