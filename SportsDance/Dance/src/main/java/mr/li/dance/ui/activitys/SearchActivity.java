
package mr.li.dance.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.fragments.SearchFragment;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/6/5
 * 描述: 首页搜索页面
 * 修订历史:
 */


public class SearchActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    FragmentManager mFragmentManager;
    SearchFragment  mCurrentFragment;
    //SearchFragment  mZhiboFragment;
    SearchFragment  mVideoFragment;
    SearchFragment  mZiXunFragment;
    SearchFragment  mPicFragment;
    SearchFragment  mMusicFragment;
    SearchFragment  mTeachFragment;
    RadioGroup      mTitleRg;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initDatas() {
        super.initDatas();
       /* mFragmentManager = getSupportFragmentManager();
        mZhiboFragment = new SearchFragment();
        Bundle zhiBoBundle = new Bundle();
        zhiBoBundle.putString("type", "video_live");
        mZhiboFragment.setArguments(zhiBoBundle);*/
        mFragmentManager = getSupportFragmentManager();
        //资讯
        mZiXunFragment = new SearchFragment();
        Bundle ziXunBundle = new Bundle();
        ziXunBundle.putString("type", "article");
        mZiXunFragment.setArguments(ziXunBundle);
        //教学
        mTeachFragment = new SearchFragment();
        Bundle teachBundle = new Bundle();
        teachBundle.putString("type", "teach");
        mTeachFragment.setArguments(teachBundle);
        //视频
        mVideoFragment = new SearchFragment();
        Bundle videoBundle = new Bundle();
        videoBundle.putString("type", "video");
        mVideoFragment.setArguments(videoBundle);
        //音乐
        mMusicFragment = new SearchFragment();
        Bundle musicBundle = new Bundle();
        musicBundle.putString("type", "music_class");
        mMusicFragment.setArguments(musicBundle);
        //图片
        mPicFragment = new SearchFragment();
        Bundle picBundle = new Bundle();
        picBundle.putString("type", "photo_class");
        mPicFragment.setArguments(picBundle);


        mCurrentFragment = mZiXunFragment;
    }

    @Override
    public void initViews() {
        setHeadVisibility(View.GONE);
        mTitleRg = (RadioGroup) mDanceViewHolder.getView(R.id.title_rg);
        mTitleRg.setOnCheckedChangeListener(this);
        mDanceViewHolder.setClickListener(R.id.back_icon, this);
        mDanceViewHolder.setClickListener(R.id.search_btn, SearchActivity.this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.content_fl, mZiXunFragment).commitAllowingStateLoss();
    }

    String type = "article";

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(mCurrentFragment);
        switch (checkedId) {
            case R.id.directseeding_rb:
                if (!mZiXunFragment.isAdded()) {
                    transaction.add(R.id.content_fl, mZiXunFragment);
                }
                type = "article";

                mCurrentFragment = mZiXunFragment;
                break;
            case R.id.video_rb:
                if (!mTeachFragment.isAdded()) {
                    transaction.add(R.id.content_fl, mTeachFragment);
                }
                mCurrentFragment = mTeachFragment;
                type = "teach";
                break;
            case R.id.consultation_rb:
                if (!mVideoFragment.isAdded()) {
                    transaction.add(R.id.content_fl, mVideoFragment);
                }
                type = "video";
                mCurrentFragment = mVideoFragment;
                break;
            case R.id.picture_rb:
                if (!mMusicFragment.isAdded()) {
                    transaction.add(R.id.content_fl, mMusicFragment);
                }
                type = "music_class";

                mCurrentFragment = mMusicFragment;
                break;
            case R.id.music_rb:
                if (!mPicFragment.isAdded()) {
                    transaction.add(R.id.content_fl, mPicFragment);
                }
                type = "photo_class";
                mCurrentFragment = mPicFragment;
                break;
        }

        transaction.show(mCurrentFragment);
        transaction.commitAllowingStateLoss();
        String content = mDanceViewHolder.getTextValue(R.id.search_et);

        if (!MyStrUtil.isEmpty(content)) {
            mCurrentFragment.refresh(content, type);

        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.search_btn:
                String content = mDanceViewHolder.getTextValue(R.id.search_et);
                if (!MyStrUtil.isEmpty(content)) {
                    mCurrentFragment.refresh(content, type);
                }
                break;
        }

    }

    public static void lunch(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }
}
