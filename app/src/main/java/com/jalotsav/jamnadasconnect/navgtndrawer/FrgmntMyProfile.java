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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.adapters.RcyclrWorkDtlsAdapter;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.common.RecyclerViewEmptySupport;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.MdlGetStandardsRes;
import com.jalotsav.jamnadasconnect.models.MdlGetStreamsRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherBasic;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherContactEductn;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherEditRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherViewRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherWork;
import com.jalotsav.jamnadasconnect.retrofitapi.APIGeneral;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.jalotsav.jamnadasconnect.retrofitapi.APITeacher;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 4/26/2017.
 */

public class FrgmntMyProfile extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = FrgmntMyProfile.class.getSimpleName();

    @BindView(R.id.cordntrlyot_frgmnt_myprofile) CoordinatorLayout mCrdntrlyot;

    @BindView(R.id.lnrlyot_frgmnt_myprofile_framelyot_title) LinearLayout mTitleContainer;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;

    @BindView(R.id.appbarlyot_frgmnt_myprofile) AppBarLayout mAppBarLayout;
//    @BindView(R.id.toolbar_frgmnt_myprofile) Toolbar mToolbar;

    @BindView(R.id.tv_frgmnt_myprofile_toolbar_title) TextView mtvToolbarTitle;
    @BindView(R.id.tv_frgmnt_myprofile_framelyot_title) TextView mTvFramelyotTitle;
    @BindView(R.id.tv_frgmnt_myprofile_framelyot_mobileno) TextView mTvFramelyotMobileno;
    @BindView(R.id.tv_frgmnt_myprofile_birthdaylbl) TextView mTvBirthdayLbl;
    @BindView(R.id.tv_frgmnt_myprofile_birthday) TextView mTvBirthday;
    @BindView(R.id.tv_frgmnt_myprofile_birthdayerror) TextView mTvBirthdayError;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;

    @BindView(R.id.view_frgmnt_myprofile_birthdayunderline) View mVwBirthdayUnderLine;

    @BindView(R.id.txtinputlyot_frgmnt_myprofile_email) TextInputLayout mTxtinptlyotEmail;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_exprnc) TextInputLayout mTxtinptlyotExprnc;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_areaofintrst) TextInputLayout mTxtinptlyotAreaOfIntrst;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_eductnlqualfctn) TextInputLayout mTxtinptlyotEductnlQualfctn;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_adrsline1) TextInputLayout mTxtinptlyotAdrsLine1;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_city) TextInputLayout mTxtinptlyotCity;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_state) TextInputLayout mTxtinptlyotState;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_country) TextInputLayout mTxtinptlyotCountry;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_pincode) TextInputLayout mTxtinptlyotPincode;

    @BindView(R.id.txtinptet_frgmnt_myprofile_email) TextInputEditText mTxtinptEtEmail;
    @BindView(R.id.txtinptet_frgmnt_myprofile_exprnc) TextInputEditText mTxtinptEtExprnc;
    @BindView(R.id.txtinptet_frgmnt_myprofile_areaofintrst) TextInputEditText mTxtinptEtAreaOfIntrst;
    @BindView(R.id.txtinptet_frgmnt_myprofile_eductnlqualfctn) TextInputEditText mTxtinptEtEductnlQualfctn;
    @BindView(R.id.txtinptet_frgmnt_myprofile_achievmnts) TextInputEditText mTxtinptEtAchievmnts;
    @BindView(R.id.txtinptet_frgmnt_myprofile_adrsline1) TextInputEditText mTxtinptEtAdrsLine1;
    @BindView(R.id.txtinptet_frgmnt_myprofile_adrsline2) TextInputEditText mTxtinptEtAdrsLine2;
    @BindView(R.id.txtinptet_frgmnt_myprofile_city) TextInputEditText mTxtinptEtCity;
    @BindView(R.id.txtinptet_frgmnt_myprofile_state) TextInputEditText mTxtinptEtState;
    @BindView(R.id.txtinptet_frgmnt_myprofile_country) TextInputEditText mTxtinptEtCountry;
    @BindView(R.id.txtinptet_frgmnt_myprofile_pincode) TextInputEditText mTxtinptEtPincode;

    public @BindView(R.id.rcyclrvw_frgmnt_myprofile_workdtls) RecyclerViewEmptySupport mRecyclerView;

    @BindView(R.id.prgrsbr_frgmnt_myprofile) ProgressBar mPrgrsbrMain;

    @BindView(R.id.appcmptbtn_frgmnt_myprofile_workdtls_add) AppCompatButton mAppcmptbtnAddWorkDtls;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;
    @BindString(R.string.no_data_avlbl_refresh) String mNoDataAvilblMsg;
    @BindString(R.string.refresh_sml) String mRefreshStr;
    @BindString(R.string.birthday_sml) String mBirthdayStr;
    @BindString(R.string.select_birthday_sml) String mSelctBirthday;
    @BindString(R.string.invalid_birthday_sml) String mInvalidBirthday;
    @BindString(R.string.entr_experience_sml) String mEntrExprnc;
    @BindString(R.string.entr_area_of_interest) String mEntrAreaOfIntrst;
    @BindString(R.string.entr_eductn_qualfctn_sml) String mEntrEductnlQualfctn;
    @BindString(R.string.entr_adrs_line_1_sml) String mEntrAdrsLine1;
    @BindString(R.string.entr_city_sml) String mEntrCity;
    @BindString(R.string.entr_state_sml) String mEntrState;
    @BindString(R.string.entr_country_sml) String mEntrCountry;
    @BindString(R.string.entr_pincode_sml) String mEntrPincode;
    @BindString(R.string.entr_schoolname_sml) String mEntrSchoolName;
    @BindString(R.string.entr_subject_sml) String mEntrSubject;
    @BindString(R.string.work_dtls_appear_here) String mWorkDtlsAppearHere;
    @BindString(R.string.add_sml) String mAddStr;
    @BindString(R.string.add_more_sml) String mAddMoreStr;
    @BindString(R.string.select_medium_sml) String mSelctStream;
    @BindString(R.string.select_standard_sml) String mSelctStandr;
    @BindString(R.string.plz_add_work_dtls) String mAddAtleastOneWorkDtls;
    @BindString(R.string.profile_updated_sml) String mProfileUpdatedMsg;

    TextInputLayout mTxtinptlyotSchoolName, mTxtinptlyotSubject;
    TextInputEditText mTxtinptEtSchoolName, mTxtinptEtSubject;

    Spinner mSpnrStream, mSpnrStandr;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    UserSessionManager session;
    String mFirstNameVal, mLastNameVal, mEmailVal = "", mMobileVal, mBirthDayVal,
            mExprncVal = "", mAreaOfIntrstVal = "", mEductnlQualfctnVal = "", mAchievmntsVal = "",
            mAdrsLine1Val, mAdrsLine2Val = "", mCityVal, mStateVal, mCountryVal, mPincodeVal,
            mSchoolNameVal, mSubjectVal;
    int mComeFrom, birthdayAgeCount;
    Calendar mCalendar;
    boolean isBirthDateSelected = false;
    RecyclerView.LayoutManager mLayoutManager;
    RcyclrWorkDtlsAdapter mAdapter;
    ArrayList<MdlTeacherWork> mArrylstMdlTeacherWork;
    public ArrayList<Integer> mArrylstDeletedWorkDtlsIds;
    ArrayAdapter<String> mArryadptrStream, mArryadptrStandr;
    ArrayList<String> mArrylstStreams, mArrylstStandrs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_myprofile, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        session = new UserSessionManager(getActivity());

        mAppBarLayout.addOnOffsetChangedListener(this);

