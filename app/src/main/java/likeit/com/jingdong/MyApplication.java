package likeit.com.jingdong;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;

import likeit.com.jingdong.service.X5NetService;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // MultiDex.install(this);
        super.onCreate();

        preInitX5Core();
        initX5WebView();
        initBugly();
    }

    public static MyApplication mContext;

    public static MyApplication getInstance() {
        if (mContext == null) {
            return new MyApplication();
        } else {
            return mContext;
        }
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
    private void initX5WebView() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
