package likeit.com.jingdong.activity;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.SimpleClickListener;


import java.util.ArrayList;
import java.util.List;

import likeit.com.jingdong.R;
import likeit.com.jingdong.adapter.ClassifyAttrList01Adapter;
import likeit.com.jingdong.adapter.ClassifyAttrsAdapter;
import likeit.com.jingdong.listener.OnClassifyFinishListener;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.ClassifyList01Model;
import likeit.com.jingdong.network.model.ClassifyListModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.utils.StringUtil;
import rx.Subscriber;


/**
 * 筛选商品属性选择的popupwindow
 */
public class FilterPopupClassify01Window extends PopupWindow {
    private final LinearLayout ll_bg;
    private final ImageView iv_close;
    private final RecyclerView top_recyclerview;
    private List<ClassifyList01Model.FiltrateBean> goodsCategoryBeanList;
    private final List<ClassifyList01Model.FiltrateBean.TwotierBean> listBeanList;
    private View contentView;
    private Context context;

    private GridView serviceGrid;
    private ListView selectionList;
    private TextView filterReset;
    private TextView filterSure;
    private ClassifyAttrList01Adapter adapter;
    private ClassifyAttrsAdapter serviceAdapter;
    private List<ClassifyListModel.ListBean.TwotierBean> serviceList;

    TopClassifyAdapter mTopClassifyAdapter;


    private String str = "";
    private String strid = "";
    private String brandsid = "";
    private ClassifyList01Model classifyList01Model;

    /**
     * 商品属性选择的popupwindow
     */
    public FilterPopupClassify01Window(final Activity context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.fragment_dialog_classify, null);
        selectionList = (ListView) contentView.findViewById(R.id.selection_list);
        filterReset = (TextView) contentView.findViewById(R.id.filter_reset);
        filterSure = (TextView) contentView.findViewById(R.id.filter_sure);
        top_recyclerview = contentView.findViewById(R.id.top_recyclerview);

        goodsCategoryBeanList = new ArrayList<>();
        listBeanList = new ArrayList<>();
        ll_bg = contentView.findViewById(R.id.ll_bg);
        iv_close = contentView.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initData();

