package likeit.com.jingdong.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import likeit.com.jingdong.R;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.LoginModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.utils.StringUtil;
import rx.Subscriber;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    private Animation animation;
    private View view;
    private String phone, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(this, R.layout.activity_welcome, null);
        phone = SharedPreferencesUtils.getString(mContext, "phone");
        pwd = SharedPreferencesUtils.getString(mContext, "pwd");
        setContentView(view);
        animation = AnimationUtils.loadAnimation(this, R.anim.splash_alpha);
        Glide.with(mContext)
                .load(R.mipmap.start).asBitmap().into(ivSplash);
        initUI();
    }

    private void initUI() {
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }   //在动画开始时使用

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }  //在动画重复时使用

            @Override
            public void onAnimationEnd(Animation arg0) {
                Log.d("TAG","phone->"+phone);
                if (StringUtil.isBlank(phone)) {
                    toActivity(LoginActivity.class);
                    finish();
                } else {
                    toLogin();
                }
            }
        });
    }

    private void toLogin() {
//        final String mobile = phone;
//        final String pwd =pwd;

        RetrofitUtil.getInstance().getUsersLogin(phone, pwd, new Subscriber<BaseResponse<LoginModel>>() {
            // RetrofitUtil.getInstance().getUsersLogin("test01", "123456", new Subscriber<BaseResponse<LoginModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<LoginModel> baseResponse) {
                if (baseResponse.code == 200) {

                    SharedPreferencesUtils.put(mContext, "pwd", pwd);
                    SharedPreferencesUtils.put(mContext, "phone", phone);
                    SharedPreferencesUtils.put(mContext, "openid", baseResponse.getData().getOpenid());
                    SharedPreferencesUtils.put(mContext, "expire", baseResponse.getData().getExpire());
                    SharedPreferencesUtils.put(mContext, "token", baseResponse.getData().getToken());
                    SharedPreferencesUtils.put(mContext, "dealer_id", baseResponse.getData().getDealer_id());
                    toActivity(MainActivity.class);
                    finish();
                }
            }
        });
    }
}