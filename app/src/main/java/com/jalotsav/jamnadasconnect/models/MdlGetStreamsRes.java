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

package com.jalotsav.jamnadasconnect.models;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.jamnadasconnect.common.AppConstants;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 5/16/2017.
 */

public class MdlGetStreamsRes implements AppConstants {

    @SerializedName(KEY_SUCCESS_CAPS)
    String success;
    @SerializedName(KEY_MESSAGE_CAPS)
    String message;
    @SerializedName(KEY_STREAM)
    ArrayList<String> arrylstStream;

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

    public ArrayList<String> getArrylstStream() {
        return arrylstStream;
    }

    public void setArrylstStream(ArrayList<String> arrylstStream) {
        this.arrylstStream = arrylstStream;
    }
}
