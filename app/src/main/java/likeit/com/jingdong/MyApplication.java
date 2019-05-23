package likeit.com.jingdong;

import android.app.Application;
import android.content.Intent;

import likeit.com.jingdong.service.X5NetService;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // MultiDex.install(this);
        super.onCreate();

        preInitX5Core();
        // ZXingLibrary.initDisplayOpinion(this);
        // initX5WebView();
        // DemoHelper.getInstance().init(mContext);
        //
        //                                             initX5WebView();
    }
    private void preInitX5Core() {
        //预加载x5内核
        Intent intent = new Intent(this, X5NetService.class);
        startService(intent);
    }
}
