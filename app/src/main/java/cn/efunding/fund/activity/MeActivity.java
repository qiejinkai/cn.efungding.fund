package cn.efunding.fund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.efunding.fund.R;
import cn.efunding.fund.appliacation.MyApplication;

/**
 * Created by qiejinkai on 16/4/5.
 */
public class MeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION = "cn.efunding.fund.activity.intent.action.MeActivity";

    private int bar_title = R.string.bar_me_title;
    private int [] footer_images = {R.drawable.home,R.drawable.activity,R.drawable.me_selected,R.drawable.help};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication context = (MyApplication)getApplicationContext();

        if(!context.isLogin()){
            startActivity(new Intent(MeActivity.this,UserActivity.class));
            return;
        }
        setContentView(R.layout.me_page);
        initBar();
        initFooter();
        initPage();
    }

    private void initPage(){
        TextView tv9 = (TextView) findViewById(R.id.textView9);
        tv9.setTextColor(getResources().getColor(R.color.fund_white));
        TextView total = (TextView) findViewById(R.id.total);
        total.setTextColor(getResources().getColor(R.color.fund_white));
        TextView tv1 = (TextView) findViewById(R.id.textView1);
        tv1.setTextColor(getResources().getColor(R.color.fund_white));
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setTextColor(getResources().getColor(R.color.fund_white));
        TextView investValue = (TextView) findViewById(R.id.investValue);
        investValue.setTextColor(getResources().getColor(R.color.fund_white));
        TextView balance = (TextView) findViewById(R.id.balance);
        balance.setTextColor(getResources().getColor(R.color.fund_white));


        Button withdraw = (Button) findViewById(R.id.withdraw);
        withdraw.setTextColor(getResources().getColor(R.color.fund_white));
        Button recharge = (Button) findViewById(R.id.recharge);
        recharge.setTextColor(getResources().getColor(R.color.fund_white));


        LinearLayout subject_y_linearLayout = (LinearLayout) findViewById(R.id.subject_y_linearLayout);
        subject_y_linearLayout.setOnClickListener(this);
        LinearLayout subject_x_linearLayout =  (LinearLayout) findViewById(R.id.subject_x_linearLayout);
        subject_x_linearLayout.setOnClickListener(this);
    }

    private void initBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView tv = (TextView) findViewById(R.id.bar_title);
        tv.setText(bar_title);
        tv.setTextColor(getResources().getColor(R.color.fund_white));
        Button btn = (Button) findViewById(R.id.btn_back_main);
        toolbar.removeView(btn);
        setSupportActionBar(toolbar);
    }

    private void initFooter(){
        ImageButton home = (ImageButton) findViewById(R.id.btn_home);
        home.setImageResource(footer_images[0]);
        home.setOnClickListener(this);

        ImageButton activity = (ImageButton) findViewById(R.id.btn_activity);
        activity.setImageResource(footer_images[1]);
        activity.setOnClickListener(this);

        ImageButton me = (ImageButton) findViewById(R.id.btn_me);
        me.setImageResource(footer_images[2]);
        me.setOnClickListener(this);

        ImageButton help = (ImageButton) findViewById(R.id.btn_help);
        help.setImageResource(footer_images[3]);
        help.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.me, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(MeActivity.this, SettingsActivity.class));
                //finish();
                overridePendingTransition(R.animator.in_from_left_500, R.animator.out_to_left);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                startActivity(new Intent(MeActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left, R.animator.out_to_left);
                break;
            case R.id.btn_activity:
                startActivity(new Intent(MeActivity.this, ActivityActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left, R.animator.out_to_left);
                break;

            case R.id.btn_me:
                break;
            case R.id.btn_help:
                startActivity(new Intent(MeActivity.this, HelpActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_right, R.animator.out_to_left);
                break;
            case R.id.subject_y_linearLayout:
                startActivity(new Intent(MeActivity.this,MyYSubjectActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_right_500, R.animator.out_to_left);

                break;
            case R.id.subject_x_linearLayout:
                startActivity(new Intent(MeActivity.this,MyXSubjectActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_right_500, R.animator.out_to_left);
                break;
        }
    }
}
