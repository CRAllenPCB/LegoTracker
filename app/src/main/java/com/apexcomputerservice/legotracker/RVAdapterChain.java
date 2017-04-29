package com.apexcomputerservice.legotracker;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.apexcomputerservice.legotracker.model.Displays;
import com.apexcomputerservice.legotracker.model.Store;
import com.facebook.stetho.inspector.protocol.module.Database;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Chris on 2/18/2017.
 */

public class RVAdapterChain extends RecyclerView.Adapter<RVAdapterChain.ChainViewHolder> {

    int selectedPos;
    private List<Chain> chains;
    private Context context;
    int chainid;

    //DatabaseHelper helper;
    //final DatabaseHelper helper = new DatabaseHelper(context);

    public class ChainViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener {

        public CardView cv;
        public TextView chainNameTextView;
        MyLongClickListener longClickListener;

        public ChainViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_chain);
            chainNameTextView = (TextView) itemView.findViewById(R.id.chain_name);

            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        //Context Menu

        public void setLongClickListener(MyLongClickListener longClickListener) {
            this.longClickListener = longClickListener;
        }

        @Override
        public boolean onLongClick(View v) {
            this.longClickListener.onLongClick(getLayoutPosition());
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 0, 0, "Edit");
            menu.add(0, 1, 0, "Delete");
        }
    }

    public RVAdapterChain(Context context, List<Chain> chains) {
        this.chains = chains;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RVAdapterChain.ChainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate custom layout
        View chainview = inflater.inflate(R.layout.item_chain, parent, false);
        //Return a new holder instance
        ChainViewHolder viewHolder = new ChainViewHolder(chainview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVAdapterChain.ChainViewHolder holder, int position) {
        holder.chainNameTextView.setText(chains.get(position).getChainName());

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos = pos;
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
    public int getItemCount() {
        return chains.size();
    }

    //TODO Edit Chain
    //Delete Chain
    public void removeChain() {
        //Get ID
        Chain c = chains.get(selectedPos);
        chainid = c.getChainid();
        List<Store> stores = null;

        //Delete it from db
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.openWriteableDB();
        //*****Add if condition here to check first for stores and displays associated with Chain
        // Delete from database
        stores = helper.getChainStores(chainid);
        final int storesEffected = stores.size();
        if (storesEffected > 0) {
            List<Displays> displays = null;
            displays = helper.getDisplaysByChainId(chainid);
            final int displaysEffected = displays.size();
           // helper.closeDB();
            AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(context);
            alertBuilder1.setTitle("Delete Chain");
            if (displaysEffected > 0) {
                alertBuilder1.setMessage("This chain has " + storesEffected + " stores and " + displaysEffected + " displays that will also be deleted. Continue?");
            } else {
                alertBuilder1.setMessage("This chain has " + storesEffected + " stores that will also be deleted. Continue?");
            }
            alertBuilder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //continue with delete

                    proceed(chainid, storesEffected, displaysEffected);

                }
            });
            alertBuilder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                    dialog.cancel();
                }
            });
            alertBuilder1.setIcon(android.R.drawable.ic_dialog_alert);
            AlertDialog alert1 = alertBuilder1.create();
            alert1.show();
        } else {
            proceed(chainid, 0, 0);
        }
        helper.closeDB();
    }



    public void proceed(int chainId, int stores, int displays) {
        DatabaseHelper helper2 = new DatabaseHelper(context);
        helper2.openWriteableDB();
        helper2.deleteChain(chainId);
        if (stores > 0) {
            helper2.deleteStoreByChain(chainId);
        }
        if (displays > 0) {
            helper2.deleteDisplayByChain(chainId);
        }
        //Remove from arraylist
        chains.remove(selectedPos);
        helper2.closeDB();
        this.notifyItemRemoved(selectedPos);

    }

}


