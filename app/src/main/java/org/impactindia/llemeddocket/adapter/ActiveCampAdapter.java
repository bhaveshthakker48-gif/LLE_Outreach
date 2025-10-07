package org.impactindia.llemeddocket.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.ui.activity.BaseActivity;
import org.impactindia.llemeddocket.ui.activity.DashboardActivityOutPro;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ActiveCampAdapter extends RecyclerView.Adapter<ActiveCampAdapter.UserViewHolder> {

    public Context mContext;
    private static Clicklistener clicklistener;
    int type = 0;
    ArrayList<OutreachPlanData> data;

    public ActiveCampAdapter(Context mContext, ArrayList<OutreachPlanData> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.outreach_itemdata,viewGroup,false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    public void setClicklistener(Clicklistener clicklistener)
    {
        this.clicklistener=clicklistener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final OutreachPlanData outreachPlanData = data.get(position);

        holder.txtcampName.setText("CampName : " + outreachPlanData.getCamp_Location());
        holder.txt_state.setText("State: " + outreachPlanData.getState());
        holder.txtdistric.setText("Distric: " + outreachPlanData.getDistric());
        holder.txttaluka.setText("Taluka: " + outreachPlanData.getTaluka());
        holder.txtoutreachfrom.setText("From: " + formatDate(outreachPlanData.getOutreach_From()));
        holder.txtoutreachto.setText("To: " + formatDate(outreachPlanData.getOutreach_To()));

        holder.llmessage_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DashboardActivityOutPro.class);
                SharedPreference.save("OutreachFrom",data.get(position).getOutreach_From());
                SharedPreference.save("OutreachTo",data.get(position).getOutreach_To());
                SharedPreference.save("Campname",outreachPlanData.getCamp_Location());
                SharedPreference.save("CAMPID",data.get(position).getCampID());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                //((AppCompatActivity)mContext).finish();
            }
        });


    }

    private String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate; // return original if parsing fails
        }
    }


    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtcampName,txt_state,txtdistric,txttaluka,txtoutreachfrom,txtoutreachto;
        private final Context cntx;
        LinearLayout llmessage_view;

        public UserViewHolder(View itemView) {
            super(itemView);
            cntx=itemView.getContext();
            itemView.setOnClickListener(this);
            txtcampName = (TextView)itemView.findViewById(R.id.txtcampName);
            txt_state = (TextView)itemView.findViewById(R.id.txt_state);
            txtdistric = (TextView)itemView.findViewById(R.id.txtdistric);
            txttaluka = (TextView)itemView.findViewById(R.id.txttaluka);
            txtoutreachfrom = (TextView)itemView.findViewById(R.id.txtoutreachfrom);
            txtoutreachto = (TextView)itemView.findViewById(R.id.txtoutreachto);
            llmessage_view = itemView.findViewById(R.id.llmessage_view);
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

}

