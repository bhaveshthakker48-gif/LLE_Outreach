package org.impactindia.llemeddocket.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.listener.RecyclerViewClickListener;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.MedicalDetailsDAO;
import org.impactindia.llemeddocket.pojo.MedicalDetails;
import org.impactindia.llemeddocket.pojo.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CampPatientRecyclerAdapter extends RecyclerView.Adapter<CampPatientRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Patient> patientList;
    private RecyclerViewClickListener listItemClickListener;
    private SimpleDateFormat reverseShortDF;
    private SimpleDateFormat shortDF;
    private MedicalDetailsDAO medicalDetailsDAO;

    public CampPatientRecyclerAdapter(Context context, List<Patient> patientList, MedicalDetailsDAO medicalDetailsDAO) {
        this.context = context;
        this.patientList = patientList;
        reverseShortDF = new SimpleDateFormat(AttributeSet.Constants.REVERSE_SHORT_DATE);
        shortDF = new SimpleDateFormat(AttributeSet.Constants.SHORT_DATE_FORMAT);
        this.medicalDetailsDAO = medicalDetailsDAO;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.camp_patient_recycler_item, parent, false);
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
                Date date = reverseShortDF.parse(patient.getLastVisitDate());
                holder.lblLastVisit.setText(shortDF.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        boolean medicalDetailsExists = false;
        try {
            medicalDetailsExists = medicalDetailsDAO.exists(MedicalDetails.PATIENT_ID, patient.getPatientId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        if (medicalDetailsExists) {
            holder.imgPlus.setImageResource(R.mipmap.view_medical_details);
        } else {
            holder.imgPlus.setImageResource(R.mipmap.add_medical_details);
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
        public ImageView imgPlus;

        public ViewHolder(View itemView) {
            super(itemView);
            lblRegNo = (TextView) itemView.findViewById(R.id.lblRegNo);
            lblName = (TextView) itemView.findViewById(R.id.lblName);
            lblTel = (TextView) itemView.findViewById(R.id.lblTel);
            lblLastVisit = (TextView) itemView.findViewById(R.id.lblLastVisit);
            imgPlus = itemView.findViewById(R.id.imgPlus);

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
