package likeit.com.jingdong.network.util;


import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import likeit.com.jingdong.BuildConfig;
import likeit.com.jingdong.network.ApiService;
import likeit.com.jingdong.network.consts.Consts;
import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.ClassifyList01Model;
import likeit.com.jingdong.network.model.ClassifyListModel;
import likeit.com.jingdong.network.model.CodeModel;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.network.model.GoodsListMoreModel;
import likeit.com.jingdong.network.model.LoginModel;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo.util
 * @date 2016/12/12  10:38
 */

public class RetrofitUtil {

    public static final int DEFAULT_TIMEOUT = 5;

    private Retrofit mRetrofit;
    private ApiService mApiService;

    private static RetrofitUtil mInstance;

    /**
     * 私有构造方法
     */
    private RetrofitUtil() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG)

        {
            //打印网络请求日志
            LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .build();
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);

        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClientBuilder.build())
                .baseUrl(Consts.APP_HOST)
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    /**
     * 用于用户登录
     *
     * @param subscriber
     */
    public void getUsersLogin(String mobile, String pwd, Subscriber<BaseResponse<LoginModel>> subscriber) {
        mApiService.UserLogin(mobile, pwd)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取二维码
     *
     * @param openid
     * @param dealerid
     * @param subscriber
     */
    public void getCode(String openid, String dealerid, Subscriber<BaseResponse<CodeModel>> subscriber) {
        mApiService.getCode(openid, dealerid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取商品列表
     *
     * @param openid
     * @param dealerid
     * @param subscriber
     */

    public void getGoodsList(String openid, String dealerid, String keywords, String merchid, String attribute, String brandsid,
                             String order, String by, String pricemin, String pricemax, String pageNum, String cid,
                             Subscriber<BaseResponse<GoodsListModel>> subscriber) {
        mApiService.getGoodsList(openid, dealerid, keywords, attribute, merchid, brandsid, order, by, pricemin, pricemax, pageNum, cid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getGoodsListMore(String openid, String dealerid, String type, String id, String pageNum,
                                 Subscriber<BaseResponse<GoodsListMoreModel>> subscriber) {
        mApiService.getGoodsListMore(openid, dealerid, type, id, pageNum)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取分类列表
     *
     * @param openid
     * @param subscriber
     */

    public void getclassifyList(String openid, Subscriber<BaseResponse<ClassifyListModel>> subscriber) {
        mApiService.getclassifyList(openid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     * 获取分类列表
     *
     * @param openid
     * @param subscriber
     */

    public void getclassifyList1(String openid, Subscriber<BaseResponse<ClassifyList01Model>> subscriber) {
        mApiService.getclassifyList01(openid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
