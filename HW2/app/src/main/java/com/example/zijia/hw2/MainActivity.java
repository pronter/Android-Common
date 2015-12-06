package com.example.zijia.hw2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText named;
    private EditText pass;
    private ImageButton image;
    private Button reset;
    private String name,password;
    private TextView message;
    private  Button jump;
    protected Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageButton)findViewById(R.id.image);
        //注册一个文本
        reset = (Button)findViewById(R.id.reset);
        //注册一个重置按钮
        named = (EditText) findViewById(R.id.name);
        //注册一个密码
        pass = (EditText)findViewById(R.id.password);
        // 得到数据
        name = named.getText().toString();
        password = pass.getText().toString();
        //添加输入的监听事件
        jump = (Button)findViewById(R.id.Jump);
        //动态添加Textview
        mcontext = this;
        message = new TextView(mcontext);
        message.setText("这是一个动态添加的Textview");
        final LinearLayout layout = (LinearLayout)findViewById(R.id.main);
        layout.addView(message);

        named.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    name = named.getText().toString();
                }
                return false;
            }
        });

        pass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    password = pass.getText().toString();
                }
                return false;
            }
        });
        //添加image的监听事件
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //字符串匹配
                if (name.equals("LeiBusi") && password.equals("Halo3Q")) {
                    //image.setImageDrawable(getResources().getDrawable(R.mipmap.state2));
                    image.setImageResource(R.drawable.state2);
                    //隐藏editText
                    named.setVisibility(View.GONE);
                    pass.setVisibility(View.GONE);
                } else {
                    image.setImageResource(R.drawable.state1);//转化imageButton的图
                    pass.requestFocus();//获取焦点
                    pass.setText("");
                    pass.setHint("密码错误！");

                }
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView newtext = new TextView(v.getContext());
                newtext.setText("这是长按添加的TextView");
                layout.addView(newtext);
                return false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setHint("请输入密码");
                pass.setText("");
                pass.setVisibility(View.VISIBLE);
                named.setVisibility(View.VISIBLE);
                named.setHint("请输入用户名");
                named.setText("");
                image.setImageResource(R.drawable.state1);
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                MainActivity.this.finish();
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
