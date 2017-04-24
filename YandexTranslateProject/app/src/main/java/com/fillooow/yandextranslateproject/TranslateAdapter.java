package com.fillooow.yandextranslateproject;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Fillow on 24.04.2017.
 */

public class TranslateAdapter extends RecyclerView.Adapter<TranslateAdapter.ViewHolder>{
    private String[] orText;
    private String[] desText;

    public TranslateAdapter (String[] orText, String[] desText) {
        this.orText = orText;
        this.desText = desText;
    }

    // Предоставляет ссылку на представления, используемые в RecyclerView
    // Класс ViewHolder сообщает, с какими данными должен работать адаптер
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        // Так как мы отображаем карточки в RecycleView, определяем их тут
        public ViewHolder(CardView v) { // Каждый объект ViewHolder отображает CardView
            super(v); // Необходимо вызвать конструктор суперкласса, тк СК ViewHolder включает
            // метаданные, необходимые для правильной работы адаптера
            // (позиция варианта в RecyclerView, например)
            cardView = v;
        }
    }

    @Override
    public TranslateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView lv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_translate, parent, false);
        return new ViewHolder(lv);
    }


    @Override
    public void onBindViewHolder(TranslateAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        TextView originalText = (TextView) cardView.findViewById(R.id.original_text);
        originalText.setText(orText[position]);
        TextView descriptionText = (TextView) cardView.findViewById(R.id.description_text);
        descriptionText.setText(desText[position]);
    }

    @Override
    public int getItemCount() {
        return orText.length;
    }
}
