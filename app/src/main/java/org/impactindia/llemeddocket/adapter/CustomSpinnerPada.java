package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;

import java.util.ArrayList;

public class CustomSpinnerPada extends BaseAdapter {
    Context context;
    ArrayList<PhcSubcenterData> userdata = new ArrayList<PhcSubcenterData>();
    LayoutInflater inflter;

    public CustomSpinnerPada(Context context, ArrayList<PhcSubcenterData> userdata) {
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
        convertView = inflter.inflate(R.layout.pada_item_list, null);
        TextView names =(TextView)convertView.findViewById(R.id.txtpada);
        names.setText(userdata.get(position).getPada());
        return convertView;

    }
}
