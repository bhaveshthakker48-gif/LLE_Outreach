package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.PadanashaworkerData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AshworkerAdapter extends RecyclerView.Adapter<AshworkerAdapter.UserViewHolder> {

    public Context mContext;
    int type = 0;
    ArrayList<PadanashaworkerData> data;

    public AshworkerAdapter(Context mContext, ArrayList<PadanashaworkerData> data) {
        this.mContext = mContext;
        this.data = data;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.healthworkerlist,viewGroup,false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {

        PadanashaworkerData padanashaworkerData = data.get(i);
        userViewHolder.txtnameanm.setText(padanashaworkerData.getAnmname());
        userViewHolder.txtmobileanm.setText(padanashaworkerData.getAnmmobileno());
        userViewHolder.txtnameasha.setText(padanashaworkerData.getAshaworkername());
        userViewHolder.txtmobileasha.setText(padanashaworkerData.getAshaworkermobile());
        userViewHolder.txtgpname.setText(padanashaworkerData.getGpname());
        userViewHolder.txtphc.setText(padanashaworkerData.getPhc());
        userViewHolder.txtsubcentr.setText(padanashaworkerData.getSubcenter());
        userViewHolder.txtVillage.setText(padanashaworkerData.getVillage());
        userViewHolder.txtpada.setText(padanashaworkerData.getPada());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtnameanm,txtmobileanm,txtnameasha,txtmobileasha,txtgpname;
        TextView txtphc,txtsubcentr,txtpada,txtVillage;
        private final Context cntx;


        public UserViewHolder(View itemView) {
            super(itemView);
            cntx=itemView.getContext();
            itemView.setOnClickListener(this);

            txtnameanm = (TextView)itemView.findViewById(R.id.txtnameanm);
            txtmobileanm = (TextView)itemView.findViewById(R.id.txtmobileanm);
            txtnameasha = (TextView)itemView.findViewById(R.id.txtnameasha);
            txtmobileasha = (TextView)itemView.findViewById(R.id.txtmobileasha);
            txtgpname = (TextView)itemView.findViewById(R.id.txtgpname);

            txtphc = (TextView)itemView.findViewById(R.id.txtphc);
            txtsubcentr = (TextView)itemView.findViewById(R.id.txtsubcentr);
            txtpada = (TextView)itemView.findViewById(R.id.txtpada);
            txtVillage = (TextView)itemView.findViewById(R.id.txtVillage);



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

