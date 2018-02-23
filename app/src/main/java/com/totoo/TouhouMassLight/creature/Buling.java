package com.totoo.TouhouMassLight.creature;


public class Buling extends NewHero {

	String ID;
	String Name;
	int Strenth;
	int Heath;
	public BoxColor type;

	public Buling(int x, int y, float startX, float startY, String iD, int strenth, BoxColor ty) {
		super(x, y, startX, startY);
		ID = iD;
		Strenth = strenth;
		type=ty;
	}

	int XinBox;
	int YinBox;
	public int getedY, getedX;

	public void setBoxCell(int getX, int getY) {
		getedY = getY;
		getedX = getX;
	}
}
