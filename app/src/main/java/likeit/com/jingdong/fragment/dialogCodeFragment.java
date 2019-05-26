package likeit.com.jingdong.fragment;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import likeit.com.jingdong.R;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.CodeModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class dialogCodeFragment extends Dialog {

    private final Context context;
    private final String openid;
    private final String dealer_id;

    @BindView(R.id.iv_code)
    ImageView iv_code;
    @BindView(R.id.tv_name)
    TextView tv_name;


    public dialogCodeFragment(@NonNull Context context) {
        super(context, R.style.dialogStyle);
        this.context = context;
        setContentView(R.layout.fragment_dialog_code);
        ButterKnife.bind(this);
        openid = SharedPreferencesUtils.getString(getContext(), "openid");
        dealer_id = SharedPreferencesUtils.getString(getContext(), "dealer_id");
        initData();
    }

    private void initData() {
        RetrofitUtil.getInstance().getCode(openid, dealer_id, new Subscriber<BaseResponse<CodeModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<CodeModel> baseResponse) {
                if (baseResponse.code == 200) {
                    Glide.with(getContext())
                            .load(baseResponse.getData().getUrl())
                            .animate(R.anim.item_alpha_in)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .skipMemoryCache(true)
                            .priority(Priority.HIGH)
                            .placeholder(R.mipmap.default_pic).error(R.mipmap.default_pic).into(iv_code);
                    tv_name.setText(baseResponse.getData().getShopname());
                }
            }
        });
    }


}
