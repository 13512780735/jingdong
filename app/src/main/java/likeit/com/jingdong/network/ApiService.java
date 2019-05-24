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
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.account.login")
    Observable<BaseResponse<LoginModel>> UserLogin(@Field("username") String mobile,
                                                   @Field("pwd") String pwd
    );


}
