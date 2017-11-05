package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.models.DetailsInfo;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.ui.widget.CommentListView;
import mr.li.dance.utils.UserInfoManager;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/27 0027
 * 描述:    社区详情图文适配器
 * 修订历史:
 */
public class DetailsListAdapter extends BaseRecyclerAdapter<DetailsInfo>  {
    private int countAddress;
    private int countComm;
    private View.OnClickListener onClickListener;
    private Context context;
    private View view;
    private ReplyInfo mReplyInfo;
    public static final int TYPE_ADD = 1;
    public static final int TYPE_DEL = 2;
    public static final int TYPE_ITEM = 5;
    public static final int ITEM_ID = 3;
    public static final int ITEM_NAME = 4;
    //
    public DetailsListAdapter(Context ctx, ArrayList<DetailsInfo> datas, View view) {
        super(ctx, datas);
        context = ctx;
        onClickListener = ((View.OnClickListener) ctx);
        mReplyInfo = ((ReplyInfo) ctx);
        if (datas.size() > 0) {
            DetailsInfo detailsInfo = datas.get(0);
            if (detailsInfo.getAddress() != null) {
                countAddress = detailsInfo.getAddress().size();
            }
            if (detailsInfo.getComm() != null) {
                countComm = detailsInfo.getComm().size();
            }
        }

        this.view = view;
    }

    @Override
    public int getItemLayoutId(int viewType) {

        switch (viewType) {
            case 1:
                return R.layout.shequ_details_pic_item;
            case 2:
                return R.layout.detials_talk;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < countAddress) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, final DetailsInfo item) {
        if (countAddress == 0 && countComm == 0) {
            return;
        }
        if (position < countAddress) {
            holder.setImageByUrlOrFilePath(R.id.pic_item, item.getAddress().get(position), R.drawable.default_banner);
        } else {
            holder.setText(R.id.shequ_time, item.getComm().get(position - countAddress).getComment_time());
            holder.setText(R.id.shequ_name, item.getComm().get(position - countAddress).getUser().get(0).getUsername());

            ImageLoaderManager.getSingleton().LoadCircle(mContext, item.getComm().get(position - countAddress).getUser().get(0).getPicture_src(),
                    holder.getImageView(R.id.shequ_head), R.drawable.default_icon);
            holder.getView(R.id.content).setVisibility(View.GONE);
            TextView textView = holder.getTextView(R.id.title);
            textView.setText(item.getComm().get(position - countAddress).getContent());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mReplyInfo.getReply(item.getComm().get(position - countAddress).getId(), item.getComm().get(position - countAddress).getUser().get(0).getUsername(),
                            TYPE_ITEM);
                }
            });


//            ((CommentListView) holder.getView(R.id.commentList))
//                    .setOnItemClickListener(new CommentListView.OnItemClickListener() {
//                @Override
//                public void onItemClick(int commentPosition) {
//                    CommentItem commentItem = commentsDatas.get(commentPosition);
//                    if(DatasUtil.curUser.getId().equals(commentItem.getUser().getId())){//复制或者删除自己的评论
//
//                        CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
//                        dialog.show();
//                    }else{//回复别人的评论
//                        if(presenter != null){
//                            CommentConfig config = new CommentConfig();
//                            config.circlePosition = circlePosition;
//                            config.commentPosition = commentPosition;
//                            config.commentType = CommentConfig.Type.REPLY;
//                            config.replyUser = commentItem.getUser();
//                            presenter.showEditTextBody(config);
//                        }
//                    }
//                }
//            });
//
//            ((CommentListView) holder.getView(R.id.commentList)).setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
//                @Override
//                public void onItemLongClick(int commentPosition) {
//                    长按进行复制或者删除
//                    CommentItem commentItem = commentsDatas.get(commentPosition);
//                    CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
//                    dialog.show();
//                }
//            });

            final CommentListView commentListView = (CommentListView) holder.getView(R.id.commentList);
            final DetailsInfo.CommBean commBean = item.getComm().get(position - countAddress);
            if (commBean != null) {
                commentListView.setDatas(commBean);
                commentListView.setVisibility(View.VISIBLE);
            } else {
                commentListView.setVisibility(View.GONE);
            }

            commentListView.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(int commentPosition) {
                    String userId = commBean.getComd().get(commentPosition).getUserid();
                    String currentUserId = UserInfoManager.getSingleton().getUserId(context);
//                    if(currentUserId.equals(userId)){//复制或者删除自己的评论

                    mReplyInfo.getReply(commBean.getId(), commBean.getUser().get(0).getUsername(),  TYPE_ADD);
//                    }
                }
            });

//             commentListView.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
//        @Override
//        public void onItemLongClick(final int commentPosition) {
//            长按进行复制或者删除
//            DetailsInfo.CommBean.Revert revert = commBean.getComd().get(commentPosition);
//            CommentDialog dialog = new CommentDialog(context, revert, commentPosition, new CommentDialog.DeleteItem() {
//                @Override
//                public void deleteItemContent() {
//                    String id = commBean.getComd().get(commentPosition).getId();
//                    mReplyInfo.getReply(id, TYPE_DEL);
        }
//            });
//            dialog.show();
//        }
//    });
//        }
    }

    @Override
    public int getItemCount() {
        return countAddress + countComm;
    }


//               holder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
//        @Override
//        public void onItemClick(int commentPosition) {
//            CommentItem commentItem = commentsDatas.get(commentPosition);
//            if(DatasUtil.curUser.getId().equals(commentItem.getUser().getId())){//复制或者删除自己的评论
//
//                CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
//                dialog.show();
//            }else{//回复别人的评论
//                if(presenter != null){
//                    CommentConfig config = new CommentConfig();
//                    config.circlePosition = circlePosition;
//                    config.commentPosition = commentPosition;
//                    config.commentType = CommentConfig.Type.REPLY;
//                    config.replyUser = commentItem.getUser();
//                    presenter.showEditTextBody(config);
//                }
//            }
//        }
//    });


    public interface ReplyInfo {
        void getReply(String id, String name, int type);
    }
}

