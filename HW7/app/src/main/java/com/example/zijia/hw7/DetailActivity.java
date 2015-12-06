package com.example.zijia.hw7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zijia on 2015/12/6.
 */
public class DetailActivity extends Activity {
    Button Ok;
    EditText No;
    EditText Name;
    EditText Phone;
    MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        myDatabaseHelper = new MyDatabaseHelper(this);
        Ok = (Button)findViewById(R.id.Ok);
        No = (EditText)findViewById(R.id.noEdit);
        Name = (EditText)findViewById(R.id.nameEdit);
        Phone = (EditText)findViewById(R.id.phoneEdit);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = No.getText().toString();
                String name = Name.getText().toString();
                String phone = Phone.getText().toString();
                if (no.equals("") || name.equals("") || phone.equals("")) return;
                Contact myData = new Contact();
                myData.setNo(no);
                myData.setName(name);
                myData.setPnumber(phone);
                myDatabaseHelper.insert(myData);
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                DetailActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

