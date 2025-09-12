package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;
import java.util.List;

public class PadaUserList extends ArrayAdapter<PhcUserData> {
    Context context;
    int resource,textviewid;
    List<PhcUserData> items,tempItems,suggestions;

    public PadaUserList(Context context, int resource, int textViewResourceId, List<PhcUserData> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textviewid = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<PhcUserData>(items); // this makes the difference.
        suggestions = new ArrayList<PhcUserData>();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.padaautocomplete_list, parent, false);
        }
        PhcUserData people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.auto_text1);
            if (lblName != null)
                lblName.setText(people.getPada());
        }
        return view;
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((PhcUserData) resultValue).getPada();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (PhcUserData people : tempItems) {
                    if (people.getPada().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<PhcUserData> filterList = (ArrayList<PhcUserData>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (PhcUserData people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };

}
