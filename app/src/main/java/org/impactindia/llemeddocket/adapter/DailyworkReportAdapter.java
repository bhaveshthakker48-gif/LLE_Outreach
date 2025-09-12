package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.ui.activity.AddHouseholdActivity;
import org.impactindia.llemeddocket.ui.activity.ViewDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyworkReportAdapter extends RecyclerView.Adapter<DailyworkReportAdapter.UserViewHolder> implements Filterable {

    public Context mContext;
    int type = 0;
    int tit = 0;
    ArrayList<PopMedicalData> data;
    ArrayList<PopMedicalData> datafiter;

    public DailyworkReportAdapter(Context mContext, ArrayList<PopMedicalData> data) {
        this.mContext = mContext;
        this.data = data;
        this.datafiter = data;
    }

    @NonNull
    @Override
    public DailyworkReportAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dailyreport_item,viewGroup,false);
        DailyworkReportAdapter.UserViewHolder viewHolder = new DailyworkReportAdapter.UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyworkReportAdapter.UserViewHolder userViewHolder, int i) {

        UserViewHolder userViewHolder1 = (UserViewHolder) userViewHolder;

        int rowPos = userViewHolder1.getAdapterPosition();
        if (rowPos  == 0)
        {
            userViewHolder.txtDate.setBackgroundResource(R.drawable.table_bg_header_background);
            userViewHolder.txtphc.setBackgroundResource(R.drawable.table_bg_header_background);
            userViewHolder.txtsubcentre.setBackgroundResource(R.drawable.table_bg_header_background);
            userViewHolder.txtvillage.setBackgroundResource(R.drawable.table_bg_header_background);
            userViewHolder.txtPada.setBackgroundResource(R.drawable.table_bg_header_background);
            userViewHolder.txthouseholdcount.setBackgroundResource(R.drawable.table_bg_header_background);
            userViewHolder.txtpopulationcount.setBackgroundResource(R.drawable.table_bg_header_background);


            userViewHolder.txtDate.setText("Date");
            userViewHolder.txtphc.setText("Phc");
            userViewHolder.txtsubcentre.setText("Sub-Center");
            userViewHolder.txtvillage.setText("Village");
            userViewHolder.txtPada.setText("Pada");
            userViewHolder.txthouseholdcount.setText("Households");
            userViewHolder.txtpopulationcount.setText("Population");
        }
        else
        {
            final PopMedicalData popMedicalData = data.get(rowPos - 1);

            userViewHolder.txtDate.setBackgroundResource(R.drawable.table_bg_content_boarder);
            userViewHolder.txtphc.setBackgroundResource(R.drawable.table_bg_content_boarder);
            userViewHolder.txtsubcentre.setBackgroundResource(R.drawable.table_bg_content_boarder);
            userViewHolder.txtvillage.setBackgroundResource(R.drawable.table_bg_content_boarder);
            userViewHolder.txtPada.setBackgroundResource(R.drawable.table_bg_content_boarder);
            userViewHolder.txthouseholdcount.setBackgroundResource(R.drawable.table_bg_content_boarder);
            userViewHolder.txtpopulationcount.setBackgroundResource(R.drawable.table_bg_content_boarder);


            userViewHolder.txtDate.setText(getddmmyyyy(popMedicalData.getDateofexamination()));
            userViewHolder.txtphc.setText(popMedicalData.getPhc());
            userViewHolder.txtsubcentre.setText(popMedicalData.getSubcentre());
            userViewHolder.txtvillage.setText(popMedicalData.getVillage());
            userViewHolder.txtPada.setText(popMedicalData.getPada());
            userViewHolder.txthouseholdcount.setText(popMedicalData.getHousholscount());
            userViewHolder.txtpopulationcount.setText(popMedicalData.getPopultioncount());
        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                Log.i("charString", charString);
                if (charString.isEmpty()) {
                    data = datafiter;
                } else {
                    ArrayList<PopMedicalData> filteredList = new ArrayList<>();

                    for (PopMedicalData programme : datafiter) {
                        if ((programme.getPhc() != null && programme.getPhc().toLowerCase().contains(charString.toLowerCase()))
                                || (programme.getSubcentre() != null && programme.getSubcentre().toLowerCase().contains(charString.toLowerCase()))
                                || (programme.getPada() != null && programme.getPada().toLowerCase().contains(charString.toLowerCase()))
                                ||(programme.getDateofexamination() != null && getddmmyyyy(programme.getDateofexamination()).toLowerCase().contains(charString.toLowerCase()) )
                                || (programme.getVillage() != null && programme.getVillage().toLowerCase().contains(charString.toLowerCase()))) {
                            filteredList.add(programme);
                        }
                    }
                    data = filteredList;
                    for (int i =0 ;i<data.size();i++)
                    {
                         tit += Integer.valueOf(data.get(i).getHousholscount());
                    }
                    Log.i("total=>dd",tit+"");
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<PopMedicalData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtDate,txtphc,txtsubcentre,txtvillage,txtPada,txthouseholdcount,txtpopulationcount;
       // ImageView img_Editdata,img_view;
        private final Context cntx;

        public UserViewHolder(View itemView) {
            super(itemView);
            cntx=itemView.getContext();
            itemView.setOnClickListener(this);
            txtDate = (TextView)itemView.findViewById(R.id.txtDate);
            txtphc = (TextView)itemView.findViewById(R.id.txtphc);
            txtsubcentre = (TextView)itemView.findViewById(R.id.txtsubcentre);
            txtvillage = (TextView)itemView.findViewById(R.id.txtvillage);
            txtPada = (TextView)itemView.findViewById(R.id.txtPada);
            txthouseholdcount = (TextView)itemView.findViewById(R.id.txthouseholdcount);
            txtpopulationcount = (TextView)itemView.findViewById(R.id.txtpopulationcount);

         }
        @Override
        public void onClick(View v) {
        }
    }

    public interface Clicklistener
    {
        public void itemClicked(View view, int position);
    }


    public String getddmmyyyy(String yyyymmdd)
    {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(yyyymmdd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString2 = new SimpleDateFormat("dd-MM-yyyy").format(date);
        return dateString2;
    }

}
