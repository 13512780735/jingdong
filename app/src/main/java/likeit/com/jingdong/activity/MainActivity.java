package likeit.com.jingdong.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.creativesource.simpledrawerlayout.SimpleDrawerLayout;

import likeit.com.jingdong.R;
import likeit.com.jingdong.web.jsinterface.FirstFragment;
import likeit.com.jingdong.fragment.FourthFragment;
import likeit.com.jingdong.fragment.SecondFragment;
import likeit.com.jingdong.fragment.ThirdFragment;

public class MainActivity extends BaseActivity {
    private View navigation;
    private TextView option_1, option_2, option_3, option_4;
    private SimpleDrawerLayout drawerLayout;
    public static Fragment restoreFragment;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.toolbar_bg));
        initUI();
    }

    private void initUI() {
        option_1 = findViewById(R.id.tv_option1);
        option_2 = findViewById(R.id.tv_option2);
        option_3 = findViewById(R.id.tv_option3);
        option_4 = findViewById(R.id.tv_option4);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        drawerLayout = findViewById(R.id.simple_drawer_layout);
        fragmentManager = getSupportFragmentManager();
//        WindowManager wm = this.getWindowManager();//获取屏幕宽高
//        int width1 = wm.getDefaultDisplay().getWidth();
//        int height1 = wm.getDefaultDisplay().getHeight();
//        ViewGroup.LayoutParams para= drawerLayout.getLayoutParams();//获取drawerlayout的布局
//        para.width=width1/3*2;//修改宽度
//        para.height=height1;//修改高度
//        drawerLayout.setLayoutParams(para); //设置修改后的布局。
        navigation = option_1;
     //   option_1.setBackgroundColor(getResources().getColor(R.color.colorChecked));
        fragment = new FirstFragment();
        fragmentManager.beginTransaction().add(R.id.fl_content_main, fragment).commit();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                drawerLayout.closeDrawers();
                switch (group.getCheckedRadioButtonId()){
                    case R.id.tv_option1:
                        animateNavigation(option_1);

                        fragment = new FirstFragment();
                        MainActivity.restoreFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.fl_content_main, fragment).commit();
                        break;
                    case R.id.tv_option2:
                      animateNavigation(option_2);
                       // drawerLayout.closeDrawers();
                        fragment = new SecondFragment();
                        MainActivity.restoreFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.fl_content_main, fragment).commit();
                        break;
                    case R.id.tv_option3:
                      animateNavigation(option_3);
                       // drawerLayout.closeDrawers();
                        fragment = new ThirdFragment();
                        MainActivity.restoreFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.fl_content_main, fragment).commit();
                        break;
                    case R.id.tv_option4:
                     animateNavigation(option_4);
                        //drawerLayout.closeDrawers();
                        fragment = new FourthFragment();
                        MainActivity.restoreFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.fl_content_main, fragment).commit();
                        break;
                }
            }
        });
    }
    private void animateNavigation(View view) {

        if(navigation == null) {
            navigation = view;
            navigation.setBackgroundColor(getResources().getColor(R.color.colorChecked));
        }
        else {
            navigation.setBackgroundColor(getResources().getColor(R.color.colorUnchecked));
            view.setBackgroundColor(getResources().getColor(R.color.colorChecked));
            navigation = view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
