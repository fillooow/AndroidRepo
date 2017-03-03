package com.hfad.messagesender;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.view.View;

public class ReceiveMessageActivity extends AppCompatActivity {
    //объявляем константу
    public static final String EXTRA_MESSAGE = "message";
    public static final String PREV_MESSAGE = "prev";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);
        Intent intent = getIntent(); //получаем интент
        String messageText = intent.getStringExtra(EXTRA_MESSAGE); //вытаскиваем текст
        TextView messageView= (TextView) findViewById(R.id.message); //
        messageView.setText(messageText);
    }

    public void onBackToMain(View view) {
        //TextView - простое текстовое поле
        TextView prevMessage = (TextView) findViewById(R.id.message);
        String prevMessageText = prevMessage.getText().toString();
        Intent intentMain = new Intent(this, CreateMessageActivity.class);
        intentMain.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, prevMessageText);
        startActivity(intentMain);
    }
}
