package likeit.com.jingdong.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import likeit.com.jingdong.R;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.LoginModel;
import likeit.com.jingdong.network.util.RetrofitUtil;
import likeit.com.jingdong.utils.SharedPreferencesUtils;
import likeit.com.jingdong.view.BorderTextView;
import rx.Subscriber;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.edphone)
    EditText edphone;
    @BindView(R.id.edpwd)
    EditText edpwd;
    @BindView(R.id.ll_login)
    LinearLayout ll_login;
    private BorderTextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        Log.d("TAG", "width12 " + width1); //200px
        Log.d("TAG", "height12 " + height1); //200px
        tv_login = findViewById(R.id.tv_login);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_login.getLayoutParams();

        params.width = height1 / 3;
        params.height = width1 / 6;
        ll_login.setLayoutParams(params);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) iv_logo.getLayoutParams();
        params1.width = height1 / 4;
        params1.height = width1 / 6;
        iv_logo.setLayoutParams(params1);
       // String imgURL = "https://wx.jddengju.com/attachment/images/global/f6WB6e7EFlCLee6elF6HewHXyEuBvl.jpg";
        Glide.with(mContext)
                .load(R.mipmap.login_bg)
                .animate(R.anim.item_alpha_in)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .priority(Priority.HIGH)
                .placeholder(R.mipmap.default_pic).error(R.mipmap.default_pic).into(iv_bg);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });
    }

    private void toLogin() {
        final String mobile = edphone.getText().toString().trim();
        final String pwd = edpwd.getText().toString().trim();

         RetrofitUtil.getInstance().getUsersLogin(mobile, pwd, new Subscriber<BaseResponse<LoginModel>>() {
          //   RetrofitUtil.getInstance().getUsersLogin("test01", "123456", new Subscriber<BaseResponse<LoginModel>>() {
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
                    SharedPreferencesUtils.put(mContext, "phone", mobile);
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
