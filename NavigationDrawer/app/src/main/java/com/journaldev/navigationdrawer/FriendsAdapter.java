package com.journaldev.navigationdrawer;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.journaldev.navigationdrawer.API.MyFriendModel;
import com.squareup.picasso.Picasso;

import java.util.List;


class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private List<MyFriendModel> friends;
    private TextView userName;
    private FriendDeleteItemListener friendDeleteItemListener;
    FriendsAdapter(List<MyFriendModel> friends, FriendDeleteItemListener friendDeleteItemListener) {
        this.friends = friends;
        this.friendDeleteItemListener = friendDeleteItemListener;
        Log.d("testtest", "constructor");
    }

    // inner class to hold a reference to each item of RecyclerView
    // Предоставляет ссылку на представления, используемые в RecyclerView
    // Класс ViewHolder сообщает, с какими данными должен работать адаптер
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cardView;
        private ImageView user_icon;
        FriendDeleteItemListener itemListener;
        // Так как мы отображаем карточки в RecycleView, определяем их тут
        public ViewHolder(CardView v, FriendDeleteItemListener itemListener) { // Каждый объект ViewHolder отображает CardView
            super(v); // Необходимо вызвать конструктор суперкласса, тк СК ViewHolder включает
            // метаданные, необходимые для правильной работы адаптера
            // (позиция варианта в RecyclerView, например)

            cardView = v;
            user_icon = (ImageView) cardView.findViewById(R.id.user_icon); // да твою мать, что за кал
            this.itemListener = itemListener;
            ImageView deleteIcon = (ImageView) cardView.findViewById(R.id.delete_friend);
            deleteIcon.setOnClickListener(this);
            // определяем всё тут, позже короче
            Log.d("testtest", "view holder");
        }

        @Override
        public void onClick(View v) {
            // MyFriendModel
            this.itemListener.onDeleteClick(getItem(getAdapterPosition()).getKey());
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_friends, parent, false);
        return new ViewHolder(cv, this.friendDeleteItemListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("testtest", "onBind");
        CardView cardView = holder.cardView; // Получаем карточку
        MyFriendModel friendsItems = friends.get(position);
        userName = (TextView) cardView.findViewById(R.id.user_name);
        // TODO: ну вот тут чёт не работает
        userName.setText(friendsItems.getValue());

        // Заполняем поля
        Context context = holder.user_icon.getContext();
        //imageLoad(item, con);
        String url = "https://penok.lisenkosoft.ru/json/user_icon/" + friendsItems.getKey();
        Log.d("testtest", friendsItems.getValue());
        // holder.user_icon = (ImageView) cardView.findViewById(R.id.user_icon);
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = 50 * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        int px = (int) dp;
        Picasso.with(context)
                .load(url)
                .error(R.drawable.non_pic)
                .placeholder(R.drawable.non_pic)
                .resize(px, px)
                .into(holder.user_icon);
        Log.d("testtest", "onBind end");
    }

    void updateFriends(List<MyFriendModel> friends) {
        this.friends = friends;
        notifyDataSetChanged();
        Log.d("testtest", "notified");
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  friends == null ? 0 : friends.size();
    }

    private MyFriendModel getItem(int adapterPosition){
        return friends.get(adapterPosition);
    }

    public interface FriendDeleteItemListener {
        void onDeleteClick(int id);
    }
}
