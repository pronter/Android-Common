package com.example.su.zuhe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
/**
 * Created by su on 2016/1/9.
 */
public class MyPlayer extends ListActivity {

    private MediaPlayer myMediaPlayer;

    private List<String> myMusicList=new ArrayList<String>();

    private int currentListItem=0;

    private static final String MUSIC_PATH = new String("/sdcard/");

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplayer);
        myMediaPlayer=new MediaPlayer();

        findView();
        musicList();
        listener();
    }


    void musicList(){
        File home=new File(MUSIC_PATH);
        try
        {
            if(home.listFiles(new MusicFilter()).length>0){
                for(File file:home.listFiles(new MusicFilter())){
                    myMusicList.add(file.getName());
                }
                ArrayAdapter<String> musicList=new ArrayAdapter<String>
                        (MyPlayer.this,android.R.layout.simple_list_item_1, myMusicList);
                setListAdapter(musicList);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    void findView(){
        viewHolder.start = (Button)findViewById(R.id.start);
        viewHolder.stop = (Button)findViewById(R.id.stop);
        viewHolder.next = (Button)findViewById(R.id.next);
        viewHolder.pause = (Button)findViewById(R.id.pause);
        viewHolder.last = (Button)findViewById(R.id.last);
    }



    void listener(){

        viewHolder.stop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(myMediaPlayer.isPlaying()){
                    myMediaPlayer.reset();
                }
            }
        });

        viewHolder.start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (myMusicList.size() != 0)
                    playMusic(MUSIC_PATH+myMusicList.get(currentListItem));
            }
        });

        viewHolder.next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                nextMusic();
            }
        });

        viewHolder.pause.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(myMediaPlayer.isPlaying()){
                    myMediaPlayer.pause();
                }else{
                    myMediaPlayer.start();
                }
            }
        });

        viewHolder.last.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                lastMusic();
            }
        });

    }


    void playMusic(String path){
        try {
            myMediaPlayer.reset();
            myMediaPlayer.setDataSource(path);
            myMediaPlayer.prepare();
            myMediaPlayer.start();
            myMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    nextMusic();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    void nextMusic(){
        int n_index = currentListItem;
        n_index++;
        if (myMusicList.size() <= 0) return;
        if (n_index >= myMusicList.size())
        {
            n_index = 0;
        }
        currentListItem = n_index;
        playMusic(MUSIC_PATH+myMusicList.get(currentListItem));
    }


    void lastMusic(){
        int n_index = currentListItem;
        n_index--;
        if (myMusicList.size() <= 0) return;
        if (n_index < 0)
        {
            n_index = myMusicList.size() - 1;
        }
        currentListItem = n_index;
        playMusic(MUSIC_PATH+myMusicList.get(currentListItem));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK){
            myMediaPlayer.stop();
            myMediaPlayer.release();
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        currentListItem=position;
        if (myMusicList.size() != 0)
            playMusic(MUSIC_PATH+myMusicList.get(currentListItem));
    }
}
