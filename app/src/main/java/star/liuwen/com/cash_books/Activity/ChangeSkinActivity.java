package star.liuwen.com.cash_books.Activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.cash_books.Adapter.BgPicGridAdapter;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.BitMapUtils;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.bean.BgPicModel;

/**
 * Created by liuwen on 2017/1/20.
 */
public class ChangeSkinActivity extends BaseActivity {
    private GridView mGridView;
    private List<BgPicModel> mList;
    private DrawerLayout mDrawerLayout;
    private BgPicGridAdapter mAdapter;

    @Override
    public int activityLayoutRes() {
        return R.layout.change_skin_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setTitle(getString(R.string.theme));
        setLeftText(getString(R.string.back));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mGridView = (GridView) findViewById(R.id.change_background_grid);
        mList = new ArrayList<>();
        getDataFormAssets();
        if (SharedPreferencesUtil.getStringPreferences(this, Config.ChangeBg, null) != null) {
            Bitmap bitmap = BitMapUtils.getBitmapByPath(this, SharedPreferencesUtil.getStringPreferences(this, Config.ChangeBg, null), false);
            mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }
        mAdapter = new BgPicGridAdapter(this, mList);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = ((BgPicModel) mAdapter.getItem(position)).path;
                SharedPreferencesUtil.setStringPreferences(ChangeSkinActivity.this, Config.ChangeBg, path);
                Drawable drawable = Drawable.createFromPath(path);
                mDrawerLayout.setBackgroundDrawable(drawable);
                Bitmap bitmap = BitMapUtils.getBitmapByPath(ChangeSkinActivity.this, path, false);
                if (bitmap != null) {
                    mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                }
                RxBus.getInstance().post(Config.isBgCash, path);
            }
        });
        mGridView.setAdapter(mAdapter);
    }

    private void getDataFormAssets() {
        AssetManager am = getAssets();
        try {
            String[] drawableList = am.list("bkgs");
            mList = new ArrayList<>();
            for (String path : drawableList) {
                BgPicModel model = new BgPicModel();
                InputStream is = am.open("bkgs/" + path);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                model.path = path;
                model.bitmap = bitmap;
                mList.add(model);
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
