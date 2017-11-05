package mr.li.dance.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/11/3
 * 描述:
 * 修订历史:
 */
public class LookFirstPic {

    public static Bitmap LookFirstPic(String filePath) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null)
                        return bitmap;
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } catch (InstantiationException e) {
            Log.e("LookFirstPic-->", "LookFirstPic", e);
        } catch (InvocationTargetException e) {
            Log.e("LookFirstPic-->", "LookFirstPic", e);
        } catch (ClassNotFoundException e) {
            Log.e("LookFirstPic-->", "LookFirstPic", e);
        } catch (NoSuchMethodException e) {
            Log.e("LookFirstPic-->", "LookFirstPic", e);
        } catch (IllegalAccessException e) {
            Log.e("LookFirstPic-->", "LookFirstPic", e);
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
