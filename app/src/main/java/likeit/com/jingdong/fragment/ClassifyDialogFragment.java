package likeit.com.jingdong.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import likeit.com.jingdong.R;
import likeit.com.jingdong.adapter.GoodsAttrListAdapter;
import likeit.com.jingdong.adapter.GoodsAttrsAdapter;
import likeit.com.jingdong.listener.OnFilterFinishListener;
import likeit.com.jingdong.network.model.GoodsListModel;

public class ClassifyDialogFragment extends DialogFragment {

    private View view;
    private GoodsListModel goodsListModel;
    private ListView selectionList;
    private TextView filterReset;
    private TextView filterSure;
    private GoodsAttrsAdapter serviceAdapter;
    private GoodsAttrListAdapter adapter;
    private String attribute1 = "";
    private String brandsid = "";

    private LinearLayout ll_bg;
    private ImageView iv_close;


    private OnFilterFinishListener mOnFilterFinishListener;

    public void setOnSelectInforCompleted(OnFilterFinishListener onFilterFinishListener) {
        mOnFilterFinishListener = onFilterFinishListener;
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
    }
}
