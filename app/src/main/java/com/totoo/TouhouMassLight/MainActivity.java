package com.totoo.TouhouMassLight;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.totoo.TouhouMassLight.util.*;

import java.io.InputStream;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity
//        implements OnCompletionListener, OnErrorListener, OnInfoListener,   OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceHolder.Callback
{
    static long Count;
    static long CountValueMax = 100;
    // NotificationManager nm;
    // Notification n;
    // nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    // n = new Notification();
    PendingIntent mpi;
    Intent   // mpi = PendingIntent.getActivity(MainActivity.this, 0, mi, 0);
            mi = new Intent();
    TextView b, bokA, bcA,
    // bokB,
    // Button
    bcB;

    private Display currDisplay;
    // private SurfaceView surfaceView;
    // private SurfaceHolder holder;
    // private MediaPlayer player;
    private int vWidth, vHeight;

//    public boolean onTouchEvent(android.view.MotionEvent event) {
//        horizontalScrollView1.onTouch(event);
//        return false;
//    }

    // private VideoView videoView;
    private ImageView boximageView;
    //    private GameworldView horizontalScrollView1;
    private ScrollView ScrollView1;
    private ImageView backgroudimageView1;
    private WebView mWebView;
    private String APPId, SplashPosId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//layout_game_world
        ViewInit();
        if (null == MusicPlayer.startMusicSer) {
            MusicPlayer Server = new MusicPlayer(MainActivity.this).createNew(MainActivity.this);
            Intent intent = new Intent(this, MusicPlayer.class);
            MyConn myConn = new MyConn();
            if (!isServiceWork(this, "MusicPlayer"))
                bindService(intent, myConn, BIND_AUTO_CREATE);
        } else
            MusicPlayer.startMusicSer.Action_ChangeSong(null);
    }

    MusicPlayer.LocalBinder binder;

    private class MyConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            isConnected = true;
//            mRemoteBookManager = IBookManager.Stub.asInterface(service);
//            toast("connected success");
            // 监听将数据回调给客户端
//            try {
            binder = (MusicPlayer.LocalBinder) iBinder;
            MusicPlayer.startMusicSer = binder.getService();
            MusicPlayer.startMusicSer.createNew(MainActivity.this);
            MusicPlayer.startMusicSer.Action_ChangeSong(null);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            mRemoteBookManager = null;
//            isConnected = false;
        }
    }

    ImageView imageView,imageViewBack;
    int imageViewstartX,imageViewstartY;
    void ViewInit() {
        imageView=findViewById(R.id.boximageView);
        imageViewBack=findViewById(R.id.boximageView);
        imageViewstartX=(int)imageView.getWidth();
        imageViewstartY=(int)imageView.getHeight();

SeekBar mS=findViewById(R.id.seekBar1);
        mS.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            float per;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               // 安卓中缩放和旋转控件
                per=(float)seekBar.getProgress()/100;
                imageView.setScaleX(1+per );
                imageView.setScaleY(1+per );
                imageView.setRotation(i);

                imageViewBack.setScaleX(1+per*2 );
                imageViewBack.setScaleY(1+per *2);
                imageViewBack.setRotation( - i*2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        FrameLayout container = (FrameLayout) this
//                .findViewById(R.id.splashcontainer);

//        new SplashAd(this, container, APPId, SplashPosId,
//                new SplashAdListener() {
//
//                    @Override
//                    public void onAdPresent() {
//                        Log.i(TAG, "splash  present");
//                        Toast.makeText(getApplication(),
//                                "SplashPresent", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onAdFailed(int arg0) {
//                        Log.i(TAG, "splash fail fail" + arg0);
////                        Toast.makeText(getApplication(),
////                                "SplashFail" + arg0, Toast.LENGTH_LONG).show();
////                            getApplicationContext().finish();
//                    }
//
//                    @Override
//                    public void onAdDismissed() {
//                        Log.i(TAG, "splash dismissed");
////                        Toast.makeText(getApplicationContext(),
////                                "SplashDismissed", Toast.LENGTH_LONG).show();
////                        getApplicationContext().finish();
//                    }
//                });
        b = (TextView) findViewById(R.id.button1);
        bokA = (TextView) findViewById(R.id.buttonA);// left
        bcA = (TextView) findViewById(R.id.buttonB);
        // bokB = (TextView) findViewById(R.id.CancelA);// right
        bcB = (TextView) findViewById(R.id.CancelB);
//        Count = 100;
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                tz();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
                GoWith(0);
            }
        });
        bokA.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                chooseA();
            }
        });
        bcA.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                exitA();
            }
        });
        // bokB.setOnClickListener(new Button.OnClickListener() {
        // @Override
        // public void onClick(View arg0) {
        // chooseB();
        // }
        // });
        bcB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                exitB();
            }
        });

