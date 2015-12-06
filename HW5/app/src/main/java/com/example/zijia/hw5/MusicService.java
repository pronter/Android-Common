package com.example.zijia.hw5;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {
    public final IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        MusicService getService() {return MusicService.this;}
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //添加MediaPlayer
    public static MediaPlayer mp = new MediaPlayer();
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //添加音频文件路径
    public MusicService() {
        try {
            mp.setDataSource("/data/You.mp3");
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //播放/暂停
    public void playOrPause() {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
        }
    }
    //停止
    public void stop() {
        if (mp != null) {
            mp.stop();
            try {
                mp.prepare();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //销毁回收
    public void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }
}


