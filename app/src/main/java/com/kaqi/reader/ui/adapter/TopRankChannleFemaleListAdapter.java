package com.kaqi.reader.ui.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
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
    private SparseBooleanArray mBooleanArray;
    private int mLastCheckedPosition = -1;

    public TopRankChannleFemaleListAdapter(Context context, List<RankingList.FemaleBean> list) {
        super(context, list, R.layout.activity_rank_channel);
        this.mBooleanArray = new SparseBooleanArray(list.size());
    }

    public void setItemChecked(int position) {
        mBooleanArray.put(position, true);
        if (mLastCheckedPosition > -1) {
            mBooleanArray.put(mLastCheckedPosition, false);
            notifyItemChanged(mLastCheckedPosition);
        }
        notifyDataSetChanged();
        mLastCheckedPosition = position;
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final RankingList.FemaleBean item) {


        holder.setText(R.id.tvName, item.getTitle());
        if (!mBooleanArray.get(position)) {
            //没有选中
            holder.getView(R.id.tvName_lr).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.getView(R.id.tvName_lr).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
        }

        holder.getView(R.id.tvName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBooleanArray.get(position)) {
                    setItemChecked(position);
                    listener.onItemClick(v, position, item);
                }

            }
        });
    }

    public void setItemClickListener(OnRvItemClickListener<RankingList.FemaleBean> listener) {
        this.listener = listener;
    }
}