//        boximageView = (ImageView) findViewById(R.id.boximageView);
//        LayoutParams boximageViewLayoutParams = boximageView.getLayoutParams();
//        boximageViewLayoutParams.width += 80;
//        boximageViewLayoutParams.height += 80;
//
//        horizontalScrollView1 = (GameworldView) findViewById(R.id.horizontalScrollView1);
        // backgroudimageView1 = horizontalScrollView1.getView();
        // horizontalScrollView1.scroll
        // // To
        // (backgroudimageView1.getWidth() / 2, backgroudimageView1.getHeight()
        // / 2);
        mWebView = (WebView) findViewById(R.id.webView1);
//        mWebView.loadUrl(
////                "http://www.baidu.com"
////                Uri.parse(
//                "asset://index.html"
////        )
//        );     //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开

//        initWebView(
        getCurrectPath();
//        );

        // ScrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        // ScrollView1.scrollTo(0, backgroudimageView1.getHeight() / 2);
    }

    public void initWebView(String data) {
        WebSettings settings = mWebView.getSettings();

        //settings.setUseWideViewPort(true);//调整到适合webview的大小，不过尽量不要用，有些手机有问题
        settings.setLoadWithOverviewMode(true);//设置WebView是否使用预览模式加载界面。
        mWebView.setVerticalScrollBarEnabled(false);//不能垂直滑动
        mWebView.setHorizontalScrollBarEnabled(false);//不能水平滑动
        settings.setTextSize(WebSettings.TextSize.LARGEST);//通过设置WebSettings，改变HTML中文字的大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        //设置WebView属性，能够执行Javascript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);//设置js可用
//        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
//                loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//                mErrorFrame.setVisibility(View.VISIBLE);
            }
        });
        mWebView.addJavascriptInterface(new AndroidJavaScript(getApplication()), "android");//设置js接口
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
    }

    /*问题原因：由于4.4浏览器不兼容，因为这里列出了二个样式来适应不同的浏览器版本

    第一种加载：mWebView.loadUrl("file:///android_asset/chart_top01.html");
    这种加载方式，会出现问题，那么采用第二种加载：
    说明：这种方式是字符串的加载形式，直接给文件的地址会出现错误，因此需要把上面本地文件读出来，然后采用下面的加载方式：

        webView.loadDataWithBaseURL("about:blank",bodyBuffer.toString(),"text/html","utf-8",null);
    读取assets文件的方法：这里可以读取HTML文件*/

