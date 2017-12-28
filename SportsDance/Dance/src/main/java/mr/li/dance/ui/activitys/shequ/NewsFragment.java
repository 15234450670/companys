package mr.li.dance.ui.activitys.shequ;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.ShequResponse;
import mr.li.dance.models.ShequInfo;
import mr.li.dance.ui.adapters.new_adapter.SheQuAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/23 0023
 * 描述:
 * 修订历史:
 */
public class NewsFragment extends BaseListFragment {
    int page = 1;

    private String TAG = getClass().getSimpleName();
    private SheQuAdapter adapter;

    @Override
    public void itemClick(int position, Object value) {
        ShequInfo shequInfo = (ShequInfo) value;
        String type = shequInfo.getType();
        if (type.equals("1")) {
            //图片
            SheQuPicDetails.lunch(getActivity(), shequInfo.getId(), shequInfo.getUid());
        } else {
            //视频
            SheQuVideoDetails.lunch(getActivity(), shequInfo.getId(), shequInfo.getUid(),shequInfo.getVideo().picture);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void initData() {
        Log.e("kan","=============================================");
        String userid = UserInfoManager.getSingleton().getUserId(getActivity());
        if (!MyStrUtil.isEmpty(userid)) {
            Request<String> request = ParameterUtils.getSingleton().getNewsFragment("1", String.valueOf(page), userid);
            request(AppConfigs.shequ_news_fragment, request, false);
        }

    }

    @Override
    public int getContentView() {
        return R.layout.shequ_news_fragment;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new SheQuAdapter(getActivity(), this);
        return adapter;
    }
    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.d(TAG, response);
        ShequResponse reponseResult = JsonMananger.getReponseResult(response, ShequResponse.class);
        if (what == AppConfigs.shequ_news_fragment) {
            if (!MyStrUtil.isEmpty(reponseResult.getData())) {
                adapter.refresh(reponseResult);
            }
        } else {
           adapter.loadMore(reponseResult);
        }

    }

    @Override
    public void refresh() {
        super.refresh();
        String userId = UserInfoManager.getSingleton().getUserId(getActivity());
        Log.e(TAG + "++", userId);
        page = 1;
        Request<String> request = ParameterUtils.getSingleton().getNewsFragment("1", String.valueOf(page), userId);
        request(AppConfigs.shequ_news_fragment, request, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        String userId = UserInfoManager.getSingleton().getUserId(getActivity());
        Log.e(TAG + "--", userId);
        page++;
        Request<String> request = ParameterUtils.getSingleton().getNewsFragment("1", String.valueOf(page), userId);
        Log.e("page", page + "");
        request(AppConfigs.shequ_news_fragments, request, false);
    }
}
