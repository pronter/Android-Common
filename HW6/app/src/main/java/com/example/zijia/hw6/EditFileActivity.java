package com.example.zijia.hw6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;

public class EditFileActivity extends AppCompatActivity {
    Button saveFile;
    Button readFile;
    Button deleteFile;
    AutoCompleteTextView fileName;
    EditText fileText;
    FileUtils fileUtils;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_file);
            saveFile = (Button)findViewById(R.id.button3);
            readFile = (Button)findViewById(R.id.button4);
            deleteFile = (Button)findViewById(R.id.button5);
            fileName= (AutoCompleteTextView)findViewById(R.id.fileName);
            fileText= (EditText)findViewById(R.id.fileText);
            fileUtils = new FileUtils();
        //存储文件
        saveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileUtils.saveContent(EditFileActivity.this, fileName.getText().toString(),
                        fileText.getText().toString());
                fileName.setAdapter(new ArrayAdapter<String>(EditFileActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, EditFileActivity.this.fileList()));
            }
        });
        //读取文件
        readFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileText.setText(fileUtils.getContent(EditFileActivity.this, fileName.getText().toString()));
            }
        });
        //删除文件
        deleteFile.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                fileUtils.deleteFile(EditFileActivity.this, fileName.getText().toString());
                fileName.setAdapter(new ArrayAdapter<String>(EditFileActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, EditFileActivity.this.fileList()));
                fileName.setText("");
                fileText.setText("");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_file, menu);
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
