package com.example.su.zuhe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageButton jishi, jisuan, tongxun, bofang, guishu, zhinan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jishi = (ImageButton)findViewById(R.id.jishi);
        jisuan = (ImageButton)findViewById(R.id.jisuan);
        tongxun = (ImageButton)findViewById(R.id.tongxun);
        bofang = (ImageButton)findViewById(R.id.bofang);
        guishu = (ImageButton)findViewById(R.id.guishu);
        zhinan = (ImageButton)findViewById(R.id.zhinan);

        jishi.setOnClickListener(this);
        jisuan.setOnClickListener(this);
        tongxun.setOnClickListener(this);
        bofang.setOnClickListener(this);
        guishu.setOnClickListener(this);
        zhinan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jishi:
                Intent intent = new Intent(MainActivity.this, jishiben.class);
                startActivity(intent);
                break;
            case R.id.jisuan:
                Intent intent1 = new Intent(MainActivity.this, Calculator.class);
                startActivity(intent1);
                break;
            case R.id.tongxun:
                Intent intent2 = new Intent(MainActivity.this, tongxunlu.class);
                startActivity(intent2);
                break;
            case R.id.bofang:
                Intent intent3 = new Intent(MainActivity.this, MyPlayer.class);
                startActivity(intent3);
                break;
            case R.id.guishu:
                Intent intent4 = new Intent(MainActivity.this, Guishudi.class);
                startActivity(intent4);
                break;
            case R.id.zhinan:
                Intent intent5 = new Intent(MainActivity.this, Zhinanzhen.class);
                startActivity(intent5);
                break;
        }
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
