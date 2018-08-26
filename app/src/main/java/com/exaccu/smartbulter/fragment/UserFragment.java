package com.exaccu.smartbulter.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exaccu.smartbulter.R;
import com.exaccu.smartbulter.entity.MyUser;
import com.exaccu.smartbulter.ui.CourierActivity;
import com.exaccu.smartbulter.ui.LoginActivity;
import com.exaccu.smartbulter.ui.PhoneActivity;
import com.exaccu.smartbulter.utils.L;
import com.exaccu.smartbulter.utils.PermissionUtil;
import com.exaccu.smartbulter.utils.UtilTools;
import com.exaccu.smartbulter.view.CustomDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements View.OnClickListener {


    private static final int RESULT_REQUEST_CODE = 102;
    @BindView(R.id.edit_user)
    TextView editUser;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.btn_update_ok)
    Button btnUpdateOk;
    @BindView(R.id.tv_courier)
    TextView tvCourier;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_exit_user)
    Button btnExitUser;
    Unbinder unbinder;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    private CustomDialog dialog;

    private Button btnCamera;
    private Button btnPicture;
    private Button btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);

        UtilTools.getImageToShare(getContext(), profileImage);
        //默认不可点击
        etUsername.setEnabled(false);
        etAge.setEnabled(false);
        etDesc.setEnabled(false);
        etSex.setEnabled(false);
        //设置具体的值
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        etUsername.setText(user.getUsername());
        etSex.setText(user.isSex() ? "男" : "女");
        etAge.setText(user.getAge() + "");
        etDesc.setText(user.getDesc());

        //自定义一个dialog
        dialog = new CustomDialog(getActivity(), 100, 100, R.layout.dialog_photo, R.style.Theme_dialog, Gravity.BOTTOM,R.style.pop_anim_style);
        dialog.setCancelable(false);

        btnCamera = dialog.findViewById(R.id.btn_camera);
        btnPicture = dialog.findViewById(R.id.btn_picture);
        btnCancel = dialog.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(this);
        btnPicture.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_update_ok, R.id.btn_exit_user, R.id.edit_user, R.id.tv_courier,R.id.tv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_user:
                btnUpdateOk.setVisibility(View.VISIBLE);
                setEnable(true);
                break;
            case R.id.btn_update_ok:
                String username = etUsername.getText().toString().trim();
                String age = (etAge.getText().toString().trim());
                String sex = etSex.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(sex)) {
                    MyUser myUser = new MyUser();
                    myUser.setUsername(username);
                    if (sex.equals("男")) {
                        myUser.setSex(true);
                    } else {
                        myUser.setSex(false);
                    }
                    if (!TextUtils.isEmpty(desc)) {
                        myUser.setDesc(desc);
                    } else {
                        myUser.setDesc(getString(R.string.no_desc));
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    myUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (null == e) {
                                setEnable(false);
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), getString(R.string.input_cannot_empty), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_exit_user:
                //退出登录
                //清楚缓存用户对象
                MyUser.logOut();
                //现在的currentUser是null
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
        }
    }

    /**
     * 控制四个输入框的焦点
     *
     * @param is
     */
    private void setEnable(boolean is) {
        etUsername.setEnabled(is);
        etSex.setEnabled(is);
        etAge.setEnabled(is);
        etDesc.setEnabled(is);
    }

    @OnClick(R.id.profile_image)
    public void onViewClicked() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.btn_camera:
                openCamera(getActivity());
                break;
        }
    }

    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    private static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int IMAGE_REQUEST_CODE = 101;
    private Uri imageUri;

    private static File tempFile;

    public void openCamera(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                PermissionUtil.verifyStoragePermissions(getActivity());
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(getActivity(),"请开启存储权限",Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为CAMERA_REQUEST_CODE
        activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profileImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImageToShare(getActivity(), profileImage);
    }
}