//    /**
//     * 资产文件帮助类
//     */
//    public static class AssetsUtil {

    // 从assets 文件夹中获取文件并读取数据
    public String getFromAssets(Context context, String fileName) {
        String result = "";
//        try {
//            InputStream in = context.getResources().getAssets().open(fileName);
//            // 获取文件的字节数
//            int lenght = in.available();
//            // 创建byte数组
//            byte[] buffer = new byte[lenght];
//            // 将文件中的数据读到byte数组中
//            in.read(buffer);
//            result = EncodingUtils.getString(buffer, "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }

//    }

    //    将上面读取的字符串二次加载：
//    这样能完成网页的二次添加样式，从而去掉4.4的BUG
    String getCurrectPath() {
//        mWebView.loadUrl("file:///android_asset/chart_top01.html");
//        mWebView.loadDataWithBaseURL("about:blank",
//                "APK'>file:///android_asset/chart_top01.html", "text/html",
//                "utf-8", null);
////        String data =
////                "<HTML>在模拟器 2.1 上测试,这是<IMG src=\"APK'>file:///android_asset/igg.jpg\"/>APK里的图片";
//        mWebView.loadDataWithBaseURL("about:blank", bodyBuffer.toString(),
//                "text/html", "utf-8",null);
        String data = getFromAssets(this, "index.html");

        int currentapiVersion = Build.VERSION.SDK_INT;
        Log.i("fuck", currentapiVersion + "");
        if (currentapiVersion >= 19)

        {
            data += "<link rel=\"stylesheet\" type=\"text/css\" href=\"product_survey.css\" />";
            mWebView.loadDataWithBaseURL("file:///android_asset/product_survey.css", data, "text/html", "utf-8", null);
        } else

        {
            data += "<link rel=\"stylesheet\" type=\"text/css\" href=\"product_survey2.css\" />";
            mWebView.loadDataWithBaseURL("file:///android_asset/product_survey2.css", data, "text/html", "utf-8", null);
        }
        return data;
    }

    /**
     * AndroidJavaScript
     * 本地与h5页面交互的js类，这里写成内部类了
     * returnAndroid方法上@JavascriptInterface一定不能漏了
     */
    private class AndroidJavaScript {
        Context mContxt;

        public AndroidJavaScript(Context mContxt) {
            this.mContxt = mContxt;
        }

        @JavascriptInterface
        public void returnAndroid(String name) {//从网页跳回到APP，这个方法已经在上面的HTML中写上了
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                if (name.isEmpty() || name.equals("")) {
                    return;
                }
            }
            Toast.makeText(getApplication(), name, Toast.LENGTH_SHORT).show();
            //这里写你的操作///////////////////////
            //MainActivity就是一个空页面，不影响
//            Intent intent = new Intent(WebDataActivity.this, MainActivity.class);
//            intent.putExtra("name", name);
//            startActivity(intent);
        }
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    protected void chooseA() {
        // bokA.setText("Get!");
        if (!(getResources().getText(R.string.chooseA).equals(bokA.getText())))
            bokA.setText(getResources().getText(R.string.chooseA));
        else
            bokA.setText(getResources().getText(R.string.chooseB));
    }

// protected void chooseB() {
// bokB.setText("Get!");
// bokA.setText(getResources().getText(R.string.chooseA));
// }

    protected void exitB() {
        GoWith(1);
    }

    protected void exitA() {
        GoWith(2);
    }

    @SuppressWarnings("deprecation")
    protected void tz() {
        String adj = null;
        if (Count == CountValueMax) {
            adj = " 滿滿的 ";
        } else if ((int) (CountValueMax / 2) > Count) {
            adj = "幹勁十足的";
        } else {
            adj = "十分努力的";
        }
        // n.tickerText = "東方 ......你的節操為：" + adj + Count;
        // n.icon = R.drawable.ic_launcher;
        // //n.setLatestEventInfo(MainActivity.this, "東方燈業引進度", "經驗增長速：每分钟 " +
        // Count+ "入", mpi);
        // nm.notify(0, n);
        // Toast.makeText(this, "通知已经弹射", 1).show();
    }

    void GoWith(int Numb) {
//        Message msg = new android.os.Message();
//        msg.obj = Numb;
//        mHander.handleMessage(msg);
        mi.setClass(MainActivity.this, FullscreenActivity.class);
        try {
            if (Numb == 1) {
                mi.setClass(MainActivity.this, FullscreenActivityCool.class);
            } else if (Numb == 2)
                mi.setClass(MainActivity.this, FullscreenActivity3d.class);
        } catch (Exception e) {
            // TODO: handle exception
        }
        MainActivity.this.startActivity(mi);
    }

    Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {

//	.equals(	msg.obj	MainActivity.this.finish();
        }

        ;
    };
    // private int getFile() {
    // int File = getResources().getIdentifier("Test_Movie.mp4", "raw",
    // getPackageName());
    // return File;
    // }

    // private FileDescriptor getFile(String file) {
    // AssetFileDescriptor fd_file = null;
    // try {
    // fd_file = getAssets().openFd(file);
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return fd_file.getFileDescriptor();
    // }
    void getVideo() {// 本地的视频 需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        String videoUrl1 = Environment.getExternalStorageDirectory().getPath() + "/fl1234.mp4";

        // 网络视频
        String videoUrl2 = "static.hdslb.com/miniloader.swf?aid=16075199&page=5";

        Uri uri = Uri.parse(videoUrl2);

        // videoView = (VideoView) this.findViewById(R.id.videoView);
        //
        // // 设置视频控制器
        // videoView.setMediaController(new MediaController(this));
        //
        // // 播放完成回调
        // videoView.setOnCompletionListener(new
        // MyPlayerOnCompletionListener());
        //
        // // 设置视频路径
        // videoView.setVideoURI(uri);
        //
        // // 开始播放视频
        // videoView.start();

    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            // Toast.makeText(LocalVideoActivity.this, "播放完成了",
            // Toast.LENGTH_SHORT).show();
        }

    }

