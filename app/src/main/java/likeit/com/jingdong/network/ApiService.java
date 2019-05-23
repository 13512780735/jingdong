package likeit.com.jingdong.network;


import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.LoginModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static likeit.com.jingdong.network.consts.Consts.APP_HOST;


/**
 * Created by admin on 2018/5/11.
 */

public interface ApiService {
    /**
     * 首页
     */
    String Home = APP_HOST + "/index.html";
    /**
     * 购物车
     */
    String Cart = APP_HOST + "/cart.html";
    /**
     * 详情
     */
    String Desc = APP_HOST + "/desc.html";
    /**
     * 商品
     */
    String Good = APP_HOST + "/goods.html";
    /**
     * 订单
     */
    String Order = APP_HOST + "/order.html";

    /**
     * 首页导航
     *
     * @return
     */
    @FormUrlEncoded
    @POST("nativeapp.shop.get_tabbar")
    Observable<BaseResponse<LoginModel>> Login(@Field("mobile") String mobile,
                                               @Field("pwd") String pwd
    );


}
