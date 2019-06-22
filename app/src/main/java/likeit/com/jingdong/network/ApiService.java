package likeit.com.jingdong.network;


import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.ClassifyList01Model;
import likeit.com.jingdong.network.model.ClassifyListModel;
import likeit.com.jingdong.network.model.CodeModel;
import likeit.com.jingdong.network.model.GoodsListModel;
import likeit.com.jingdong.network.model.GoodsListMoreModel;
import likeit.com.jingdong.network.model.LoginModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static likeit.com.jingdong.network.consts.Consts.APP_HOST;
import static likeit.com.jingdong.network.consts.Consts.HOST;


/**
 * Created by admin on 2018/5/11.
 */

public interface ApiService {
    /**
     * 首页
     */
    String Home = HOST + "/index.html";
    /**
     * 购物车
     */
    String Cart = HOST + "/cart.html";
    /**
     * 详情
     */
    String Desc = HOST + "/desc.html";
    /**
     * 商品
     */
    String Good = HOST + "/goods.html";
    /**
     * 订单
     */
    String Order = HOST + "/order.html";

    /**
     * 天气
     */
    String Weather = "http://apicloud.mob.com/v1/weather/ip";
    /**
     * 商品列表
     */
    String GoodList = APP_HOST + "/index.php?i=1&c=entry&r=dealer.api.category.categorylist";
    String ClassifyList = APP_HOST + "/index.php?i=1&c=entry&r=dealer.api.category.ad_category";


    /**
     * 商品分类
     */
    String ClassifyList01 = APP_HOST + "/index.php?i=1&c=entry&r=dealer.api.category.ad_category1";

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.account.login")
    Observable<BaseResponse<LoginModel>> UserLogin(@Field("username") String mobile,
                                                   @Field("pwd") String pwd
    );

    //获取二维码
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.account.manage")
    Observable<BaseResponse<CodeModel>> getCode(@Field("openid") String openid,
                                                @Field("dealerid") String dealerid
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.category.categorylist")
    Observable<BaseResponse<GoodsListModel>> getGoodsList(@Field("openid") String openid,
                                                          @Field("dealerid") String dealerid,
                                                          @Field("keywords") String keywords,
                                                          @Field("attribute") String attribute,
                                                          @Field("merchid") String merchid,
                                                          @Field("brandsid") String brandsid,
                                                          @Field("order") String order,
                                                          @Field("by") String by,
                                                          @Field("pricemin") String pricemin,
                                                          @Field("pricemax") String pricemax,
                                                          @Field("pageNum") String pageNum,
                                                          @Field("cid") String cid
    );

    //获取更多
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.home.goodslists")
    Observable<BaseResponse<GoodsListMoreModel>> getGoodsListMore(@Field("openid") String openid,
                                                                  @Field("dealerid") String dealerid,
                                                                  @Field("type") String type,
                                                                  @Field("id") String id,
                                                                  @Field("pageNum") String pageNum
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.category.ad_category")
    Observable<BaseResponse<ClassifyListModel>> getclassifyList(@Field("openid") String openid
    );
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.category.ad_category1")
    Observable<BaseResponse<ClassifyList01Model>> getclassifyList01(@Field("openid") String openid
    );

}
