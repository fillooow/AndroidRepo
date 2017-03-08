package com.hfad.messagesender;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        //выводим предыдущее сообщение
        Intent recIntent = getIntent();
        String recMessageText = recIntent.getStringExtra(ReceiveMessageActivity.EXTRA_MESSAGE);
        TextView recView = (TextView) findViewById(R.id.prev);
        if (!(recMessageText==null))
            recView.setText("Your previous message: " + recMessageText);
        else
            recView.setText("");
    }

    public void onSendMessage(View view) {
        //EditText - ввод текстового поля, наследник TextView
        EditText messageView = (EditText) findViewById(R.id.message);
        String messageText = messageView.getText().toString();
        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, messageText);
        startActivity(intent);
    }

    public void onMessageSender(View view) {
        EditText messageViewSender = (EditText) findViewById(R.id.message);
        String messageTextSender = messageViewSender.getText().toString();
        Intent intentSender = new Intent(Intent.ACTION_SEND);
        intentSender.setType("text/plain");
        intentSender.putExtra(Intent.EXTRA_TEXT, messageTextSender);
        //получаем значение строкового ресурса
        //это заголовок в окошке выбора
        String chooserTitle = getString(R.string.chooser);
        //startActivity(intentSender, chooserTitle); не пашет, ибо нужен API 16, а тут 15
        startActivity(intentSender);
    }
}

