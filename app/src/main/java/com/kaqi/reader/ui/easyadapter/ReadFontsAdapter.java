/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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