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

import android.os.Environment;

import java.io.File;

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
    String API_GNRL_LOGIN = "general/login";
    String API_GNRL_REGISTRATION = "general/registration";
    String API_GNRL_UPLOAD_CHUNK_IMAGE = "general/api_upload_chunk_image";
    String API_GNRL_GET_STREAM_lIST = "general/get_stream_list";
    String API_GNRL_GET_STANDARD_LIST = "general/get_standard_list";
    String API_TCHR_TEACHER_EDIT = "teacher/api_teacher_edit";
    String API_TCHR_TEACHER_VIEW = "teacher/api_teacher_view";
    String API_TCHR_CHECK_TEACHER_DATA_AVALBLTY = "teacher/api_check_teacher_data_availability";
    String API_BOOKREQST_BOOKREQST_ADD = "book_request/api_book_request_add";
    String API_BOOKCORCTN_BOOKCORCTN_ADD = "book_correction/api_book_correction_add";
    String API_TEACHRSUGSTN_TEACHRSUGSTN_ADD = "teacher_suggestion/api_teacher_suggestion_add";
    String API_TEACHRMSG_TEACHRMSG_LIST = "teacher_message/api_teacher_message_list";
    String API_TEACHRMSG_TEACHRMSG_VIEW = "teacher_message/api_teacher_message_view";

    // Web-Service Keys
    String KEY_API_ROOT_URL = "api_root_url";
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
    String KEY_USER_DETAILS = "user_details";
    String KEY_FIRST_NAME = "first_name";
    String KEY_MIDDLE_NAME = "middle_name";
    String KEY_LAST_NAME = "last_name";
    String KEY_MOBILE = "mobile";
    String KEY_PROFILE_PICTURE = "profile_picture";
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
    String KEY_PROFILE = "profile";
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
    String KEY_MISSING_PARAM = "missing_param";
    String KEY_STANDARD = "standard";
    String KEY_MODULE = "module";
    String KEY_CHUNK = "chunk";
    String KEY_FILE_NAME = "file_name";
    String KEY_LAST_CHUNK = "last_chunk";
    String KEY_EXT = "ext";
    String KEY_OUTPUT_URL = "output_url";
    String KEY_UPLOAD_FILE_NAME = "upload_file_name";
    String KEY_ATTACHMENTS = "attachments";
    String KEY_SUBJECT = "subject";
    String KEY_DESCRIPTION = "description";
    String KEY_LAST_ID = "last_id";
    String KEY_KEYWORD = "keyword";
    String KEY_TEACHER_MESSAGE_LIST = "teacher_message_list";
    String KEY_TM_ID = "tm_id";
    String KEY_TM_SUBJECT = "tm_subject";
    String KEY_TM_MODULE = "tm_module";
    String KEY_TM_MESSAGE = "tm_message";
    String KEY_TM_IMAGE = "tm_image";
    String KEY_TEACHER_MESSAGE_DATA = "teacher_message_data";

    // Web-Service Values
    String VALUES_TRUE = "TRUE";
    String VALUES_FALSE = "FALSE";

    // PutExtra Keys
    String PUT_EXTRA_COME_FROM = "comeFrom";
    String PUT_EXTRA_IMAGE_PATH = "imagePath";
    String PUT_EXTRA_IMAGE_PATH_TYPE = "imagePathType";
    String PUT_EXTRA_NAVDRWER_POSTN = "navdrawerPosition";
    String PUT_EXTRA_TM_ID = "tMessageId";

    // PutExtra Values
    int IMAGE_PATH_TYPE_FILE = 11;
    int IMAGE_PATH_TYPE_SERVER = 12;

    // Remote Config keys
    String FIRECONFIG_API_ROOT_URL = "api_root_url";

    // Navigation Drawer MenuItem position check for direct open that fragment
    int NAVDRWER_DASHBOARD = 21;
    int NAVDRWER_BOOK_REQUEST = 22;
    int NAVDRWER_NEWS = 23;

    // Come From Values
    int COME_FROM_SPECIMEN_COPY = 31;

    // Request Keys
    int REQUEST_VERFCTN_MOBILENO = 101;
    int REQUEST_APP_PERMISSION = 102;
    int REQUEST_SPLASH_ACTIVITY = 103;
    int REQUEST_PICK_IMAGE = 104;
    int REQUEST_OPEN_CAMERA = 105;

    // Chunk Image
    int CHUNK_SIZE = 1400000;

    // Chunk Upload Module
    String CHUNK_MODULE_CORRECTION = "Correction";
    String CHUNK_MODULE_SUGGESTION = "Suggestion";
    String CHUNK_MODULE_PROFILE = "Profile";

    // External storage path directory
    String JAMNADAS_CONNECT = "Jamnadas Connect";
    String DOT_TEMP = ".temp";
    String AUDIO_SML = "Audio";
    String EXTRNL_STORAGE_PATH_STRING = Environment.getExternalStorageDirectory().getAbsolutePath();
//    File PATH_EXTRNL_STORAGE = new File(EXTRNL_STORAGE_PATH_STRING);
    File PATH_TEMP_AUDIO = new File(EXTRNL_STORAGE_PATH_STRING + File.separator + JAMNADAS_CONNECT
        + File.separator + DOT_TEMP + File.separator + AUDIO_SML);

    // RecyclerView View Type
    int RECYCLRVIEW_TYPE_DATA = 0;
    int RECYCLRVIEW_TYPE_LOADING = 1;

    // Others
    String ID_SML = "id";
    String BASE64_PART = "base64Part";
    String EXTENSION_JPG = "jpg";
    String EXTENSION_3GPP = "3gpp";
    String EXTENSION_MP3 = "mp3";

    // Push Notification
    int NOTIFICATION_BUILDER_ID_SINGLE = 1;

    // Push Notification Keys
    String KEY_PUSHNOTFCTN_TITLE = "title";
    String KEY_PUSHNOTFCTN_MESSAGE = "message";
    String KEY_PUSHNOTFCTN_MODULE_ID = "module_id";
    String KEY_PUSHNOTFCTN_MODULE = "module";
    String KEY_PUSHNOTFCTN_IMAGE = "image";
    String KEY_PUSHNOTFCTN_MODULE_BOOK_REQUEST = "BOOK_REQUEST";
//    String KEY_PUSHNOTFCTN_MODULE_BOOK_ORDER = "BOOK_ORDER";
    String KEY_PUSHNOTFCTN_MODULE_NEWS = "NEWS";
}
