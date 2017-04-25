package star.liuwen.com.cash_books.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liuwen on 2017/1/20.
 * 获取assets下的资源文件类
 */
public class BitMapUtils {
    public static Bitmap getBitmapByPath(Context context, String path, boolean auto) {
        //	Log.i("weather", "getbitmap is called");
        AssetManager am = context.getAssets();
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            if (auto == false) {
                is = am.open("bkgs/" + path);
            } else if (auto == true) {
                is = am.open("autobkgs/" + path);

            }
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
