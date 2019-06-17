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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.loopj.android.http.RequestParams;
import com.tencent.bugly.beta.Beta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import likeit.com.jingdong.R;
import likeit.com.jingdong.activity.LoginActivity;
import likeit.com.jingdong.listener.OnFinishListener;
import likeit.com.jingdong.listener.OnLoginInforCompleted;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.utils.HttpUtil;
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
    private String temperature;
    private TextView tv_address;
    private String address01;
    private String shopname;
    private ImageView tvRight;
    private dialogCodeFragment dialog01;


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
        address01 = SharedPreferencesUtils.getString(getActivity(), "address");
        shopname = SharedPreferencesUtils.getString(getActivity(), "shopname");
        dialogShow();
        initUI(view);
        new TimeThread().start();
        GetNetIp();
        return view;
    }

    TextView tv_date, tv_time;
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
        tv_address = view.findViewById(R.id.tv_address);
        tv_address.setText(address01 + "(" + shopname + ")");
        tv_rlupdate_version = view.findViewById(R.id.tv_rlupdate_version);
        versionTv.setText(getVersion());
        tv_rlupdate_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beta.checkUpgrade();
            }
        });
        tvRight = view.findViewById(R.id.tv_right);
        Glide.with(getActivity())
                .load(R.mipmap.ic_code)
                .animate(R.anim.item_alpha_in)
                .priority(Priority.HIGH)
                .into(tvRight);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog01 = new dialogCodeFragment(getContext());
                dialog01.setCanceledOnTouchOutside(true);
                dialog01.show();
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
