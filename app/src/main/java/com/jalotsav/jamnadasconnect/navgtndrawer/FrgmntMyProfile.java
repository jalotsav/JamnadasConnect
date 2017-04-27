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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.SignUp;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherBasic;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherContactEductn;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherEditRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherViewRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherWork;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.jalotsav.jamnadasconnect.retrofitapi.APITeacher;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 4/26/2017.
 */

public class FrgmntMyProfile extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.cordntrlyot_frgmnt_myprofile) CoordinatorLayout mCrdntrlyot;

    @BindView(R.id.lnrlyot_frgmnt_myprofile_framelyot_title) LinearLayout mTitleContainer;
    @BindView(R.id.tv_frgmnt_myprofile_toolbar_title) TextView mtvToolbarTitle;
    @BindView(R.id.appbarlyot_frgmnt_myprofile) AppBarLayout mAppBarLayout;
//    @BindView(R.id.toolbar_frgmnt_myprofile) Toolbar mToolbar;

    @BindView(R.id.tv_frgmnt_myprofile_framelyot_title) TextView mTvFramelyotTitle;
    @BindView(R.id.tv_frgmnt_myprofile_framelyot_mobileno) TextView mTvFramelyotMobileno;

    @BindView(R.id.txtinputlyot_frgmnt_myprofile_exprnc) TextInputLayout mTxtinptlyotExprnc;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_areaofintrst) TextInputLayout mTxtinptlyotAreaOfIntrst;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_eductnlqualfctn) TextInputLayout mTxtinptlyotEductnlQualfctn;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_adrsline1) TextInputLayout mTxtinptlyotAdrsLine1;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_city) TextInputLayout mTxtinptlyotCity;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_state) TextInputLayout mTxtinptlyotState;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_country) TextInputLayout mTxtinptlyotCountry;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_pincode) TextInputLayout mTxtinptlyotPincode;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_schoolname) TextInputLayout mTxtinptlyotSchoolName;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_stream) TextInputLayout mTxtinptlyotStream;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_standr) TextInputLayout mTxtinptlyotStandr;
    @BindView(R.id.txtinputlyot_frgmnt_myprofile_subject) TextInputLayout mTxtinptlyotSubject;

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
    @BindView(R.id.txtinptet_frgmnt_myprofile_schoolname) TextInputEditText mTxtinptEtSchoolName;
    @BindView(R.id.txtinptet_frgmnt_myprofile_stream) TextInputEditText mTxtinptEtStream;
    @BindView(R.id.txtinptet_frgmnt_myprofile_standr) TextInputEditText mTxtinptEtStandr;
    @BindView(R.id.txtinptet_frgmnt_myprofile_subject) TextInputEditText mTxtinptEtSubject;

    @BindView(R.id.prgrsbr_frgmnt_myprofile) ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.entr_experience_sml) String mEntrExprnc;
    @BindString(R.string.entr_area_of_interest) String mEntrAreaOfIntrst;
    @BindString(R.string.entr_eductn_qualfctn_sml) String mEntrEductnlQualfctn;
    @BindString(R.string.entr_adrs_line_1_sml) String mEntrAdrsLine1;
    @BindString(R.string.entr_city_sml) String mEntrCity;
    @BindString(R.string.entr_state_sml) String mEntrState;
    @BindString(R.string.entr_country_sml) String mEntrCountry;
    @BindString(R.string.entr_pincode_sml) String mEntrPincode;
    @BindString(R.string.entr_schoolname_sml) String mEntrSchoolName;
    @BindString(R.string.entr_stream_sml) String mEntrStream;
    @BindString(R.string.entr_standr_sml) String mEntrStandr;
    @BindString(R.string.entr_subject_sml) String mEntrSubject;
    @BindString(R.string.profile_updated_sml) String mProfileUpdatedMsg;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    UserSessionManager session;
    String mFirstNameVal, mLastNameVal, mEmailVal = "", mMobileVal,
            mExprncVal, mAreaOfIntrstVal, mEductnlQualfctnVal, mAchievmntsVal = "",
            mAdrsLine1Val, mAdrsLine2Val = "", mCityVal, mStateVal, mCountryVal, mPincodeVal,
            mSchoolNameVal, mStreamVal, mStandrVal, mSubjectVal;
    int workJsonTIId;

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

        if (GeneralFunctions.isNetConnected(getActivity()))
            getTeacherDetails();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();

        return rootView;
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
        APITeacher objApiTeacher = APIRetroBuilder.getRetroBuilder().create(APITeacher.class);
        Call<MdlTeacherViewRes> callMdlTeacherViewRes = objApiTeacher.callTeacherView(
                GeneralFunctions.getDeviceInfo(getActivity()), session.getUserId());
        callMdlTeacherViewRes.enqueue(new Callback<MdlTeacherViewRes>() {
            @Override
            public void onResponse(Call<MdlTeacherViewRes> call, Response<MdlTeacherViewRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
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
                    }

                    // Education & Contact Details
                    for(MdlTeacherContactEductn objMdlTeacherContactEductn : objMdlTeacherViewRes.getObjMdlTeacherContactEductn()) {

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
                    for(MdlTeacherWork objMdlTeacherWork : objMdlTeacherViewRes.getObjMdlTeacherWork()) {

                        workJsonTIId = objMdlTeacherWork.getTiId();
                        mTxtinptEtSchoolName.setText(objMdlTeacherWork.getTiInstituteTitle());
                        mTxtinptEtStream.setText(objMdlTeacherWork.getTicStream());
                        mTxtinptEtStandr.setText(objMdlTeacherWork.getTicStd());
                        mTxtinptEtSubject.setText(objMdlTeacherWork.getTicSubject());
                    }
                } else
                    Snackbar.make(mCrdntrlyot, objMdlTeacherViewRes.getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MdlTeacherViewRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    // Check all validation of fields and call API
    private void checkAllValidation() {

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotExprnc, mTxtinptEtExprnc, mEntrExprnc)) // Experience
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotAreaOfIntrst, mTxtinptEtAreaOfIntrst, mEntrAreaOfIntrst)) // Area Of Interest
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotEductnlQualfctn, mTxtinptEtEductnlQualfctn, mEntrEductnlQualfctn)) // Education Qualification
            return;

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

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotSchoolName, mTxtinptEtSchoolName, mEntrSchoolName)) // SchoolName
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotStream, mTxtinptEtStream, mEntrStream)) // Stream
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotStandr, mTxtinptEtStandr, mEntrStandr)) // Standard
            return;

        if(!validateStandard())
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotSubject, mTxtinptEtSubject, mEntrSubject)) // Subject
            return;

        if (GeneralFunctions.isNetConnected(getActivity()))
            callTeacherEditAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
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

    // Check Standard input digits validation for field
    private boolean validateStandard() {

        int standrVal = Integer.parseInt(mTxtinptEtStandr.getText().toString().trim());
        if (standrVal >0 && standrVal <=12) {
            mTxtinptlyotStandr.setError(null);
            mTxtinptlyotStandr.setErrorEnabled(false);
            return true;
        } else {
            mTxtinptlyotStandr.setErrorEnabled(true);
            mTxtinptlyotStandr.setError(getString(R.string.invalid_standr));
            ValidationUtils.requestFocus(getActivity(), mTxtinptEtStandr);
            return false;
        }
    }

    private void callTeacherEditAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
