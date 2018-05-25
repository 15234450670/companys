package mr.li.dance.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/5/23 0023
 * 描述:
 * 修订历史:
 */
public class ZhiBoPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public ZhiBoPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}