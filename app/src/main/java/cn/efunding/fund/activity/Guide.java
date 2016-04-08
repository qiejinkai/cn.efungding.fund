package cn.efunding.fund.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.efunding.fund.R;

/**
 * Created by qiejinkai on 16/4/1.
 */
public class Guide extends Activity implements ViewPager.OnPageChangeListener{

    private int currPosition = 0;
    private static final int MSG_NEXT = 1;
    private long waitTime = 4000;
    private ViewPager vp ;
    private MyViewPagerAdapter mpa;
    private List<View> list;
    private LinearLayout ll ;

    private List<ImageView> docs;

    private ViewPagerImageHandler vpih ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_module);

        initItem();

        vp = (ViewPager) findViewById(R.id.vp);

        mpa = new MyViewPagerAdapter(list,this);

        vp.setAdapter(mpa);

        vp.addOnPageChangeListener(this);

        vpih = new ViewPagerImageHandler();

        vpih.sendEmptyMessageDelayed(MSG_NEXT,waitTime);

    }

    private void initItem(){
        list = new ArrayList<View>();
        list.add(createImageView(R.drawable.banner1, this));
        list.add(createImageView(R.drawable.banner2,this));
        list.add(createImageView(R.drawable.banner3,this));
        ll = (LinearLayout)findViewById(R.id.ll);
        docs = new ArrayList<ImageView>();
        for (int i = 0 ;i <list.size();i++){

            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.login_point);
            ll.addView(iv, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            docs.add(iv);
        }

    }
    private static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    private View createImageView(int image,Context context){

        LinearLayout root = new LinearLayout(context);

        ImageView iv = new ImageView(context);

        iv.setImageBitmap(readBitMap(this, image));
        iv.setScaleType(ImageView.ScaleType.CENTER);
        iv.setAdjustViewBounds(true);
        root.addView(iv, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        return root;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.currPosition = position;
        if(docs.size() >1 && !vpih.hasMessages(MSG_NEXT)) {
            vpih.sendEmptyMessageDelayed(MSG_NEXT, waitTime);
        }

        int index = 0;
        for (ImageView iv:docs) {
            if(index == position){
                iv.setImageResource(R.drawable.login_point_selected);
            }else{
                iv.setImageResource(R.drawable.login_point);
            }
            index++;
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyViewPagerAdapter extends PagerAdapter{

        private List<View> views;
        private Context context;

        public MyViewPagerAdapter(List<View> views, Context context) {
            this.views = views;
            this.context = context;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public class ViewPagerImageHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_NEXT:
                    System.out.println("CURRPOSITION  : "+currPosition);
                    int position = currPosition >= list.size()-1 ? 0 : currPosition+1;
                    System.out.println("POSITION  : " + position);
                    vp.setCurrentItem(position);
                    break;
            }
        }
    }
}
