package com.totoo.TouhouMassLight.creature;

import android.graphics.Bitmap;

public class _Things_MoveAble {
	public int slowXinScreen = 0;
	public int slowYinScreen = 0;
	static int XinMap, YinMap;
	static int CricleStrenth;
	public Bitmap Img = null;
	// public int slowXinScreen = 0;
	// public int slowYinScreen = 0;
	public BoxColor CricleType;

	public int[] move() {
		return null;
	};


	public boolean checkLocAva(int x, int y, int stX, int stY, int endX, int endY) {
		if (endX >= x) {
			if (endY >= y) {
				if (x >= stX) {
					if (y >= stY) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
