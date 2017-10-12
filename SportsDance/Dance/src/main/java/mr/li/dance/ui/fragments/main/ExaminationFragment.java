package mr.li.dance.ui.fragments.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.broadcast.BroadcastManager;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.StringResponse;
import mr.li.dance.models.CertificateInfo;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.exam.CertificateReusltActivity;
import mr.li.dance.ui.activitys.exam.ExamCardActivity;
import mr.li.dance.ui.activitys.exam.QueryCertificateActivity;
import mr.li.dance.ui.activitys.mine.UserInfoActivity;
import mr.li.dance.ui.adapters.LvLiAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.NToast;
import mr.li.dance.utils.UserInfoManager;


/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/5/22
 * 描述: 主页-考级页面
 * 修订历史:
 */

public class ExaminationFragment extends BaseListActivity implements View.OnClickListener {
    LvLiAdapter mLvLiAdapter;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_exam;
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("我的证书");
        mRefreshLayout.setEnableLoadmore(false);
        mDanceViewHolder.setClickListener(R.id.image_right, this);
        mDanceViewHolder.setClickListener(R.id.image_right2, this);
        mDanceViewHolder.setClickListener(R.id.submit_btn, this);
        mDanceViewHolder.setClickListener(R.id.bulu_btn, this);
        mDanceViewHolder.setClickListener(R.id.explain_layout, this);
        checkInfomation();
    }


    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public void refresh() {
        super.refresh();
        String idn = UserInfoManager.getSingleton().getUserInfo(this).getId_card();
        String name = UserInfoManager.getSingleton().getUserInfo(this).getReal_name();
        int type = 1;
        Request<String> request = ParameterUtils.getSingleton().getKaojiCertificateMap(idn, name, type);
        request(AppConfigs.kaoji_mycertificate, request, false);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mLvLiAdapter = new LvLiAdapter(this);
        return mLvLiAdapter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_right:
                QueryCertificateActivity.lunch(this);
                break;
            case R.id.image_right2:
                ExamCardActivity.lunch(this);
                break;
            case R.id.bulu_btn:
                String userId = UserInfoManager.getSingleton().getUserId(this);
                UserInfoActivity.lunch(this);
                break;
            case R.id.explain_layout:
                MyDanceWebActivity.lunch(this, MyDanceWebActivity.KAOJI,"考级说明", AppConfigs.KAOJIEXPLAIN);
                break;
            case R.id.submit_btn:
                query();
                break;
        }
    }
    public static void lunch(Context context){
        Intent intent = new Intent(context, ExaminationFragment.class);
        context.startActivity(intent);
    }

    private void query() {
        String name = mDanceViewHolder.getTextValue(R.id.name_tv);
        String idn = mDanceViewHolder.getTextValue(R.id.card_tv);
        if (MyStrUtil.isEmpty(name)) {
            NToast.shortToast(this, "请输入查询的姓名");
            return;
        }
        if (MyStrUtil.isEmpty(idn)) {
            NToast.shortToast(this, "请输入查询的身份证号");
            return;
        }
        int type = 1;
        Request<String> request = ParameterUtils.getSingleton().getKaojiCertificateMap(idn, name, type);
        request(AppConfigs.kaoji_certificate, request, true);
    }


    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        StringResponse stringResponse = JsonMananger.getReponseResult(response, StringResponse.class);
        if (AppConfigs.kaoji_certificate == what) {
            String dateStr = stringResponse.getData();
            List<CertificateInfo> list = JsonMananger.jsonToList(dateStr, CertificateInfo.class);
            ArrayList<CertificateInfo> dataList = new ArrayList<>();
            dataList.addAll(list);
            CertificateReusltActivity.lunch(this, dataList);
        } else {
            String dateStr = stringResponse.getData();
            List<CertificateInfo> list = JsonMananger.jsonToList(dateStr, CertificateInfo.class);
            querySelfResult(list);
        }

    }

    private void querySelfResult(List<CertificateInfo> list) {
        if (MyStrUtil.isEmpty(list)) {
            mDanceViewHolder.setViewVisibility(R.id.nodatalayout, View.VISIBLE);
            mDanceViewHolder.setViewVisibility(R.id.bulu_layout, View.GONE);
            mDanceViewHolder.setViewVisibility(R.id.right_layout, View.INVISIBLE);
        } else {
            mDanceViewHolder.setViewVisibility(R.id.nodatalayout, View.GONE);
            mDanceViewHolder.setViewVisibility(R.id.right_layout, View.VISIBLE);
            mLvLiAdapter.addList(true, list);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BroadcastManager.getInstance(this).addAction(AppConfigs.updateinfoAction, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkInfomation();
            }
        });
    }

    private void checkInfomation() {
        if (!UserInfoManager.getSingleton().isLoading(this)) {
            return;
        }
        String realName = UserInfoManager.getSingleton().getUserInfo(this).getReal_name();
        String idCard = UserInfoManager.getSingleton().getUserInfo(this).getId_card();
        if (!MyStrUtil.isEmpty(realName) && !MyStrUtil.isEmpty(idCard)) {
            refresh();
        } else {
            mDanceViewHolder.setViewVisibility(R.id.nodatalayout, View.VISIBLE);
            if (MyStrUtil.isEmpty(realName) || MyStrUtil.isEmpty(idCard)) {
                mDanceViewHolder.setViewVisibility(R.id.bulu_layout, View.VISIBLE);
            } else {
                mDanceViewHolder.setViewVisibility(R.id.bulu_layout, View.GONE);
            }
        }
    }

    @Override
    public void onHeadLeftButtonClick(View v) {
        super.onHeadLeftButtonClick(v);
        hintKbTwo();
    }
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 0x002) {
            refresh();
        }
    }


}
