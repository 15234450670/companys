package mr.li.dance.ui.activitys.newActivitys;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.MyFansInfo;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:     我的粉丝
 * 修订历史:
 */
public class Shequ_fans extends BaseActivity {


    private ListView list_view;
    private String   userid;

    @Override
    public int getContentViewId() {
        return R.layout.fans;
    }

    @Override
    public void initViews() {
        setTitle("粉丝");
        list_view = (ListView) mDanceViewHolder.getView(R.id.list_view);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        userid = mIntentExtras.getString("itemId");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> personFans = ParameterUtils.getSingleton().getPersonFans(userid);
        request(AppConfigs.home_album, personFans, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        MyFansInfo reponseResult = JsonMananger.getReponseResult(response, MyFansInfo.class);
        final List<MyFansInfo.DataBean> data = reponseResult.getData();
        if (MyStrUtil.isEmpty(data)) {
            mDanceViewHolder.setViewVisibility(R.id.content, View.VISIBLE);
            mDanceViewHolder.setViewVisibility(R.id.list_view, View.GONE);
        } else {
            mDanceViewHolder.setViewVisibility(R.id.content, View.GONE);
            mDanceViewHolder.setViewVisibility(R.id.list_view, View.VISIBLE);
            list_view.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return data.size() == 0 ? null : data.size();
                }

                @Override
                public Object getItem(int i) {
                    return data.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    View v = LayoutInflater.from(Shequ_fans.this).inflate(R.layout.myfans, null);
                    TextView fans_name = (TextView) v.findViewById(R.id.fans_name);
                    fans_name.setText(data.get(i).getUsername());
                    if (MyStrUtil.isEmpty(data.get(i).getUsername())) {
                        ImageLoaderManager.getSingleton().LoadCircle(Shequ_fans.this, data.get(i).getPicture_src(), mDanceViewHolder.getImageView(R.id.fans_head), R.drawable.default_icon);

                    }
                    return v;
                }
            });
        }

    }

    public static void lunch(Context context, String id) {
        Intent intent = new Intent(context, Shequ_fans.class);
        intent.putExtra("itemId", id);
        context.startActivity(intent);
    }
}
