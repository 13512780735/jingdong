package likeit.com.jingdong.adapter;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import likeit.com.jingdong.R;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.view.RatioImageView;

public class GoodListAdapter extends BaseQuickAdapter<GoodsListModel.ListBean, BaseViewHolder> {
    public GoodListAdapter(int layoutResId, List<GoodsListModel.ListBean> data) {
        super(R.layout.good_listview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsListModel.ListBean item) {
        Glide.with(mContext)
                .load(item.getThumb())
                .animate(R.anim.item_alpha_in)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .priority(Priority.HIGH)
                .placeholder(R.mipmap.default_pic).error(R.mipmap.default_pic).into((RatioImageView) helper.getView(R.id.iv_goods_img));
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_price, "¥" + item.getMinprice());
        //  helper.setText(R.id.tv_name, item.getTitle());
        helper.addOnClickListener(R.id.tv_buy);
    }
}
