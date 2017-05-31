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

package com.jalotsav.jamnadasconnect.navgtndrawer;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.MdlUploadChunkImageRes;
import com.jalotsav.jamnadasconnect.models.teachersuggestions.MdlTeachrSugstnsAddRes;
import com.jalotsav.jamnadasconnect.retrofitapi.APIGeneral;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.jalotsav.jamnadasconnect.retrofitapi.APITeacherSuggestions;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 5/27/2017.
 */

public class FrgmntTeacherSuggestions extends Fragment {

    private static final String TAG = FrgmntTeacherSuggestions.class.getSimpleName();

    @BindView(R.id.cordntrlyot_frgmnt_teachrsugstns) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.txtinputlyot_frgmnt_teachrsugstns_title) TextInputLayout mTxtinptlyotTitle;
    @BindView(R.id.txtinputlyot_frgmnt_teachrsugstns_descrptn) TextInputLayout mTxtinptlyotDescrptn;
    @BindView(R.id.txtinptet_frgmnt_teachrsugstns_title) TextInputEditText mTxtinptEtTitle;
    @BindView(R.id.txtinptet_frgmnt_teachrsugstns_descrptn) TextInputEditText mTxtinptEtDescrptn;
    @BindView(R.id.imgvw_frgmnt_teachrsugstns_backsteppr) ImageView mImgvwBackSteppr;
    @BindView(R.id.vwswtchr_frgmnt_teachrsugstns_steppr) ViewSwitcher mVwswtchrSteppr;
    @BindView(R.id.appcmptbtn_frgmnt_teachrsugstns_attchmnt_add) AppCompatButton mAppcmptbtnAttachAdd;
    @BindView(R.id.lnrlyot_frgmnt_bookcorctn_attachimage) LinearLayout mLnrlyotAtchdImg;
    @BindView(R.id.lnrlyot_frgmnt_bookcorctn_attachaudio) LinearLayout mLnrlyotAtchdAudio;
    @BindView(R.id.imgvw_frgmnt_bookcorctn_attachimage_preview) ImageView mImgvwAtchdImgPreview;
    @BindView(R.id.imgvw_frgmnt_bookcorctn_attachaudio_startstop) ImageView mImgvwAtchdAudStartStop;
    @BindView(R.id.tv_frgmnt_bookcorctn_attachaudio_timeremain) TextView mTvTimeRemaining;
    @BindView(R.id.tv_frgmnt_bookcorctn_attachaudio_recordattchdmsg) TextView mTvRecrdngAttachd;
    @BindView(R.id.prgrsbr_frgmnt_teachrsugstns) ProgressBar mPrgrsbrMain;
    @BindView(R.id.prgrsbr_frgmnt_bookcorctn_attachaudio_recordprgrs) ProgressBar mPrgrsbrAudioRecord;

    @BindString(R.string.please_wait_3dots) String mPleaseWait;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;
    @BindString(R.string.audio_record_problem_sml) String mAudioRcrdPrblmMsg;
    @BindString(R.string.allow_permtn_atchmnt) String mAllowPermsnMsg;
    @BindString(R.string.refresh_sml) String mRefreshStr;
    @BindString(R.string.entr_title_sml) String mEntrTitle;
    @BindString(R.string.entr_descrptn_sml) String mEntrDescrptn;

    @BindDrawable(R.drawable.ic_pictures_flat_128dp) Drawable mDrwblDefaultPicture;
    @BindDrawable(R.drawable.ic_play_flat_128dp) Drawable mDrwblPlay;
    @BindDrawable(R.drawable.ic_stop_flat_128dp) Drawable mDrwblStop;

    UserSessionManager session;
    ProgressDialog mPrgrsDialog;
    boolean mIsAttachImage, mIsWithAttachement, mIsImageProcessing, mIsCounterRunning;
    String mTitleVal, mDescrptnVal, mChunkResFileName = "", mChunkResImageName = "", mFirstChunk = "0", mLastChunk = "0", mSendingImageChunk;
    Uri mImageUri;
    List<String> mArrylstSelectedImages = new ArrayList<>();
    List<JSONObject> mArrlstJsonAttchdImgBase64 = new ArrayList<>();
    List<String> mArrlystChunks;
    ArrayList<String> mArrylstUploadedImageNames = new ArrayList<>();
    int mImgCount = 0, mResposeCount = 0;
    RecordAudioCountDownTimer mRecordAudioCountTimer;
    MediaRecorder mMediaRcrdr;
    String mAudioRcrdOutputFile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_teacher_suggestions, container, false);
        ButterKnife.bind(this, rootView);

        session = new UserSessionManager(getActivity());

        // Init image upload in chunk Progress Dialog
        mPrgrsDialog = new ProgressDialog(getActivity());
        mPrgrsDialog.setMessage(mPleaseWait);
        mPrgrsDialog.setCancelable(false);

        return rootView;
    }

    @OnClick({R.id.fab_frgmnt_teachrsugstns_done, R.id.imgvw_frgmnt_teachrsugstns_backsteppr, R.id.imgvw_frgmnt_bookcorctn_attachimage,
            R.id.imgvw_frgmnt_bookcorctn_attachaudio, R.id.appcmptbtn_frgmnt_teachrsugstns_attchmnt_add,
            R.id.imgvw_frgmnt_bookcorctn_attachaudio_startstop})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.fab_frgmnt_teachrsugstns_done:

                if(!mIsCounterRunning && mPrgrsbrMain.getVisibility() != View.VISIBLE) {
                    if (GeneralFunctions.isNetConnected(getActivity()))
                        checkAllValidation();
                    else
                        Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.imgvw_frgmnt_teachrsugstns_backsteppr:

                if(mVwswtchrSteppr.getDisplayedChild() != 0) {
                    mVwswtchrSteppr.setInAnimation(getActivity(), R.anim.slide_left_in);
                    mVwswtchrSteppr.setOutAnimation(getActivity(), R.anim.slide_right_out);
                    mVwswtchrSteppr.showPrevious();
                }
                mImgvwBackSteppr.setVisibility(View.GONE);
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachimage:

                mIsAttachImage = true;
                updateUIAttachmnt();
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachaudio:

                mIsAttachImage = false;
                updateUIAttachmnt();
                break;
            case R.id.appcmptbtn_frgmnt_teachrsugstns_attchmnt_add:

                checkAppPermission(view);
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachaudio_startstop:

                checkAppPermission(view);
                break;
        }
    }

    // Update Attachment view UI, Change ViewSwitcher
    private void updateUIAttachmnt() {

        mImgvwBackSteppr.setVisibility(View.VISIBLE);

        if(mIsAttachImage) {

            mVwswtchrSteppr.setInAnimation(getActivity(), R.anim.slide_right_in);
            mVwswtchrSteppr.setOutAnimation(getActivity(), R.anim.slide_left_out);
            mVwswtchrSteppr.showNext();
            mLnrlyotAtchdImg.setVisibility(View.VISIBLE);
            mLnrlyotAtchdAudio.setVisibility(View.GONE);
            mAppcmptbtnAttachAdd.setVisibility(View.VISIBLE);
        } else {

            mVwswtchrSteppr.setInAnimation(getActivity(), R.anim.slide_right_in);
            mVwswtchrSteppr.setOutAnimation(getActivity(), R.anim.slide_left_out);
            mVwswtchrSteppr.showNext();
            mLnrlyotAtchdImg.setVisibility(View.GONE);
            mLnrlyotAtchdAudio.setVisibility(View.VISIBLE);
            mAppcmptbtnAttachAdd.setVisibility(View.INVISIBLE);
        }
    }

    // Check Storage & Camera permission for use
    private void checkAppPermission(View view) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!isCheckSelfPermission())
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, AppConstants.REQUEST_APP_PERMISSION);
            else {
                if(mIsAttachImage)
                    showImagePickPopupmenu(view);
                else startRecordAudio();
            }
        } else {

            if(mIsAttachImage)
                showImagePickPopupmenu(view);
            else startRecordAudio();
        }
    }

    private boolean isCheckSelfPermission(){

        int selfPermsnStorage = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int selfPermsnCamera = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int selfPermsnAudio = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
        return  selfPermsnStorage == PackageManager.PERMISSION_GRANTED && selfPermsnCamera == PackageManager.PERMISSION_GRANTED && selfPermsnAudio == PackageManager.PERMISSION_GRANTED;
    }

    // Show pop-up for pick or capture image
    private void showImagePickPopupmenu(View view) {

        PopupMenu mPopupmenu = new PopupMenu(getActivity(), view);
        mPopupmenu.getMenuInflater().inflate(R.menu.menu_popup_imagepick, mPopupmenu.getMenu());
        mPopupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_camera:

                        Intent intntCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intntCamera, AppConstants.REQUEST_OPEN_CAMERA);
                        break;
                    case R.id.action_gallery:

                        Intent intntGallery = new Intent();
                        intntGallery.setAction(Intent.ACTION_PICK);
                        intntGallery.setType("image/*");
                        startActivityForResult(Intent.createChooser(intntGallery, "Select a Image"), AppConstants.REQUEST_PICK_IMAGE);
                        break;
                }
                return false;
            }
        });
        mPopupmenu.show();
    }

    // Start Record Audio
    private void startRecordAudio() {

        if(!mIsCounterRunning) {

            try {

                initMediaRecorder();

                mMediaRcrdr.prepare();
                mMediaRcrdr.start();

                mIsCounterRunning = true;
                mPrgrsbrAudioRecord.setProgress(1800);
                mRecordAudioCountTimer = new RecordAudioCountDownTimer(180000, 1000);
                mRecordAudioCountTimer.start();
                mImgvwAtchdAudStartStop.setImageDrawable(mDrwblStop);
                mTvRecrdngAttachd.setVisibility(View.INVISIBLE);
            } catch (Exception e) {

                e.printStackTrace();
                Snackbar.make(mCrdntrlyot, mAudioRcrdPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        } else
            finishRecordAudio();
    }

    // Init MediaRecorder
    private void initMediaRecorder() {

        try {

            File tempAudioPath = AppConstants.PATH_TEMP_AUDIO;

            // Delete all previous files
            if(tempAudioPath.exists())
                GeneralFunctions.deleteFilesRecursive(tempAudioPath.getAbsolutePath());

            tempAudioPath.mkdirs();
            String fileSuffix = ".".concat(AppConstants.EXTENSION_MP3);
            File fileAudioRcrdFileName = File.createTempFile(GeneralFunctions.getCurrentTimestamp(), fileSuffix, tempAudioPath);
            mAudioRcrdOutputFile = fileAudioRcrdFileName.getAbsolutePath();

            mMediaRcrdr = new MediaRecorder();
            mMediaRcrdr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRcrdr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRcrdr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mMediaRcrdr.setOutputFile(mAudioRcrdOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check all validation of fields and call API
    private void checkAllValidation() {

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotTitle, mTxtinptEtTitle, mEntrTitle)) // Title
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotDescrptn, mTxtinptEtDescrptn, mEntrDescrptn)) // Description
            return;

        if (GeneralFunctions.isNetConnected(getActivity())) {

            if(mIsWithAttachement)
                getChunksListFromBase64();
            else
                callTeacherSuggestionAddAPI();
        } else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Get chunks list
    private void getChunksListFromBase64() {

        try {

            mResposeCount = 0;
            mChunkResFileName = "";
            mArrlystChunks = GeneralFunctions.getChunkFromBase64(mArrlstJsonAttchdImgBase64.get(0).getString(AppConstants.BASE64_PART));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIsImageProcessing = true;

        if (GeneralFunctions.isNetConnected(getActivity()))
            prepareChunkCallUploadAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Prepare chunk and call Upload API for sending
    private void prepareChunkCallUploadAPI() {

        int last_chunk = mArrlystChunks.size() - 1;
        if (mResposeCount == 0)
            mSendingImageChunk = "data:image/jpeg;base64," + mArrlystChunks.get(mResposeCount);
        else
            mSendingImageChunk = mArrlystChunks.get(mResposeCount);

        if (mResposeCount == 0)
            mFirstChunk = "1";
        else
            mFirstChunk = "0";
        //  mapParam.put("first_chunk", String.valueOf(mFirstChunk));

        if (mResposeCount == last_chunk)
            mLastChunk = "1";
        else
            mLastChunk = "0";

        callUploadChunkImageAPI();
    }

    // Call Retrofit API
    private void callUploadChunkImageAPI() {

        if(!mPrgrsDialog.isShowing())
            mPrgrsDialog.show();

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder(true).create(APIGeneral.class);
        Call<MdlUploadChunkImageRes> callResponseBody = objApiGeneral.callUploadChunkImage(AppConstants.CHUNK_MODULE_SUGGESTION,
                mSendingImageChunk, mChunkResFileName, mLastChunk, mIsAttachImage ? AppConstants.EXTENSION_JPG : AppConstants.EXTENSION_MP3);
        callResponseBody.enqueue(new Callback<MdlUploadChunkImageRes>() {
            @Override
            public void onResponse(Call<MdlUploadChunkImageRes> call, Response<MdlUploadChunkImageRes> response) {

                if(response.isSuccessful()) {

                    try {
                        MdlUploadChunkImageRes objMdlUploadChunkImageRes = response.body();
                        if(!TextUtils.isEmpty(objMdlUploadChunkImageRes.getUploadFileName())) {

                            mChunkResImageName = objMdlUploadChunkImageRes.getUploadFileName();
                            mArrylstUploadedImageNames.add(mChunkResImageName);
                            mIsImageProcessing = false;
                            mArrlstJsonAttchdImgBase64.remove(0);

                            /*alProgress.set(mArrylstUploadedImageNames.size() - 1, AppKeyword.statusmessage_600_false);
                            alDelete.set(mArrylstUploadedImageNames.size() - 1, AppKeyword.statusmessage_700_true);

                            adapterSendConversationImgList = new AdapterSendConversationImgList(activity, mArrylstSelectedImages, alProgress, alDelete);
                            rvImages.setAdapter(adapterSendConversationImgList);*/

                            if (mArrlstJsonAttchdImgBase64.size() > 0) {
                                getChunksListFromBase64();
                            } else {
                                mPrgrsDialog.dismiss();

                                callTeacherSuggestionAddAPI();
                            }
                        } else if (TextUtils.isEmpty(objMdlUploadChunkImageRes.getUploadFileName())) {

                            mResposeCount++;
                            mChunkResFileName = objMdlUploadChunkImageRes.getFileName();
                            prepareChunkCallUploadAPI();
                        } else {

                            mPrgrsDialog.dismiss();
                            Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else {

                    mPrgrsDialog.dismiss();
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MdlUploadChunkImageRes> call, Throwable t) {

                mPrgrsDialog.dismiss();
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    // Call Retrofit API
    private void callTeacherSuggestionAddAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        mTitleVal = mTxtinptEtTitle.getText().toString().trim();
        mDescrptnVal = mTxtinptEtDescrptn.getText().toString().trim();
        String attchmentsNames;
        if(mArrylstUploadedImageNames.isEmpty())
            attchmentsNames = "";
        else attchmentsNames = mArrylstUploadedImageNames.get(0);

        APITeacherSuggestions objApiTeachrSugstns = APIRetroBuilder.getRetroBuilder(false).create(APITeacherSuggestions.class);
        Call<MdlTeachrSugstnsAddRes> callMdlTeachrSugstnsAddRes = objApiTeachrSugstns.callTeachSugstnsAdd(
                GeneralFunctions.getDeviceInfo(getActivity()), session.getUserId(), mTitleVal, mDescrptnVal, attchmentsNames);
        callMdlTeachrSugstnsAddRes.enqueue(new Callback<MdlTeachrSugstnsAddRes>() {
            @Override
            public void onResponse(Call<MdlTeachrSugstnsAddRes> call, Response<MdlTeachrSugstnsAddRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                try {
                    MdlTeachrSugstnsAddRes objMdlTeachrSugstnsAddRes = response.body();
                    if(objMdlTeachrSugstnsAddRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        GeneralFunctions.hideSoftKeyboard(getActivity());
                        GeneralFunctions.showToastSingle(getActivity(), objMdlTeachrSugstnsAddRes.getMessage(), Toast.LENGTH_LONG);
//                        Snackbar.make(mCrdntrlyot, objMdlTeachrSugstnsAddRes.getMessage(), Snackbar.LENGTH_SHORT).show();

                        clearUI();

                        getActivity().onBackPressed();
                    } else
                        Snackbar.make(mCrdntrlyot, objMdlTeachrSugstnsAddRes.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlTeachrSugstnsAddRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    // Clear all values of UI
    private void clearUI() {

        mTxtinptEtTitle.setText("");
        mTxtinptEtDescrptn.setText("");
        mImgvwAtchdImgPreview.setImageDrawable(mDrwblDefaultPicture);

        mArrylstUploadedImageNames = new ArrayList<>();
        mArrlstJsonAttchdImgBase64 = new ArrayList<>();
        mIsImageProcessing = false;
        mImgCount = 0;
        mArrylstSelectedImages = new ArrayList<>();
        /*alProgress = new ArrayList<>();
        alDelete = new ArrayList<>();
        adapterSendConversationImgList = new AdapterSendConversationImgList(activity, mArrylstSelectedImages, alProgress, alDelete);
        rvImages.setAdapter(adapterSendConversationImgList);*/

        if(mVwswtchrSteppr.getDisplayedChild() != 0) {
            mVwswtchrSteppr.setInAnimation(getActivity(), R.anim.slide_left_in);
            mVwswtchrSteppr.setOutAnimation(getActivity(), R.anim.slide_right_out);
            mVwswtchrSteppr.showPrevious();
        }

        mAppcmptbtnAttachAdd.setVisibility(View.INVISIBLE);
        mTvRecrdngAttachd.setVisibility(View.INVISIBLE);
    }

    private class RecordAudioCountDownTimer extends CountDownTimer {

        RecordAudioCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            String currentTime = String.format(Locale.getDefault(), "%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
            );
            mTvTimeRemaining.setText(currentTime);
            int currentprgrs = (int) (millisUntilFinished / 1000);
            LogHelper.printLog(AppConstants.LOGTYPE_INFO, TAG, "CurrentProgress: " + currentprgrs);
            mPrgrsbrAudioRecord.setProgress(currentprgrs);
        }

        @Override
        public void onFinish() {

            finishRecordAudio();
        }
    }

    // Finish current Record Audio CountDownTimer & Media Recorder
    private void finishRecordAudio() {

        try {

            mMediaRcrdr.stop();
            mMediaRcrdr.release();
            mMediaRcrdr = null;

            mIsCounterRunning = false;
            mRecordAudioCountTimer.cancel();
            mPrgrsbrAudioRecord.setProgress(1800);
            String currentTime = String.format(Locale.getDefault(), "%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(180000),
                    TimeUnit.MILLISECONDS.toSeconds(180000) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(180000))
            );
            mTvTimeRemaining.setText(currentTime);
            mImgvwAtchdAudStartStop.setImageDrawable(mDrwblPlay);

            if(mAudioRcrdOutputFile.length() != 0) {

                mTvRecrdngAttachd.setVisibility(View.VISIBLE);

                String strBase64 = GeneralFunctions.convertAudioToBase64(mAudioRcrdOutputFile);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(AppConstants.ID_SML, mImgCount);
                jsonObject.put(AppConstants.BASE64_PART, strBase64);
                mArrlstJsonAttchdImgBase64.add(jsonObject);
                mImgCount++;

                mIsWithAttachement = true;
            }
        } catch (Exception e) {

            e.printStackTrace();
            Snackbar.make(mCrdntrlyot, mAudioRcrdPrblmMsg, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == AppConstants.REQUEST_APP_PERMISSION) {

            if(grantResults.length > 0) {

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    mAppcmptbtnAttachAdd.performClick();
                } else
                    Snackbar.make(mCrdntrlyot, mAllowPermsnMsg, Snackbar.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.REQUEST_OPEN_CAMERA:

                    onCaptureImageResult(result);

                    LogHelper.printLog(AppConstants.LOGTYPE_INFO, TAG, "onActivityResult: " + mImageUri);

                    updateUISetAttchdImageArray();

                    /*if (!TextUtils.isEmpty(mImageUri.toString()))
                        new UserImageUploadTask().execute();*/
                    break;
                case AppConstants.REQUEST_PICK_IMAGE:

                    mImageUri = result.getData();
                    mImgvwAtchdImgPreview.setImageURI(mImageUri);

                    LogHelper.printLog(AppConstants.LOGTYPE_INFO, TAG, "onActivityResult: " + mImageUri);

                    updateUISetAttchdImageArray();
                    /*if (!TextUtils.isEmpty(mImageUri.toString()))
                        new UserImageUploadTask().execute();*/
                    break;
            }
        }
    }

    // Update UI and set image in attached array list
    private void updateUISetAttchdImageArray() {

        try {

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            System.out.println("File Path Column is: " + Arrays.toString(filePathColumn));

            Cursor cursor = getActivity().getContentResolver().query(mImageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            String picturePath = cursor.getString(columnIndex);
            System.out.println("File Path is: " + picturePath);

            File file = new File(picturePath);

            double fileSizeInMB = (file.length() / (1024 * 1024));
            System.out.println("File Size in MB is: " + fileSizeInMB);

            cursor.close();

            /*if (fileSizeInMB > 2) {

                general.DisplayToast(activity, getString(R.string.image_upload_error), AppKeyword.TOASTSHORT, AppKeyword.status_toast);
            } else {*/

            String imagePathName = picturePath;
//                IMAGE_URI_PROFILE_STR = imagePathName;
            mArrylstSelectedImages.add(String.valueOf(imagePathName));

                /*alProgress.add(mArrylstUploadedImageNames.size(), AppKeyword.statusmessage_700_true);
                alDelete.add(mArrylstUploadedImageNames.size(), AppKeyword.statusmessage_600_false);
                rvImages.setHasFixedSize(true);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                rvImages.setLayoutManager(layoutManager);

                adapterSendConversationImgList = new AdapterSendConversationImgList(activity, mArrylstSelectedImages, alProgress, alDelete);
                rvImages.setAdapter(adapterSendConversationImgList);*/

            String strBase64 = GeneralFunctions.convertImageToBase64(imagePathName);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(AppConstants.ID_SML, mImgCount);
            jsonObject.put(AppConstants.BASE64_PART, strBase64);
            mArrlstJsonAttchdImgBase64.add(jsonObject);
            mImgCount++;

            mIsWithAttachement = true;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mImgvwAtchdImgPreview.setImageBitmap(thumbnail);
        mImageUri = getImageUri(getActivity(), thumbnail);
//        File finalFile = new File(getRealPathFromURI(mImageUri));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mIsCounterRunning)
            mRecordAudioCountTimer.cancel();
    }
}
