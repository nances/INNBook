package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.adapter.helper.AbsRecyclerViewAdapter;

import java.util.List;

public class ActivityCenterHotRecyclerAdapter extends AbsRecyclerViewAdapter {
    private List<String> activitys;

    private Context mContext;

    public ActivityCenterHotRecyclerAdapter(RecyclerView recyclerView, List<String> activitys, Context context) {
        super(recyclerView);
        this.activitys = activitys;
        this.mContext = context;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_tag_child, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tag_child_btn_name.setText(activitys.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return activitys.size();
    }


    private class ItemViewHolder extends ClickableViewHolder {

        TextView tag_child_btn_name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tag_child_btn_name = $(R.id.tag_child_btn_name);
        }
    }
}