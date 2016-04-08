package cn.efunding.fund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.efunding.fund.R;
import cn.efunding.fund.entity.XSubject;

/**
 * Created by qiejinkai on 16/4/7.
 */
public class SubjectXAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<XSubject> subjectXList;

    public SubjectXAdapter(Context context,List<XSubject>subjectXList ) {
        this.layoutInflater = LayoutInflater.from(context);
        this.subjectXList = subjectXList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return subjectXList.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectXList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.subject_x_module,null);
        }

        //System.out.println("X Size : "+subjectXList.size());
        XSubject xSubject = (XSubject)subjectXList.get(position);
        System.out.println(xSubject.getTitle());
        TextView tv = (TextView) convertView.findViewById(R.id.x_subject_tv);
        tv.setText(xSubject.getTitle());
        return convertView;
    }
}
