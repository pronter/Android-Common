package com.example.zijia.hw5;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.InputStream;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Button btn2;
    Button btn3;
    SeekBar seekBar;
    TextView textView;
    TextView state;
    MusicService musicService;

    //按返回键实现后台运行
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //Activity启动时绑定MusicService
    private void bindServiceConnection() {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    //bindService成功后回调onServiceConnection函数，通过IBinder获取musicService对象
    //实现Activity与MusicService的绑定
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder) service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    //定义简单日期格式，用time.format的格式来显示所需要的数据
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    //定义Handler，监听滑动条的进度变化
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            textView.setText(time.format(musicService.mp.getCurrentPosition()) + "/" + time.format(musicService.mp.getDuration()));
            seekBar.setProgress(musicService.mp.getCurrentPosition());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        musicService.mp.seekTo(seekBar.getProgress());
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            handler.postDelayed(r, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindServiceConnection();
        btn = (Button)findViewById(R.id.button);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        musicService = new MusicService();
        textView = (TextView)findViewById(R.id.textView);
        state = (TextView)findViewById(R.id.state);

        //播放/暂停
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.playOrPause();
                textView.setText("OK");
                if (musicService.mp.isPlaying()) {
                    state.setText("Playing");
                } else {
                    state.setText("Pausing");
                }
                textView.setText(time.format(musicService.mp.getCurrentPosition()) + "/" + time.format(musicService.mp.getDuration()));
                seekBar.setProgress(musicService.mp.getCurrentPosition());
                seekBar.setMax(musicService.mp.getDuration());
                handler.post(r);
            }
        });
        //停止
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.stop();
                state.setText("Stoping");
            }
        });
        //退出
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(r);
                unbindService(sc);
                try {
                    //MainActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
}
