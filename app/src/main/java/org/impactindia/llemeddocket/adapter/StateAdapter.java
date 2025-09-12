package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.State;

import java.util.List;

public class StateAdapter extends ArrayAdapter<State> {

    private Context context;
    private int resource;
    private LayoutInflater inflater;
    private List<State> stateList;

    public StateAdapter(Context context, int resource, int textViewResourceId,
                        List<State> stateList) {
        super(context, resource, textViewResourceId, stateList);
        this.context = context;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.stateList = stateList;
    }

    public void setStateData(List<State> stateList) {
        this.stateList = null;
        this.stateList = stateList;
    }

    @Override
    public int getCount() {
        return stateList.size();
    }

    @Override
    public State getItem(int position) {
        return stateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if (rowView == null) {
            rowView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemText = (TextView) rowView.findViewById(R.id.itemText);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        populateList(position, viewHolder);
        return rowView;
    }

    private void populateList(int position, ViewHolder viewHolder) {
        State state = getItem(position);
        viewHolder.itemText.setText(state.getStateName());
    }

    private static class ViewHolder {
        public TextView itemText;
    }

}
