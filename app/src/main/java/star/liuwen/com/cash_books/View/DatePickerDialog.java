package star.liuwen.com.cash_books.View;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;

/**
 * 单选的滚轮效果
 * Created by liuwen on 2017/3/23.
 */
public class DatePickerDialog extends BaseDialog {

    private TextView tv_cancel;
    private TextView tv_sure;
    private TextView tv_title;
    private WheelView wheelView;
    private OnClickCallback callback;
    private String data;
    private List<String> list;

    public DatePickerDialog(Context context) {
        super(context);
        this.mContext = context;
        this.list = new ArrayList<>();
        list = DataEnige.getDateDate();
        this.dialog = new Dialog(mContext, R.style.picker_dialog);
        dialog.setContentView(R.layout.date_money_picker);
        tv_cancel = (TextView) dialog.findViewById(R.id.cancel);
        tv_sure = (TextView) dialog.findViewById(R.id.ok);
        tv_title = (TextView) dialog.findViewById(R.id.title);
        tv_title.setText("还款日");
        wheelView = (WheelView) dialog.findViewById(R.id.wheel);
        wheelView.setData(list);
        data = list.get(0);
        wheelView.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                data = text;
            }
        });
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        setDialogLocation(mContext, dialog);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ok:
                if (callback != null) {
                    if (!data.equals("") && !"".equals(data)) {
                        callback.onSure(data);
                    }
                }
                break;
            case R.id.cancel:
                if (callback != null) {
                    callback.onCancel();
                }
                break;
        }
    }

    public void setCallback(OnClickCallback callback) {
        this.callback = callback;
    }

    public interface OnClickCallback {
        void onCancel();

        void onSure(String data);
    }
}
