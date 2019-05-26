package likeit.com.jingdong;

import android.app.Application;
import android.content.Intent;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import likeit.com.jingdong.service.X5NetService;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // MultiDex.install(this);
        super.onCreate();

        preInitX5Core();
        initBugly();
    }

    private void initBugly() {
        /**
         * Bugly更新
         */
        Bugly.init(getApplicationContext(), "2b493e1c7f", false);
        Beta.autoCheckUpgrade = true;//设置自动检查
        Beta.upgradeCheckPeriod = 60 * 60 * 1000;
        Beta.largeIconId = R.mipmap.ic_launcher;
    }

    private void preInitX5Core() {
        //预加载x5内核
        Intent intent = new Intent(this, X5NetService.class);
        startService(intent);
    }
}
