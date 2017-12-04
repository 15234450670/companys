package mr.li.dance.ui.activitys.shequ;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.PersonLookAndFansResponse;
import mr.li.dance.https.response.SheQuDetailsResponse;
import mr.li.dance.models.DetailsInfo;
import mr.li.dance.models.LookCacnelInfo;
import mr.li.dance.models.PersonInfo;
import mr.li.dance.models.ReportInfo;
import mr.li.dance.ui.activitys.base.BaseActivityApp;
import mr.li.dance.ui.activitys.newActivitys.PersonageActivity;
import mr.li.dance.ui.adapters.new_adapter.DetailsListAdapter;
import mr.li.dance.ui.dialogs.GengduoDialog;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.NToast;
import mr.li.dance.utils.ShareUtils;
import mr.li.dance.utils.UserInfoManager;
import mr.li.dance.utils.glide.ImageLoaderManager;
import mr.li.dance.utils.util.SoftInputUtils;

import static mr.li.dance.ui.adapters.new_adapter.DetailsListAdapter.TYPE_ADD;
import static mr.li.dance.ui.adapters.new_adapter.DetailsListAdapter.TYPE_ITEM;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/26 0026
 * 描述:          社区视频详情
 * 修订历史:
 */
public class SheQuVideoDetails extends BaseActivityApp implements View.OnClickListener, View.OnLayoutChangeListener, DetailsListAdapter.ReplyInfo {
    private String TAG = getClass().getSimpleName();
    private String     itemId;
    private String     uid;
    private ImageView  imageView;
    private PersonInfo titleData;
    boolean       isCollected;
    GengduoDialog dialog;
    private DetailsInfo            data;
    private JCVideoPlayerStandard  player;
    private RecyclerView           listViewUtils;
    private ArrayList<DetailsInfo> detailsInfo;
    private DetailsListAdapter     adapter;
    private RelativeLayout         editTextBodyLl;
    private TextView               sendIv;
    private EditText               circleEt;
    private ScrollView             mScrollView;
    private int                    keyHeight;
    private String                 id;
    private boolean type = true;
    private String dataId;
    private String pic;
    boolean isDianZan;

    @Override
    public int getContentViewId() {
        return R.layout.shequ_video_details;
    }

    @Override
    public void initViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("视频详情");
        listViewUtils = (RecyclerView) mDanceViewHolder.getView(R.id.list_view);
        editTextBodyLl = ((RelativeLayout) mDanceViewHolder.getView(R.id.editTextBodyLl));
        sendIv = mDanceViewHolder.getTextView(R.id.sendIv);
        circleEt = mDanceViewHolder.getEditText(R.id.circleEt);
        mScrollView = ((ScrollView) findViewById(R.id.scrollView));
        editTextBodyLl.addOnLayoutChangeListener(this);
        keyHeight = this.getWindowManager().getDefaultDisplay().getHeight() / 3;

