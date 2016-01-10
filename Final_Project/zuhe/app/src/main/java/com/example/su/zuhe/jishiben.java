package com.example.su.zuhe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by su on 2016/1/9.
 */
public class jishiben extends Activity implements View.OnClickListener {

    private List<Map<String, String>> mapList;
    private ListView listView;
    private Button button;
    private heiheiDatebaseHelper myDatebaseHelper;
    private FileUtils fileUtils;
    dbAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jishi);

        launch();
        setListview();
    }

    private void launch() {
        button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(this);
        listView = (ListView)findViewById(R.id.listView);
        fileUtils = new FileUtils();
        myDatebaseHelper = new heiheiDatebaseHelper(this);
        mapList = new ArrayList<Map<String, String>>();
        setData(mapList);
        adapter = new dbAdapter(jishiben.this, mapList, R.layout.contact, new String[] {"name", "time"} ,
                new int[]{R.id.nameTv, R.id.timeTv});
        listView.setAdapter(adapter);
    }

    public void setListview() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(jishiben.this);
                builder.setMessage("确认删除吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Map<String, String> item = mapList.get(position);
                        fileUtils.deleteFile(jishiben.this, item.get("name"));
                        Contact contact = new Contact();
                        contact.setName(item.get("name"));
                        contact.setTime(item.get("time"));
                        myDatebaseHelper.delete(contact);
                        mapList.clear();
                        setData(mapList);
                        listView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(jishiben.this, updateFile.class);
                Bundle bundle = new Bundle();
                Map<String, String> item = mapList.get(position);
                bundle.putString("name", item.get("name"));
                bundle.putString("time", item.get("time"));
                intent.putExtras(bundle);
                startActivity(intent);
                jishiben.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                Intent intent = new Intent(jishiben.this, addNew.class);
                startActivity(intent);
                jishiben.this.finish();
        }
    }

    public void setData(List<Map<String, String>> list) {
        Map<String, String> data;
        Cursor c = myDatebaseHelper.query();
        while (c.moveToNext()) {
            data = new HashMap<String, String>();
            data.put("name", c.getString(c.getColumnIndex("_name")));
            data.put("time", c.getString(c.getColumnIndex("_time")));
            list.add(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jishi, menu);
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

    public class dbAdapter extends SimpleAdapter {
        private ArrayList<Map<String, String>> mData;
        private LayoutInflater layoutInflater;

        public dbAdapter(Context context, List<? extends Map<String, String>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.layoutInflater = LayoutInflater.from(context);
            this.mData = (ArrayList<Map<String, String>>)data;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            viewHolder holder = null;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.contact, null);
                holder = new viewHolder();
                holder.name = (TextView) view.findViewById(R.id.nameTv);
                holder.time = (TextView) view.findViewById(R.id.timeTv);
                view.setTag(holder);
            } else {
                holder = (viewHolder) view.getTag();
            }
            Map<String, String> item = mData.get(position);
            holder.name.setText(item.get("name"));
            holder.time.setText(item.get("time"));
            return view;
        }
    }

    public class viewHolder {
        TextView name;
        TextView time;
    }
}

