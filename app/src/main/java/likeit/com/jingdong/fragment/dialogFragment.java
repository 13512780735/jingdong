package likeit.com.jingdong.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import likeit.com.jingdong.R;
import likeit.com.jingdong.utils.SharedPreferencesUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class dialogFragment extends Dialog {

    private final Context context;
    private final String pwd;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edpwd)
    EditText edpwd;


    public dialogFragment(@NonNull Context context) {
        super(context, R.style.dialogStyle);
        this.context = context;
        setContentView(R.layout.fragment_dialog);
        ButterKnife.bind(this);
        pwd = SharedPreferencesUtils.getString(getContext(), "pwd");
    }

    public interface OnDeptCallBack {
        void onCallBack(String st);
    }

    private OnDeptCallBack onBack;


    @OnClick({R.id.tv_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                String pwd1 = edpwd.getText().toString().trim();
                if (pwd1.equals(pwd)) {
                    onBack.onCallBack("关闭了");
                    dismiss();
                } else return;
        }
    }
}
