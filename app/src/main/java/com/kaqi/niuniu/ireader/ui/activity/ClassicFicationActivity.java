package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.TabEntity;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.ui.fragment.ClassFicationItemFragment;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;

public class ClassicFicationActivity extends BaseActivity {

    @BindView(R.id.indicator)
    VerticalTabLayout slidingTabLayout;
    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    private String[] mTitles = {"男主","女主"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    ClassFicationItemFragment fragment;
    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    private long exitTime;
    //记录当前TAB的标题
    private String curTabTile;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ClassicFicationActivity.class));
    }

    @Override
    public int getContentId() {
        return R.layout.activity_classfication;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonToolbar.setTitleText(R.string.category);
        commonToolbar.setBackVisibility(true);
        initTab();
        //初始化Fragment
        slidingTabLayout.measure(0, 0);
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        slidingTabLayout.setupWithFragment(getSupportFragmentManager(), R.id.container, getFragments()
                , new TabAdapter() {
                    @Override
                    public int getCount() {
                        return mTabEntities.size();
                    }

                    @Override
                    public QTabView.TabBadge getBadge(int position) {
                        return null;
                    }

                    @Override
                    public QTabView.TabIcon getIcon(int position) {
                        return null;
                    }

                    @Override
                    public QTabView.TabTitle getTitle(int position) {
                        return new QTabView.TabTitle.Builder().setContent(mTitles[position]).build();
                    }

                    @Override
                    public int getBackground(int position) {
                        return 0;
                    }
                });
        slidingTabLayout.setTabSelected(0);
    }

    /**
     * create Fragment
     * @data 2019年06月29日17:46:18
     * @return
     */
    private List<Fragment> getFragments() {

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTabEntities.size(); i++) {
            fragment = new ClassFicationItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", mTitles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        return fragments;
    }

}
