package cn.efunding.fund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import cn.efunding.fund.R;

/**
 * Created by qiejinkai on 16/4/13.
 */
public class SubjectActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String ACTION = "cn.efunding.fund.activity.intent.action.SubjectActivity";

    private String bar_title = "";

    private EditText inputEt;
    private TextView incomeTv;
    private TextView valueTv;
    private long yeild = 15;
    private int closeDays = 1;

    private String content ="<p style=\"text-align:center\">" +
            "<img src=\"http://fund.xiaogutou.cn/image/20160224/1456289551855684.png\" title=\"1456289551855684.png\" " +
            "alt=\"新手标详情页_1136x640-1---副本.png\" width=\"395\" " +
            "height=\"671\" style=\"width: 395px; height: 671px;\"></p>";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_page);
        Intent i = getIntent();
        bar_title = i.getStringExtra("title");

        initBar();
        initPage();
        initButton();

        WebView wv = (WebView) findViewById(R.id.wv);

        wv.loadData(content,"text/html","utf-8");
    }

    private void initPage(){
        TextView toInvestors = (TextView) findViewById(R.id.toInvestors);
        toInvestors.setTextColor(getResources().getColor(R.color.fund_deep_gray));

        TextView tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setTextColor(getResources().getColor(R.color.fund_white));

        TextView subject_yeild = (TextView) findViewById(R.id.subject_yeild);
        subject_yeild.setTextColor(getResources().getColor(R.color.fund_white));

        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setTextColor(getResources().getColor(R.color.fund_white));

        TextView lockdays = (TextView) findViewById(R.id.lockdays);
        lockdays.setTextColor(getResources().getColor(R.color.fund_white));

        TextView tv4 = (TextView) findViewById(R.id.textView4);
        tv4.setTextColor(getResources().getColor(R.color.fund_deep_gray));

        TextView subject_permission_value = (TextView) findViewById(R.id.subject_permission_value);
        subject_permission_value.setTextColor(getResources().getColor(R.color.fund_red));

        inputEt = (EditText) findViewById(R.id.inputValue);

        inputEt.addTextChangedListener(new TextWatcher() {

            private String beforeText;


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //String text = i;
                if(beforeText!=null && beforeText.equals(inputEt.getText().toString()) ){
                    return;
                }
                long value = getEtValue(inputEt.getText().toString());

                if(value > 100){
                    value = value%100==0?value:value-(value%100);
                    setValueText(value);
                }
            }
        });

        incomeTv = (TextView) findViewById(R.id.income);
        valueTv = (TextView) findViewById(R.id.value);
        incomeTv.setTextColor(getResources().getColor(R.color.fund_red));
    }

    private void initBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView tv = (TextView) findViewById(R.id.bar_title);
        tv.setText(bar_title);
        tv.setTextColor(getResources().getColor(R.color.fund_white));

        Button btn = (Button) findViewById(R.id.btn_back_main);
        btn.setTextColor(getResources().getColor(R.color.fund_white));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectActivity.this, MainActivity.class));
                finish();
            }
        });
        setSupportActionBar(toolbar);

    }

    private void initButton(){
        Button subtract = (Button) findViewById(R.id.subtract);
        subtract.setOnClickListener(this);
        Button plus = (Button) findViewById(R.id.plus);
        plus.setOnClickListener(this);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setTextColor(getResources().getColor(R.color.fund_white));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.subtract:
                subtract();
                break;
            case R.id.plus:
                plus();
                break;
        }
    }

    private void subtract(){
        String valueStr = inputEt.getText().toString();

        long value = getEtValue(valueStr);

        if(value >= 100){

            value -=100;
            inputEt.setText(value + "");
        }



    }
    private void plus(){

        String valueStr = inputEt.getText().toString();

        long value = getEtValue(valueStr);

        value += 100;

        inputEt.setText(value+"");
    }

    private long getEtValue(String valueStr) {

        long value = 0l;

        try {

            value = Integer.parseInt(valueStr);

        }catch (NumberFormatException e){
            value = 0;
        }
        return value;
    }

    private void setValueText(long value){

        inputEt.setText(value+"");

        valueTv.setText(value + "");

        //String income = ;

        incomeTv.setText(mathIncome(value ,yeild,yeild,closeDays));
    }

    private String mathIncome(long value,long yeildBottom,long yeildTop,int closeDays){

        Calendar  time = Calendar.getInstance(Locale.CHINA);
        time.setTime(new Date());
        int days = new GregorianCalendar().isLeapYear(time.get(Calendar.YEAR))?366:365;

        if(yeildBottom == yeildTop){

            double income = (value*closeDays*yeildBottom)/(days*100);
            return income+"";
        }else{
            double incomeBottom = (value*closeDays*yeildBottom)/(days*100);
            double incomeTop = (value*closeDays*yeildTop)/(days*100);
            return incomeBottom+"~"+incomeTop;
        }

        //return "";
    }


}
