package mr.li.dance.ui.activitys.newActivitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeAlbumResponse;
import mr.li.dance.models.LabelInfo;
import mr.li.dance.ui.activitys.SearchActivity;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.ui.fragments.newfragment.NewPicFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.util.IndexViewPager;

import static mr.li.dance.ui.activitys.MainActivity.myBinder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/8 0008
 * 描述:
 * 修订历史:
 */
public class PicActivity extends BaseActivity {
    private TabLayout      tabLayout;
    private ImageView      label_pic;
    private IndexViewPager vp;
    List<Fragment> list = new ArrayList<>();
    @Override
    public int getContentViewId() {
        return R.layout.new_type2_activity;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        //搜索按钮
        mDanceViewHolder.setClickListener(R.id.search_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(PicActivity.this, AppConfigs.CLICK_EVENT_6);
                SearchActivity.lunch(PicActivity.this);
            }
        });
        //音乐按钮
        mDanceViewHolder.setClickListener(R.id.btn_music, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBinder!=null&&myBinder.binderIsPlaying()) {
                    PlayMusicActivity.lunch(PicActivity.this);
                } else {
                    Toast.makeText(PicActivity.this, "请去播放音乐", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Request<String> request = ParameterUtils.getSingleton().getHomeAlbumMap(1);
        request(AppConfigs.home_album, request, false);


    }

    @Override
    public void initViews() {
        setHeadVisibility(View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.btn_back, View.VISIBLE);
        tabLayout = (TabLayout) findViewById(R.id.rv);
        label_pic = (ImageView) findViewById(R.id.label_pic);
        vp = (IndexViewPager) findViewById(R.id.fl);
        finishs();
    }
    public void finishs() {
        mDanceViewHolder.setClickListener(R.id.btn_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        if (what==AppConfigs.home_album) {
            HomeAlbumResponse reponseResult = JsonMananger.getReponseResult(response, HomeAlbumResponse.class);
            ArrayList<LabelInfo> mLabel = reponseResult.getLabel();
            if (MyStrUtil.isEmpty(mLabel)) {
                mDanceViewHolder.setViewVisibility(R.id.wu, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.you, View.GONE);
            } else {
                for (int i = 0; i < mLabel.size(); i++) {
                    NewPicFragment newPicFragment = new NewPicFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("path", mLabel.get(i).getClass_id());
                    newPicFragment.setArguments(bundle);
                    list.add(newPicFragment);
                }
                NewViewPagerAdapter adapter = new NewViewPagerAdapter(getSupportFragmentManager(), list,mLabel);
                vp.setAdapter(adapter);
                tabLayout.setupWithViewPager(vp);
            }
        }
    }

    public static void lunch(Context context) {
        Intent intent = new Intent(context, PicActivity.class);
        context.startActivity(intent);
    }
}
