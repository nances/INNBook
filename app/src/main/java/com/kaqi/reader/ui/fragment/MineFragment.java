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

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.activity.AboutActivity;
import com.kaqi.reader.ui.activity.FeedbackActivity;
import com.kaqi.reader.ui.activity.LoginActivity;
import com.kaqi.reader.ui.activity.ReadBookHistoryActivity;
import com.kaqi.reader.ui.activity.RegisterActivity;
import com.kaqi.reader.ui.activity.SettingActivity;
import com.kaqi.reader.view.textview.SuperTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心
 * 2019年04月23日12:58:37
 *
 * @author niqiao
 */
public class MineFragment extends BaseFragment {

    @Bind(R.id.uc_setting_iv)
    SuperTextView ucSettingIv;
    @Bind(R.id.about_us)
    SuperTextView about_us;
    @Bind(R.id.read_history)
    SuperTextView read_history;
    @Bind(R.id.uc_msg_iv)
    ImageView ucMsgIv;
    @Bind(R.id.frag_uc_nickname_tv)
    TextView fragUcNicknameTv;
    @Bind(R.id.regisiter_tv)
    TextView regisiter_tv;
    @Bind(R.id.login_tv)
    TextView login_tv;
    @Bind(R.id.uc_avater)
    ImageView ucAvater;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {


    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.uc_setting_iv, R.id.uc_msg_iv, R.id.regisiter_tv, R.id.login_tv, R.id.uc_avater,
            R.id.about_us, R.id.read_history, R.id.message_icon, R.id.feedback_stv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.uc_setting_iv:
                SettingActivity.startActivity(getActivity());
                break;
            case R.id.uc_msg_iv:
                break;
            case R.id.regisiter_tv:
                RegisterActivity.startAction(getActivity());
                break;
            case R.id.login_tv:
                LoginActivity.startActivity(getActivity());
                break;
            case R.id.uc_avater:

                break;
            case R.id.about_us:
                AboutActivity.startActivity(getActivity());
                break;
            case R.id.read_history:
                ReadBookHistoryActivity.startActivity(getActivity());
                break;
            case R.id.message_icon:

                break;
            case R.id.feedback_stv:
                FeedbackActivity.startActivity(getActivity());
                break;
        }
    }
}
