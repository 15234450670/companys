package mr.li.dance.ui.activitys.newActivitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.SpecialResponse;
import mr.li.dance.models.SpecialInfo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.ui.adapters.new_adapter.SpecialAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/28 0028
 * 描述:    视频相关专辑页
 * 修订历史:
 */
public class SpecialActivity extends BaseListActivity{
    SpecialAdapter adapter;
    String         mMatchId;
    int page = 1;
    @Override
    public void itemClick(int position, Object value) {
        SpecialInfo info = (SpecialInfo) value;
        String id = info.getId();
        VideoDetailActivity.lunch(this,id);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new SpecialAdapter(this);
        adapter.setItemClickListener(this);
        return adapter;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_layout;
    }

    public static void lunch(Context context, String mMatchId) {
        Intent intent = new Intent(context, SpecialActivity.class);
        intent.putExtra("matchid", mMatchId);
        context.startActivity(intent);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> request = ParameterUtils.getSingleton().getVideoSpeial(mMatchId,String.valueOf(page));
        request(AppConfigs.video_special, request, false);
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("相关专辑");
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        SpecialResponse reponseResult = JsonMananger.getReponseResult(response, SpecialResponse.class);
        ArrayList<SpecialInfo> data = reponseResult.getData();
          adapter.addList(isRefresh,data);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> request = ParameterUtils.getSingleton().getVideoSpeial(mMatchId,String.valueOf(page));
        request(AppConfigs.video_special, request, false);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> request = ParameterUtils.getSingleton().getVideoSpeial(mMatchId,String.valueOf(page));
        request(AppConfigs.video_special, request, false);
    }
}
