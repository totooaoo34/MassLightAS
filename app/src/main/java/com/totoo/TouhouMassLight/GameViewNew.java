package com.totoo.TouhouMassLight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.totoo.TouhouMassLight.creature.*;

public class GameViewNew extends View {
    int TouchX = -1;
    int TouchY = -1;
    float startX = 0, startY = 0, endX = 0, endY = 0;

    Bitmap PlayerImage;
    Bitmap BossImage;
    int StepLength = 34;
    NewHero molisha;// ???
    NewHero reimei;// boss
    Buling buling[] = new Buling[16];
    List<NewMagic> magicListX = new ArrayList<NewMagic>();// ????????
    List<NewMagic> magicListY = new ArrayList<NewMagic>();// ????????
    public List<NewMagic> NewMagicList = new ArrayList<NewMagic>();
    int SW = 480;// ?????
    int SH = 768;// ??
    // int MapBodderX;
    // int MapBodderY;
    int TouchArceH;
    int TouchArceW;
    _Things_MoveAble[][] WorldMap = new _Things_MoveAble[][]{};
    int[][] mapCache;
    int[] tagetXY;
    final Double UITpersent = 0.25;
    final Double UIpersent2 = 0.2;
    final Double UITpersent2 = 0.1;
    // public static final int StepLength = 34;
    int EarthLine = 800;
    int LeftDivede = 0;
    int RightDivede = 0;
    int HighDivede = 0;
    int MiddleDivede = 0;
    int startBarDivedeX = 200;
    int startBarDivedeX_Weight = 2;
    int Center = 0;
    int LowDivede;
    NewSuperShow RSS;
    NewSuperShow MSS;
    _Things_MoveAble[] creatrues;
    int playerHeath = 50;
    String AlretString = "wamring";
    Paint drawSome = null;
    long Count;
    long CountValueMax = 100;
    // private float width, height;
    // private MagicBox magicBox;
    private boolean initFlag;
    int flagCount = 0;
    Random ran = new Random();
    private int _pixiv = 8;

    public GameViewNew(Context context, AttributeSet attr) {// ????D???Y?
        super(context, attr);
        initFlag = init();
    }

    private boolean init() {

        // MapBodderX = SW / 4;
        // MapBodderY = SH / 4;

        getBoder(SW, SH);

        initContorlUIPic();
        return true;
    }

    public void flashThem(int x, int y, int x2, int y2) {
        molisha.flashInMap(WorldMap, x, y,
                // molisha.getXinMap() + 1, molisha.getYinMap() + 1,
                (int) startX, (int) startY, (int) endX, (int) endY);// ??????????????
        reimei.flashInMap(WorldMap, x2, y2,
                // reimei.getXinMap() + 1, reimei.getYinMap() + 1,
                (int) startX, (int) startY, (int) endX, (int) endY);
    }

    void getBoder(int _sw, int _sh) {
        getBoder(_sw, _sh, 0);
    }

    void getBoder(int _sw, int _sh, int pixiv) {
        if (0 != pixiv)
            _pixiv = pixiv;
        if (_sw > _sh) {// ????
            startX = (_sw - _sh) / 2;
            startY = 0;
            endX = _sw - (_sw - _sh) / 2;
            endY = _sh;
        } else {// ????
            startX = 0;
            startY = (_sh - _sw) / 2;
            endX = _sw;
            endY = _sh - (_sh - _sw) / 2;
        }
        SW = _sw;
        SH = _sh;
        MagicBox.width = endX - startX;
        MagicBox.height = endY - startY;
        molisha = new NewHero(40 / StepLength, 400 / StepLength, startX, startY);
        reimei = new NewHero(300 / StepLength, 400 / StepLength, startX, startY);
        MSS = new NewSuperShow(molisha, StepLength);
        RSS = new NewSuperShow(reimei, StepLength);
        // ??????
        WorldMap = new _Things_MoveAble[_pixiv][_pixiv];
        for (int i = 0; i < _pixiv; i++) {
            for (int j = 0; j < _pixiv; j++)
                WorldMap[i][j] = new _Things_MoveAble();

        }
        int getX, getY;
        for (int i = 0; i < 16; i++) {// ????????
            getX = ran.nextInt(_pixiv);
            getY = ran.nextInt(_pixiv);
            switch (ran.nextInt(2) + 1) {
                case 1:
                    WorldMap[getX][getY].CricleType = BoxColor.whiteBuliding;// ?????,???????
                    break;
                case 2:
                    WorldMap[getX][getY].CricleType = BoxColor.blueWater;// ?????,???????
                    break;

                default:
                    break;
            }
            buling[i] = new Buling((int) (MagicBox.getCellInMap(getX) + startX),
                    (int) (MagicBox.getCellInMap(getY) + startY), startX, startY, AlretString, 1,
                    WorldMap[getX][getY].CricleType);
            buling[i].setBoxCell(getX, getY);
            // for (int k = 0; k < 8; k++) {
            // for (int j = 0; j < 8; j++)

            // }
        }
        // ????????
        // boolean isBlock = false;
        // for (int i = 0; i < 16; i++) {
        // for (int j = 0; j < 16; j++) {
        // if (1 == buling[i].getedX - buling[j].getedX || -1 ==
        // buling[i].getedX - buling[j].getedX) {// X????
        // if (0 == buling[i].getedY - buling[j].getedY) {// y????
        // // isBlock = true;
        // magicListX.add(new NewMagic(AlretString, j, j, molisha, j, j));
        // }
        // }
        // if (1 == buling[i].getedY - buling[j].getedY || -1 ==
        // buling[i].getedY - buling[j].getedY) {// Y????
        // if (0 == buling[i].getedX - buling[j].getedX) {// x????
        // magicListY.add(new NewMagic(AlretString, j, j, molisha, j, j));
        // }
        // }
        //
        // }
        //
        // }
        if (_sw > _sh)
            flashThem((int) (startX * 2), (int) (MagicBox.height / 2), (int) (endX - startX),
                    (int) (MagicBox.height / 2));
        else
            flashThem((int) (MagicBox.width / 2), (int) (startY * 2), (int) (MagicBox.width / 2),
                    (int) (endY - startY));
    }

