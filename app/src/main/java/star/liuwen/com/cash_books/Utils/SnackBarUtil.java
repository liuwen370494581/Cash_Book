package star.liuwen.com.cash_books.Utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by liuwen on 2017/2/14.
 */
public class SnackBarUtil {

    public static void show(View view, CharSequence text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.parseColor("#71CE7E"));
        if (text.length() > 10) {
            snackbar.setDuration(Snackbar.LENGTH_LONG);
        }
        snackbar.show();
    }
}
