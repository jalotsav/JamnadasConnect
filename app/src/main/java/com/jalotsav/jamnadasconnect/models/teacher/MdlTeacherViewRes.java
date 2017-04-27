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

package com.jalotsav.jamnadasconnect.models.teacher;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.jamnadasconnect.common.AppConstants;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 27/4/17.
 */

public class MdlTeacherViewRes implements AppConstants {

    @SerializedName(KEY_SUCCESS_CAPS)
    String success;
    @SerializedName(KEY_MESSAGE_CAPS)
    String message;
    @SerializedName(KEY_TEACHER_OTHER_DATA)
    ArrayList<MdlTeacherContactEductn> objMdlTeacherContactEductn;
    @SerializedName(KEY_INSTITUTE_DETAILS)
    ArrayList<MdlTeacherWork> objMdlTeacherWork;
    @SerializedName(KEY_TEACHER_BASIC_DATA)
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

    public ArrayList<MdlTeacherContactEductn> getObjMdlTeacherContactEductn() {
        return objMdlTeacherContactEductn;
    }

    public void setObjMdlTeacherContactEductn(ArrayList<MdlTeacherContactEductn> objMdlTeacherContactEductn) {
        this.objMdlTeacherContactEductn = objMdlTeacherContactEductn;
    }

    public ArrayList<MdlTeacherWork> getObjMdlTeacherWork() {
        return objMdlTeacherWork;
    }

    public void setObjMdlTeacherWork(ArrayList<MdlTeacherWork> objMdlTeacherWork) {
        this.objMdlTeacherWork = objMdlTeacherWork;
    }

    public ArrayList<MdlTeacherBasic> getObjMdlTeacherBasic() {
        return objMdlTeacherBasic;
    }

    public void setObjMdlTeacherBasic(ArrayList<MdlTeacherBasic> objMdlTeacherBasic) {
        this.objMdlTeacherBasic = objMdlTeacherBasic;
    }
}