        imageView = mDanceViewHolder.getImageView(R.id.details_look);
        setRightImage(R.drawable.more, R.drawable.dianzan1);
        player = (JCVideoPlayerStandard) mDanceViewHolder.getView(R.id.player_list_video);
        // TODO: 11/1/17 展示
        sendIv.setOnClickListener(this);
        editTextBodyLl.setVisibility(View.VISIBLE);
    }

    public static void lunch(Context context, String id, String uid, String pic) {
        Intent intent = new Intent(context, SheQuVideoDetails.class);
        intent.putExtra("itemId", id);
        intent.putExtra("uid", uid);
        intent.putExtra("pic", pic);
        context.startActivity(intent);

    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        itemId = mIntentExtras.getString("itemId");
        uid = mIntentExtras.getString("uid");
        pic = mIntentExtras.getString("pic");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> personDetails = ParameterUtils.getSingleton().getPersonDetails(itemId, userId, uid);
        request(AppConfigs.person_details, personDetails, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e(TAG, response);
        if (what == AppConfigs.person_details) {
            SheQuDetailsResponse reponseResult = JsonMananger.getReponseResult(response, SheQuDetailsResponse.class);
            data = reponseResult.getData();
            id = data.getId();
            dataId = data.getId();
            if (!MyStrUtil.isEmpty(data)) {
                mDanceViewHolder.setText(R.id.shequ_title, data.getTitle());
                ImageLoaderManager.getSingleton().LoadCircle(this, data.getUser().get(0).getPicture_src(), mDanceViewHolder.getImageView(R.id.details_icon), R.drawable.default_icon);
                mDanceViewHolder.setText(R.id.details_name, data.getUser().get(0).getUsername());
                mDanceViewHolder.setText(R.id.details_time, data.getDynamic_time());
                if (!MyStrUtil.isEmpty(data.getContent())) {
                    mDanceViewHolder.getView(R.id.details_content).setVisibility(View.VISIBLE);
                    mDanceViewHolder.setText(R.id.details_content, data.getContent());
                } else {
                    mDanceViewHolder.getView(R.id.details_content).setVisibility(View.GONE);
                }
                mDanceViewHolder.getView(R.id.lin_head).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PersonageActivity.lunch(mContext, uid, data.getUser().get(0).getUsername(), data.getUser().get(0).getPicture_src());
                    }
                });
                int is_upvote = data.getIs_upvote();
                isDianZan = (1 != is_upvote);
            }
            String path = data.getVideo().get(0);

            //视频播放
            if (!MyStrUtil.isEmpty(path)) {
                boolean setUp = player.setUp(path, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                if (setUp) {
                    //后台返回的图片
                    if (TextUtils.isEmpty(pic)) {
                        player.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(this).load(R.drawable.default_banner).into(player.thumbImageView);
                    } else {
                        player.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(this).load(pic).into(player.thumbImageView);
                    }

                }
            }


        }
        int commCount = 0;
        detailsInfo = new ArrayList<>();
        if (!MyStrUtil.isEmpty(data.getComm())) {
            listViewUtils.setVisibility(View.VISIBLE);
            commCount = data.getComm().size();
            for (int i = 0; i < commCount; i++) {
                detailsInfo.add(data);
            }
            adapter = new DetailsListAdapter(this, detailsInfo, getCurrentFocus());
            listViewUtils.setLayoutManager(new LinearLayoutManager(this));
            adapter.addList(detailsInfo);
            listViewUtils.setAdapter(adapter);

        } else {
            listViewUtils.setVisibility(View.GONE);
        }


        //关注操作
        if (what == AppConfigs.user_collection) {
            LookCacnelInfo reponseResult1 = JsonMananger.getReponseResult(response, LookCacnelInfo.class);
            LookCacnelInfo.DataBean data = reponseResult1.getData();
            NToast.shortToast(this, data.getMessage());
            isCollected = !isCollected;
        }
        if (isCollected) {
            imageView.setImageResource(R.drawable.my_look_no);
        } else {
            imageView.setImageResource(R.drawable.my_look_yes);
        }
        if (what == AppConfigs.GET_PUBLISH) {
            initDatas();
            Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();

        }
        if (what == AppConfigs.person) {
            isDianZan = !isDianZan;
        }
        if (isDianZan) {
            mRightIv2.setImageResource(R.drawable.dianzan1);
        } else {
            mRightIv2.setImageResource(R.drawable.dianzan2);

        }
        type = true;
    }


    //关注状态
    @Override
    public void onResume() {
        super.onResume();
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> person = ParameterUtils.getSingleton().getPerson(userId, uid);
        CallServer.getRequestInstance().add(this, 0, person, new HttpListener() {
            @Override
            public void onSucceed(int what, String response) {
                PersonLookAndFansResponse reponseResult = JsonMananger.getReponseResult(response, PersonLookAndFansResponse.class);
                titleData = reponseResult.getData();
                int is_attention = titleData.is_attention;
                Log.e(TAG, is_attention + "");
                if (is_attention == 1) {
                    imageView.setImageResource(R.drawable.my_look_no);
                } else {
                    imageView.setImageResource(R.drawable.my_look_yes);
                }

                imageView.setOnClickListener(SheQuVideoDetails.this);
            }

            @Override
            public void onFailed(int what, int responseCode, String response) {

            }
        }, false, false);
        initDatas();
    }

    //删除动态
    private void PersonDelete(final String id) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否删除动态?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Request<String> personDelete = ParameterUtils.getSingleton().getPersonDelete("1", id);
                        CallServer.getRequestInstance().add(SheQuVideoDetails.this, 0, personDelete, new HttpListener() {
                            @Override
                            public void onSucceed(int what, String response) {
                                ReportInfo report = JsonMananger.getReponseResult(response, ReportInfo.class);
                                View view = mDanceViewHolder.getView(R.id.lin);
                                View view1 = mDanceViewHolder.getView(R.id.v);
                                if (!MyStrUtil.isEmpty(report.getData())) {
                                    view.setVisibility(View.GONE);

                                    view1.setVisibility(View.VISIBLE);
                                    NToast.longToast(SheQuVideoDetails.this, report.getData());
                                } else {
                                    view.setVisibility(View.VISIBLE);
                                    view1.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailed(int what, int responseCode, String response) {

                            }
                        }, false, false);
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

    //举报
    private void Dialogs(final String userId, final String uid) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否举报?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Request<String> personReport = ParameterUtils.getSingleton().getPersonReport(userId, "3", uid);
                        CallServer.getRequestInstance().add(SheQuVideoDetails.this, 0, personReport, new HttpListener() {
                            @Override
                            public void onSucceed(int what, String response) {
                                ReportInfo report = JsonMananger.getReponseResult(response, ReportInfo.class);
                                NToast.longToast(SheQuVideoDetails.this, report.getData());
                            }

                            @Override
                            public void onFailed(int what, int responseCode, String response) {

                            }
                        }, false, false);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_look:
                String userId = UserInfoManager.getSingleton().getUserId(this);
                int operation = isCollected ? 1 : 2;
                Request<String> personCancelLook = ParameterUtils.getSingleton().getPersonCancelLook(userId, uid, operation);
                request(AppConfigs.user_collection, personCancelLook, false);
                break;
            case R.id.sendIv:
                String s = circleEt.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    reuqestNet(id, type);
                    SoftInputUtils.closeInput(this, getCurrentFocus());
                    circleEt.getText().clear();
                }

                break;
        }
    }

    private void reuqestNet(String id, boolean type) {
        String userId = UserInfoManager.getSingleton().getUserId(this);
        if (type) {
            Request<String> publishingDynamics = ParameterUtils.getSingleton().publishingDynamics(userId, id.equals(dataId) ? 1 : 2, circleEt.getText().toString(), id);
            request(AppConfigs.GET_PUBLISH, publishingDynamics, false);

        } else {
            Request<String> publishingDynamics = ParameterUtils.getSingleton().publishingDynamics(userId, 2, circleEt.getText().toString(), id);
            request(AppConfigs.GET_PUBLISH, publishingDynamics, false);
        }
    }

    //更多
    @Override
    public void onHeadRightButtonClick(View v) {
        final String userId = UserInfoManager.getSingleton().getUserId(SheQuVideoDetails.this);
        dialog = new GengduoDialog(this, new GengduoDialog.DialogClickListener() {
            @Override
            public void selectItem(View view, String value) {
                switch (value) {
                    case "删除":
                        PersonDelete(data.getId());
                        break;
                    case "分享":
                        ShareUtils shareUtils = new ShareUtils(SheQuVideoDetails.this);

                        String mShareContent = data.getTitle();
                        shareUtils.showShareDilaog(AppConfigs.CLICK_EVENT_21, AppConfigs.shequ_detial+data.getId(), mShareContent);

                        break;
                    case "举报":

                        Dialogs(userId, uid);
                        break;
                    case "取消":
                        dialog.dismin();
                        break;
                    default:
                        break;
                }
            }
        });
        if (userId.equals(uid)) {
            dialog.dispalyMy(); //自己的动态
        } else {
            dialog.dispalyOther();//别人的动态
        }
    }

    //点赞
    public void onHeadRightButtonClick2(View v) {
        int operation = isDianZan ? 1 : 2;
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> personLike = ParameterUtils.getSingleton().getPersonLike(userId, operation, data.getId());
        request(AppConfigs.person, personLike, false);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    private void recylcerViewBottom() {
        mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                circleEt.setFocusable(true);
                circleEt.setFocusableInTouchMode(true);
                circleEt.requestFocus();
            }
        }, 100);

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom) >= keyHeight) {
            recylcerViewBottom();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom) >= keyHeight) {
            circleEt.setHint("说点什么..");
        }
    }

    @Override
    public void getReply(String id, String name, int itemType) {
        if (itemType == TYPE_ADD) {
            circleEt.setHint("回复：" + name);
            SoftInputUtils.showInput(this, getCurrentFocus());
            this.id = id;
            type = false;
        } /*else if(itemType == TYPE_DEL){
            deleteItemRequest(id, 2);
        } */ else if (itemType == TYPE_ITEM) {
            this.id = id;
            circleEt.setHint("回复：" + name);
            SoftInputUtils.showInput(this, getCurrentFocus());
        }
    }

    @Override
    public void getDatasItem() {
        initDatas();
    }

    @Override
    public void share(String id, String title) {
        showShareDialog(id, title);
    }

    ShareUtils mShareUtils;

    private void showShareDialog(String id, String title) {
        MobclickAgent.onEvent(this, AppConfigs.CLICK_EVENT_29);
        if (mShareUtils == null) {
            mShareUtils = new ShareUtils(this);
        }
        mShareUtils.showShareDilaog(AppConfigs.CLICK_EVENT_29, AppConfigs.shequ_detial + id, title);
    }

    @Override
    public void onHeadLeftButtonClick(View v) {
        super.onHeadLeftButtonClick(v);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
