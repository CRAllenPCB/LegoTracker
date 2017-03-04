package com.apexcomputerservice.legotracker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    public class ChainViewHolder extends RecyclerView.ViewHolder{

        public CardView cv;
        public TextView chainNameTextView;



        public ChainViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_chain);
            chainNameTextView = (TextView)itemView.findViewById(R.id.chain_name);
        }
    }

    private List<Chain> chains;
    private Context context;

    public String TAG="SQLInputCheck";


    public RVAdapterChain(Context context,List<Chain> chains){
        this.chains=chains;
        this.context=context;
    }

    private Context getContext(){
        return context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RVAdapterChain.ChainViewHolder onCreateViewHolder(ViewGroup viewGroup, int position){
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate custom layout
        View chainview = inflater.inflate(R.layout.item_chain, viewGroup, false);
        //Return a new holder instance
        ChainViewHolder cvh = new ChainViewHolder(chainview);
        return cvh;
     }

    @Override
    public void onBindViewHolder(RVAdapterChain.ChainViewHolder chainViewHolder, int position){
        //Get the data model based on position
        Chain chain = chains.get(position);
            Log.v(TAG,"VH position is" + String.valueOf(position));
        //Set item views based on your view and data model
        TextView chainTextView = chainViewHolder.chainNameTextView;
        chainTextView.setText(chain.getChainName());
    }

    @Override
    public int getItemCount(){
        return chains.size();
    }



}


