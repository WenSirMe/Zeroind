package org.sssta.zeroind.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import org.sssta.zeroind.Constants;
import org.sssta.zeroind.R;
import org.sssta.zeroind.fragment.LoginFragment;
import org.sssta.zeroind.fragment.RegisterFragment;
import org.sssta.zeroind.model.User;
import org.sssta.zeroind.service.BaseService;
import org.sssta.zeroind.service.response.ImageLoadResponse;
import org.sssta.zeroind.service.response.InfoResponse;
import org.sssta.zeroind.ui.CircleImageView;
import org.sssta.zeroind.ui.SlidingTabLayout;
import org.sssta.zeroind.util.SharedPreferenceUtil;
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

//
public class RegisterActivity extends AppCompatActivity implements RegisterFragment.RegisterListener{
    @Bind(R.id.lg_slidingTablayout)
    SlidingTabLayout mSlidingTablayout;
    @Bind(R.id.lg_viewPager)
    ViewPager mViewPager;
    @Bind(R.id.commit_login_register)
    ImageButton commitLG;
    @Bind(R.id.lg_user_image)
    CircleImageView mUserImage;

    private final int LOGIN_TAG = 0,REGISTER_TAG = 1;
    private int currentTag = 0;
    //
//
//    String[] genders = {"男", "女"};
//
//
    private Bitmap imageBitmap;
    private File tempFile;
    private boolean isUserPhotoSelected = false;
    private Context mContext;
    String imageLoad = null;

    Fragment[] fragments = new Fragment[2];

    private boolean isLoading = false;
    private String[] titles = new String[] {"登录","注册"};

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
        init();
    }

    private void init() {
        fragments[0] = LoginFragment.newInstance(mContext);
        fragments[1] = RegisterFragment.newInstance(mContext);
        mUserImage.setOnClickListener(myImageViewClick);
        commitLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTag == LOGIN_TAG) {
                    String userName = ((LoginFragment) fragments[LOGIN_TAG]).getUserName();
                    String password = ((LoginFragment) fragments[LOGIN_TAG]).getPassword();
                    if (userName != null && password != null && !isLoading) {
                        login(userName, password);
                        isLoading = true;
                    }
                } else {
                    String userName = ((RegisterFragment) fragments[REGISTER_TAG]).getUserName();
                    String password = ((RegisterFragment) fragments[REGISTER_TAG]).getPassword();
                    String sex = ((RegisterFragment) fragments[REGISTER_TAG]).getSex();
                    if (userName != null && password != null && !isLoading) {
                        register(userName, password, sex);
                        isLoading = true;
                    }
                }
            }
        });
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragments));
        mSlidingTablayout.setCustomTabView(R.layout.collection_tab_indicator, 0);
        mSlidingTablayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });
        mSlidingTablayout.setElevation(10f);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                isLoading = false;
                currentTag = position;
                if (currentTag == LOGIN_TAG) {
                    mUserImage.setClickable(false);
                    isUserPhotoSelected = false;
                    Picasso.with(mContext).load(Constants.BASE_IMAGE_LOAD +
                            SharedPreferenceUtil.getInstance().getImg_id()
                            + Constants.PHOTO_TYPE)
                            .error(R.drawable.ehhe).into(mUserImage);

                } else {
                    mUserImage.setClickable(true);
                    Picasso.with(mContext).load(R.drawable.ehhe).into(mUserImage);
                }
                Log.d("position", position + "");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("position1", position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("position2", state + "");
            }
        });
        mSlidingTablayout.setViewPager(mViewPager);
    }

    //
//    @Override
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
                        imageBitmap = bundle.getParcelable("data");
                        convertBitmap2File(imageBitmap, tempFile);
                        mUserImage.setImageBitmap(imageBitmap);
                        isUserPhotoSelected = true;
                        RequestBody body = RequestBody.create(
                                MediaType.parse("image/*"), tempFile);
                        Call<ImageLoadResponse> call = BaseService.getUserService().uploadImage(body);
                        call.enqueue(new Callback<ImageLoadResponse>() {
                            @Override
                            public void onResponse(Response<ImageLoadResponse> response, Retrofit retrofit) {

                                if (response.body() != null && response.body().getStatus() == 0) {
                                    imageLoad = response.body().getImg_id();
                                    SharedPreferenceUtil.getInstance().setImageId(imageLoad);
                                } else {
                                    imageLoad = null;
                                    Toast.makeText(mContext, "头像上传失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("iamgeupdate", t.toString());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//

    /**
     * 裁剪图片[待后期更改
     *
     * @param uri
     */
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

    /**
     * 头像的点击事件
     */
    private View.OnClickListener myImageViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent getImageIntent = new Intent();
            getImageIntent.setType("image/*");
            getImageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(getImageIntent, Constants.SELECT_PIC);
        }
    };

    //
    @Override
    protected void onDestroy() {
        if (tempFile != null) {
            tempFile.delete();
        }
        super.onDestroy();
    }

    private void createTmpFile() {

        if (tempFile != null)
            return;
        tempFile = new File(Environment
                .getExternalStorageDirectory(), Constants.IMAGEFILE);
        if (tempFile.exists()) {
            tempFile.delete();
            Log.d("tempfile", "temp file exit!");
        }
    }

    //
    //生成文件
    public static boolean convertBitmap2File(Bitmap bitmap, File file) {

        try {

            if (!file.exists()) {
                file.createNewFile();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
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

    public void login(String userName, final String passWord) {
        Call<InfoResponse> call = BaseService.getUserService().login(userName,passWord);
        call.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Response<InfoResponse> response, Retrofit retrofit) {
                if (response.body()!=null && response.body().getStatus() == 0) {
                    InfoResponse infoResponse = response.body();
                    User user = infoResponse.getUser();
                    user.setPassword(passWord);
                    SharedPreferenceUtil.getInstance().setUser(user);
                    SharedPreferenceUtil.getInstance().setToken(infoResponse.getToken());
                    Intent intent = new Intent(mContext,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    isLoading = false;
                    Toast.makeText(mContext, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                isLoading =false;
                Toast.makeText(mContext, "error:登陆失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void register(String userName, final String password, String sex) {
        Call<InfoResponse> call = BaseService.getUserService().register(userName,password,sex,imageLoad);
        call.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Response<InfoResponse> response, Retrofit retrofit) {
                InfoResponse infoResponse = response.body();
                if (infoResponse != null && infoResponse.getStatus() == 0) {
                    User user = infoResponse.getUser();
                    user.setPassword(password);
                    SharedPreferenceUtil.getInstance().setUser(user);
                    SharedPreferenceUtil.getInstance().setToken(infoResponse.getToken());
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    SharedPreferenceUtil.getInstance().setToken(response.body().getToken());
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "error 注册成功", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments;

        public MyViewPagerAdapter(FragmentManager fm, Fragment[] fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }


        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }
//
}
