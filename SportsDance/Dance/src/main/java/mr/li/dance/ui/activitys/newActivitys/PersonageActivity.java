package mr.li.dance.ui.activitys.newActivitys;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.PersonLookAndFansResponse;
import mr.li.dance.https.response.PersonResponse;
import mr.li.dance.models.PersonInfo;
import mr.li.dance.models.UserInfo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.new_adapter.PersonItemAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.UserInfoManager;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:   个人主页
 * 修订历史:
 */
public class PersonageActivity extends BaseListActivity {

    private TextView shequ_look;
    private TextView shequ_fans;
    int page = 1;
    PersonItemAdapter adapter;
    private String TAG = getClass().getSimpleName();

    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("个人名片");
        mDanceViewHolder.setViewVisibility(R.id.shequ_time, View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.title, View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.content, View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.spline, View.VISIBLE);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        UserInfo userInfo = UserInfoManager.getSingleton().getUserInfo(this);
        final String userid = userInfo.getUserid();
        ImageLoaderManager.getSingleton().LoadCircle(this, userInfo.getPicture(), mDanceViewHolder.getImageView(R.id.shequ_head), R.drawable.icon_mydefault);
        mDanceViewHolder.setText(R.id.shequ_name, userInfo.getUsername());

        Request<String> person = ParameterUtils.getSingleton().getPerson(userid);
        CallServer.getRequestInstance().add(PersonageActivity.this, 0, person, new HttpListener() {
            @Override
            public void onSucceed(int what, String response) {
                mDanceViewHolder.setViewVisibility(R.id.shequ_lin, View.VISIBLE); //关注粉丝
                shequ_look = mDanceViewHolder.getTextView(R.id.shequ_look);
                shequ_fans = mDanceViewHolder.getTextView(R.id.shequ_fans);
                PersonLookAndFansResponse reponseResult = JsonMananger.getReponseResult(response, PersonLookAndFansResponse.class);
                PersonInfo data = reponseResult.getData();
                Log.e("xxx", data.toString());
                shequ_look.setText("关注 ： " + data.getUser_num());
                shequ_fans.setText("粉丝 ： " + data.getAttention_num());
                shequ_look.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "guanzhu", Toast.LENGTH_SHORT).show();
                    }
                });
                shequ_fans.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Shequ_fans.lunch(PersonageActivity.this, userid);
                    }
                });
            }

            @Override
            public void onFailed(int what, int responseCode, String response) {

            }
        }, true, true);
        Request<String> personItem = ParameterUtils.getSingleton().getPersonItem("1", String.valueOf(page), userid);
        request(AppConfigs.person_item, personItem, false);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new PersonItemAdapter(this);
        return adapter;
    }

    @Override
    public int getContentViewId() {
        return R.layout.person_activity;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e(TAG, response);
        if (what == AppConfigs.person_item) {
            PersonResponse reponseResult = JsonMananger.getReponseResult(response, PersonResponse.class);
            adapter.refresh(reponseResult);
        } else if (what == AppConfigs.person_items) {
            PersonResponse reponseResult = JsonMananger.getReponseResult(response, PersonResponse.class);
            adapter.loadMore(reponseResult);
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> personItem = ParameterUtils.getSingleton().getPersonItem("1", String.valueOf(page), userId);
        request(AppConfigs.person_item, personItem, false);

    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> personItem = ParameterUtils.getSingleton().getPersonItem("1", String.valueOf(page), userId);
        request(AppConfigs.person_items, personItem, false);
    }


    public static void lunch(Fragment context) {
        context.startActivity(new Intent(context.getContext(), PersonageActivity.class));
    }
}