    @Override
    protected void onDraw(Canvas canvas) {// ???????
        // if (!initFlag) {
        drawBG(canvas);
        drawContorl(canvas);
        // initFlag = true;
        // } else {
        // flagCount++;
        // if (flagCount > 100) {
        // initFlag = false;
        // }
        // }
        for (int i = 0; i < 16; i++) {
            if (null != buling[i])
                if (BoxColor.blueWater == buling[i].type)
                    drawMap(canvas, buling[i], PlayerImage, MSS);
        }
        if (null != molisha) {
            if (null != FullscreenActivity.contorlView)
                molisha.go((int) FullscreenActivity.contorlView._FX, (int) FullscreenActivity.contorlView._FY, WorldMap,
                        (int) startX, (int) startY, (int) endX, (int) endY);

            if (!(molisha.Heath > 0))
                molisha.setFlag(true);

            drawPlayer(canvas, molisha, PlayerImage, MSS);
        }

        if (null != reimei)
            drawPlayer(canvas, reimei, BossImage, RSS);
        for (int i = 0; i < 16; i++) {
            if (null != buling[i])
                if (BoxColor.whiteBuliding == buling[i].type)
                    drawBuling(canvas, buling[i], PlayerImage, MSS);
        }
        drawMagic(canvas);

    }

    private void initContorlUIPic() {// ?d???[??D?
        // bgPic = ((BitmapDrawable)
        // getResources().getDrawable(R.drawable.bg)).getBitmap();
        PlayerImage = ((BitmapDrawable) getResources().getDrawable(R.drawable.pa)).getBitmap();
        BossImage = ((BitmapDrawable) getResources().getDrawable(R.drawable.pb)).getBitmap();
    }

    boolean drawBG(Canvas canvas) {// ??????L?u
        // canvas.drawColor(Color.BLACK);
        // drawImage(canvas, bgPic, 0, 0);
        // if (null != AlretString)
        // canvas.drawText(AlretString, StepLength, StepLength, drawSome);
        return false;
    }

    void drawMap(Canvas c, NewHero h, Bitmap Img, NewSuperShow SS) {// ?[???????L?u
        // Paint p = new Paint();
        // for (int i = 0; i < MapBodderX; i++) {
        // for (int j = 0; j < MapBodderY; j++) {
        // if (0 != WorldMap[i][j])
        // p.setColor(Color.BLUE);
        // c.drawRect(x * width / 8 + startX, //
        // y * height / 8 + startY, //
        // x + 1) * width / 8 + startX, //
        // y + 1) * height / 8 + startY, //
        // p);
        // // canvas.drawText("" + WorldMap[i][j], i * StepLength, j *
        // // StepLength, drawSome);
        // }
        // }
        // return false;
        drawCreatrue(c, h, Img, SS, 2);
    }

    void drawBuling(Canvas c, NewHero h, Bitmap Img, NewSuperShow SS) {
        drawCreatrue(c, h, Img, SS, 1);
    }

    void drawPlayer(Canvas c, NewHero h, Bitmap Img, NewSuperShow SS) {
        drawCreatrue(c, h, Img, SS, 0);
    }

