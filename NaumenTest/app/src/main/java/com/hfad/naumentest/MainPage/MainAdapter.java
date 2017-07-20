package com.hfad.naumentest.MainPage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hfad.naumentest.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Item> computers;
    private Context mContext;
    private MainItemListener mItemListener;
    private int computerId;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public TextView textComputer;
        public TextView textCompany;
        public TextView cmpnyName; // Это для строки с надписью "Company name"
        MainItemListener mItemListener;

        // Тут мы инициализируем всё связанное с будущим элементом
        public ViewHolder(View itemView, MainItemListener postItemListener) {
            super(itemView);
            textCompany = (TextView) itemView.findViewById(R.id.companyText);
            textComputer = (TextView) itemView.findViewById(R.id.computerText);
            cmpnyName = (TextView) itemView.findViewById(R.id.cmpnyName);
            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
            // linearLayout = itemView;
        }

        @Override
        public void onClick(View view) {
            Item item = getItem(getAdapterPosition());
            this.mItemListener.onMainClick(item.getId());

            notifyDataSetChanged();
        }
    }

    public MainAdapter(Context context, List<Item> posts, MainItemListener itemListener) {
        computers = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    // Заполняет наш элемент разметки и создаёт holder
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()) // Макет для каждого элемента
                .inflate(R.layout.my_main_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, this.mItemListener);
        return viewHolder;
    }

    // Заполняем виджет View из элемента списка с номером position
    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        Item item = computers.get(position);
        TextView computerName = holder.textComputer;
        TextView companyName = holder.textCompany;
        TextView cmpnName = holder.cmpnyName;

        computerName.setText(item.getName());
        if (item.getCompany() != null){
            companyName.setText(item.getCompany().getName());
            cmpnName.setText("Company name");
        }
        else {
            //companyName.setText("");
            //cmpnName.setText("");
            cmpnName.setVisibility(View.GONE);
            companyName.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return computers.size();
    }

    public void updateAnswers(List<Item> items) {
        computers = items;
        notifyDataSetChanged();
    }

    private Item getItem(int adapterPosition) {
        return computers.get(adapterPosition);
    }

    public interface MainItemListener {
        void onMainClick(long id);
    }
}
