package com.example.su.zuhe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by su on 2016/1/2.
 */
public class addNew extends Activity implements View.OnClickListener {

    private EditText Content, activityTime, activityName;
    private Button button;
    private FileUtils fileUtils;
    private heiheiDatebaseHelper myDatebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        launch();
    }

    public void launch() {
        myDatebaseHelper = new heiheiDatebaseHelper(this);
        Content = (EditText)findViewById(R.id.contentEdit);
        activityTime = (EditText)findViewById(R.id.timeEdit);
        activityName = (EditText)findViewById(R.id.nameEdit);
        fileUtils = new FileUtils();
        button = (Button)findViewById(R.id.saveNew);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveNew:
                int can = 1;
                if (activityName.getText().toString().length() == 0) {
                    can = 0;
                }
                if (activityTime.getText().toString().length() == 0) {
                    can = 0;
                }
                if (can == 1) {
                    fileUtils.saveContent(addNew.this, activityName.getText().toString(), Content.getText().toString());
                    Contact contact = new Contact();
                    contact.setTime(activityTime.getText().toString());
                    contact.setName(activityName.getText().toString());
                    myDatebaseHelper.insert(contact);
                     Intent intent = new Intent(addNew.this, jishiben.class);
                    startActivity(intent);
                    addNew.this.finish();
                } else {
                    Toast.makeText(addNew.this, "Please enter a valid time and theme", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
