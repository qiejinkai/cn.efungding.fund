package cn.efunding.fund.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import cn.efunding.fund.R;
import cn.efunding.fund.adapter.ActivityAdapter;
import cn.efunding.fund.entity.Article;

/**
 * Created by qiejinkai on 16/4/5.
 */
public class ActivityActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION = "cn.efunding.fund.activity.intent.action.ActivityActivity";

    private int bar_title = R.string.bar_activity_title;
    private int [] footer_images = {R.drawable.home,R.drawable.activity_selected,R.drawable.me,R.drawable.help};

    private PullToRefreshListView plv;

    private List<Article> activityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        initBar();
        initFooter();
        initActivity();
        plv = (PullToRefreshListView) findViewById(R.id.plv);

        final ActivityAdapter activityAdapter = new ActivityAdapter(this,activityList);
        plv.setAdapter(activityAdapter);
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        activityList.add(new Article("http://fund.xiaogutou.cn/", "http://xh.eechou.com/image/2016-03-21/1646b56a0231228afca6f85ca11686af.jpg"));
                        activityAdapter.notifyDataSetChanged();
                        plv.onRefreshComplete();
                    }
                }.execute();
            }
        });
        plv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, false));

    }
    private void initActivity(){
        activityList = new ArrayList<Article>();

        activityList.add(new Article("http://fund.xiaogutou.cn/","http://xh.eechou.com/image/2016-03-21/1646b56a0231228afca6f85ca11686af.jpg"));


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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home:
                startActivity(new Intent(ActivityActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left,R.animator.out_to_left);
                break;
            case R.id.btn_activity:

                break;
            case R.id.btn_me:
                startActivity(new Intent(ActivityActivity.this, MeActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_right,R.animator.out_to_left);
                break;
            case R.id.btn_help:
                startActivity(new Intent(ActivityActivity.this, HelpActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_right,R.animator.out_to_left);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance().clearMemoryCache();;
    }
}
