package likeit.com.jingdong.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import likeit.com.jingdong.R;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    private Animation animation;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        view = View.inflate(this, R.layout.activity_welcome, null);
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
                toActivity(LoginActivity.class);
                finish();
            }
        });
    }
}