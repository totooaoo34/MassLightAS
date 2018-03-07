package com.totoo.TouhouMassLight;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.totoo.TouhouMassLight.creature.NewHero;
import com.totoo.TouhouMassLight.creature.NewMagic;
import com.totoo.TouhouMassLight.creature.NewSuperShow;
import com.totoo.TouhouMassLight.util.ContorlView;

public class FullscreenActivity extends Activity {
    static Canvas GameCan = null;
    static Paint drawSome = null;
    public static final Double UITpersent = 0.25;
    public static final Double UIpersent2 = 0.2;
    public int SW = 480;
    public int SH = 768;
    public int EarthLine = (int) (SH * 0.62);
    int TouchArceH = (int) (SH * UITpersent);
    int TouchArceW = (int) (SW * UITpersent);
    int LeftDivede = (int) (SW * UITpersent);
    int RightDivede = (int) (SW * UITpersent * 3);
    int HighDivede = (int) (SH * UIpersent2);
    int MiddleDivede = (int) (SH * UIpersent2 * 2);
    int Center = (int) (SH * 0.8);
    int LowDivede = (int) (SH * UIpersent2 * 3);
    static NewHero molisha;
    static NewHero reimei;
    static int MapBodderX;
    static int MapBodderY;
    int[][] WorldMap;
    int[][] mapCache;
    int x;
    int y;
    int[] tagetXY;

    // Bitmap bgPic;
    Canvas CMain = null;
    Bitmap bMap = null;
    Bitmap bSCmap = null;
    // NotificationManager nm;
    // Notification n;
    DisplayMetrics dm;
    public static GameViewNew gv;
    String AlretString = "look there";
    NewSuperShow RSS;
    NewSuperShow MSS;
    View view;
    public static ContorlView contorlView;
    protected Handler mHandler;

//    OrientationEventListener mOrientationListener;
    private HorizontalScrollView horizontalScrollView1;
    protected TextView HeathTextView;

    private int NewMagicListSize = 72;
    protected int _pixiv;
    private boolean ExitGame = false;
    Thread game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // ȫ���ö�
        prepare(0);
        Runnable thread;
        thread = new GameThread();
        game = new Thread(thread);//这里有一处bug，想想是为什么
        game.start();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        ExitGame = false;
    }

    protected void prepare(int degare) {
        initGameSource();
        HeathTextView = initUI(_pixiv = 8);
        mHandler = new GameUIHandler(HeathTextView, FullscreenActivity.this, gv);

        gv.getBoder(SW, SH, _pixiv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            gv.setRotationX(25);
        }
        // gv.flashThem();
    }
//void registListener(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            gv.setRotationX(45);
//        }
//        mOrientationListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                // Log.v(DEBUG_TAG, "Orientation changed to " + orientation);
//                if (orientation > 350 || orientation < 10) { // 0��
//                    if (0 == orientation) {
////                        prepare(0);
//                    } else
//                        orientation = 0;
//                } else if (orientation > 80 && orientation < 100) { // 90��
//                    if (90 == orientation) {
////                        prepare(90);
//                    } else
//                        orientation = 90;
//                } else if (orientation > 170 && orientation < 190) { // 180��
//                    if (180 == orientation) {
////                        prepare(180);
//                    } else
//                        orientation = 180;
//                } else if (orientation > 260 && orientation < 280) { // 270��
//                    if (270 == orientation) {
////                        prepare(270);
//                    } else
//                        orientation = 270;
//                } else {
//                    return;
//                } // http://blog.csdn.net/goldenfish1919/article/details/47423131
//
//            }
//        };
//        if (mOrientationListener.canDetectOrientation()) {
//            // Log.v(DEBUG_TAG, "Can detect orientation");
//            mOrientationListener.enable();
//        } else {
//            // Log.v(DEBUG_TAG, "Cannot detect orientation");
//            mOrientationListener.disable();
//        }}
//    protected TextView initUI(int pixiv) {
//        if (0 != pixiv)
//            _pixiv = pixiv;
//        return initUI(0);
//    }


    protected TextView initUI(int pixiv) {// ����ϵ�y
        view = getLayoutInflater().inflate(R.layout.activity_fullscreen, null);
        setContentView(view);
        gv = (GameViewNew) view.findViewById(R.id.imageView1)
        // new GameView(this)
        ;
        contorlView = (ContorlView) findViewById(R.id.cl);
        TextView HeathTV = (TextView) view.findViewById(R.id.HeathTextView);
        return HeathTV;
    }

    //        RotateAnimation animation2 = new RotateAnimation(0, degare + 45, SW / 2, SH / 4*3);
    //        animation2.setRepeatMode(RotateAnimation.RESTART);
