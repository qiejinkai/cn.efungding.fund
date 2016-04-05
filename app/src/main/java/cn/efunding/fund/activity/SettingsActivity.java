package cn.efunding.fund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.efunding.fund.R;

/**
 * Created by qiejinkai on 16/4/3.
 */
public class SettingsActivity extends AppCompatActivity {

    private int bar_title = R.string.bar_settings_title;;

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
                startActivity(new Intent(SettingsActivity.this, MeActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left_500, R.animator.out_to_left);
            }
        });
        setSupportActionBar(toolbar);

    }


}
