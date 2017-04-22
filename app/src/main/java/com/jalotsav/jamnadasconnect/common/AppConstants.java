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

package com.jalotsav.jamnadasconnect.common;

/**
 * Created by Jalotsav on 5/4/17.
 */

public interface AppConstants {

    // Build Type
    String DEBUG_BUILD_TYPE = "debug";

    // Log Type
    int LOGTYPE_VERBOSE = 1;
    int LOGTYPE_DEBUG = 2;
    int LOGTYPE_INFO = 3;
    int LOGTYPE_WARN = 4;
    int LOGTYPE_ERROR = 5;

    // API URLs
    String API_ROOT_URL = "http://www.jamnadas.com/c-jamnadas-admin/api/";
    String API_GNRL_LOGIN = "general/login";
    String API_GNRL_REGISTRATION = "general/registration";
    String API_TCHR_TEACHER_EDIT= "teacher/api_teacher_edit";

    // Web-Service Keys
    String KEY_EMAIL_MOB = "email_mob";
    String KEY_PASSWORD = "password";
    String KEY_DEVICE_INFO = "device_info";
    String KEY_DEVICE_ID = "device_id";
    String KEY_PUSH_NOTIFICATION_ID = "push_notification_id";
    String KEY_OS_VERSION = "os_version";
    String KEY_DEVICE_TYPE = "device_type";
    String KEY_SUCCESS_CAPS = "SUCCESS";
    String KEY_MESSAGE_CAPS = "MESSAGE";
    String KEY_TDK_ID = "tdk_id";
    String KEY_USER_ID = "user_id";
    String KEY_FIRST_NAME = "first_name";
    String KEY_MIDDLE_NAME = "middle_name";
    String KEY_LAST_NAME = "last_name";
    String KEY_MOBILE = "mobile";
    String KEY_EMAIL = "email";
    String KEY_BIRTHDATE = "birthdate";
    String KEY_EXPERIENCE = "experience";
    String KEY_AREA_OF_INTEREST = "area_of_interest";
    String KEY_EDUCATIONAL_QUALIFICATION = "educational_qualification";
    String KEY_ACHIEVEMENTS = "achievements";
    String KEY_ADDRESS_LINE_1 = "address_line_1";
    String KEY_ADDRESS_LINE_2 = "address_line_2";
    String KEY_CITY = "city";
    String KEY_STATE = "state";
    String KEY_COUNTRY = "country";
    String KEY_PINCODE = "pincode";
    String KEY_WORK_DELETE_ID = "work_delete_id";
    String KEY_WORK_JSON_DATA = "work_json_data";
    String KEY_TI_ID = "ti_id";
    String KEY_TI_INSTITUTE_TITLE = "ti_institute_title";
    String KEY_TIC_STREAM = "tic_stream";
    String KEY_TIC_STD = "tic_std";
    String KEY_TIC_SUBJECT = "tic_subject";

    // Web-Service Values
    String VALUES_TRUE = "TRUE";
    String VALUES_FALSE = "FALSE";

    // Request Keys
    int REQUEST_VERFCTN_MOBILENO = 101;
    int REQUEST_APP_PERMISSION = 102;
}
