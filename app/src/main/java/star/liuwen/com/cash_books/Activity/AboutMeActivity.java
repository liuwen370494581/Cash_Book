package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.ApkInfoUtils;

/**
 * Created by liuwen on 2017/1/22.
 */
public class AboutMeActivity extends BaseActivity {
    private TextView txtVersion, txtWeb;


    @Override
    public int activityLayoutRes() {
        return R.layout.about_me_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setTitle(getString(R.string.about_me));
        setLeftText(getString(R.string.back));

        txtVersion = (TextView) findViewById(R.id.about_me_txt_version);
        txtWeb = (TextView) findViewById(R.id.about_website_tv);
        txtWeb.setText(Html.fromHtml("<u>" + getString(R.string.my_github_web) + "<u>"));
        txtWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(txtWeb.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        txtVersion.setText("Version " + ApkInfoUtils.getVersionName(this));
    }
}
