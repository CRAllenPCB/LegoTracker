package com.apexcomputerservice.legotracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class DisplayDetails extends Fragment {

    public DisplayDetails(){
        //Empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view;
        //Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_display_details, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_display_details);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(DisplayDetails.this.getActivity(),AddDisplay.class));
            }
        });
        return view;

    }


}
