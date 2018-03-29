package mr.li.dance.ui.activitys.game;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.GameTabResponse;
import mr.li.dance.ui.fragments.BaseFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:        赛事信息
 * 修订历史:
 */
public class TabMessage extends BaseFragment {
    private String TAG = getClass().getSimpleName();

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        String id = arguments.getString("id");
        Log.e(TAG, id);
        Request<String> gameMapIntroduce = ParameterUtils.getSingleton().getGameMapIntroduce(id);
        request(AppConfigs.Bound_state, gameMapIntroduce, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e(TAG, response);
        GameTabResponse reponseResult = JsonMananger.getReponseResult(response, GameTabResponse.class);
        final GameTabResponse.DataBean data = reponseResult.getData();
        String start_time = data.getStart_time().replace("-", ".");
        String end_time = data.getEnd_time().substring(5).replace("-", ".");
        danceViewHolder.setText(R.id.creat_tv, start_time + "-" + end_time); //比赛时间
        String start_sign_up = data.getStart_sign_up().replace("-", ".");
        String end_sign_up = data.getEnd_sign_up().substring(5).replace("-", ".");
        danceViewHolder.setText(R.id.end_tv, start_sign_up + "-" + end_sign_up); //报名时间
        final String address = data.getAddress();
        danceViewHolder.setText(R.id.map_address, address); //地址
        //跳地图
        danceViewHolder.getView(R.id.map_onclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MapActivity.lunch(getActivity(), address, data.getLongitude(), data.getLatitudes());
            }
        });
        String type = data.getType(); //级别
        switch (type) {
            case "10001":
                danceViewHolder.setText(R.id.wdsf_tv, "WDSF");
                break;
            case "10002":
                danceViewHolder.setText(R.id.wdsf_tv, "CDSF");
                break;
            case "10003":
                danceViewHolder.setText(R.id.wdsf_tv, "地方赛事");
                break;
        }
        int live = data.getLive();  //是否有直播
        if (live != 0) {
            danceViewHolder.getView(R.id.video_tv).setVisibility(View.VISIBLE);
        } else {
            danceViewHolder.getView(R.id.video_tv).setVisibility(View.GONE);
        }
        RecyclerView rv = (RecyclerView) danceViewHolder.getView(R.id.rv_z);
        RecyclerView undertake_rv = (RecyclerView) danceViewHolder.getView(R.id.undertake_rv);
        RecyclerView assist_rv = (RecyclerView) danceViewHolder.getView(R.id.assist_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        undertake_rv.setLayoutManager(layoutManager1);
        assist_rv.setLayoutManager(layoutManager2);
        List<String> zhuban = data.getZhuban();
        if (!MyStrUtil.isEmpty(zhuban)) {
            danceViewHolder.getView(R.id.sponsor).setVisibility(View.VISIBLE);
            SponsorAdapter adapter = new SponsorAdapter(getActivity());
            adapter.addList(zhuban);
            rv.setAdapter(adapter);
        } else {
            danceViewHolder.getView(R.id.sponsor).setVisibility(View.GONE);
        }

        List<String> chengban = data.getChengban();
        if (!MyStrUtil.isEmpty(chengban)) {
            UndertakeAdapter adapter = new UndertakeAdapter(getActivity());
            danceViewHolder.getView(R.id.undertake).setVisibility(View.VISIBLE);
            adapter.addList(chengban);
            undertake_rv.setAdapter(adapter);
        } else {
            danceViewHolder.getView(R.id.undertake).setVisibility(View.GONE);
        }
        List<String> xieban = data.getXieban();
        if (!MyStrUtil.isEmpty(xieban)) {
            AssistAdapter assistAdapter = new AssistAdapter(getActivity());
            danceViewHolder.getView(R.id.assist).setVisibility(View.VISIBLE);
            assistAdapter.addList(xieban);
            assist_rv.setAdapter(assistAdapter);
        } else {
            danceViewHolder.getView(R.id.assist).setVisibility(View.GONE);
        }

    }

    @Override
    public void initViews() {

    }

    @Override
    public int getContentView() {
        return R.layout.tab_message;
    }
}
