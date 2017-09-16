package mr.li.dance.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.MusicResponse;
import mr.li.dance.models.BannerInfo;
import mr.li.dance.models.HuoDongInfo;
import mr.li.dance.models.MusicIndexPesponse;
import mr.li.dance.models.MusicRecAppBean;
import mr.li.dance.ui.activitys.LoginActivity;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.activitys.album.AlbumActivity;
import mr.li.dance.ui.activitys.match.MatchDetailActivity;
import mr.li.dance.ui.activitys.music.DanceMusicActivity;
import mr.li.dance.ui.activitys.music.SongActivity;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.ui.activitys.video.ZhiBoDetailActivity;
import mr.li.dance.ui.adapters.viewholder.BaseViewHolder;
import mr.li.dance.ui.widget.SlideShowView;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述:
 * 修订历史:
 */
public class MusicAdapter extends DanceBaseAdapter {
    private final int TYPE_1 = 1;//轮播页面
    private final int TYPE_2 = 2;//列表

    Context mContext;
    private List<BannerInfo>      mLunBoDatas;
    private List<MusicRecAppBean> mDatas;

    public MusicAdapter(Context context) {
        mContext = context;
        mLunBoDatas = new ArrayList<>();
        mDatas = new ArrayList<>();

    }

    public void refresh(MusicResponse homeResponse) {
        super.refresh();
        mLunBoDatas.clear();
        mDatas.clear();
        ArrayList<BannerInfo> banner = homeResponse.getData().getBanner();
        if (!MyStrUtil.isEmpty(banner)) {
            mLunBoDatas.addAll(banner);
        }
        ArrayList<MusicRecAppBean> music_item = homeResponse.getData().getMusicRecApp();
        if (!MyStrUtil.isEmpty(music_item)) {
            mDatas.addAll(music_item);
        }

        notifyDataSetChanged();
    }

    public void loadMore(MusicIndexPesponse indexResponse) {
        ArrayList<MusicRecAppBean> music_item = indexResponse.getData();
        if (!MyStrUtil.isEmpty(music_item)) {
            mDatas.addAll(music_item);
            Log.e("mDatas", mDatas.size() + "");
            super.loadMore();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (MyStrUtil.isEmpty(mDatas) && MyStrUtil.isEmpty(mLunBoDatas)) {
            return 0;
        } else {
            return mDatas.size()+1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case TYPE_1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type1, null);
                viewHolder = new MyViewHolder1(view);
                break;
            case TYPE_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_rec_type_2, null);
                viewHolder = new MyViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder1) {
            bindType1((MyViewHolder1) holder, position);
        } else if (holder instanceof MyViewHolder) {
            bindType2((MyViewHolder) holder, position-1);
        }
    }

    private void bindType1(MyViewHolder1 holder, int position) {
        holder.slideShowView.setOnGolistener(new SlideShowView.BannerClickListener() {
            @Override
            public void itemClick(int position) {
                Log.e("sss", UserInfoManager.getSingleton().isLoading(mContext) + "");
                BannerInfo bannerInfo = mLunBoDatas.get(position);
                switch (bannerInfo.getType()) {
                    case 10101://直播
                        ZhiBoDetailActivity.lunch(mContext, bannerInfo.getNumber());
                        break;
                    case 10102://点播
                        VideoDetailActivity.lunch(mContext, bannerInfo.getNumber());
                        break;
                    case 10103://z咨询
                        String url = String.format(AppConfigs.ZixunShareUrl, bannerInfo.getNumber());
                        MyDanceWebActivity.lunch(mContext, MyDanceWebActivity.ZIXUNTYPE, "", url, true);
                        break;
                    case 10104://图片
                        AlbumActivity.lunch(mContext, bannerInfo.getNumber(), "");
                        break;
                    case 10105://赛事
                        MatchDetailActivity.lunch(mContext, bannerInfo.getNumber());
                        break;
                    case 10106://外联

                        if (!MyStrUtil.isEmpty(bannerInfo.getUrl())) {

                            MyDanceWebActivity.lunch(mContext, MyDanceWebActivity.OTHERTYPE, "", bannerInfo.getUrl(), bannerInfo.getId());
                        }
                        break;
                    case 10107://活动
                        if (mContext != null && UserInfoManager.getSingleton().isLoading(mContext)) {
                            if (!MyStrUtil.isEmpty(bannerInfo.getUrl())) {
                                huodong(bannerInfo);
                            }
                        } else {
                            if (mContext != null) {
                                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            }
                        }
                        break;
                    case 10108:
                        SongActivity.lunch(mContext, bannerInfo.getNumber(),bannerInfo.getTitle());
                        break;

                }
            }
        });
        if (!MyStrUtil.isEmpty(mLunBoDatas)) {
            holder.slideShowView.setImageUrls(mLunBoDatas);
            holder.slideShowView.startPlay();
        }
    }

    public void huodong(final BannerInfo bannerInfo) {
        String appId = bannerInfo.getAppid();
        String appsecret = bannerInfo.getAppsecret();
        final String url = bannerInfo.getUrl();
        String userId = UserInfoManager.getSingleton().getUserId(mContext);
        Request<String> huoDongInfoMap = ParameterUtils.getSingleton().getHuoDongInfoMap(appId, appsecret, url, userId);
        CallServer.getRequestInstance().add(mContext, 0, huoDongInfoMap, new HttpListener() {
            @Override
            public void onSucceed(int what, String response) {

                HuoDongInfo reponseResult = JsonMananger.getReponseResult(response, HuoDongInfo.class);
                Log.e("sdfsdf", "请求了:" + reponseResult.getData());
                MyDanceWebActivity.lunch(mContext, MyDanceWebActivity.OTHERTYPE, "", reponseResult.getData(), url, bannerInfo.getId());
            }

            @Override
            public void onFailed(int what, int responseCode, String response) {
                Log.e("sdfsdf", "失败了" + responseCode);
            }
        }, true, true);

    }


    private void bindType2(final MyViewHolder holder, final int position) {
        final MusicRecAppBean musicRecAppBean = mDatas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanceMusicActivity.lunch(mContext,musicRecAppBean.getId(),musicRecAppBean.getName());

            }
        });

        if (!MyStrUtil.isEmpty(musicRecAppBean.getImg())) {
            holder.danceViewHolder.setRoundImageByUrlOrFilePath(R.id.item_pic, musicRecAppBean.getImg(), R.drawable.default_video);
        } else {

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }

    }


    class MyViewHolder1 extends RecyclerView.ViewHolder {
        SlideShowView slideShowView;

        public MyViewHolder1(View itemView) {
            super(itemView);
            slideShowView = (SlideShowView) itemView.findViewById(R.id.slideShowView);
        }
    }

    class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View itemView) {
            super(mContext, itemView);

        }
    }

}
