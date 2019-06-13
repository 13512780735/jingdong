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
import likeit.com.jingdong.adapter.ClassifyAttrListAdapter;
import likeit.com.jingdong.adapter.ClassifyAttrsAdapter;
import likeit.com.jingdong.adapter.GoodsAttrListAdapter;
import likeit.com.jingdong.adapter.GoodsAttrsAdapter;
import likeit.com.jingdong.listener.OnClassifyFinishListener;
import likeit.com.jingdong.listener.OnFilterFinishListener;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.network.model.ClassifyListModel;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.utils.HttpUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.utils.StringUtil;


/**
 * 筛选商品属性选择的popupwindow
 */
public class FilterPopupClassifyWindow extends PopupWindow {
    private final LinearLayout ll_bg;
    private final ImageView iv_close;
    private View contentView;
    private Context context;
    private View goodsNoView;

    private GridView serviceGrid;
    private ListView selectionList;
    private TextView filterReset;
    private TextView filterSure;
    private ClassifyAttrListAdapter adapter;
    private ClassifyAttrsAdapter serviceAdapter;
    private List<ClassifyListModel.ListBean> itemData;
    private List<ClassifyListModel.ListBean.TwotierBean> serviceList;

    private String attribute1 = "";
    private String attribute2 = "";
    private String attribute3 = "";
    private String attribute4 = "";
    private String attribute5 = "";
    private String attribute6 = "";
    private String attributename1 = "";
    private String attributename2 = "";
    private String attributename3 = "";
    private String attributename4 = "";
    private String attributename5 = "";
    private String attributename6 = "";
    private String str = "";
    private String strid = "";
    private String brandsid = "";

    /**
     * 商品属性选择的popupwindow
     */
    public FilterPopupClassifyWindow(final Activity context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.fragment_dialog_classify, null);
        //goodsNoView = contentView.findViewById(R.id.popup_goods_noview);
        selectionList = (ListView) contentView.findViewById(R.id.selection_list);
        filterReset = (TextView) contentView.findViewById(R.id.filter_reset);
        filterSure = (TextView) contentView.findViewById(R.id.filter_sure);
        ll_bg = contentView.findViewById(R.id.ll_bg);
        iv_close = contentView.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new OnClickListener() {
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
        serviceList = new ArrayList<ClassifyListModel.ListBean.TwotierBean>();
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
        adapter = new ClassifyAttrListAdapter(context, itemData);
        selectionList.setAdapter(adapter);
        initData();
        // 重置的点击监听，将所有选项全设为false
        filterReset.setOnClickListener(new OnClickListener() {

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
                    for (int j = 0; j < itemData.get(i).getTwotier().size(); j++) {
                        itemData.get(i).getTwotier().get(j).setChecked(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        // 确定的点击监听，将所有已选中项列出
        filterSure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                str = "";
                strid = "";
                for (int i = 0; i < itemData.size(); i++) {
                    for (int j = 0; j < itemData.get(i).getTwotier().size(); j++) {
                        if (itemData.get(i).getTwotier().get(j).isChecked()) {
                            str = str + itemData.get(i).getTwotier().get(j).getName() + ",";
                            strid = strid + itemData.get(i).getTwotier().get(j).getId() + ",";
                            // String fields = itemData.get(i).getType();
//
//                            if ("0".equals(fields)) {
//                                attribute1 = itemData.get(i).getList().get(j).getId();
//                            } else if ("1".equals(fields)) {
//                                brandsid = itemData.get(i).getList().get(j).getId();
//                            }else if ("2".equals(fields)) {
//                                brandsid = itemData.get(i).getList().get(j).getId();
//                            }
                            String fields = itemData.get(i).getType();

//                            if ("1".equals(fields)) {
//                                attribute1 = itemData.get(i).getTwotier().get(j).getId();
//                                attributename1 = itemData.get(i).getTwotier().get(j).getName();
//                            } else if ("2".equals(fields)) {
//                                attribute2 = itemData.get(i).getTwotier().get(j).getId();
//                                attributename2 = itemData.get(i).getTwotier().get(j).getName();
//                            } else if ("3".equals(fields)) {
//                                attribute3 = itemData.get(i).getTwotier().get(j).getId();
//                                attributename3 = itemData.get(i).getTwotier().get(j).getName();
//                            } else if ("4".equals(fields)) {
//                                attribute4 = itemData.get(i).getTwotier().get(j).getId();
//                                attributename4 = itemData.get(i).getTwotier().get(j).getName();
//                            } else if ("5".equals(fields)) {
//                                attribute5 = itemData.get(i).getTwotier().get(j).getId();
//                                attributename5 = itemData.get(i).getTwotier().get(j).getName();
//                            } else if ("6".equals(fields)) {
//                                attribute6 = itemData.get(i).getTwotier().get(j).getId();
//                                attributename6 = itemData.get(i).getTwotier().get(j).getName();
//                            }
                        }
                    }
                }
                String cid = "";
                String cidName = "";
                //  Log.d("TAG","str-->"+str);
                // mOnClassifyFinishListener.selectInforCompleted(attribute1);
//                if (StringUtil.isBlank(attribute1) && !StringUtil.isBlank(attribute2) && !StringUtil.isBlank(attribute3) && !StringUtil.isBlank(attribute4) && !StringUtil.isBlank(attribute5) && !StringUtil.isBlank(attribute6)) {
//                    cid = attribute2 + "," + attribute3 + "," + attribute4 + "," + attribute5 + "," + attribute6;
//                    cidName = attributename2 + "," + attributename3 + "," + attributename4 + "," + attributename5 + "," + attributename6;
//                }else if(!StringUtil.isBlank(attribute1) && StringUtil.isBlank(attribute2) && !StringUtil.isBlank(attribute3) && !StringUtil.isBlank(attribute4) && !StringUtil.isBlank(attribute5) && !StringUtil.isBlank(attribute6)){
//
//                }
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

    private void initData() {
        String url = ApiService.ClassifyList;
        String openid = SharedPreferencesUtils.getString(context, "openid");
        RequestParams params = new RequestParams();
        params.put("openid", openid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", response);
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    if (code == 200) {
                        JSONObject object1 = object.optJSONObject("data");
                        JSONArray json = object1.optJSONArray("filtrate");
                        Log.d("TAG", json.toString());
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

    private OnClassifyFinishListener mOnClassifyFinishListener;

    public void setOnClassifyFinishListener(OnClassifyFinishListener onClassifyFinishListener) {
        mOnClassifyFinishListener = onClassifyFinishListener;
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
            ClassifyListModel.ListBean saleName = new ClassifyListModel.ListBean();
            JSONObject obj = (JSONObject) json.opt(i);
            Log.d("TAG","titel-->"+obj.optString("titel"));
            saleName.setTitel(obj.optString("titel"));
            saleName.setType(obj.optString("type"));
            List<ClassifyListModel.ListBean.TwotierBean> list = new ArrayList<ClassifyListModel.ListBean.TwotierBean>();
            JSONArray array = new JSONArray();
            array = obj.optJSONArray("twotier");
            for (int j = 0; j < array.length(); j++) {
                JSONObject object = array.getJSONObject(j);
                ClassifyListModel.ListBean.TwotierBean vo = new ClassifyListModel.ListBean.TwotierBean();
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
            saleName.setTwotier(list);
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


}
