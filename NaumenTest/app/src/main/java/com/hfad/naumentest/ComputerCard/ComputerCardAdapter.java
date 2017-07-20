package com.hfad.naumentest.ComputerCard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.naumentest.R;

import java.util.List;

public class ComputerCardAdapter extends RecyclerView.Adapter<ComputerCardAdapter.ViewHolder> {

    private List<CardModel> mItems;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public TextView titleTv;
        PostItemListener mItemListener;
        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(android.R.id.text1); // text1 - дефолт

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CardModel item = getItem(getAdapterPosition()); // Получаем номер позиции
            this.mItemListener.onPostClick(item.getId()); // Действие на клик, получаем айди

            notifyDataSetChanged();
        }
    }

    public ComputerCardAdapter(Context context, List<CardModel> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public ComputerCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View postView = inflater.inflate(R.layout.items, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ComputerCardAdapter.ViewHolder holder, int position) {

        CardModel item = mItems.get(position);
        TextView textView = holder.titleTv;
        textView.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<CardModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private CardModel getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}
