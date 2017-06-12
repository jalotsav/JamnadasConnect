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
import com.jalotsav.jamnadasconnect.models.news.MdlTeacherMsgListRes;
import com.jalotsav.jamnadasconnect.models.news.MdlTeacherMsgViewRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Jalotsav on 6/9/2017.
 */

public interface APINews {

    @FormUrlEncoded
    @POST(AppConstants.API_TEACHRMSG_TEACHRMSG_LIST)
    Call<MdlTeacherMsgListRes> callTeacherMsgList(@Field(AppConstants.KEY_DEVICE_INFO) String deviceInfo,
                                         @Field(AppConstants.KEY_USER_ID) int userId,
                                         @Field(AppConstants.KEY_LAST_ID) int newsMsgLastId,
                                         @Field(AppConstants.KEY_KEYWORD) String keyword);

    @FormUrlEncoded
    @POST(AppConstants.API_TEACHRMSG_TEACHRMSG_VIEW)
    Call<MdlTeacherMsgViewRes> callTeacherMsgView(@Field(AppConstants.KEY_DEVICE_INFO) String deviceInfo,
                                                  @Field(AppConstants.KEY_USER_ID) int userId,
                                                  @Field(AppConstants.KEY_TM_ID) int tmId);
}
