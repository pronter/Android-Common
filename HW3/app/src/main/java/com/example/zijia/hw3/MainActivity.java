package com.example.zijia.hw3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        private List<String> fruitList = new ArrayList<>();
        private List<Integer> fruitid = new ArrayList<>();
        public String[] fruit = {
                "Apple",
                "Orange",
                "Banana",
                "Cherry",
                "Coco",
                "Kiwi",
                "Pear",
                "Strawberry",
                "Melons"
        };

        public int[] fruit_add = {
                R.drawable.apple,
                R.drawable.orange,
                R.drawable.banana,
                R.drawable.cherry,
                R.drawable.coco,
                R.drawable.kiwi,
                R.drawable.pear,
                R.drawable.strawberry,
                R.drawable.watermelon
        };
        ListView listView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            for (int i = 0; i < fruit.length; i++){
                fruitList.add(new String(fruit[i]));
            }
            for (int i = 0; i < fruit_add.length; i++){
                fruitid.add(new Integer(fruit_add[i]));
            }

            listView = (ListView)findViewById(R.id.listView);
            final EfficientAdpter effi = new EfficientAdpter(this);
            listView.setAdapter(effi);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Main2Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("friut_name", position);
                    bundle.putString("fn",fruitList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    fruitList.remove(position);
                    fruitid.remove(position);
                    listView.setAdapter(effi);
                    return true;
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public class EfficientAdpter extends BaseAdapter {

            private LayoutInflater mInflater;

            public  EfficientAdpter(Context context){
                mInflater = LayoutInflater.from(context);
            }

            public int getCount(){
                return fruit.length;
            }

            public Object getItem(int position){
                return position;
            }

            public long getItemId(int position){
                return position;
            }

            public View getView(int position,View convertView,ViewGroup parent){
                ViewHolder holder;
                if (convertView == null){
                    convertView = mInflater.inflate(R.layout.fruit,null);//read row.xml
                    holder = new ViewHolder();
                    holder.ltext = (TextView)convertView.findViewById(R.id.listText);
                    holder.liamge = (ImageView)convertView.findViewById(R.id.listImage);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder)convertView.getTag();
                }
                holder.ltext.setText(fruitList.get(position));
                holder.liamge.setImageResource(fruitid.get(position));

                return convertView;
            }

            class ViewHolder{
                TextView ltext;
                ImageView liamge;
            }
        }
    }
