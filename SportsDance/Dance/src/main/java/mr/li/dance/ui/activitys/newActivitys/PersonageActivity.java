package mr.li.dance.ui.activitys.newActivitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.PersonLookAndFansResponse;
import mr.li.dance.https.response.PersonResponse;
import mr.li.dance.models.LookCacnelInfo;
import mr.li.dance.models.PersonInfo;
import mr.li.dance.models.PersonItemInfo;
import mr.li.dance.models.ReportInfo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.shequ.SheQuPicDetails;
import mr.li.dance.ui.activitys.shequ.SheQuVideoDetails;
import mr.li.dance.ui.adapters.new_adapter.PersonItemAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.NToast;
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
    private String itemid;
    private String name;
    private String pic;
    boolean isCollected;

    @Override
    public void itemClick(int position, Object value) {
        PersonItemInfo shequInfo = (PersonItemInfo) value;
        String type = shequInfo.getType();
        if (type.equals("1")) {
            //图片
            SheQuPicDetails.lunch(this, shequInfo.getId(), shequInfo.getUid());
        } else {
            //视频
            SheQuVideoDetails.lunch(this, shequInfo.getId(), shequInfo.getUid(), shequInfo.video.get(0).picture);
        }
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        itemid = mIntentExtras.getString("itemid");
        name = mIntentExtras.getString("name");
        pic = mIntentExtras.getString("pic");
    }


    @Override
    public void initViews() {
        super.initViews();
        setTitle("个人名片");
        String userId = UserInfoManager.getSingleton().getUserId(this);
        if (!itemid.equals(userId)) {
            setRightImage(R.drawable.collect_icon, R.drawable.shequ_report);
        } else {

        }
        mDanceViewHolder.setViewVisibility(R.id.shequ_time, View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.title, View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.content, View.GONE);
        mDanceViewHolder.setViewVisibility(R.id.spline, View.VISIBLE);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        String userId = UserInfoManager.getSingleton().getUserId(this);
        ImageLoaderManager.getSingleton().LoadCircle(this, pic, mDanceViewHolder.getImageView(R.id.shequ_head), R.drawable.icon_mydefault);
        mDanceViewHolder.setText(R.id.shequ_name, name);
        Request<String> personItem = ParameterUtils.getSingleton().getPersonItem("1", String.valueOf(page), itemid, userId);
        Log.e(TAG, itemid);
        Log.e(TAG, userId + "---");
        request(AppConfigs.person_item, personItem, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        final String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> person = ParameterUtils.getSingleton().getPerson(userId, itemid);
        CallServer.getRequestInstance().add(PersonageActivity.this, 0, person, new HttpListener() {
            @Override
            public void onSucceed(int what, String response) {

                mDanceViewHolder.setViewVisibility(R.id.shequ_lin, View.VISIBLE); //关注粉丝
                shequ_look = mDanceViewHolder.getTextView(R.id.shequ_look);
                shequ_fans = mDanceViewHolder.getTextView(R.id.shequ_fans);
                PersonLookAndFansResponse reponseResult = JsonMananger.getReponseResult(response, PersonLookAndFansResponse.class);
                PersonInfo data = reponseResult.getData();
                isCollected = (2 != data.is_attention);

                if (isCollected) {
                    mRightIv.setImageResource(R.drawable.collect_icon_002);
                } else {
                    mRightIv.setImageResource(R.drawable.collect_icon);
                }
                Log.e("xxx", data.toString());
                shequ_look.setText("关注 ： " + data.getUser_num());
                shequ_fans.setText("粉丝 ： " + data.getAttention_num());
                shequ_look.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Shequ_look.lunch(PersonageActivity.this, itemid);
                    }
                });
                shequ_fans.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Shequ_fans.lunch(PersonageActivity.this, itemid);
                    }
                });
            }

            @Override
            public void onFailed(int what, int responseCode, String response) {

            }
        }, true, true);
        initDatas();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new PersonItemAdapter(this, this);
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
        PersonResponse reponseResult = JsonMananger.getReponseResult(response, PersonResponse.class);

        if (what == AppConfigs.person_item) {
            adapter.refresh(reponseResult);
        } else if (what == AppConfigs.person_items) {
            adapter.loadMore(reponseResult);
        }
        if (what == AppConfigs.user_collection) {
            LookCacnelInfo reponseResult1 = JsonMananger.getReponseResult(response, LookCacnelInfo.class);
            LookCacnelInfo.DataBean data = reponseResult1.getData();
            NToast.shortToast(this, data.getMessage());
            isCollected = !isCollected;
        }

        if (isCollected) {
            mRightIv.setImageResource(R.drawable.collect_icon_002);
        } else {
            mRightIv.setImageResource(R.drawable.collect_icon);
        }

        //举报
        if (what == AppConfigs.home_album) {
            ReportInfo report = JsonMananger.getReponseResult(response, ReportInfo.class);
            NToast.longToast(this, report.getData());
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        String userId = UserInfoManager.getSingleton().getUserId(this);
        page = 1;
        Request<String> personItem = ParameterUtils.getSingleton().getPersonItem("1", String.valueOf(page), itemid, userId);
        request(AppConfigs.person_item, personItem, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        String userId = UserInfoManager.getSingleton().getUserId(this);
        page++;
        Request<String> personItem = ParameterUtils.getSingleton().getPersonItem("1", String.valueOf(page), itemid, userId);
        request(AppConfigs.person_items, personItem, false);
    }


    public static void lunch(Fragment context, String id, String name, String pic) {
        Intent intent = new Intent(context.getContext(), PersonageActivity.class);
        intent.putExtra("itemid", id);
        intent.putExtra("name", name);
        intent.putExtra("pic", pic);
        context.startActivity(intent);
    }

    public static void lunch(Context context, String id, String name, String pic) {
        Intent intent = new Intent(context, PersonageActivity.class);
        intent.putExtra("itemid", id);
        intent.putExtra("name", name);
        intent.putExtra("pic", pic);
        context.startActivity(intent);
    }

    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        String userId = UserInfoManager.getSingleton().getUserId(this);
        int operation = isCollected ? 1 : 2;
        Request<String> personCancelLook = ParameterUtils.getSingleton().getPersonCancelLook(userId, itemid, operation);
        request(AppConfigs.user_collection, personCancelLook, false);
    }

    public void onHeadRightButtonClick2(View v) {

        Dialogs();
    }

    private void Dialogs() {
        final String userId = UserInfoManager.getSingleton().getUserId(this);
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("是否举报?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Request<String> personReport = ParameterUtils.getSingleton().getPersonReport(userId, "3", itemid);
                        request(AppConfigs.home_album, personReport, false);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.setCanceledOnTouchOutside(false);

    }

}