//        mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mtvToolbarTitle, 0, View.INVISIBLE);

        mComeFrom = getArguments().getInt(AppConstants.PUT_EXTRA_COME_FROM, 0);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mLnrlyotAppearHere);

        mTvAppearHere.setText(mWorkDtlsAppearHere);

        mArrylstMdlTeacherWork = new ArrayList<>();
        mArrylstDeletedWorkDtlsIds = new ArrayList<>();
        mAdapter = new RcyclrWorkDtlsAdapter(getActivity(), FrgmntMyProfile.this, mArrylstMdlTeacherWork);
        mRecyclerView.setAdapter(mAdapter);

        mArrylstStreams = new ArrayList<>();
        mArrylstStreams.add(mSelctStream);
        mArrylstStandrs = new ArrayList<>();
        mArrylstStandrs.add(mSelctStandr);

        mArryadptrStream = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mArrylstStreams);
        mArryadptrStandr = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mArrylstStandrs);

        getAllDetails();

        return rootView;
    }

    // Get Teacher, Streams, Standard details
    private void getAllDetails() {

        if (GeneralFunctions.isNetConnected(getActivity())) {
            getTeacherDetails();
            getStreams();
            getStandards();
        } else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mtvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mtvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {

            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {

        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    // Call Retrofit API
    private void getTeacherDetails() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        APITeacher objApiTeacher = APIRetroBuilder.getRetroBuilder(false).create(APITeacher.class);
        Call<MdlTeacherViewRes> callMdlTeacherViewRes = objApiTeacher.callTeacherView(
                GeneralFunctions.getDeviceInfo(getActivity()), session.getUserId());
        callMdlTeacherViewRes.enqueue(new Callback<MdlTeacherViewRes>() {
            @Override
            public void onResponse(Call<MdlTeacherViewRes> call, Response<MdlTeacherViewRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                try {
                    MdlTeacherViewRes objMdlTeacherViewRes = response.body();

                    if(objMdlTeacherViewRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        // Basic Details
                        for(MdlTeacherBasic objMdlTeacherBasic : objMdlTeacherViewRes.getObjMdlTeacherBasic()) {

                            mTvFramelyotTitle.setText(objMdlTeacherBasic.getFirstName().concat(" ").concat(objMdlTeacherBasic.getLastName()));
                            mtvToolbarTitle.setText(objMdlTeacherBasic.getFirstName().concat(" ").concat(objMdlTeacherBasic.getLastName()));
                            mTvFramelyotMobileno.setText(objMdlTeacherBasic.getMobile());
                            mFirstNameVal = objMdlTeacherBasic.getFirstName();
                            mLastNameVal = objMdlTeacherBasic.getLastName();
                            mMobileVal = objMdlTeacherBasic.getMobile();
                            mEmailVal = objMdlTeacherBasic.getEmail();
                            mTxtinptEtEmail.setText(mEmailVal);
                        }

                        // Education & Contact Details
                        for(MdlTeacherContactEductn objMdlTeacherContactEductn : objMdlTeacherViewRes.getObjMdlTeacherContactEductn()) {

                            if(!TextUtils.isEmpty(objMdlTeacherContactEductn.getBirthdate())) {

                                Date dateOfBirth = new Date(Long.parseLong(objMdlTeacherContactEductn.getBirthdate()) * 1000);
                                mCalendar = Calendar.getInstance();
                                mCalendar.setTime(dateOfBirth);
                                isBirthDateSelected = true;
                                calculateAge();

                                mTvBirthdayError.setVisibility(View.GONE);
                                mTvBirthdayLbl.setTextColor(ContextCompat.getColor(getActivity(), R.color.grayA8));
                            }
                            mTxtinptEtExprnc.setText(objMdlTeacherContactEductn.getExperience());
                            mTxtinptEtAreaOfIntrst.setText(objMdlTeacherContactEductn.getAreaOfInterest());
                            mTxtinptEtEductnlQualfctn.setText(objMdlTeacherContactEductn.getEducationalQualification());
                            mTxtinptEtAchievmnts.setText(objMdlTeacherContactEductn.getAchievements());
                            mTxtinptEtAdrsLine1.setText(objMdlTeacherContactEductn.getAddressLine1());
                            mTxtinptEtAdrsLine2.setText(objMdlTeacherContactEductn.getAddressLine2());
                            mTxtinptEtCity.setText(objMdlTeacherContactEductn.getCity());
                            mTxtinptEtState.setText(objMdlTeacherContactEductn.getState());
                            mTxtinptEtCountry.setText(objMdlTeacherContactEductn.getCountry());
                            mTxtinptEtPincode.setText(objMdlTeacherContactEductn.getPincode());
                        }

                        // Work Details
                        mArrylstMdlTeacherWork = new ArrayList<>();
                        mArrylstDeletedWorkDtlsIds = new ArrayList<>();
                        mArrylstMdlTeacherWork.addAll(objMdlTeacherViewRes.getObjMdlTeacherWork());
                        mAdapter = new RcyclrWorkDtlsAdapter(getActivity(), FrgmntMyProfile.this, mArrylstMdlTeacherWork);
                        mRecyclerView.setAdapter(mAdapter);

                        updateAddWorkDetailsButton();
                    } else
                        Snackbar.make(mCrdntrlyot, objMdlTeacherViewRes.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlTeacherViewRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    // Call Retrofit API
    private void getStreams() {

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder(false).create(APIGeneral.class);
        Call<MdlGetStreamsRes> callMdlGetStreamsRes = objApiGeneral.callGetStreams();
        callMdlGetStreamsRes.enqueue(new Callback<MdlGetStreamsRes>() {
            @Override
            public void onResponse(Call<MdlGetStreamsRes> call, Response<MdlGetStreamsRes> response) {

                try {

                    MdlGetStreamsRes objMdlGetStreamsRes = response.body();
                    if(objMdlGetStreamsRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE))
                        mArrylstStreams.addAll(objMdlGetStreamsRes.getArrylstStream());
                    else
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

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder(false).create(APIGeneral.class);
        Call<MdlGetStandardsRes> callMdlGetStandardsRes = objApiGeneral.callGetStandards();
        callMdlGetStandardsRes.enqueue(new Callback<MdlGetStandardsRes>() {
            @Override
            public void onResponse(Call<MdlGetStandardsRes> call, Response<MdlGetStandardsRes> response) {

                try {

                    MdlGetStandardsRes objMdlGetStandardsRes = response.body();
                    if(objMdlGetStandardsRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE))
                        mArrylstStandrs.addAll(objMdlGetStandardsRes.getArrylstStandard());
                    else
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

    // Update Add WorkDetails button text as per ArrayList size (Add/Add more)
    public void updateAddWorkDetailsButton() {

        if(mAdapter.getItemCount() > 0)
            mAppcmptbtnAddWorkDtls.setText(mAddMoreStr);
        else
            mAppcmptbtnAddWorkDtls.setText(mAddStr);
    }

    @OnClick({R.id.rltvlyot_frgmnt_myprofile_birthday, R.id.appcmptbtn_frgmnt_myprofile_workdtls_add})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.rltvlyot_frgmnt_myprofile_birthday:

                if(mPrgrsbrMain.getVisibility() != View.VISIBLE)
                    showDOBCalender();
                break;
            case R.id.appcmptbtn_frgmnt_myprofile_workdtls_add:

                if(mPrgrsbrMain.getVisibility() != View.VISIBLE)
                    showAddWorkDetailsDialog();
                break;
        }
    }

    // Show DatePicker for select BirthDate
    private void showDOBCalender() {

        mCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        /*monthOfYear = monthOfYear + 1;
                        String slctdPreviousDate = monthOfYear + "/" + dayOfMonth + "/" + year;*/

                        // Convert selected Date to Timestamp
                        mCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        isBirthDateSelected = true;
                        calculateAge();
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.show();
    }

    // Calculate Age from selected Date
    private void calculateAge() {

        Date dateOfBirth = new Date(mCalendar.getTimeInMillis());
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar today = Calendar.getInstance();
        birthdayAgeCount = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH))
            birthdayAgeCount--;
        else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))
            birthdayAgeCount--;

        validateBirthday();
    }

    // Show Dialog for Add Work Details
    private void showAddWorkDetailsDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lo_dialog_workdtls);
        Window mWindow = dialog.getWindow();
        if (mWindow != null)
            mWindow.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        mTxtinptlyotSchoolName = (TextInputLayout) dialog.findViewById(R.id.txtinputlyot_dialog_workdtls_schoolname);
        mTxtinptlyotSubject = (TextInputLayout) dialog.findViewById(R.id.txtinputlyot_dialog_workdtls_subject);
        mTxtinptEtSchoolName = (TextInputEditText) dialog.findViewById(R.id.txtinptet_dialog_workdtls_schoolname);
        mTxtinptEtSubject = (TextInputEditText) dialog.findViewById(R.id.txtinptet_dialog_workdtls_subject);
        mSpnrStream = (Spinner) dialog.findViewById(R.id.spnr_dialog_workdtls_stream);
        mSpnrStandr = (Spinner) dialog.findViewById(R.id.spnr_dialog_workdtls_standr);

        mSpnrStream.setAdapter(mArryadptrStream);
        mSpnrStandr.setAdapter(mArryadptrStandr);

        dialog.findViewById(R.id.appcmptbtn_dialog_workdtls_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.appcmptbtn_dialog_workdtls_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAddWorkDetailsValidation();
            }

            private void checkAddWorkDetailsValidation() {

                if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotSchoolName, mTxtinptEtSchoolName, mEntrSchoolName)) // SchoolName
                    return;

                if(!validateStream())
                    return;

                if(!validateStandard())
                    return;

                if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotSubject, mTxtinptEtSubject, mEntrSubject)) // Subject
                    return;

                addWorkDetails();
            }

            // Validate Stream spinner
            private boolean validateStream() {

                if(mArrylstStreams.size() <= 1) {

                    dialog.dismiss();
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

                    dialog.dismiss();
                    showRefreshSnackbar();
                    return false;
                } else if(mSpnrStandr.getSelectedItemPosition() == 0) {

                    GeneralFunctions.showToastSingle(getActivity(), mSelctStandr, Toast.LENGTH_SHORT);
                    return false;
                } else
                    return true;
            }

            // Add user entered Work Details in to Array and Update RecyclerView
            private void addWorkDetails() {

                mSchoolNameVal = mTxtinptEtSchoolName.getText().toString().trim();
                mSubjectVal = mTxtinptEtSubject.getText().toString().trim();

                MdlTeacherWork objMdlTeacherWork = new MdlTeacherWork();
                objMdlTeacherWork.setTiInstituteTitle(mSchoolNameVal);
                objMdlTeacherWork.setTicStream(mSpnrStream.getSelectedItem().toString());
                objMdlTeacherWork.setTicStd(mSpnrStandr.getSelectedItem().toString());
                objMdlTeacherWork.setTicSubject(mSubjectVal);
                mAdapter.addItem(objMdlTeacherWork);

                dialog.dismiss();

                updateAddWorkDetailsButton();
            }
        });
        dialog.show();
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

    // Check all validation of fields and call API
    private void checkAllValidation() {

        if(!validateBirthday())
            return;

        if(!TextUtils.isEmpty(mTxtinptEtEmail.getText().toString().trim())) { // Email
            if (!ValidationUtils.validateEmailFormat(getActivity(), mTxtinptlyotEmail, mTxtinptEtEmail)) {
                return;
            }
        }

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotAdrsLine1, mTxtinptEtAdrsLine1, mEntrAdrsLine1)) // Address Line 1
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotCity, mTxtinptEtCity, mEntrCity)) // City
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotState, mTxtinptEtState, mEntrState)) // State
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotCountry, mTxtinptEtCountry, mEntrCountry)) // Country
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotPincode, mTxtinptEtPincode, mEntrPincode)) // PinCode
            return;

        if(!validatePincode())
            return;

        if(!validateWorkDetails())
            return;

        /*if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotExprnc, mTxtinptEtExprnc, mEntrExprnc)) // Experience
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotAreaOfIntrst, mTxtinptEtAreaOfIntrst, mEntrAreaOfIntrst)) // Area Of Interest
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotEductnlQualfctn, mTxtinptEtEductnlQualfctn, mEntrEductnlQualfctn)) // Education Qualification
            return;*/

        if (GeneralFunctions.isNetConnected(getActivity()))
            callTeacherEditAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Validation for BirthDay
    private boolean validateBirthday() {

        if(!isBirthDateSelected){

            if(mTvBirthday.getText().toString().equalsIgnoreCase(mBirthdayStr)) {

                mTvBirthdayLbl.setVisibility(View.VISIBLE);
                mTvBirthdayLbl.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryOrange));

                mTvBirthdayError.setVisibility(View.VISIBLE);
                mTvBirthdayError.setText(mSelctBirthday);

                isBirthDateSelected = false;
                return false;
            }
        } else if (birthdayAgeCount < 18) {

            mTvBirthdayLbl.setVisibility(View.VISIBLE);
            mTvBirthdayLbl.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryOrange));

            mTvBirthday.setText(mBirthdayStr);
            mTvBirthday.setTextColor(ContextCompat.getColor(getActivity(), R.color.grayA8));

            mTvBirthdayError.setVisibility(View.VISIBLE);
            mTvBirthdayError.setText(mInvalidBirthday);

            isBirthDateSelected = false;
            return false;
        } else {

            mTvBirthdayError.setVisibility(View.GONE);
            mTvBirthdayLbl.setTextColor(ContextCompat.getColor(getActivity(), R.color.grayA8));

            long slctdDateTimestamp = mCalendar.getTimeInMillis()/1000;
            mTvBirthday.setText(GeneralFunctions.getDateFromTimestamp(slctdDateTimestamp));
            mTvBirthday.setTextColor(Color.BLACK);

            isBirthDateSelected = true;
            return true;
        }
        return false;
    }

    // Check PinCode length validation for field
    private boolean validatePincode() {

        if (mTxtinptEtPincode.getText().toString().trim().length() == 6) {
            mTxtinptlyotPincode.setError(null);
            mTxtinptlyotPincode.setErrorEnabled(false);
            return true;
        } else {
            mTxtinptlyotPincode.setErrorEnabled(true);
            mTxtinptlyotPincode.setError(getString(R.string.invalid_pincode));
            ValidationUtils.requestFocus(getActivity(), mTxtinptEtPincode);
            return false;
        }
    }

    // Validation for Work Details Array
    private boolean validateWorkDetails() {

        if(mAdapter.getItemCount() == 0) {
            Snackbar.make(mCrdntrlyot, mAddAtleastOneWorkDtls, Snackbar.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void callTeacherEditAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
//        mFirstNameVal =  mTxtinptEtFirstName.getText().toString().trim();
//        mFirstNameVal = Character.toUpperCase(mFirstNameVal.charAt(0))+mFirstNameVal.substring(1); // First Character Uppercase
//        mLastNameVal = mTxtinptEtLastName.getText().toString().trim();
//        mLastNameVal = Character.toUpperCase(mLastNameVal.charAt(0)) + mLastNameVal.substring(1); // First Character Uppercase
        mEmailVal = mTxtinptEtEmail.getText().toString().trim();
        mBirthDayVal = mTvBirthday.getText().toString().trim();
        mExprncVal = mTxtinptEtExprnc.getText().toString().trim();
        mAreaOfIntrstVal = mTxtinptEtAreaOfIntrst.getText().toString().trim();
        mEductnlQualfctnVal = mTxtinptEtEductnlQualfctn.getText().toString().trim();
        mAchievmntsVal = mTxtinptEtAchievmnts.getText().toString().trim();
        mAdrsLine1Val = mTxtinptEtAdrsLine1.getText().toString().trim();
        mAdrsLine2Val = mTxtinptEtAdrsLine2.getText().toString().trim();
        mCityVal = mTxtinptEtCity.getText().toString().trim();
        mCityVal = Character.toUpperCase(mCityVal.charAt(0)) + mCityVal.substring(1); // First Character Uppercase
        mStateVal = mTxtinptEtState.getText().toString().trim();
        mCountryVal = mTxtinptEtCountry.getText().toString().trim();
        mPincodeVal = mTxtinptEtPincode.getText().toString().trim();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String workJSONData = gson.toJson(mAdapter.getAllItems());
        String workDeletedIds = TextUtils.join(",", mArrylstDeletedWorkDtlsIds);

        APITeacher objApiTeacher = APIRetroBuilder.getRetroBuilder(false).create(APITeacher.class);
        Call<MdlTeacherEditRes> callMdlTeacherEditRes = objApiTeacher.callTeacherEdit(GeneralFunctions.getDeviceInfo(getActivity()),
                session.getUserId(), mFirstNameVal, "", mLastNameVal, mEmailVal, mMobileVal, mBirthDayVal,
                mExprncVal, mAreaOfIntrstVal, mEductnlQualfctnVal, mAchievmntsVal,
                mAdrsLine1Val, mAdrsLine2Val, mCityVal, mStateVal, mCountryVal, mPincodeVal, workDeletedIds, workJSONData);
        callMdlTeacherEditRes.enqueue(new Callback<MdlTeacherEditRes>() {
            @Override
            public void onResponse(Call<MdlTeacherEditRes> call, Response<MdlTeacherEditRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                try {
                    MdlTeacherEditRes objMdlRegstrRes = response.body();
                    if(objMdlRegstrRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        GeneralFunctions.hideSoftKeyboard(getActivity());
                        GeneralFunctions.showToastSingle(getActivity(), mProfileUpdatedMsg, Toast.LENGTH_LONG);
//                        Snackbar.make(mCrdntrlyot, mProfileUpdatedMsg, Snackbar.LENGTH_SHORT).show();
                        if(mComeFrom == AppConstants.COME_FROM_SPECIMEN_COPY) {

                            MenuItem mMenuItem = ((NavgtnDrwrMain) getActivity()).mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_specimen_copy);
                            ((NavgtnDrwrMain) getActivity()).onNavigationItemSelected(mMenuItem);
                        } else
                            getActivity().onBackPressed();
                    } else
                        Snackbar.make(mCrdntrlyot, objMdlRegstrRes.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlTeacherEditRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });
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

                if (GeneralFunctions.isNetConnected(getActivity()))
                    checkAllValidation();
                else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
