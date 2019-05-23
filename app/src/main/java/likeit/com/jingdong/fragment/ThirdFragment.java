package likeit.com.jingdong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import likeit.com.jingdong.R;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.view.MyX5WebView;

public class ThirdFragment extends Fragment {
    MyX5WebView mWebView;
    private String webUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        initUI(view);
        return view;
    }
    private void initUI(View view) {
        mWebView = view.findViewById(R.id.main_web);
        webUrl = ApiService.Cart;
        Log.e("TAg", webUrl);
        mWebView.loadUrl(webUrl);
        // initWebViewSettings();
    }
}
