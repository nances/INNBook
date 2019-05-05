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
import com.kaqi.reader.bean.RankingList;
import com.kaqi.reader.common.OnRvItemClickListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author Nancy.
 * @date 2019年04月29日11:23:16
 */
public class TopRankChannleFemaleListAdapter extends EasyRVAdapter<RankingList.FemaleBean> {

    private OnRvItemClickListener<RankingList.FemaleBean> listener;

    public TopRankChannleFemaleListAdapter(Context context, List<RankingList.FemaleBean> list) {
        super(context, list, R.layout.activity_rank_channel);
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final RankingList.FemaleBean item) {
        holder.setText(R.id.tvName, item.getTitle());
        holder.getView(R.id.tvName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, position, item);
            }
        });
    }

    public void setItemClickListener(OnRvItemClickListener<RankingList.FemaleBean> listener) {
        this.listener = listener;
    }
}
