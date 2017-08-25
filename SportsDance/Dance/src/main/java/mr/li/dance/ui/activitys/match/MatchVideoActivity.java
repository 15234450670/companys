package mr.li.dance.ui.activitys.match;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.MatchVideoResponse;
import mr.li.dance.https.response.StringResponse;
import mr.li.dance.models.Match;
import mr.li.dance.models.MatchShareInfo;
import mr.li.dance.models.Video;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.ui.adapters.MatchVideoAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.ShareUtils;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/6/5
 * 描述: 赛事视频页面
 * 修订历史:
 */
public class MatchVideoActivity extends BaseListActivity<Video> {
    MatchVideoAdapter mMatchVideoAdapter;
    private String mMatchId;

    @Override
    public int getContentViewId() {
        return R.layout.list_layout;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> request = ParameterUtils.getSingleton().getMatchVedioMap(mMatchId);
        request(AppConfigs.match_matchVedio, request, true);
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("赛事视频");
        setRightImage(R.drawable.share_icon_001);
    }

    @Override
    public void itemClick(int position, Video value) {
        if (null != value) {
            VideoDetailActivity.lunch(this, value.getAlbum_id());
        }
    }

    private void getData() {
        Request<String> request = ParameterUtils.getSingleton().getMatchVedioMap(mMatchId);
        request(AppConfigs.match_matchVedio, request, true);
    }

    @Override
    public void refresh() {
        super.refresh();
        getData();
    }

    @Override
    public void loadMore() {
        super.loadMore();
        Request<String> request = ParameterUtils.getSingleton().getMatchVedioListMap(mMatchId, mMatchVideoAdapter.getNextPage());

        request(AppConfigs.match_matchVedioList, request, false);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mMatchVideoAdapter = new MatchVideoAdapter(this);
        mMatchVideoAdapter.setItemClickListener(this);
        return mMatchVideoAdapter;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        StringResponse stringResponse = JsonMananger.getReponseResult(response, StringResponse.class);
        String dataStr = stringResponse.getData();
        Log.e("xxx",dataStr);
        if (what == AppConfigs.match_matchVedio) {
            MatchVideoResponse videoResponse = JsonMananger.getReponseResult(dataStr, MatchVideoResponse.class);
            Match match = new Match();
            match.setType(videoResponse.getType());
            match.setTitle(videoResponse.getTitle());
            match.setAddress(videoResponse.getAddress());
            mMatchVideoAdapter.setmMatchInfo(match);
            mMatchVideoAdapter.addList(isRefresh, videoResponse.getAlbum());
        } else if (what==AppConfigs.match_share_cj) {
            MatchShareInfo reponseResult = JsonMananger.getReponseResult(response, MatchShareInfo.class);
            String share = reponseResult.getData();
          //  showShareDialog(share);
        }else {
            MatchVideoResponse videoResponse = JsonMananger.getReponseResult(dataStr, MatchVideoResponse.class);
            mMatchVideoAdapter.addList(isRefresh,  videoResponse.getAlbum());

        }

    }


    public static void lunch(Context context, String mMatchId) {
        Intent intent = new Intent(context, MatchVideoActivity.class);
        intent.putExtra("matchid", mMatchId);
        context.startActivity(intent);
    }
    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
      //  ShareVideo();
        showShareDialog();
    }

    /**
     * 分享
     */
    public void ShareVideo(){
        Request<String> stringRequest = ParameterUtils.getSingleton().getMatchShareVedioMap(mMatchId);
        request(AppConfigs.match_share_cj, stringRequest, false);
    }
    ShareUtils mShareUtils;

    private void showShareDialog() {
        MobclickAgent.onEvent(this, AppConfigs.CLICK_EVENT_29);
        if (mShareUtils == null) {
            mShareUtils = new ShareUtils(this);
        }

        mShareUtils.showShareDilaog(AppConfigs.CLICK_EVENT_29, "http://work.cdsf.org.cn/h5/share.spfx?id="+mMatchId, "赛事视频");
    }
}