//        animation2.setFillAfter(true);
//        contorlView.setAnimation(animation);
//        animation2.start();
//        horizontalScrollView1 = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
//        horizontalScrollView1.setMinimumHeight((int) gv.startY);


    protected void initGameSource() {// ��ʼ���[���YԴ
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        SH = dm.heightPixels;
        SW = dm.widthPixels;
        EarthLine = (int) (SH * 0.62);
        Center = (int) (SH * 0.7);
        TouchArceH = (int) (SH * UITpersent);
        TouchArceW = (int) (SW * UITpersent);
        LeftDivede = (int) (SW * UITpersent);
        RightDivede = (int) (SW * UITpersent * 3);
        HighDivede = (int) (SH * UIpersent2);
        MiddleDivede = (int) (SH * UIpersent2 * 2);
        Center = (int) (SH * 0.8);
        LowDivede = (int) (SH * UIpersent2 * 3);
        drawSome = new Paint();
        drawSome.setColor(Color.RED);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
//        thread.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mOrientationListener.disable();

    }

    protected RotateAnimation startAnimate(int degare, View viewObj) {

        RotateAnimation animation = new RotateAnimation(0, degare + 45, SW / 2, SH / 2);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {

            }
        });
        viewObj.setAnimation(animation);
//        animation.setRepeatMode(RotateAnimation.RESTART);
        animation.setFillAfter(true);
        return animation;
    }
    // void comfrimMap() {// ���µ؈D���
    // if (null != mapCache) {
    // WorldMap = mapCache;
    // }
    // }

    @Override
    public boolean onTouchEvent(MotionEvent event) {// ���²�׽
        int ia = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        gv.TouchX = x;
        gv.TouchY = y;
        if (MotionEvent.ACTION_DOWN == event.getAction())
            pressACircle();
        else
            PressKey(x, y);
        return super.onTouchEvent(event);
    }

    private void pressACircle() {
        // TODO Auto-generated method stub

    }

    boolean PressKey(int x, int y) {// ���I푑�
        TouchArce();
        if (gv.NewMagicList.size() < NewMagicListSize)
            gv.NewMagicList.add(new NewMagic(AlretString, gv.molisha.getXinMap() > x ? -1 : 1, //
                    gv.molisha.getYinMap() > y ? -1 : 1, //
                    gv.molisha, gv.TouchX, gv.TouchY));
        else
            for (int i = 0; i < NewMagicListSize; i++) {
                gv.NewMagicList.get(i).DeractionInX = gv.molisha.getXinMap() > x ? -1 : 1;
                gv.NewMagicList.get(i).DeractionInY = gv.molisha.getYinMap() > y ? -1 : 1;

            }
        return false;
    }

    boolean TouchArce() {// �օ^��׽ϵ�y
        if (gv.TouchX < LeftDivede) {// �ұ�
            if (gv.TouchY < HighDivede) {// �����
                // tz("�����");
                SSA();
            } else if (gv.TouchY < MiddleDivede) {// �����
                // tz("�����");
                WSA();
            } else if (gv.TouchY < LowDivede) {// �w���I
                // tz("�w���I");
                PF();
            } else {// ����
                // tz("����");
                PL();
            }
        } else if (gv.TouchX < RightDivede) {// �м�
            if (gv.TouchY > Center) {// ����
                // tz("����");
                PD();
            } else {// ����
                // tz("����");
                PU();
            }
        } else {// ���

            if (gv.TouchY < HighDivede) {// ����1
                // tz("����1");
                SC1();
            } else if (gv.TouchY < MiddleDivede) {// ����2
                // tz("����2");
                SC2();
            } else if (gv.TouchY < LowDivede) {// ����
                // tz("����");
                A();
            } else {// ����
                // tz("����");
                PR();
            }
        }

        return false;
    }

    class GameThread implements Runnable {
        Message msg;
        boolean exitflag = false;

        @Override
        public void run() {// �[�򾀳�
            while (!exitflag && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                if (ExitGame) {
                    exitflag = true;
                    game.interrupt();
//                    Message msg = new Message();
//                    msg.obj = true;
//                    mHandler.sendMessage(msg);
                } else {
                    contorlView.postInvalidate();
                    if (!gv.molisha.isGameExit()) {//是否绘制
                        gv.postInvalidate();
                        mHandler.sendEmptyMessage(0);//交给
                    }
                }
            }
        }
    }

    private void A() {

    }

    private void SC2() {

    }

    private void SC1() {

    }

    private void PU() {
        // molisha.setDescription(3);// ����
        // mapCache = molisha.moveUp(WorldMap, MapBodderX, MapBodderY);
        // comfrimMap();
    }

    private void PD() {
        // molisha.setDescription(1);// ����
        // mapCache = molisha.moveDown(WorldMap, MapBodderX, MapBodderY);
        // comfrimMap();
    }

    private void PL() {
        // molisha.setDescription(2);// ����

        // mapCache = molisha.moveLeft(WorldMap, MapBodderX, MapBodderY);
        // comfrimMap();
    }

    private void PR() {
        // molisha.setDescription(0);// ����
        // mapCache = molisha.moveRight(WorldMap, MapBodderX, MapBodderY);
        // comfrimMap();
    }

    private void PF() {

    }

    private void WSA() {

    }

    private void SSA() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
//            Toast.makeText("lyj_test","now_is_back_event",1).show();
            ExitGame = true;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game = null;
//            game.destroy();
//            game.stop();
//            game.exit = true;  // 终止线程thread
//            game.join();
            finish();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
//        2、重写onBackPressed方法
//
//    Android 2.0开始又多出了一种新的方法，对于Activity 可以单独获取Back键的按下事件，直接重写onBackPressed方法即可，代码如下

    @Override
    public void onBackPressed() {
        // 这里处理逻辑代码，大家注意：该方法仅适用于2.0或更新版的sdk
        return;
    }
}
