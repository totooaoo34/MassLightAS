package com.totoo.TouhouMassLight;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.TextView;

public class FullscreenActivity3d extends FullscreenActivity {

    private TextView HeathTextView;
    View mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // ȫ���ö�
        prepare(0);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            gv.setRotationX(45);
//        }
//        mOrientationListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                // Log.v(DEBUG_TAG, "Orientation changed to " + orientation);
//                prepare(0);
//            }
//        };
//        if (mOrientationListener.canDetectOrientation()) {
//            // Log.v(DEBUG_TAG, "Can detect orientation");
//            mOrientationListener.enable();
//        } else {
//            // Log.v(DEBUG_TAG, "Cannot detect orientation");
//            mOrientationListener.disable();
//        }
        new Thread(new GameThread()).start();
    }

    @Override
    public void prepare(int degare) {
        initGameSource();
        HeathTextView = initUI(_pixiv = 256);
        startAnimate(degare, gv).startNow();
        mHandler = new GameUIHandler(HeathTextView, FullscreenActivity3d.this, gv);
        gv.getBoder(SW, SH, _pixiv);
        // gv.flashThem();
    }



    OrientationEventListener mOrientationListener;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mOrientationListener.disable();
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
        PressKey(x, y);
        return super.onTouchEvent(event);
    }

    @Override
    boolean PressKey(int x, int y) {// ���I푑�
        TouchArce();
        return false;
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

}
