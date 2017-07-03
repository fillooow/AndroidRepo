package com.hfad.naumentest.ComputerCard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.naumentest.API.APIUtils;
import com.hfad.naumentest.API.NService;
import com.hfad.naumentest.MainPage.SimilarModel;
import com.hfad.naumentest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComputerCard extends AppCompatActivity {

    private NService cardService;
    private String imageUrl;
    private TextView cardComputerName;
    private TextView cardComputerDescription;
    private ImageView cardImageView;
    private int cardComputerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_card);

        Intent intent = getIntent();
        cardComputerId = intent.getIntExtra("computerId", -1);
        // TODO: не забыть проверку на >0 в ошибке

        cardService = APIUtils.getMService();
        loadCard();
        loadSimilar();
    }

    public void loadCard() {
        cardService.getComputer(cardComputerId).enqueue(new Callback<CardModel>() {

            @Override
            public void onResponse(Call<CardModel> call, Response<CardModel> response) {

                if(response.isSuccessful()) {
                    imageUrl = response.body().getImageUrl();
                    cardImageView = (ImageView) findViewById(R.id.cardImage);
                    imageLoad(imageUrl);


                    cardComputerName = (TextView) findViewById(R.id.cardComputerName);
                    if (response.body().getName() != null)
                        cardComputerName.setText(response.body().getName());

                    cardComputerDescription = (TextView) findViewById(R.id.cardComputerDescription);
                    if (response.body().getDescription() != null)
                        cardComputerDescription.setText(response.body().getDescription());
                }else {
                    cardComputerName = (TextView) findViewById(R.id.computerName);
                    cardComputerName.setText("test");
                }
            }

            @Override
            public void onFailure(Call<CardModel> call, Throwable t) {
                //showErrorMes  sage();
                Log.d("cardtestcrap", t.toString());

            }
        });
    }

    private void imageLoad(String url) {
        Picasso.with(this)
                .load(url) // .load("https://pp.userapi.com/c637425/v637425484/4fb4c/gNuYysBiwa0.jpg")
                .placeholder(R.drawable.pic)
                .error(R.drawable.pic)
                .into(cardImageView);
    }

    private void loadSimilar(){
        cardService.getSimilar(cardComputerId).enqueue(new Callback<List<SimilarModel>>() {

            @Override
            public void onResponse(Call<List<SimilarModel>> call, Response<List<SimilarModel>> response) {
                if(response.isSuccessful()) {
                    TextView textView = (TextView) findViewById(R.id.textView9);
                    textView.setText("gav");
                    // Toast.makeText(ComputerCard.this, "Gav gav", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SimilarModel>> call, Throwable t) {
                //showErrorMes  sage();
                TextView textView = (TextView) findViewById(R.id.textView9);
                textView.setText("ne gav");
                Log.d("cardtestcrap", t.toString());

            }
        });
    }
}
