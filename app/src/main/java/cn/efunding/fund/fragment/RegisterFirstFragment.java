package cn.efunding.fund.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.net.URL;

import cn.efunding.fund.R;
import cn.efunding.fund.activity.MainActivity;
import cn.efunding.fund.activity.UserActivity;
import cn.efunding.fund.appliacation.MyApplication;
import cn.efunding.fund.common.JSON;
import cn.efunding.fund.common.Value;
import cn.efunding.fund.url.CURL;
import cn.efunding.fund.url.IPost;

/**
 * Created by qiejinkai on 16/4/26.
 */
public class RegisterFirstFragment extends Fragment {

    private String bar_title = "注册新用户";

    private final static int MSG_ERROR = 1;
    private final static int MSG_NEXT = 2;

    private String DID;

    private EditText phone ;

    private String phoneNum;

    private String code;

    private EditText verifyCode;

    private String codeUrl ;

    ImageView iv;

    public RegisterFirstFragment() {

    }

    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MSG_ERROR:
                    String error = msg.obj.toString();
                    getUserActivity().alertMsg(error);
                    ImageLoader.getInstance().displayImage(codeUrl, iv, ((MyApplication) getActivity().getApplicationContext()).getDisplayImageOptions(false));

                    break;
                case MSG_NEXT:
                    getUserActivity().setPhone(phoneNum);

                    getFragmentManager().beginTransaction().replace(R.id.register_main,new RegisterSecondFragment()).addToBackStack(null).commit();

                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register_first_fragment,container,false);
        initBar(rootView);
        initPage(rootView);
        return rootView;
    }

    private void initPage(View rootView){

        MyApplication context = (MyApplication)getActivity().getApplicationContext();

        DID = context.getDid();

        codeUrl = getString(R.string.domain) + getString(R.string.base_url_join_code).replace("{did}", DID);

        iv = (ImageView) rootView.findViewById(R.id.verifyCodeImage);
        iv.setBackgroundColor(getResources().getColor(R.color.fund_white));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().displayImage(codeUrl, iv, ((MyApplication) getActivity().getApplicationContext()).getDisplayImageOptions(false));

            }
        });
        ImageLoader.getInstance().displayImage(codeUrl, iv, ((MyApplication) getActivity().getApplicationContext()).getDisplayImageOptions(false));

        Button changeImage = (Button) rootView.findViewById(R.id.btnChangeImage);
        changeImage.setTextColor(getResources().getColor(R.color.fund_white));

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().displayImage(codeUrl, iv, ((MyApplication) getActivity().getApplicationContext()).getDisplayImageOptions(false));

            }
        });

        phone = (EditText) rootView.findViewById(R.id.referee);
        verifyCode = (EditText) rootView.findViewById(R.id.verifyCode);

        Button next = (Button) rootView.findViewById(R.id.btnNext);
        next.setTextColor(getResources().getColor(R.color.fund_white));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum = phone.getText().toString();
                code = verifyCode.getText().toString();

                new AsyncTask<Void, Void, Void>() {

                    private String error;
                    @Override
                    protected Void doInBackground(Void... params) {

                        if(Value.isEmpty(phoneNum)){
                            error = "请输入手机号";
                            return null;
                        }
                        if(Value.isEmpty(code)){
                            error = "请输入验证码";
                            return null;
                        }

                        String postUrl = getString(R.string.domain) + getString(R.string.base_url_join_smscode);

                        try {
                            IPost  post = CURL.post(new URL(postUrl));
                            post.addValue("did", DID);
                            post.addValue("phone", phoneNum);
                            post.addValue("code", code);
                            Object ob = JSON.decodeString(post.exec());
                            error = Value.stringValueForKey(ob, "error",null);
                            return null;

                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            error = throwable.getMessage();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        if(error != null ){

                            Message msg = new Message();
                            msg.what = MSG_ERROR ;
                            msg.obj = error;
                            myHandler.sendMessage(msg);

                        }else{
                            Message msg = new Message();
                            msg.what = MSG_NEXT ;
                            myHandler.sendMessage(msg);

                        }

                    }
                }.execute();
            }
        });

        Button toLogin = (Button) rootView.findViewById(R.id.btnToLogin);
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

                getActivity().startActivity(new Intent(MainActivity.ACTION));
                getActivity().finish();
                getActivity().overridePendingTransition(R.animator.in_from_left_500, R.animator.out_to_left);
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    private UserActivity getUserActivity(){
        return (UserActivity)getActivity();
    }
}
