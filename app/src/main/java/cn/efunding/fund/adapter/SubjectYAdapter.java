package cn.efunding.fund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import cn.efunding.fund.R;
import cn.efunding.fund.entity.YSubject;
import cn.efunding.fund.view.RoundPregressBarView;

/**
 * Created by qiejinkai on 16/4/7.
 */
public class SubjectYAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<YSubject> subjectYList;

    public SubjectYAdapter(Context context,List<YSubject>subjectYList ) {
        this.layoutInflater = LayoutInflater.from(context);
        this.subjectYList = subjectYList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return subjectYList.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectYList.get(position);
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
        YSubject ySubject = (YSubject)subjectYList.get(position);
        TextView tv = (TextView) convertView.findViewById(R.id.y_subject_tv_subject_name);
        tv.setText(ySubject.getTitle());

        RoundPregressBarView bpbv = (RoundPregressBarView) convertView.findViewById(R.id.y_subject_roundProgressBar);
        bpbv.setProgress(20);
        return convertView;
    }
}
