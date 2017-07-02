/*
 * Copyright (c) 2017 Jalotsav
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jalotsav.jamnadasconnect.retrofitapi;

import android.content.Context;

import com.jalotsav.jamnadasconnect.BuildConfig;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jalotsav on 4/12/2017.
 */

public class APIRetroBuilder {

    private static Retrofit OBJ_RETROFIT = null;

    public static Retrofit getRetroBuilder(Context context, boolean enableConnectTimeout) {

        if (OBJ_RETROFIT == null) {

            UserSessionManager session = new UserSessionManager(context);
            if (BuildConfig.BUILD_TYPE.equals(AppConstants.DEBUG_BUILD_TYPE)) {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                clientBuilder.addInterceptor(loggingInterceptor); // print OkHTTP log
                if(enableConnectTimeout) {
                    clientBuilder.connectTimeout(2, TimeUnit.MINUTES);
                    clientBuilder.readTimeout(2, TimeUnit.MINUTES);
                }

                OBJ_RETROFIT = new Retrofit.Builder()
                        .baseUrl(session.getApiRootUrl())
                        .client(clientBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            } else {

                OBJ_RETROFIT = new Retrofit.Builder()
                        .baseUrl(session.getApiRootUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return OBJ_RETROFIT;
    }
}
