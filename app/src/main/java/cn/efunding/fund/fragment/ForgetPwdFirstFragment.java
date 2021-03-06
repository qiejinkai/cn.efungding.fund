package cn.efunding.fund.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.efunding.fund.R;
import cn.efunding.fund.appliacation.MyApplication;

/**
 * Created by qiejinkai on 16/4/26.
 */
public class ForgetPwdFirstFragment extends Fragment {

    private String bar_title = "找回密码";

    public ForgetPwdFirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register_first_fragment,container,false);
        initBar(rootView);
        initPage(rootView);
        return rootView;
    }

    private void initPage(View rootView){


        final ImageView iv = (ImageView) rootView.findViewById(R.id.verifyCodeImage);
        iv.setBackgroundColor(getResources().getColor(R.color.fund_white));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        //iv.setAdjustViewBounds(true);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double r = Math.random();
                ImageLoader.getInstance().displayImage(getString(R.string.domain)+getString(R.string.base_url_verifycode) + r, iv, ((MyApplication) getActivity().getApplicationContext()).getDisplayImageOptions(false));

            }
        });
        double r = Math.random();
        ImageLoader.getInstance().displayImage(getString(R.string.domain)+getString(R.string.base_url_verifycode) + r, iv, ((MyApplication) getActivity().getApplicationContext()).getDisplayImageOptions(false));



        Button changeImage = (Button) rootView.findViewById(R.id.btnChangeImage);
        changeImage.setTextColor(getResources().getColor(R.color.fund_white));

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double r = Math.random();
                ImageLoader.getInstance().displayImage(getString(R.string.domain)+getString(R.string.base_url_verifycode) + r, iv, ((MyApplication) getActivity().getApplicationContext()).getDisplayImageOptions(false));

            }
        });

        Button next = (Button) rootView.findViewById(R.id.btnNext);
        next.setTextColor(getResources().getColor(R.color.fund_white));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.register_main,new ForgetPwdSecondFragment()).addToBackStack(null).commit();
            }
        });

        Button toLogin = (Button) rootView.findViewById(R.id.btnToLogin);
        toLogin.setVisibility(View.GONE);
        toLogin.setTextColor(getResources().getColor(R.color.fund_red));
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.register_main, new LoginFragment()).addToBackStack(null).commit();
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

                getFragmentManager().popBackStack();
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }
}
