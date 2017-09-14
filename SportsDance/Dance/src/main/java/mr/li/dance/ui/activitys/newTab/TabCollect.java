package mr.li.dance.ui.activitys.newTab;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.fragments.homepage.MusicFragment;
import mr.li.dance.ui.fragments.newfragment.NewCollectXCFragment;
import mr.li.dance.ui.fragments.newfragment.NewCollectvideoFragment;
import mr.li.dance.utils.AppConfigs;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/5 0005
 * 描述:
 * 修订历史:
 */
public class TabCollect extends BaseActivity implements View.OnClickListener {

    private FragmentManager      fragmentManager;
    private FragmentTransaction  beginTransaction;
    private MusicFragment        MusicFragment;
    private NewCollectXCFragment             xcFragment;
    private NewCollectvideoFragment spFragment;
    private View                    v;
    private View                    v1;
    private View                    v2;

    @Override
    public int getContentViewId() {
        return R.layout.activity_tabcollect;
    }

    @Override
    public void initViews() {
        setTitle("我的收藏");
        mDanceViewHolder.setClickListener(R.id.collect_gd, this);
        mDanceViewHolder.setClickListener(R.id.collect_sp, this);
        mDanceViewHolder.setClickListener(R.id.collect_xc, this);
        v = findViewById(R.id.collect_sp_v);
        v1 = findViewById(R.id.collect_xc_v);
        v2 = findViewById(R.id.collect_gd_v);
        controlFragment(spFragment);
        showView(v,v1,v2,R.color.red_normal);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        fragmentManager = getSupportFragmentManager();
        spFragment = new NewCollectvideoFragment();
        xcFragment = new NewCollectXCFragment();
        MusicFragment = new MusicFragment();
    }

    //跳转
    public static void lunch(Context context) {
        context.startActivity(new Intent(context, TabCollect.class));
    }

    //添加到meLayout
    public void controlFragment(Fragment f1) {
        beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.collect_fragment, f1);
      //  showAndReplace(f1)  ;
        beginTransaction.commit();
    }

    public void showView(View view,View view1,View view2,int color) {
        view.setVisibility(View.VISIBLE);
        view.setBackgroundResource(color);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);

    }

    //点击切换
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_sp:
                MobclickAgent.onEvent(this, AppConfigs.CLICK_EVENT_14);
                showView(v,v1,v2,R.color.red_normal);
               // showAndReplace(spFragment);
                controlFragment(spFragment);
                break;
            case R.id.collect_xc:
                MobclickAgent.onEvent(this, AppConfigs.CLICK_EVENT_13);
                showView(v1,v,v2,R.color.red_normal);
               // showAndReplace(xcFragment);
                controlFragment(xcFragment);
                break;
            case R.id.collect_gd:
                showView(v2,v,v1,R.color.red_normal);
               // showAndReplace(MusicFragment);
                controlFragment(MusicFragment);
                break;
        }
    }

}
