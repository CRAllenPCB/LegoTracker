package com.apexcomputerservice.legotracker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.apexcomputerservice.legotracker.model.Chain;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Chris on 2/18/2017.
 */

public class RVAdapterChain extends RecyclerView.Adapter<RVAdapterChain.ChainViewHolder> {

    int selectedPos;

    public class ChainViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener {

        public CardView cv;
        public TextView chainNameTextView;
        MyLongClickListener longClickListener;




        public ChainViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_chain);
            chainNameTextView = (TextView)itemView.findViewById(R.id.chain_name);

            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        //Context Menu

        public void setLongClickListener(MyLongClickListener longClickListener){
            this.longClickListener=longClickListener;
        }


        @Override
        public boolean onLongClick(View v){
            this.longClickListener.onLongClick(getLayoutPosition());
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            menu.add(0,0,0,"Edit");
            menu.add(0,1,0,"Delete");

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
    public RVAdapterChain.ChainViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate custom layout
        View chainview = inflater.inflate(R.layout.item_chain, parent, false);
        //Return a new holder instance
        ChainViewHolder viewHolder = new ChainViewHolder(chainview);
        return viewHolder;
     }

    @Override
    public void onBindViewHolder(RVAdapterChain.ChainViewHolder holder, int position){
       holder.chainNameTextView.setText(chains.get(position).getChainName());

        holder.setLongClickListener(new MyLongClickListener(){
            @Override
            public void onLongClick(int pos){
                selectedPos=pos;
            }

        });



       /*
        //Get the data model based on position
        Chain chain = chains.get(position);
            Log.v(TAG,"VH position is" + String.valueOf(position));
        //Set item views based on your view and data model
        TextView chainTextView = holder.chainNameTextView;
        chainTextView.setText(chain.getChainName());
        */
    }

    @Override
    public int getItemCount(){
        return chains.size();
    }

    //CONTEXT MENU


}


