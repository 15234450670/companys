package mr.li.dance.ui.activitys.newActivitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import mr.li.dance.models.LabelInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/11 0011
 * 描述:
 * 修订历史:
 */
public class NewViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment>  mList;
    List<LabelInfo> mLabel;
    public NewViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<LabelInfo> name) {
        super(fm);
        this.mLabel = name;
        this.mList = list;

    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mLabel.get(position).getClass_name();
    }
}
