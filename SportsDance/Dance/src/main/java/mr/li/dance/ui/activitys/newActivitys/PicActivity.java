package mr.li.dance.ui.activitys.newActivitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeAlbumInfo;
import mr.li.dance.models.LabelInfo;
import mr.li.dance.models.LabelSelect;
import mr.li.dance.ui.activitys.SearchActivity;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.ui.adapters.new_adapter.ExPandableAdapter;
import mr.li.dance.ui.fragments.newfragment.NewLabelPicFragment;
import mr.li.dance.ui.fragments.newfragment.NewPicFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.TimeOut;
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
    private TabLayout tabLayout;
    private ImageView label_pic;
    private IndexViewPager vp;
    List<Fragment> list = new ArrayList<>();
    int page = 1;
    private PopupWindow popupWindow;
    private ExpandableListView celv;
    private ExPandableAdapter exPandableAdapter;
    private List<LabelSelect.DataBean> data;

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
                if (TimeOut.isFastClick()) {
                    MobclickAgent.onEvent(PicActivity.this, AppConfigs.CLICK_EVENT_6);
                    SearchActivity.lunch(PicActivity.this);
                }

            }
        });
        //音乐按钮
        mDanceViewHolder.setClickListener(R.id.btn_music, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBinder != null && myBinder.binderIsPlaying()) {
                    PlayMusicActivity.lunch(PicActivity.this);
                } else {
                    Toast.makeText(PicActivity.this, "请去播放音乐", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Request<String> request = ParameterUtils.getSingleton().getHomeAlbum2Map(page);
        request(AppConfigs.home_album, request, false);


    }

    @Override
    public void initViews() {
        setHeadVisibility(View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.btn_back, View.VISIBLE);
        tabLayout = (TabLayout) findViewById(R.id.rv);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mDanceViewHolder.getView(R.id.frame).setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        label_pic = (ImageView) findViewById(R.id.label_pic);
        label_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimeOut.isFastClick()) {
                    Request<String> request = ParameterUtils.getSingleton().getLabelSelect("10905");
                    request(AppConfigs.home_tab_zx, request, false);
                }


            }
        });
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
        if (what == AppConfigs.home_album) {
            HomeAlbumInfo reponseResult = JsonMananger.getReponseResult(response, HomeAlbumInfo.class);
            List<LabelInfo> mLabel = reponseResult.getData().getLabel();
            if (MyStrUtil.isEmpty(mLabel)) {
                mDanceViewHolder.setViewVisibility(R.id.wu, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.you, View.GONE);
            } else {
                for (int i = 0; i < mLabel.size(); i++) {
                    NewPicFragment newZiXunFragment = new NewPicFragment();
                    Bundle bundle = new Bundle();
                    if (TextUtils.isEmpty(mLabel.get(i).getClass_id())) {
                        Log.d("getClass_id()", "mLabel= null ");
                    } else {
                        bundle.putString("path", mLabel.get(i).getClass_id());
                        newZiXunFragment.setArguments(bundle);
                    }
                    list.add(newZiXunFragment);
                }
                NewViewPagerAdapter adapter = new NewViewPagerAdapter(getSupportFragmentManager(), list, mLabel);
                vp.setAdapter(adapter);
                tabLayout.setupWithViewPager(vp);
            }
        } else {
            LabelSelect reponseResult = JsonMananger.getReponseResult(response, LabelSelect.class);
            data = reponseResult.getData();
            LabelSelect(data);
        }
    }

    public static void lunch(Context context) {
        Intent intent = new Intent(context, PicActivity.class);
        context.startActivity(intent);
    }

    //弹出标签选择
    private void LabelSelect(List<LabelSelect.DataBean> data) {
        final View popipWindow_view = getLayoutInflater().inflate(R.layout.label_select, null, false);
        //label_rv = (RecyclerView) popipWindow_view.findViewById(R.id.label_rv);
        celv = (ExpandableListView) popipWindow_view.findViewById(R.id.celv);

        TextView reset = (TextView) popipWindow_view.findViewById(R.id.reset);
        TextView sure = (TextView) popipWindow_view.findViewById(R.id.sure);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth() * 4 / 5 + 80;
        popupWindow = new PopupWindow(popipWindow_view, width,
                WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
       /* label_rv.setLayoutManager(new LinearLayoutManager(this));
        label_rv.setAdapter(new LabelAdapter(this, dataBeen));*/
        celv.setGroupIndicator(null);
        exPandableAdapter = new ExPandableAdapter(this, data);
        celv.setAdapter(exPandableAdapter);
        int count = celv.getCount();
        for (int i = 0; i < count; i++) {
            //展开
            celv.expandGroup(i);
        }


        View parent = findViewById(R.id.parent);
        popupWindow.setAnimationStyle(R.style.AnimationLeftFade);
        popupWindow.showAtLocation(parent, Gravity.RIGHT, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        PopDisappear();
        //确定
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenPop();
                popupWindow.dismiss();
                //PopDisappear();
            }
        });
        //重置
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exPandableAdapter != null) {
                    exPandableAdapter.itemReset();
                }
            }
        });
    }

    //pop消失
    private void PopDisappear() {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);

            }
        });
    }

    //标签选择
    private void ScreenPop() {
        StringBuilder sb = new StringBuilder();
        final String kk = ",";
        for (int i = 0; i < data.size(); i++) {
            List<LabelSelect.DataBean.ListBean> list = data.get(i).getList();

            for (int k = 0; k < list.size(); k++) {
                LabelSelect.DataBean.ListBean bean = list.get(k);
                if (bean.isSelect) {
                    sb.append(bean.getId());
                    sb.append(kk);
                }
            }

        }
        if (!TextUtils.isEmpty(sb.toString())) {
            sb.deleteCharAt(sb.length() - 1);
            vp.setCurrentItem(0);
            View view = mDanceViewHolder.getView(R.id.frame);
            view.setVisibility(View.VISIBLE);
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            //图片标签选中
            NewLabelPicFragment labelFragment = new NewLabelPicFragment();
            Bundle bundle = new Bundle();
            bundle.putString("path", sb.toString());
            labelFragment.setArguments(bundle);
            transaction.replace(R.id.frame, labelFragment);
            transaction.commit();
        }

    }
}
