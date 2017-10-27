package mr.li.dance.ui.activitys.shequ;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yolanda.nohttp.rest.Request;

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
import mr.li.dance.ui.dialogs.GengduoDialog;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.NToast;
import mr.li.dance.utils.ShareUtils;
import mr.li.dance.utils.UserInfoManager;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/26 0026
 * 描述:          社区视频详情
 * 修订历史:
 */
public class SheQuVideoDetails extends BaseActivityApp implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private String     itemId;
    private String     uid;
    private ImageView  imageView;
    private PersonInfo titleData;
    boolean       isCollected;
    GengduoDialog dialog;
    private DetailsInfo           data;
    private JCVideoPlayerStandard player;

    @Override
    public int getContentViewId() {
        return R.layout.shequ_video_details;
    }

    @Override
    public void initViews() {
        setTitle("视频详情");

        imageView = mDanceViewHolder.getImageView(R.id.details_look);
        setRightImage(R.drawable.more);
        player = (JCVideoPlayerStandard) mDanceViewHolder.getView(R.id.player_list_video);


    }

    public static void lunch(Context context, String id, String uid) {
        Intent intent = new Intent(context, SheQuVideoDetails.class);
        intent.putExtra("itemId", id);
        intent.putExtra("uid", uid);
        context.startActivity(intent);

    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        itemId = mIntentExtras.getString("itemId");
        uid = mIntentExtras.getString("uid");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> personDetails = ParameterUtils.getSingleton().getPersonDetails(itemId, userId);
        request(AppConfigs.person_details, personDetails, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e(TAG, response);
        if (what == AppConfigs.person_details) {
            SheQuDetailsResponse reponseResult = JsonMananger.getReponseResult(response, SheQuDetailsResponse.class);
            data = reponseResult.getData();
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
            }
            //视频播放
            boolean setUp = player.setUp(data.getVideo().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            if (setUp) {
                //后台返回的图片
                // Glide.with(this).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(player.thumbImageView);
            }
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
                        String shareUrl = String.format(AppConfigs.DOINGTAI, data.getId());
                        String mShareContent = data.getTitle();
                        shareUtils.showShareDilaog(AppConfigs.CLICK_EVENT_21, shareUrl, mShareContent);
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
}