//    @Override
//    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
//        // 当Surface尺寸等参数改变时触发
//        Log.v("Surface Change:::", "surfaceChanged called");
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        // // 当SurfaceView中的Surface被创建的时候被调用
//        // // 在这里我们指定MediaPlayer在当前的Surface中进行播放
//        // player.setDisplay(holder);
//        // // 在指定了MediaPlayer播放的容器后，我们就可以使用prepare或者prepareAsync来准备播放了
//        // player.prepareAsync();
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//
//        Log.v("Surface Destory:::", "surfaceDestroyed called");
//    }
//
//    @Override
//    public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
//        // 当video大小改变时触发
//        // 这个方法在设置player的source后至少触发一次
//        Log.v("Video Size Change", "onVideoSizeChanged called");
//
//    }
//
//    @Override
//    public void onSeekComplete(MediaPlayer arg0) {
//        // seek操作完成时触发
//        Log.v("Seek Completion", "onSeekComplete called");
//
//    }
//
//    @Override
//    public void onPrepared(MediaPlayer player) {
//        // // 当prepare完成后，该方法触发，在这里我们播放视频
//        //
//        // // 首先取得video的宽和高
//        // vWidth = player.getVideoWidth();
//        // vHeight = player.getVideoHeight();
//        //
//        // if (vWidth > currDisplay.getWidth() || vHeight >
//        // currDisplay.getHeight()) {
//        // // 如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
//        // float wRatio = (float) vWidth / (float) currDisplay.getWidth();
//        // float hRatio = (float) vHeight / (float) currDisplay.getHeight();
//        //
//        // // 选择大的一个进行缩放
//        // float ratio = Math.max(wRatio, hRatio);
//        //
//        // vWidth = (int) Math.ceil(vWidth / ratio);
//        // vHeight = (int) Math.ceil(vHeight / ratio);
//        //
//        // // 设置surfaceView的布局参数
//        // surfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth,
//        // vHeight));
//        //
//        // // 然后开始播放视频
//        //
//        // player.start();
//        // }
//    }
//
//    @Override
//    public boolean onInfo(MediaPlayer player, int whatInfo, int extra) {
//        // 当一些特定信息出现或者警告时触发
//        switch (whatInfo) {
//            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
//                break;
//            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
//                break;
//            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
//                break;
//            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
//                break;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onError(MediaPlayer player, int whatError, int extra) {
//        Log.v("Play Error:::", "onError called");
//        switch (whatError) {
//            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
//                Log.v("Play Error:::", "MEDIA_ERROR_SERVER_DIED");
//                break;
//            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
//                Log.v("Play Error:::", "MEDIA_ERROR_UNKNOWN");
//                break;
//            default:
//                break;
//        }
//        return false;
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer player) {
//        // 当MediaPlayer播放完成后触发
//        Log.v("Play Over:::", "onComletion called");
//        // this.finish();
//    }
//
//    Uri getResourceUri(Context context, int res) {
//        try {
//            Context packageContext = context.createPackageContext(context.getPackageName(), Context.CONTEXT_RESTRICTED);
//            Resources resources = packageContext.getResources();
//            String appPkg = packageContext.getPackageName();
//            String resPkg = resources.getResourcePackageName(res);
//            String type = resources.getResourceTypeName(res);
//            String name = resources.getResourceEntryName(res);
//            Uri.Builder uriBuilder = new Uri.Builder();
//            uriBuilder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
//            uriBuilder.encodedAuthority(appPkg);
//            uriBuilder.appendEncodedPath(type);
//            if (!appPkg.equals(resPkg)) {
//                uriBuilder.appendEncodedPath(resPkg + ":" + name);
//            } else {
//
//                uriBuilder.appendEncodedPath(name);
//
//                return uriBuilder.build();
//
//            }
//        } catch (Exception e) {
//
//        }
//        return null;
//
//    }

    void playone() {
        // surfaceView = (SurfaceView) this.findViewById(R.id.video_surface);
        // // 给SurfaceView添加CallBack监听
        // holder = surfaceView.getHolder();
        // holder.addCallback(this);
        // // 为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        // holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // // 下面开始实例化MediaPlayer对象
        // player = new MediaPlayer();
        // player.setOnCompletionListener(this);
        // player.setOnErrorListener(this);
        // player.setOnInfoListener(this);
        // player.setOnPreparedListener(this);
        // player.setOnSeekCompleteListener(this);
        // player.setOnVideoSizeChangedListener(this);
        // Log.v("Begin:::", "surfaceDestroyed called");
        // 然后指定需要播放文件的路径，初始化MediaPlayer
        // String dataPath =
        // // Environment.getExternalStorageDirectory().get+
        // "assets://Test_Movie.mp4";
        // Uri uri = Uri.parse("assets://Test_Movie.mp4");
        // try {
        // // player
        // // // .setDataSource(getFile(File));
        // // // .setDataSource(MainActivity.this, uri)
        // // .setDataSource(getApplicationContext(),
        // // Uri.parse("static.hdslb.com/miniloader.swf?aid=16075199&page=5"));
        // // ;
        // Log.v("Next:::", "surfaceDestroyed called");
        // } catch (IllegalArgumentException e) {
        // e.printStackTrace();
        // } catch (IllegalStateException e) {
        // e.printStackTrace();
        // }
        // // catch (IOException e) {
        // // e.printStackTrace();
        // // }
        // // 然后，我们取得当前Display对象
        // currDisplay = this.getWindowManager().getDefaultDisplay();
    }
}
