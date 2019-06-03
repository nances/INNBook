package com.kaqi.reader.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaqi.reader.R;

import butterknife.Bind;

public class CommomRechargeMoneyDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView outMoneyTv;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private String data[] = {"5元", "8元", "12元", "20元", "30元", "50元"};
    @Bind(R.id.pay_grid)
    GridView pay_grid;
    String selectedStr = "0元";
    private MyAdapter rechargeAdapter;
    private String rechageMoney;
    public CommomRechargeMoneyDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomRechargeMoneyDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CommomRechargeMoneyDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    protected CommomRechargeMoneyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomRechargeMoneyDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommomRechargeMoneyDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public CommomRechargeMoneyDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    private void initView() {
        pay_grid = (GridView) findViewById(R.id.pay_grid);
        outMoneyTv = (TextView) findViewById(R.id.out_money);
        outMoneyTv.setOnClickListener(this);
        rechargeAdapter = new MyAdapter();
        pay_grid.setAdapter(rechargeAdapter);
        pay_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("Nancys","i is value : " + i);
                rechageMoney = data[i];
            }
        });
    }

    public void setSelct() {
        outMoneyTv.setText("提现");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.out_money:
                if (listener != null && outMoneyTv.getText().toString().equals("提现")) {
                    listener.onClick(this, selectedStr);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, String  money);
    }

    class MyAdapter extends BaseAdapter {
        ViewHolder holder;
        boolean canFocusable = false;
        boolean isFirst = true;
        String firstStr = "";

        public void setEditDefault(String str) {
            firstStr = str;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.rechage_item, null);
                holder.pay_num = (TextView) convertView.findViewById(R.id.pay_num);
                holder.pay_rl = (RelativeLayout) convertView.findViewById(R.id.pay_rl);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String temp = (String) getItem(position);
            holder.pay_num.setText(temp);
            holder.pay_rl.setTag(temp);

            if (selectedStr.equals(holder.pay_rl.getTag())) {
                holder.pay_rl.setSelected(true);
                holder.pay_num.setTextColor(mContext.getResources().getColor(R.color.yellow_30));
                setSelct();
            } else {
                holder.pay_rl.setSelected(false);
                holder.pay_num.setTextColor(mContext.getResources().getColor(R.color.common_h1));
            }

            holder.pay_rl.setOnClickListener(itemClickListener);

            return convertView;
        }


        private class ViewHolder {
            private RelativeLayout pay_rl;
            private TextView pay_num;
        }

        private View.OnClickListener editClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof EditText) {
                    canFocusable = true;
                    notifyDataSetChanged();
                }
            }
        };


        private View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() instanceof String) {
                    canFocusable = false;
                    selectedStr = (String) v.getTag();
                    notifyDataSetChanged();

                }

            }
        };

    }

}