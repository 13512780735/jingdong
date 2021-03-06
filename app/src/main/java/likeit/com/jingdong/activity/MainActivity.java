package likeit.com.jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.creativesource.simpledrawerlayout.SimpleDrawerLayout;

import likeit.com.jingdong.R;
import likeit.com.jingdong.fragment.FirstFragment;
import likeit.com.jingdong.fragment.FourthFragment;
import likeit.com.jingdong.listener.OnFinishListener;

public class MainActivity extends BaseActivity implements OnFinishListener {
    private View navigation;
    private RadioButton option_1, option_2, option_3, option_4;
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
//        option_2 = findViewById(R.id.tv_option2);
//        option_3 = findViewById(R.id.tv_option3);
        option_4 = findViewById(R.id.tv_option4);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        drawerLayout = findViewById(R.id.simple_drawer_layout);
        fragmentManager = getSupportFragmentManager();
        navigation = option_1;
        fragment = new FirstFragment();
        fragmentManager.beginTransaction().add(R.id.fl_content_main, fragment).commit();
//        mRadioGroup.clearCheck();
//        option_1.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                drawerLayout.closeDrawers();
                switch (group.getCheckedRadioButtonId()) {

                    case R.id.tv_option1:
                        animateNavigation(option_1);
                        fragment = new FirstFragment();
                        MainActivity.restoreFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.fl_content_main, fragment).commit();
                        break;
//                    case R.id.tv_option2:
//                        animateNavigation(option_2);
//                        // drawerLayout.closeDrawers();
//                        fragment = new SecondFragment();
//                        MainActivity.restoreFragment = fragment;
//                        fragmentManager.beginTransaction().replace(R.id.fl_content_main, fragment).commit();
//                        break;
//                    case R.id.tv_option3:
//                        animateNavigation(option_3);
//                        // drawerLayout.closeDrawers();
//                        fragment = new ThirdFragment();
//                        MainActivity.restoreFragment = fragment;
//                        fragmentManager.beginTransaction().replace(R.id.fl_content_main, fragment).commit();
//                        break;
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

        if (navigation == null) {
            navigation = view;
            navigation.setBackgroundColor(getResources().getColor(R.color.colorChecked));
        } else {
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

    //
    @Override
    public void onSuccess(int position) {
        if (position == 1) {
           // mRadioGroup.clearCheck();
            option_1.setChecked(true);
        }
    }

//    @Override
//    public void onBackPressed() {//重写的Activity返回
//
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.HOME");
//        startActivity(intent);
//
//    }
}
