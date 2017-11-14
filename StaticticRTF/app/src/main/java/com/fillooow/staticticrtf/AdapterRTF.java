package com.fillooow.staticticrtf;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fillow on 11.11.2017.
 */

public class AdapterRTF extends RecyclerView.Adapter<AdapterRTF.ViewHolder> {
    private ArrayList<String> chars;
    private ArrayList<Integer> counters;

    public AdapterRTF(ArrayList<String> chars, ArrayList<Integer> counters){
        this.chars = chars;
        this.counters = counters;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_result, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView charsTV = (TextView) holder.cardView.findViewById(R.id.chars);
        if(!chars.get(position).equals(" "))
            charsTV.setText(chars.get(position));
        else
            charsTV.setText("Пробел");
        TextView counterTV = (TextView) holder.cardView.findViewById(R.id.counter);
        counterTV.setText(counters.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return chars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
}
