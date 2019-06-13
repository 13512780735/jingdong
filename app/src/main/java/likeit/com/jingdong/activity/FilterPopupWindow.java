package likeit.com.jingdong.activity;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import likeit.com.jingdong.R;
import likeit.com.jingdong.adapter.GoodsAttrListAdapter;
import likeit.com.jingdong.adapter.GoodsAttrsAdapter;
import likeit.com.jingdong.listener.OnFilterFinishListener;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.network.model.ClassifyListModel;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.utils.HttpUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;


/**
 * 筛选商品属性选择的popupwindow
 */
public class FilterPopupWindow extends PopupWindow {
    private final LinearLayout ll_bg;
    private final ImageView iv_close;
    private View contentView;
    private Context context;
    private View goodsNoView;

    private GridView serviceGrid;
    private ListView selectionList;
    private TextView filterReset;
    private TextView filterSure;
    private GoodsAttrListAdapter adapter;
    private GoodsAttrsAdapter serviceAdapter;
    private List<GoodsListModel.FiltrateBean> itemData;
    private List<GoodsListModel.FiltrateBean.ListBeanX> serviceList;

    private String attribute1="";
    private String str = "";
    private String strid = "";
    private String brandsid = "";
    /**
     * 商品属性选择的popupwindow
     */
    public FilterPopupWindow(final Activity context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.fragment_dialog_filter01, null);
        //goodsNoView = contentView.findViewById(R.id.popup_goods_noview);
        selectionList = (ListView) contentView.findViewById(R.id.selection_list);
        filterReset = (TextView) contentView.findViewById(R.id.filter_reset);
        filterSure = (TextView) contentView.findViewById(R.id.filter_sure);
        ll_bg = contentView.findViewById(R.id.ll_bg);
        iv_close = contentView.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //goodsNoView.setOnClickListener(new CancelOnClickListener());
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
       serviceList = new ArrayList<GoodsListModel.FiltrateBean.ListBeanX>();
//        for (int i = 0; i < serviceStr.length; i++) {
//            ClassifyListModel.ListBean.TwotierBean vo = new ClassifyListModel.ListBean.TwotierBean();
//            vo.setValue(serviceStr[i]);
//            serviceList.add(vo);
//        }
//        serviceAdapter = new GoodsAttrsAdapter(context);
//        serviceGrid.setAdapter(serviceAdapter);
//        serviceAdapter.notifyDataSetChanged(true, serviceList);
//        serviceGrid.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                //设置当前选中的位置的状态为非。
//                serviceList.get(position).setChecked(!serviceList.get(position).isChecked());
//                attribute1 = String.valueOf(position);
//                for (int i = 0; i < serviceList.size(); i++) {
//                    //跳过已设置的选中的位置的状态
//
//                    if (i == position) {
//                        continue;
//                    }
//                    serviceList.get(i).setChecked(false);
//                }
//                serviceAdapter.notifyDataSetChanged(true, serviceList);
//            }
//        });

        itemData = new ArrayList<>();
        adapter = new GoodsAttrListAdapter(context, itemData);
        selectionList.setAdapter(adapter);
        initData();
        // 重置的点击监听，将所有选项全设为false
        filterReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < serviceList.size(); i++) {
                    //跳过已设置的选中的位置的状态
                    serviceList.get(i).setChecked(false);
                }
                // serviceAdapter.notifyDataSetChanged(true, serviceList);
                //   initData();
//
                for (int i = 0; i < itemData.size(); i++) {
                    for (int j = 0; j < itemData.get(i).getList().size(); j++) {
                        itemData.get(i).getList().get(j).setChecked(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        // 确定的点击监听，将所有已选中项列出
        filterSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < itemData.size(); i++) {
                    for (int j = 0; j < itemData.get(i).getList().size(); j++) {
                        if (itemData.get(i).getList().get(j).isChecked()) {
//                            str = str + itemData.get(i).getList().get(j).getName();
//                            strid = strid + itemData.get(i).getList().get(j).getId();
                            // String fields = itemData.get(i).getType();
//
//                            if ("0".equals(fields)) {
//                                attribute1 = itemData.get(i).getList().get(j).getId();
//                            } else if ("1".equals(fields)) {
//                                brandsid = itemData.get(i).getList().get(j).getId();
//                            }else if ("2".equals(fields)) {
//                                brandsid = itemData.get(i).getList().get(j).getId();
//                            }
                            String fields = itemData.get(i).getName();

                            if ("property".equals(fields)) {
                                attribute1 = itemData.get(i).getList().get(j).getId();
                            } else if ("brands".equals(fields)) {
                                brandsid = itemData.get(i).getList().get(j).getId();
                            }
                        }
                    }
                }
                // String cid = ;
                //  Log.d("TAG","str-->"+str);
                // mOnClassifyFinishListener.selectInforCompleted(attribute1);
                mOnFilterFinishListener.selectInforCompleted(attribute1, brandsid);
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

    private void initData() {
        String url = ApiService.GoodList;
        String openid = SharedPreferencesUtils.getString(context, "openid");
        String dealer_id = SharedPreferencesUtils.getString(context, "dealer_id");
        RequestParams params = new RequestParams();
        params.put("openid", openid);
        params.put("dealer_id", dealer_id);
        params.put("keywords", "");
        params.put("attribute", "");
        params.put("merchid", "");
        params.put("brandsid", "");
        params.put("order", "");
        params.put("by", "");
        params.put("pricemin", "");
        params.put("pricemax", "");
        params.put("pageNum", "");
        params.put("cid", "");
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    if (code == 200) {
                        JSONObject object1 = object.optJSONObject("data");
                        JSONArray json = object1.optJSONArray("filtrate");
                        Log.d("TAG",json.toString());
                        refreshAttrs(json);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private OnFilterFinishListener mOnFilterFinishListener;

    public void setOnSelectInforCompleted(OnFilterFinishListener onFilterFinishListener) {
        mOnFilterFinishListener = onFilterFinishListener;
    }


    /**
     * 刷新商品属性
     *
     * @param json
     * @throws JSONException
     */
    public void refreshAttrs(JSONArray json) throws JSONException {
        itemData.clear();
        for (int i = 0; i < json.length(); i++) {
            GoodsListModel.FiltrateBean saleName = new GoodsListModel.FiltrateBean();
            JSONObject obj = (JSONObject) json.opt(i);
            saleName.setTitle(obj.optString("title"));
            saleName.setFields(obj.optString("fields"));
            List<GoodsListModel.FiltrateBean.ListBeanX> list = new ArrayList<GoodsListModel.FiltrateBean.ListBeanX>();
            JSONArray array = new JSONArray();
            array = obj.optJSONArray("list");
            for (int j = 0; j < array.length(); j++) {
                JSONObject object = array.getJSONObject(j);
                GoodsListModel.FiltrateBean.ListBeanX vo = new GoodsListModel.FiltrateBean.ListBeanX();
                vo.setName(object.optString("name"));
                vo.setId(object.optString("id"));
                vo.setChecked(false);
//                if ("1".equals(object.getString("checkStatus"))) {
//                    vo.setChecked(true);
//                } else {
//                    vo.setChecked(false);
//                }
                list.add(vo);
            }
            saleName.setList(list);
            // 是否展开
            saleName.setNameIsChecked(false);
            itemData.add(saleName);
        }
        adapter.notifyDataSetChanged();
    }

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
            this.showAsDropDown(parent,0,0);
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


}
