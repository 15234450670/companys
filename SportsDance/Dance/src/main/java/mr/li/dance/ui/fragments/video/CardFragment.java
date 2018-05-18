package mr.li.dance.ui.fragments.video;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.LiveCard;
import mr.li.dance.ui.adapters.LiveCardAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/5/17 0017
 * 描述:    赛程表
 * 修订历史:
 */
public class CardFragment extends BaseListFragment {
    LiveCardAdapter adapter;
    private String id;
    int page = 1;


    @Override
    public void initData() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;

        }
        id = arguments.getString("id");
        Log.e("id------->", id);
        Request<String> liveCard = ParameterUtils.getSingleton().getLiveCard(id, String.valueOf(page));
        request(AppConfigs.card_live, liveCard, false);

    }

    @Override
    public void initViews() {

    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> liveCard = ParameterUtils.getSingleton().getLiveCard(id, String.valueOf(page));
        request(AppConfigs.card_live, liveCard, false);
        Toast.makeText(getActivity(), ""+page, Toast.LENGTH_SHORT).show();
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
            //   adapter.addList(isRefresh,menu);
        }
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new LiveCardAdapter(getActivity());
        return adapter;
    }

    @Override
    public int getContentView() {
        return R.layout.live_card;
    }


    @Override
    public void itemClick(int position, Object value) {

    }
}
