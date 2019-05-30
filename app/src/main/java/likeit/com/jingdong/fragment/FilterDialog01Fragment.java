package likeit.com.jingdong.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import likeit.com.jingdong.R;
import likeit.com.jingdong.adapter.GoodsAttrListAdapter;
import likeit.com.jingdong.adapter.GoodsAttrsAdapter;
import likeit.com.jingdong.listener.OnFilterFinishListener;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import rx.Subscriber;

public class FilterDialog01Fragment extends DialogFragment {

    private View view;
    private GoodsListModel goodsListModel;
    private ListView selectionList;
    private TextView filterReset;
    private TextView filterSure;
    private GoodsAttrsAdapter serviceAdapter;
    private GoodsAttrListAdapter adapter;
    private String attribute1 = "";
    private String brandsid = "";

    private OnFilterFinishListener mOnFilterFinishListener;
    private LinearLayout ll_bg;
    private ImageView iv_close;

    public void setOnSelectInforCompleted(OnFilterFinishListener onFilterFinishListener) {
        mOnFilterFinishListener = onFilterFinishListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.fragment_dialog_filter01, container, false);

        initData();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    List<GoodsListModel.FiltrateBean> filtrateBean;

    private void initData() {
        String openid = SharedPreferencesUtils.getString(getActivity(), "openid");
        String dealer_id = SharedPreferencesUtils.getString(getActivity(), "dealer_id");
        RetrofitUtil.getInstance().getGoodsList(openid, dealer_id, "", "", "", "", "", "", "", "", String.valueOf(1), "", new Subscriber<BaseResponse<GoodsListModel>>() {
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
                    // List<GoodsListModel.ListBean> list = goodsListModel.getList();
                    filtrateBean = goodsListModel.getFiltrate();
                    initUI(view);
                } else {
                    Toast.makeText(getActivity(), baseResponse.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private List<GoodsListModel.FiltrateBean.ListBeanX> serviceList;
    private List<GoodsListModel.FiltrateBean> itemData;

    private void initUI(View view) {
        ll_bg = view.findViewById(R.id.ll_bg);
        iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        selectionList = (ListView) view.findViewById(R.id.selection_list);
        filterReset = (TextView) view.findViewById(R.id.filter_reset);
        filterSure = (TextView) view.findViewById(R.id.filter_sure);
        serviceList = new ArrayList<GoodsListModel.FiltrateBean.ListBeanX>();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_bg.getLayoutParams();

        params.width = width1 / 4 * 3;
        params.height = height1 / 4 * 3;
        ll_bg.setLayoutParams(params);
//        serviceAdapter = new GoodsAttrsAdapter(getActivity());
//        serviceGrid.setAdapter(serviceAdapter);
//        serviceAdapter.notifyDataSetChanged(true, serviceList);
//        serviceGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                //设置当前选中的位置的状态为非。
//                serviceList.get(position).setChecked(!serviceList.get(position).isChecked());
//                XLog.e("position-->" + position);
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

        itemData = filtrateBean;
        adapter = new GoodsAttrListAdapter(getActivity(), itemData);
        selectionList.setAdapter(adapter);
        initData();
        // 重置的点击监听，将所有选项全设为false
        filterReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                ed_low_price.setText("");
//                ed_high_price.setText("");
//                attribute1="";
                for (int i = 0; i < serviceList.size(); i++) {
                    //跳过已设置的选中的位置的状态
                    serviceList.get(i).setChecked(false);
                }
                serviceAdapter.notifyDataSetChanged(true, serviceList);
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
        //  filterSure.setOnClickListener(this);
        filterSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < itemData.size(); i++) {
                    for (int j = 0; j < itemData.get(i).getList().size(); j++) {
                        if (itemData.get(i).getList().get(j).isChecked()) {
                            // str = str + itemData.get(i).getList().get(j).getName();
                            String fields = itemData.get(i).getName();

                            if ("property".equals(fields)) {
                                attribute1 = itemData.get(i).getList().get(j).getId();
                            } else if ("brands".equals(fields)) {
                                brandsid = itemData.get(i).getList().get(j).getId();
                            }
                        }
                    }
                }
                // attribute=attribute1;
                //   String str1=pricemin+"-"+pricemax+"-"+attribute1+"-"+merchid+"-"+cid;
                //  mOnFilterInforCompleted.inputFilterInforCompleted(pricemin,pricemax,attribute,merchid,cid);
                mOnFilterFinishListener.selectInforCompleted(attribute1, brandsid);
                dismiss();
                // Toast.makeText(FilterPopupWindow.this.context, pricemax, Toast.LENGTH_SHORT).show();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        mOnFilterInforCompleted.inputFilterInforCompleted(pricemin,pricemax,attribute,merchid,cid);
//                    }
//                }).start();

                //    Toast.makeText(FilterPopupWindow.this.context, str, Toast.LENGTH_SHORT).show();
                //  dismiss();
            }
        });

//        getActivity().setContentView(view);
//        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
//        this.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
//        ColorDrawable dw = new ColorDrawable(00000000);
//        this.setBackgroundDrawable(dw);
//        this.setFocusable(true);
//        this.setOutsideTouchable(false);
//        this.update();

    }
}
