package com.totoo.TouhouMassLight;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

public class MusicPlayer extends Service {
    private MediaPlayer mediaPlayer;
    private Context mContext;
    private int count = 0;
    public static MusicPlayer startMusicSer;
    //    private MediaPlayer MP;
    private LocalBinder binder = new LocalBinder();

    public MusicPlayer() {

    }

    public MusicPlayer(Context mainActivity) {
        mContext = mainActivity;
        if (null == mediaPlayer)
            mediaPlayer = MediaPlayer.create(mContext, R.raw.variation);
    }

    /**
     * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口
     */
    public class LocalBinder extends Binder {
        // 声明一个方法，getService。（提供给客户端调用）
        MusicPlayer getService() {
            // 返回当前对象LocalService,这样我们就可在客户端端调用Service的公共方法了
            return MusicPlayer.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {

        return binder;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
//        MP = MediaPlayer.create(this, null);
//        MP.start();
        Action_ChangeSong(null);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void Action_ChangeSong(Uri data) {
        if (count > 1) count = 0;
        else
            count++;
        if (data != null) {
            if (null != mediaPlayer) {
                // mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(mContext, data);
                } catch (Exception e) {
                }

            }
        } else {
            switch (count) {
                case 0:
                    mediaPlayer = MediaPlayer.create(mContext, R.raw.variation);
                    break;
                case 1:
                    mediaPlayer = MediaPlayer.create(mContext, R.raw.sensitiveheart);
                    break;

                default:
                    mediaPlayer = MediaPlayer.create(mContext, R.raw.maikaze);
                    break;
            }

        }
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer arg0) {
//                if (!Activity_Main.SongSinglePlay) {
//                    arg0.reset();
//                    arg0.start();
//                }
                Action_ChangeSong(null);
            }
        });
//        try {
//            songBack = Constants.ReadSong(-1);
//            songNext = Constants.ReadSong(1);
//        } catch (Exception e) {
//        }
        mediaPlayer.start();
//        Activity_Main.Main_seekbar.setMax(mediaPlayer.getDuration());
//        handler.removeCallbacks(runThread);
//        handler.post(runThread);
    }

    public MusicPlayer createNew(MainActivity mainActivity) {
//        mediaPlayer;
        mContext = mainActivity;
        if (null == mediaPlayer)
            mediaPlayer = MediaPlayer.create(mContext, R.raw.maikaze);
        startMusicSer = MusicPlayer.this;
        return MusicPlayer.this;
    }
}
