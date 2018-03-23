package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:     赛事介绍控制页
 * 修订历史:
 */
public class GameIntroduceActivity extends BaseActivity {
    String mMatchId;
    private String[] titles = {"赛事信息", "赛事规程", "赛事设项", "赛程表"};
    private ArrayList<Fragment> list;
    private TabLayout           tab;
    private ViewPager           viewPager;
    private MyViewPagerAdapter  adapter;

    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_introduce;
    }

    @Override
    public void initDatas() {
        super.initDatas();

    }

    @Override
    public void initViews() {
        setTitle("赛事介绍");
        Bundle bundle = new Bundle();
        bundle.putString("id", mMatchId);
        tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.vp);
        tab.setTabMode(TabLayout.MODE_FIXED);
        list = new ArrayList<>();
        TabMessage tabMessage = new TabMessage();
        TabRegulation tabRegulation = new TabRegulation();
        TabProject tabProject = new TabProject();
        TabCard tabCard = new TabCard();
        list.add(tabMessage);
        list.add(tabRegulation);
        list.add(tabProject);
        list.add(tabCard);
        tabMessage.setArguments(bundle);
        tabRegulation.setArguments(bundle);
        tabProject.setArguments(bundle);
        tabCard.setArguments(bundle);
        adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //绑定
        tab.setupWithViewPager(viewPager);
        //     viewPager.setOffscreenPageLimit(0);
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

    public static void lunch(Context context, String matchId) {
        Intent intent = new Intent(context, GameIntroduceActivity.class);
        intent.putExtra("matchid", matchId);
        context.startActivity(intent);
    }
}
