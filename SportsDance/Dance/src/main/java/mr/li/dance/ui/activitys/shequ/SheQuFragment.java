package mr.li.dance.ui.activitys.shequ;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.ui.fragments.BaseFragment;
import mr.li.dance.utils.AppConfigs;

import static mr.li.dance.ui.activitys.MainActivity.myBinder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/23 0023
 * 描述:   社区控制
 * 修订历史:
 */
public class SheQuFragment extends BaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private List<Fragment> mFragment = new ArrayList<>();
    ViewPager            mViewPaper;
    FragmentPagerAdapter mPagerAdapter;
    private RadioGroup mTitleRg;

    @Override
    public void initData() {
        Log.e("-----------------","zzzzzzzzzzzzzzzzzzzzzzzz");
        //setScreen();
        mFragment.add(new NewsFragment());
        mFragment.add(new HotFragment());
        FragmentManager mFragmentManager = getChildFragmentManager();
        mPagerAdapter = new FragmentPagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
    }

    @Override
    public void initViews() {

        danceViewHolder.setClickListener(R.id.btn_music, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBinder != null && myBinder.binderIsPlaying()) {
                    PlayMusicActivity.lunch(getActivity());
                } else {
                    Toast.makeText(getActivity(), "请去播放音乐", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mViewPaper = (ViewPager) danceViewHolder.getView(R.id.shequ_viewpager);
        mViewPaper.setAdapter(mPagerAdapter);
        mViewPaper.setOnPageChangeListener(this);
        mTitleRg = (RadioGroup) danceViewHolder.getView(R.id.shequ_title_rg);
        mTitleRg.setOnCheckedChangeListener(this);
    }

    @Override
    public int getContentView() {
        return R.layout.shequ_fragment;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mTitleRg.check(R.id.news_rb);
                break;
            case 1:
                mTitleRg.check(R.id.hot_rb);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {

        switch (checkedId) {
            case R.id.news_rb:
                MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_29);
                mViewPaper.setCurrentItem(0);
                break;
            case R.id.hot_rb:
                MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_30);
                mViewPaper.setCurrentItem(1);
                break;
        }
    }
}