//        mFirstNameVal =  mTxtinptEtFirstName.getText().toString().trim();
//        mFirstNameVal = Character.toUpperCase(mFirstNameVal.charAt(0))+mFirstNameVal.substring(1); // First Character Uppercase
//        mLastNameVal = mTxtinptEtLastName.getText().toString().trim();
//        mLastNameVal = Character.toUpperCase(mLastNameVal.charAt(0)) + mLastNameVal.substring(1); // First Character Uppercase
//        mEmailVal = mTxtinptEtEmail.getText().toString().trim();
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
        mSchoolNameVal = mTxtinptEtSchoolName.getText().toString().trim();
        mStreamVal = mTxtinptEtStream.getText().toString().trim();
        mStandrVal = mTxtinptEtStandr.getText().toString().trim();
        mSubjectVal = mTxtinptEtSubject.getText().toString().trim();
        ArrayList<MdlTeacherWork> arrylstTeacherWork = new ArrayList<>();
        MdlTeacherWork objMdlTeacherWork = new MdlTeacherWork();
        objMdlTeacherWork.setTiId(workJsonTIId);
        objMdlTeacherWork.setTiInstituteTitle(mSchoolNameVal);
        objMdlTeacherWork.setTicStream(mStreamVal);
        objMdlTeacherWork.setTicStd(mStandrVal);
        objMdlTeacherWork.setTicSubject(mSubjectVal);
        arrylstTeacherWork.add(objMdlTeacherWork);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String workJSONData = gson.toJson(arrylstTeacherWork);

        APITeacher objApiTeacher = APIRetroBuilder.getRetroBuilder().create(APITeacher.class);
        Call<MdlTeacherEditRes> callMdlTeacherEditRes = objApiTeacher.callTeacherEdit(GeneralFunctions.getDeviceInfo(getActivity()),
                session.getUserId(), mFirstNameVal, "", mLastNameVal, mEmailVal, mMobileVal, "",
                mExprncVal, mAreaOfIntrstVal, mEductnlQualfctnVal, mAchievmntsVal,
                mAdrsLine1Val, mAdrsLine2Val, mCityVal, mStateVal, mCountryVal, mPincodeVal, "", workJSONData);
        callMdlTeacherEditRes.enqueue(new Callback<MdlTeacherEditRes>() {
            @Override
            public void onResponse(Call<MdlTeacherEditRes> call, Response<MdlTeacherEditRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                MdlTeacherEditRes objMdlRegstrRes = response.body();
                if(objMdlRegstrRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                    Snackbar.make(mCrdntrlyot, mProfileUpdatedMsg, Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(mCrdntrlyot, objMdlRegstrRes.getMessage(), Snackbar.LENGTH_LONG).show();
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
        inflater.inflate(R.menu.menu_done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_done:

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
