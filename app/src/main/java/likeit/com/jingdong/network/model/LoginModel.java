package likeit.com.jingdong.network.model;

public class LoginModel {

    /**
     * openid : 1cc43a4cc9b610132a492cdc3843289cb196fbcb2a953178d47c4a70c26632e5
     * expire : 0
     * token : OTVmM1RZc0JnTmluZzVid1ZRTFB0YkFlNENTL3o4NXM4TnNvSm1VRDVucStIcVByaFh6MldFdE9tVTQ=
     * dealer_id : 2174
     */

    private String openid;
    private int expire;
    private String token;
    private String dealer_id;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }
}
