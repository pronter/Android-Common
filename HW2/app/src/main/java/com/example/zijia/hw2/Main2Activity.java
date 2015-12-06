package com.example.zijia.hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
        private View.OnClickListener listen;
        private TextView text;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            text = (TextView)findViewById(R.id.number);
            //按键的3行
            LinearLayout row1 = (LinearLayout)findViewById(R.id.row1);
            LinearLayout row2 = (LinearLayout)findViewById(R.id.row2);
            LinearLayout row3 = (LinearLayout)findViewById(R.id.row3);
            //添加一个监听事件
            listen = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btn1 = (Button) v;
                    String t = btn1.getText().toString();
                    String t2 = text.getText().toString();
                    t2 = t2+t;
                    text.setText(t2);
                }
            };
            //动态添加按钮
            for (int i=1;i<=3;i++){
                Button btn = new Button(this);
                btn.setText(i+"");
                btn.setOnClickListener(listen);
                row1.addView(btn);
            }
            for (int i=4;i<=6;i++){
                Button btn = new Button(this);
                btn.setText(i+"");
                btn.setOnClickListener(listen);
                row2.addView(btn);
            }
            for (int i=7;i<=9;i++){
                Button btn = new Button(this);
                btn.setText(i+"");
                btn.setOnClickListener(listen);
                row3.addView(btn);
            }

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main2, menu);
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
