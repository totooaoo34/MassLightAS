package com.totoo.TouhouMassLight.util;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ContorlView extends View {
	int CircleCenter = 120;

	public ContorlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		p = new Paint();
		// TODO Auto-generated constructor stub

	}

	private Paint p;
	public float TouchX = CircleCenter;
	public float TouchY = CircleCenter;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
		p.setColor(Color.YELLOW);
		p.setAlpha(100);
		p.setStrokeWidth(20);
		canvas.drawCircle(CircleCenter, CircleCenter, 120, p);
		// canvas.drawCircle(CircleCenter, CircleCenter, 70, p);
		p.setColor(Color.GRAY);
		// p.setAlpha(80);
		// p.setStrokeWidth(20);
		canvas.drawCircle(CircleCenter, CircleCenter, 100, p);
		p.setColor(Color.WHITE);
		// p.setAlpha(100);
		// p.setStrokeWidth(20);
		circleAgain(canvas);
	}

	float x, y;
	public float _FX=1;
	public float _FY=1;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		x = event.getX();
		y = event.getY();
		TouchX = x;
		TouchY = y;

		return true;// super.onTouchEvent(event);

	}

	void circleAgain(Canvas canvas) {
		canvas.drawCircle(TouchX, TouchY, 50, p);
		_FX = -(TouchX - CircleCenter) / 10;
		_FY = -(TouchY - CircleCenter) / 10;
		TouchX += _FX;
		TouchY += _FY;
	}

}
