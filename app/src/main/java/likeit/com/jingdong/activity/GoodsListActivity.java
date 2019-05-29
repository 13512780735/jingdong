package likeit.com.jingdong.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import butterknife.BindView;
import butterknife.OnClick;
import likeit.com.jingdong.AppManager;
import likeit.com.jingdong.R;
import likeit.com.jingdong.fragment.dialogCodeFragment;
import likeit.com.jingdong.view.BorderRelativeLayout;
import likeit.com.jingdong.view.BorderTextView;

public class GoodsListActivity extends BaseActivity implements View.OnClickListener {
    BorderRelativeLayout rlBack, rlHome, rlTop;
    private ImageView tvRight;
    private dialogCodeFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_list);
        initUI();
    }

    private void initUI() {
        tvRight = findViewById(R.id.tv_right);
        rlBack = findViewById(R.id.rl_back);
        rlHome = findViewById(R.id.rl_home);
        rlTop = findViewById(R.id.rl_top);
        rlBack.setOnClickListener(this);
        rlHome.setOnClickListener(this);
        rlTop.setOnClickListener(this);
        Glide.with(this)
                .load(R.mipmap.ic_code)
                .animate(R.anim.item_alpha_in)
                .priority(Priority.HIGH)
                .into(tvRight);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new dialogCodeFragment(mContext);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_home:
                toActivity(MainActivity.class);
                AppManager.getAppManager().finishAllActivity();
                break;
            case R.id.rl_top:
                break;
        }

    }
}
