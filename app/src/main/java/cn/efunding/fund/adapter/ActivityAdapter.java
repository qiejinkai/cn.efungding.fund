package cn.efunding.fund.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.efunding.fund.R;
import cn.efunding.fund.activity.ActivityActivity;
import cn.efunding.fund.activity.MainActivity;
import cn.efunding.fund.activity.WebActivity;
import cn.efunding.fund.entity.Activity;
import cn.efunding.fund.entity.XSubject;
import context.FundAppliaction;

/**
 * Created by qiejinkai on 16/4/7.
 */
public class ActivityAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Activity> activitieList;


    public ActivityAdapter(Context context, List<Activity> activitieList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.activitieList = activitieList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return activitieList.size();
    }

    @Override
    public Object getItem(int position) {
        return activitieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.activity_module,null);
        }
        final Activity activity = activitieList.get(position);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        iv.setScaleType(ImageView.ScaleType.CENTER);
        //iv.setAdjustViewBounds(true);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebActivity.class);
                i.putExtra("url",activity.getUrl());
                i.putExtra("from_action", ActivityActivity.ACTION);
                context.startActivity(i);
                //finish();
                //context.overridePendingTransition(R.animator.in_from_right, R.animator.out_to_left);
            }
        });
        ImageLoader.getInstance().displayImage(activity.getImageUrl(),iv,((FundAppliaction)context.getApplicationContext()).getDisplayImageOptions());

        return convertView;
    }

    public List<Activity> getActivitieList() {
        return activitieList;
    }

    public void setActivitieList(List<Activity> activitieList) {
        this.activitieList = activitieList;
    }
}
