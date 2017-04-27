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
    String API_TCHR_TEACHER_EDIT = "teacher/api_teacher_edit";
    String API_TCHR_TEACHER_VIEW = "teacher/api_teacher_view";
    String API_BOOKREQST_BOOKREQST_ADD = "book_request/api_book_request_add";

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
    String KEY_TEACHER_OTHER_DATA = "teacher_other_data";
    String KEY_INSTITUTE_DETAILS = "institute_details";
    String KEY_TEACHER_BASIC_DATA = "teacher_basic_data";
    String KEY_TD_EXPERIENCE = "td_experience";
    String KEY_TD_AREA_OF_INTEREST = "td_area_of_interest";
    String KEY_TD_EDUCATIONAL_QUALIFICATION = "td_educational_qualification";
    String KEY_TD_ACHIEVEMENTS = "td_achievements";
    String KEY_TD_BIRTH_DATE = "td_birth_date";
    String KEY_TD_ADDRESS_LINE_1 = "td_address_line_1";
    String KEY_TD_ADDRESS_LINE_2 = "td_address_line_2";
    String KEY_TD_CITY = "td_city";
    String KEY_TD_STATE = "td_state";
    String KEY_TD_COUNTRY = "td_country";
    String KEY_TD_PINCODE = "td_pincode";
    String KEY_BOOK_NAME = "book_name";
    String KEY_STREAM = "stream";
    String KEY_CLASS = "class";

    // Web-Service Values
    String VALUES_TRUE = "TRUE";
    String VALUES_FALSE = "FALSE";

    // Request Keys
    int REQUEST_VERFCTN_MOBILENO = 101;
    int REQUEST_APP_PERMISSION = 102;
}
