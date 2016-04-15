package cn.efunding.fund.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.efunding.fund.R;
import cn.efunding.fund.activity.MainActivity;
import cn.efunding.fund.activity.SubjectActivity;
import cn.efunding.fund.activity.WebActivity;
import cn.efunding.fund.entity.Banner;
import cn.efunding.fund.entity.XSubject;
import cn.efunding.fund.entity.YSubject;
import context.FundAppliaction;

/**
 * Created by qiejinkai on 16/4/7.
 */
public class HomeAdapter extends BaseAdapter implements ViewPager.OnPageChangeListener{

    private List<Banner> bannerList ;

    private List<XSubject> x_subjectList ;
    private List<YSubject> y_subjectList ;
    private LayoutInflater layoutInflater;
    private Context context;

    WeakReference<Activity> activity;

    public List<XSubject> getX_subjectList() {
        return x_subjectList;
    }

    public void setX_subjectList(List<XSubject> x_subjectList) {
        this.x_subjectList = x_subjectList;
    }

    public List<YSubject> getY_subjectList() {
        return y_subjectList;
    }

    public void setY_subjectList(List<YSubject> y_subjectList) {
        this.y_subjectList = y_subjectList;
    }

    public HomeAdapter(Context context,List<Banner> bannerList, List<XSubject> x_sujectList, List<YSubject> y_subjectList ) {
        this.bannerList = bannerList;
        this.x_subjectList = x_sujectList;
        this.y_subjectList = y_subjectList;
        this.layoutInflater = LayoutInflater.from(context);;
        this.context = context;
        activity = new WeakReference<Activity>((Activity)context);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.main_content,null);
            
        }
        makeBanner(convertView);

        makeSubjectX(convertView);

        makeSubjectY(convertView);

        return convertView;
    }

    private ListView y_listView ;
    private SubjectYAdapter yAdapter;

    public SubjectYAdapter getyAdapter() {
        return yAdapter;
    }

    public void setyAdapter(SubjectYAdapter yAdapter) {
        this.yAdapter = yAdapter;
    }

    public SubjectXAdapter getxAdapter() {
        return xAdapter;
    }

    public void setxAdapter(SubjectXAdapter xAdapter) {
        this.xAdapter = xAdapter;
    }

    private void makeSubjectY(View convertView) {
        if(convertView != null){
            y_listView = (ListView) convertView.findViewById(R.id.y_listView);
            yAdapter = new SubjectYAdapter(convertView.getContext(),y_subjectList);
            y_listView.setAdapter(yAdapter);
            setListViewHeightBasedOnChildren(y_listView);
            y_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(SubjectActivity.ACTION);
                    i.putExtra("title", y_subjectList.get(position).getTitle());
                    context.startActivity(i);
                    activity.get().finish();

                }
            });
        }
    }


    private ListView x_listView ;
    private SubjectXAdapter xAdapter;
    private void makeSubjectX(View convertView) {
        if(convertView != null){
            x_listView = (ListView) convertView.findViewById(R.id.x_listView);
            xAdapter = new SubjectXAdapter(convertView.getContext(),x_subjectList);
            x_listView.setAdapter(xAdapter);
            setListViewHeightBasedOnChildren(x_listView);
            //x_listView
            x_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(SubjectActivity.ACTION);
                    i.putExtra("title",x_subjectList.get(position).getTitle());
                    context.startActivity(i);
                    activity.get().finish();
                }
            });
        }

    }

    //刷新两个list的高度
    public void reFreshListViewHeight(){
        setListViewHeightBasedOnChildren(x_listView);
        setListViewHeightBasedOnChildren(y_listView);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        Adapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    private int currPosition = 0;
    private static final int MSG_NEXT = 1;
    private long waitTime = 4000;
    private ViewPager vp ;
    private MyViewPagerAdapter mpa;
    private List<Object> imagesInfo;
    private List<View> viewList = new ArrayList<View>();;
    private LinearLayout ll ;

    private List<ImageView> docs =  new ArrayList<ImageView>();;

    private ViewPagerImageHandler vpih ;


    private void makeBanner(View convertView) {

        if(convertView != null) {

            initItem(convertView);

            vp = (ViewPager) convertView.findViewById(R.id.vp);

            mpa = new MyViewPagerAdapter(viewList, context);

            vp.setAdapter(mpa);

            vp.addOnPageChangeListener(this);

            vpih = new ViewPagerImageHandler();

            vpih.sendEmptyMessageDelayed(MSG_NEXT, waitTime);
        }
    }


    private void initItem(View convertView ){

        if(bannerList != null && bannerList.size() > 0) {
            for (Banner banner :bannerList) {
                //Banner banner = (Banner)obj;
                //System.out.println(banner.getImageId());
                viewList.add(createImageView(banner.getSrcUrl(),banner.getImageId(),banner.getUrl(), context));
            }

            ll = (LinearLayout) convertView.findViewById(R.id.ll);
            docs = new ArrayList<ImageView>();
            for (int i = 0; i < viewList.size(); i++) {

                ImageView iv = new ImageView(context);
                iv.setImageResource(R.drawable.login_point);
                ll.addView(iv, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                docs.add(iv);
            }
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

    private View createImageView(String srcUrl,int image, final String url , final Context context){

        LinearLayout root = new LinearLayout(context);

        ImageView iv = new ImageView(context);

        //iv.setImageBitmap(readBitMap(context, image));
        iv.setScaleType(ImageView.ScaleType.CENTER);
        //iv.setAdjustViewBounds(true);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebActivity.class);
                i.putExtra("url",url);
                i.putExtra("from_action",MainActivity.ACTION);
                context.startActivity(i);
                //finish();
                activity.get().finish();
            }
        });
        ImageLoader.getInstance().displayImage(srcUrl,iv,((FundAppliaction)context.getApplicationContext()).getDisplayImageOptions());
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
    public class ViewPagerImageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_NEXT:
                    //System.out.println("CURRPOSITION  : "+currPosition);
                    int position = currPosition >= viewList.size()-1 ? 0 : currPosition+1;
                    //System.out.println("POSITION  : " + position);
                    vp.setCurrentItem(position);
                    break;
            }
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

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
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}
