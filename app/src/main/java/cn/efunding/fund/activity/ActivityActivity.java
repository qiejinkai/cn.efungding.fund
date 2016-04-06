package cn.efunding.fund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.efunding.fund.R;

/**
 * Created by qiejinkai on 16/4/5.
 */
public class ActivityActivity extends AppCompatActivity implements View.OnClickListener {

    private int bar_title = R.string.bar_activity_title;
    private int [] footer_images = {R.drawable.home,R.drawable.activity_selected,R.drawable.me,R.drawable.help};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        initBar();
        initFooter();

        Button btn = (Button) findViewById(R.id.btnToBanner);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityActivity.this,Guide.class));
                finish();;
            }
        });
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
        switch (v.getId()){
            case R.id.btn_home:
                startActivity(new Intent(ActivityActivity.this, MainActivity.class));
                //finish();
                overridePendingTransition(R.animator.in_from_left,R.animator.out_to_left);
                break;
            case R.id.btn_activity:

                break;
            case R.id.btn_me:
                startActivity(new Intent(ActivityActivity.this, MeActivity.class));
                //finish();
                overridePendingTransition(R.animator.in_from_right,R.animator.out_to_left);
                break;
            case R.id.btn_help:
                startActivity(new Intent(ActivityActivity.this, HelpActivity.class));
                //finish();
                overridePendingTransition(R.animator.in_from_right,R.animator.out_to_left);
                break;
        }
    }
}
