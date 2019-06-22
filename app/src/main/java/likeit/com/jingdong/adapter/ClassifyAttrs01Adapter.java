package likeit.com.jingdong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import likeit.com.jingdong.R;
import likeit.com.jingdong.network.model.ClassifyList01Model;
import likeit.com.jingdong.network.model.ClassifyListModel;


/**
 * 子属性GridView的适配器
 */
public class ClassifyAttrs01Adapter extends BaseAdapter {

    private Context context;
    private List<ClassifyList01Model.FiltrateBean.TwotierBean.ThreetierBean> data = new ArrayList<ClassifyList01Model.FiltrateBean.TwotierBean.ThreetierBean>();

    public ClassifyAttrs01Adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        final MyView myView;
        if (v == null) {
            myView = new MyView();
            v = View.inflate(context, R.layout.item_goods_attrs, null);
            myView.attr = (TextView) v.findViewById(R.id.attr_name);
            v.setTag(myView);
        } else {
            myView = (MyView) v.getTag();
        }
        myView.attr.setText(data.get(position).getName());
        /**
         * 根据选中状态来设置item的背景和字体颜色
         */
        if (data.get(position).isChecked()) {
            myView.attr.setBackgroundResource(R.drawable.goods_attr_selected_shape);
            myView.attr.setTextColor(Color.parseColor("#F93955"));
        } else {
            myView.attr.setBackgroundResource(R.drawable.goods_attr_unselected_shape);
            myView.attr.setTextColor(Color.parseColor("#AAAAAA"));
        }
        return v;
    }

    static class MyView {
        public TextView attr;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(boolean isUnfold,
                                     final List<ClassifyList01Model.FiltrateBean.TwotierBean.ThreetierBean> tempData) {
        if (tempData == null || 0 == tempData.size()) {
            return;
        }
        data.clear();
        // 如果是展开的，则加入全部data，反之则只显示3条
        if (isUnfold) {
            data.addAll(tempData);
        } else {
            if (tempData.size() == 4) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
            } else if (tempData.size() == 5) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
                data.add(tempData.get(4));
            } else if (tempData.size() == 6) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
                data.add(tempData.get(4));
                data.add(tempData.get(5));
            }
            else if (tempData.size() == 7) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
                data.add(tempData.get(4));
                data.add(tempData.get(5));
                data.add(tempData.get(6));
            } else if (tempData.size() == 8) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
                data.add(tempData.get(4));
                data.add(tempData.get(5));
                data.add(tempData.get(6));
                data.add(tempData.get(7));
            } else if (tempData.size() == 9) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
                data.add(tempData.get(4));
                data.add(tempData.get(5));
                data.add(tempData.get(6));
                data.add(tempData.get(7));
                data.add(tempData.get(8));
            } else if (tempData.size() == 10) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
                data.add(tempData.get(4));
                data.add(tempData.get(5));
                data.add(tempData.get(6));
                data.add(tempData.get(7));
                data.add(tempData.get(8));
                data.add(tempData.get(9));
            } else if (tempData.size() == 11) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
                data.add(tempData.get(4));
                data.add(tempData.get(5));
                data.add(tempData.get(6));
                data.add(tempData.get(7));
                data.add(tempData.get(8));
                data.add(tempData.get(9));
                data.add(tempData.get(10));
            } else if (tempData.size() == 12) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
                data.add(tempData.get(3));
                data.add(tempData.get(4));
                data.add(tempData.get(5));
                data.add(tempData.get(6));
                data.add(tempData.get(7));
                data.add(tempData.get(8));
                data.add(tempData.get(9));
                data.add(tempData.get(10));
                data.add(tempData.get(11));
            } else if (tempData.size() == 3) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
                data.add(tempData.get(2));
            } else if (tempData.size() == 2) {
                data.add(tempData.get(0));
                data.add(tempData.get(1));
            } else {
                data.add(tempData.get(0));
            }

        }
        notifyDataSetChanged();
    }

}
