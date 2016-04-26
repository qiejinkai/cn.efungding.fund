package cn.efunding.fund.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.efunding.fund.R;
import cn.efunding.fund.activity.MainActivity;

/**
 * Created by qiejinkai on 16/4/26.
 */
public class RegisterFirstFragment extends Fragment {

    private String bar_title = "注册新用户";

    public RegisterFirstFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register_first_fragment,container,false);
        initBar(rootView);
        initPage(rootView);
        return rootView;
    }

    private void initPage(View rootView){


        Button changeImage = (Button) rootView.findViewById(R.id.btnChangeImage);
        changeImage.setTextColor(getResources().getColor(R.color.fund_white));

        Button next = (Button) rootView.findViewById(R.id.btnNext);
        next.setTextColor(getResources().getColor(R.color.fund_white));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.register_main,new RegisterSecondFragment()).addToBackStack(null).commit();
            }
        });

        Button toLogin = (Button) rootView.findViewById(R.id.btnToLogin);
        toLogin.setTextColor(getResources().getColor(R.color.fund_red));
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.register_main,new LoginFragment()).addToBackStack(null).commit();
            }
        });


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

                getActivity().startActivity(new Intent(MainActivity.ACTION));
                getActivity().finish();
                getActivity().overridePendingTransition(R.animator.in_from_left_500, R.animator.out_to_left);
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }
}
