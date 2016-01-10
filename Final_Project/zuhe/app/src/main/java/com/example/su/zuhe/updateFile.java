package com.example.su.zuhe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by su on 2016/1/3.
 */
public class updateFile extends Activity implements View.OnClickListener {

    private EditText setName, setTime, setContent;
    private Button button;
    private FileUtils fileUtils;
    private heiheiDatebaseHelper myDatebaseHelper;
    private String name, time;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        launch();
        getBundle();
        readContent();
    }

    public void launch() {
        button = (Button)findViewById(R.id.saveUpdate);
        setName = (EditText)findViewById(R.id.setName);
        setTime = (EditText)findViewById(R.id.setTime);
        myDatebaseHelper = new heiheiDatebaseHelper(this);
        fileUtils = new FileUtils();
        setContent = (EditText)findViewById(R.id.setContent);
        button.setOnClickListener(this);
    }

    public void getBundle() {
        Bundle bundle = getIntent().getExtras();
        setName.setText(bundle.getString("name"));
        name = bundle.getString("name");
        setTime.setText(bundle.getString("time"));
        time = bundle.getString("time");
    }

    public void readContent() {
        setContent.setText(fileUtils.getContent(updateFile.this, setName.getText().toString()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveUpdate:
                if (name.equals(setName.getText().toString()) && time.equals(setTime.getText().toString())) {
                    fileUtils.saveContent(updateFile.this, setName.getText().toString(), setContent.getText().toString());
                    Intent intent = new Intent();
                    intent.setClass(updateFile.this, jishiben.class);
                    startActivity(intent);
                    updateFile.this.finish();
                } else {
                    fileUtils.deleteFile(updateFile.this, name);
                    fileUtils.saveContent(updateFile.this, setName.getText().toString(), setContent.getText().toString());
                    Contact contact = new Contact();
                    contact.setName(name);
                    contact.setTime(time);
                    myDatebaseHelper.delete(contact);
                    contact.setTime(setTime.getText().toString());
                    contact.setName(setName.getText().toString());
                    myDatebaseHelper.insert(contact);
                    Intent intent = new Intent();
                    intent.setClass(updateFile.this, MainActivity.class);
                    startActivity(intent);
                    updateFile.this.finish();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
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
