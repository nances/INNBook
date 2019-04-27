package com.kaqi.reader.view.magicindicator;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;

public class BoldPagerTitleView extends AppCompatTextView implements IMeasurablePagerTitleView {
    protected int mSelectedColor;
    protected int mNormalColor;
    protected float mSelectedSize;
    protected float mNormalSize;

    public BoldPagerTitleView(Context context) {
        super(context, (AttributeSet) null);
        this.init(context);
    }

    private void init(Context context) {
        this.setGravity(17);
        int padding = UIUtil.dip2px(context, 10.0D);
        this.setPadding(padding, 0, padding, 0);
        this.setSingleLine();
        this.setEllipsize(TextUtils.TruncateAt.END);
    }

    public void onSelected(int index, int totalCount) {
        this.setTextColor(this.mSelectedColor);
        this.setTextSize(this.mSelectedSize);
        this.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    public void onDeselected(int index, int totalCount) {
        this.setTextColor(this.mNormalColor);
        this.setTextSize(this.mNormalSize);
        this.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//常规
    }

    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    public int getContentLeft() {
        Rect bound = new Rect();
        this.getPaint().getTextBounds(this.getText().toString(), 0, this.getText().length(), bound);
        int contentWidth = bound.width();
        return this.getLeft() + this.getWidth() / 2 - contentWidth / 2;
    }

    public int getContentTop() {
        Paint.FontMetrics metrics = this.getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) ((float) (this.getHeight() / 2) - contentHeight / 2.0F);
    }

    public int getContentRight() {
        Rect bound = new Rect();
        this.getPaint().getTextBounds(this.getText().toString(), 0, this.getText().length(), bound);
        int contentWidth = bound.width();
        return this.getLeft() + this.getWidth() / 2 + contentWidth / 2;
    }

    public int getContentBottom() {
        Paint.FontMetrics metrics = this.getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) ((float) (this.getHeight() / 2) + contentHeight / 2.0F);
    }

    public int getSelectedColor() {
        return this.mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        return this.mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        this.mNormalColor = normalColor;
    }

    public float getSelectedSize() {
        return mSelectedSize;
    }

    public void setSelectedSize(float mSelectedSize) {
        this.mSelectedSize = mSelectedSize;
    }

    public float getNormalSize() {
        return mNormalSize;
    }

    public void setNormalSize(float mNormalSize) {
        this.mNormalSize = mNormalSize;
    }
}
