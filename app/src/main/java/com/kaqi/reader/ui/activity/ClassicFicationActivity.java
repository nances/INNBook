package com.kaqi.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.bean.TabEntity;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.fragment.ClassFicationItemFragment;
import com.kaqi.reader.utils.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;

public class ClassicFicationActivity extends BaseActivity {

    @Bind(R.id.indicator)
    VerticalTabLayout slidingTabLayout;
    @Bind(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    private String[] mTitles = {"男生", "女生"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ClassFicationItemFragment classBoyFicationItemFragment;
    private ClassFicationItemFragment classGirlFicationItemFragment;


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
    public int getLayoutId() {
        return R.layout.activity_classfication;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        commonToolbar.setTitleText(R.string.category);
        commonToolbar.setBackVisibility(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTabEntities.size(); i++) {
            ClassFicationItemFragment fragment = new ClassFicationItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", mTitles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
            Log.v("Nancy", "mTitles[i]" + mTitles[i]);
        }
        return fragments;
    }


    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }
}
