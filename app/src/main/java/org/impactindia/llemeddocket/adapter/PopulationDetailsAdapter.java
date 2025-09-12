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

public class PopulationDetailsAdapter extends RecyclerView.Adapter<PopulationDetailsAdapter.UserViewHolder> implements Filterable {

    public Context mContext;
    int type = 0;
    ArrayList<PopMedicalData> data;
    ArrayList<PopMedicalData> datafiter;

    public PopulationDetailsAdapter(Context mContext, ArrayList<PopMedicalData> data) {
        this.mContext = mContext;
        this.data = data;
        this.datafiter = data;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.population_list_item,viewGroup,false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
            final PopMedicalData popMedicalData = data.get(i);
       // txtDateofdataenter,txthouseno,nameagegender,phcsubcentpadavillage

            if (!isEmpty(popMedicalData.getDateofexamination()))
            {
                Log.i("ckm=>date=>",popMedicalData.getDateofexamination());
                userViewHolder.txtDateofdataenter.setText("Date : " + getddmmyyyy(popMedicalData.getDateofexamination()));
            }

         userViewHolder.txthouseno.setText("House no : " + popMedicalData.getHouseholdno());
         userViewHolder.nameagegender.setText(popMedicalData.getFname()+ " " +popMedicalData.getMname() +  " "+popMedicalData.getLname() + ", " + popMedicalData.getGender() +", " +popMedicalData.getAge() + ", ");
         userViewHolder.phcsubcentpadavillage.setText("PHC : " + popMedicalData.getPhc() + ", " + "Subcenter : " + popMedicalData.getSubcentre() + ", " + "Pada : " +popMedicalData.getPada() + ", " +"Village : " + popMedicalData.getVillage());

         if (popMedicalData.getIsfresh().equals("0"))
         {
             userViewHolder.img_Editdata.setVisibility(View.VISIBLE);
         }
         else
         {
             userViewHolder.img_Editdata.setVisibility(View.GONE);
         }


        userViewHolder.img_Editdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("ckm=>tableId",popMedicalData.getId()+"");
                Intent i = new Intent(mContext, AddHouseholdActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EDITID",String.valueOf(popMedicalData.getId()));
                i.putExtras(bundle);
                mContext.startActivity(i);

            }
        });

        userViewHolder.img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, ViewDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EDITID",String.valueOf(popMedicalData.getId()));
                bundle.putString("GENDER",popMedicalData.getGender());
                i.putExtras(bundle);
                mContext.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
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
                    // here we are matching session name, topic, speaker & chairperson
                    for (PopMedicalData programme : datafiter) {
                        if ((programme.getPhc() != null && programme.getPhc().toLowerCase().contains(charString.toLowerCase()))
                                || (programme.getSubcentre() != null && programme.getSubcentre().toLowerCase().contains(charString.toLowerCase()))
                                || (programme.getHouseholdno() != null && programme.getHouseholdno().toLowerCase().contains(charString.toLowerCase()) )
                                || (programme.getPada() != null && programme.getPada().toLowerCase().contains(charString.toLowerCase()))
                                ||(programme.getDateofdata() != null && getddmmyyyy(programme.getDateofdata()).toLowerCase().contains(charString.toLowerCase()) )
                                || (programme.getVillage() != null && programme.getVillage().toLowerCase().contains(charString.toLowerCase())))
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

        TextView txtDateofdataenter,txthouseno,nameagegender,phcsubcentpadavillage;
        ImageView img_Editdata,img_view;
        private final Context cntx;

        public UserViewHolder(View itemView) {
            super(itemView);
            cntx=itemView.getContext();
            itemView.setOnClickListener(this);
            txtDateofdataenter = (TextView)itemView.findViewById(R.id.txtDateofdataenter);
            txthouseno = (TextView)itemView.findViewById(R.id.txthouseno);
            nameagegender = (TextView)itemView.findViewById(R.id.nameagegender);
            phcsubcentpadavillage = (TextView)itemView.findViewById(R.id.phcsubcentpadavillage);
            img_Editdata = (ImageView) itemView.findViewById(R.id.img_Editdata);
            img_view = (ImageView) itemView.findViewById(R.id.img_view);

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
        //  System.out.println(dateString2); // 2011-
        //Log.i("ckm=>convertedDate",dateString2);
        return dateString2;
    }

}
