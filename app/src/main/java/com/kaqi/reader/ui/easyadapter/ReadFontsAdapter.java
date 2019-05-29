package com.kaqi.reader.ui.easyadapter;

import android.content.Context;

import com.kaqi.reader.R;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author Nancy.
 * @date 2019年05月04日21:54:52.
 */
public class ReadFontsAdapter extends EasyLVAdapter<String> {

    private int selected = 0;

    public ReadFontsAdapter(Context context, List<String> list, int selected) {
        super(context, list, R.layout.item_read_font);
        this.selected = selected;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String readTheme) {
        if (readTheme != null) {
            holder.setText(R.id.btn_yy, readTheme);
            if (selected == position) {
                holder.setVisible(R.id.ivSelected, true);
                holder.setTextColor(R.id.btn_yy, mContext.getResources().getColor(R.color.black));
            } else {
                holder.setTextColor(R.id.btn_yy, mContext.getResources().getColor(R.color.white));
                holder.setVisible(R.id.ivSelected, false);
            }

        }
    }

    public void select(int position) {
        selected = position;
        notifyDataSetChanged();
    }
}
