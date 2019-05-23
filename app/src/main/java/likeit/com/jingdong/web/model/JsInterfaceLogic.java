package likeit.com.jingdong.web.model;

import android.util.Log;
import android.webkit.JavascriptInterface;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import likeit.com.jingdong.web.base.BaseFragment;

public class JsInterfaceLogic {
    private BaseFragment mFragment;
    private String type;
    private String code;

    public JsInterfaceLogic(BaseFragment mFragment) {
        this.mFragment = mFragment;
    }

    @JavascriptInterface
    public void login(String account, String password) {
        mFragment.showNativeMessage(String.format("执行登录操作，账号为：%s，密码为：%s", account, password));
    }

    @JavascriptInterface
    public void logout() {

        mFragment.showNativeMessage("");
    }

    @JavascriptInterface
    public void weixinPay(String str) {
        mFragment.showWeChatPay(str);
    }

    @JavascriptInterface
    public void alipayPay(String str) {
        mFragment.showAlipayPay(str);
    }

    @JavascriptInterface
    public void showShare(String str) {
        mFragment.appShare(str);
    }

    @JavascriptInterface
    public void app_linkurl(String str) {
        mFragment.app_linkurl(str);
    }


    @JavascriptInterface
    public void app_back() {
        mFragment.app_back();
    }

    @JavascriptInterface
    public String is_app() {
        return "Android";
    }

    @JavascriptInterface
    public String getLoginUser() {
        return new JSONObject(new HashMap(4) {{
            put("user_id", 666);
            put("username", "你就说6不6");
            put("sex", "未知");
            put("isStudent", false);
        }}).toString();
    }
    @JavascriptInterface
    public void openCart(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            Log.d("TAG88888", jsonObject.toString());
//            String funcName = jsonObject.getString("funcName");
//            String dataStr = jsonObject.getString("data");
//            switchName(funcName, dataStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @JavascriptInterface
    public String isShowPay() {
        return new JSONObject(new HashMap(4) {{
            put("boolean", true);
        }}).toString();
    }
}
