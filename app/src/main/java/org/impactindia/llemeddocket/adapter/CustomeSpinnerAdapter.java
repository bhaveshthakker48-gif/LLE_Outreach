package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;
import java.util.List;

public class CustomeSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<PhcUserData> userdata = new ArrayList<PhcUserData>();
    LayoutInflater inflter;

    public CustomeSpinnerAdapter(Context context, ArrayList<PhcUserData> userdata) {
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
        convertView = inflter.inflate(R.layout.phc_list_item, null);
        TextView names =(TextView)convertView.findViewById(R.id.txtData);
        names.setText(userdata.get(position).getPhc());
        return convertView;

    }
}
