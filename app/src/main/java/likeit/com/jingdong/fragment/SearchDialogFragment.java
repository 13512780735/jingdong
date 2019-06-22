package likeit.com.jingdong.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import likeit.com.jingdong.R;
import likeit.com.jingdong.activity.GoodsListActivity;
import likeit.com.jingdong.view.BorderRelativeLayout;

public class SearchDialogFragment extends DialogFragment implements View.OnClickListener {
    LinearLayout ll_bg;
    BorderRelativeLayout rlDel;
    private EditText edContent;
    private TextView tvSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fragment_dialog_search, container, false);

        initUI(view);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    private void initUI(View view) {
        ll_bg = view.findViewById(R.id.ll_bg);
        rlDel = view.findViewById(R.id.rl_del);
        edContent = view.findViewById(R.id.ed_content);
        tvSearch = view.findViewById(R.id.tv_search);
        rlDel.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_bg.getLayoutParams();

        params.width = width1 / 4 * 3;
        params.height = height1 / 3;
        ll_bg.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_del:
                dismiss();
                break;
            case R.id.tv_search:
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("keywords", edContent.getText().toString());
                bundle.putString("cidName", "");
                bundle.putString("cid", "");
                intent.putExtras(bundle);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
        }
    }
}
