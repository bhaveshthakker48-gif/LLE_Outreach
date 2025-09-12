package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PhcUserData;
import org.impactindia.llemeddocket.pojo.UserDetails;

import java.util.ArrayList;

public class ActiveCampReport  extends BaseAdapter {
    Context context;
   // ArrayList<OutreachPlanData> userdata = new ArrayList<OutreachPlanData>();
    ArrayList<UserDetails> userdata = new ArrayList<UserDetails>();
    LayoutInflater inflter;

    public ActiveCampReport(Context context, ArrayList<UserDetails> userdata) {
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
        convertView = inflter.inflate(R.layout.activecamplist_item, null);
        TextView names = (TextView)convertView.findViewById(R.id.txtactivcamp);
        names.setText(userdata.get(position).getName());
        return convertView;

    }
}

