package likeit.com.jingdong.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;

import likeit.com.jingdong.R;
import likeit.com.jingdong.activity.LoginActivity;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.view.BorderTextView;
import likeit.com.jingdong.view.MyX5WebView;

public class FourthFragment extends Fragment implements dialogFragment.OnDeptCallBack {
    MyX5WebView mWebView;
    private String webUrl;
    private LinearLayout ll_bg;
    private BorderTextView tv_logout;
    private TextView versionTv;
    private RelativeLayout tv_rlupdate_version;
    private dialogFragment dialog = null;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fourth, container, false);
        ll_bg = view.findViewById(R.id.ll_bg);
        ll_bg.setVisibility(View.GONE);
        dialogShow();

        return view;
    }

    private void dialogShow() {
        dialog = new dialogFragment(getContext());
        dialog.show();
    }

    private void initUI(View view) {

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_bg.getLayoutParams();

        params.width = height1 / 3;
        ll_bg.setLayoutParams(params);
        tv_logout = view.findViewById(R.id.tv_logout);
        versionTv = view.findViewById(R.id.version_tv);
        tv_rlupdate_version = view.findViewById(R.id.tv_rlupdate_version);
        versionTv.setText(getVersion());
        tv_rlupdate_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beta.checkUpgrade();
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.put(getActivity(), "pwd", "");
                SharedPreferencesUtils.put(getActivity(), "openid", "");
                SharedPreferencesUtils.put(getActivity(), "expire", "");
                SharedPreferencesUtils.put(getActivity(), "token", "");
                SharedPreferencesUtils.put(getActivity(), "dealer_id", "");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void onCallBack(String st) {
        ll_bg.setVisibility(View.VISIBLE);
        initUI(view);
    }
}
