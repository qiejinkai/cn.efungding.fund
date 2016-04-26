package cn.efunding.fund.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.efunding.fund.*;
import cn.efunding.fund.fragment.RegisterFirstFragment;

/**
 * Created by qiejinkai on 16/4/25.
 */
public class UserActivity extends AppCompatActivity{

    private String bar_title = "注册";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        getSupportFragmentManager().beginTransaction().add(R.id.register_main, new RegisterFirstFragment()).commit();


    }

    private void initPage(){

        EditText phone = (EditText) findViewById(R.id.phone);

        Drawable phone_dra = getResources().getDrawable(R.drawable.phone);

        phone_dra.setBounds(0, 0, 50, 80);//第一0是距左边距离，第二0是距上边距离，40分别是长宽

        phone.setCompoundDrawables(phone_dra, null, null, null);//只放左边
        EditText verifyCode = (EditText) findViewById(R.id.verifyCode);

        Drawable verifyCode_dra = getResources().getDrawable(R.drawable.phone);

        verifyCode_dra.setBounds(0, 0, 50, 80);
        //第一0是距左边距离，第二0是距上边距离，40分别是长宽

        verifyCode.setCompoundDrawables(verifyCode_dra, null, null, null);//只放左边

        Button changeImage = (Button) findViewById(R.id.btnChangeImage);
        changeImage.setTextColor(getResources().getColor(R.color.fund_white));

        Button next = (Button) findViewById(R.id.btnNext);
        next.setTextColor(getResources().getColor(R.color.fund_white));

        Button toLogin = (Button) findViewById(R.id.btnToLogin);
        toLogin.setTextColor(getResources().getColor(R.color.fund_red));


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

                startActivity(new Intent(UserActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left_500, R.animator.out_to_left);
            }
        });
        setSupportActionBar(toolbar);
    }
}
