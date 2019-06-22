package likeit.com.jingdong.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import likeit.com.jingdong.adapter.GoodListAdapter;
import likeit.com.jingdong.fragment.ClassifyDialogFragment;
import likeit.com.jingdong.fragment.FilterDialog01Fragment;
import likeit.com.jingdong.fragment.dialogCodeFragment;
import likeit.com.jingdong.listener.OnClassifyFinishListener;
import likeit.com.jingdong.listener.OnFilterFinishListener;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.HttpUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.utils.StringUtil;
import likeit.com.jingdong.view.BorderRelativeLayout;
import rx.Subscriber;

public class GoodsListActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, OnFilterFinishListener, OnClassifyFinishListener {
    BorderRelativeLayout rlBack, rlHome, rlTop;
    private ImageView tvRight;
    private dialogCodeFragment dialog;
    @BindView(R.id.search_content_et)
    EditText search_content_et;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_01)
    TextView tv_01;

    @BindView(R.id.tv_synthesis_sort)
    TextView mTvSynthesisSort;
    @BindView(R.id.layout_synthesis_sort)
    RelativeLayout mLayoutSynthesisSort;
    @BindView(R.id.tv_sales_sort)
    TextView mTvSalesSort;
    @BindView(R.id.layout_sales_sort)
    RelativeLayout mLayoutSalesSort;
    @BindView(R.id.tv_sort_price)
    TextView mTvSortPrice;
    @BindView(R.id.iv_price)
    ImageView iv_price;
    @BindView(R.id.iv_sales)
    ImageView iv_sales;
    @BindView(R.id.layout_expert_service)
    RelativeLayout mLayoutExpertService;
    @BindView(R.id.tv_filter_sort)
    TextView mTvFilterSort;
    @BindView(R.id.layout_filter_sort)
    RelativeLayout mLayoutFilterSort;
    @BindView(R.id.main)
    View main;

    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.ll_header)
    LinearLayout ll_header;
    private boolean synthesisFalg = false;
    private boolean salesFalg = false;
    private boolean priceFalg = false;
    private int priceFalg01 = 0; //判断升序降序
    private boolean filterFalg = false;

    private int pageNum = 1;
    private static final int PAGE_SIZE = 1;//为什么是6呢？
    private int mNextRequestPage = 1;
    private boolean isErr = true;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;

    private String attribute, merchid, order, by, pricemin, pricemax, keywords, brandsid, cid;
    private GoodListAdapter mAdapter;
    private GoodsListModel goodsListModel;
    private TextView tv_date, tv_time;
    private FilterPopupWindow popupWindow;
    private FilterPopupClassify01Window popupWindow1;
    private String temperature;
    private TextView tv_address;
    private String cidName1;


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
                initData1();
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
    private String address01;
    private String shopname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_list);
        attribute = "";
        merchid = "";
        order = "";
        by = "";
        pricemin = "";
        pricemax = "";
        cidName1 = getIntent().getExtras().getString("cidName");
        cid = getIntent().getExtras().getString("cid");
        keywords = getIntent().getExtras().getString("keywords");
        address01 = SharedPreferencesUtils.getString(mContext, "address");
        shopname = SharedPreferencesUtils.getString(mContext, "shopname");
        brandsid = "";
        initUI();
        initTopPop();
        new TimeThread().start();
        GetNetIp();
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

    private void initTopPop() {
        popupWindow = new FilterPopupWindow(GoodsListActivity.this);
        popupWindow.setOnSelectInforCompleted(this);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失时使背景不透明
                bgAlpha(1f);
            }
        });


        popupWindow1 = new FilterPopupClassify01Window(GoodsListActivity.this);
        popupWindow1.setOnClassifyFinishListener(this);
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失时使背景不透明
                bgAlpha(1f);
            }
        });
    }

    private String weather, address;

    private void initData1() {
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

    private List<GoodsListModel.ListBean> data = new ArrayList<>();

    private void initUI() {

        search_content_et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    keywords = search_content_et.getText().toString().trim();
                    initAdapter();
                    hideinfo();
                }
                return false;
            }
        });

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
        tv_01.setText("所以分类：" + cidName1);

        iv_price.setBackgroundResource(R.mipmap.icon_sort_up_down);
        iv_sales.setBackgroundResource(R.mipmap.icon_sort_up_down);
        mTvSynthesisSort.setTextColor(Color.parseColor("#F93955"));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        data.clear();
        initAdapter();

    }

    private void hideinfo() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodListAdapter(R.layout.good_listview_items, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.disableLoadMoreIfNotFullPage();
        // mSwipeRefreshLayout.setOnRefreshListener(this);
        initData(pageNum, false);
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

    private void initData(int pageNum, final boolean isloadmore) {
        String openid = SharedPreferencesUtils.getString(mContext, "openid");
        String dealer_id = SharedPreferencesUtils.getString(mContext, "dealer_id");
        Log.d("openid-->", openid);
        Log.d("keyword-->", keywords);
        Log.d("attribute-->", attribute);
        Log.d("merchid-->", merchid);
        Log.d("order-->", order);
        Log.d("by-->", by);
        Log.d("pricemin-->", pricemin);
        Log.d("pricemax-->", pricemax);
        Log.d("cid-->", cid);

        RetrofitUtil.getInstance().getGoodsList(openid, dealer_id, keywords, attribute, merchid, brandsid, order, by, pricemin, pricemax, String.valueOf(pageNum), cid, new Subscriber<BaseResponse<GoodsListModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<GoodsListModel> baseResponse) {
                if (baseResponse.code == 200) {
                    goodsListModel = baseResponse.getData();
                    List<GoodsListModel.ListBean> list = goodsListModel.getList();
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
                        initData(pageNum, true);
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
                initData(pageNum, false);
                mAdapter.setEnableLoadMore(true);//启用加载
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }


    @OnClick({R.id.rl_back, R.id.tv_classify, R.id.rl_classify, R.id.rl_home, R.id.rl_top, R.id.tv_synthesis_sort, R.id.layout_synthesis_sort, R.id.tv_sales_sort, R.id.layout_sales_sort, R.id.tv_sort_price, R.id.layout_expert_service, R.id.tv_filter_sort, R.id.layout_filter_sort})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_classify:
            case R.id.rl_classify:
                //  showClassifyDialog();
                bgAlpha(0.6f);
                popupWindow1.showFilterPopup(ll_header);
                break;
            case R.id.rl_top://置頂
                mRecyclerView.smoothScrollToPosition(0);//滑到顶部
                break;
            //综合
            case R.id.tv_synthesis_sort:
            case R.id.layout_synthesis_sort:
                attribute = "";
                merchid = "";
                order = "";
                by = "";
                pricemin = "";
                pricemax = "";
                pageNum = 1;
                mTvSalesSort.setTextColor(Color.parseColor("#333333"));
                mTvSortPrice.setTextColor(Color.parseColor("#333333"));
                mTvFilterSort.setTextColor(Color.parseColor("#333333"));
                iv_price.setBackgroundResource(R.mipmap.icon_sort_up_down);
                iv_sales.setBackgroundResource(R.mipmap.icon_sort_up_down);

                salesFalg = false;
                priceFalg = false;
                filterFalg = false;
                if (!synthesisFalg) {
                    mTvSynthesisSort.setTextColor(Color.parseColor("#F93955"));
                } else {
                    mTvSynthesisSort.setTextColor(Color.parseColor("#333333"));
                }

                initAdapter();
                break;
            //销量
            case R.id.tv_sales_sort:
            case R.id.layout_sales_sort:
                attribute = "";
                merchid = "";
                order = "salesreal";
                // by = "desc";
                pricemin = "";
                pricemax = "";
                pageNum = 1;
                mTvSynthesisSort.setTextColor(Color.parseColor("#333333"));
                mTvSortPrice.setTextColor(Color.parseColor("#333333"));
                mTvFilterSort.setTextColor(Color.parseColor("#333333"));
                iv_price.setBackgroundResource(R.mipmap.icon_sort_up_down);
                synthesisFalg = false;
                priceFalg = false;
                filterFalg = false;
//                if (!salesFalg) {
//                    mTvSalesSort.setTextColor(Color.parseColor("#F93955"));
//                } else {
//                    mTvSalesSort.setTextColor(Color.parseColor("#333333"));
//                }
                if (!priceFalg) {
                    switch (priceFalg01) {
                        case 0:
                            by = "asc";
                            mTvSalesSort.setTextColor(Color.parseColor("#F93955"));
                            iv_sales.setBackgroundResource(R.mipmap.icon_sort_up);
                            priceFalg01 = 1;
                            break;
                        case 1:
                            by = "desc";
                            iv_sales.setBackgroundResource(R.mipmap.icon_sort_down);
                            priceFalg01 = 0;
                            break;
                    }
                }
                initAdapter();
                break;
            //价格
            case R.id.tv_sort_price:
            case R.id.layout_expert_service:
                attribute = "";
                merchid = "";
                order = "minprice";
                pricemin = "";
                pricemax = "";
                pageNum = 1;
                mTvSynthesisSort.setTextColor(Color.parseColor("#333333"));
                mTvSalesSort.setTextColor(Color.parseColor("#333333"));
                mTvFilterSort.setTextColor(Color.parseColor("#333333"));
                iv_sales.setBackgroundResource(R.mipmap.icon_sort_up_down);
                synthesisFalg = false;
                salesFalg = false;
                filterFalg = false;
                if (!priceFalg) {
                    switch (priceFalg01) {
                        case 0:
                            by = "asc";
                            mTvSortPrice.setTextColor(Color.parseColor("#F93955"));
                            iv_price.setBackgroundResource(R.mipmap.icon_sort_up);
                            priceFalg01 = 1;
                            break;
                        case 1:
                            by = "desc";
                            iv_price.setBackgroundResource(R.mipmap.icon_sort_down);
                            priceFalg01 = 0;
                            break;
                    }
                }
                initAdapter();
                break;
            case R.id.layout_filter_sort:
            case R.id.tv_filter_sort:
                //  showFilter();
                bgAlpha(0.6f);
                popupWindow.showFilterPopup(ll_header);
                // popupWindow.showAsDropDown(ll_header,0,0);
                //  popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                //showFilterPopup
                break;
            case R.id.rl_home:
                toActivity(MainActivity.class);
                AppManager.getAppManager().finishAllActivity();
                break;

        }
    }

    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    private void showClassifyDialog() {
        ClassifyDialogFragment classifyDialogFragment = new ClassifyDialogFragment();
        classifyDialogFragment.show(getSupportFragmentManager(), "classifyDialogFragment");
    }

    private void showFilter() {
        FilterDialog01Fragment filterDialogFragment = new FilterDialog01Fragment();
        filterDialogFragment.setOnSelectInforCompleted(this);

        filterDialogFragment.show(getSupportFragmentManager(), "filterDialogFragment");
    }

    /**
     * 筛选返回
     *
     * @param attribute01
     * @param brandsid01
     */
    @Override
    public void selectInforCompleted(String attribute01, String brandsid01) {
        Log.d("TAG", "8885-->" + attribute01);
        Log.d("TAG", "9992-->" + brandsid01);
        attribute = attribute01;
        brandsid = brandsid01;

        initAdapter();
    }


    /**
     * 分类返回
     *
     * @param cidName
     * @param id
     */
    @Override
    public void selectInforCompleted01(String cidName, String id) {
        Log.d("TAG", "8885-->" + cidName);
        Log.d("TAG", "9992-->" + id);
        cid = id;
        tv_01.setText("所以分类：" + cidName);
        initAdapter();
    }
}
