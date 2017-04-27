package com.apexcomputerservice.legotracker.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apexcomputerservice.legotracker.DatabaseHelper;
import com.apexcomputerservice.legotracker.MyLongClickListener;
import com.apexcomputerservice.legotracker.R;
import com.apexcomputerservice.legotracker.model.Displays;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Chris on 4/23/2017.
 */

public class RVAdapterDisplay extends RecyclerView.Adapter<RVAdapterDisplay.DisplayViewHolder> {

    int selectedPos;
    private List<Displays> displays;
    String TAG = "Check";

    public class DisplayViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener{


        public CardView cv;
        public TextView displayType, weeksUp, resold, store, storeLocation;
        MyLongClickListener longClickListener;

        public DisplayViewHolder(View itemView){
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv_display);
            displayType = (TextView) itemView.findViewById(R.id.tvDisplayType);
            weeksUp = (TextView)itemView.findViewById(R.id.tvWeeksUp);
            resold = (TextView)itemView.findViewById(R.id.tvResold);
            store = (TextView) itemView.findViewById(R.id.tvStore);
            storeLocation = (TextView)itemView.findViewById(R.id.tvLocation);

            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setLongClickListener(MyLongClickListener longClickListener){
            this.longClickListener = longClickListener;
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


    private Context mContext;

    public RVAdapterDisplay(Context context, List<Displays> displays){
        this.displays = displays;
        this.mContext = context;
    }

    private Context getContext(){
        return mContext;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RVAdapterDisplay.DisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //Inflate custom layout
        View displayview = inflater.inflate(R.layout.item_display, parent, false);
        //Return a new holder instance
        DisplayViewHolder viewHolder = new DisplayViewHolder(displayview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVAdapterDisplay.DisplayViewHolder holder, int position){
        int chainId = displays.get(position).getChainid();
        int storeId = displays.get(position).getStoreid();
        int legoId = displays.get(position).getTypeid();
        int brandId = displays.get(position).getBrandId();
        int qty = displays.get(position).getCurrentQty();
        int weeks = displays.get(position).getWeeksUp();
        int resoldTimes = displays.get(position).getResold();
        long placementDate = displays.get(position).getPlacementDate();
        DatabaseHelper helper = new DatabaseHelper(getContext());
            String chainName = String.valueOf(helper.getSingleChain(chainId));
            String storeNum = String.valueOf(helper.getSingleStoreNumber(storeId));
        holder.store.setText(chainName + " " + storeNum);
            String storeLoc = String.valueOf(helper.getSingleStoreLocation(storeId));
        holder.storeLocation.setText(storeLoc);
            String brandName = String.valueOf(helper.getSingleBrand(brandId));
            String legoDescription = String.valueOf(helper.getSingleLegoType(legoId));
                if (qty > 1) {
                    String legoType = brandName + " " + legoDescription + " (" + qty + ")";
                    holder.displayType.setText(legoType);
                } else {
                    String legoType = brandName + " " + legoDescription;
                    holder.displayType.setText(legoType);
                }
        if(resoldTimes >0) {
            String resoldText = "Resold " + resoldTimes + "x";
            holder.resold.setText(resoldText);
        } else{
            String resoldText = "Resold Never";
            holder.resold.setText(resoldText);
        }

        if (weeks < 0 ){
            String weeksUpText =  "Up " + weeks + " weeks";
            holder.weeksUp.setText(weeksUpText);
        } else{
            Calendar currentDate = Calendar.getInstance();
            long currentDateUnix = currentDate.getTimeInMillis()/1000;
            long unixDifference = currentDateUnix - placementDate;
            long daysDifference = unixDifference/86400;
            long weeksDifference = daysDifference/7;
            String weeksUpText = "Up " + weeksDifference + " weeks";
            holder.weeksUp.setText(weeksUpText);
        }

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos=pos;
            }
        });
    }

    @Override
    public int getItemCount(){
        return displays.size();
    }

    //TODO Delete & Edits
}
