package likeit.com.jingdong.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import likeit.com.jingdong.R;
import likeit.com.jingdong.listener.OnFinishListener;
import likeit.com.jingdong.listener.OnLoginInforCompleted;
import likeit.com.jingdong.utils.SharedPreferencesUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class dialogFragment extends DialogFragment {

    String pwd;

    TextView tvTitle;
    EditText edpwd;
    LinearLayout ll_bg;
    private OnLoginInforCompleted mOnLoginInforCompleted;
    private TextView tv_confirm, tv_cancle;

    public void setOnLoginInforCompleted(OnLoginInforCompleted onLoginInforCompleted) {
        mOnLoginInforCompleted = onLoginInforCompleted;
    }

    private DialogInterface.OnDismissListener mOnClickListener;

    //    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
//        this.mOnClickListener = listener;
//    }
//
//    //做一些弹框的初始化，以及创建一个弹框
//
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
//        if(mOnClickListener != null) {
//            mOnLoginInforCompleted.inputLoginInforCompleted("2");
//            mOnClickListener.onDismiss(dialog);
//        }
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        initUI(view);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    private OnFinishListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnFinishListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI(View view) {
        ll_bg = view.findViewById(R.id.ll_bg);
        edpwd = view.findViewById(R.id.edpwd);
        tvTitle = view.findViewById(R.id.tv_title);
        tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_cancle = view.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                listener.onSuccess(1);
                // mOnLoginInforCompleted.inputLoginInforCompleted("2");
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "点击了");
//                mOnLoginInforCompleted.inputLoginInforCompleted("1");
//                dismiss();
                String pwd1 = edpwd.getText().toString();
                pwd = SharedPreferencesUtils.getString(getActivity(), "pwd");
                Log.d("TAG", "555->" + pwd1);
                Log.d("TAG1", "888->" + pwd);
                if (!pwd1.equals(pwd)) {
                    Toast.makeText(getActivity(), "密码不正确，请重新输入！", Toast.LENGTH_LONG);
                    edpwd.setText("");
                    return;
                } else {
                    mOnLoginInforCompleted.inputLoginInforCompleted("1");
                    dismiss();
                }
//                if (pwd1.equals(pwd)) {
//                    Log.d("TAG","点击了2");
//                    mOnLoginInforCompleted.inputLoginInforCompleted("1");
//                    dismiss();
//                } else return;
            }
        });


        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_bg.getLayoutParams();

        params.width = width1 / 5*4;
        params.height = height1 / 3;
        ll_bg.setLayoutParams(params);
    }

}
