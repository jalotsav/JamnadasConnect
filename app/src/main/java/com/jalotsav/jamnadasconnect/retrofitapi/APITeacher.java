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
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherEditRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Jalotsav on 4/19/2017.
 */

public interface APITeacher {

    @FormUrlEncoded
    @POST(AppConstants.API_TCHR_TEACHER_EDIT)
    Call<MdlTeacherEditRes> callTeacherEdit(@Field(AppConstants.KEY_DEVICE_INFO) String deviceInfo,
                                            @Field(AppConstants.KEY_USER_ID) int userId,
                                            @Field(AppConstants.KEY_FIRST_NAME) String firstName,
                                            @Field(AppConstants.KEY_MIDDLE_NAME) String middleName,
                                            @Field(AppConstants.KEY_LAST_NAME) String lastName,
                                            @Field(AppConstants.KEY_EMAIL) String email,
                                            @Field(AppConstants.KEY_MOBILE) String mobile,
                                            @Field(AppConstants.KEY_BIRTHDATE) String birthdate,
                                            @Field(AppConstants.KEY_EXPERIENCE) String experience,
                                            @Field(AppConstants.KEY_AREA_OF_INTEREST) String areaOfInterest,
                                            @Field(AppConstants.KEY_EDUCATIONAL_QUALIFICATION) String educatnlQualfctn,
                                            @Field(AppConstants.KEY_ACHIEVEMENTS) String achievements,
                                            @Field(AppConstants.KEY_ADDRESS_LINE_1) String addressLine1,
                                            @Field(AppConstants.KEY_ADDRESS_LINE_2) String addressLine2,
                                            @Field(AppConstants.KEY_CITY) String city,
                                            @Field(AppConstants.KEY_STATE) String state,
                                            @Field(AppConstants.KEY_COUNTRY) String country,
                                            @Field(AppConstants.KEY_PINCODE) String pincode,
                                            @Field(AppConstants.KEY_WORK_DELETE_ID) String workDeleteId,
                                            @Field(AppConstants.KEY_WORK_JSON_DATA) String workJsonData);
}
