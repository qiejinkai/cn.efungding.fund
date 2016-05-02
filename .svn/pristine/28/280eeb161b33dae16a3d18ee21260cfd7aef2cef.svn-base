package cn.efunding.fund.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import cn.efunding.fund.R;

/**
 * Created by qiejinkai on 16/4/3.
 */
public class WelcomeActivity extends Activity {

    private long wait_time = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, cn.efunding.fund.activity.MainActivity.class));
                finish();
            }
        },wait_time);
    }
}
