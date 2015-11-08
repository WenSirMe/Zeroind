package org.sssta.zeroind.service;

import com.squareup.okhttp.RequestBody;

import org.sssta.zeroind.service.response.ImageLoadResponse;
import org.sssta.zeroind.service.response.InfoResponse;
import org.sssta.zeroind.service.response.MessageResponse;
import org.sssta.zeroind.service.response.ResponseStatus;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by Heaven on 2015/11/1.
 */
public interface MessageService {
    @FormUrlEncoded
    @POST("unreadMsg.do")
    Call<MessageResponse> getUnReadMessage(@Field("token") String token);

    @FormUrlEncoded
    @POST("postMsg.do")
    Call<ResponseStatus> postMessage(
            @Field("token") String token,
            @Field("wind_direction") int wind_direction,
            @Field("content") String content,
            @Field("img_id") String img_id);

    @FormUrlEncoded
    @POST("replyMsg.do")
    Call<ResponseStatus> replyMessage(
            @Field("token") String token,
            @Field("id") int id,
            @Field("content") String content);

    @FormUrlEncoded
    @POST("forwardMsg.do")
    Call<ResponseStatus> forwardMessage(
            @Field("token") String token,
            @Field("id") int id);

    @FormUrlEncoded
    @POST("destroyMsg.do")
    Call<ResponseStatus> destroyMessage(
            @Field("token") String token,
            @Field("id") int id);

    @FormUrlEncoded
    @POST("getMsg.do")
    Call<ResponseStatus> getMessageDetail(
            @Field("token") String token,
            @Field("id") int id);


    @Multipart
    @POST("uploadImage.do")
    Call<ImageLoadResponse> uploadImage(
            @Part("file\"; filename=\"hello.png\" ") RequestBody file);
}
