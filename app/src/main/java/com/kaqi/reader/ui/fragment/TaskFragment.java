/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaqi.reader.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.adapter.SignDaysAdapter;
import com.kaqi.reader.view.DrawableCenterButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务列表
 *
 * @author Nancy.
 * @date 2019年05月27日18:03:12
 */
public class TaskFragment extends BaseFragment {
    @Bind(R.id.sign_day_gridview)
    GridView signDayGridview;
    @Bind(R.id.sign_day)
    DrawableCenterButton signDay;
    @Bind(R.id.read_video_sign)
    DrawableCenterButton readVideoSign;
    private int curFont = -1;
    List<String> DaySign = new ArrayList<String>();
    SignDaysAdapter signDaysAdapter;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_task;
    }

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }


    @Override
    public void initDatas() {
        getSignDays();
        signDaysAdapter = new SignDaysAdapter(getActivity(), (DaySign), curFont);

        signDayGridview.setAdapter(signDaysAdapter);
        signDayGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                signDaysAdapter.select(position);
            }
        });
    }

    @Override
    public void configViews() {


    }

    /**
     * 获取金币
     */
    private void getSignDays() {
        DaySign.add("36");
        DaySign.add("48");
        DaySign.add("62");
        DaySign.add("76");
        DaySign.add("92");
        DaySign.add("128");
        DaySign.add("158");
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.sign_day, R.id.read_video_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_day:
                break;
            case R.id.read_video_sign:
                break;
        }
    }
}
