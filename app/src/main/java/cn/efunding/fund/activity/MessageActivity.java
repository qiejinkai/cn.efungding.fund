package cn.efunding.fund.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.efunding.fund.R;

/**
 * Created by qiejinkai on 16/4/3.
 */
public class MessageActivity extends AppCompatActivity {

    private String bar_title = "通知";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_page);
        initBar();
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
                startActivity(new Intent(MessageActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left, R.animator.out_to_left);
            }
        });
        setSupportActionBar(toolbar);

    }


}
