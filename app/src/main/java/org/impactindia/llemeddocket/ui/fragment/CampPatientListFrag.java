package org.impactindia.llemeddocket.ui.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.CampPatientRecyclerAdapter;
import org.impactindia.llemeddocket.adapter.PatientRecyclerAdapter;
import org.impactindia.llemeddocket.listener.RecyclerViewClickListener;
import org.impactindia.llemeddocket.orm.CampPatientDAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.MedicalDetailsDAO;
import org.impactindia.llemeddocket.orm.PatientDAO;
import org.impactindia.llemeddocket.pojo.MedicalDetails;
import org.impactindia.llemeddocket.pojo.Patient;

import java.util.List;

public class CampPatientListFrag extends BaseFrag implements RecyclerViewClickListener, SearchView.OnQueryTextListener {

    private static final String MEDICAL_DETAILS_ADD_FRAG = "MedicalDetailsAddFrag";
    private static final String MEDICAL_DETAILS_FRAG = "MedicalDetailsFrag";

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private SQLiteDatabase db;
    private CampPatientDAO campPatientDAO;
    private MedicalDetailsDAO medicalDetailsDAO;
    private Bundle args;
    private List<Patient> patientList;
    private CampPatientRecyclerAdapter recyclerAdapter;
    private int patientCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        args = getArguments();
        db = new DatabaseHelper(getActivity()).getWritableDatabase();
        campPatientDAO = new CampPatientDAO(getActivity(), db);
        medicalDetailsDAO = new MedicalDetailsDAO(getActivity(), db);
        patientList = campPatientDAO.findAll("order by " + Patient.FNAME + " COLLATE NOCASE asc");
        patientCount = patientList.size();
        recyclerAdapter = new CampPatientRecyclerAdapter(getActivity(), patientList, medicalDetailsDAO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.frag_camp_patient_list, container, false);
        initViews(view, savedInstanceState);
        return view;
    }

    private void initViews(View view, Bundle savedInstanceState) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_camp_patient_list);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.camp_patient_opt_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        MenuItem menuPatientCount = menu.findItem(R.id.menuPatientCount);
        search.setVisible(true);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.menu_opt_search_by_name_or_number_hint));
        menuPatientCount.setTitle(String.format(getString(R.string.title_formatted_count), patientCount));
    }

    @Override
    public void onListItemClicked(Object object) {
        boolean medicalDataExists = false;
        Patient patient = (Patient) object;
        Bundle args = new Bundle();
        args.putLong(MedicalDetailsAddFrag.PATIENT_ID, patient.getPatientId());
        try {
            medicalDataExists = medicalDetailsDAO.exists(MedicalDetails.PATIENT_ID, patient.getPatientId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        if (medicalDataExists) {
            FragmentManager mgr = getActivity().getSupportFragmentManager();
            MedicalDetailsFrag medicalDetailsFrag = (MedicalDetailsFrag) mgr.findFragmentByTag(MEDICAL_DETAILS_FRAG);
            if (medicalDetailsFrag == null) {
                medicalDetailsFrag = new MedicalDetailsFrag();
            }
            medicalDetailsFrag.setArguments(args);
            FragmentTransaction transaction = mgr.beginTransaction();
            transaction.replace(R.id.content_frame, medicalDetailsFrag, MEDICAL_DETAILS_FRAG);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            FragmentManager mgr = getActivity().getSupportFragmentManager();
            MedicalDetailsAddFrag medicalDetailsAddFrag = (MedicalDetailsAddFrag) mgr.findFragmentByTag(MEDICAL_DETAILS_ADD_FRAG);
            if (medicalDetailsAddFrag == null) {
                medicalDetailsAddFrag = new MedicalDetailsAddFrag();
            }
            medicalDetailsAddFrag.setArguments(args);
            FragmentTransaction transaction = mgr.beginTransaction();
            transaction.replace(R.id.content_frame, medicalDetailsAddFrag, MEDICAL_DETAILS_ADD_FRAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }


    }

    @Override
    public void onListItemLongClicked(Object object) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (recyclerAdapter != null) {
            try {
                patientList = campPatientDAO.getSuggestionsByNameOrPhoneNo(s);
                recyclerAdapter.setPatientList(patientList);
                recyclerAdapter.notifyDataSetChanged();
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
