package com.totoo.TouhouMassLight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class GameUIHandler extends Handler {

    private TextView HeathTextView;
    private Context nContext;
    private GameViewNew gv;

    public GameUIHandler(TextView heathTextView, Context nContext, GameViewNew gv) {
        super();
        HeathTextView = heathTextView;
        this.nContext = nContext;
        this.gv = gv;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
//        if (null != msg.obj)//直接退出
//            if (null != nContext) {
//                ((Activity) nContext).finish();
//                nContext = null;
//            }
        if (gv.molisha.Heath > 0)
            HeathTextView.setText("Ѫ����" + gv.molisha.Heath);
        else {
            new AlertDialog.Builder(nContext).setTitle("ϵͳ��ʾ")// ���öԻ������

                    .setMessage("��ȷ���������ݶ���������Ƴ�ϵͳ��")// ������ʾ������

                    .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {// ���ȷ����ť

                        @Override

                        public void onClick(DialogInterface dialog, int which) {// ȷ����ť����Ӧ�¼�

                            ((FullscreenActivity) nContext).finish();

                        }

                    }).setNegativeButton("����", new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

                @Override

                public void onClick(DialogInterface dialog, int which) {// ��Ӧ�¼�
                    gv.molisha.setFlag(true);
                    ((FullscreenActivity) nContext).prepare(0);
                }

            }).show();
        }
    }

}
