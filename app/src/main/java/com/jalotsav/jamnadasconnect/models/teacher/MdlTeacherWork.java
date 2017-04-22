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

/**
 * Created by Jalotsav on 4/19/2017.
 */

public class MdlTeacherWork implements AppConstants {

    @SerializedName(KEY_TI_ID)
    int tiId;
    @SerializedName(KEY_TI_INSTITUTE_TITLE)
    String tiInstituteTitle;
    @SerializedName(KEY_TIC_STREAM)
    String ticStream;
    @SerializedName(KEY_TIC_STD)
    String ticStd;
    @SerializedName(KEY_TIC_SUBJECT)
    String ticSubject;

    public int getTiId() {
        return tiId;
    }

    public void setTiId(int tiId) {
        this.tiId = tiId;
    }

    public String getTiInstituteTitle() {
        return tiInstituteTitle;
    }

    public void setTiInstituteTitle(String tiInstituteTitle) {
        this.tiInstituteTitle = tiInstituteTitle;
    }

    public String getTicStream() {
        return ticStream;
    }

    public void setTicStream(String ticStream) {
        this.ticStream = ticStream;
    }

    public String getTicStd() {
        return ticStd;
    }

    public void setTicStd(String ticStd) {
        this.ticStd = ticStd;
    }

    public String getTicSubject() {
        return ticSubject;
    }

    public void setTicSubject(String ticSubject) {
        this.ticSubject = ticSubject;
    }
}
