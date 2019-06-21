package com.kaqi.reader.ui.adapter;

import android.content.Context;

import com.kaqi.reader.R;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author Nancy.
 * @date 2019年05月04日21:54:52.
 */
public class SignDaysAdapter extends EasyLVAdapter<String> {

    private int selected = 0;

    public SignDaysAdapter(Context context, List<String> list, int selected) {
        super(context, list, R.layout.item_sign_days_money);
        this.selected = selected;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String readTheme) {
        if (readTheme != null) {
            holder.setText(R.id.item_days_money, readTheme);
            if (selected == position) {
                holder.setVisible(R.id.ivSelected, true);
//                holder.setTextColor(R.id.sign_item_days, mContext.getResources().getColor(R.color.alpha_55_white));
            } else {
//                holder.setTextColor(R.id.sign_item_days, mContext.getResources().getColor(R.color.black));
                holder.setVisible(R.id.ivSelected, false);
            }

        }
    }

    public void select(int position) {
        selected = position;
        notifyDataSetChanged();
    }
}
