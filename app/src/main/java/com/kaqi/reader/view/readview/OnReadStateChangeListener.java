package com.kaqi.reader.view.readview;

public interface OnReadStateChangeListener {

    void onChapterChanged(int chapter,boolean nextPage);

    void onPageChanged(int chapter, int page);

    void onLoadChapterFailure(int chapter);

    void onLoadAdChapterChange();

    void onCenterClick();

    void onFlip();
}
