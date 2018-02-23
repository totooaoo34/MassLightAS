package com.totoo.TouhouMassLight.creature;


public class NewSuperShow {
	public static final int SlowLenth = 4;
	int dscr;
	int xSlow;
	int ySlow;
	int StepLength;
	int tagetX;
	int tagetY;

	public NewSuperShow(NewHero h, int stp) {
		StepLength = stp;
		xSlow = h.getXinMap() * StepLength;
		ySlow = h.getYinMap() * StepLength;
	}

	public NewHero slowMove(NewHero h) {

		dscr = h.getDescription();
		if (-1 != dscr)
			switch (dscr) {
			case 0:
				xSlow += SlowLenth;// ????
				break;
			case 1:
				ySlow += SlowLenth;// ????
				break;
			case 2:
				xSlow -= SlowLenth;// ????
				break;
			case 3:
				ySlow -= SlowLenth;// ????
				break;
			}
		int XMoveMark = xSlow - h.getXinMap();
		int YMoveMark = ySlow - h.getYinMap();
		if (XMoveMark > StepLength || XMoveMark < -SlowLenth)
			tagetX = h.getXinMap() + XMoveMark ^ -1;
		if (YMoveMark > SlowLenth || YMoveMark < SlowLenth)
			tagetY = h.getYinMap() + YMoveMark ^ -1;

//		if (0 < XMoveMark) {
//			if (0 < YMoveMark) {// ?????
//
//			} else if (0 == YMoveMark) {// ?????
//
//			} else {// ?????
//
//			}
//		} else if (0 == XMoveMark) {
//			if (0 < YMoveMark) {// ?????
//
//			} else if (0 == YMoveMark) {// ?o?
//
//			} else {// ?????
//
//			}
//		} else {
//			if (0 < YMoveMark) {// ?????
//
//			} else if (0 == YMoveMark) {// ????
//
//			} else {// ?????
//
//			}
//		}
		h.setXinMap(tagetX);
		h.setYinMap(tagetY);
		return h;
	}

	public int getxSlow() {
		return xSlow;
	}

	public void setxSlow(int xSlow) {
		this.xSlow = xSlow;
	}

	public int getySlow() {
		return ySlow;
	}

	public void setySlow(int ySlow) {
		this.ySlow = ySlow;
	}

	public int getDscr() {
		return dscr;
	}

	public void setDscr(int dscr) {
		this.dscr = dscr;
	}

}
