package com.journaldev.navigationdrawer;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.journaldev.navigationdrawer.API.MyStatsModel;

import java.util.List;


class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    private List<MyStatsModel> myStatsItems;

    StatsAdapter(List<MyStatsModel> msItems) {
        this.myStatsItems = msItems;
        Log.d("testtest", "stats constructor");
    }

    // inner class to hold a reference to each item of RecyclerView
    // Предоставляет ссылку на представления, используемые в RecyclerView
    // Класс ViewHolder сообщает, с какими данными должен работать адаптер
    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        // Так как мы отображаем карточки в RecycleView, определяем их тут
        public ViewHolder(CardView v) { // Каждый объект ViewHolder отображает CardView
            super(v); // Необходимо вызвать конструктор суперкласса, тк СК ViewHolder включает
            // метаданные, необходимые для правильной работы адаптера
            // (позиция варианта в RecyclerView, например)
            cardView = v;

            Log.d("testtest", "stats viewholder");
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_stats, parent, false);
        return new ViewHolder(cv);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView; // Получаем карточку
        MyStatsModel item = myStatsItems.get(position);

        // Заполняем поля
        TextView keyText = (TextView) cardView.findViewById(R.id.key_text);
        keyText.setText(item.getKey());
        TextView valueText = (TextView) cardView.findViewById(R.id.value_text);
        if (item.getValue() == null)
            valueText.setText(R.string.set_name);
        else {
            if(item.getValue() instanceof Number) {
                Double doub = (Double) item.getValue();
                Integer i = doub.intValue();
                valueText.setText(i.toString());
            } else
                valueText.setText(item.getValue().toString());
        }

        Log.d("testtest", "stats end adapter");
    }

    void updateStats(List<MyStatsModel> items) {
        myStatsItems = items;
        notifyDataSetChanged();
        Log.d("testtest", "stats notified");
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  myStatsItems == null ? 0 : myStatsItems.size();
    }
}
