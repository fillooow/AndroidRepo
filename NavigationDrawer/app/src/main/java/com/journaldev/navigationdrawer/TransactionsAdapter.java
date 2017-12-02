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

import com.journaldev.navigationdrawer.API.TransactionsListModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    private List<TransactionsListModel> transactions;
    private List<String> authorNames;
    private List<String> payeeNames;
    private List<String> payerNames;

    TransactionsAdapter(List<TransactionsListModel> transactions) {
        this.transactions = transactions;
        Log.d("testtest", "constructor");
    }
    /*TransactionsAdapter(List<TransactionsListModel> transactions, List<String> authorNames,
                        List<String> payeeNames, List<String> payerNames) {
        this.transactions = transactions;
        this.authorNames = authorNames;
        this.payeeNames = payeeNames;
        this.payerNames = payerNames;
        Log.d("testtest", "constructor");
    }*/

    // inner class to hold a reference to each item of RecyclerView
    // Предоставляет ссылку на представления, используемые в RecyclerView
    // Класс ViewHolder сообщает, с какими данными должен работать адаптер
    //TODO: чекнуть, будет ли работать теперь, ибо раньше тут было static вместо public
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView user_icon;
        // Так как мы отображаем карточки в RecycleView, определяем их тут
        public ViewHolder(CardView v) { // Каждый объект ViewHolder отображает CardView
            super(v); // Необходимо вызвать конструктор суперкласса, тк СК ViewHolder включает
            // метаданные, необходимые для правильной работы адаптера
            // (позиция варианта в RecyclerView, например)

            cardView = v;
            //user_icon = (ImageView) cardView.findViewById(R.id.user_icon); // да твою мать, что за кал
            // определяем всё тут, позже короче
            Log.d("testtest", "view holder");
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_transactions_history, parent, false);
        return new ViewHolder(cv);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("testtest", "onBind");
        CardView cardView = holder.cardView; // Получаем карточку
        TransactionsListModel historyItems = transactions.get(position); // Дефолт респонс с сервера
        // В нём у нас id и инфа по транзакциям
        /*String authorItem = authorNames.get(position); // Достаём имя автора транзакции
        String payeeItem = payeeNames.get(position); // Достаём имя того, кто берёт деньги
        String payerItem = payerNames.get(position); // Достаём имя того, кто отдаёт деньги */

        TextView author = (TextView) cardView.findViewById(R.id.author_history);
        //if (authorItem == null)
            author.setText("id " + historyItems.getAuthor().toString());
       // else
            //author.setText("Author: " + authorItem);

        TextView payee = (TextView) cardView.findViewById(R.id.payee_history);
        //if (payeeItem == null)
            payee.setText("id " + historyItems.getPayee().toString());
        //else
            //payee.setText("Payee: " + payeeItem);

        TextView payer = (TextView) cardView.findViewById(R.id.payer_history);
        //if (payerItem == null)
            payer.setText("id " + historyItems.getPayer().toString());
        //else
            //payer.setText("Payer: " + payerItem);

        TextView count = (TextView) cardView.findViewById(R.id.count_history);
        count.setText(historyItems.getCount().toString() + " рублей");

        TextView time = (TextView) cardView.findViewById(R.id.time_history);
        String timeMillisecondsStr = historyItems.getTime().replaceAll("[^0-9]", "");
        // Получаем время в формате /Date.../
         // Удаляем всё, что не цифры
        long timeMilliseconds = Long.parseLong(timeMillisecondsStr);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTimeInMillis(timeMilliseconds);
        time.setText("Created: " +
                new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(timeCalendar.getTime()) +
                " at " + new SimpleDateFormat("HH:mm:ss", Locale.US).format(timeCalendar.getTime()));

        TextView date = (TextView) cardView.findViewById(R.id.time_limit_history);
        if (historyItems.getDate() != null) {
            String dateMillisecondsStr = historyItems.getDate().replaceAll("[^0-9]", "");
            // Получаем время в формате /Date.../
            // Удаляем всё, что не цифры
            long dateMilliseconds = Long.parseLong(dateMillisecondsStr);
            Calendar dateCalendar = Calendar.getInstance();
            dateCalendar.setTimeInMillis(dateMilliseconds);
            date.setText("For: " +
                    new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(dateCalendar.getTime()) +
                    " at " +
                    new SimpleDateFormat("HH:mm:ss", Locale.US).format(dateCalendar.getTime()));
        }
        else
            date.setVisibility(View.GONE);

        TextView message = (TextView) cardView.findViewById(R.id.message_history);
        if (historyItems.getMessage() != null)
            if (historyItems.getMessage().equals(""))
                message.setVisibility(View.GONE);
            else
                message.setText("Сообщение для Вас: " + historyItems.getMessage());

        // TODO: заменить всё
        // userName.setText(friendsItems.getValue());
        /*
        // Заполняем поля
        Context context = holder.user_icon.getContext();
        //imageLoad(item, con);
        // TODO: методы переобозначить, тут 2 строки кала подряд
        String url = "https://penok.lisenkosoft.ru/json/user_icon/" + historyItems.getPayee();
        Log.d("testtest", historyItems.getPayee().toString());
        // holder.user_icon = (ImageView) cardView.findViewById(R.id.user_icon);
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = 50 * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        int px = (int) dp;
        Picasso.with(context)
                .load(url)
                .error(R.drawable.non_pic)
                .resize(px, px)
                .into(holder.user_icon); */
        Log.d("testtest", "onBind end");
    }

    /*void updateTransactions(List<TransactionsListModel> transactions, List<String> authorNames,
                            List<String> payeeNames, List<String> payerNames) {
        this.transactions = transactions;
        this.authorNames = authorNames;
        this.payeeNames = payeeNames;
        this.payerNames = payerNames;
        notifyDataSetChanged();
        Log.d("testtest", "notified");
    }*/

    void updateTransactions(List<TransactionsListModel> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
        Log.d("testtest", "notified");
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  transactions == null ? 0 : transactions.size();
    }

}
