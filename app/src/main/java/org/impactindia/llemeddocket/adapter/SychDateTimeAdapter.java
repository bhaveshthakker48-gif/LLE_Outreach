package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.SyncData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SychDateTimeAdapter  extends RecyclerView.Adapter<SychDateTimeAdapter.UserViewHolder> {

    public Context mContext;
    int type = 0;
    ArrayList<SyncData> data;

    public SychDateTimeAdapter(Context mContext, ArrayList<SyncData> data) {
        this.mContext = mContext;
        this.data = data;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_synchtimeitmes,viewGroup,false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        SyncData syncData = data.get(i);
        userViewHolder.txtdatetime.setText(syncData.getSynchdate());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtdatetime;
        private final Context cntx;


        public UserViewHolder(View itemView) {
            super(itemView);
            cntx=itemView.getContext();
            itemView.setOnClickListener(this);
            txtdatetime = (TextView)itemView.findViewById(R.id.txtdatetime);
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


