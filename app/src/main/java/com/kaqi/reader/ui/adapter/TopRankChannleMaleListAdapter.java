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
public class TopRankChannleMaleListAdapter extends EasyRVAdapter<RankingList.MaleBean> {

    private OnRvItemClickListener<RankingList.MaleBean> listener;
    private SparseBooleanArray mBooleanArray;
    private int mLastCheckedPosition = -1;

    public TopRankChannleMaleListAdapter(Context context, List<RankingList.MaleBean> list) {
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
    protected void onBindData(final EasyRVHolder holder, final int position, final RankingList.MaleBean item) {

        if (!mBooleanArray.get(position)) {
            //没有选中
            holder.getView(R.id.tvName_lr).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.getView(R.id.tvName_lr).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
        }


        holder.setText(R.id.tvName, item.getTitle());
        holder.getView(R.id.tvName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBooleanArray.get(position)) {
                    listener.onItemClick(v, position, item);
                    setItemChecked(position);
                }
            }
        });
    }

    public void setItemClickListener(OnRvItemClickListener<RankingList.MaleBean> listener) {
        this.listener = listener;
    }
}
