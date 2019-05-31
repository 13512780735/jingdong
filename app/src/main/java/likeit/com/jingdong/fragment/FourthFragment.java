package likeit.com.jingdong.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import likeit.com.jingdong.R;
import likeit.com.jingdong.activity.LoginActivity;
import likeit.com.jingdong.listener.OnFinishListener;
import likeit.com.jingdong.listener.OnLoginInforCompleted;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.utils.StringUtil;
import likeit.com.jingdong.view.BorderTextView;
import likeit.com.jingdong.view.MyX5WebView;

public class FourthFragment extends Fragment implements OnLoginInforCompleted {
    MyX5WebView mWebView;
    private String webUrl;
    private LinearLayout ll_bg;
    private BorderTextView tv_logout;
    private TextView versionTv;
    private RelativeLayout tv_rlupdate_version;
    private dialogFragment dialog = null;
    View view;


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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fourth, container, false);
        ll_bg = view.findViewById(R.id.ll_bg);

        dialogShow();
        initUI(view);
        new TimeThread().start();
        return view;
    }

    TextView tv_date, tv_time;

    private void dialogShow() {
        tv_date = view.findViewById(R.id.tv_date);
        tv_time = view.findViewById(R.id.tv_time);
//        tv_date.setText(StringUtil.getCurrentDate());
//        tv_time.setText(StringUtil.getTime());
        ll_bg.setVisibility(View.GONE);
        dialogFragment dialog = new dialogFragment();
        dialog.setOnLoginInforCompleted(this);
        dialog.show(this.getFragmentManager(), "dialogFragment");
    }

    private void initUI(View view) {
        // dialog.dismiss();
        // ll_bg.setVisibility(View.VISIBLE);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_bg.getLayoutParams();

        params.height = height1 / 3;
        params.width = height1 / 2;
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
                SharedPreferencesUtils.put(getActivity(), "phone", "");
                SharedPreferencesUtils.put(getActivity(), "openid", "");
                SharedPreferencesUtils.put(getActivity(), "expire", "");
                SharedPreferencesUtils.put(getActivity(), "token", "");
                SharedPreferencesUtils.put(getActivity(), "dealer_id", "");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private OnFinishListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnFinishListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void inputLoginInforCompleted(String userName) {
        Log.d("TAG", "点击了23");
        if ("1".equals(userName)) {
            ll_bg.setVisibility(View.VISIBLE);
        } else {
            listener.onSuccess(1);
            //  dialog.dismiss();
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }
}
