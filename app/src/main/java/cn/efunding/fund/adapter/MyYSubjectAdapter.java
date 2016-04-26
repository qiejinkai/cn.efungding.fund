package cn.efunding.fund.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.efunding.fund.R;
import cn.efunding.fund.entity.XSubject;
import cn.efunding.fund.entity.YSubject;
import cn.efunding.fund.view.RoundProgressBarView;

/**
 * Created by qiejinkai on 16/4/21.
 */
public class MyYSubjectAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<YSubject> list;
    private WeakReference<Activity> activity;

    public MyYSubjectAdapter(Context context, List<YSubject> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        activity = new WeakReference<Activity>((Activity)context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.subject_y_module,null);
        }
        YSubject ySubject = (YSubject)list.get(position);
        TextView tv = (TextView) convertView.findViewById(R.id.y_subject_tv_subject_name);
        tv.setText(ySubject.getTitle());

        TextView yeild= (TextView) convertView.findViewById(R.id.y_subject_tv_yeild);
        yeild.setTextColor(convertView.getResources().getColor(R.color.fund_red));
        yeild.setText("20~25");

        TextView percent= (TextView) convertView.findViewById(R.id.y_subject_tv_yeild_percent);
        percent.setTextColor(convertView.getResources().getColor(R.color.fund_red));

        RoundProgressBarView bpbv = (RoundProgressBarView) convertView.findViewById(R.id.y_subject_roundProgressBar);
        bpbv.setProgress(ySubject.getPercent());
        return convertView;
    }
}
