package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.adapter.SuggestionAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    @BindView(R.id.mRecyclerView)
    GridView mRecyclerView;
    @BindView(R.id.submit_btn)
    TextView submitBtn;

    private int curFont = -1;
    List<String> questionList = new ArrayList<String>();
    SuggestionAdapter suggestionAdapter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void initWidget() {
        commonToolbar.setTitleText("反馈建议");
        commonToolbar.setBackVisibility(true);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initQuestionList();
        suggestionAdapter = new SuggestionAdapter(this, (questionList), curFont);

        mRecyclerView.setAdapter(suggestionAdapter);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                suggestionAdapter.select(position);
            }
        });
    }


    /**
     * 问题反馈信息
     */
    public void initQuestionList() {
        questionList.add("读书赚钱");
        questionList.add("现金/金币");
        questionList.add("福利中心");
        questionList.add("章节问题");
        questionList.add("个人信息");
        questionList.add("产品功能");

    }
}
