package likeit.com.jingdong.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import likeit.com.jingdong.R;
import likeit.com.jingdong.adapter.ClassifyAttrListAdapter;
import likeit.com.jingdong.adapter.ClassifyAttrsAdapter;
import likeit.com.jingdong.adapter.GoodsAttrListAdapter;
import likeit.com.jingdong.adapter.GoodsAttrsAdapter;
import likeit.com.jingdong.listener.OnClassifyFinishListener;
import likeit.com.jingdong.listener.OnFilterFinishListener;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.ClassifyListModel;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import rx.Subscriber;

public class ClassifyDialogFragment extends DialogFragment {

    private View view;
    private ListView selectionList;
    private TextView filterReset;
    private TextView filterSure;
    private ClassifyAttrsAdapter serviceAdapter;
    private ClassifyAttrListAdapter adapter;
    private String attribute1 = "";
    private String brandsid = "";

    private LinearLayout ll_bg;
    private ImageView iv_close;


    private ClassifyListModel classifyListModel;
    private List<ClassifyListModel.ListBean> listBean;
    private List<ClassifyListModel.ListBean.TwotierBean> serviceList;
    private List<ClassifyListModel.ListBean> itemData;
    private String str = "";
    private String strid = "";
    private OnClassifyFinishListener mOnClassifyFinishListener;

    public void OnClassifyFinishListener(OnClassifyFinishListener onClassifyFinishListener) {
        mOnClassifyFinishListener = onClassifyFinishListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.fragment_dialog_classify, container, false);

        initData();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    private void initData() {
        String openid = SharedPreferencesUtils.getString(getActivity(), "openid");
        RetrofitUtil.getInstance().getclassifyList(openid, new Subscriber<BaseResponse<ClassifyListModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<ClassifyListModel> baseResponse) {
                if (baseResponse.code == 200) {
                    classifyListModel = baseResponse.getData();
                    // List<GoodsListModel.ListBean> list = goodsListModel.getList();
                    listBean = classifyListModel.getList();
                    initUI(view);
                } else {
                    Toast.makeText(getActivity(), baseResponse.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        });
    }

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
        serviceList = new ArrayList<ClassifyListModel.ListBean.TwotierBean>();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_bg.getLayoutParams();

        params.width = width1 / 4 * 3;
        params.height = height1 / 4 * 3;
        ll_bg.setLayoutParams(params);

        itemData = listBean;
        adapter = new ClassifyAttrListAdapter(getActivity(), itemData);
        selectionList.setAdapter(adapter);
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
                    for (int j = 0; j < itemData.get(i).getTwotier().size(); j++) {
                        itemData.get(i).getTwotier().get(j).setChecked(false);
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
                    for (int j = 0; j < itemData.get(i).getTwotier().size(); j++) {
                        if (itemData.get(i).getTwotier().get(j).isChecked()) {
                            str = str + itemData.get(i).getTwotier().get(j).getName();
                            strid=strid+itemData.get(i).getTwotier().get(j).getId();
                            // String fields = itemData.get(i).getType();
//
//                            if ("0".equals(fields)) {
//                                attribute1 = itemData.get(i).getList().get(j).getId();
//                            } else if ("1".equals(fields)) {
//                                brandsid = itemData.get(i).getList().get(j).getId();
//                            }else if ("2".equals(fields)) {
//                                brandsid = itemData.get(i).getList().get(j).getId();
//                            }
                        }
                    }
                }
               // String cid = ;
                Log.d("TAG","str-->"+str);
               // mOnClassifyFinishListener.selectInforCompleted(attribute1);
                dismiss();
            }
        });
    }
}
