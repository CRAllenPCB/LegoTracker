package com.apexcomputerservice.legotracker.adapters;

import android.content.Context;
import android.provider.ContactsContract;
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

import com.apexcomputerservice.legotracker.DatabaseHelper;
import com.apexcomputerservice.legotracker.MyLongClickListener;
import com.apexcomputerservice.legotracker.R;
import com.apexcomputerservice.legotracker.model.Chain;
import com.apexcomputerservice.legotracker.model.Store;
import com.facebook.stetho.inspector.protocol.module.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 3/7/17.
 */


public class RVAdapterStore extends RecyclerView.Adapter<RVAdapterStore.StoreViewHolder> {

    int selectedPos;

    public class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener {

        public CardView cv;
        public TextView storeNameTextView , chainNameTextView;
        MyLongClickListener longClickListener;




        public StoreViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_store);
            storeNameTextView = (TextView)itemView.findViewById(R.id.store_name);
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

    private List<Store> stores;
    private Context context;

    public String TAG="SQLInputCheck";


    public RVAdapterStore(Context context,List<Store> stores){
        this.stores=stores;
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
    public RVAdapterStore.StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate custom layout
        View storeview = inflater.inflate(R.layout.item_store, parent, false);
        //Return a new holder instance
        StoreViewHolder viewHolder = new StoreViewHolder(storeview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVAdapterStore.StoreViewHolder holder, int position){
        holder.storeNameTextView.setText(stores.get(position).getStoreNumber());
        int chainId = stores.get(position).getChainid();
        Chain chain = new Chain();
        DatabaseHelper helper = new DatabaseHelper(getContext());
        String chainName = String.valueOf(helper.getSingleChain(chainId)) + " ";
        holder.chainNameTextView.setText(chainName);

        holder.setLongClickListener(new MyLongClickListener(){
            @Override
            public void onLongClick(int pos){
                selectedPos=pos;
            }

        });

        /*
        Have to get Chain id also - above

         */
    }

    @Override
    public int getItemCount(){
        return stores.size();
    }

    //Delete Chain
    public void removeStore(){
        //Get ID
        Store s = stores.get(selectedPos);
        int id =s.getStoreid();

        //Delete it from db
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.openWriteableDB();
        //*****Add if condition here to check first for stores and displays associated with Chain
        // Delete from database
        helper.deleteChain(id);
        //Remove from arraylist
        stores.remove(selectedPos);
        helper.closeDB();
        this.notifyItemRemoved(selectedPos);
    }


}