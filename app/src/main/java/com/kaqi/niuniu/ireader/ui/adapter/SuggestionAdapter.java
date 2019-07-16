package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;

import com.kaqi.niuniu.ireader.R;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author Nancy.
 * @date 2019年05月04日21:54:52.
 */
public class SuggestionAdapter extends EasyLVAdapter<String> {

    private int selected = 0;

    public SuggestionAdapter(Context context, List<String> list, int selected) {
        super(context, list, R.layout.suggestion_item);
        this.selected = selected;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String suggesstion_item) {
        if (!suggesstion_item.equals("")) {
            holder.setText(R.id.item_days_money, suggesstion_item);
            if (selected == position) {
                holder.setVisible(R.id.ivSelected, true);
            } else {
                holder.setVisible(R.id.ivSelected, false);
            }

        }
    }

    public void select(int position) {
        selected = position;
        notifyDataSetChanged();
    }
}
