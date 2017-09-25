package mr.li.dance.ui.activitys.newActivitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import mr.li.dance.https.response.MusicResponse;
import mr.li.dance.models.LabelInfo;
import mr.li.dance.models.LabelSelect;
import mr.li.dance.ui.activitys.SearchActivity;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.ui.adapters.new_adapter.ExPandableAdapter;
import mr.li.dance.ui.fragments.newfragment.NewMusicFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.util.CustomExpandableListView;
import mr.li.dance.utils.util.IndexViewPager;

import static mr.li.dance.ui.activitys.MainActivity.myBinder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/8 0008
 * 描述:
 * 修订历史:
 */
public class MusicActivity extends BaseActivity {
    private TabLayout      tabLayout;
    private ImageView      label_pic;
    private IndexViewPager vp;
    List<Fragment> list = new ArrayList<>();
    public static int tabPosition = -1;
    private PopupWindow popupWindow;
    ExPandableAdapter adapter;
    private CustomExpandableListView   celv;
    private ExPandableAdapter          exPandableAdapter;
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
                MobclickAgent.onEvent(MusicActivity.this, AppConfigs.CLICK_EVENT_6);
                SearchActivity.lunch(MusicActivity.this);
            }
        });
        //音乐按钮
        mDanceViewHolder.setClickListener(R.id.btn_music, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBinder!=null&&myBinder.binderIsPlaying()) {
                    PlayMusicActivity.lunch(MusicActivity.this);
                } else {
                    Toast.makeText(MusicActivity.this, "请去播放音乐", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Request<String> musicInfoMap = ParameterUtils.getSingleton().getMusicInfo2Map();
        request(AppConfigs.home_music, musicInfoMap, false);

    }
    //弹出标签选择
    private void LabelSelect(List<LabelSelect.DataBean> data) {
        final View popipWindow_view = getLayoutInflater().inflate(R.layout.label_select, null, false);
        //label_rv = (RecyclerView) popipWindow_view.findViewById(R.id.label_rv);
        celv = (CustomExpandableListView) popipWindow_view.findViewById(R.id.celv);

        TextView reset = (TextView) popipWindow_view.findViewById(R.id.reset);
        TextView sure = (TextView) popipWindow_view.findViewById(R.id.sure);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth() * 4 / 5;
        popupWindow = new PopupWindow(popipWindow_view, width,
                WindowManager.LayoutParams.MATCH_PARENT);
        PopDisappear();
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
                popupWindow.dismiss();
                //PopDisappear();
            }
        });
        //重置
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exPandableAdapter!=null) {
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

                if (ExPandableAdapter.isChildCanSelect) {
                    // TODO: 2017/9/24
                    StringBuilder sb = new StringBuilder();
                    final String kk = "--";
                    for (int i = 1 ; i < data.size() ; i++) {

                        List<LabelSelect.DataBean.ListBean> list = data.get(i).getList();

                        for (int k = 0 ; k < list.size() ; k++) {
                            LabelSelect.DataBean.ListBean bean = list.get(k);
                            if (bean.isSelect) {
                                sb.append(bean.getId());
                                sb.append(kk);
                            }
                        }

                    }

                    Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    int tab = exPandableAdapter.getTabPosition();
                    if(tab<0){
                        // TODO: 2017/9/24
                        Toast.makeText(mContext, "未选中标签！", Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        int position = tabLayout.getSelectedTabPosition();
                        if (position == tab) {
                            return;
                        } else {
                            vp.setCurrentItem(tab+1);
                            //tabLayout.getTabAt(tab+1);
                        }

                    }
                }

            }
        });
    }
    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        if (what == AppConfigs.home_music) {
            MusicResponse reponseResult = JsonMananger.getReponseResult(response, MusicResponse.class);
            ArrayList<LabelInfo> mLabel= reponseResult.getData().getLabel();
            if (MyStrUtil.isEmpty(mLabel)) {
                mDanceViewHolder.setViewVisibility(R.id.wu, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.you, View.GONE);
            } else {
                for (int i = 0; i < mLabel.size(); i++) {
                    NewMusicFragment newZiXunFragment = new NewMusicFragment();
                    Bundle bundle = new Bundle();
                    if (TextUtils.isEmpty(mLabel.get(i).getClass_id())) {
                        Log.d("getClass_id()", "mLabel= null ");
                    } else {
                        bundle.putString("path", mLabel.get(i).getClass_id());
                        newZiXunFragment.setArguments(bundle);
                    }
                    list.add(newZiXunFragment);
                }
                NewViewPagerAdapter adapter = new NewViewPagerAdapter(getSupportFragmentManager(), list,mLabel);
                vp.setAdapter(adapter);
                tabLayout.setupWithViewPager(vp);
            }
        }else {
            LabelSelect reponseResult = JsonMananger.getReponseResult(response, LabelSelect.class);
            data = reponseResult.getData();
            LabelSelect(data);
        }
    }

    @Override
    public void initViews() {
        setHeadVisibility(View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.btn_back, View.VISIBLE);
        tabLayout = (TabLayout) findViewById(R.id.rv);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition() - 1;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        label_pic = (ImageView) findViewById(R.id.label_pic);
        vp = (IndexViewPager) findViewById(R.id.fl);
        finishs();
        label_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request<String> request = ParameterUtils.getSingleton().getLabelSelect("10904");
                request(AppConfigs.home_tab_zx, request, false);

            }
        });
    }

    /**
     * 结束界面
     */
    public void finishs() {
        mDanceViewHolder.setClickListener(R.id.btn_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void lunch(Context context) {
        Intent intent = new Intent(context, MusicActivity.class);
        context.startActivity(intent);
    }
}
