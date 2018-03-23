package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.GameDetailResponse;
import mr.li.dance.ui.activitys.album.AlbumActivity;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.activitys.match.ScoreGroupActivity;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.ShareUtils;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:
 * 修订历史:
 */
public class GameDetailActivity extends BaseActivity implements View.OnClickListener {
    private String       mMatchId;
    private View         detail_gameRank;
    private View         detail_gameGrade;
    private View         detail_gameProgram;
    private View         detail_gameNew;
    private View         detail_gameVideo;
    private View         detail_gamePic;
    private RecyclerView rv;
    private String       GameName;

    @Override
    public int getContentViewId() {
        return R.layout.activity_gamedetail;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void initViews() {
        setTitle("赛事详情");
        setRightImage(R.drawable.share_icon_001);
        //介绍
        detail_gameRank = mDanceViewHolder.getView(R.id.detail_gameRank);
        //成绩
        detail_gameGrade = mDanceViewHolder.getView(R.id.detail_gameGrade);
        //秩序册
        detail_gameProgram = mDanceViewHolder.getView(R.id.detail_gameProgram);
        detail_gameNew = mDanceViewHolder.getView(R.id.detail_gameNew);
        detail_gameVideo = mDanceViewHolder.getView(R.id.detail_gameVideo);
        detail_gamePic = mDanceViewHolder.getView(R.id.detail_gamePic);
        //新闻点击更多
        mDanceViewHolder.getView(R.id.new_more).setOnClickListener(this);
        //视频点击更多
        mDanceViewHolder.getView(R.id.video_more).setOnClickListener(this);
        //图片点击更多
        mDanceViewHolder.getView(R.id.pic_more).setOnClickListener(this);
        detail_gameRank.setOnClickListener(this);
        detail_gameGrade.setOnClickListener(this);
        detail_gameProgram.setOnClickListener(this);

        rv = (RecyclerView) mDanceViewHolder.getView(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> gameMapDetail = ParameterUtils.getSingleton().getGameMapDetail(mMatchId);
        request(AppConfigs.getGame_detail, gameMapDetail, false);

    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        GameDetailResponse reponseResult = JsonMananger.getReponseResult(response, GameDetailResponse.class);
        GameDetailResponse.DataBean data = reponseResult.getData();
        if (!MyStrUtil.isEmpty(data)) {
            mDanceViewHolder.setImageByUrlOrFilePath(R.id.video_bg, data.getImg(), R.drawable.default_banner);
            mDanceViewHolder.setText(R.id.matchname_tv, data.getTitle());
            GameName = data.getTitle();
            String competeResult = data.getCompeteResult(); //成绩
            if (!MyStrUtil.isEmpty(competeResult)) {
                detail_gameGrade.setVisibility(View.VISIBLE);
            } else {
                detail_gameGrade.setVisibility(View.GONE);
            }
            int competeZxc = data.getCompeteZxc();               //秩序册
            String s = String.valueOf(competeZxc);
            if (!MyStrUtil.isEmpty(s)) {
                detail_gameProgram.setVisibility(View.VISIBLE);
            } else {
                detail_gameProgram.setVisibility(View.GONE);
            }
            List<GameDetailResponse.DataBean.ArticleBean> article = data.getArticle();
            if (!MyStrUtil.isEmpty(article)) {
                detail_gameNew.setVisibility(View.VISIBLE);
                //这里得适配数据
                GameDetailAdapter adapter = new GameDetailAdapter(this);
                adapter.addList(article);
                rv.setAdapter(adapter);


            } else {
                detail_gameNew.setVisibility(View.GONE);
            }
            final GameDetailResponse.DataBean.CompeteVideoBean competeVideo = data.getCompeteVideo();
            if (!MyStrUtil.isEmpty(competeVideo)) {
                detail_gameVideo.setVisibility(View.VISIBLE);
                mDanceViewHolder.getImageView(R.id.tubiao).setVisibility(View.VISIBLE);
                mDanceViewHolder.setImageByUrlOrFilePath(R.id.video_imageView, competeVideo.getPicture(), R.drawable.default_banner);
                mDanceViewHolder.setText(R.id.name, competeVideo.getName());
                mDanceViewHolder.getView(R.id.iamge_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VideoDetailActivity.lunch(GameDetailActivity.this, competeVideo.getId());
                    }
                });

            } else {
                detail_gameVideo.setVisibility(View.GONE);
            }
            final GameDetailResponse.DataBean.CompetePhotoClassBean competePhotoClass = data.getCompetePhotoClass();
            if (!MyStrUtil.isEmpty(competePhotoClass)) {
                detail_gamePic.setVisibility(View.VISIBLE);
                mDanceViewHolder.setImageByUrlOrFilePath1(R.id.imageView, competePhotoClass.getPicture(), R.drawable.default_banner);
                mDanceViewHolder.setText(R.id.pic_name, competePhotoClass.getTitle());
                mDanceViewHolder.setText(R.id.num_tv, competePhotoClass.getSum());
                mDanceViewHolder.getView(R.id.pic_re).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(competePhotoClass.getTitle())) {
                            AlbumActivity.lunch(GameDetailActivity.this, competePhotoClass.getId(), competePhotoClass.getTitle());
                        } else {
                            AlbumActivity.lunch(GameDetailActivity.this, competePhotoClass.getId(), "相册详情");
                        }
                    }
                });
            } else {
                detail_gamePic.setVisibility(View.GONE);
            }
        }

    }

    public static void lunch(Context context, String matchId) {
        Intent intent = new Intent(context, GameDetailActivity.class);
        intent.putExtra("matchid", matchId);
        context.startActivity(intent);
    }

    public static void lunchs(Context context, String matchId) {
        Intent intent = new Intent(context, GameDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("matchid", matchId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.detail_gameRank:
                GameIntroduceActivity.lunch(this,mMatchId);
                break;
            case R.id.detail_gameGrade:
                ScoreGroupActivity.lunch(this,mMatchId);
                break;
            case R.id.detail_gameProgram:
                Toast.makeText(mContext, "秩序册", Toast.LENGTH_SHORT).show();
                break;
            case R.id.new_more:
                MoreNewActivity.lunch(this, mMatchId);
                break;
            case R.id.video_more:
                MoreVideoActivity.lunch(this, mMatchId);
                break;
            case R.id.pic_more:
                MoreAlbumActivity.lunch(this,mMatchId);
                break;
        }
    }


    public void onHeadRightButtonClick(View v) {
        showShareDialog();
    }

    ShareUtils mShareUtils;

    private void showShareDialog() {

        if (mShareUtils == null) {
            mShareUtils = new ShareUtils(this);
        }
        String shareUrl = String.format(AppConfigs.SHAREGAME, mMatchId);
        String mShareContent = GameName;
        String countId = AppConfigs.CLICK_EVENT_22;
        mShareUtils.showShareDilaog(countId, shareUrl, mShareContent);
    }

}
