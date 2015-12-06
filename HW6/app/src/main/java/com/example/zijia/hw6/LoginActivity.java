package com.example.zijia.hw6;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.nio.charset.Charset;

public class LoginActivity extends AppCompatActivity {
    Button login;
    Button register;
    EditText user;
    EditText password;
    CheckBox checkBox;
    String enpassword;
    String depassword;

    //简单的密码加密解密算法   异或运算
    private static final String key0 = "FECOI()*&<MNCXZPKL";
    private static final Charset charset = Charset.forName("UTF-8");
    private static byte[] keyBytes = key0.getBytes(charset);
    public static String encode(String enc){
        byte[] b = enc.getBytes(charset);
        for(int i=0,size=b.length;i<size;i++){
            for(byte keyBytes0:keyBytes){
                b[i] = (byte) (b[i]^keyBytes0);
            }
        }
        return new String(b);
    }
    public static String decode(String dec){
        byte[] e = dec.getBytes(charset);
        byte[] dee = e;
        for(int i=0,size=e.length;i<size;i++){
            for(byte keyBytes0:keyBytes){
                e[i] = (byte) (dee[i]^keyBytes0);
            }
        }
        return new String(e);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.button2);
        register = (Button)findViewById(R.id.button);
        user = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        //注册事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("user", user.getText().toString());
                editor.putString("password", password.getText().toString());
                //密码加密
//                enpassword = encode(password.getText().toString());
//                editor.putString("password", enpassword);
                editor.commit();
                Toast.makeText(LoginActivity.this, "Register success", Toast.LENGTH_SHORT).show();
            }
        });
        //登录事件
        login.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                String pre_user = sharedPreferences.getString("user", "");
                String pre_password = sharedPreferences.getString("password", "");
                //密码解密
//                depassword = sharedPreferences.getString("password", "");
//                String pre_password = decode(depassword);
                if (pre_user.equals(user.getText().toString()) && pre_password.equals(password.getText().toString())) {
                    startActivity(new Intent(LoginActivity.this, EditFileActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //判断CheckBox是否选上及对应事件
        if (getSharedPreferences("data", MODE_PRIVATE).getBoolean("isChecked", false)) {
            user.setText(getSharedPreferences("data", MODE_PRIVATE).getString("user", ""));
            password.setText(getSharedPreferences("data", MODE_PRIVATE).getString("password",""));
            checkBox.setChecked(true);
        }
        //记录密码就保存密码数据，不记录则清空密码栏。
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putBoolean("isChecked", isChecked);
                    editor.commit();
                } else {
                    password.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
