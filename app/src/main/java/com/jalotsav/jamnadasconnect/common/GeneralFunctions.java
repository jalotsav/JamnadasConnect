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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jalotsav.jamnadasconnect.models.MdlDeviceInfo;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jalotsav on 5/4/17.
 */

public class GeneralFunctions {

    /***
     * Check Internet Connection
     * Mobile device is connect with Internet or not?
     * ***/
    public static boolean isNetConnected(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //This for Wifi.
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected())
            return true;

        //This for Mobile Network.
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected())
            return true;

        //This for Return true else false for Current status.
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            return true;

        return false;
    }

    /***
     * Open native call dialer with given number
     * ***/
    public static void openDialerToCall(Context context, String mobileNo) {

        try {
            context.startActivity(
                    new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + mobileNo))
            );
        } catch (Exception e) {e.printStackTrace();}
    }

    /***
     * Check Email Address Format is valid or not
     * ***/
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /***
     * Check Mobile Format is valid or not
     * ***/
    public static boolean isValidMobile(String target) {

//        String mobile = "^\\(?([0-9]{3})\\)?[-.]?([0-9]{3})[-.]?([0-9]{4})$";
        String mobile = "^[789]\\d{9}$";
        Matcher matcherEmail = Pattern.compile(mobile).matcher(target);
        return matcherEmail.matches();
    }

    /***
     * Check Email Format is valid or not
     * ***/
    public static boolean isValidEmail(String str) {

        String reg_Email = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcherEmail = Pattern.compile(reg_Email).matcher(str);
        return matcherEmail.matches();
    }

    public static String getDeviceInfo(Context context) {

        String pushNotfctnToken = FirebaseInstanceId.getInstance().getToken();
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceOSVersion = Build.VERSION.RELEASE;
        String deviceType = "Android";
//        String deviceModel = Build.BRAND + Build.MODEL;
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(new MdlDeviceInfo(deviceId, pushNotfctnToken, deviceOSVersion, deviceType));
    }

    /**
     *  Generate RANDOM Integer number between given Minimum and Maximum number
     *  @param minNumber
     *  @param maxNumber
     **/
    public static int generateRandomIntegerNumber(int minNumber, int maxNumber){

        Random rndmGnrtr = new Random();
        return rndmGnrtr.nextInt(maxNumber - minNumber + 1) + minNumber; // minNumber +  for avoid 0
    }

    /***
     * Convert Timestamp to dd-MMM-yyyy Date format
     * ***/
    public static String getDateFromTimestamp(long timestamp) {

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000);
        return DateFormat.format("dd-MMM-yyyy", cal).toString();
    }
}
