//package likeit.com.jingdong.web.jsinterface;
//
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.ValueCallback;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import likeit.com.jingdong.R;
//import likeit.com.jingdong.web.base.BaseFragment;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class JsInterfaceFragment extends BaseFragment<JsInterfaceContract.Presenter> implements JsInterfaceContract.View {
//
//    private static final boolean IS_NEED_CLAER = true;
//    @BindView(R.id.tv_toolbar_title)
//    TextView mTvToolbarTitle;
//    @BindView(R.id.img_toolbar_back)
//    ImageView mImgToolbarBack;
//    @BindView(R.id.main_web)
//    MyX5WebView mWebView;
////    @BindView(R.id.load_error_layout)
////    LinearLayout ll_control_error;
////    @BindView(R.id.online_error_btn_retry)
////    RelativeLayout error;
//
//
//
//    private String mobile, pwd;
//    private boolean mIsLoadSuccess;
//    private View mErrorView;
//    private com.tencent.smtt.sdk.WebSettings mWebSettings;
//    private String token;
//    private String url;
//    private int RESULT_OK;
//    private OpenFileWebChromeClient mOpenFileWebChromeClient;
//    private String succurl;
//    private String faildurl;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_js_interface, container, false);
//        mUnbinder = ButterKnife.bind(this, view);
//        mobile = SharedPreferencesUtils.getString(getActivity(), "phone");
//        pwd = SharedPreferencesUtils.getString(getActivity(), "pwd");
//        token = SharedPreferencesUtils.getString(getActivity(), "token");
//        String Login = SharedPreferencesUtils.getString(getActivity(), "login");
//        String openid = SharedPreferencesUtils.getString(getActivity(), "openid");
//        String webUrl = SharedPreferencesUtils.getString(getActivity(), "webUrl");
//        XLog.d("Login-->" + Login);
//        XLog.d("token-->" + token);
//        url = webUrl + "&openid=" + openid;
////        if ("1".equals(Login)) {
////            url = Consts.HOME_HOST + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile";
////
////        } else {
////            url = Consts.HOME_HOST + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&token=" + token;
////            // url="http://aoquan.maimaitoo.com/app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=member&mobile=" + mobile + "&pwd=" + pwd + "&act=auto";
////        }
////        error.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                ll_control_error.setVisibility(View.GONE);
////                mWebView.reload();
////            }
////        });
//        // setupUI();
//        initWebViewSettings();
//        return view;
//    }
//
//    private void initWebViewSettings() {
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new JsInterfaceLogic(this), "app");
//        mWebSettings = mWebView.getSettings();
//        mWebSettings.setJavaScriptEnabled(true);    //允许加载javascript
//        mWebSettings.setSupportZoom(false);          //允许缩放
//        mWebSettings.setBuiltInZoomControls(false);  //原网页基础上缩放
//        mWebSettings.setUseWideViewPort(false);      //任意比例缩放
//        mWebSettings.setLoadWithOverviewMode(true);
//        mWebSettings.setDomStorageEnabled(true);
//        mWebSettings.setDefaultTextEncodingName("UTF-8");
//        mWebSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
//        mWebSettings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
//        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
//        mWebSettings.setAllowFileAccessFromFileURLs(true);
//        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
//        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
//        mOpenFileWebChromeClient = new OpenFileWebChromeClient(getActivity());
//        //mWebView.setWebChromeClient(mOpenFileWebChromeClient);
//
//        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
//        // settings 的设计
//        mWebView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//                    mWebView.goBack();
//                    return true;
//                }
//                return false;
//
//            }
//        });
//        mImgToolbarBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mWebView.goBack();
//            }
//        });
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMessage(PayEventMessage event) {
//        String type = event.getType();
//        XLog.e("evenyType-->" + type);
//        if ("1".equals(type)) {
//            mPresenter.clickBtn1();
//        } else {
//            mPresenter.clickBtn2();
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Override
//    public void renderUrl(@NonNull String url) {
//
//        mWebView.loadUrl(url);
//
//    }
//
//
////    private void setupUI() {
////        mWebView.getSettings().setJavaScriptEnabled(true);
////        mWebView.addJavascriptInterface(new JsInterfaceLogic(this), "app");
////        mWebSettings = mWebView.getSettings();
////        mWebSettings.setJavaScriptEnabled(true);    //允许加载javascript
////        mWebSettings.setSupportZoom(false);          //允许缩放
////        mWebSettings.setBuiltInZoomControls(false);  //原网页基础上缩放
////        mWebSettings.setUseWideViewPort(false);      //任意比例缩放
////        mWebSettings.setLoadWithOverviewMode(true);
////        mWebSettings.setDomStorageEnabled(true);
////
////        mWebSettings.setAppCacheEnabled(true);
////        mWebSettings.setSaveFormData(true);
////        mWebSettings.setDomStorageEnabled(true);
////        mWebSettings.setDatabaseEnabled(true);
////        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
////        mWebSettings.setDefaultTextEncodingName("UTF-8");
////        mWebSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
////        mWebSettings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
////        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
////        mWebSettings.setAllowFileAccessFromFileURLs(true);
////        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
////        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
////        mOpenFileWebChromeClient = new OpenFileWebChromeClient(getActivity());
////        mWebView.setWebChromeClient(mOpenFileWebChromeClient);
//////        mWebView.setWebChromeClient(new WebChromeClient() {
//////                        @Override
//////            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//////                result.confirm();
//////                return super.onJsAlert(view, url, message, result);
//////            }
//////        });
////        mWebView.setWebViewClient(new WebViewClient() {
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                view.loadUrl(url);
////
////                return true;
////            }
////
////            @Override
////            public void onPageStarted(WebView view, String url, Bitmap favicon) {
////                super.onPageStarted(view, url, favicon);
////                //  mDialog.show();
////
////            }
////
////            @Override
////            public void onPageFinished(WebView view, String url) {
////                // mWebView.setVisibility(View.VISIBLE);
////                mDialog.dismiss();
////                //hideErrorPage();
////                //super.onPageFinished(view, url);
////                if (!isError) {
////                    isSuccess = true;
////                    //回调成功后的相关操作
////                    ll_control_error.setVisibility(View.GONE);
////                    mWebView.setVisibility(View.VISIBLE);
////                } else {
////                    isError = false;
////                    ll_control_error.setVisibility(View.VISIBLE);
////                }
////            }
////
////
////            @Override
////            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
////                super.onReceivedError(view, request, error);
////                isError = true;
////                isSuccess = false;
////                //6.0以上执行
////                mWebView.setVisibility(View.GONE);
////                ll_control_error.setVisibility(View.VISIBLE);
////            }
////        });
////
////        mWebView.setOnKeyListener(new View.OnKeyListener() {
////            @Override
////            public boolean onKey(View v, int keyCode, KeyEvent event) {
////                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
////                    mWebView.goBack();
////                    return true;
////                }
////                return false;
////
////            }
////        });
////        mImgToolbarBack.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                mWebView.goBack();
////            }
////        });
////    }
//
//    @Override
//    public void showNativeMessage(String message) {
//        super.showNativeMessage(message);
//        mWebView.clearCache(true);
//        mWebView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (IS_NEED_CLAER) {
//                    mWebView.clearHistory();
//                }
//            }
//        }, 1000);
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);
//        AppManager.getAppManager().finishAllActivity();
//    }
//
//    @Override
//    public void showWeChatPay(String str) {
//        super.showWeChatPay(str);
//        XLog.e("微信支付：" + str);
//        XLog.json(str);
//
//        try {
//            JSONObject object = new JSONObject(str);
//            String appId = object.optString("appid");
//            String partnerId = object.optString("mch_id");
//            String prepayId = object.optString("prepay_id");
//            String nonceStr = object.optString("nonce_str");
//            String packageValue = "Sign=Wxpay";
//            succurl = object.optString("succurl");
//            faildurl = object.optString("faildurl");
//            SharedPreferencesUtils.put(getActivity(), "succurl", succurl);
//            SharedPreferencesUtils.put(getActivity(), "faildurl", faildurl);
//            long timeMills = System.currentTimeMillis() / 1000;
//            String timeStamp = String.valueOf(timeMills);
//            String stringA =
//                    "appid=" + appId
//                            + "&noncestr=" + nonceStr
//                            + "&package=" + packageValue
//                            + "&partnerid=" + partnerId
//                            + "&prepayid=" + prepayId
//                            + "&timestamp=" + timeStamp;
//            String key = "dahgdrh678fdh4sdhtui527gjsdtasaa";
//            String stringSignTemp = stringA + "&key=" + key;
//            String sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();
//            sendPayred(appId, partnerId, prepayId, nonceStr, packageValue, sign, timeStamp);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @Override
//    public void showAlipayPay(String str) {
//        super.showAlipayPay(str);
//        XLog.e("支付宝支付：" + str);
//
//        alipay(str);
//    }
//
//
//    @Override
//    public void execJavaScript(@NonNull String js) {
//        mWebView.evaluateJavascript(js, new com.tencent.smtt.sdk.ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
//
//            }
//        });
////        mWebView.evaluateJavascript(js, new ValueCallback<String>() {
////            @Override
////            public void onReceiveValue(String s) {
////                //showNativeMessage("调用JS方法后得到的返回值是：" + s);
////                //window.history.go(-1);
////            }
////        });
//    }
//
//    /**
//     * 支付宝支付
//     *
//     * @param data
//     */
//    private void alipay(String data) {
//        try {
//            JSONObject object = new JSONObject(data);
//            final String payInfo = object.optString("data");
//            succurl = object.optString("succurl");
//            faildurl = object.optString("faildurl");
////            SharedPreferencesUtils.put(getActivity(), "succurl", succurl);
////            SharedPreferencesUtils.put(getActivity(), "faildurl", faildurl);
//
//            // final String payInfo = data;
//
//            Runnable payRunnable = new Runnable() {
//
//                @Override
//                public void run() {
//                    // 构造PayTask 对象
//                    PayTask alipay = new PayTask(getActivity());
//                    // 调用支付接口，获取支付结果
//                    String result = alipay.pay(payInfo, true);
//                    Message msg = new Message();
//                    msg.what = SDK_PAY_FLAG;
//                    msg.obj = result;
//                    mHandler.sendMessage(msg);
//                }
//            };
//
//            // 必须异步调用
//            Thread payThread = new Thread(payRunnable);
//            payThread.start();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 微信支付
//     *
//     * @param appId
//     * @param partnerId
//     * @param prepayId
//     * @param nonceStr
//     * @param packageValue
//     * @param sign
//     * @param timeStamp
//     */
//    private void sendPayred(String appId, String partnerId, String prepayId, String nonceStr, String packageValue, String sign, String timeStamp) {
//        PayReq request = new PayReq();
//        request.appId = appId;
//        request.partnerId = partnerId;
//        request.prepayId = prepayId;
//        request.nonceStr = nonceStr;
//        request.packageValue = packageValue;
//        request.sign = sign;
//        request.timeStamp = timeStamp;
//        api.sendReq(request);
//    }
//
//    @Override
//    public void appShare(String str) {
//        super.appShare(str);
//        try {
//            JSONObject object = new JSONObject(str);
//            String title = object.optString("title");
//            String desc = object.optString("desc");
//            String text = object.optString("text");
//            String imgPaths = object.optString("imgPaths");
//            String url = object.optString("url");
//            showShare(title, desc, text, imgPaths, url);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //alipay(str);
//
//    }
//
//    /**
//     * 打电话
//     * Intent intent = new Intent();
//     * //设置拨打电话的动作
//     * intent.setAction(Intent.ACTION_CALL);
//     * //设置拨打电话的号码
//     * intent.setData(Uri.parse("tel:" + phone));
//     * //开启打电话的意图
//     * startActivity(intent);
//     */
//    @Override
//    public void tel(String str) {
//        super.tel(str);
//        XLog.e("tel:" + str);
//        Intent intent = new Intent();
//        //设置拨打电话的动作
//        intent.setAction(Intent.ACTION_CALL);
//        //设置拨打电话的号码
//        intent.setData(Uri.parse("tel:" + str));
//        //开启打电话的意图
//        startActivity(intent);
//    }
//
//    /**
//     * 定位
//     *
//     * @param str
//     */
//    @Override
//    public void navigation(String str) {
//        super.navigation(str);
//        XLog.e("navigation:" + str);
//        try {
//            JSONObject object = new JSONObject(str);
//            String mAddressStr = object.optString("address");
//            String lat = object.optString("lat");
//            String lng = object.optString("lng");
//            XLog.json(str);
//            XLog.e("address" + mAddressStr);
//            XLog.e("lat" + lat);
//            XLog.e("lng" + lng);
//
//            double mLat = Double.valueOf(lat);
//            double mLng = Double.valueOf(lng);
//            goToGaodeMap(mLat, mLng, mAddressStr);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 跳转高德地图
//     */
//    private void goToGaodeMap(double mLat, double mLng, String mAddressStr) {
//        if (!isInstalled("com.autonavi.minimap")) {
//            ToastUtils.showToast(getActivity(), "请先安装高德地图客户端");
//            return;
//        }
//        LatLng endPoint = BD2GCJ(new LatLng(mLat, mLng));//坐标转换
//        StringBuffer stringBuffer = new StringBuffer("androidamap://route?sourceApplication=").append("amap");
//        stringBuffer.append("&dlat=").append(endPoint.latitude)
//                .append("&dlon=").append(endPoint.longitude)
//                .append("&dev=").append(0)
//                // .append("&type=").append("drive")
//                .append("&dname=" + mAddressStr)
//                .append("&t=").append(0);
//        //  .append("&style=").append(2);
//        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
//        intent.setPackage("com.autonavi.minimap");
//        startActivity(intent);
//    }
//
//    /**
//     * 检测程序是否安装
//     *
//     * @param packageName
//     * @return
//     */
//    private boolean isInstalled(String packageName) {
//        PackageManager manager = getActivity().getPackageManager();
//        //获取所有已安装程序的包信息
//        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
//        if (installedPackages != null) {
//            for (PackageInfo info : installedPackages) {
//                if (info.packageName.equals(packageName))
//                    return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * BD-09 坐标转换成 GCJ-02 坐标
//     */
//    public static LatLng BD2GCJ(LatLng bd) {
//        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
//        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
//        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
//
//        double lng = z * Math.cos(theta);//lng
//        double lat = z * Math.sin(theta);//lat
//        return new LatLng(lat, lng);
//    }
//
//    /**
//     * GCJ-02 坐标转换成 BD-09 坐标
//     */
//    public static LatLng GCJ2BD(LatLng bd) {
//        double x = bd.longitude, y = bd.latitude;
//        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
//        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
//        double tempLon = z * Math.cos(theta) + 0.0065;
//        double tempLat = z * Math.sin(theta) + 0.006;
//        return new LatLng(tempLat, tempLon);
//    }
//
//    /**
//     * 跳链接
//     *
//     * @param str
//     */
//    @Override
//    public void app_linkurl(String str) {
//        super.app_linkurl(str);
//
//        try {
//            JSONObject object = new JSONObject(str);
//            String linkurl = object.optString("linkurl");
//            String id = object.optJSONObject("params").optString("id");
//            String weburl = object.optString("weburl");
//            String type = object.optJSONObject("params").optString("type");
//            String member = object.optJSONObject("params").optString("member");
//            XLog.json(str);
//            XLog.e("linkurl" + linkurl);
//            XLog.e("id" + id);
//            XLog.e("weburl" + weburl);
//            if (!TextUtils.isEmpty(type)) {
//                IntentUtils.intentTos(getActivity(), linkurl, id, weburl, type);
//            } else if (!TextUtils.isEmpty(member)) {
//                IntentUtils.intentTos02(getActivity(), linkurl, id, weburl, member);
//            }
//            IntentUtils.intentTo(getActivity(), linkurl, id, weburl);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 商品詳情分享
//     *
//     * @param title
//     * @param desc
//     * @param text
//     * @param imgPaths
//     * @param url
//     */
//
//    private void showShare(String title, String desc, String text, String imgPaths, String url) {
//        XLog.e("url" + url);
//        XLog.e("imgPaths" + imgPaths);
//        XLog.e("title" + title);
//        Resources res = getActivity().getResources();
//        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // title标题，微信、QQ和QQ空间等平台使用
//        oks.setTitle(title);
//        // titleUrl QQ和QQ空间跳转链接
//        oks.setTitleUrl(url);
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(text);
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImageData(bmp);
//        oks.setImageUrl(imgPaths);
//
//        oks.setSite(getActivity().getString(R.string.app_name));
//        // url在微信、微博，Facebook等平台中使用
//        oks.setUrl(url);
//        // comment是我对这条分享的评论，仅在人人网使用
//        // 启动分享GUI
//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Toast.makeText(getActivity(), "成功", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Toast.makeText(getActivity(), "错误", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
//            }
//        });
//        oks.show(getActivity());
//    }
//
//
//    public class OpenFileWebChromeClient extends WebChromeClient {
//        public static final int REQUEST_FILE_PICKER = 1;
//        public ValueCallback<Uri> mFilePathCallback;
//        public ValueCallback<Uri[]> mFilePathCallbacks;
//        Activity mContext;
//
//        public OpenFileWebChromeClient(Activity mContext) {
//            super();
//            this.mContext = mContext;
//        }
//
//        // Android < 3.0 调用这个方法
//        public void openFileChooser(ValueCallback<Uri> filePathCallback) {
//            mFilePathCallback = filePathCallback;
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("*/*");
//            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
//                    REQUEST_FILE_PICKER);
//        }
//
//        // 3.0 + 调用这个方法
//        public void openFileChooser(ValueCallback filePathCallback,
//                                    String acceptType) {
//            mFilePathCallback = filePathCallback;
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("*/*");
//            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
//                    REQUEST_FILE_PICKER);
//        }
//
//        //  / js上传文件的<input type="file" name="fileField" id="fileField" />事件捕获
//        // Android > 4.1.1 调用这个方法
//        public void openFileChooser(ValueCallback<Uri> filePathCallback,
//                                    String acceptType, String capture) {
//            mFilePathCallback = filePathCallback;
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("*/*");
//            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
//                    REQUEST_FILE_PICKER);
//        }
//
//        @Override
//        public boolean onShowFileChooser(WebView webView,
//                                         ValueCallback<Uri[]> filePathCallback,
//                                         FileChooserParams fileChooserParams) {
//            mFilePathCallbacks = filePathCallback;
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("*/*");
//            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
//                    REQUEST_FILE_PICKER);
//            return true;
//        }
//    }
//
//
//    /**
//     * 以下是webview的照片上传时候，用于在网页打开android图库文件
//     */
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (requestCode == OpenFileWebChromeClient.REQUEST_FILE_PICKER) {
//            if (mOpenFileWebChromeClient.mFilePathCallback != null) {
//                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
//                        : intent.getData();
//                if (result != null) {
//                    String path = MediaUtility.getPath(getActivity(),
//                            result);
//                    Uri uri = Uri.fromFile(new File(path));
//                    mOpenFileWebChromeClient.mFilePathCallback
//                            .onReceiveValue(uri);
//                } else {
//                    mOpenFileWebChromeClient.mFilePathCallback
//                            .onReceiveValue(null);
//                }
//            }
//            if (mOpenFileWebChromeClient.mFilePathCallbacks != null) {
//                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
//                        : intent.getData();
//                if (result != null) {
//                    String path = MediaUtility.getPath(getActivity(),
//                            result);
//                    Uri uri = Uri.fromFile(new File(path));
//                    mOpenFileWebChromeClient.mFilePathCallbacks
//                            .onReceiveValue(new Uri[]{uri});
//                } else {
//                    mOpenFileWebChromeClient.mFilePathCallbacks
//                            .onReceiveValue(null);
//                }
//            }
//
//            mOpenFileWebChromeClient.mFilePathCallback = null;
//            mOpenFileWebChromeClient.mFilePathCallbacks = null;
//        }
//    }
//}
