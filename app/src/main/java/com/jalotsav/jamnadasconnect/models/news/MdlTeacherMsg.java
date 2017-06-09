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

package com.jalotsav.jamnadasconnect.models.news;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.jamnadasconnect.common.AppConstants;

/**
 * Created by Jalotsav on 6/8/2017.
 */

public class MdlTeacherMsg implements AppConstants {

    @SerializedName(KEY_TM_ID)
    int tmId;
    @SerializedName(KEY_TM_SUBJECT)
    String tmSubject;
    @SerializedName(KEY_TM_MODULE)
    String tmModule;
    @SerializedName(KEY_TM_MESSAGE)
    String tmMessage;
    @SerializedName(KEY_TM_IMAGE)
    String tmImage;

    public int getTmId() {
        return tmId;
    }

    public void setTmId(int tmId) {
        this.tmId = tmId;
    }

    public String getTmSubject() {
        return tmSubject;
    }

    public void setTmSubject(String tmSubject) {
        this.tmSubject = tmSubject;
    }

    public String getTmModule() {
        return tmModule;
    }

    public void setTmModule(String tmModule) {
        this.tmModule = tmModule;
    }

    public String getTmMessage() {
        return tmMessage;
    }

    public void setTmMessage(String tmMessage) {
        this.tmMessage = tmMessage;
    }

    public String getTmImage() {
        return tmImage;
    }

    public void setTmImage(String tmImage) {
        this.tmImage = tmImage;
    }
}
