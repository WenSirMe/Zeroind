package org.sssta.zeroind.service;

import org.sssta.zeroind.service.response.ImageLoadResponse;
import org.sssta.zeroind.service.response.InfoResponse;
import org.sssta.zeroind.service.response.RegisterResponse;
import org.sssta.zeroind.service.response.ResponseStatus;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by 林韬 on 2015/10/7.
 */
public interface UserService {

    @FormUrlEncoded
    @POST("register.do")
    Call<InfoResponse> register(
                    @Field("username")String username,
                    @Field("password")String password,
                    @Field("sex")String sex,
                    @Field("img_id")String img_id);

    @Multipart
    @POST("uploadImage.do")
    Call<ImageLoadResponse> uploadImage(
            @Part("file\"; filename=\"hello.png\" ")RequestBody file);

    @FormUrlEncoded
    @POST("checkName.do")
    Call<ResponseStatus> checkName(@Field("username")String username);

    @FormUrlEncoded
    @POST("setInfo.do")
    Call<ResponseStatus> setInfo(@Field("token") String token,
                                 @Field("username")String username,
                                 @Field("img_id") String iag_id,
                                 @Field("signature")String signature);


    @FormUrlEncoded
    @POST("getMyInfo.do")
    Call<InfoResponse> getInfo(@Field("token")String token);

    @FormUrlEncoded
    @POST("login.do")
    Call<InfoResponse> login(@Field("username")String username,
                             @Field("password")String password);

}
