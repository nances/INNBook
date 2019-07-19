package com.kaqi.niuniu.ireader.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.activity.AboutActivity;
import com.kaqi.niuniu.ireader.ui.activity.DownloadActivity;
import com.kaqi.niuniu.ireader.ui.activity.FeedbackActivity;
import com.kaqi.niuniu.ireader.ui.activity.HelpActivity;
import com.kaqi.niuniu.ireader.ui.activity.LoginActivity;
import com.kaqi.niuniu.ireader.ui.activity.MessageActivity;
import com.kaqi.niuniu.ireader.ui.activity.MoneyDetailedListActivity;
import com.kaqi.niuniu.ireader.ui.activity.ReadBookHistoryActivity;
import com.kaqi.niuniu.ireader.ui.activity.RegisterActivity;
import com.kaqi.niuniu.ireader.ui.activity.SettingActivity;
import com.kaqi.niuniu.ireader.ui.base.BaseFragment;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.SharedPreUtils;
import com.kaqi.niuniu.ireader.widget.textview.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 * 2019年04月23日12:58:37
 *
 * @author niqiao
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.uc_setting_iv)
    SuperTextView ucSettingIv;
    @BindView(R.id.about_us)
    SuperTextView about_us;
    @BindView(R.id.read_history)
    SuperTextView read_history;
    @BindView(R.id.uc_msg_iv)
    ImageView ucMsgIv;
    @BindView(R.id.frag_uc_nickname_tv)
    TextView fragUcNicknameTv;
    @BindView(R.id.regisiter_tv)
    TextView regisiter_tv;
    @BindView(R.id.login_tv)
    TextView login_tv;
    @BindView(R.id.uc_avater)
    ImageView ucAvater;

    public static MineFragment newInstance() {
        return new MineFragment();
    }


    @OnClick({R.id.uc_setting_iv, R.id.uc_msg_iv, R.id.regisiter_tv, R.id.login_tv, R.id.uc_avater,
            R.id.about_us, R.id.read_history, R.id.message_icon, R.id.feedback_stv, R.id.help_stv, R.id.money_detail_list, R.id.downlaod_icon})
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
                if("".equals(SharedPreUtils.getInstance().getString(Constant.UID))){
                    LoginActivity.startActivity(getActivity());
                }
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
//                if("".equals(SharedPreUtils.getInstance().getString(Constant.UID))){
//                    LoginActivity.startActivity(getActivity());
//                }else{
//                }
                MessageActivity.startActivity(getActivity());
                break;
            case R.id.feedback_stv:
                FeedbackActivity.startActivity(getActivity());
                break;
            case R.id.help_stv:
                HelpActivity.startActivity(getActivity());
                break;
            case R.id.money_detail_list:
                MoneyDetailedListActivity.startActivity(getActivity());
                break;
            case R.id.downlaod_icon:
                DownloadActivity.startActivity(getActivity());
                break;

        }
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_mine;
    }
}
