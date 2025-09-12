package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PadanashaworkerData;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;

public class CustomeAshaSpinner extends BaseAdapter {
    Context context;
    ArrayList<PadanashaworkerData> userdata = new ArrayList<PadanashaworkerData>();
    LayoutInflater inflter;

    public CustomeAshaSpinner(Context context, ArrayList<PadanashaworkerData> userdata) {
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
        convertView = inflter.inflate(R.layout.ashaworkerlist, null);
        TextView names =(TextView)convertView.findViewById(R.id.txtasha);
        names.setText(userdata.get(position).getAshaworkername());
        return convertView;

    }
}
