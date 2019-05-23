package likeit.com.jingdong.web.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


/**
 * @author gangan
 */
public class BaseActivity extends AppCompatActivity {
    private BaseActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
//        AppManager.getAppManager().addActivity(this);
//        int color = getResources().getColor(R.color.white);
//        StatusBarUtil.setColor(this, color, 0);
//        StatusBarUtil.setLightMode(this);
        // RxBus.get().register(this);
        //  ukey = UtilPreference.getStringValue(this, "ukey");
    }

    public void addFragment(@NonNull android.app.Fragment fragment, int frameId) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameId, fragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//      //  return super.onKeyDown(keyCode, event);
//        return false;
//    }
}
