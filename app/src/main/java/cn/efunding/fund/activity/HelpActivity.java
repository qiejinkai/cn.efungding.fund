package cn.efunding.fund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.efunding.fund.R;

/**
 * Created by qiejinkai on 16/4/5.
 */
public class HelpActivity extends AppCompatActivity implements View.OnClickListener{

    private int bar_title = R.string.bar_help_title;
    private int [] footer_images = {R.drawable.home,R.drawable.activity,R.drawable.me,R.drawable.help_selected};
    public static final String ACTION = "cn.efunding.fund.activity.intent.action.HelpActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);
        initBar();
        initFooter();
        initPage();
    }
    private void initPage(){
        LinearLayout question = (LinearLayout) findViewById(R.id.question);
        question.setOnClickListener(this);
        LinearLayout feedback =  (LinearLayout) findViewById(R.id.feedback);
        feedback.setOnClickListener(this);
        LinearLayout webcat = (LinearLayout) findViewById(R.id.webcat);
        webcat.setOnClickListener(this);
        LinearLayout about = (LinearLayout) findViewById(R.id.about);
        about.setOnClickListener(this);
        LinearLayout controller = (LinearLayout) findViewById(R.id.controller);
        controller.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                startActivity(new Intent(HelpActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left, R.animator.out_to_left);
                break;
            case R.id.btn_activity:
                startActivity(new Intent(HelpActivity.this, ActivityActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left, R.animator.out_to_left);
                break;
            case R.id.btn_me:
                startActivity(new Intent(HelpActivity.this, MeActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left, R.animator.out_to_left);
                break;
            case R.id.btn_help:
                break;
            case R.id.question:
                startActivity(new WebActivityIntent(HelpActivity.this,WebActivity.class,HelpActivity.ACTION,R.string.domain,R.string.base_url_question,"常见问题"));
                finish();
                overridePendingTransition(R.animator.in_from_right_500, R.animator.out_to_left);
                break;
            case R.id.feedback:
                startActivity(new WebActivityIntent(HelpActivity.this,WebActivity.class,HelpActivity.ACTION,R.string.domain,R.string.base_url_webcat,""));
                finish();
                overridePendingTransition(R.animator.in_from_right_500, R.animator.out_to_left);
                break;
            case R.id.webcat:
                startActivity(new WebActivityIntent(HelpActivity.this,WebActivity.class,HelpActivity.ACTION,R.string.domain,R.string.base_url_webcat,"微信客服"));
                finish();
                overridePendingTransition(R.animator.in_from_right_500, R.animator.out_to_left);
                break;
            case R.id.about:
                startActivity(new WebActivityIntent(HelpActivity.this,WebActivity.class,HelpActivity.ACTION,R.string.domain,R.string.base_url_about,"关于小骨头"));
                finish();
                overridePendingTransition(R.animator.in_from_right_500, R.animator.out_to_left);
                break;
            case R.id.controller:
                startActivity(new WebActivityIntent(HelpActivity.this,WebActivity.class,HelpActivity.ACTION,R.string.domain,R.string.base_url_controller,"风险控制"));
                finish();
                overridePendingTransition(R.animator.in_from_right_500, R.animator.out_to_left);
                break;
        }
    }
}
