package likeit.com.jingdong.web.jsinterface;

import android.support.annotation.NonNull;


import likeit.com.jingdong.web.base.BasePresenterInterface;
import likeit.com.jingdong.web.base.BaseView;

interface JsInterfaceContract {
    interface View extends BaseView<Presenter> {
        /**
         * 渲染页面
         *
         * @param url HTML链接
         */
        void renderUrl(@NonNull final String url);

        /**
         * 执行JS
         *
         * @param js js代码
         */
        void execJavaScript(@NonNull final String js);

    }

    interface Presenter extends BasePresenterInterface {
        /**
         * 点击按钮1
         */
        void clickBtn1();

        /**
         * 点击按钮2
         */
        void clickBtn2();
    }
}
