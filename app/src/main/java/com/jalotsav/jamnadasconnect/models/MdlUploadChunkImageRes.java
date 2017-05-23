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
 * Created by Jalotsav on 5/23/2017.
 */

public class MdlUploadChunkImageRes implements AppConstants {

    @SerializedName(KEY_FILE_NAME)
    String fileName;
    @SerializedName(KEY_OUTPUT_URL)
    String outputUrl;
    @SerializedName(KEY_UPLOAD_FILE_NAME)
    String uploadFileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOutputUrl() {
        return outputUrl;
    }

    public void setOutputUrl(String outputUrl) {
        this.outputUrl = outputUrl;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
}
