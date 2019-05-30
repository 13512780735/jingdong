package likeit.com.jingdong.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import likeit.com.jingdong.R;
import likeit.com.jingdong.listener.OnFilterFinishListener;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import rx.Subscriber;

public class FilterDialogFragment extends DialogFragment implements View.OnClickListener {


    private LinearLayout ll_bg;
    private ImageView iv_close;
    private RecyclerView mRecyclerView;
    private TextView tvCancle, tvConfirm;
    private View view;
    private GoodsListModel goodsListModel;
    private TagFlowLayout mFlowLayout;
    private TextView tv_title, tv_title01;
    private TagFlowLayout mFlowLayout01;
    private String attribute = "";
    private String brandsid = "";
    private OnFilterFinishListener mOnFilterFinishListener;

    public void setOnSelectInforCompleted(OnFilterFinishListener onFilterFinishListener) {
        mOnFilterFinishListener = onFilterFinishListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.fragment_dialog_filter, container, false);

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

    private void initUI(View view) {
        ll_bg = view.findViewById(R.id.ll_bg);
        iv_close = view.findViewById(R.id.iv_close);
        //  mRecyclerView = view.findViewById(R.id.mRecyclerView);
        tvCancle = view.findViewById(R.id.tv_cancle);
        tvConfirm = view.findViewById(R.id.tv_confirm);
        tvCancle.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        iv_close.setOnClickListener(this);
//        mAdapter = new FilterItemAdapter(R.layout.filter_item, filtrateBean);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_bg.getLayoutParams();

        params.width = width1 / 3 * 2;
        params.height = height1 / 3 * 2;
        ll_bg.setLayoutParams(params);
        tv_title = view.findViewById(R.id.tv_title);
        tv_title01 = view.findViewById(R.id.tv_title01);
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        mFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_flowlayout);
        mFlowLayout01 = (TagFlowLayout) view.findViewById(R.id.id_flowlayout01);
        final List<GoodsListModel.FiltrateBean.ListBeanX> data = filtrateBean.get(0).getList();
        final List<GoodsListModel.FiltrateBean.ListBeanX> data01 = filtrateBean.get(1).getList();
        tv_title.setText(filtrateBean.get(0).getTitle());
        tv_title01.setText(filtrateBean.get(1).getTitle());
        mFlowLayout.setAdapter(new TagAdapter<GoodsListModel.FiltrateBean.ListBeanX>(data) {

            @Override
            public View getView(FlowLayout parent, int position, GoodsListModel.FiltrateBean.ListBeanX s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.filter_items_view,
                        mFlowLayout, false);
                tv.setText(s.getName());
                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                // Toast.makeText(getActivity(), data.get(position).getId(), Toast.LENGTH_SHORT).show();

                //view.setVisibility(View.GONE);
                Log.d("TAG", "545-->" + data.get(position).getId());
                attribute = data.get(position).getId();
                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                // getActivity().setTitle("choose:" + selectPosSet.toString());
                // if(data.size())

            }
        });
        mFlowLayout01.setAdapter(new TagAdapter<GoodsListModel.FiltrateBean.ListBeanX>(data01) {

            @Override
            public View getView(FlowLayout parent, int position, GoodsListModel.FiltrateBean.ListBeanX s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.filter_items_view,
                        mFlowLayout, false);
                tv.setText(s.getName());
                return tv;
            }
        });

        mFlowLayout01.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                // Toast.makeText(getActivity(), data.get(position).getId(), Toast.LENGTH_SHORT).show();

                //view.setVisibility(View.GONE);
                Log.d("TAG", "545-->" + data01.get(position).getId());
                brandsid = data01.get(position).getId();
                return true;
            }
        });


        mFlowLayout01.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                // getActivity().setTitle("choose:" + selectPosSet.toString());
                // if(data.size())
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_cancle:
                break;
            case R.id.tv_confirm:
                Log.d("TAG", "888-->" + attribute);
                Log.d("TAG", "999-->" + brandsid);
                mOnFilterFinishListener.selectInforCompleted(attribute, brandsid);
                dismiss();
                break;
        }
    }


//    public class FilterItemAdapter extends BaseQuickAdapter<GoodsListModel.FiltrateBean, BaseViewHolder> {
//
//        public FilterItemAdapter(int layoutResId, List<GoodsListModel.FiltrateBean> data) {
//            super(R.layout.filter_item, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, GoodsListModel.FiltrateBean item) {
//            helper.setText(R.id.tv_title, item.getTitle());
//            final LayoutInflater mInflater = LayoutInflater.from(getActivity());
//            mFlowLayout = (TagFlowLayout) helper.getView(R.id.id_flowlayout);
//            data = item.getList();
//            //mFlowLayout.setMaxSelectCount(3);
//            mFlowLayout.setAdapter(new TagAdapter<GoodsListModel.FiltrateBean.ListBeanX>(data) {
//
//                @Override
//                public View getView(FlowLayout parent, int position, GoodsListModel.FiltrateBean.ListBeanX s) {
//                    TextView tv = (TextView) mInflater.inflate(R.layout.filter_items_view,
//                            mFlowLayout, false);
//                    tv.setText(s.getName());
//                    return tv;
//                }
//            });
//
//            mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//                @Override
//                public boolean onTagClick(View view, int position, FlowLayout parent) {
//                    // Toast.makeText(getActivity(), data.get(position).getId(), Toast.LENGTH_SHORT).show();
//
//                    //view.setVisibility(View.GONE);
//                    Log.d("TAG", "545-->" + data.get(position).getName());
//                    return true;
//                }
//            });
//
//
//            mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
//                @Override
//                public void onSelected(Set<Integer> selectPosSet) {
//                    // getActivity().setTitle("choose:" + selectPosSet.toString());
//                    // if(data.size())
//                }
//            });
//        }
//    }
}
