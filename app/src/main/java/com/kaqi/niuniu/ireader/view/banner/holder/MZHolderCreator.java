package com.kaqi.niuniu.ireader.view.banner.holder;

/**
 * Created by Niqiao on 17/5/26.
 */

public interface MZHolderCreator<VH extends MZViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
