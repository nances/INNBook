package com.kaqi.niuniu.ireader.view;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.utils.Utils;


/**
 * 标题控件
 */
public class HourglassView extends TextView implements View.OnClickListener {

    private OnClickListener listener;
    private MyCount myCount;
    private EditText editText;
    private TextView area;
    private Context context;

    public boolean isPhone() {
        return isPhone;
    }

    public void setPhone(boolean phone) {
        isPhone = phone;
    }

    boolean isPhone = true;

    public HourglassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setText("获取验证码");
        // this.setTextColor(Color.parseColor("#848484"));
        this.setTextSize(12);
        this.setOnClickListener(this);
        this.setClickable(false);
        this.context = context;


    }

    public void setOnclick(OnClickListener listener) {
        this.listener = listener;
        myCount = new MyCount(this);
        this.setClickable(true);

    }

    public void OnAttch(EditText editText) {
        this.editText = editText;

    }

    public void OnAttch(EditText editText, TextView area) {
        this.area = area;
        this.editText = editText;

    }

    @Override
    public void onClick(View v) {
        if (isPhone) {
            if (editText != null) {
                if (Utils.isPhone(context, editText.getText().toString(), area != null ? area.getText().toString() : "86")) {
                    myCount.start();
                    this.setClickable(false);
                    this.setTextColor(Color.GRAY);
                    listener.onClick(v);
                }

            } else {
                Toast.makeText(context, "请为短信倒计时按钮绑定EditText", Toast.LENGTH_SHORT).show();
            }
        } else {
            myCount.start();
            this.setClickable(false);
            this.setTextColor(Color.GRAY);
            listener.onClick(v);
        }

    }

    /**
     * 时间倒计时，刷新view
     *
     * @author liming
     */
    class MyCount extends CountDownTimer {
        TextView textView;

        public MyCount(TextView textView) {
            super(60000, 1000);
            this.textView = textView;
        }

        @Override
        public void onFinish() {
            if (this != null) {
                this.cancel();
                // textView.setVisibility(View.GONE);
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setText("重新获取");
                textView.setClickable(true);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText(millisUntilFinished / 1000 + "S");
        }
    }
}
