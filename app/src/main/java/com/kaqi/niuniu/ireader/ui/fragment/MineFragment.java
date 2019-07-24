package com.kaqi.niuniu.ireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.activity.AboutActivity;
import com.kaqi.niuniu.ireader.ui.activity.DownloadActivity;
import com.kaqi.niuniu.ireader.ui.activity.FeedbackActivity;
import com.kaqi.niuniu.ireader.ui.activity.HelpActivity;
import com.kaqi.niuniu.ireader.ui.activity.LoginActivity;
import com.kaqi.niuniu.ireader.ui.activity.MessageActivity;
import com.kaqi.niuniu.ireader.ui.activity.MoneyDetailedListActivity;
import com.kaqi.niuniu.ireader.ui.activity.ReadBookHistoryActivity;
import com.kaqi.niuniu.ireader.ui.activity.SettingActivity;
import com.kaqi.niuniu.ireader.ui.activity.UserInfoActivity;
import com.kaqi.niuniu.ireader.ui.base.BaseFragment;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.SharedPreUtils;
import com.kaqi.niuniu.ireader.widget.MineWaveView;
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
    @BindView(R.id.frag_uc_nickname_tv)
    TextView fragUcNicknameTv;
    @BindView(R.id.regisiter_tv)
    TextView regisiter_tv;
    @BindView(R.id.uc_avater)
    ImageView ucAvater;
    @BindView(R.id.user_bg)
    MineWaveView userBg;
    @BindView(R.id.money_detail_list)
    LinearLayout moneyDetailList;
    @BindView(R.id.downlaod_icon)
    SuperTextView downlaodIcon;
    @BindView(R.id.message_icon)
    SuperTextView messageIcon;
    @BindView(R.id.feedback_stv)
    SuperTextView feedbackStv;
    @BindView(R.id.help_stv)
    SuperTextView helpStv;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.money_mine_iv)
    ImageView moneyMineIv;
    @BindView(R.id.money_total_tv)
    TextView moneyTotalTv;
    @BindView(R.id.money_today_iv)
    ImageView moneyTodayIv;
    @BindView(R.id.money_today_tv)
    TextView moneyTodayTv;


    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarColor(R.color.yellow_30).init();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        userBg.startAnim();
    }

    /**
     * 刷新用户信息
     */
    public void updateUserInfo() {
        if (TextUtils.isEmpty(SharedPreUtils.getInstance().getString(Constant.UID))) {
            regisiter_tv.setVisibility(View.VISIBLE);
        } else {
            regisiter_tv.setVisibility(View.GONE);
            userId.setVisibility(View.VISIBLE);
            fragUcNicknameTv.setVisibility(View.VISIBLE);
            userId.setText(SharedPreUtils.getInstance().getString(Constant.UID));
            fragUcNicknameTv.setText(SharedPreUtils.getInstance().getString(Constant.NICK_NAME));
            Glide.with(getActivity())
                    .load(SharedPreUtils.getInstance().getString(Constant.USER_ICON))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(ucAvater);
        }

    }


    @OnClick({R.id.uc_setting_iv, R.id.regisiter_tv, R.id.uc_avater,
            R.id.about_us, R.id.read_history, R.id.message_icon, R.id.feedback_stv, R.id.help_stv, R.id.money_detail_list, R.id.downlaod_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.uc_setting_iv:
                SettingActivity.startActivity(getActivity());
                break;
            case R.id.regisiter_tv:
                if (TextUtils.isEmpty(SharedPreUtils.getInstance().getString(Constant.UID))) {
                    LoginActivity.startActivity(getActivity());
                }
                break;
            case R.id.uc_avater:
//                if (TextUtils.isEmpty(SharedPreUtils.getInstance().getString(Constant.UID))) {
//                    LoginActivity.startActivity(getActivity());
//                }
                UserInfoActivity.startActivity(getActivity());
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
    public void onResume() {
        super.onResume();
        updateUserInfo();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        userBg.stopAnim();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_mine;
    }

}
