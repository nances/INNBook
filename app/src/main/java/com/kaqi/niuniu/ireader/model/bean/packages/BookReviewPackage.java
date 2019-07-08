package com.kaqi.niuniu.ireader.model.bean.packages;

import com.kaqi.niuniu.ireader.model.bean.BaseBean;
import com.kaqi.niuniu.ireader.model.bean.BookReviewBean;

import java.util.List;

/**
 * Created by newbiechen on 17-4-21.
 */

public class BookReviewPackage extends BaseBean {

    private List<BookReviewBean> reviews;

    public List<BookReviewBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReviewBean> reviews) {
        this.reviews = reviews;
    }
}