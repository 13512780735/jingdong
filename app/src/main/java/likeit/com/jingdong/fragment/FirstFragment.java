package likeit.com.jingdong.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.loopj.android.http.RequestParams;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;


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
import likeit.com.jingdong.MyApplication;
import likeit.com.jingdong.R;
import likeit.com.jingdong.activity.CartActivity;
import likeit.com.jingdong.activity.FilterPopupClassifyWindow;
import likeit.com.jingdong.activity.FilterPopupWindow;
import likeit.com.jingdong.activity.GoodDescActivity;
import likeit.com.jingdong.activity.GoodList01Activity;
import likeit.com.jingdong.activity.GoodsListActivity;
import likeit.com.jingdong.listener.OnClassifyFinishListener;
import likeit.com.jingdong.listener.OnFinishListener;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.network.model.WeatherModel;
import likeit.com.jingdong.utils.HttpUtil;
import likeit.com.jingdong.utils.IpGetUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.utils.StringUtil;
import likeit.com.jingdong.view.BorderRelativeLayout;
import likeit.com.jingdong.view.MyX5WebView;
import likeit.com.jingdong.web.jsinterface.MediaUtility;

public class FirstFragment extends Fragment implements View.OnClickListener, OnClassifyFinishListener {
    MyX5WebView mWebView;
    private com.tencent.smtt.sdk.WebSettings mWebSettings;
    //    WebView mWebView;
//    private WebSettings mWebSettings;
    private String url;
    private OpenFileWebChromeClient mOpenFileWebChromeClient;
    private OnFinishListener listener;
    private ImageView tvRight;
    private dialogCodeFragment dialog;
    private BorderRelativeLayout rlSearch, rlCart, rlShop, rlTop;
    View ll_header;

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
    private TextView count; //购物车数量
    private String temperature;
    private TextView tv_address;
    private String address01;
    private String shopname;
    private FilterPopupClassifyWindow popupWindow1;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnFinishListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectInforCompleted01(String cidName, String id) {
        Log.d("TAG", "8885-->" + cidName);
        Log.d("TAG", "9992-->" + id);
        Intent intent=new Intent(getActivity(),GoodsListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("keywords", "");
        bundle.putString("cidName", cidName);
        bundle.putString("cid", id);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        String dealerid = SharedPreferencesUtils.getString(getActivity(), "dealer_id");
        String openid = SharedPreferencesUtils.getString(getActivity(), "openid");
        address01 = SharedPreferencesUtils.getString(getActivity(), "address");
        shopname = SharedPreferencesUtils.getString(getActivity(), "shopname");
        Log.d("TAG", "address-->" + address01 + "shopname-->" + shopname);
        url = ApiService.Home + "?dealerid=" + dealerid + "&openid=" + openid;
        initUI(view);
//        TimeThread timeThread = new TimeThread();
//        timeThread.run();
        new TimeThread().start();
        //getIPAddress();
        GetNetIp();
        initTopPop();
        initWebViewSettings();
        return view;
    }

    private void initTopPop() {
        popupWindow1 = new FilterPopupClassifyWindow(getActivity());
        popupWindow1.setOnClassifyFinishListener(this);
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失时使背景不透明
               // bgAlpha(1f);
            }
        });
    }
    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
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

    private void initUI(View view) {
        ll_header=view.findViewById(R.id.ll_header);
        mWebView = view.findViewById(R.id.main_web);
        //安卓自带浏览器内核
//        android.webkit.CookieSyncManager.createInstance(MyApplication.mContext);
//        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
//        cookieManager.removeAllCookie();
        //X5浏览器内核
//        CookieSyncManager.createInstance(MyApplication.mContext);
//        CookieManager x5cookieManager = CookieManager.getInstance();
//        x5cookieManager.removeAllCookie();
        //删除浏览器相关数据库
//        MyApplication.mContext.deleteDatabase("webview.db");
//        MyApplication.mContext.deleteDatabase("webviewCache.db");
        mWebView.clearCache(true);
        tv_date = view.findViewById(R.id.tv_date);
        tv_address = view.findViewById(R.id.tv_address);
        tv_address.setText(address01 + "(" + shopname + ")");
        count = view.findViewById(R.id.count);

        tv_time = view.findViewById(R.id.tv_time);
//        tv_date.setText(StringUtil.getCurrentDate());
//        tv_time.setText(StringUtil.getTime());
        rlSearch = view.findViewById(R.id.rl_search);
        rlCart = view.findViewById(R.id.rl_cart);
        rlShop = view.findViewById(R.id.rl_shop);
        rlTop = view.findViewById(R.id.rl_top);
        tvRight = view.findViewById(R.id.tv_right);
        Glide.with(getActivity())
                .load(R.mipmap.ic_code)
                .animate(R.anim.item_alpha_in)
                .priority(Priority.HIGH)
                .into(tvRight);
        mWebView.loadUrl(url);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new dialogCodeFragment(getContext());
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        mWebView.evaluateJavascript("clearCart()", null);

        rlSearch.setOnClickListener(this);
        rlCart.setOnClickListener(this);
        rlTop.setOnClickListener(this);
        rlShop.setOnClickListener(this);
    }

    private void initWebViewSettings() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebSettings = mWebView.getSettings();
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
        mOpenFileWebChromeClient = new OpenFileWebChromeClient(getActivity());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_search:
                showSearchDialog();
                break;
            case R.id.rl_cart:
                Intent intent01 = new Intent(getActivity(), CartActivity.class);
                getActivity().startActivity(intent01);
                break;
            case R.id.rl_shop:
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("keywords", "");
                bundle.putString("cidName", "");
                bundle.putString("cid", "");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_top:
                mWebView.evaluateJavascript("appScrollTop()", null);
                break;
        }
    }

    private void showSearchDialog() {
        SearchDialogFragment dialogFragment = new SearchDialogFragment();
        dialogFragment.show(getFragmentManager(), "dialogFragment");
    }

    private final class JSInterface {
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void openCart(String data) {
//            try {
//                JSONObject jsonObject = new JSONObject(data);
//                Log.d("TAG88888", jsonObject.toString());
//                if (listener != null) {
//                    listener.onSuccess(2);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

        @JavascriptInterface
        public void openDesc(String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                String id = jsonObject.optString("id");
                Bundle bundle = new Bundle();
                bundle.putString("id", id);

                Intent intent = new Intent(getActivity(), GoodDescActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                Log.d("TAG88888", jsonObject.toString());
//                if (listener != null) {
//                    listener.onSuccess(1);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void toMore(String data) {
            Log.d("TAG", "data-->" + data);
            try {
                JSONObject jsonObject = new JSONObject(data);
                String id = jsonObject.optString("id");
                String type = jsonObject.optString("type");
                String title = jsonObject.optString("title");
                Log.d("TAG", "id-->" + id);
                Log.d("TAG", "type-->" + type);
                Log.d("TAG", "title-->" + title);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("type", type);
                bundle.putString("title", title);

                Intent intent = new Intent(getActivity(), GoodList01Activity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
//                Log.d("TAG88888", jsonObject.toString());
//                if (listener != null) {
//                    listener.onSuccess(1);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void OpenGoods(String data) {
//            try {
//                JSONObject jsonObject = new JSONObject(data);
//                Log.d("TAG88888", jsonObject.toString());
//                if (listener != null) {
//                    listener.onSuccess(1);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

        @JavascriptInterface
        public void openCatalog() {
            Log.d("TAG","点击了");
          // bgAlpha(0.6f);
            popupWindow1.showFilterPopup(ll_header);
        }
        @JavascriptInterface
        public void openSearch() {
            showSearchDialog();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String num = SharedPreferencesUtils.getString(getActivity(), "cartNum");
        if (StringUtil.isBlank(num)) {
            num = "0";
        }
        count.setVisibility(View.VISIBLE);
        count.setText(num + "");
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
        if (requestCode == OpenFileWebChromeClient.REQUEST_FILE_PICKER) {
            if (mOpenFileWebChromeClient.mFilePathCallback != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
                        : intent.getData();
                if (result != null) {
                    String path = MediaUtility.getPath(getActivity(),
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
                    String path = MediaUtility.getPath(getActivity(),
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

}
