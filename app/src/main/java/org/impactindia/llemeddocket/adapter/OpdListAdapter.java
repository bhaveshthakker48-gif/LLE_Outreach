package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.ui.activity.DashboardActivityOutPro;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.impactindia.llemeddocket.util.StringUtils.isEmpty;

import androidx.recyclerview.widget.RecyclerView;

public class OpdListAdapter extends RecyclerView.Adapter<OpdListAdapter.UserViewHolder> implements Filterable {

    public Context mContext;
    private static OpdListAdapter.Clicklistener clicklistener;
    int type = 0;
    ArrayList<PopMedicalData> data;
    ArrayList<PopMedicalData> datafilter;

    public OpdListAdapter(Context mContext, ArrayList<PopMedicalData> data) {
        this.mContext = mContext;
        this.data = data;
        this.datafilter = data;
    }

    @Override
    public OpdListAdapter.UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.opd_list,viewGroup,false);
        OpdListAdapter.UserViewHolder viewHolder = new OpdListAdapter.UserViewHolder(view);
        return viewHolder;
    }

    public void setClicklistener(OpdListAdapter.Clicklistener clicklistener)
    {
        this.clicklistener = clicklistener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public void onBindViewHolder(OpdListAdapter.UserViewHolder holder, final int position) {
      //  final OutreachPlanData outreachPlanData = data.get(position);
        PopMedicalData popMedicalData = data.get(position);
        if (!isEmpty(popMedicalData.getEyeopd()))
        {
            holder.eyeopddate.setText("Eye Opd :  " + getddmmyyyy(popMedicalData.getEyeopd()));
        }
        else
        {
            holder.eyeopddate.setVisibility(View.GONE);
        }
        if (!isEmpty(popMedicalData.getEyesurgery()))
        {
            holder.cataractopddate.setText("Cataract :  " +getddmmyyyy(popMedicalData.getEyesurgery()));
        }
        else
        {
            holder.cataractopddate.setVisibility(View.GONE);
        }
        if (!isEmpty(popMedicalData.getEpilepsyopd()))
        {
            holder.epfopddate.setText("Epilepsy Opd :  " +getddmmyyyy(popMedicalData.getEpilepsyopd()));
        }
        else
        {
            holder.epfopddate.setVisibility(View.GONE);
        }
        if (!isEmpty(popMedicalData.getDentalopd()))
        {
            holder.dentalopddate.setText("Dental Opd :  " +getddmmyyyy(popMedicalData.getDentalopd()));
        }
        else
        {
            holder.dentalopddate.setVisibility(View.GONE);
        }
        if (!isEmpty(popMedicalData.getPlasticsurgeryopd()))
        {
            holder.plasticsuropdate.setText("plasticsurgery Opd :  " +getddmmyyyy(popMedicalData.getPlasticsurgeryopd()));
        }
        else
        {
            holder.plasticsuropdate.setVisibility(View.GONE);
        }
        if (!isEmpty(popMedicalData.getOrthopedicsurgery()))
        {
            holder.orthoopdate.setText("Orthopedic Opd :  " +getddmmyyyy(popMedicalData.getOrthopedicsurgery()));
        }
        else
        {
            holder.orthoopdate.setVisibility(View.GONE);
        }
        if (!isEmpty(popMedicalData.getEntopd()))
        {
            holder.entopdate.setText("ENT Opd :  " +getddmmyyyy(popMedicalData.getEntopd()));
        }
        else
        {
            holder.entopdate.setVisibility(View.GONE);
        }
        if (!isEmpty(popMedicalData.getGynaecopd()))
        {
            holder.gynaecopdate.setText("Gynaec Opd :  " +getddmmyyyy(popMedicalData.getGynaecopd()));
        }
        else
        {
            holder.gynaecopdate.setVisibility(View.GONE);
        }
        if (!isEmpty(popMedicalData.getOralcanceropd()))
        {
            holder.oralcanceropd.setText("Cancer Opd :  " +getddmmyyyy(popMedicalData.getOralcanceropd()));
        }
        else
        {
            holder.oralcanceropd.setVisibility(View.GONE);
        }

        holder.txtpadaname.setText("Pada: " + popMedicalData.getPada());
        holder.nameagegenderopd.setText("Name : " + popMedicalData.getFname() + " " + popMedicalData.getMname() + " " + popMedicalData.getLname() + " ," + popMedicalData.getGender() + " , " + popMedicalData.getAge() );
        holder.txtDateofdataenter1.setText("Examination : " + getddmmyyyy(popMedicalData.getDateofexamination()));
        holder.txthouseholdid.setText("House Id : " + popMedicalData.getHouseholdno());

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                Log.i("charString1", charString);
                if (charString.isEmpty()) {
                    data = datafilter;
                } else {
                    ArrayList<PopMedicalData> filteredList = new ArrayList<>();
                    // here we are matching session name, topic, speaker & chairperson
                    for (PopMedicalData programme : datafilter) {
                        if ((programme.getDateofexamination() != null && getddmmyyyy(programme.getDateofexamination()).toLowerCase().contains(charString.toLowerCase()) ))
                        {
                            filteredList.add(programme);
                        }
                    }
                    data = filteredList;
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

        TextView nameagegenderopd,txtpadaname,txtDateofdataenter1,eyeopddate,cataractopddate,epfopddate,dentalopddate,plasticsuropdate,orthoopdate
        ,entopdate,gynaecopdate,txthouseholdid,oralcanceropd;
        private final Context cntx;
        LinearLayout llmessage_view;

        public UserViewHolder(View itemView) {
            super(itemView);
            cntx=itemView.getContext();
            itemView.setOnClickListener(this);
            nameagegenderopd = (TextView)itemView.findViewById(R.id.nameagegenderopd);
            txtpadaname = (TextView)itemView.findViewById(R.id.txtpadaname);
            txtDateofdataenter1 = (TextView)itemView.findViewById(R.id.txtDateofdataenter1);
            eyeopddate = (TextView)itemView.findViewById(R.id.eyeopddate);
            cataractopddate = (TextView)itemView.findViewById(R.id.cataractopddate);
            epfopddate = (TextView)itemView.findViewById(R.id.epfopddate);
            dentalopddate = (TextView)itemView.findViewById(R.id.dentalopddate);
            plasticsuropdate = (TextView)itemView.findViewById(R.id.plasticsuropdate);
            orthoopdate = (TextView)itemView.findViewById(R.id.orthoopdate);
            entopdate = (TextView)itemView.findViewById(R.id.entopdate);
            gynaecopdate = (TextView)itemView.findViewById(R.id.gynaecopdate);
            txthouseholdid = (TextView)itemView.findViewById(R.id.txthouseholdid);
            oralcanceropd = (TextView)itemView.findViewById(R.id.oralcanceropd);
        }

        @Override
        public void onClick(View view) {
            if (clicklistener!=null)
            {
                clicklistener.itemClicked(view,getPosition());
            }
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
        //  System.out.println(dateString2); // 2011-
        //Log.i("ckm=>convertedDate",dateString2);
        return dateString2;
    }


}


