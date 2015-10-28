package org.sssta.zeroind.service;

import org.sssta.zeroind.Constants;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by 林韬 on 2015/10/7.
 */
public class BaseService {
    private static Retrofit mRetrofit;
    private static UserService mUserService;

    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public static UserService getUserService() {
        if (mUserService == null) {
            mUserService = getRetrofit().create(UserService.class);
        }
        return mUserService;
    }

}
