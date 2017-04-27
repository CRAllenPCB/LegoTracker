package com.apexcomputerservice.legotracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apexcomputerservice.legotracker.adapters.RVAdapterDisplay;
import com.apexcomputerservice.legotracker.model.Displays;

import java.util.ArrayList;
import java.util.List;


public class DisplayDetails extends Fragment {

    DatabaseHelper helper;
    List<Displays> displaysList;
    RecyclerView mRecyclerView;
    RVAdapterDisplay mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private final static String TABLE_NAME = "displays";
    boolean emptyTable;
    TextView emptyTextView;
    String TAG = "Tag";


    public DisplayDetails(){
        //Empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)){
            //recreate fragment here
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setAllowOptimization(false);
            ft.detach(this).attach(this).commitAllowingStateLoss();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        //Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_display_details, container, false);
        //populateView
        //Check that table is populated
        helper = new DatabaseHelper(getContext());
        emptyTable = helper.isTableEmpty(TABLE_NAME);
        helper.close();
        if (emptyTable) {
            emptyTextView = (TextView) view.findViewById(R.id.tvBlank);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView = (TextView) view.findViewById(R.id.tvBlank);
            emptyTextView.setVisibility(View.GONE);

            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
            mRecyclerView.setHasFixedSize(true);

            displaysList = new ArrayList<Displays>();
            displaysList = helper.getAllDisplays();
            helper.close();

            mAdapter = new RVAdapterDisplay(getActivity(), displaysList);
            mRecyclerView.setAdapter(mAdapter);

            mLayoutManger = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManger);

        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_display_details);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(DisplayDetails.this.getActivity(),AddDisplay.class));
                Intent intent = new Intent(DisplayDetails.this.getActivity(), AddDisplay.class);
                startActivityForResult(intent, 10001);
            }
        });
        return view;


    }


}
