package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;
import java.util.List;

public class VillagelistAdapter extends ArrayAdapter<PhcUserData> {
    Context context;
    int resource,textviewid;
    List<PhcUserData> items,tempItems,suggestions;

    public VillagelistAdapter(Context context, int resource, int textViewResourceId, List<PhcUserData> items) {
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
            view = inflater.inflate(R.layout.autocomplete_list, parent, false);
        }
        PhcUserData people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.auto_text);
            if (lblName != null)
                lblName.setText(people.getVillage());
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
            return ((PhcUserData) resultValue).getVillage();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i("VillageFilter", "performFiltering called | constraint = " + constraint);

            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                suggestions.clear();
                for (PhcUserData people : tempItems) {
                    if (people.getVillage().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                Log.i("VillageFilter", "Filtered size = " + suggestions.size());
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
            } else {
                // ✅ Show all villages when text is empty
                Log.i("VillageFilter", "Empty text -> returning full list size = " + tempItems.size());
                filterResults.values = new ArrayList<>(tempItems);
                filterResults.count = tempItems.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.i("VillageFilter", "publishResults called | count = " + results.count);

            if (results != null && results.count > 0) {
                clear();
                addAll((List<PhcUserData>) results.values);
                notifyDataSetChanged();
                Log.i("VillageFilter", "Adapter updated with size = " + getCount());
            } else {
                Log.i("VillageFilter", "No results to publish");
            }
        }
    };

}
