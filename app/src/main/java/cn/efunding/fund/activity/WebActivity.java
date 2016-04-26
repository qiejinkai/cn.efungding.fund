package cn.efunding.fund.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.efunding.fund.R;

/**
 * Created by qiejinkai on 16/4/5.
 */
public class WebActivity extends AppCompatActivity {

    private WebView wv ;

    private String from_action ;

    private String url;

    private String bar_title ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_page);

        Intent i = getIntent();

        from_action = i.getStringExtra("from_action");
        url = i.getStringExtra("url");
        bar_title = i.getStringExtra("bar_title");
        initBar();
        wv = (WebView) findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setUserAgentString("cn.efunding.app");
        //wv.setWebViewClient(new SampleWebViewClient());

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv.loadUrl(url);


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

                startActivity(new Intent(from_action));
                finish();
                overridePendingTransition(R.animator.in_from_left_500, R.animator.out_to_left);
            }
        });
        setSupportActionBar(toolbar);

    }


}
class WebActivityIntent extends Intent{

    public WebActivityIntent(Context packageContext, Class<?> cls,String from_action,String url){
        super(packageContext,cls);
        this.putExtra("url", url);
        this.putExtra("from_action", from_action);
    }

    public WebActivityIntent(Context packageContext, Class<?> cls,String from_action,int urlId){
        super(packageContext, cls);
        this.putExtra("url", packageContext.getResources().getString(urlId));
        this.putExtra("from_action", from_action);
    }
    public WebActivityIntent(Context packageContext, Class<?> cls,String from_action,int urlId,String bar_title){
        super(packageContext, cls);
        this.putExtra("url", packageContext.getResources().getString(urlId));
        this.putExtra("from_action", from_action);
        this.putExtra("bar_title",bar_title);
    }
}