package cn.efunding.fund;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import context.FundAppliaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
    }

    private FundAppliaction getApp(){
        return (FundAppliaction)getApplicationContext();
    }
}
