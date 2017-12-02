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
import com.journaldev.navigationdrawer.API.MyFriendModel;
import com.journaldev.navigationdrawer.API.PenyokService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment {

    private RecyclerView friendsRecyclerView;
    private TextView emptyTextFriends;

    private PenyokService friendsService;
    private FriendsAdapter friendsAdapter;
    private List<MyFriendModel> myFriendModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linearLayoutFriends = (LinearLayout) inflater.inflate(R.layout.fragment_recycle, container, false);
        friendsRecyclerView = (RecyclerView) linearLayoutFriends.findViewById(R.id.rv_fragments);
        emptyTextFriends = (TextView) linearLayoutFriends.findViewById(R.id.emptyText);
        friendsService = APIUtils.getPService();

        friendsAdapter = new FriendsAdapter(new ArrayList<MyFriendModel>(0),
                new FriendsAdapter.FriendDeleteItemListener() {
                    @Override
                    public void onDeleteClick(int id) {
                        friendsService.getRemoveFriend(id, MainActivity.getSid()).enqueue(new Callback<Model>() {

                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                if(response.isSuccessful()) {
                                    if(response.body().getRemoveFriend().equals(true))
                                        Toast.makeText(getActivity(), "true", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();

                                    friendsAdapter.updateFriends(myFriendModels);
                                }
                            }

                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                //showErrorMessage();
                                Log.d("testtest", t.toString());
                                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
        friendsRecyclerView.setAdapter(friendsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        friendsRecyclerView.setLayoutManager(layoutManager);
        loadFriends();

        return linearLayoutFriends;
    }

    private void loadFriends(){
        friendsService.getFriends(MainActivity.getSid()).enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()) {
                    if(response.body().getMyFriends() == null){
                        friendsRecyclerView.setVisibility(View.VISIBLE);
                        emptyTextFriends.setVisibility(View.GONE);
                        emptyTextFriends.setText("Похоже, у вас ещё нет друзей");
                    } else {
                        friendsRecyclerView.setVisibility(View.VISIBLE);
                        emptyTextFriends.setVisibility(View.GONE);
                        myFriendModels = response.body().getMyFriends();
                        friendsAdapter.updateFriends(response.body().getMyFriends());
                    }

                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                emptyTextFriends.setVisibility(View.VISIBLE);
                friendsRecyclerView.setVisibility(View.GONE);
                emptyTextFriends.setText("Проверьте ваше подключение к сети");
            }
        });
    }

    //public void onDeleteFriendClick(View view) {

    //}

}
