package likeit.com.jingdong.network;


import likeit.com.jingdong.network.model.BaseResponse;
import likeit.com.jingdong.network.model.CodeModel;
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
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.account.login")
    Observable<BaseResponse<LoginModel>> UserLogin(@Field("username") String mobile,
                                                   @Field("pwd") String pwd
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&r=dealer.api.account.manage")
    Observable<BaseResponse<CodeModel>> getCode(@Field("openid") String openid,
                                                @Field("dealerid") String dealerid
    );


}
