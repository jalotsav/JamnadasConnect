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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.models.MdlDeviceInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    /**
     *  Show/Cancel Toast
     **/
    private static Toast TOAST = null;

    public static void showToastSingle(Context context, String message, int toastLength) {

        if(TOAST != null) TOAST.cancel();
        switch (toastLength) {
            case Toast.LENGTH_SHORT:

                TOAST = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                TOAST.show();
                break;
            case Toast.LENGTH_LONG:

                TOAST = Toast.makeText(context, message, Toast.LENGTH_LONG);
                TOAST.show();
                break;
            default:

                TOAST = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                TOAST.show();
                break;
        }
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

    /***
     * Get Current TimeStamp
     * ***/
    public static String getCurrentTimestamp() {

        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /***
     * Common Material Primary colors array
     * ***/
    public static ArrayList<Integer> getPrimaryColorArray(Context context) {

        ArrayList<Integer> arrylst_prmrycolor = new ArrayList<Integer>();
        try {
            arrylst_prmrycolor.add(ContextCompat.getColor(context, R.color.colorPrimaryTeal));
            arrylst_prmrycolor.add(ContextCompat.getColor(context, R.color.colorPrimaryBlue));
            arrylst_prmrycolor.add(ContextCompat.getColor(context, R.color.colorPrimaryRed));
            arrylst_prmrycolor.add(ContextCompat.getColor(context, R.color.colorPrimaryPink));
            arrylst_prmrycolor.add(ContextCompat.getColor(context, R.color.colorPrimaryPurple));
            arrylst_prmrycolor.add(ContextCompat.getColor(context, R.color.colorPrimaryIndigo));
            arrylst_prmrycolor.add(ContextCompat.getColor(context, R.color.colorPrimaryDarkAmber));
            arrylst_prmrycolor.add(ContextCompat.getColor(context, R.color.colorPrimaryBlueGrey));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return arrylst_prmrycolor;
    }

    /***
     * Convert image to Base64 from file path
     * ***/
    public static String convertImageToBase64(String path) {

        Bitmap btmp = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        btmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();

        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }

    /***
     * Convert image to Base64 from file path
     * ***/
    public static String convertAudioToBase64(String path) {

        byte[] byteArrayImage = new byte[0];

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(new File(path));

            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);

            byteArrayImage = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }

    /***
     * Get all chunks from Base64
     * ***/
    public static List<String> getChunkFromBase64(String base64) {

        List<String> arrylstChunks = new ArrayList<>();
        int index = 0;
        while(index < base64.length()) {
            arrylstChunks.add(base64.substring(index, Math.min(index + AppConstants.CHUNK_SIZE, base64.length())));
            index += AppConstants.CHUNK_SIZE;
        }

        return arrylstChunks;
    }

    /***
     * Delete all inner directories and files of given path
     * ***/
    public static void deleteFilesRecursive(String strPath) {

        File fileOrDirectory = new File(strPath);

        if (fileOrDirectory.isDirectory()){
            for (File child : fileOrDirectory.listFiles())
                deleteFilesRecursive(child.getPath());
            fileOrDirectory.delete();
        }else
            fileOrDirectory.delete();
    }
}
