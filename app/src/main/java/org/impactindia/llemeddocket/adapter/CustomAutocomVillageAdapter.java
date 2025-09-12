package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;
import java.util.List;

public class CustomAutocomVillageAdapter extends ArrayAdapter<PhcUserData> {
    Context context;
    ArrayList<PhcUserData> getvillage = new ArrayList<PhcUserData>();
    private ArrayList<PhcUserData> filteredProducts = new ArrayList<PhcUserData>();

    public CustomAutocomVillageAdapter(Context context,  ArrayList<PhcUserData> getvillage) {
        super(context, R.layout.autocomplete_list, getvillage);
        this.context = context;
        this.getvillage = getvillage;
    }

    @Override
    public int getCount() {
        return filteredProducts.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new ProductFilter(this, getvillage);
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        PhcUserData product = filteredProducts.get(position);
        view = LayoutInflater.from(context).inflate(R.layout.autocomplete_list, parent, false);
        TextView textViewName = (TextView) view.findViewById(R.id.auto_text);
        //ImageView imageViewPhoto = (ImageView) view.findViewById(R.id.imageViewPhoto);
        textViewName.setText(product.getVillage());
        //imageViewPhoto.setImageResource(product.getPhoto());
        return view;
    }




    private class ProductFilter extends Filter {

        CustomAutocomVillageAdapter productListAdapter;
        ArrayList<PhcUserData> originalList;
        ArrayList<PhcUserData> filteredList;

        public ProductFilter(CustomAutocomVillageAdapter productListAdapter, ArrayList<PhcUserData> originalList) {
            super();
            this.productListAdapter = productListAdapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final PhcUserData product : originalList) {
                    if (product.getVillage().toLowerCase().contains(filterPattern)) {
                        filteredList.add(product);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productListAdapter.filteredProducts.clear();
            productListAdapter.filteredProducts.addAll((List) results.values);
            productListAdapter.notifyDataSetChanged();
        }
    }

}




