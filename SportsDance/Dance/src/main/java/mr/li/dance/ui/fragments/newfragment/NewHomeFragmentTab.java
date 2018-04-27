package mr.li.dance.ui.fragments.newfragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.SearchActivity;
import mr.li.dance.ui.fragments.BaseFragment;
import mr.li.dance.ui.fragments.secondFragment.SecondHomeFragment;
import mr.li.dance.ui.fragments.secondFragment.SecondMessageFragment;
import mr.li.dance.ui.fragments.secondFragment.SecondPicFragment;
import mr.li.dance.ui.fragments.secondFragment.SecondVideoFragment;
import mr.li.dance.utils.AppConfigs;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/4/25 0025
 * 描述:
 * 修订历史:
 */
public class NewHomeFragmentTab extends BaseFragment {
    private String[] titles = {"首页", "资讯", "视频", "图片"};
    private TabLayout           tab;
    private ViewPager           viewPager;
    private ArrayList<Fragment> list;
    private MyViewPagerAdapter  myViewPagerAdapter;

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        //搜索按钮
        danceViewHolder.setClickListener(R.id.label_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_6);
                SearchActivity.lunch(getActivity());
            }
        });
        tab = (TabLayout) danceViewHolder.getView(R.id.tab);
        viewPager = (ViewPager) danceViewHolder.getView(R.id.vp);
        tab.setTabMode(TabLayout.MODE_FIXED);
        list = new ArrayList<>();
        SecondHomeFragment secondHomeFragment = new SecondHomeFragment();
        SecondMessageFragment secondMessageFragment = new SecondMessageFragment();
        SecondVideoFragment secondVideoFragment = new SecondVideoFragment();
        SecondPicFragment secondPicFragment = new SecondPicFragment();

        list.add(secondHomeFragment);
        list.add(secondMessageFragment);
        list.add(secondVideoFragment);
        list.add(secondPicFragment);
        myViewPagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        tab.setupWithViewPager(viewPager);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public int getContentView() {
        return R.layout.basefragment_all_tab;
    }

}
