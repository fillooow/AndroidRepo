package com.fillooow.staticticrtf;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Fillow on 11.11.2017.
 */

public class AdapterRTF extends RecyclerView.Adapter<AdapterRTF.ViewHolder> {
    String[] test = new String[3];

    public AdapterRTF(String[] test){
        this.test = test;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_result, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView tv = (TextView) holder.cardView.findViewById(R.id.testText);
        tv.setText(test[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
}
