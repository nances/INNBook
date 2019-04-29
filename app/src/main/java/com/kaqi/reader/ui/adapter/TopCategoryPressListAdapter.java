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
package com.kaqi.reader.ui.adapter;

import android.content.Context;
import android.view.View;

import com.kaqi.reader.R;
import com.kaqi.reader.base.Constant;
import com.kaqi.reader.bean.CategoryList;
import com.kaqi.reader.common.OnRvItemClickListener;
import com.kaqi.reader.ui.activity.SubCategoryListActivity;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author Nancy.
 * @date 2019年04月29日11:23:16
 */
public class TopCategoryPressListAdapter extends EasyRVAdapter<CategoryList.PressBean> {
    private OnRvItemClickListener itemClickListener;

    public TopCategoryPressListAdapter(Context context, List<CategoryList.PressBean> list) {
        super(context, list, R.layout.item_top_category_list);
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final CategoryList.PressBean item) {
        holder.setText(R.id.tvName, item.getName())
                .setText(R.id.tvBookCount, String.format(mContext.getString(R.string
                        .category_book_count), item.getBookCount()));
        if(item.getBookCover()!=null && item.getBookCover().size()>0){
            holder.setRoundImageUrl(R.id.ivRecommendCover, Constant.IMG_BASE_URL + item.getBookCover().get(0).toString(),
                    R.drawable.cover_default);

        }
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubCategoryListActivity.startActivity(mContext, item.getName(), Constant.Gender.FEMALE);
            }
        });
    }

}
