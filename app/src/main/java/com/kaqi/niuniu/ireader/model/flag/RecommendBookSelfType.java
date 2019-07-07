package com.kaqi.niuniu.ireader.model.flag;

import android.support.annotation.StringRes;

import com.kaqi.niuniu.ireader.App;
import com.kaqi.niuniu.ireader.R;

/**
 * Created by niqiao on 17-5-1.
 */

public enum RecommendBookSelfType {
    HOT(R.string.nb_fragment_book_list_hot,"recommend_hots"),
    NEWEST(R.string.nb_fragment_book_list_boys,"recommend_boys"),
    COLLECT(R.string.nb_fragment_book_list_grils,"recommend_girls")
    ;
    private String typeName;
    private String netName;

    RecommendBookSelfType(@StringRes int typeName, String netName){
        this.typeName = App.getContext().getString(typeName);
        this.netName = netName;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getNetName(){
        return netName;
    }
}
