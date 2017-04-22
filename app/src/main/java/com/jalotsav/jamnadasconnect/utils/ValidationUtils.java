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

package com.jalotsav.jamnadasconnect.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;

/**
 * Created by Jalotsav on 4/15/2017.
 */

public class ValidationUtils {

    // Check validation for Mobile
    public static boolean validateMobile(Context context, TextInputLayout mTxtinptlyotMobile, TextInputEditText mTxtinptEtMobile) {

        if (mTxtinptEtMobile.getText().toString().trim().isEmpty()) {
            mTxtinptlyotMobile.setErrorEnabled(true);
            mTxtinptlyotMobile.setError(context.getString(R.string.entr_mobile_sml));
            requestFocus(context, mTxtinptEtMobile);
            return false;
        } else if (!GeneralFunctions.isValidMobile(mTxtinptEtMobile.getText().toString().trim())) {
            mTxtinptlyotMobile.setErrorEnabled(true);
            mTxtinptlyotMobile.setError(context.getString(R.string.invalid_mobile));
            requestFocus(context, mTxtinptEtMobile);
            return false;
        } else {
            mTxtinptlyotMobile.setError(null);
            mTxtinptlyotMobile.setErrorEnabled(false);
            return true;
        }
    }

    // Check validation for Password
    public static boolean validatePassword(Context context, TextInputLayout mTxtinptlyotPaswrd, TextInputEditText mTxtinptEtPaswrd) {

        if (mTxtinptEtPaswrd.getText().toString().trim().isEmpty()) {
            mTxtinptlyotPaswrd.setErrorEnabled(true);
            mTxtinptlyotPaswrd.setError(context.getString(R.string.entr_password_sml));
            requestFocus(context, mTxtinptEtPaswrd);
            return false;
        } else if (mTxtinptEtPaswrd.getText().toString().trim().length() < 6) {
            mTxtinptlyotPaswrd.setErrorEnabled(true);
            mTxtinptlyotPaswrd.setError(context.getString(R.string.password_6chars_long));
            requestFocus(context, mTxtinptEtPaswrd);
            return false;
        } else {
            mTxtinptlyotPaswrd.setError(null);
            mTxtinptlyotPaswrd.setErrorEnabled(false);
            return true;
        }
    }

    // Check Empty validation for field
    public static boolean validateEmpty(Context context, TextInputLayout mTxtinptlyot, TextInputEditText mTxtinptEt, String errorMsg) {

        if (mTxtinptEt.getText().toString().trim().isEmpty()) {
            mTxtinptlyot.setErrorEnabled(true);
            mTxtinptlyot.setError(errorMsg);
            requestFocus(context, mTxtinptEt);
            return false;
        } else {
            mTxtinptlyot.setError(null);
            mTxtinptlyot.setErrorEnabled(false);
            return true;
        }
    }

    // Check validation for Email
    public static boolean validateEmailFormat(Context context, TextInputLayout mTxtinptlyotEmail, TextInputEditText mTxtinptEtEmail) {

        if (!GeneralFunctions.isValidEmail(mTxtinptEtEmail.getText().toString().trim())) {
            mTxtinptlyotEmail.setErrorEnabled(true);
            mTxtinptlyotEmail.setError(context.getString(R.string.invalid_email));
            requestFocus(context, mTxtinptEtEmail);
            return false;
        } else {
            mTxtinptlyotEmail.setError(null);
            mTxtinptlyotEmail.setErrorEnabled(false);
            return true;
        }
    }

    public static void requestFocus(Context context, View view) {
        if (view.requestFocus()) {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
