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

import com.jalotsav.jamnadasconnect.BuildConfig;
import com.jalotsav.jamnadasconnect.common.AppConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jalotsav on 4/12/2017.
 */

public class APIRetroBuilder {

    private static Retrofit OBJ_RETROFIT = null;

    public static Retrofit getRetroBuilder() {

        if (OBJ_RETROFIT == null) {

            if (BuildConfig.BUILD_TYPE.equals(AppConstants.DEBUG_BUILD_TYPE)) {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                clientBuilder.addInterceptor(loggingInterceptor); // print OkHTTP log
//                clientBuilder.connectTimeout(2, TimeUnit.MINUTES);
//                clientBuilder.readTimeout(2, TimeUnit.MINUTES);

                OBJ_RETROFIT = new Retrofit.Builder()
                        .baseUrl(AppConstants.API_ROOT_URL)
                        .client(clientBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            } else {

                OBJ_RETROFIT = new Retrofit.Builder()
                        .baseUrl(AppConstants.API_ROOT_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return OBJ_RETROFIT;
    }
}
