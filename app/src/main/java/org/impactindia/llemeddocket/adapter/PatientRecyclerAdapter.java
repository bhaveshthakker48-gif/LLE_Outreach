package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.listener.RecyclerViewClickListener;
import org.impactindia.llemeddocket.pojo.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientRecyclerAdapter extends RecyclerView.Adapter<PatientRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Patient> patientList;
    private RecyclerViewClickListener listItemClickListener;
    private SimpleDateFormat modifiedDF;
    private SimpleDateFormat shortDF;

    public PatientRecyclerAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
        modifiedDF = new SimpleDateFormat(AttributeSet.Constants.MODIFIED_DATE_FORMAT);
        shortDF = new SimpleDateFormat(AttributeSet.Constants.SHORT_DATE_FORMAT);
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_recycler_item, parent, false);
        // Set the view to the ViewHolder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.lblRegNo.setText(patient.getRegNo());
        holder.lblName.setText(patient.getfName() + " " + patient.getlName());
        holder.lblTel.setText(isEmpty(patient.getMobileNo()) ? isEmpty(patient.getResidenceNo()) ? "" : patient.getResidenceNo() : patient.getMobileNo());
        if (!isEmpty(patient.getLastVisitDate())) {
            try {
                Date date = modifiedDF.parse(patient.getLastVisitDate());
                holder.lblLastVisit.setText(shortDF.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean isEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public void setOnItemClickListener(RecyclerViewClickListener listener) {
        listItemClickListener = listener;
    }

    // Create the ViewHolder class to keep references to your com.pretiumpharma.pretiumpharmasfe.views
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView lblRegNo, lblName, lblTel, lblLastVisit;


        public ViewHolder(View itemView) {
            super(itemView);
            lblRegNo = (TextView) itemView.findViewById(R.id.lblRegNo);
            lblName = (TextView) itemView.findViewById(R.id.lblName);
            lblTel = (TextView) itemView.findViewById(R.id.lblTel);
            lblLastVisit = (TextView) itemView.findViewById(R.id.lblLastVisit);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listItemClickListener != null) {
                listItemClickListener.onListItemClicked(patientList.get(getLayoutPosition()));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (listItemClickListener != null) {
                listItemClickListener.onListItemLongClicked(patientList.get(getLayoutPosition()));
            }
            return true;
        }
    }
}
