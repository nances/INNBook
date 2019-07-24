package com.kaqi.niuniu.ireader.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.RxBus;
import com.kaqi.niuniu.ireader.event.TaskShareEvent;
import com.kaqi.niuniu.ireader.ui.adapter.SignDaysAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseFragment;
import com.kaqi.niuniu.ireader.widget.DrawableCenterButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 任务列表
 *
 * @author Nancy.
 * @date 2019年05月27日18:03:12
 */
public class TaskFragment extends BaseFragment {
    @BindView(R.id.sign_day_gridview)
    GridView signDayGridview;
    @BindView(R.id.sign_day)
    DrawableCenterButton signDay;
    @BindView(R.id.read_video_sign)
    DrawableCenterButton readVideoSign;
    @BindView(R.id.sign_every_days)
    TextView signEveryDays;
    @BindView(R.id.read_every_days)
    TextView readEveryDays;
    @BindView(R.id.share_every_days)
    TextView shareEveryDays;
    @BindView(R.id.read_ad_every_days)
    TextView readAdEveryDays;
    private int curFont = -1;
    List<String> DaySign = new ArrayList<String>();
    SignDaysAdapter signDaysAdapter;

    public boolean isReadDays = false;
    Drawable drawable = null;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarColor(R.color.yellow_30).init();

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
    protected int getContentId() {
        return R.layout.activity_task;
    }

    @OnClick({R.id.sign_day, R.id.read_video_sign
            , R.id.sign_every_days, R.id.read_every_days, R.id.share_every_days, R.id.read_ad_every_days})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_day:
                signDay.setSelected(signEveryDays.isSelected() ? false : true);
                break;
            case R.id.read_video_sign:
                readVideoSign.setSelected(signEveryDays.isSelected() ? false : true);
                break;
            case R.id.sign_every_days:
                signEveryDays.setSelected(signEveryDays.isSelected() ? false : true);
                break;
            case R.id.read_every_days:
//                ((MainActivity) activity).setCurrentItem(1);
                break;
            case R.id.share_every_days:
                RxBus.getInstance().post(new TaskShareEvent(1));
                break;
            case R.id.read_ad_every_days:
                readAdEveryDays.setSelected(signEveryDays.isSelected() ? false : true);
                break;
        }
    }
}
