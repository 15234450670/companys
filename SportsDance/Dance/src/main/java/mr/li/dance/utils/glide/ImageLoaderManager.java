package mr.li.dance.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import mr.li.dance.utils.BlurTransformation;
import mr.li.dance.utils.NLog;

public class ImageLoaderManager {
    private volatile static ImageLoaderManager singleton;

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getSingleton() {
        if (singleton == null) {
            synchronized (ImageLoaderManager.class) {
                if (singleton == null) {
                    singleton = new ImageLoaderManager();
                }
            }
        }
        return singleton;
    }

     public void Load(Context context, String imgUrl, ImageView imageView, int defaultDrawable) {
        NLog.d("ImageLoaderManager", "imgUrl = " + imgUrl);
        try {
            Glide.with(context).load(imgUrl).dontTransform().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(defaultDrawable).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Load1(Context context, String imgUrl, final ImageView imageView, int defaultDrawable) {
        NLog.d("ImageLoaderManager", "imgUrl = " + imgUrl);
        try {
            Glide.with(context).load(imgUrl).asBitmap().dontTransform().dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(defaultDrawable).into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    super.setResource(resource);
                    int width = resource.getWidth();
                    int height = resource.getHeight();
                    //获取imageView的宽
                    int imageViewWidth= imageView.getWidth();
                    //计算缩放比例
                    float sy= (float) (imageViewWidth* 0.1)/(float) (width * 0.1);
                    //计算图片等比例放大后的高
                    int imageViewHeight= (int) (height * sy);
                    ViewGroup.LayoutParams params = imageView.getLayoutParams();
                    params.height = imageViewHeight;
                    imageView.setLayoutParams(params);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Loads(Context context, String imgUrl, final ImageView imageView, int defaultDrawable){
        NLog.d("ImageLoaderManager", "imgUrl = " + imgUrl);

            Glide.with(context).load(imgUrl).asBitmap().centerCrop().dontTransform().dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(defaultDrawable).into(imageView);


    }

    public void LoadBySize(Context context, String imgUrl, ImageView imageView, int defaultDrawable, int itemWidth, int itemHeight) {
        float degree = 0f;
//        if (itemWidth < itemHeight) {
//            degree = 90f;
//        }
        NLog.d("ImageLoaderManager", "imgUrl = " + imgUrl);
        NLog.d("ImageLoaderManager", "itemWidth = " + itemWidth + "  itemHeight=" + itemHeight);
        try {
            Glide.with(context).load(imgUrl).override(itemWidth, itemHeight)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(defaultDrawable).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Load(Context context, int resId, ImageView imageView, int defaultDrawable) {
        try {
            Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(defaultDrawable).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Load(Context context, int resId, ImageView imageView) {
        try {
            Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Load(Context context, String resId, ImageView imageView) {
        try {
            Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载模糊图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public void LoadMoHu(Context context, String imgUrl, ImageView imageView, int defaultId) {
        if (context == null) {
            return;
        }

        NLog.d("ImageLoaderManager", "imgUrl = " + imgUrl);
        try {
            Glide.with(context)
                    .load(imgUrl)
                    .bitmapTransform(new BlurTransformation(context, 16)).thumbnail(0.5f)//高斯模糊
                    .placeholder(defaultId)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void LoadMoHu_gedan(Context context, String imgUrl, ImageView imageView) {
        if (context == null) {
            return;
        }

        NLog.d("ImageLoaderManager", "imgUrl = " + imgUrl);
        try {
            Glide.with(context)
                    .load(imgUrl)
                    .bitmapTransform(new BlurTransformation(context, 16)).thumbnail(0.5f)//高斯模糊
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadCircle(Context context, String imgUrl, ImageView imageView, int defaultDrawable) {
        if (context == null) {
            return;
        }
        try {
            NLog.d("ImageLoaderManager", "imgUrl = " + imgUrl);
            Glide.with(context).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL).
                    transform(new GlideCircleTransform(context)).placeholder(defaultDrawable).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void LoadRound(Context context, String imgUrl, ImageView imageView, int defaultDrawable) {
        if (context == null) {
            return;
        }

        NLog.d("ImageLoaderManager", "imgUrl = " + imgUrl);
        try {
            Glide.with(context).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL).
                    transform(new GlideRoundTransform(context)).placeholder(defaultDrawable).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

}