    public void drawCreatrue(Canvas c, NewHero h, Bitmap Img, NewSuperShow SS, int type) {

        Paint p = new Paint();
        try {// ??????L?u
            if (-1 != h.getXinMap()) {
                if (-1 != h.getYinMap()) {
                    // h = SS.slowMove(h);
                    // x = SS.getxSlow();
                    // y = SS.getySlow();

                    h.magicBox.showBox(c, p, h, type, WorldMap);
                    if (0 == type)
                        drawImage(c, Img, h.getXinMap() - Img.getWidth() / 2, h.getYinMap() - Img.getHeight() / 2
                                // TouchX - StepLength / 2
                                // , TouchY
                        );
                    // if (0 == type)// tpye?0??????????
                    // p.setColor(Color.WHITE);
                    // c.drawText("x:" + TouchX + " y:" + TouchY, TouchX,
                    // TouchY, p);
                    // tz(h.getDescription()+" " + SS.getxSlow()+"
                    // "+SS.getySlow());
                }
            }
        } catch (Exception e) {
        }

    }

    NewMagic temp;
    private int tempCount = _pixiv;

    public void drawMagic(Canvas canvas) {// ????????L?u

        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);// ?????
        p.setAlpha(90);
        tempCount = NewMagicList.size();
        for (int i = 0; i < tempCount; i++) {
            temp = NewMagicList.get(i);
            if (!temp.checkLocAva(temp.XinMap, temp.YinMap, (int) startX, (int) startY, (int) endX, (int) endY)) {
                tempCount--;

if(null!=molisha)
                if(temp.checkLocAva(temp.XinMap, temp.YinMap,  molisha.getXinMap(),
                          molisha.getYinMap(),
                        (int) (molisha.getXinMap()+MagicBox.width) ,
                        (int) (molisha.getYinMap()+MagicBox.width)))

                    //sleep 1秒钟
                    molisha.Heath -= 0.01;

                NewMagicList.remove(temp);
                continue;
            }
            canvas.drawCircle(temp.XinMap, temp.YinMap, 10, p);
            temp.XinMap += temp.getSpeedInX(TouchX);
            temp.YinMap += temp.getSpeedInY(TouchY);
        }
    }

    public void drawContorl(Canvas canvas) {// ????????L?u
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        canvas.drawRect(startX, startY, endX, endY, p);
        p.setStyle(Paint.Style.STROKE);
        // canvas.drawText("??????A", RightDivede + 20, MiddleDivede + 20, p);
        // canvas.drawLine(LeftDivede, 0, LeftDivede, SH, p);
        // canvas.drawText("?????SS", 0 + 20, 0 + 20, p);
        // canvas.drawLine(RightDivede, 0, RightDivede, SH, p);
        // canvas.drawText("?????WS", 0 + 20, HighDivede + 20, p);
        // canvas.drawLine(0, HighDivede, LeftDivede, HighDivede, p);
        // canvas.drawText("?w??F", 0 + 20, MiddleDivede + 20, p);
        // canvas.drawLine(RightDivede, HighDivede, SW, HighDivede, p);
        // canvas.drawText("L????", 0 + 20, LowDivede + 20, p);
        // canvas.drawLine(0, MiddleDivede, LeftDivede, MiddleDivede, p);
        // canvas.drawText("U????", LeftDivede + 20, 0 + 20, p);
        // canvas.drawLine(RightDivede, MiddleDivede, SW, MiddleDivede, p);
        // canvas.drawText("D????", LeftDivede + 20, Center + 20, p);
        // canvas.drawLine(0, LowDivede, LeftDivede, LowDivede, p);
        // canvas.drawText("???R", RightDivede + 20, LowDivede + 20, p);
        // canvas.drawLine(RightDivede, LowDivede, SW, LowDivede, p);
        // canvas.drawText("?????oSC1", RightDivede + 20, 0 + 20, p);
        // canvas.drawLine(LeftDivede, Center, RightDivede, Center, p);
        // canvas.drawText("?????pSC2", RightDivede + 20, HighDivede + 20, p);
        p.setColor(Color.GREEN);
        p.setStrokeWidth(1);
        for (int i = 0; i < 8; i++) {
            //
            canvas.drawLine(
                    (i) * MagicBox.width / 8
                            // _pixiv
                            + startX,
                    startY, // x=x
                    (i) * MagicBox.width / _pixiv + startX, endY//
                    , p);// downHere,xUP,yEquals
        }
        for (int j = 0; j < 8; j++) {

            canvas.drawLine(startX,
                    (j) * MagicBox.width / 8
                            // _pixiv
                            + startY, // y=y
                    endX, (j) * MagicBox.width / _pixiv + startY, //
                    p);// goHere
        }
        p.setColor(Color.WHITE);
        p.setStrokeWidth(10);
        p.setAlpha(90);
        canvas.drawRect(startX, startY, endX, endY, p);
    }

    void drawImage(Canvas c, Bitmap b, int x, int y) {
        c.drawBitmap(b, x, y, null);
    }

    void ShootTip(String str) {
        // Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
