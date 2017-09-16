package mr.li.dance.ui.adapters.new_adapter;

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
import mr.li.dance.https.response.HomeIndexResponse;
import mr.li.dance.https.response.HomeResponse;
import mr.li.dance.models.BannerInfo;
import mr.li.dance.models.BaseHomeItem;
import mr.li.dance.models.HuoDongInfo;
import mr.li.dance.ui.activitys.LoginActivity;
import mr.li.dance.ui.activitys.MainActivity;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.activitys.album.AlbumActivity;
import mr.li.dance.ui.activitys.match.MatchDetailActivity;
import mr.li.dance.ui.activitys.music.SongActivity;
import mr.li.dance.ui.activitys.newActivitys.MessageActivity;
import mr.li.dance.ui.activitys.newActivitys.MusicActivity;
import mr.li.dance.ui.activitys.newActivitys.PicActivity;
import mr.li.dance.ui.activitys.newActivitys.TeachActivity;
import mr.li.dance.ui.activitys.newActivitys.VideoActivity;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.ui.activitys.video.ZhiBoDetailActivity;
import mr.li.dance.ui.adapters.DanceBaseAdapter;
import mr.li.dance.ui.adapters.viewholder.BaseViewHolder;
import mr.li.dance.ui.adapters.viewholder.HomeViewHolder;
import mr.li.dance.ui.widget.SlideShowView;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/1 0001
 * 描述:
 * 修订历史:
 */
public class New_HomeRecyclerAdapter extends DanceBaseAdapter {
    private       int mExtraCount    = 2;//除了列表额外的加载项目数
    private final int TYPE_1         = 1;//轮播页面
    private final int TYPE_2         = 2;//按钮
    private final int TYPE_SMALL_PIC = 3;//小图
    private final int TYPE_BIG_PIC   = 4;//大图
    private final int TYPE_MAIN      = 5;//正常的列表加载页面
    private List<BannerInfo> mLunBoDatas;  //轮播图集合

    private List<BaseHomeItem> mDatas;         //列表集合
    Context mContext;

