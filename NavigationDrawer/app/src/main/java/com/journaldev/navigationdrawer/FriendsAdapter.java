package com.journaldev.navigationdrawer;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.journaldev.navigationdrawer.API.APIUtils;
import com.journaldev.navigationdrawer.API.Model;
import com.journaldev.navigationdrawer.API.PenyokService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private List<Integer> friendsItems;
    private List<String> friendsNames;
    private Context context;
    private PenyokService friendsAdapterService;
    private String friendsAdapterSid;
    private TextView userName;

    FriendsAdapter(List<Integer> items, List<String> names, Context context, String sid) {
        this.friendsItems = items;
        this.friendsNames = names;
        this.context = context;
        this.friendsAdapterSid = sid;
    }

    // inner class to hold a reference to each item of RecyclerView
    // Предоставляет ссылку на представления, используемые в RecyclerView
    // Класс ViewHolder сообщает, с какими данными должен работать адаптер
    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView user_icon;
        // Так как мы отображаем карточки в RecycleView, определяем их тут
        public ViewHolder(CardView v) { // Каждый объект ViewHolder отображает CardView
            super(v); // Необходимо вызвать конструктор суперкласса, тк СК ViewHolder включает
            // метаданные, необходимые для правильной работы адаптера
            // (позиция варианта в RecyclerView, например)

            cardView = v;
            user_icon = (ImageView) cardView.findViewById(R.id.user_icon); // да твою мать, что за кал
            // определяем всё тут, позже короче
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_friends, parent, false);
        return new ViewHolder(cv);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView; // Получаем карточку
        Integer item = friendsItems.get(position);
        userName = (TextView) cardView.findViewById(R.id.user_name);
        // TODO: ну вот тут чёт не работает
        if (friendsNames.get(position) != null)
            userName.setText(friendsNames.get(position));
        else
            userName.setText("хмм");

        // Заполняем поля
        Context con = holder.user_icon.getContext();
        //imageLoad(item, con);
        String url = "https://penok.lisenkosoft.ru/json/user_icon/" + item;
        Picasso.with(con)
                .load(url)
                .error(R.drawable.non_pic)
                .into(holder.user_icon);




    }

    void updateFriends(List<Integer> items, List<String> names) {
        friendsItems = items;
        friendsNames = names;
        notifyDataSetChanged();
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  friendsItems == null ? 0 : friendsItems.size();
    }

    /*private void imageLoad(Integer id, Context contextImage) {

        String url = "https://penok.lisenkosoft.ru/json/user_icon/" + id;
        Picasso.with(contextImage)
                .load(url)
                .error(R.drawable.non_pic)
                .into(user_icon);
    }*/

}
