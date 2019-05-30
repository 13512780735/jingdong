package likeit.com.jingdong.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import likeit.com.jingdong.AppManager;
import likeit.com.jingdong.R;
import likeit.com.jingdong.adapter.GoodListAdapter;
import likeit.com.jingdong.fragment.FilterDialog01Fragment;
import likeit.com.jingdong.fragment.dialogCodeFragment;
import likeit.com.jingdong.listener.OnFilterFinishListener;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.view.BorderRelativeLayout;
import rx.Subscriber;

public class GoodsListActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, OnFilterFinishListener {
    BorderRelativeLayout rlBack, rlHome, rlTop;
    private ImageView tvRight;
    private dialogCodeFragment dialog;
    @BindView(R.id.search_content_et)
    EditText search_content_et;
    @BindView(R.id.tv_search)
    TextView tv_search;

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
        keywords = "";
        brandsid = "";
        cid = "";
        initUI();
    }

    private List<GoodsListModel.ListBean> data = new ArrayList<>();

    private void initUI() {

        search_content_et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    initAdapter();
                    hideinfo();
                }
                return false;
            }
        });


        tvRight = findViewById(R.id.tv_right);
        rlBack = findViewById(R.id.rl_back);
        rlHome = findViewById(R.id.rl_home);
        rlTop = findViewById(R.id.rl_top);
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


        iv_price.setBackgroundResource(R.mipmap.icon_sort_up_down);
        iv_sales.setBackgroundResource(R.mipmap.icon_sort_up_down);
        mTvSynthesisSort.setTextColor(Color.parseColor("#F93955"));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
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
        Log.e("openid-->", openid);
        Log.e("keyword-->", keywords);
        Log.e("attribute-->", attribute);
        Log.e("merchid-->", merchid);
        Log.e("order-->", order);
        Log.e("by-->", by);
        Log.e("pricemin-->", pricemin);
        Log.e("pricemax-->", pricemax);
        Log.e("cid-->", cid);
        keywords = search_content_et.getText().toString().trim();
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


    @OnClick({R.id.rl_back, R.id.rl_home, R.id.rl_top, R.id.tv_synthesis_sort, R.id.layout_synthesis_sort, R.id.tv_sales_sort, R.id.layout_sales_sort, R.id.tv_sort_price, R.id.layout_expert_service, R.id.tv_filter_sort, R.id.layout_filter_sort})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_back:
                finish();
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
                showFilter();
                // popupWindow.showFilterPopup(ll_header);
                break;
            case R.id.rl_home:
                toActivity(MainActivity.class);
                AppManager.getAppManager().finishAllActivity();
                break;

        }
    }

    private void showFilter() {
        FilterDialog01Fragment filterDialogFragment = new FilterDialog01Fragment();
        filterDialogFragment.setOnSelectInforCompleted(this);
        filterDialogFragment.show(getSupportFragmentManager(), "filterDialogFragment");
    }

    @Override
    public void selectInforCompleted(String attribute01, String brandsid01) {
        Log.d("TAG","8885-->"+attribute01);
        Log.d("TAG","9992-->"+brandsid01);
        attribute = attribute01;
        brandsid = brandsid01;
        initAdapter();
    }
}
