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

package com.jalotsav.jamnadasconnect;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jalotsav on 4/20/2017.
 */

public class VerifyMobileNo extends AppCompatActivity {

    private static final String TAG = VerifyMobileNo.class.getSimpleName();

    @BindView(R.id.cordntrlyot_verifymobileno) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.tv_verifymobileno_mobileno) TextView mTvMobileNo;
    @BindView(R.id.prgrsbr_verifymobileno_autodetectmsg) ProgressBar mPrgrsbrAutoDetect;
    @BindView(R.id.appcmptbtn_verifymobileno_resendsms) AppCompatButton mAppcmptbtnResend;
    @BindView(R.id.txtinputlyot_verifymobileno_vrfctncode) TextInputLayout mTxtinptlyotVrfctnCode;
    @BindView(R.id.txtinptet_verifymobileno_vrfctncode) TextInputEditText mTxtinptEtVrfctnCode;

    @BindString(R.string.app_name) String mAppName;
    @BindString(R.string.no_detect_any_mobileno) String mNoDetectMobileNoMsg;
    @BindString(R.string.allow_permtn_sms) String mAllowSMSPermsnMsg;
    @BindString(R.string.sending_sms_fail) String mSendngSmsFailMsg;
    @BindString(R.string.entr_verfctn_code) String mEntrVrfctnCodeMsg;
    @BindString(R.string.invalid_verfctn_code) String mInvalidVrfctnCodeMsg;
    @BindString(R.string.auto_detect_sms_fail) String mAutoDetectFailMsg;

    IntentFilter mIntnFiltr;
    static String MOBILE_NO;
    static int OTP_CODE_GENRTD = 200692, OTP_CODE_SMSRECVD = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_verify_mobileno);
        ButterKnife.bind(this);

        MOBILE_NO = getIntent().getStringExtra(AppConstants.KEY_MOBILE);

        // Register BroadcastReceiver --> IncomingSmsReceiver
        mIntnFiltr = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(IncomingSmsReceiver, mIntnFiltr);

        if(!TextUtils.isEmpty(MOBILE_NO)) {

            mTvMobileNo.setText(MOBILE_NO);

            checkSMSPermission();
        } else {

            Toast.makeText(this, mNoDetectMobileNoMsg, Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        mTxtinptEtVrfctnCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionid, KeyEvent event) {

                if(actionid == EditorInfo.IME_ACTION_DONE)
                    matchOTPCode();

                return false;
            }
        });
    }

    // Check RunTime permission for Send, Receive, Read SMS
    private void checkSMSPermission() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!isCheckSelfPermission())
                requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                        AppConstants.REQUEST_APP_PERMISSION);
            else
                sendSMS();
        } else
            sendSMS();
    }

    // check permission is already asked or get status
    private boolean isCheckSelfPermission() {

        int selfPermsnSend = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int selfPermsnReceive = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int selfPermsnRead = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        return  selfPermsnSend == PackageManager.PERMISSION_GRANTED
                && selfPermsnReceive == PackageManager.PERMISSION_GRANTED
                && selfPermsnRead == PackageManager.PERMISSION_GRANTED;
    }

    // send SMS to given MobileNo
    private void sendSMS() {

        try {

            // Show AutoDetectMessage ProgressBar
            mPrgrsbrAutoDetect.setVisibility(View.VISIBLE);

            OTP_CODE_GENRTD = GeneralFunctions.generateRandomIntegerNumber(100000, 999999);

            /*
             * SMS Format
             * One Time Password for your "APP_NAME" registration is "OTP_CODE_GENRTD".
             * Please use this password to complete your Registration.
             * */
            /*String smsMessage = "One Time Password for your "
                    + getResources().getString(R.string.app_name)
                    + " registration is "
                    + OTP_CODE_GENRTD
                    + ".Please use this password to complete your Registration.";*/
            String smsMessage = getString(R.string.vrfymobile_sms_msg, mAppName, OTP_CODE_GENRTD);

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(MOBILE_NO, null, smsMessage, null, null);

            Handler mHndlr = new Handler();
            mHndlr.postDelayed(new Runnable() {
                @Override
                public void run() {

                    mPrgrsbrAutoDetect.setVisibility(View.GONE);
                    mAppcmptbtnResend.setVisibility(View.VISIBLE);
                }
            }, 120000);
        } catch (Exception e) {

            e.printStackTrace();
            Snackbar.make(mCrdntrlyot, mSendngSmsFailMsg, Snackbar.LENGTH_LONG).show();
            mAppcmptbtnResend.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.appcmptbtn_verifymobileno_resendsms, R.id.fab_verifymobileno_submit})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.appcmptbtn_verifymobileno_resendsms:

                checkSMSPermission();
                mAppcmptbtnResend.setVisibility(View.GONE);
                break;
            case R.id.fab_verifymobileno_submit:

                matchOTPCode();
                break;
        }
    }

    // Match OTP Code and jump to previous activity with Result
    private void matchOTPCode() {

        String etVerfctnCodeStr = mTxtinptEtVrfctnCode.getText().toString().trim();

        if(TextUtils.isEmpty(etVerfctnCodeStr)){

            mTxtinptlyotVrfctnCode.setErrorEnabled(true);
            mTxtinptlyotVrfctnCode.setError(mEntrVrfctnCodeMsg);
        } else{

            OTP_CODE_SMSRECVD = Integer.parseInt(etVerfctnCodeStr);
            if(OTP_CODE_GENRTD == OTP_CODE_SMSRECVD){

                mTxtinptlyotVrfctnCode.setError(null);
                mTxtinptlyotVrfctnCode.setErrorEnabled(false);

                setResult(RESULT_OK);
                finish();
            }else{

                mTxtinptlyotVrfctnCode.setErrorEnabled(true);
                mTxtinptlyotVrfctnCode.setError(mInvalidVrfctnCodeMsg);
                ValidationUtils.requestFocus(this, mTxtinptEtVrfctnCode);

                mAppcmptbtnResend.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == AppConstants.REQUEST_APP_PERMISSION) {

            if(grantResults.length > 0) {

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                } else {
                    Snackbar.make(mCrdntrlyot, mAllowSMSPermsnMsg, Snackbar.LENGTH_LONG).show();
                    mAppcmptbtnResend.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private BroadcastReceiver IncomingSmsReceiver  = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            final Bundle bundle = intent.getExtras();
            try {
                if (bundle != null) {

                    Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (Object objPdusObj : pdusObj) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) objPdusObj);
//						String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        String smsCurrentMessage = currentMessage.getDisplayMessageBody();

                        /*String smsMessageStartFormat = "One Time Password for your " + getResources().getString(R.string.app_name);*/
                        String smsMessageStartFormat = context.getResources().getString(R.string.vrfymobile_sms_startformt_msg, mAppName);
                        if (smsCurrentMessage.contains(smsMessageStartFormat)) {

                            String otpCodeRecvdSMS = smsCurrentMessage.split("is ", 2)[1].substring(0, 6);
                            mTxtinptEtVrfctnCode.setText(otpCodeRecvdSMS.trim());

                            matchOTPCode();
                        }
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
                LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                Toast.makeText(context, mAutoDetectFailMsg, Toast.LENGTH_LONG).show();
                mAppcmptbtnResend.setVisibility(View.VISIBLE);
            }
            mPrgrsbrAutoDetect.setVisibility(View.GONE);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(IncomingSmsReceiver); // UnRegister BroadcastReceiver --> IncomingSmsReceiver
    }
}
