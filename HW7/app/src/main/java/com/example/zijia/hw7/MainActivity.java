package com.example.zijia.hw7;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    Button addNew;
    MyDatabaseHelper myDatabaseHelper;
    List<Map<String, String>> mapList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
    }

    public void setUpViews() {
        myDatabaseHelper = new MyDatabaseHelper(this);
        addNew = (Button)findViewById(R.id.button);
        mapList = new ArrayList<Map<String, String>>();
        setData(mapList);
        listView = (ListView)findViewById(R.id.listView);
        final dbAdapter adapter = new dbAdapter(MainActivity.this, mapList, R.layout.contact, new String[] {"no", "name", "phone"} ,
                new int[]{R.id.noTv, R.id.nameTv, R.id.phoneTv});
        listView.setAdapter(adapter);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        //设置长按联系人监听事件，弹出对话框，对话框布局及按钮监听事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("确认删除吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Map<String, String> item = mapList.get(position);
                        Contact contact = new Contact();
                        contact.setNo(item.get("no"));
                        contact.setName(item.get("name"));
                        contact.setPnumber(item.get("pNumber"));
                        myDatabaseHelper.delete(contact);
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

        //设置单击联系人监听事件，弹出对话框，对话框布局及按钮监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final View dia = (View) getLayoutInflater().inflate(R.layout.dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("修改");
                builder.setView(dia);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText eno, ena, eph;
                        eno = (EditText) dia.findViewById(R.id.xueHao);
                        ena = (EditText) dia.findViewById(R.id.xingMing);
                        eph = (EditText) dia.findViewById(R.id.dianHua);
                        String no = eno.getText().toString();
                        String name = ena.getText().toString();
                        String phone = eph.getText().toString();
                        Contact myData = new Contact();
                        myData.setNo(no);
                        myData.setName(name);
                        myData.setPnumber(phone);
                        Map<String, String> item = mapList.get(position);
                        String where = item.get("no");
                        myDatabaseHelper.update(myData, where);
                        mapList.clear();
                        setData(mapList);
                        listView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    //设置数据保存到数据库中
    public void setData(List<Map<String, String>> list) {
        Map<String, String> data;
        Cursor c = myDatabaseHelper.query();
        while (c.moveToNext()) {
            data = new HashMap<String, String>();
            data.put("no", c.getString(c.getColumnIndex("_no")));
            data.put("name", c.getString(c.getColumnIndex("_name")));
            data.put("pNumber", c.getString(c.getColumnIndex("_pnumber")));
            list.add(data);
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

    //简易适配器，连接数据和UI显示
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
                holder.no = (TextView) view.findViewById(R.id.noTv);
                holder.name = (TextView) view.findViewById(R.id.nameTv);
                holder.phone = (TextView) view.findViewById(R.id.phoneTv);
                view.setTag(holder);
            } else {
                holder = (viewHolder) view.getTag();
            }
            Map<String, String> item = mData.get(position);
            holder.no.setText(item.get("no"));
            holder.name.setText(item.get("name"));
            holder.phone.setText(item.get("pNumber"));
            return view;
        }
    }

    public class viewHolder {
        TextView no;
        TextView name;
        TextView phone;
    }
}




