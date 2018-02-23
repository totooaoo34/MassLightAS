package com.totoo.TouhouMassLight.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.totoo.TouhouMassLight.R;

import javax.crypto.ExemptionMechanismSpi;

public class GameworldView extends View {

    private int width, height// , tempX, tempY
            ;
    private float TouchedX = 0, TouchedY = 0, clickX = 0, clickY = 0, beforeX = 0, beforeY = 0;
    Bitmap drawable;
    private Drawable img;

    Paint p;

    public GameworldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        img = getResources().getDrawable(R.drawable.gameworld);
        drawable = (((BitmapDrawable) img).getBitmap());
        // img.setImageDrawable(drawable);
        // android.view.ViewGroup.LayoutParams layoutParams =
        // img.getLayoutParams();
        // layoutParams.height =
        height = img.getIntrinsicHeight();
        // layoutParams.width =
        width = img.getIntrinsicWidth();
        // img.setScrollX(
        clickX = (beforeX = width / 2 - GameworldView.this.getWidth() / 2)
        // )
        ;
        // img.setScrollY(
        clickY = (beforeY = height / 2 - GameworldView.this.getHeight() / 2)
        // )
        ;
        p = new Paint();
        p.setColor(Color.BLACK);
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.draw(canvas);
//        try {
        if (!(beforeX < 1))
            beforeX = 0;
        if (!(beforeY < 1))
            beforeY = 0;
        if (!(beforeX > -height + GameworldView.this.getHeight()))
            beforeY = -height + GameworldView.this.getHeight();
        if (!(beforeX > -width + GameworldView.this.getWidth()))// ?????
            beforeX = -width + GameworldView.this.getWidth();
        {
            scroll((int) clickX, (int) clickY);
            canvas.drawBitmap(drawable, (int) beforeX, (int) beforeY, null);
        }
        p.setAntiAlias(true);
        canvas.drawRect(clickX - 40, clickX + 40, clickY - 40, clickY + 40, p);
//        } catch (Exception e) {
//        }
    }

    public boolean onTouch(MotionEvent event) {
        postInvalidate();
        clickX = event.getX();
        clickY = event.getY();
        return super.onTouchEvent(event);
    }

    public void scroll(int X, int Y) {
        // TODO Auto-generated method stub
        TouchedX = -(int) (X// ev.getRawX()
                // - TouchedX
                - GameworldView.this.getWidth() / 2);
        beforeX = (int) (beforeX// img.getScrollX()
                + TouchedX);
        // if (-GameworldView.this.getWidth() <= tempX && tempX <=
        // GameworldView.this.getWidth())
        // img.setScrollX((int) tempX);
        // (ev.getRawX());
        TouchedY = -(int) (Y// ev.getRawY()
                // - TouchedY
                - GameworldView.this.getHeight() / 2);
        beforeY = (int) (beforeY// img.getScrollY()
                + TouchedY);
        // if (-GameworldView.this.getHeight() <= tempY && tempY <=
        // GameworldView.this.getHeight())
        // img.setScrollY((int) tempY);
    }

    // public ImageView getView() {
    // // TODO Auto-generated method stub
    // return // findViewById(R.id.imageView1)
    // img;
    //
    // }
}
