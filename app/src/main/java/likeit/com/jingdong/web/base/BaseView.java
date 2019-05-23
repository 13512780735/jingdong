package likeit.com.jingdong.web.base;

import android.app.Activity;
import android.support.annotation.NonNull;

public interface BaseView<T> {

    void setPresenter(@NonNull T presenter);

    void startActivity(Class<? extends Activity> activity);
}