    public New_HomeRecyclerAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<BaseHomeItem>();
        mLunBoDatas = new ArrayList<>();

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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_home_type2, null);
                viewHolder = new AdverViewHolder(mContext, view);
                break;
            case TYPE_SMALL_PIC:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_home_type_small, null);
                viewHolder = new HomeViewHolder(mContext, view);
                break;
            case TYPE_BIG_PIC:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albumnotitle, null);
                viewHolder = new HomeViewHolder(mContext, view);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base, null);
                viewHolder = new HomeViewHolder(mContext, view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder1) {
            bindType1((MyViewHolder1) holder, position);
        } else if (holder instanceof AdverViewHolder) {
            bindType2((AdverViewHolder) holder, position);
        } else if (holder instanceof HomeViewHolder) {
            bindType3((HomeViewHolder) holder, position - mExtraCount);
        }
    }

    private void bindType2(AdverViewHolder holder, int position) {
        //资讯
        holder.danceViewHolder.setClickListener(R.id.new_zx, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.lunch(mContext);
            }
        });
        //教学
        holder.danceViewHolder.setClickListener(R.id.new_teach, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeachActivity.lunch(mContext);
            }
        });
        //视频
        holder.danceViewHolder.setClickListener(R.id.new_video, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoActivity.lunch(mContext);
            }
        });
        //音乐
        holder.danceViewHolder.setClickListener(R.id.new_music, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicActivity.lunch(mContext);
            }
        });
        //图片
        holder.danceViewHolder.setClickListener(R.id.new_pic, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PicActivity.lunch(mContext);
            }
        });
    }

    private void bindType1(MyViewHolder1 holder, int position) {
        holder.slideShowView.setOnGolistener(new SlideShowView.BannerClickListener() {
            @Override
            public void itemClick(int position) {
                BannerInfo bannerInfo = mLunBoDatas.get(position);

                switch (bannerInfo.getType()) {
                    case 10101://直播
                        if (MainActivity.myBinder.binderIsPlaying()) {
                            MainActivity.floatImage.setVisibility(View.GONE);
                            MainActivity.myBinder.binderPause();
                        }
                        ZhiBoDetailActivity.lunch(mContext, bannerInfo.getNumber());
                        break;
                    case 10102://点播
                        if (MainActivity.myBinder.binderIsPlaying()) {
                            MainActivity.floatImage.setVisibility(View.GONE);
                            MainActivity.myBinder.binderPause();
                        }
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
                        SongActivity.lunch(mContext, bannerInfo.getNumber(), bannerInfo.getTitle());
                        break;

                }
            }
        });
        if (!MyStrUtil.isEmpty(mLunBoDatas)) {
            holder.slideShowView.setImageUrls(mLunBoDatas);
            holder.slideShowView.startPlay();
        }
    }

    private void bindType3(final HomeViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (mDatas.get(position).getType()) {
                    case 10101://直播
                        if (MainActivity.myBinder.binderIsPlaying()) {
                            MainActivity.myBinder.binderPause();
                        }
                        ZhiBoDetailActivity.lunch(mContext, mDatas.get(position).getId());
                        break;
                    case 10102://点播
                        if (MainActivity.myBinder.binderIsPlaying()) {
                            MainActivity.myBinder.binderPause();
                        }
                        VideoDetailActivity.lunch(mContext, mDatas.get(position).getId());
                        break;
                    case 10103://赛事资讯
                        String saiShiurl = String.format(AppConfigs.ZixunShareUrl, String.valueOf(mDatas.get(position).getId()));
                        MyDanceWebActivity.lunch(mContext, MyDanceWebActivity.ZIXUNTYPE, mDatas.get(position).getCompete_name(), saiShiurl, true);
                        break;
                    case 10104://赛事相册
                        AlbumActivity.lunch(mContext, mDatas.get(position).getId(), mDatas.get(position).getCompete_name());
                        break;
                    case 10105://赛事
                        if (MainActivity.myBinder.binderIsPlaying()) {
                            MainActivity.myBinder.binderPause();
                        }
                        MatchDetailActivity.lunch(mContext, mDatas.get(position).getCompete_id());
                        break;
                    case 10106://外联
                        String url = mDatas.get(position).getUrl();
                        String wailianId = mDatas.get(position).getId();
                        if (!MyStrUtil.isEmpty(url)) {
                            MyDanceWebActivity.lunch(mContext, MyDanceWebActivity.OTHERTYPE, mDatas.get(position).getTitle(), url, wailianId);
                        }
                        break;
                    case 10107:
                        BaseHomeItem baseHomeItem = mDatas.get(position);
                        Log.e("baseHomeItem", baseHomeItem.toString());
                        if (mContext != null && UserInfoManager.getSingleton().isLoading(mContext)) {
                            if (!MyStrUtil.isEmpty(baseHomeItem.getUrl())) {
                                huodong_listView(baseHomeItem);
                            }
                        } else {
                            if (mContext != null) {
                                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            }
                        }
                        break;
                    case 10108:
                        SongActivity.lunch(mContext, mDatas.get(position).getId(), mDatas.get(position).getTitle());
                        break;

                    default:
                        break;
                }
            }
        });
        holder.setViewDatas(mDatas.get(position));
    }

    /**
     * 活动轮播图参数拼接
     * @param bannerInfo
     */
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

    /**
     * 活动列表参数拼接
     * @param base
     */
    public void huodong_listView(final BaseHomeItem base) {
        String appId = base.getAppid();
       // String appId = "JK48ada5a480e37d411";
        Log.e("appId:::", appId);
        //String appsecret = "32dae2ac34079322325d28cfa0825w3aa1";
        String appsecret = base.getAppsecret();
        Log.e("appsecret::::", appsecret);
        final String url = base.getUrl();
        Log.e("url:::::", url);
        String userId = UserInfoManager.getSingleton().getUserId(mContext);
        Log.e("userId", userId);
        final String title = base.getTitle();
        Request<String> huoDongInfoMap = ParameterUtils.getSingleton().getHuoDongInfoMap(appId, appsecret, url, userId);

        CallServer.getRequestInstance().add(mContext, 0, huoDongInfoMap, new HttpListener() {
            @Override
            public void onSucceed(int what, String response) {

                HuoDongInfo reponseResult = JsonMananger.getReponseResult(response, HuoDongInfo.class);
                Log.e("sdfsdf", "请求了:" + reponseResult.getData());
                MyDanceWebActivity.lunch(mContext, MyDanceWebActivity.OTHERTYPE, title, reponseResult.getData(), url, base.getId());

            }

            @Override
            public void onFailed(int what, int responseCode, String response) {
                Log.e("sdfsdf", "失败了" + responseCode);
            }
        }, true, true);

    }


    @Override
    public int getItemCount() {
        if (MyStrUtil.isEmpty(mLunBoDatas) && MyStrUtil.isEmpty(mDatas)) {
            return 0;
        } else {
            return mDatas.size() + mExtraCount;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else if (position == 1) {
            return TYPE_2;
        } else {
            BaseHomeItem homeItem = mDatas.get(position - mExtraCount);
            if (homeItem.getShow_type().equals("1")) {
                  return TYPE_SMALL_PIC;
                } else {
                return TYPE_BIG_PIC;
            }

        }
    }

    public void loadMore(HomeIndexResponse homeResponse) {
        ArrayList indexRec = homeResponse.getData();
        if (!MyStrUtil.isEmpty(indexRec)) {
            currentPage++;
        }
        addDataItems(indexRec);
    }

    public void refresh(HomeResponse homeResponse) {
        currentPage = 1;
        mLunBoDatas.clear();
        ArrayList<BannerInfo> bannerInfos = homeResponse.getData().getBanner();
        if (!MyStrUtil.isEmpty(bannerInfos)) {
            mLunBoDatas.addAll(bannerInfos);
            Log.e("mLun", mLunBoDatas.size() + "");
        }
        mDatas.clear();
        ArrayList<BaseHomeItem> indexRec = homeResponse.getData().getIndexRec();
        addDataItems(indexRec);
    }

    public void addDataItems(ArrayList<BaseHomeItem> dataArray) {
        if (!MyStrUtil.isEmpty(dataArray)) {
            mDatas.addAll(dataArray);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        public SlideShowView slideShowView;

        public MyViewHolder1(View itemView) {
            super(itemView);
            slideShowView = (SlideShowView) itemView.findViewById(R.id.slideShowView);
        }
    }

    private class AdverViewHolder extends BaseViewHolder {
        public AdverViewHolder(Context context, View itemView) {
            super(context, itemView);

        }
    }
}
