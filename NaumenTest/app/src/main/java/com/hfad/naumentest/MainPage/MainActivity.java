package com.hfad.naumentest.MainPage;

/* Retrofit для загрузки JSON-а, Picasso для загрузки картинок. Ищи в ComputerCard */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hfad.naumentest.API.APIUtils;
import com.hfad.naumentest.API.NService;
import com.hfad.naumentest.ComputerCard.CardModel;
import com.hfad.naumentest.ComputerCard.ComputerCard;
import com.hfad.naumentest.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<Item> responseItems;
    private NService mService;

    private TextView computerName;
    private TextView companyName;
    private TextView pagesCounter;
    private Button nextButton;
    private Button previousButton;

    private int computerId;
    private int intentComputerId;
    private int page = 0; // Страница
    private int pages = 0; // Для рассчёта onNextButton в lastPage(), тупо больше page на 1
    private double maxPages = 1; // Дробное максимальное количество страниц, мы его вычисляем
    private int offset = 0; // Количество пропущенных элементов
    private int elementsOnPage = 10; // Элементов на странице
    private int total = 572; // Всего элементов
    private boolean nextEnabled = true;
    private boolean previousEnabled = true;
    private RecyclerView myRecyclerView;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        pagesCounter = (TextView) findViewById(R.id.pageCounterID);
        mService = APIUtils.getMService(); // Строим retrofit объект, собираем ссылку
        myRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
        mainAdapter = new MainAdapter(this, new ArrayList<Item>(0), new MainAdapter.MainItemListener() {

            @Override
            public void onMainClick(long id) {
                // Toast.makeText(ComputerCard.this, "Computer id is" + id, Toast.LENGTH_SHORT).show();
                computerId = (int) id;
                Intent intent = new Intent(MainActivity.this, ComputerCard.class);
                intent.putExtra("computerId", computerId);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setAdapter(mainAdapter);
        myRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        myRecyclerView.addItemDecoration(itemDecoration);
        loadSomeCrap();
    }

    public void loadSomeCrap() {
        mService.getItems(page).enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                if(response.isSuccessful()) {
                    /*responseItems = response.body().getItems();

                    computerName = (TextView) findViewById(R.id.computerName);
                    computerName.setText(responseItems.get(1).getName());

                    companyName = (TextView) findViewById(R.id.companyName);
                    if (responseItems.get(1).getCompany() != null) // Поле может не существовать
                        companyName.setText(responseItems.get(1).getCompany().getName());*/

                    //computerId = responseItems.get(1).getId();
                    page = response.body().getPage(); //  Получаем страницу
                    offset = response.body().getOffset(); // Получаем количество просмотренных пк
                    total = response.body().getTotal(); // Получаем количество всех пека
                    pagesCounter.setText("page " + page + " of " + (int) lastPage()); // page X of Y
                    mainAdapter.updateAnswers(response.body().getItems()); // Пихаем List<Items> в адаптер
                    Log.d("testcrap", "posts loaded from API");
                }else
                    Log.d("testcrap", "posts not loaded from API");
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //showErrorMes  sage();
                Log.d("testcrap", t.toString());

            }
        });
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(this, ComputerCard.class);
        intent.putExtra("computerId", computerId);
        startActivity(intent);
    }

    public void onNextClick(View view){
        if(page < lastPage()){
            page++;
        }
        if (page == lastPage())
            nextEnabled = false;
        nextButton.setEnabled(nextEnabled);
        loadSomeCrap();
        previousEnabled = true;
        previousButton.setEnabled(previousEnabled);
    }

    public void onPreviousClick(View view){
        if(page>0) {
            page--;
        }
        if (page == 0)
            previousEnabled = false;
        previousButton.setEnabled(previousEnabled);
        loadSomeCrap();
        nextEnabled = true;
        nextButton.setEnabled(nextEnabled);
    }

    // Получаем номер последней страницы
    public double lastPage(){
        offset+=10; // Потому что считаются лишь элементы на прошлых страницах
        /*Log.d("page", "pages: " + pages);
        Log.d("page", "page: " + page);*/
        // TODO: удалить
        pages = page+1;
        elementsOnPage = offset/pages; // Получим количество элементов на странице
        maxPages = total/elementsOnPage;
        maxPages = Math.ceil(maxPages); // Округляем до большего
        return maxPages;
    }
}
