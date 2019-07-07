package com.kaqi.niuniu.ireader.model.bean.packages;

import com.kaqi.niuniu.ireader.model.bean.BaseBean;
import com.kaqi.niuniu.ireader.model.bean.BookChapterBean;

import java.util.List;

/**
 * Created by newbiechen on 17-5-10.
 */

public class BookChapterPackage extends BaseBean {


    private String _id;
    private String book;
    private int chaptersCount1;
    private String chaptersUpdated;
    private String updated;
    private List<BookChapterBean> chapters;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getChaptersCount1() {
        return chaptersCount1;
    }

    public void setChaptersCount1(int chaptersCount1) {
        this.chaptersCount1 = chaptersCount1;
    }

    public String getChaptersUpdated() {
        return chaptersUpdated;
    }

    public void setChaptersUpdated(String chaptersUpdated) {
        this.chaptersUpdated = chaptersUpdated;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public List<BookChapterBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<BookChapterBean> chapters) {
        this.chapters = chapters;
    }
}
