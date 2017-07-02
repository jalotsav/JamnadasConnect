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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.adapters.RcyclrAttchdImgsAdapter;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.common.RecyclerViewEmptySupport;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.MdlGetStandardsRes;
import com.jalotsav.jamnadasconnect.models.MdlGetStreamsRes;
import com.jalotsav.jamnadasconnect.models.MdlUploadChunkImageRes;
import com.jalotsav.jamnadasconnect.models.bookcorrection.MdlBookCorrectionAddRes;
import com.jalotsav.jamnadasconnect.retrofitapi.APIBookCorrection;
import com.jalotsav.jamnadasconnect.retrofitapi.APIGeneral;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Created by Jalotsav on 5/22/2017.
 */

public class FrgmntBookCorrection extends Fragment {

    private static final String TAG = FrgmntBookCorrection.class.getSimpleName();

    @BindView(R.id.cordntrlyot_frgmnt_bookcorctn) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.txtinputlyot_frgmnt_bookcorctn_bookname) TextInputLayout mTxtinptlyotBookName;
    @BindView(R.id.txtinptet_frgmnt_bookcorctn_bookname) TextInputEditText mTxtinptEtBookName;
    @BindView(R.id.spnr_frgmnt_bookcorctn_stream) Spinner mSpnrStream;
    @BindView(R.id.spnr_frgmnt_bookcorctn_standr) Spinner mSpnrStandr;
    @BindView(R.id.imgvw_frgmnt_bookcorctn_backsteppr) ImageView mImgvwBackSteppr;
    @BindView(R.id.vwswtchr_frgmnt_bookcorctn_steppr) ViewSwitcher mVwswtchrSteppr;
    @BindView(R.id.appcmptbtn_frgmnt_bookcorctn_attchmnt_add) AppCompatButton mAppcmptbtnAttachAdd;
    @BindView(R.id.lnrlyot_frgmnt_bookcorctn_attachimage) LinearLayout mLnrlyotAtchdImg;
    @BindView(R.id.lnrlyot_frgmnt_bookcorctn_attachaudio) LinearLayout mLnrlyotAtchdAudio;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.lnrlyot_frgmnt_bookcorctn_attachaudio_mainplaystop) LinearLayout mLnrlyotAtchdAudMainPlayStop;
    @BindView(R.id.rcyclrvw_frgmnt_bookcorctn_attchdimgs) RecyclerViewEmptySupport mRecyclerView;
    @BindView(R.id.imgvw_frgmnt_bookcorctn_attachaudio_startstop) ImageView mImgvwAtchdAudStartStop;
    @BindView(R.id.imgvw_frgmnt_bookcorctn_attachaudio_playstop) ImageView mImgvwAtchdAudPlayStop;
    @BindView(R.id.tv_frgmnt_bookcorctn_attachaudio_lblstartstop) TextView mTvAtchdAudStartStopLbl;
    @BindView(R.id.tv_frgmnt_bookcorctn_attachaudio_lblplaystop) TextView mTvAtchdAudPlayStopLbl;
    @BindView(R.id.tv_frgmnt_bookcorctn_attachaudio_timeremain) TextView mTvTimeRemaining;
    @BindView(R.id.rltvlyot_frgmnt_bookcorctn_attachaudio_timeremain) RelativeLayout mRltvlyotRemaining;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.prgrsbr_frgmnt_bookcorctn) ProgressBar mPrgrsbrMain;
    @BindView(R.id.prgrsbr_frgmnt_bookcorctn_attachaudio_recordprgrs) ProgressBar mPrgrsbrAudioRecord;

    @BindString(R.string.please_wait_3dots) String mPleaseWait;
    @BindString(R.string.note_sml) String mNoteStr;
    @BindString(R.string.bookcrctn_note_dialog_msg) String mBookCrctnNoteDialogMsg;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;
    @BindString(R.string.audio_record_problem_sml) String mAudioRcrdPrblmMsg;
    @BindString(R.string.playing_audio_recorded_problem_sml) String mPlayingAudioRcrdedPrblmMsg;
    @BindString(R.string.no_data_avlbl_refresh) String mNoDataAvilblMsg;
    @BindString(R.string.allow_permtn_atchmnt) String mAllowPermsnMsg;
    @BindString(R.string.record_sml) String mRecordStr;
    @BindString(R.string.play_sml) String mPlayStr;
    @BindString(R.string.stop_sml) String mStopStr;
    @BindString(R.string.selctd_file_mustbe_lessthan_5mb) String mSelctLessThan5MBMsg;
    @BindString(R.string.refresh_sml) String mRefreshStr;
    @BindString(R.string.attchd_imgs_appear_here) String mAttchdImgsAppearHere;
    @BindString(R.string.cant_add_morethan_10_items) String mCantAddMoreThan10Items;
    @BindString(R.string.entr_book_name_sml) String mEntrBookName;
    @BindString(R.string.select_medium_sml) String mSelctStream;
    @BindString(R.string.select_standard_sml) String mSelctStandr;

    @BindDrawable(R.drawable.ic_pictures_flat_128dp) Drawable mDrwblDefaultPicture;
    @BindDrawable(R.drawable.ic_microphone_flat_128dp) Drawable mDrwblMicrophone;
    @BindDrawable(R.drawable.ic_play_flat_128dp) Drawable mDrwblPlay;
    @BindDrawable(R.drawable.ic_stop_flat_128dp) Drawable mDrwblStop;

    UserSessionManager session;
    ProgressDialog mPrgrsDialog;
    boolean mIsAttachImage, mIsWithAttachement, mIsImageProcessing, mIsCounterRunning, mIsMediaPlaying;
    String mBookNameVal, mChunkResFileName = "", mChunkResImageName = "", mFirstChunk = "0", mLastChunk = "0", mSendingImageChunk;
    ArrayAdapter<String> mArryadptrStream, mArryadptrStandr;
    ArrayList<String> mArrylstStreams, mArrylstStandrs;
    RecyclerView.LayoutManager mLayoutManager;
    RcyclrAttchdImgsAdapter mAdapter;
    Uri mImageUri;
    ArrayList<String> mArrylstSelectedImages = new ArrayList<>();
    public ArrayList<JSONObject> mArrlstJsonAttchdImgBase64 = new ArrayList<>();
    ArrayList<String> mArrlystChunks;
    ArrayList<String> mArrylstUploadedImageNames = new ArrayList<>();
    public int mImgCount = 0, mResposeCount = 0;
    RecordAudioCountDownTimer mRecordAudioCountTimer;
    MediaRecorder mMediaRcrdr;
    MediaPlayer mMediaPlayer;
    String mAudioRcrdOutputFile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_book_correction, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        session = new UserSessionManager(getActivity());

        mArrylstStreams = new ArrayList<>();
        mArrylstStreams.add(mSelctStream);
        mArrylstStandrs = new ArrayList<>();
        mArrylstStandrs.add(mSelctStandr);

        // Init Spinner value
        mArryadptrStream = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mArrylstStreams);
        mSpnrStream.setAdapter(mArryadptrStream);
        mArryadptrStandr = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mArrylstStandrs);
        mSpnrStandr.setAdapter(mArryadptrStandr);

        mLayoutManager = new GridLayoutManager(getActivity(), 5);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mLnrlyotAppearHere);

        mTvAppearHere.setText(mAttchdImgsAppearHere);

        mArrylstSelectedImages = new ArrayList<>();
        mAdapter = new RcyclrAttchdImgsAdapter(getActivity(), FrgmntBookCorrection.this, null, mArrylstSelectedImages);
        mRecyclerView.setAdapter(mAdapter);

        // Init image upload in chunk Progress Dialog
        mPrgrsDialog = new ProgressDialog(getActivity());
        mPrgrsDialog.setMessage(mPleaseWait);
        mPrgrsDialog.setCancelable(false);

        noteAlertDialog();

        getAllDetails();

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                playStopRecordedMedia();
            }
        });

        return rootView;
    }

    // Show AlertDialog for Note
    private void noteAlertDialog() {

        AlertDialog.Builder alrtDlg = new AlertDialog.Builder(getActivity());
        alrtDlg.setTitle(mNoteStr);
        alrtDlg.setMessage(mBookCrctnNoteDialogMsg);
        alrtDlg.setCancelable(false);
        alrtDlg.setPositiveButton(getString(android.R.string.ok).toUpperCase(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        alrtDlg.show();
    }

    // Get Teacher Profile, Streams, Standard details
    private void getAllDetails() {

        if (GeneralFunctions.isNetConnected(getActivity())) {

            getStreams();
            getStandards();
        } else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Call Retrofit API
    private void getStreams() {

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder(getActivity(), false).create(APIGeneral.class);
        Call<MdlGetStreamsRes> callMdlGetStreamsRes = objApiGeneral.callGetStreams();
        callMdlGetStreamsRes.enqueue(new Callback<MdlGetStreamsRes>() {
            @Override
            public void onResponse(Call<MdlGetStreamsRes> call, Response<MdlGetStreamsRes> response) {

                try {

                    MdlGetStreamsRes objMdlGetStreamsRes = response.body();
                    if(objMdlGetStreamsRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        mArrylstStreams = new ArrayList<>();
                        mArrylstStreams.add(mSelctStream);
                        mArrylstStreams.addAll(objMdlGetStreamsRes.getArrylstStream());
                        mArryadptrStream = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mArrylstStreams);
                        mSpnrStream.setAdapter(mArryadptrStream);
                    } else
                        Snackbar.make(mCrdntrlyot, objMdlGetStreamsRes.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlGetStreamsRes> call, Throwable t) {

                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    // Call Retrofit API
    private void getStandards() {

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder(getActivity(), false).create(APIGeneral.class);
        Call<MdlGetStandardsRes> callMdlGetStandardsRes = objApiGeneral.callGetStandards();
        callMdlGetStandardsRes.enqueue(new Callback<MdlGetStandardsRes>() {
            @Override
            public void onResponse(Call<MdlGetStandardsRes> call, Response<MdlGetStandardsRes> response) {

                try {

                    MdlGetStandardsRes objMdlGetStandardsRes = response.body();
                    if(objMdlGetStandardsRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        mArrylstStandrs = new ArrayList<>();
                        mArrylstStandrs.add(mSelctStandr);
                        mArrylstStandrs.addAll(objMdlGetStandardsRes.getArrylstStandard());
                        mArryadptrStandr = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mArrylstStandrs);
                        mSpnrStandr.setAdapter(mArryadptrStandr);
                    } else
                        Snackbar.make(mCrdntrlyot, objMdlGetStandardsRes.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlGetStandardsRes> call, Throwable t) {

                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.imgvw_frgmnt_bookcorctn_backsteppr, R.id.imgvw_frgmnt_bookcorctn_attachimage,
            R.id.imgvw_frgmnt_bookcorctn_attachaudio, R.id.appcmptbtn_frgmnt_bookcorctn_attchmnt_add,
            R.id.imgvw_frgmnt_bookcorctn_attachaudio_startstop, R.id.imgvw_frgmnt_bookcorctn_attachaudio_playstop})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.imgvw_frgmnt_bookcorctn_backsteppr:

                clearAttachmentUI();
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachimage:

                mIsAttachImage = true;
                updateUIAttachmnt();
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachaudio:

                mIsAttachImage = false;
                updateUIAttachmnt();
                break;
            case R.id.appcmptbtn_frgmnt_bookcorctn_attchmnt_add:

                if(mAdapter.getItemCount() == 10)
                    Snackbar.make(mCrdntrlyot, mCantAddMoreThan10Items, Snackbar.LENGTH_LONG).show();
                else
                    checkAppPermission(view);
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachaudio_startstop:

                checkAppPermission(view);
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachaudio_playstop:

                playStopRecordedMedia();
                break;
        }
    }

    // Play/Stop Recorded Audio
    private void playStopRecordedMedia() {

        if(!mIsMediaPlaying) {

            if (mAudioRcrdOutputFile.length() != 0) {

                try {

                    mIsMediaPlaying = true;
                    if(mMediaPlayer == null)
                        mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(mAudioRcrdOutputFile);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    mImgvwAtchdAudPlayStop.setImageDrawable(mDrwblStop);
                    mTvAtchdAudPlayStopLbl.setText(mStopStr);
                    mRltvlyotRemaining.setVisibility(View.INVISIBLE);
                    mPrgrsbrAudioRecord.setVisibility(View.INVISIBLE);
                } catch (IOException e) {

                    e.printStackTrace();
                    mIsMediaPlaying = false;
                    Snackbar.make(mCrdntrlyot, mPlayingAudioRcrdedPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }
        } else {

            try {

                if(mMediaPlayer != null) {

                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                    mIsMediaPlaying = false;
                    mImgvwAtchdAudPlayStop.setImageDrawable(mDrwblPlay);
                    mTvAtchdAudPlayStopLbl.setText(mPlayStr);
                    mRltvlyotRemaining.setVisibility(View.VISIBLE);
                    mPrgrsbrAudioRecord.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {

                e.printStackTrace();
                Snackbar.make(mCrdntrlyot, mPlayingAudioRcrdedPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
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
                else startStopRecordAudio();
            }
        } else {

            if(mIsAttachImage)
                showImagePickPopupmenu(view);
            else startStopRecordAudio();
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

    // Start/Stop Record Audio
    private void startStopRecordAudio() {

        if(!mIsCounterRunning) {

            try {

                if(mIsMediaPlaying)
                    playStopRecordedMedia();

                initMediaRecorder();

                mRltvlyotRemaining.setVisibility(View.VISIBLE);
                mPrgrsbrAudioRecord.setVisibility(View.VISIBLE);

                mMediaRcrdr.prepare();
                mMediaRcrdr.start();

                mIsCounterRunning = true;
                mPrgrsbrAudioRecord.setProgress(1800);
                mRecordAudioCountTimer = new RecordAudioCountDownTimer(180000, 1000);
                mRecordAudioCountTimer.start();
                mImgvwAtchdAudStartStop.setImageDrawable(mDrwblStop);
                mTvAtchdAudStartStopLbl.setText(mStopStr);
                mLnrlyotAtchdAudMainPlayStop.setVisibility(View.GONE);
            } catch (Exception e) {

                e.printStackTrace();
                mIsCounterRunning = false;
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

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotBookName, mTxtinptEtBookName, mEntrBookName)) // Book Name
            return;

        if(!validateStream())
            return;

        if(!validateStandard())
            return;

        if (GeneralFunctions.isNetConnected(getActivity())) {

            if(mIsWithAttachement)
                getChunksListFromBase64();
            else
                callBookCorrectionAddAPI();
        } else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();

    }

    // Validate Stream spinner
    private boolean validateStream() {

        if(mArrylstStreams.size() <= 1) {

            showRefreshSnackbar();
            return false;
        } else if(mSpnrStream.getSelectedItemPosition() == 0) {

            GeneralFunctions.showToastSingle(getActivity(), mSelctStream, Toast.LENGTH_SHORT);
            return false;
        } else
            return true;
    }

    // Validate Standard spinner
    private boolean validateStandard() {

        if(mArrylstStandrs.size() <= 1) {

            showRefreshSnackbar();
            return false;
        } else if(mSpnrStandr.getSelectedItemPosition() == 0) {

            GeneralFunctions.showToastSingle(getActivity(), mSelctStandr, Toast.LENGTH_SHORT);
            return false;
        } else
            return true;
    }

    // Show SnackBar with Refresh action
    private void showRefreshSnackbar() {

        Snackbar.make(mCrdntrlyot, mNoDataAvilblMsg, Snackbar.LENGTH_LONG)
                .setAction(mRefreshStr, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        getAllDetails();
                    }
                })
                .show();
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

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder(getActivity(), true).create(APIGeneral.class);
        Call<MdlUploadChunkImageRes> callResponseBody = objApiGeneral.callUploadChunkImage(AppConstants.CHUNK_MODULE_CORRECTION,
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

                            if (mArrlstJsonAttchdImgBase64.size() > 0) {
                                getChunksListFromBase64();
                            } else {
                                mPrgrsDialog.dismiss();

                                callBookCorrectionAddAPI();
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
    private void callBookCorrectionAddAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        mBookNameVal = mTxtinptEtBookName.getText().toString().trim();
        String attchmentsNames;
        if(mAdapter.getItemCount() > 0)
            attchmentsNames = TextUtils.join(",", mArrylstUploadedImageNames);
        else attchmentsNames = "";

        APIBookCorrection objApiBookCorrctn = APIRetroBuilder.getRetroBuilder(getActivity(), false).create(APIBookCorrection.class);
        Call<MdlBookCorrectionAddRes> callMdlBookReqstAddRes = objApiBookCorrctn.callBookCorrectnAdd(
                GeneralFunctions.getDeviceInfo(getActivity()), session.getUserId(), mBookNameVal,
                mSpnrStream.getSelectedItem().toString(), mSpnrStandr.getSelectedItem().toString(), attchmentsNames);
        callMdlBookReqstAddRes.enqueue(new Callback<MdlBookCorrectionAddRes>() {
            @Override
            public void onResponse(Call<MdlBookCorrectionAddRes> call, Response<MdlBookCorrectionAddRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                try {
                    MdlBookCorrectionAddRes objMdlBookCorrctnAddRes = response.body();
                    if(objMdlBookCorrctnAddRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        GeneralFunctions.hideSoftKeyboard(getActivity());
                        GeneralFunctions.showToastSingle(getActivity(), objMdlBookCorrctnAddRes.getMessage(), Toast.LENGTH_LONG);

                        mTxtinptEtBookName.setText("");
                        mSpnrStream.setSelection(0);
                        mSpnrStandr.setSelection(0);

                        clearAttachmentUI();

                        getActivity().onBackPressed();
                    } else
                        Snackbar.make(mCrdntrlyot, objMdlBookCorrctnAddRes.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlBookCorrectionAddRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    // Clear all values of Attachment UI
    private void clearAttachmentUI() {

        if(mIsCounterRunning)
            finishRecordAudio();
        if(mIsMediaPlaying)
            playStopRecordedMedia();

        mArrylstUploadedImageNames = new ArrayList<>();
        mArrlstJsonAttchdImgBase64 = new ArrayList<>();
        mIsImageProcessing = false;
        mImgCount = 0;
        mArrylstSelectedImages = new ArrayList<>();
        mAdapter = new RcyclrAttchdImgsAdapter(getActivity(), FrgmntBookCorrection.this, null, mArrylstSelectedImages);
        mRecyclerView.setAdapter(mAdapter);

        if(mVwswtchrSteppr.getDisplayedChild() != 0) {
            mVwswtchrSteppr.setInAnimation(getActivity(), R.anim.slide_left_in);
            mVwswtchrSteppr.setOutAnimation(getActivity(), R.anim.slide_right_out);
            mVwswtchrSteppr.showPrevious();
        }

        mAppcmptbtnAttachAdd.setVisibility(View.INVISIBLE);
        mLnrlyotAtchdAudMainPlayStop.setVisibility(View.GONE);
        mImgvwBackSteppr.setVisibility(View.GONE);
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
            mImgvwAtchdAudStartStop.setImageDrawable(mDrwblMicrophone);
            mTvAtchdAudStartStopLbl.setText(mRecordStr);

            if(mAudioRcrdOutputFile.length() != 0) {

                mLnrlyotAtchdAudMainPlayStop.setVisibility(View.VISIBLE);

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
                    break;
                case AppConstants.REQUEST_PICK_IMAGE:

                    mImageUri = result.getData();

                    LogHelper.printLog(AppConstants.LOGTYPE_INFO, TAG, "onActivityResult: " + mImageUri);

                    updateUISetAttchdImageArray();
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

            if (fileSizeInMB > 5)
                Snackbar.make(mCrdntrlyot, mSelctLessThan5MBMsg, Snackbar.LENGTH_LONG).show();
            else {

                mAdapter.addItem(String.valueOf(picturePath));

                String strBase64 = GeneralFunctions.convertImageToBase64(picturePath);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(AppConstants.ID_SML, mImgCount);
                jsonObject.put(AppConstants.BASE64_PART, strBase64);
                mArrlstJsonAttchdImgBase64.add(jsonObject);
                mImgCount++;

                mIsWithAttachement = true;
            }
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
        mImageUri = getImageUri(getActivity(), thumbnail);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_myprofile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_myprofile_refresh:

                getAllDetails();
                break;
            case R.id.action_myprofile_done:

                if(!mIsCounterRunning && mPrgrsbrMain.getVisibility() != View.VISIBLE) {
                    if (GeneralFunctions.isNetConnected(getActivity()))
                        checkAllValidation();
                    else
                        Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mIsCounterRunning)
            mRecordAudioCountTimer.cancel();

        if(mIsMediaPlaying) {

            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
