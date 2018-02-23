package com.totoo.TouhouMassLight.creature;


public class NewHero extends _Things_MoveAble {
    String ID;
    String Name;
    public int Heath = 100;
    Long Strenth;
    private int XinMap = 1;
    private int YinMap = 1;
    int description = -1;
    static boolean godMode;
    public MagicBox magicBox;
    private int descriptionY, descriptionX;
    private boolean exitFlag = false;


    public void setFlag(boolean flag) {
        exitFlag = flag;
    }

    public boolean isGameExit() {
        return exitFlag;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public NewHero(int x, int y, float startX, float startY
                   // , MagicBox msg
    ) {
        XinMap = x;
        YinMap = y;
        magicBox = new MagicBox(MagicBox.width, MagicBox.height, startX, startY);
    }

    public NewHero() {
    }

    public void attack() {
    }

    public void hurt() {
    }

    public int[][] moveRight(int[][] map, int bdx, int bdy, int stx, int sty) {
        move(XinMap + 1, YinMap, bdx, bdy, stx, sty, map);
        return map;

    }

    public int[][] moveLeft(int[][] map, int bdx, int bdy, int stx, int sty) {
        move(XinMap - 1, YinMap, bdx, bdy, stx, sty, map);
        return map;
    }

    public int[][] moveUp(int[][] map, int bdx, int bdy, int stx, int sty) {
        move(XinMap, YinMap - 1, bdx, bdy, stx, sty, map);
        return map;
    }

    public int[][] moveDown(int[][] map, int bdx, int bdy, int stx, int sty) {

        move(XinMap, YinMap + 1, bdx, bdy, stx, sty, map);
        return map;
    }


    boolean Contact(int[][] map, NewHero tagetX, NewHero tagetY) {
        if (checkLocAva(tagetX.XinMap, tagetX.YinMap, tagetY.XinMap, tagetY.YinMap, tagetY.XinMap + 40,
                tagetY.YinMap + 40))
            return true;

        return false;
    }

    public boolean move(int x, int y, int bdx, int bdy, int stx, int sty, int[][] map) {
        // flashInMap(map, x, y, bdx, bdy, bdy, bdy);
        return godMode;

    }

    public void useMagic(int[][] map) {
    }

    public void flashInMap(_Things_MoveAble[][] map, int x, int y, int bdx, int bdy, int stx, int sty) {
        if (checkLocAva(x, y, bdx, bdy, stx, sty)) {
            // if
            // (!checkLocAva(XinMap,YinMap,tagetY.XinMap,tagetY.YinMap,tagetY.XinMap+40,tagetY.YinMap+40))
            // map[XinMap][YinMap] = 0;// ����
            // map[XinMap][YinMap - 1] = 0;// ͷ
            // map[XinMap][YinMap + 1] = 0;// ����
            // map[XinMap - 1][YinMap] = 0;// ���
            // map[XinMap + 1][YinMap] = 0;// �Ҽ�
            // map[XinMap - 1][YinMap + 1] = 0;// ����
            // map[XinMap + 1][YinMap + 1] = 0;// ����
            //
            // map[XinMap - 1][YinMap + 2] = 0;// ���
            // map[XinMap + 1][YinMap + 2] = 0;// �ҽ�
            //
            // map[x][y] = 2;// ����
            // map[x][y - 1] = 1;// ͷ
            // map[x][y + 1] = 1;// ����
            // map[x - 1][y] = 1;// ���
            // map[x + 1][y] = 1;// �Ҽ�
            // map[x - 1][y + 1] = 1;// ����
            // map[x + 1][y + 1] = 1;// ����
            //
            // map[x - 1][y + 2] = 1;// ���
            // map[x + 1][y + 2] = 1;// �ҽ�
            setXinMap(x);
            setYinMap(y);

        }
    }

    public int getXinMap() {
        return XinMap;
    }

    public void setXinMap(int xinMap) {
        XinMap = xinMap;
    }

    public int getYinMap() {
        return YinMap;
    }

    public void setYinMap(int yinMap) {
        YinMap = yinMap;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
        if (0 == description)
            descriptionX = -1;
        else if (1 == description)
            descriptionX = 1;
        else if (2 == description)
            descriptionY = -1;
        else if (3 == description)
            descriptionY = 1;
        else {
            descriptionX = 0;
            descriptionY = 0;
        }
    }

    void addDes(int description) {
        if (0 == description)
            descriptionX += -1;
        else if (1 == description)
            descriptionX += 1;
        else if (2 == description)
            descriptionY += -1;
        else if (3 == description)
            descriptionY += 1;
    }

    public void go(int _FX, int _FY, _Things_MoveAble[][] map, int bdx, int bdy, int stx, int sty) {
        flashInMap(map, getXinMap() + -_FX + descriptionX, getYinMap() + -_FY + descriptionY, bdx, bdy, stx, sty);
    }

}