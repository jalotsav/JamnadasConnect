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

package com.jalotsav.jamnadasconnect.models.login;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherBasic;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 4/12/2017.
 */

public class MdlLoginRes implements AppConstants {

    @SerializedName(KEY_SUCCESS_CAPS)
    String success;
    @SerializedName(KEY_MESSAGE_CAPS)
    String message;
    @SerializedName(KEY_TDK_ID)
    String tdk_id;
    @SerializedName(KEY_USER_ID)
    String user_id;
    @SerializedName(KEY_USER_DETAILS)
    ArrayList<MdlTeacherBasic> objMdlTeacherBasic;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTdk_id() {
        return tdk_id;
    }

    public void setTdk_id(String tdk_id) {
        this.tdk_id = tdk_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<MdlTeacherBasic> getObjMdlTeacherBasic() {
        return objMdlTeacherBasic;
    }

    public void setObjMdlTeacherBasic(ArrayList<MdlTeacherBasic> objMdlTeacherBasic) {
        this.objMdlTeacherBasic = objMdlTeacherBasic;
    }
}
