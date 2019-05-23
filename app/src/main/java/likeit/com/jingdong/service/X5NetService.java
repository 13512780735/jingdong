package likeit.com.jingdong.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by tzw on 2018/3/14.
 */

public class X5NetService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public X5NetService(String name) {
        super(name);
    }

//    public static final String TAG = LogTAG.x5webview;
//    public X5NetService(){
//        super(TAG);
//    }
//    public X5NetService(String name) {
//        super(TAG);
//    }

    @Override
    public void onHandleIntent(@Nullable Intent intent) {
        initX5Web();
    }
    public void initX5Web() {
        if (!QbSdk.isTbsCoreInited()) {
            // 设置X5初始化完成的回调接口
            QbSdk.preInit(getApplicationContext(), null);
        }
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
        @Override
        public void onViewInitFinished(boolean arg0) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onCoreInitFinished() {
            // TODO Auto-generated method stub
        }
    };


}



