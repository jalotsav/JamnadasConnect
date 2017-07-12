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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.jalotsav.jamnadasconnect.SignIn;

/**
 * Created by Jalotsav on 4/19/2017.
 */

public class UserSessionManager implements AppConstants {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    // All Shared Preferences Keys
    // Is User SignIn or not
    private static final String IS_USER_LOGIN = "isUserLoggedIn";

    // Constructor
    public UserSessionManager(Context context) {

        this.mContext = context;
        String PREFER_NAME = mContext.getPackageName() + "_shrdprfrnc";
        int PRIVATE_MODE = 0;
        pref = mContext.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    // Create login session
    // Get-Set UserId to SharedPreferences
    public int getUserId() {

        return pref.getInt(KEY_USER_ID, 0);
    }

    public void setUserId(int userId) {

        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.commit();
    }

    // Get-Set APIRootUrl to SharedPreferences
    public String getApiRootUrl() {

        return pref.getString(KEY_API_ROOT_URL, "");
    }

    public void setApiRootUrl(String apiRootUrl) {

        editor.putString(KEY_API_ROOT_URL, apiRootUrl);
        editor.commit();
    }

    // Get-Set FirstName to SharedPreferences
    public String getFirstName() {

        return pref.getString(KEY_FIRST_NAME, "");
    }

    public void setFirstName(String firstName) {

        editor.putString(KEY_FIRST_NAME, firstName);
        editor.commit();
    }

    // Get-Set LastName to SharedPreferences
    public String getLastName() {

        return pref.getString(KEY_LAST_NAME, "");
    }

    public void setLastName(String lastName) {

        editor.putString(KEY_LAST_NAME, lastName);
        editor.commit();
    }

    // Get-Set Email to SharedPreferences
    public String getMobile() {

        return pref.getString(KEY_MOBILE, "");
    }

    public void setMobile(String mobile) {

        editor.putString(KEY_MOBILE, mobile);
        editor.commit();
    }

    // Get-Set Email to SharedPreferences
    public String getEmail() {

        return pref.getString(KEY_EMAIL, "");
    }

    public void setEmail(String email) {

        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    // Get-Set ProfilePicturePath to SharedPreferences
    public String getProfilePicturePath() {

        return pref.getString(KEY_PROFILE_PICTURE, "");
    }

    public void setProfilePicturePath(String profilePicturePath) {

        editor.putString(KEY_PROFILE_PICTURE, profilePicturePath);
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {

        if (!this.isUserLoggedIn()) {

            Intent i = new Intent(mContext, SignIn.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContext.startActivity(i);
//            ((Activity)_context).overridePendingTransition(0,0);

            return false;
        }
        return true;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to SignIn Activity
        Intent i = new Intent(mContext, SignIn.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(i);
    }

    // Check for login
    private boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
