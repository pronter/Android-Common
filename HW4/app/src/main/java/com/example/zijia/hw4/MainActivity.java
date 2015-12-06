package com.example.zijia.hw4;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    protected static final String ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    private Button button;
    private Button button2;
    private MyBroadcastReceiver receiver;
    private int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.widget);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (state == 0) {
                    receiver = new MyBroadcastReceiver();
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(ACTION);
                    registerReceiver(receiver, filter);
                    state = 1;
                    button.setText("Unregister Broadcast");
                } else {
                    unregisterReceiver(receiver);
                    state = 0;
                    button.setText("Register Brodcast");
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION);
                intent.putExtra("message", editText.getText().toString());
                sendBroadcast(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
