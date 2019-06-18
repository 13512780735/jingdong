package likeit.com.jingdong.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.RequestParams;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import likeit.com.jingdong.AppManager;
import likeit.com.jingdong.R;
import likeit.com.jingdong.adapter.GoodList01Adapter;
import likeit.com.jingdong.fragment.dialogCodeFragment;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.GoodsListMoreModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.HttpUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.view.BorderRelativeLayout;
import rx.Subscriber;

public class GoodList01Activity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private String id, type, title;
    private String address01;
    private String shopname;
    private TextView tv_date, tv_time;
    BorderRelativeLayout rlBack, rlHome, rlTop;
    private ImageView tvRight;
    private TextView tv_address;
    private String temperature;
    private dialogCodeFragment dialog;
    private int pageNum = 1;
    private static final int PAGE_SIZE = 1;//为什么是6呢？
    private int mNextRequestPage = 1;
    private boolean isErr = true;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    private GoodList01Adapter mAdapter;
    private GoodsListMoreModel goodsListModel;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_good_list01);
        String dealerid = SharedPreferencesUtils.getString(mContext, "dealer_id");
        String openid = SharedPreferencesUtils.getString(mContext, "openid");
        address01 = SharedPreferencesUtils.getString(mContext, "address");
        shopname = SharedPreferencesUtils.getString(mContext, "shopname");
        id = getIntent().getExtras().getString("id");
        type = getIntent().getExtras().getString("type");
        title = getIntent().getExtras().getString("title");
        initUI();
        //initTopPop();
        new TimeThread().start();
        GetNetIp();
    }

    private void initUI() {
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
//        tv_date.setText(StringUtil.getCurrentDate());
//        tv_time.setText(StringUtil.getTime());
        tvRight = findViewById(R.id.tv_right);
        rlBack = findViewById(R.id.rl_back);
        rlHome = findViewById(R.id.rl_home);
        rlTop = findViewById(R.id.rl_top);
        tv_address = findViewById(R.id.tv_address);
        tv_address.setText(address01 + "(" + shopname + ")");
//        rlBack.setOnClickListener(this);
//        rlHome.setOnClickListener(this);
//        rlTop.setOnClickListener(this);
        Glide.with(this)
                .load(R.mipmap.ic_code)
                .animate(R.anim.item_alpha_in)
                .priority(Priority.HIGH)
                .into(tvRight);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new dialogCodeFragment(mContext);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        initAdapter();
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
//
//    private void initTopPop() {
//        popupWindow = new FilterPopupWindow(GoodsListActivity.this);
//        popupWindow.setOnSelectInforCompleted(this);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                //popupwindow消失时使背景不透明
//                bgAlpha(1f);
//            }
//        });
//
//
//        popupWindow1 = new FilterPopupClassifyWindow(GoodsListActivity.this);
//        popupWindow1.setOnClassifyFinishListener(this);
//        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                //popupwindow消失时使背景不透明
//                bgAlpha(1f);
//            }
//        });
//    }

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

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        mAdapter = new GoodList01Adapter(R.layout.good_listview_items, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.disableLoadMoreIfNotFullPage();
        // mSwipeRefreshLayout.setOnRefreshListener(this);
        initData01(pageNum, false);
        mCurrentCounter = mAdapter.getData().size();


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()

        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String id = data.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                toActivity(GoodDescActivity.class, bundle);
            }
        });
    }

    private List<GoodsListMoreModel.ListBeanX.ListBean> data = new ArrayList<>();

    private void initData01(int pageNum, final boolean isloadmore) {
        String openid = SharedPreferencesUtils.getString(mContext, "openid");
        String dealer_id = SharedPreferencesUtils.getString(mContext, "dealer_id");
        Log.e("openid-->", openid);
        RetrofitUtil.getInstance().getGoodsListMore(openid, dealer_id, type, id, String.valueOf(pageNum), new Subscriber<BaseResponse<GoodsListMoreModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<GoodsListMoreModel> baseResponse) {
                if (baseResponse.code == 200) {
                    goodsListModel = baseResponse.getData();
                    List<GoodsListMoreModel.ListBeanX.ListBean> list = goodsListModel.getList().get(0).getList();
                    TOTAL_COUNTER = Integer.valueOf(goodsListModel.getTotal());
                    // Log.json(baseResponse.data.getList().toString());
                    if (list != null && list.size() > 0) {
                        if (!isloadmore) {
                            data = list;
                        } else {
                            data.addAll(list);
                        }
                        mAdapter.setNewData(data);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.setEmptyView(R.layout.notdata_view);
                    }
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        //TOTAL_COUNTER = Integer.valueOf(GoodsListModel.ListBean.);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    //数据全部加载完毕
                    mAdapter.loadMoreEnd();
                } else {
                    if (isErr) {
                        //成功获取更多数据
                        //  mQuickAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                        pageNum += 1;
                        initData01(pageNum, true);
                        mCurrentCounter = mAdapter.getData().size();
                        mAdapter.loadMoreComplete();
                    } else {
                        //获取更多数据失败
                        isErr = true;
                        mAdapter.loadMoreFail();

                    }
                }
            }

        }, 3000);
    }

    @Override
    public void onRefresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//禁止加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // mAdapter.setNewData(data);
                data.clear();
                isErr = true;
                mCurrentCounter = PAGE_SIZE;
                pageNum = 1;//页数置为1 才能继续重新加载
                initData01(pageNum, false);
                mAdapter.setEnableLoadMore(true);//启用加载
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @OnClick({R.id.rl_back, R.id.rl_top, R.id.rl_home})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_top:
                mRecyclerView.smoothScrollToPosition(0);//滑到顶部
                break;
            case R.id.rl_home:
                toActivity(MainActivity.class);
                AppManager.getAppManager().finishAllActivity();
                break;

        }
    }
}
