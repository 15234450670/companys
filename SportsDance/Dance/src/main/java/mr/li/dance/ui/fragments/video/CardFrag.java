package mr.li.dance.ui.fragments.video;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.LiveCard;
import mr.li.dance.ui.activitys.game.GameDetailActivity;
import mr.li.dance.ui.activitys.video.ZhiBoDetailActivity;
import mr.li.dance.ui.adapters.LiveCardAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/5/23 0023
 * 描述:
 * 修订历史:
 */
public class CardFrag extends BaseListFragment {
    LiveCardAdapter adapter;
    private String id;
    int page = 1;
    private String mId;
    private String name;

    @Override
    public int getContentView() {
        return R.layout.live_card;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;

        }
        id = arguments.getString("id");
        mId = arguments.getString("mId");
        name = arguments.getString("name");

        Log.e("id------->", id);
        Request<String> liveCard = ParameterUtils.getSingleton().getLiveCard(id, String.valueOf(page));
        request(AppConfigs.card_live, liveCard, false);
    }

    @Override
    public void initViews() {
        super.initViews();
        if (!MyStrUtil.isEmpty(name)) {
            View view = danceViewHolder.getView(R.id.class_jieshao);
            view.setVisibility(View.VISIBLE);
            danceViewHolder.setText(R.id.matchname_tv, name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GameDetailActivity.lunch(getActivity(), mId);
                }
            });

        } else {
            danceViewHolder.getView(R.id.class_jieshao).setVisibility(View.GONE);
        }
        danceViewHolder.getTextView(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZhiBoDetailActivity.viewPager.setCurrentItem(0);
            }
        });
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> liveCard = ParameterUtils.getSingleton().getLiveCard(id, String.valueOf(page));
        request(AppConfigs.card_live, liveCard, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> liveCard = ParameterUtils.getSingleton().getLiveCard(id, String.valueOf(page));
        request(AppConfigs.card_live, liveCard, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        if (what == AppConfigs.card_live) {
            Log.e("re", response);
        }

        LiveCard reponseResult = JsonMananger.getReponseResult(response, LiveCard.class);
        List<LiveCard.DataBean.MenuBean> menu = reponseResult.getData().getMenu();
        if (!MyStrUtil.isEmpty(menu)) {
            adapter.addList(isRefresh, menu);
        }
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new LiveCardAdapter(getActivity());
        return adapter;
    }

    @Override
    public void itemClick(int position, Object value) {

    }
}