        // 重置的点击监听，将所有选项全设为false
        filterReset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                filterReset1();

            }
        });
        // 确定的点击监听，将所有已选中项列出
        filterSure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                str = "";
                strid = "";
                for (int i = 0; i < listBeanList.size(); i++) {
                    for (int j = 0; j < listBeanList.get(i).getThreetier().size(); j++) {
                        if (listBeanList.get(i).getThreetier().get(j).isChecked()) {
                            str = str + listBeanList.get(i).getThreetier().get(j).getName() + ",";
                            strid = strid + listBeanList.get(i).getThreetier().get(j).getId() + ",";
                            //  String fields = listBeanList.get(i).getRank();

                        }
                    }
                }
                String cid = "";
                String cidName = "";
                if (!StringUtil.isBlank(str)) {
                    cidName = str.substring(0, str.length() - 1);
                    cid = strid.substring(0, strid.length() - 1);
                }
                mOnClassifyFinishListener.selectInforCompleted01(cidName, cid);
                dismiss();
            }
        });

        this.setContentView(contentView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(00000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.update();

    }

    private void filterReset1() {
        for (int i = 0; i < serviceList.size(); i++) {
            //跳过已设置的选中的位置的状态
            serviceList.get(i).setChecked(false);
        }
//
        for (int i = 0; i < listBeanList.size(); i++) {
            for (int j = 0; j < listBeanList.get(i).getThreetier().size(); j++) {
                listBeanList.get(i).getThreetier().get(j).setChecked(false);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        String openid = SharedPreferencesUtils.getString(context, "openid");
        RetrofitUtil.getInstance().getclassifyList1(openid, new Subscriber<BaseResponse<ClassifyList01Model>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<ClassifyList01Model> baseResponse) {
                if (baseResponse.code == 200) {
                    classifyList01Model = baseResponse.getData();
                    goodsCategoryBeanList = classifyList01Model.getFiltrate();
                    for (int i = 0; i < goodsCategoryBeanList.size(); i++) {
                        listBeanList.addAll(goodsCategoryBeanList.get(i).getTwotier());
                    }
                    initUI();
                    mTopClassifyAdapter.setNewData(goodsCategoryBeanList);
                    // adapter.setNewData(listBeanList);
                    mTopClassifyAdapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void initUI() {


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_bg.getLayoutParams();

        params.width = width1 / 4 * 3;
        params.height = height1 / 4 * 3;
        ll_bg.setLayoutParams(params);
        contentView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return true;
            }
        });
        serviceList = new ArrayList<>();
        mTopClassifyAdapter = new TopClassifyAdapter(R.layout.item_main_top, goodsCategoryBeanList);
        adapter = new ClassifyAttrList01Adapter(context, listBeanList);
        top_recyclerview.setLayoutManager(new GridLayoutManager(context, goodsCategoryBeanList.size()));
        top_recyclerview.setAdapter(mTopClassifyAdapter);
        selectionList.setAdapter(adapter);
        top_recyclerview.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                filterReset1();
                ClassifyList01Model.FiltrateBean shopSortBean = classifyList01Model.getFiltrate().get(position);
                listBeanList.clear();
                listBeanList.addAll(shopSortBean.getTwotier());
                mTopClassifyAdapter.setSelectPos(position);
                mTopClassifyAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }
//    private void initData() {
//        String url = ApiService.ClassifyList01;
//        String openid = SharedPreferencesUtils.getString(context, "openid");
//        RequestParams params = new RequestParams();
//        params.put("openid", openid);
//        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
//            @Override
//            public void success(String response) {
//                Log.d("TAG", response);
//                try {
//                    JSONObject object = new JSONObject(response);
//                    int code = object.optInt("code");
//                    String msg = object.optString("msg");
//                    if (code == 200) {
//                        JSONObject object1 = object.optJSONObject("data");
//                        JSONArray json = object1.optJSONArray("filtrate");
//
//                        for (int i = 0; i < json.length(); i++) {
//                            ClassifyListModel.ListBean saleName = new ClassifyListModel.ListBean();
//                            JSONObject obj = (JSONObject) json.opt(i);
//                            Log.d("TAG", "titel-->" + obj.optString("titel"));
//                            saleName.setTitel(obj.optString("titel"));
//                            saleName.setType(obj.optString("type"));
//                            List<ClassifyList01Model.FiltrateBean> list = new ArrayList<ClassifyList01Model.FiltrateBean>();
//                            JSONArray array = new JSONArray();
//                            array = obj.optJSONArray("twotier");
//                            for (int j = 0; j < array.length(); j++) {
//                                JSONObject object = array.getJSONObject(j);
//                                ClassifyListModel.ListBean.TwotierBean vo = new ClassifyListModel.ListBean.TwotierBean();
//                                vo.setName(object.optString("name"));
//                                vo.setId(object.optString("id"));
//                                vo.setChecked(false);
//                                list.add(vo);
//                            }
//                            saleName.setTwotier(list);
//                            // 是否展开
//                            saleName.setNameIsChecked(false);
//                            itemData.add(saleName);
//                        }
//                        Log.d("TAG", json.toString());
//                        refreshAttrs(json);
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void failed(Throwable e) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//            }
//        });
//    }

    private OnClassifyFinishListener mOnClassifyFinishListener;

    public void setOnClassifyFinishListener(OnClassifyFinishListener onClassifyFinishListener) {
        mOnClassifyFinishListener = onClassifyFinishListener;
    }

//    /**
//     * 刷新商品属性
//     *
//     * @param json
//     * @throws JSONException
//     */
//    public void refreshAttrs(JSONArray json) throws JSONException {
//        itemData.clear();
//        for (int i = 0; i < json.length(); i++) {
//            ClassifyListModel.ListBean saleName = new ClassifyListModel.ListBean();
//            JSONObject obj = (JSONObject) json.opt(i);
//            Log.d("TAG", "titel-->" + obj.optString("titel"));
//            saleName.setTitel(obj.optString("titel"));
//            saleName.setType(obj.optString("type"));
//            List<ClassifyListModel.ListBean.TwotierBean> list = new ArrayList<ClassifyListModel.ListBean.TwotierBean>();
//            JSONArray array = new JSONArray();
//            array = obj.optJSONArray("twotier");
//            for (int j = 0; j < array.length(); j++) {
//                JSONObject object = array.getJSONObject(j);
//                ClassifyListModel.ListBean.TwotierBean vo = new ClassifyListModel.ListBean.TwotierBean();
//                vo.setName(object.optString("name"));
//                vo.setId(object.optString("id"));
//                vo.setChecked(false);
//                list.add(vo);
//            }
//            saleName.setTwotier(list);
//            // 是否展开
//            saleName.setNameIsChecked(false);
//            itemData.add(saleName);
//        }
//        adapter.notifyDataSetChanged();
//    }

    public class CancelOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

    public boolean onKeyDown(Context context, int keyCode, KeyEvent event) {
        this.context = context;
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }
        return true;
    }

    public void showFilterPopup(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }


    public class TopClassifyAdapter extends BaseQuickAdapter<ClassifyList01Model.FiltrateBean, BaseViewHolder> {
        private int selectPos = 0;

        public TopClassifyAdapter(int layoutResId, List<ClassifyList01Model.FiltrateBean> data) {
            super(R.layout.item_main_top, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ClassifyList01Model.FiltrateBean item) {
            if (selectPos == helper.getAdapterPosition()) {
                helper.setVisible(R.id.item_main_left_bg, true);
                helper.convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                helper.setTextColor(R.id.item_main_left_type, Color.parseColor("#F93955"));
            } else {
                helper.convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                helper.setTextColor(R.id.item_main_left_type, Color.parseColor("#333333"));
                helper.setVisible(R.id.item_main_left_bg, false);
            }

            helper.setText(R.id.item_main_left_type, item.getTitel());
        }

        public int getSelectPos() {
            return selectPos;
        }

        public void setSelectPos(int selectPos) {
            this.selectPos = selectPos;
        }
    }
}
