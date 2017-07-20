package com.hfad.naumentest.ComputerCard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.naumentest.API.APIUtils;
import com.hfad.naumentest.API.NService;
import com.hfad.naumentest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComputerCard extends AppCompatActivity {

    private NService cardService;
    private String imageUrl;
    private TextView cardComputerDescription;
    private ImageView cardImageView;
    private int cardComputerId;
    private RecyclerView mRecyclerView;
    private ComputerCardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_card);

        Intent intent = getIntent();
        cardComputerId = intent.getIntExtra("computerId", -1);
        // TODO: не забыть проверку на >0 в ошибке

        cardService = APIUtils.getMService();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_card);
        mAdapter = new ComputerCardAdapter(this, new ArrayList<CardModel>(0), new ComputerCardAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                // TODO: уот оно 2
                Toast.makeText(ComputerCard.this, "Computer id is" + id, Toast.LENGTH_SHORT).show();
                cardComputerId = (int) id;
                loadCard();
                loadSimilar();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        //mRecyclerView.addItemDecoration(itemDecoration);
        loadCard();
        loadSimilar();
    }

    public void loadCard() {
        cardService.getComputer(cardComputerId).enqueue(new Callback<CardModel>() {

            @Override
            public void onResponse(Call<CardModel> call, Response<CardModel> response) {

                if(response.isSuccessful()) {

                    cardImageView = (ImageView) findViewById(R.id.cardImage);
                    if(response.body().getImageUrl() != null) {
                        imageUrl = response.body().getImageUrl();
                        // TODO: уот оно
                        // Toast.makeText(ComputerCard.this, "Computer id is" + imageUrl, Toast.LENGTH_SHORT).show();
                        imageLoad(imageUrl);
                    }


                    if (response.body().getName() != null) {
                        getSupportActionBar().setTitle(response.body().getName());
                    }

                    cardComputerDescription = (TextView) findViewById(R.id.cardComputerDescription);
                    if (response.body().getDescription() != null)
                        cardComputerDescription.setText(response.body().getDescription());
                }else {
                    Toast.makeText(ComputerCard.this, "запрос сломался", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CardModel> call, Throwable t) {
                //showErrorMessage();
                Log.d("cardtestcrap", t.toString());

            }
        });
    }

    private void imageLoad(String url) {
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.wait)
                .error(R.drawable.error)
                .into(cardImageView);
    }

    private void loadSimilar(){
        cardService.getSimilar(cardComputerId).enqueue(new Callback<List<CardModel>>() {

            @Override
            public void onResponse(Call<List<CardModel>> call, Response<List<CardModel>> response) {
                if(response.isSuccessful()) {
                    mAdapter.updateAnswers(response.body());
                    // Toast.makeText(ComputerCard.this, "Gav gav", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CardModel>> call, Throwable t) {
                //showErrorMessage();
                Log.d("cardtestcrap", t.toString());
                // Toast.makeText(ComputerCard.this, "Gav gav", Toast.LENGTH_SHORT).show();
            }
        });
    }
}