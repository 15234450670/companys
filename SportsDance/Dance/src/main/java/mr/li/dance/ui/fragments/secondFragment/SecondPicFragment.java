package mr.li.dance.ui.fragments.secondFragment;

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

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeAlbumInfo;
import mr.li.dance.models.LabelInfo;
import mr.li.dance.models.LabelSelect;
import mr.li.dance.ui.activitys.newActivitys.NewViewPagerAdapter;
import mr.li.dance.ui.adapters.new_adapter.ExPandableAdapter;
import mr.li.dance.ui.fragments.BaseFragment;
import mr.li.dance.ui.fragments.newfragment.NewLabelPicFragment;
import mr.li.dance.ui.fragments.newfragment.NewPicFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.TimeOut;
import mr.li.dance.utils.util.IndexViewPager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/4/25 0025
 * 描述:
 * 修订历史:
 */
public class SecondPicFragment extends BaseFragment {
    private TabLayout      tabLayout;
    private ImageView      label_pic;
    private IndexViewPager vp;
    List<Fragment> list = new ArrayList<>();
    int            page = 1;
    private PopupWindow                popupWindow;
    private ExpandableListView         celv;
    private ExPandableAdapter          exPandableAdapter;
    private List<LabelSelect.DataBean> data;
    private NewViewPagerAdapter adapter;

    @Override
    public void initData() {
        Request<String> request = ParameterUtils.getSingleton().getHomeAlbum2Map(page);
        request(AppConfigs.home_album, request, false);
    }

    @Override
    public void initViews() {
        tabLayout = (TabLayout) danceViewHolder.getView(R.id.rv);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                danceViewHolder.getView(R.id.frame).setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        label_pic = (ImageView) danceViewHolder.getView(R.id.label_pic);
        label_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimeOut.isFastClick()) {
                    Request<String> request = ParameterUtils.getSingleton().getLabelSelect("10905");
                    request(AppConfigs.home_tab_zx, request, false);
                }


            }
        });
        vp = (IndexViewPager) danceViewHolder.getView(R.id.fl);
    }

    @Override
    public int getContentView() {
        return R.layout.second_message_fragment;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        if (what == AppConfigs.home_album) {
            HomeAlbumInfo reponseResult = JsonMananger.getReponseResult(response, HomeAlbumInfo.class);
            List<LabelInfo> mLabel = reponseResult.getData().getLabel();
            if (MyStrUtil.isEmpty(mLabel)) {
                danceViewHolder.setViewVisibility(R.id.wu, View.VISIBLE);
                danceViewHolder.setViewVisibility(R.id.you, View.GONE);
            } else {
                if (adapter == null) {
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
                    adapter = new NewViewPagerAdapter(getChildFragmentManager(), list, mLabel);
                }

                vp.setAdapter(adapter);
                tabLayout.setupWithViewPager(vp);
            }
        } else {
            LabelSelect reponseResult = JsonMananger.getReponseResult(response, LabelSelect.class);
            data = reponseResult.getData();
            LabelSelect(data);
        }
    }

    //弹出标签选择
    private void LabelSelect(List<LabelSelect.DataBean> data) {
        final View popipWindow_view = getActivity().getLayoutInflater().inflate(R.layout.label_select, null, false);
        //label_rv = (RecyclerView) popipWindow_view.findViewById(R.id.label_rv);
        celv = (ExpandableListView) popipWindow_view.findViewById(R.id.celv);

        TextView reset = (TextView) popipWindow_view.findViewById(R.id.reset);
        TextView sure = (TextView) popipWindow_view.findViewById(R.id.sure);
        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth() * 4 / 5 + 80;
        popupWindow = new PopupWindow(popipWindow_view, width,
                WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
       /* label_rv.setLayoutManager(new LinearLayoutManager(this));
        label_rv.setAdapter(new LabelAdapter(this, dataBeen));*/
        celv.setGroupIndicator(null);
        exPandableAdapter = new ExPandableAdapter(getActivity(), data);
        celv.setAdapter(exPandableAdapter);
        int count = celv.getCount();
        for (int i = 0; i < count; i++) {
            //展开
            celv.expandGroup(i);
        }


        View parent = danceViewHolder.getView(R.id.parent);
        popupWindow.setAnimationStyle(R.style.AnimationLeftFade);
        popupWindow.showAtLocation(parent, Gravity.RIGHT, 0, 0);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.3f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
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
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);

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
            View view = danceViewHolder.getView(R.id.frame);
            view.setVisibility(View.VISIBLE);
            FragmentManager supportFragmentManager = getChildFragmentManager();
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
