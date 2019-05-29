package likeit.com.jingdong.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import likeit.com.jingdong.R;
import likeit.com.jingdong.activity.GoodDescActivity;
import likeit.com.jingdong.activity.GoodsListActivity;
import likeit.com.jingdong.listener.OnFinishListener;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.view.BorderRelativeLayout;
import likeit.com.jingdong.view.MyX5WebView;
import likeit.com.jingdong.web.jsinterface.MediaUtility;

public class FirstFragment extends Fragment implements View.OnClickListener {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        String dealerid = SharedPreferencesUtils.getString(getActivity(), "dealer_id");
        String openid = SharedPreferencesUtils.getString(getActivity(), "openid");
        url = ApiService.Home + "?dealerid=" + dealerid + "&openid=" + openid;
        initUI(view);
        initWebViewSettings();
        return view;
    }

    private void initUI(View view) {
        mWebView = view.findViewById(R.id.main_web);
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
                break;
            case R.id.rl_shop:
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_top:
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
        public void OpenDesc(String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                Intent intent = new Intent(getActivity(), GoodDescActivity.class);
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