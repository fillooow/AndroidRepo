package com.hfad.penyok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.penyok.API.APIUtils;
import com.hfad.penyok.API.Model;
import com.hfad.penyok.API.PenyokService;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private PenyokService pService;

    private EditText loginEditText;
    private EditText passwordEditText;
    private TextView textView;

    private String login;
    private String password;
    private String sid;
    private static final String DEVICE = "android";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pService = APIUtils.getPService(); // Строим retrofit объект


    }

    public void logIn(String log, String pass, String device) {
        pService.getLogIn(log, pass, device).enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                if(response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "запрос удался", Toast.LENGTH_SHORT).show();
                    textView = (TextView) findViewById(R.id.textView);
                    textView.setText(response.body().toString());

                    if (response.body().getLogin() != null){
                        sid = response.body().getLogin();
                        Intent intent = new Intent(LoginActivity.this, Main3Activity.class);
                        intent.putExtra("sid", sid);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "запрос сломался", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                // Проверяем возможность подключения к хосту
                if (t instanceof  UnknownHostException)
                    Toast.makeText(LoginActivity.this, "проверьте подключение к интернету",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(LoginActivity.this, "всё сломалось" + t.toString(), Toast.LENGTH_SHORT).show();
                // Log.d("cardtestcrap", t.toString());
            }
        });
    }

    public void onLogInClick(View view) {
        /* loginEditText = (EditText) findViewById(R.id.login);
        passwordEditText = (EditText) findViewById(R.id.password);

        login = loginEditText.getText().toString();
        password = passwordEditText.getText().toString(); */
        login = "nyrko_ueban";
        password = "1488";
        logIn(login, password, DEVICE);
    }
}
