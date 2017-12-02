package com.journaldev.navigationdrawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.journaldev.navigationdrawer.API.APIUtils;
import com.journaldev.navigationdrawer.API.Model;
import com.journaldev.navigationdrawer.API.PenyokService;
import com.journaldev.navigationdrawer.API.TransactionsListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsFragment extends Fragment {


    private RecyclerView transactionsRecyclerView;
    private TextView emptyTextTransactions;

    private PenyokService transactionsService;
    private TransactionsAdapter transactionsAdapter;

    private ArrayList<String> authorNames; // Создатели транзакций
    private ArrayList<String> payeeNames; // Они дают в долг
    private ArrayList<String> payerNames; // Они возвращают долг (занимают деньги у payee)
    private ArrayList<TransactionsListModel> transactions;

    private int authorCounter = 0;
    private int payeeCounter = 0;
    private int payerCounter = 0;
    private int size = 0;

    public TransactionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //TODO: заменить лейаут
        //transactionsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycle, container, false);

        transactionsService = APIUtils.getPService();
        transactions = new ArrayList<>();
        authorNames = new ArrayList<>();
        payeeNames = new ArrayList<>();
        payerNames = new ArrayList<>();

        //TODO: заменить лейаут
        LinearLayout linearLayoutTransactions = (LinearLayout) inflater.inflate(R.layout.fragment_recycle, container, false);
        //transactionsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycle, container, false);
        transactionsRecyclerView = (RecyclerView) linearLayoutTransactions.findViewById(R.id.rv_fragments);
        emptyTextTransactions = (TextView) linearLayoutTransactions.findViewById(R.id.emptyText);

        transactionsAdapter = new TransactionsAdapter(new ArrayList<TransactionsListModel>(0)/*,
                new ArrayList<String>(0), new ArrayList<String>(0), new ArrayList<String>(0)*/);
        transactionsRecyclerView.setAdapter(transactionsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        transactionsRecyclerView.setLayoutManager(layoutManager);
        loadTransactions();


        return linearLayoutTransactions;
    }

    private void loadTransactions() {
        transactionsService.getTransactionsHistory(MainActivity.getSid()).enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()) {
                    Log.d("testtest", "loaded names");

                    if (response.body().getTransactionsList() == null || response.body().getTransactionsList().equals("[]")) {
                        emptyTextTransactions.setVisibility(View.VISIBLE);
                        transactionsRecyclerView.setVisibility(View.GONE);
                        emptyTextTransactions.setText("Похоже, у вас ещё не было транзакций");
                    }
                    else {
                        emptyTextTransactions.setVisibility(View.GONE);
                        transactionsRecyclerView.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();
                        Log.d("testtest", "response  " + response.body().getTransactionsList().toString());
                        transactions.addAll(response.body().getTransactionsList());
                        /*size = transactions.size();
                        for (int i = 0; i < transactions.size(); i++) {
                            Log.d("testtest", "size: " + transactions.size());
                            Log.d("testtest", "counter " + i);
                            //TODO: кейс + остальные ключи
                            loadNames(transactions.get(i).getAuthor(), "author");
                            loadNames(transactions.get(i).getPayee(), "payee");
                            loadNames(transactions.get(i).getPayer(), "payer");
                        }

                        //TODO: чекать, есть ли такое id у автора и добавлять его сразу + ап счётчика
                        // соль в том, что экономится трафик*/
                    }

                    //needToUpdate(authorCounter, payeeCounter, payerCounter,
                            //size, transactions, authorNames, payeeNames, payerNames);

                    transactionsAdapter.updateTransactions(response.body().getTransactionsList());
                } else
                    Toast.makeText(getActivity(), "loadTransactions", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                emptyTextTransactions.setVisibility(View.VISIBLE);
                transactionsRecyclerView.setVisibility(View.GONE);
                emptyTextTransactions.setText("Проверьте ваше подключение к сети");
            }
        });
    }

    private void loadNames(final int id, final String key){
        transactionsService.getUserName(id, MainActivity.getSid()).enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()) {
                    Log.d("testtest", "loading " + key + " " + id);
                    Log.d("testtest", response.body().getNameFor());
                    //TODO: сюда кейс
                    switch (key) {
                        case "author":
                            authorNames.add(response.body().getNameFor());
                            authorCounter++;
                            break;
                        case "payee":
                            payeeNames.add(response.body().getNameFor());
                            payeeCounter++;
                            break;
                        case "payer":
                            payerNames.add(response.body().getNameFor());
                            payerCounter++;
                            break;
                    }

                    Log.d("testtest", "loaded authorsss, counter " + authorCounter);
                    //transactionsAdapter.updateTransactions(response.body().getTransactionsList());
                    needToUpdate(authorCounter, payeeCounter, payerCounter, size,
                            transactions, authorNames, payeeNames, payerNames);
                } else
                    Toast.makeText(getActivity(), "loadNames", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //showErrorMessage();
                Log.d("testtest", t.toString());
                Toast.makeText(getActivity(), "ошибка " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void needToUpdate(int authorCounter,
                              int payeeCounter,
                              int payerCounter,
                              int size,
                              List<TransactionsListModel> response,
                              ArrayList<String> authorNames,
                              ArrayList<String> payeeNames,
                              ArrayList<String> payerNames){
        if ((authorCounter == size) && (payeeCounter == size) && (payerCounter == size)) {
            /*
            authorCounter = 0;
            payeeCounter = 0;
            payerCounter = 0;*/
           // transactionsAdapter.updateTransactions(response, authorNames, payeeNames, payerNames);
        }
    }

}
