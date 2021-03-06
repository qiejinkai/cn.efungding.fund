package cn.efunding.fund.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.efunding.fund.R;
import cn.efunding.fund.adapter.HomeAdapter;
import cn.efunding.fund.common.JSON;
import cn.efunding.fund.common.Value;
import cn.efunding.fund.entity.Article;
import cn.efunding.fund.entity.XSubject;
import cn.efunding.fund.entity.YSubject;
import cn.efunding.fund.url.CURL;
import cn.efunding.fund.url.IGet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION = "cn.efunding.fund.activity.intent.action.MainActivity";
    private int bar_title = R.string.bar_home_title;
    private int [] footer_images = {R.drawable.home_selected,R.drawable.activity,R.drawable.me,R.drawable.help};

    public static final int MSG_TOAST = 1;

    private PullToRefreshListView plv;

    private AdapterView views;

    private HomeAdapter homeAdapter;

    List<Article> bannerList;
    List<XSubject> xSubjectList = new ArrayList<XSubject>();
    List<YSubject> ySubjectList = new ArrayList<YSubject>();

    public class MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_TOAST:
                    Toast.makeText(MainActivity.this,msg.obj.toString(),Toast.LENGTH_LONG);
                    break;
            }
        }
    }

    public interface MainCallBack{
        public void handle();
    }

    class MainAsyncTask extends AsyncTask<Void,Void,Void>{

        private Object bannerArray ;
        private Object xsubjectArray ;
        private Object ysubjectArray ;

        private Object returnData;

        private MainCallBack cb;

        public MainAsyncTask(MainCallBack cb) {
            this.cb = cb;
        }

        public MainAsyncTask() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            String homeUrl =getString(R.string.domain)+getString(R.string.base_url_query_home);


            IGet get;

            try {

                get = CURL.get(new URL(homeUrl));

                returnData = JSON.decodeString(get.exec());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            bannerArray = Value.objectValueForKey(returnData,"banners",null);
            xsubjectArray = Value.objectValueForKey(returnData,"newheads",null);
            ysubjectArray = Value.objectValueForKey(returnData,"yuejinbao",null);

            if(bannerArray != null ){
                bannerList= new ArrayList<Article>();
                List<Object> list = (List<Object>)bannerArray;
                for (Object banner:list) {
                    Article a = new Article();
                    a.setImageUrl(getString(R.string.domain)+Value.stringValueForKey(banner,"image",""));
                    a.setUrl(Value.stringValueForKey(banner,"url",""));
                    bannerList.add(a);
                }

                if(homeAdapter != null){
                    homeAdapter.setBannerList(bannerList);
                    homeAdapter.notifyDataSetChanged();
                }

            }

            if(xsubjectArray != null ){
                xSubjectList= new ArrayList<XSubject>();
                List<Object> list = (List<Object>)xsubjectArray;
                for (Object xsubject:list) {
                    XSubject x = new XSubject(xsubject);
                    xSubjectList.add(x);
                }

                if(homeAdapter != null){
                    homeAdapter.setX_subjectList(xSubjectList);
                    homeAdapter.getxAdapter().notifyDataSetChanged();
                }

            }

            if(ysubjectArray != null ){
                ySubjectList= new ArrayList<YSubject>();
                List<Object> list = (List<Object>)ysubjectArray;
                for (Object ysubject:list) {
                    YSubject y = new YSubject(ysubject);
                    ySubjectList.add(y);
                }

                if(homeAdapter != null){
                    homeAdapter.setY_subjectList(ySubjectList);
                    homeAdapter.getyAdapter().notifyDataSetChanged();
                }

            }

            if(cb != null){
                cb.handle();
            }

        }



    }

    private void initData(){

       new MainAsyncTask().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        initData();
        initBar();
        initFooter();
        plv = (PullToRefreshListView) findViewById(R.id.lv);
        homeAdapter  = new HomeAdapter(this,bannerList,xSubjectList,ySubjectList);
        plv.setAdapter(homeAdapter);

        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new MainAsyncTask(new MainCallBack() {
                    @Override
                    public void handle() {
                        plv.onRefreshComplete();
                    }
                }).execute();

            }
        });


    }

    private void initBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView tv = (TextView) findViewById(R.id.bar_title);
        tv.setText(bar_title);
        tv.setTextColor(getResources().getColor(R.color.fund_white));
        Button btn = (Button) findViewById(R.id.btn_back_main);
        toolbar.removeView(btn);
        setSupportActionBar(toolbar);
    }

    private void initFooter(){
        ImageButton home = (ImageButton) findViewById(R.id.btn_home);
        home.setImageResource(footer_images[0]);
        home.setOnClickListener(this);

        ImageButton activity = (ImageButton) findViewById(R.id.btn_activity);
        activity.setImageResource(footer_images[1]);
        activity.setOnClickListener(this);

        ImageButton me = (ImageButton) findViewById(R.id.btn_me);
        me.setImageResource(footer_images[2]);
        me.setOnClickListener(this);

        ImageButton help = (ImageButton) findViewById(R.id.btn_help);
        help.setImageResource(footer_images[3]);
        help.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_message:
                startActivity(new Intent(MainActivity.this, MessageActivity.class));
                //finish();
                overridePendingTransition(R.animator.in_from_right_500,R.animator.out_to_left);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home:
                break;
            case R.id.btn_activity:
                startActivity(new Intent(MainActivity.this, ActivityActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_right,R.animator.out_to_left);

                break;
            case R.id.btn_me:
                startActivity(new Intent(MainActivity.this, MeActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_right,R.animator.out_to_left);
                break;
            case R.id.btn_help:
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_right,R.animator.out_to_left);
                break;
        }
    }

}
