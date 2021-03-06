package cn.efunding.fund.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.efunding.fund.R;
import cn.efunding.fund.adapter.MyXSubjectAdapter;
import cn.efunding.fund.entity.XSubject;
import cn.efunding.fund.entity.YSubject;

/**
 * Created by qiejinkai on 16/4/21.
 */
public class MyXSubjectActivity extends AppCompatActivity {

    private String bar_title ="我的新手标";
    private PullToRefreshListView plv;

    private List<XSubject> list = new ArrayList<XSubject>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_subject_x_page);

        initBar();
        initPage();

        list.add(new XSubject("新手标1号"));

        final MyXSubjectAdapter adapter = new MyXSubjectAdapter(this,list);
        plv = (PullToRefreshListView) findViewById(R.id.lv);
        plv.setAdapter(adapter);
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
                        adapter.notifyDataSetChanged();
                        plv.onRefreshComplete();
                    }
                }.execute();
            }
        });


    }

    private void initPage(){
        TextView totalValue = (TextView) findViewById(R.id.totalValue);
        totalValue.setTextColor(getResources().getColor(R.color.fund_white));
        TextView totalValue_text = (TextView) findViewById(R.id.totalValue_text);
        totalValue_text.setTextColor(getResources().getColor(R.color.fund_white));
        TextView income = (TextView) findViewById(R.id.income);
        income.setTextColor(getResources().getColor(R.color.fund_white));
        TextView income_text = (TextView) findViewById(R.id.income_text);
        income_text.setTextColor(getResources().getColor(R.color.fund_white));


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

                startActivity(new Intent(MyXSubjectActivity.this,MeActivity.class));
                finish();
                overridePendingTransition(R.animator.in_from_left_500, R.animator.out_to_left);
            }
        });
        setSupportActionBar(toolbar);

    }

}
