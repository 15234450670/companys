package mr.li.dance.ui.activitys.newActivitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lecloud.sdk.constant.PlayerEvent;
import com.lecloud.sdk.constant.PlayerParams;
import com.lecloud.sdk.constant.StatusCode;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.lecloud.sdk.videoview.VideoViewListener;
import com.lecloud.skin.videoview.vod.UIVodVideoView;
import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.rest.Request;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.BaseItemAdapterType;
import mr.li.dance.models.TeachDetailInfo;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.BaseItemAdapter;
import mr.li.dance.ui.widget.VideoLayoutParams;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.ShareUtils;


/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/20 0020
 * 描述:       教学详情
 * 修订历史:
 */
public class TeachDetailsActivity extends BaseListActivity {
    BaseItemAdapter mAdapter;
    int page = 1;
    String mId;
    String mPic;
    String mTitle;
    private IMediaDataVideoView videoView;
    private TextView            querydetail_tv;   //开始播放
    private LinearLayout        class_jieshao;
    private LinearLayout        class_section;
    private String              content;
    LinkedHashMap<String, String> rateMap            = new LinkedHashMap<String, String>();
    VideoViewListener             mVideoViewListener = new VideoViewListener() {

        @Override
        public void onStateResult(int event, Bundle bundle) {
            handleVideoInfoEvent(event, bundle);// 处理视频信息事件
            handlePlayerEvent(event, bundle);// 处理播放器事件
        }
        @Override
        public String onGetVideoRateList(LinkedHashMap<String, String> map) {
            rateMap = map;
            for (Map.Entry<String, String> rates : map.entrySet()) {
                if (rates.getValue().equals("高清")) {
                    return rates.getKey();
                }
            }
            return "";
        }
    };
    private List<TeachDetailInfo.DataBean.OtherListBean> otherList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_teachdetails;
    }


    @Override
    public void getIntentData() {
        super.getIntentData();
        mId = mIntentExtras.getString("id");
        mPic = mIntentExtras.getString("pic");
        mTitle = mIntentExtras.getString("title");
        content = mIntentExtras.getString("content");
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("教学详情");
        setRightImage(R.drawable.share_icon_001);
        mDanceViewHolder.setImageByUrlOrFilePath(R.id.pic, mPic, R.drawable.default_video);   //图片
        mDanceViewHolder.setText(R.id.teach_title, mTitle);   //标题
        class_jieshao = (LinearLayout) mDanceViewHolder.getView(R.id.class_jieshao);   //课程介绍
        class_section = (LinearLayout) mDanceViewHolder.getView(R.id.class_section);  //课程章节
        querydetail_tv = mDanceViewHolder.getTextView(R.id.querydetail_tv);
        videoView = new UIVodVideoView(this);
        ((UIVodVideoView) videoView).setVideoAutoPlay(true);
        videoView.setVideoViewListener(mVideoViewListener);
        final RelativeLayout videoContainer = (RelativeLayout) findViewById(R.id.videoContainer);
        videoContainer.addView((View) videoView, VideoLayoutParams.computeContainerSize(this, 16, 9));

    }
    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> request = ParameterUtils.getSingleton().getHomeTeachDetailsMap(mId, String.valueOf(page));
        request(AppConfigs.home_tab_teach_details, request, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.onDestroy();
            videoView.setVideoViewListener(null);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            setHeadVisibility(View.GONE);
            mDanceViewHolder.getImageView(R.id.pic).setVisibility(View.GONE);
        } else {
            setHeadVisibility(View.VISIBLE);
            /*if (videoView.isPlaying()) {
                mDanceViewHolder.getImageView(R.id.pic).setVisibility(View.GONE);
            } else {
                mDanceViewHolder.getImageView(R.id.pic).setVisibility(View.VISIBLE);
            }*/

        }
        if (videoView != null) {
            videoView.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new BaseItemAdapter(mContext, BaseItemAdapterType.TEACHER);
        mAdapter.setItemClickListener(this);
        return mAdapter;
    }

    public static void lunch(Fragment fragment, String id, String pic, String title, String content) {
        Intent intent = new Intent(fragment.getActivity(), TeachDetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("pic", pic);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        fragment.startActivity(intent);
    }

    public static void lunch(Context context, String id, String pic, String title) {
        Intent intent = new Intent(context, TeachDetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("pic", pic);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }


    @Override
    public void onSucceed(int what, String responseStr) {
        super.onSucceed(what, responseStr);
        Log.e("xxxxxx", responseStr);
        if (what == AppConfigs.home_tab_teach_details) {
            final TeachDetailInfo reponseResult = JsonMananger.getReponseResult(responseStr, TeachDetailInfo.class);
            if (reponseResult == null) {
                return;
            }
            if (reponseResult.getData() == null) {
                return;
            }
            List<TeachDetailInfo.DataBean.DetailBean> detail = reponseResult.getData().getDetail();
            if (!MyStrUtil.isEmpty(detail)) {
                String described = detail.get(0).getDescribed();
                if (!TextUtils.isEmpty(described)) {
                    class_jieshao.setVisibility(View.VISIBLE);
                    mDanceViewHolder.setText(R.id.jieshao, described);
                    class_jieshao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyDanceWebActivity.lunch(TeachDetailsActivity.this, MyDanceWebActivity.TEACHERCLASS, mTitle, AppConfigs.TEACHERCLASS + mId, true);
                        }
                    });
                }
            }
            otherList = reponseResult.getData().getOtherList();
            if (!MyStrUtil.isEmpty(otherList)) {

                class_section.setVisibility(View.VISIBLE);
                mAdapter.refresh(reponseResult.getData().getOtherList());
                //点击开始播放
                querydetail_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<TeachDetailInfo.DataBean.OtherListBean> otherList = reponseResult.getData().getOtherList();
                        setTeachDetail(otherList.get(0).getVideo_unique());
                        otherList.get(0).isClick = true;

                        mAdapter.notifyDataSetChanged();
                    }
                });

            }

        }
    }

    private void setTeachDetail(String path) {
        mDanceViewHolder.getImageView(R.id.pic).setVisibility(View.GONE);
        mDanceViewHolder.getView(R.id.stop_layout).setVisibility(View.GONE);
        videoView.resetPlayer();
        Bundle mBundle = new Bundle();
        mBundle.putString(PlayerParams.KEY_PLAY_UUID, AppConfigs.KEY_PLAY_UUID);
        mBundle.putString(PlayerParams.KEY_PLAY_VUID, path);
        mBundle.putString(PlayerParams.KEY_PLAY_PU, AppConfigs.KEY_PLAY_PU);
        videoView.setPanorama(true);
        videoView.setDataSource(mBundle);

    }

    /**
     * 处理视频信息类事件
     */
    private void handleVideoInfoEvent(int state, Bundle bundle) {
    }

    /**
     * 处理播放器本身事件，具体事件可以参见IPlayer类
     */
    private void handlePlayerEvent(int state, Bundle bundle) {
        switch (state) {
            case PlayerEvent.PLAY_VIDEOSIZE_CHANGED:
                /**
                 * 获取到视频的宽高的时候，此时可以通过视频的宽高计算出比例，进而设置视频view的显示大小。
                 * 如果不按照视频的比例进行显示的话，(以surfaceView为例子)内容会填充整个surfaceView。
                 * 意味着你的surfaceView显示的内容有可能是拉伸的
                 */
                break;

            case PlayerEvent.PLAY_PREPARED:
                // 播放器准备完成，此刻调用start()就可以进行播放了
                if (videoView != null) {
                    videoView.onStart();
                }
                break;
            case PlayerEvent.PLAY_INFO:
                int code = bundle.getInt(PlayerParams.KEY_RESULT_STATUS_CODE);
                if (code == StatusCode.PLAY_INFO_VIDEO_RENDERING_START) {
                    //播放第一帧
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void itemClick(int position, Object value) {
        TeachDetailInfo.DataBean.OtherListBean value1 = (TeachDetailInfo.DataBean.OtherListBean) value;
        String path = value1.getVideo_unique();
        setTeachDetail(path);
        for (int i = 0; i < otherList.size(); i++) {
            if (i==position){
                otherList.get(i).isClick = true;

            }else {
                otherList.get(i).isClick = false;

            }
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void refresh() {
        super.refresh();
        Request<String> request = ParameterUtils.getSingleton().getHomeTeachDetailsMap(mId, String.valueOf(page));
        request(AppConfigs.home_tab_teach_details, request, true);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> request = ParameterUtils.getSingleton().getHomeTeachDetailsMap(mId, String.valueOf(page));
        request(AppConfigs.home_tab_teach_details, request, true);
    }

    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        showShareDialog();
    }
    ShareUtils mShareUtils;
    private void showShareDialog() {
        MobclickAgent.onEvent(this, AppConfigs.CLICK_EVENT_29);
        if (mShareUtils == null) {
            mShareUtils = new ShareUtils(this);
        }
        mShareUtils.showShareDilaog(AppConfigs.CLICK_EVENT_29, AppConfigs.tach_detial + mId, mTitle);
    }
}
