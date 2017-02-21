package com.apexcomputerservice.legotracker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apexcomputerservice.legotracker.model.Chain;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Chris on 2/18/2017.
 */

public class RVAdapterChain extends RecyclerView.Adapter<RVAdapterChain.ChainViewHolder> {



   /* DatabaseHelper myDatabaseHelper = new DatabaseHelper(RVAdapterChain.this);
    myDatabaseHelper.openReadableDB();
    List<Chain> chains = myDatabaseHelper.getChains();
    myDatabaseHelper.closeDB();

    */

   public List<Chain> chains;


    public RVAdapterChain(List<Chain> chains){

        this.chains=chains;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ChainViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){



        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chain,viewGroup,false);
        ChainViewHolder cvh = new ChainViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ChainViewHolder chainViewHolder, int i){
        chainViewHolder.chainName.setText(chains.get(i).getChainName());
    }

    @Override
    public int getItemCount(){
        return chains.size();
    }


    public static class ChainViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView chainName;


        ChainViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_chain);
            chainName = (TextView)itemView.findViewById(R.id.chain_name);
        }
    }
}


