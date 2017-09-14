package mr.li.dance.ui.fragments.newfragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import mr.li.dance.R;
import mr.li.dance.broadcast.BroadcastManager;
import mr.li.dance.models.UserInfo;
import mr.li.dance.ui.activitys.LoginActivity;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.activitys.mine.AccountActivity;
import mr.li.dance.ui.activitys.mine.MyMessageActivity;
import mr.li.dance.ui.activitys.mine.SettingActivity;
import mr.li.dance.ui.activitys.mine.SuggestActivity;
import mr.li.dance.ui.activitys.mine.UserInfoActivity;
import mr.li.dance.ui.activitys.newTab.TabCollect;
import mr.li.dance.ui.adapters.MineAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.ui.fragments.main.ExaminationFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.UserInfoManager;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/4 0004
 * 描述:      新的我的界面
 * 修订历史:
 */
public class NewMineFragment extends BaseListFragment {
    MineAdapter mMineAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        BroadcastManager.getInstance(getActivity()).addAction(AppConfigs.updateinfoAction, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshInfo();
            }
        });
    }
    @Override
    public void itemClick(int position, Object value) {
        if (!UserInfoManager.getSingleton().isLoading(getActivity())) {
            LoginActivity.lunch(this, 0x001);
            return;
        }
        switch (position) {
            //我的消息
            case 0:
               /* MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_14);
                MyCollectActivity.lunch(getActivity(), false);*/
                MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_10);
                MyMessageActivity.lunch(getActivity());
                break;
            //我的账户
            case 1:
             /*   MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_13);
                MyCollectActivity.lunch(getActivity(), true);*/
                AccountActivity.lunch(getActivity());
                break;
            //我的收藏
            case 2:
                TabCollect.lunch(getActivity());
             /*   MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_11);
                MyGuanzhuActivity.lunch(getActivity(), 0x004);*/
                break;
            //我的证书
            case 3:
                /*MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_12);
                MyAlbumActivity.lunch(getActivity(), 0x005);*/
                 ExaminationFragment.lunch(getActivity());
                break;
            //关于我们
            case 4:
                MyDanceWebActivity.lunch(getActivity(), MyDanceWebActivity.ABOUTTYPE, "关于我们");
              //  AccountActivity.lunch(getActivity());
                break;
            //意见反馈
            case 5:
                SuggestActivity.lunch(getActivity());
                break;
            //设置
            case 6:
              //  MyDanceWebActivity.lunch(getActivity(), MyDanceWebActivity.ABOUTTYPE, "关于我们");
                SettingActivity.lunch(getActivity());
                break;

        }

    }

    @Override
    public void initViews() {
        super.initViews();
        danceViewHolder.setClickListener(R.id.headicon, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserInfoManager.getSingleton().isLoading(getActivity())) {
                    UserInfoActivity.lunch(NewMineFragment.this);
                } else {
                    MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_15);
                    LoginActivity.lunch(NewMineFragment.this, 0x001);
                }

            }
        });
        refreshInfo();
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_new_mine;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mMineAdapter = new MineAdapter(getContext());
        mMineAdapter.setItemClickListener(this);
        return mMineAdapter;
    }


    private void refreshInfo() {
        if (null != getActivity() && UserInfoManager.getSingleton().isLoading(getActivity())) {
            UserInfo userInfo = UserInfoManager.getSingleton().getUserInfo(getActivity());
            danceViewHolder.setText(R.id.nick_tv, userInfo.getUsername());
            ImageLoaderManager.getSingleton().LoadCircle(getActivity(), userInfo.getPicture(), danceViewHolder.getImageView(R.id.headicon), R.drawable.icon_mydefault);
            danceViewHolder.setViewVisibility(R.id.message_icon, View.VISIBLE);
            ImageLoaderManager.getSingleton().LoadMoHu(getActivity(), userInfo.getPicture(), danceViewHolder.getImageView(R.id.background_iv), R.drawable.icon_mydefault);
        } else {
            danceViewHolder.setText(R.id.nick_tv, "未登录");
            danceViewHolder.setImageResDrawable(R.id.headicon, R.drawable.icon_mydefault, R.drawable.icon_mydefault);
            danceViewHolder.setViewVisibility(R.id.message_icon, View.INVISIBLE);
            ImageLoaderManager.getSingleton().LoadMoHu(getActivity(), "", danceViewHolder.getImageView(R.id.background_iv), R.drawable.icon_mydefault);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        refreshInfo();
    }
}
