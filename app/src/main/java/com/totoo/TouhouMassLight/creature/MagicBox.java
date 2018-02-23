package com.totoo.TouhouMassLight.creature;

import com.totoo.TouhouMassLight.creature.NewHero;
import com.totoo.TouhouMassLight.creature._Things_MoveAble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MagicBox {

	public static float width;
	private float startX;
	private float startY;
	public static float height;
	private float watchX;
	private float watchY;

	public MagicBox(float width, float height, float startX, float startY) {
		super();
		MagicBox.width = width;
		this.startX = startX;
		this.startY = startY;
		MagicBox.height = height;
	}

	public void setBox(float wid, float hei, float X, float Y) {
		MagicBox.width = wid;
		this.startX = X;
		this.startY = Y;
		MagicBox.height = hei;
	}

	public void showBox(Canvas c, Paint p, NewHero h, _Things_MoveAble[][] world) {//
		showBox(c, p, h, 0, world);
	}

	public void showBox(Canvas c, Paint p, NewHero h, int type, _Things_MoveAble[][] world) {
		if (0 == type)
			p.setColor(Color.RED);
		else if (2 == type)
			p.setColor(Color.BLUE);
		else if (1 == type)
			p.setColor(Color.WHITE);
		// if (ColorEnum.redCreture == type || ColorEnum.blueWater == type) {//
		//
		c.drawRect(getCellx(h) * width / 8 + startX, //
				getCelly(h) * height / 8 + startY, //
				(getCellx(h) + 1) * width / 8 + startX, //
				(getCelly(h) + 1) * height / 8 + startY, //
				p);
		if (BoxColor.whiteBuliding == world[(int) getCellx(h)][(int) getCelly(h)].CricleType) {
			if (100 > h.Heath)
				h.Heath += 1;
		} else if (BoxColor.blueWater == world[(int) getCellx(h)][(int) getCelly(h)].CricleType)
			// if (0 != world[x)][y].CricleStrenth)
			if (0 < h.Heath)
			h.Heath -= 1;
		// }
	}

	public void showBox(Canvas c, Paint p, _Things_MoveAble[][] world) {

	}

	// private void tz(String str) {
	// AlretString = str;
	// drawSome.setColor(Color.RED);
	// }
	float getCellx(NewHero h) {
		if (watchX != (int) ((h.getXinMap() - startX) / (width / 8)))
			watchX = (int) ((h.getXinMap() - startX) / (width / 8));
		if (watchX > 8)
			watchX = 8;
		return (watchX);
	}

	float getCelly(NewHero h) {
		if (watchY != (int) ((h.getYinMap() - startY) / (height / 8)))
			watchY = (int) ((h.getYinMap() - startY) / (height / 8));
		if (watchY > 8)
			watchY = 8;
		return (watchY);
	}

	public static int getCellInMap(int number) {
		return (int) ((number) * (height / 8));
	}
}
