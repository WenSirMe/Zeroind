package org.sssta.zeroind.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.sssta.zeroind.Constants;
import org.sssta.zeroind.R;
import org.sssta.zeroind.service.BaseService;
import org.sssta.zeroind.service.response.ImageLoadResponse;
import org.sssta.zeroind.service.response.ResponseStatus;
import org.sssta.zeroind.ui.CircleImageView;
import org.sssta.zeroind.util.SharedPreferenceUtil;
import org.sssta.zeroind.util.TextUtil;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserSetting extends AppCompatActivity {
    private Context mContext;


    @Bind(R.id.btext_setting_password)
    TextView btextPasswordSetting;
    @Bind(R.id.linear_setting_passwrod)
    LinearLayout passwordLinear;
    @Bind(R.id.original_password)
    EditText originalPassword;
    @Bind(R.id.new_password)
    EditText newPassword;
    @Bind(R.id.setting_image)
    CircleImageView settingImage;
    @Bind(R.id.setting_username)
    EditText settingUsername;
    @Bind(R.id.setting_direction)
    EditText settingDirection;
    @Bind(R.id.setting_signature)
    EditText settingSignature;
    @Bind(R.id.setting_commit)
    Button buttonCommit;

    private File tempFile;
    private boolean isUserPhotoSelected = false;
    private boolean isNameUseful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ButterKnife.bind(this);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        Picasso.with(mContext).load(Constants.BASE_IMAGE_LOAD+SharedPreferenceUtil.getInstance().getImg_id()
                +Constants.PHOTO_TYPE).into(settingImage);
        settingUsername.setText(SharedPreferenceUtil.getInstance().getUserName());
        settingDirection.setText(Constants.WIND_DIRS[SharedPreferenceUtil.getInstance().getDirection()]);
        settingSignature.setText(SharedPreferenceUtil.getInstance().getSignature());
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageIntent = new Intent();
                getImageIntent.setType("image/*");
                getImageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(getImageIntent, Constants.SELECT_PIC);
            }
        });
        buttonCommit.setOnClickListener(myOnclickListener);
        btextPasswordSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLinearShow()) {
                    btextPasswordSetting.setText("设置新密码");
                    passwordLinear.setVisibility(View.GONE);
                    originalPassword.setText("");
                    newPassword.setText("");
                } else {
                    btextPasswordSetting.setText("取消");
                    passwordLinear.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    View.OnClickListener myOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isUserPhotoSelected) {
                RequestBody body = RequestBody.create(
                        MediaType.parse("image/*"), tempFile);
                Call<ImageLoadResponse> call = BaseService.getUserService().uploadImage(body);
                call.enqueue(new Callback<ImageLoadResponse>() {
                    @Override
                    public void onResponse(Response<ImageLoadResponse> response, Retrofit retrofit) {
                        if (response.body()!=null && response.body().getStatus() == 0) {
                            SharedPreferenceUtil.getInstance().setImageId(response.body().getImg_id());
                            isUserPhotoSelected = false;
                        } else {
                            Toast.makeText(getApplicationContext(),"图片上传失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(),"图片上传失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            final String password;
            if (isLinearShow()) {
                if (!originalPassword.getText().toString().equals(SharedPreferenceUtil.getInstance().getPassword())) {
                    originalPassword.setError("原密码输入错误");
                    return;
                }
                if (!TextUtil.checkPassLegal(newPassword.getText().toString())) {
                    newPassword.setError("密码长度为6至30之间");
                    return;
                }
                password = newPassword.getText().toString();
            } else {
                password = SharedPreferenceUtil.getInstance().getPassword();
            }
            final String userName;
            if (TextUtil.isEmpty(settingUsername)) {
                settingUsername.setError("用户名不能为空");
            }else {
                userName = settingUsername.getText().toString();
                if (!userName.equals(SharedPreferenceUtil.getInstance().getUserName())) {
                    Call<ResponseStatus> call = BaseService.getUserService().checkName(userName);
                    call.enqueue(new Callback<ResponseStatus>() {
                        @Override
                        public void onResponse(Response<ResponseStatus> response, Retrofit retrofit) {
                            if (response.body()!=null && response.body().getStatus() == 0) {
                                isNameUseful = true;
                                final String signature = settingSignature.getText().toString();
                                Call<ResponseStatus> statusCall = BaseService.getUserService().setInfo(
                                        SharedPreferenceUtil.getInstance().getToken(),
                                        userName,
                                        SharedPreferenceUtil.getInstance().getImg_id(),
                                        signature);
                                statusCall.enqueue(new Callback<ResponseStatus>() {
                                    @Override
                                    public void onResponse(Response<ResponseStatus> response, Retrofit retrofit) {
                                        if (response.body()!=null && response.body().getStatus() == 0) {
                                            SharedPreferenceUtil.getInstance().setUserName(userName);
                                            SharedPreferenceUtil.getInstance().setPassword(password);
                                            SharedPreferenceUtil.getInstance().setSignature(signature);
                                            Toast.makeText(getApplicationContext(), "更改成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Throwable t) {

                                    }
                                });
                            } else {
                                isNameUseful = false;
                                settingUsername.setError("用户名不可用");
                            }
                        }
                        @Override
                        public void onFailure(Throwable t) {
                            System.out.println(t.toString());
                        }
                    });
                }
            }

        }
    };

    private boolean isLinearShow() {
        if (passwordLinear.getVisibility() == View.GONE) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.SELECT_PIC:
//                    if (data != null && data.getData() != null) {
                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        createTmpFile();
                        cropPhoto(data.getData());
                    } else {
                        Toast.makeText(mContext, "图片写入失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.CROP_PIC:
                    try {
                        createTmpFile();
                        Bundle bundle = data.getExtras();
                        Bitmap imageBitmap = bundle.getParcelable("data");
                        convertBitmap2File(imageBitmap, tempFile);
                        settingImage.setImageBitmap(imageBitmap);
                        RequestBody body = RequestBody.create(
                                MediaType.parse("image/*"), tempFile);
                        Call<ImageLoadResponse> call = BaseService.getUserService().uploadImage(body);
                        call.enqueue(new Callback<ImageLoadResponse>() {
                            @Override
                            public void onResponse(Response<ImageLoadResponse> response, Retrofit retrofit) {
                                if (response.body() != null && response.body().getStatus() == 0) {
                                    SharedPreferenceUtil.getInstance().setImageId(response.body().getImg_id());
                                    isUserPhotoSelected = false;
                                } else {
                                    Toast.makeText(getApplicationContext(), "图片上传失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                isUserPhotoSelected = true;
                                Toast.makeText(getApplicationContext(), "图片上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createTmpFile(){

        if (tempFile!= null)
            return;
        tempFile = new File(Environment
                .getExternalStorageDirectory(), Constants.IMAGEFILE);
        if (tempFile.exists()){
            tempFile.delete();
            Log.d("tempfile", "temp file exit!");
        }
    }

    //生成文件
    public static boolean convertBitmap2File(Bitmap bitmap,File file){

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constants.CROP_PIC);
    }
}
