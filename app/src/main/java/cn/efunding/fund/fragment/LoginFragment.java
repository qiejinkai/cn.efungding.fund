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
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.net.URL;

import cn.efunding.fund.R;
import cn.efunding.fund.activity.MainActivity;
import cn.efunding.fund.activity.MeActivity;
import cn.efunding.fund.activity.UserActivity;
import cn.efunding.fund.appliacation.MyApplication;
import cn.efunding.fund.common.JSON;
import cn.efunding.fund.common.Value;
import cn.efunding.fund.url.CURL;
import cn.efunding.fund.url.IPost;

/**
 * Created by qiejinkai on 16/4/26.
 */
public class LoginFragment extends Fragment {

    private final static int MSG_ERROR = 1;

    private final static int MSG_LOGIN = 2;


    private String bar_title = "登录";

    private String phone;

    private String DID;

    private String password;

    private EditText phoneEt;

    private EditText passwordEt;

    private String loginUrl;

    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MSG_ERROR:
                    String error = msg.obj.toString();
                    getUserActivity().alertMsg(error);
                    break;
                case MSG_LOGIN:
                    Object obj = msg.obj;

                    if(obj!=null){
                        String token = Value.stringValueForKey(obj,"token",null);
                        MyApplication context = (MyApplication)getActivity().getApplicationContext();
                        context.setToken(token);
                        context.setIsLogin(true);
                        context.setUserInfo(Value.stringValueForKey(obj, "user", null));
                        context.setWalletInfo(Value.stringValueForKey(obj, "wallet", null));

                        getActivity().startActivity(new Intent(MeActivity.ACTION));
                        getActivity().finish();
                    }
                    break;
            }
        }
    };

    public LoginFragment() {
    }

    private UserActivity getUserActivity(){
        return (UserActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.login_fragment,container,false);
        initBar(rootView);
        initPage(rootView);
        return rootView;
    }

    private void initPage(View rootView){


        Button forget = (Button) rootView.findViewById(R.id.forget);
        forget.setTextColor(getResources().getColor(R.color.fund_white));

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.register_main, new ForgetPwdFirstFragment()).addToBackStack(null).commit();
            }
        });

        phoneEt = (EditText) rootView.findViewById(R.id.phone);
        passwordEt = (EditText) rootView.findViewById(R.id.password);
        DID = ((MyApplication)getActivity().getApplicationContext()).getDid();

        Button login = (Button) rootView.findViewById(R.id.login);
        login.setTextColor(getResources().getColor(R.color.fund_white));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = phoneEt.getText().toString();
                password = passwordEt.getText().toString();
                loginUrl = getString(R.string.domain)+getString(R.string.base_url_login);
                //getUserActivity().alertMsg("点我点我点我");


                new AsyncTask<Void, Void, Void>() {
                    private String error;
                    private Object result;
                    @Override
                    protected Void doInBackground(Void... params) {

                        if(Value.isEmpty(phone)){

                            error = "请输入手机号";
                            return null;
                        }
                        if(Value.isEmpty(password)){
                            error = "请输入密码";
                            return null;
                        }

                        try {
                            IPost post = CURL.post(new URL(loginUrl));
                            post.addValue("did", DID);
                            post.addValue("phone", phone);
                            post.addValue("password", password);
                            Object ob = JSON.decodeString(post.exec());
                            error = Value.stringValueForKey(ob, "error",null);
                            result = ob;
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
                            msg.what = MSG_LOGIN ;
                            msg.obj = result;
                            myHandler.sendMessage(msg);

                        }
                    }
                }.execute();
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
