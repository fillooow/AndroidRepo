package com.journaldev.navigationdrawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.journaldev.navigationdrawer.API.APIUtils;
import com.journaldev.navigationdrawer.API.Model;
import com.journaldev.navigationdrawer.API.MyStatsModel;
import com.journaldev.navigationdrawer.API.PenyokService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class StatsFragment extends Fragment {

    private String sid;
    private PenyokService statsService;
    private RecyclerView statsRecyclerView;
    //TODO: удалить
    // private View view;
    private StatsAdapter statsAdapter;

    public StatsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        statsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_stats, container, false);

        // view = getView();
        sid = ((MainActivity) getActivity()).getSid();
        statsService = APIUtils.getPService();
        statsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_stats, container, false);
        statsAdapter = new StatsAdapter(new ArrayList<MyStatsModel>(0));
        statsRecyclerView.setAdapter(statsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        statsRecyclerView.setLayoutManager(layoutManager);
        loadStats();

        return statsRecyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // пишем тут
    }

    private void loadStats(){
        statsService.getStats(sid).enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()) {
                        statsAdapter.updateStats(response.body().getMyStats());
                        // Toast.makeText(getActivity(), "Gav gav", Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //showErrorMessage();
                Log.d("cardtestcrap", t.toString());
                // Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
