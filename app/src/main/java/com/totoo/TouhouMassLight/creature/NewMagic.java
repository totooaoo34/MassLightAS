package com.totoo.TouhouMassLight.creature;

public class NewMagic extends _Things_MoveAble {

	String ID;
	String Name;
	Long Time;
	Long Strenth;
	int SpeedInX = 10;
	int SpeedInY = 10;
	public int DeractionInX;
	public int DeractionInY;
	public int XinMap;
	public int YinMap;
	NewHero targetHero;
	private int speed = 10;

	public NewMagic(String name, int speedInX, int speedInY, NewHero targetHero, int x, int y) {
		super();
		Name = name;
		DeractionInX = speedInX;
		DeractionInY = speedInY;
		this.targetHero = targetHero;
		XinMap = targetHero.getXinMap();
		YinMap = targetHero.getYinMap();

		SpeedInX = Math.abs((x - targetHero.getXinMap())) / speed;
		SpeedInY = Math.abs((y - targetHero.getYinMap())) / speed;

	}

	public int getSpeedInX(int x) {
		SpeedInX = Math.abs((x - targetHero.getXinMap())) / speed;
		return SpeedInX * DeractionInX;
	}

	public int getSpeedInY(int y) {
		SpeedInY = Math.abs((y - targetHero.getYinMap())) / speed;
		return SpeedInY * DeractionInY;
	}
}
