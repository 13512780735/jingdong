package likeit.com.jingdong.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import likeit.com.jingdong.AppManager;
import likeit.com.jingdong.R;
import likeit.com.jingdong.fragment.dialogCodeFragment;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.utils.HttpUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.utils.StringUtil;
import likeit.com.jingdong.view.BorderRelativeLayout;
import likeit.com.jingdong.view.BorderTextView;
import likeit.com.jingdong.view.MyX5WebView;
import likeit.com.jingdong.fragment.FirstFragment;
import likeit.com.jingdong.web.jsinterface.MediaUtility;

public class GoodDescActivity extends BaseActivity {
    BorderRelativeLayout rlBack;
    private ImageView tvRight;
    MyX5WebView mWebView;
    private String url;
    private com.tencent.smtt.sdk.WebSettings mWebSettings;
    private OpenFileWebChromeClient mOpenFileWebChromeClient;
    private dialogCodeFragment dialog;
    private String id;
    private String temperature;
    private TextView tv_address;

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;//消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss   ");
                    String data = format.format(date);
                    tv_date.setText(data); //更新时间
                    break;
                default:
                    break;

            }
        }
    };
    private String ip;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //  WwIpView.setText("外网IP:" + msg.obj.toString());
                ip = msg.obj.toString();
                Log.d("TAG", "999-->" + ip);
                initData();
            }
        }
    };
    private String address01;
    private String shopname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_good_desc);
        String dealerid = SharedPreferencesUtils.getString(mContext, "dealer_id");
        String openid = SharedPreferencesUtils.getString(mContext, "openid");
        address01 = SharedPreferencesUtils.getString(mContext, "address");
        shopname = SharedPreferencesUtils.getString(mContext, "shopname");
        id = getIntent().getExtras().getString("id");
        url = ApiService.Desc + "?dealerid=" + dealerid + "&openid=" + openid + "&id=" + id;
        Log.d("TAG","url-->"+url);
        initUI();
        new TimeThread().start();
        GetNetIp();
        initWebViewSettings();

    }


    public void GetNetIp() {
        new Thread() {
            @Override
            public void run() {
                String line = "";
                URL infoUrl = null;
                InputStream inStream = null;
                try {
                    infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
                    URLConnection connection = infoUrl.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                        StringBuilder strber = new StringBuilder();
                        while ((line = reader.readLine()) != null)
                            strber.append(line + "\n");
                        inStream.close();
                        // 从反馈的结果中提取出IP地址
                        int start = strber.indexOf("{");
                        int end = strber.indexOf("}");
                        String json = strber.substring(start, end + 1);
                        if (json != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                line = jsonObject.optString("cip");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = line;
                        //向主线程发送消息
                        handler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private String weather, address;

    private void initData() {
        Log.d("TAG", "9995-->" + ip);
        //   String url = ApiService.Weather + "?key=" + "2b53d9a689993" + "&ip=" + ip;
        String url = ApiService.Weather;
        RequestParams params = new RequestParams();
        params.put("key", "2b53d9a689993");
        params.put("ip", ip);
        HttpUtil.get(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", response.toString());
                try {
                    JSONObject object = new JSONObject(response);
                    int retCode = object.optInt("retCode");
                    String msg = object.optString("msg");
                    if (retCode == 200) {
                        JSONArray array = object.optJSONArray("result");
                        Log.d("TAg", array.toString());
                        JSONObject object1 = array.optJSONObject(0);
                        //   WeatherModel weatherModel = array.optJSONObject(0);
                        //   Log.d("TAg", weatherModel.toString());
                        //   WeatherModel weatherModel=object.optJSONObject("result");
                        // WeatherModel weatherModel= (WeatherModel) array.get(0);
                        Log.d("TAG", "555-->" + object1.opt("city"));
                        address = object1.optString("city");
                        JSONArray array1 = object1.optJSONArray("future");
                        JSONObject object2 = array1.optJSONObject(0);
                        weather = object2.optString("dayTime");
                        temperature = object2.optString("temperature");
                        tv_time.setText(address + "  " + weather + " " + temperature);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }


    TextView tv_date, tv_time;

    private void initUI() {
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
//        tv_date.setText(StringUtil.getCurrentDate());
//        tv_time.setText(StringUtil.getTime());
        rlBack = findViewById(R.id.rl_back);
        mWebView = findViewById(R.id.main_web);
        tv_address =findViewById(R.id.tv_address);
        tv_address.setText(address01 + "(" + shopname + ")");
        mWebView.loadUrl(url);
        tvRight = findViewById(R.id.tv_right);
        Glide.with(this)
                .load(R.mipmap.ic_code)
                .animate(R.anim.item_alpha_in)
                .priority(Priority.HIGH)
                .into(tvRight);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new dialogCodeFragment(getApplicationContext());
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
    }

    private void initWebViewSettings() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebSettings = mWebView.getSettings();
        mWebView.clearCache(true);
        mWebSettings.setJavaScriptEnabled(true);    //允许加载javascript
        mWebSettings.setSupportZoom(false);          //允许缩放
        mWebSettings.setBuiltInZoomControls(false);  //原网页基础上缩放
        mWebSettings.setUseWideViewPort(false);      //任意比例缩放
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDefaultTextEncodingName("UTF-8");
        mWebSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        mWebSettings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        mWebSettings.setAllowFileAccessFromFileURLs(true);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        mOpenFileWebChromeClient = new OpenFileWebChromeClient(this);
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;

            }
        });
        mWebView.addJavascriptInterface(new JSInterface(), "app");
    }

    private final class JSInterface {
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void toHome() {
            toActivity(MainActivity.class);
            AppManager.getAppManager().finishAllActivity();
        }

        public void openCart(String data) {
            toActivity(CartActivity.class);
            AppManager.getAppManager().finishAllActivity();
        }
        @JavascriptInterface
        public void setCartNumber(String data) {
            Log.d("TAG","data-->"+data);
            try {
                JSONObject jsonObject = new JSONObject(data);
                String num = jsonObject.optString("num");
                SharedPreferencesUtils.put(mContext,"cartNum",num);
                Log.d("TAG88888", jsonObject.toString());
//                if (listener != null) {
//                    listener.onSuccess(1);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @JavascriptInterface
        public void back() {
            finish();
        }
    }

    public class OpenFileWebChromeClient extends WebChromeClient {
        public static final int REQUEST_FILE_PICKER = 1;
        public ValueCallback<Uri> mFilePathCallback;
        public ValueCallback<Uri[]> mFilePathCallbacks;
        Activity mContext;

        public OpenFileWebChromeClient(Activity mContext) {
            super();
            this.mContext = mContext;
        }

        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> filePathCallback) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }

        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback filePathCallback,
                                    String acceptType) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }

        //  / js上传文件的<input type="file" name="fileField" id="fileField" />事件捕获
        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> filePathCallback,
                                    String acceptType, String capture) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }

        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            mFilePathCallbacks = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
            return true;
        }
    }


    /**
     * 以下是webview的照片上传时候，用于在网页打开android图库文件
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FirstFragment.OpenFileWebChromeClient.REQUEST_FILE_PICKER) {
            if (mOpenFileWebChromeClient.mFilePathCallback != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
                        : intent.getData();
                if (result != null) {
                    String path = MediaUtility.getPath(mContext,
                            result);
                    Uri uri = Uri.fromFile(new File(path));
                    mOpenFileWebChromeClient.mFilePathCallback
                            .onReceiveValue(uri);
                } else {
                    mOpenFileWebChromeClient.mFilePathCallback
                            .onReceiveValue(null);
                }
            }
            if (mOpenFileWebChromeClient.mFilePathCallbacks != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
                        : intent.getData();
                if (result != null) {
                    String path = MediaUtility.getPath(mContext,
                            result);
                    Uri uri = Uri.fromFile(new File(path));
                    mOpenFileWebChromeClient.mFilePathCallbacks
                            .onReceiveValue(new Uri[]{uri});
                } else {
                    mOpenFileWebChromeClient.mFilePathCallbacks
                            .onReceiveValue(null);
                }
            }

            mOpenFileWebChromeClient.mFilePathCallback = null;
            mOpenFileWebChromeClient.mFilePathCallbacks = null;
        }
    }

    @OnClick({R.id.rl_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
