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

import android.util.Log;

import com.jalotsav.jamnadasconnect.BuildConfig;

/**
 * Created by Jalotsav on 4/6/2017.
 */

public class LogHelper implements AppConstants {

    private static boolean LOGGING_ENABLED = false;

    static {

        if (BuildConfig.BUILD_TYPE.equals(DEBUG_BUILD_TYPE))
            LOGGING_ENABLED = true;
    }

    public static void printLog(int logType, String logTag, String logMessage) {

        if (LOGGING_ENABLED) {

            switch (logType) {

                case LOGTYPE_VERBOSE:

                    if (Log.isLoggable(logTag, Log.VERBOSE))
                        Log.v(logTag, logMessage);
                    break;
                case LOGTYPE_DEBUG:

                    if (Log.isLoggable(logTag, Log.DEBUG))
                        Log.d(logTag, logMessage);
                    break;
                case LOGTYPE_INFO:

                    Log.i(logTag, logMessage);
                    break;
                case LOGTYPE_WARN:

                    Log.w(logTag, logMessage);
                    break;
                case LOGTYPE_ERROR:

                    Log.e(logTag, logMessage);
                    break;
                default:

                    Log.i(logTag, logMessage);
                    break;
            }
        }
    }
}
