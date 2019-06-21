package com.kaqi.reader.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.kaqi.reader.R;
import com.kaqi.reader.bean.ItemModel;

/**
 * @author Nancy
 * @date 2019年05月31日19:25:37
 */
public class MoneyListAdapter extends ListBaseAdapter<ItemModel> {

    public MoneyListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_item_text;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ItemModel item = mDataList.get(position);

        TextView titleText = holder.getView(R.id.money_time);
        titleText.setText(item.title);
    }
}