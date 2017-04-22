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

import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.models.login.MdlLoginRes;
import com.jalotsav.jamnadasconnect.models.registration.MdlRegistrationRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Jalotsav on 4/12/2017.
 */

public interface APIGeneral {

    @FormUrlEncoded
    @POST(AppConstants.API_GNRL_LOGIN)
    Call<MdlLoginRes> callLogin(@Field(AppConstants.KEY_EMAIL_MOB) String emailMobile,
                                @Field(AppConstants.KEY_PASSWORD) String password,
                                @Field(AppConstants.KEY_DEVICE_INFO) String deviceInfo);

    @FormUrlEncoded
    @POST(AppConstants.API_GNRL_REGISTRATION)
    Call<MdlRegistrationRes> callRegistration(@Field(AppConstants.KEY_FIRST_NAME) String firstName,
                                              @Field(AppConstants.KEY_LAST_NAME) String lastName,
                                              @Field(AppConstants.KEY_MOBILE) String mobile,
                                              @Field(AppConstants.KEY_EMAIL) String email,
                                              @Field(AppConstants.KEY_PASSWORD) String password);
}
