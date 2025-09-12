package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;

public class CustomSpinnerSubCenter extends BaseAdapter {
    Context context;
    ArrayList<PhcSubcenterData> userdata = new ArrayList<PhcSubcenterData>();
    LayoutInflater inflter;

    public CustomSpinnerSubCenter(Context context, ArrayList<PhcSubcenterData> userdata) {
        this.context = context;
        this.userdata = userdata;
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return userdata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.subcenter_list_item, null);
        TextView names =(TextView)convertView.findViewById(R.id.txtsubcenterData);
        names.setText(userdata.get(position).getSubCenter());
       // names.setText(userdata.get(position).getPada());
        return convertView;

    }
}
