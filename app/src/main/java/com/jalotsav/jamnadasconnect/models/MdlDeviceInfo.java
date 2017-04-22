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

/**
 * Created by Jalotsav on 4/12/2017.
 */

public class MdlDeviceInfo implements AppConstants {

    @SerializedName(KEY_DEVICE_ID)
    String deviceId;
    @SerializedName(KEY_PUSH_NOTIFICATION_ID)
    String pushNotificationId;
    @SerializedName(KEY_OS_VERSION)
    String osVersion;
    @SerializedName(KEY_DEVICE_TYPE)
    String deviceType;

    public MdlDeviceInfo(String deviceId, String pushNotificationId, String osVersion, String deviceType) {
        this.deviceId = deviceId;
        this.pushNotificationId = pushNotificationId;
        this.osVersion = osVersion;
        this.deviceType = deviceType;
    }
}
