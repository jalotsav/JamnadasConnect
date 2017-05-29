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

package com.jalotsav.jamnadasconnect.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jalotsav.jamnadasconnect.ActvtyMain;
import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;

import java.util.Map;

/**
 * Created by Jalotsav on 28/4/17.
 */

public class FireMessagingService extends FirebaseMessagingService implements AppConstants {

    private static final String TAG = FireMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            LogHelper.printLog(LOGTYPE_INFO, TAG, "From: " + remoteMessage.getFrom());

            if(remoteMessage.getNotification() != null)
                LogHelper.printLog(LOGTYPE_INFO, TAG, "PushNotify Notification(): " + remoteMessage.getNotification().getBody());

            if(remoteMessage.getData().size() > 0)
                LogHelper.printLog(LOGTYPE_INFO, TAG, "PushNotify Data(): " + remoteMessage.getData());

            // Build notification
            sendNotification(remoteMessage.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(Map<String, String> messageBody) {

        String notifctnTitle = messageBody.get(KEY_PUSHNOTFCTN_TITLE);
        String notifctnMessage = messageBody.get(KEY_PUSHNOTFCTN_MESSAGE);
        String notifctnModuleId = messageBody.get(KEY_PUSHNOTFCTN_MODULE_ID);
        String notifctnModule = messageBody.get(KEY_PUSHNOTFCTN_MODULE);
        String notifctnImageUri = messageBody.get(KEY_PUSHNOTFCTN_IMAGE);

        NotificationCompat.Style notifctnStyle = null;

        if(!TextUtils.isEmpty(notifctnImageUri))
            notifctnStyle = new NotificationCompat.BigPictureStyle().setBigContentTitle(notifctnTitle).setSummaryText(notifctnMessage).bigPicture(GeneralFunctions.getBitmapFromUrl(notifctnImageUri));
        else
            notifctnStyle = new NotificationCompat.BigTextStyle().setBigContentTitle(notifctnTitle).bigText(notifctnMessage);

        Intent intent = new Intent(this, ActvtyMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_SPLASHACTIVITY, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(notifctnTitle)
                .setContentText(notifctnMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setStyle(notifctnStyle)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_BUILDER_ID_SINGLE, notificationBuilder.build());
    }
}
