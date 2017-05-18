package star.liuwen.com.cash_books.Fragment;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.EasyPermissions;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.CircleImageView;
import star.liuwen.com.cash_books.bean.CommunityModel;

/**
 * Created by liuwen on 2017/5/9.
 * 社区交流页面
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener, BGANinePhotoLayout.Delegate, EasyPermissions.PermissionCallbacks {

    private RecyclerView mRecyclerView;
    private View headView;
    private CircleImageView userImage;
    private ImageView imageEdit;
    private TextView txtEditContents;
    private BGANinePhotoLayout mCurrentClickNpl;
    private CommunityAdapter mAdapter;

    @Override
    public void lazyInitData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_community);
        initView();
        return getContentView();
    }

    private void initView() {
        setTitle(getString(R.string.community));
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.f_comm_recycler_view);

        headView = View.inflate(getActivity(), R.layout.head_community, null);
        userImage = (CircleImageView) headView.findViewById(R.id.comm_image);
        txtEditContents = (TextView) headView.findViewById(R.id.comm_txt);
        imageEdit = (ImageView) headView.findViewById(R.id.comm_edit);

        mAdapter = new CommunityAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setData(DataEnige.getCommunityDate());
        mAdapter.addHeaderView(headView);

        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());

        Bitmap bt = BitmapFactory.decodeFile(Config.RootPath + "head.jpg");
        if (bt != null) {
            userImage.setImageBitmap(bt);
        }
        imageEdit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageEdit) {
        }
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }
        //保存图片的目录 改成自己要保存的图片目录，如果不传递该参数就不会显示右上角的保存按钮
        File downloadDir = new File(Config.RootPath, "cash_book");
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(getActivity(), downloadDir, mCurrentClickNpl.getCurrentClickItem()));
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片
                startActivity(BGAPhotoPreviewActivity.newIntent(getActivity(), downloadDir, mCurrentClickNpl.getData(), mCurrentClickNpl.getCurrentClickItemPosition()));
            }
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", 1, perms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == 1) {
            ToastUtils.showToast(getActivity(), "您拒绝了「图片预览」所需要的相关权限!");
        }

    }


    private class CommunityAdapter extends BGARecyclerViewAdapter<CommunityModel> {

        public CommunityAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_fragment_community);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, CommunityModel model) {
            if (TextUtils.isEmpty(model.getContent())) {
                helper.setVisibility(R.id.item_comm_title, View.GONE);
            } else {
                helper.setVisibility(R.id.item_comm_title, View.VISIBLE);
                helper.setText(R.id.item_comm_title, model.getContent());
            }
            helper.setImageResource(R.id.item_comm_image, model.getUserPhoto());
            helper.setText(R.id.item_comm_name, model.getUserName());
            helper.setText(R.id.item_comm_location, model.getUserLocation());
            helper.setText(R.id.item_comm_times, model.getUserTime());

            if (model.getDiscuss().size() == 0) {
                helper.setVisibility(R.id.item_comm_discuss_number, View.GONE);
            } else {
                helper.setVisibility(R.id.item_comm_discuss_number, View.VISIBLE);
                helper.setText(R.id.item_comm_discuss_number, model.getDiscuss().size() + "");
            }
            if (model.getGoodJob() < 0) {
                helper.setVisibility(R.id.item_comm_goodJop_number, View.GONE);
            } else {
                helper.setVisibility(R.id.item_comm_goodJop_number, View.VISIBLE);
                helper.setText(R.id.item_comm_goodJop_number, model.getGoodJob() + "");
            }
            BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.npl_item_moment_photos);
            ninePhotoLayout.setDelegate(CommunityFragment.this);
            ninePhotoLayout.setData(model.getPhotos());

        }
    }
}
