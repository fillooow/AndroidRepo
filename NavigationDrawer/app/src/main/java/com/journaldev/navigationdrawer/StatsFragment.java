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

import com.journaldev.navigationdrawer.API.APIUtils;
import com.journaldev.navigationdrawer.API.Model;
import com.journaldev.navigationdrawer.API.MyStatsModel;
import com.journaldev.navigationdrawer.API.PenyokService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsFragment extends Fragment {

    private TextView emptyTextStats;
    private RecyclerView statsRecyclerView;

    private PenyokService statsService;
    private StatsAdapter statsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linearLayoutStats = (LinearLayout) inflater.inflate(R.layout.fragment_recycle, container, false);
        //transactionsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycle, container, false);
        statsRecyclerView = (RecyclerView) linearLayoutStats.findViewById(R.id.rv_fragments);
        emptyTextStats = (TextView) linearLayoutStats.findViewById(R.id.emptyText);

        statsService = APIUtils.getPService();
//        statsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycle, container, false);
        statsAdapter = new StatsAdapter(new ArrayList<MyStatsModel>(0));
        statsRecyclerView.setAdapter(statsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        statsRecyclerView.setLayoutManager(layoutManager);
        loadStats();

        return linearLayoutStats;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadStats(){
        statsService.getStats(MainActivity.getSid()).enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()) {
                        if(response.body().getMyStats() == null) {
                            emptyTextStats.setVisibility(View.VISIBLE);
                            statsRecyclerView.setVisibility(View.GONE);
                        }
                        else {
                            Log.d("tag", "stats response null" + MainActivity.getSid());
                            emptyTextStats.setVisibility(View.GONE);
                            statsRecyclerView.setVisibility(View.VISIBLE);
                            statsAdapter.updateStats(response.body().getMyStats());
                        }
                    }
                }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                emptyTextStats.setVisibility(View.VISIBLE);
                statsRecyclerView.setVisibility(View.GONE);
                emptyTextStats.setText("Проверьте ваше подключение к сети");
            }
        });
    }
}
