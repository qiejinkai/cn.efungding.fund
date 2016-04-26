package cn.efunding.fund.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import cn.efunding.fund.R;
import cn.efunding.fund.activity.MainActivity;

/**
 * Created by qiejinkai on 16/4/26.
 */
public class RegisterSecondFragment extends Fragment {

    private String bar_title = "注册新用户";

    public RegisterSecondFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register_second_fragment,container,false);
        initBar(rootView);
        initPage(rootView);
        return rootView;
    }

    private void initPage(View rootView){

        Button modify = (Button) rootView.findViewById(R.id.modify);
        modify.setTextColor(getResources().getColor(R.color.fund_white));

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        TextView phoneNum = (TextView) rootView.findViewById(R.id.phoneNum);
        phoneNum.setTextColor(getResources().getColor(R.color.fund_white));

        Button register = (Button) rootView.findViewById(R.id.register);
        register.setTextColor(getResources().getColor(R.color.fund_white));
        register.setText("注 册");



    }
    private void initBar(final View rootView){
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView tv = (TextView) rootView.findViewById(R.id.bar_title);
        tv.setText(bar_title);
        tv.setTextColor(getResources().getColor(R.color.fund_white));
        Button btn = (Button) rootView.findViewById(R.id.btn_back_main);
        btn.setTextColor(getResources().getColor(R.color.fund_white));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }
}
