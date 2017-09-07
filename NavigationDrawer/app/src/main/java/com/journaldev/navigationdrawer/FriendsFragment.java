package com.journaldev.navigationdrawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.journaldev.navigationdrawer.API.APIUtils;
import com.journaldev.navigationdrawer.API.Model;
import com.journaldev.navigationdrawer.API.MyStatsModel;
import com.journaldev.navigationdrawer.API.PenyokService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class FriendsFragment extends Fragment {

    private String sid;
    private PenyokService friendsService;
    private RecyclerView friendsRecyclerView;
    private FriendsAdapter friendsAdapter;
    private List<Integer> ids;
    private List<String> names;

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        friendsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_stats, container, false);

        // view = getView();
        sid = ((MainActivity) getActivity()).getSid();
        friendsService = APIUtils.getPService();

        friendsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_stats, container, false);
        friendsAdapter = new FriendsAdapter(new ArrayList<Integer>(0), new ArrayList<String>(0), getActivity().getApplicationContext(), sid);
        friendsRecyclerView.setAdapter(friendsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        friendsRecyclerView.setLayoutManager(layoutManager);
        loadFriends();

        return friendsRecyclerView;
    }

    private void loadFriends(){
        friendsService.getFriends(sid).enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()) {
                    ids = response.body().getMyFriends();
                    loadUserName(ids);
                    friendsAdapter.updateFriends(response.body().getMyFriends(), names);
                    // Toast.makeText(getActivity(), "Gav gav", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //showErrorMessage();
                Log.d("cardtestcrap", t.toString());
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserName(List<Integer> id) {
        for (int x : id) {
            friendsService.getUserName(x, sid).enqueue(new Callback<Model>() {

                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getNameFor() != null)
                            names.add(response.body().getNameFor());
                    else
                        names.add("Set your name");
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

}
