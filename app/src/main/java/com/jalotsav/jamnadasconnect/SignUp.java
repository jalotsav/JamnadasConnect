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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.registration.MdlRegistrationRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherEditRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherWork;
import com.jalotsav.jamnadasconnect.navgtndrawer.NavgtnDrwrMain;
import com.jalotsav.jamnadasconnect.retrofitapi.APIGeneral;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.jalotsav.jamnadasconnect.retrofitapi.APITeacher;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 4/15/2017.
 */

public class SignUp extends AppCompatActivity {

    @BindView(R.id.cordntrlyot_signup)
    CoordinatorLayout mCrdntrlyot;

    @BindView(R.id.txtinputlyot_signup_firstname) TextInputLayout mTxtinptlyotFirstName;
    @BindView(R.id.txtinputlyot_signup_lastname) TextInputLayout mTxtinptlyotLastName;
    @BindView(R.id.txtinputlyot_signup_schoolname) TextInputLayout mTxtinptlyotSchoolName;
    /*@BindView(R.id.txtinputlyot_signup_stream) TextInputLayout mTxtinptlyotStream;
    @BindView(R.id.txtinputlyot_signup_standr) TextInputLayout mTxtinptlyotStandr;
    @BindView(R.id.txtinputlyot_signup_subject) TextInputLayout mTxtinptlyotSubject;*/
    @BindView(R.id.txtinputlyot_signup_city) TextInputLayout mTxtinptlyotCity;
    @BindView(R.id.txtinputlyot_signup_mobileno) TextInputLayout mTxtinptlyotMobile;
//    @BindView(R.id.txtinputlyot_signup_email) TextInputLayout mTxtinptlyotEmail;
    @BindView(R.id.txtinputlyot_signup_password) TextInputLayout mTxtinptlyotPaswrd;

    @BindView(R.id.txtinptet_signup_firstname) TextInputEditText mTxtinptEtFirstName;
    @BindView(R.id.txtinptet_signup_lastname) TextInputEditText mTxtinptEtLastName;
    @BindView(R.id.txtinptet_signup_schoolname) TextInputEditText mTxtinptEtSchoolName;
    /*@BindView(R.id.txtinptet_signup_stream) TextInputEditText mTxtinptEtStream;
    @BindView(R.id.txtinptet_signup_standr) TextInputEditText mTxtinptEtStandr;
    @BindView(R.id.txtinptet_signup_subject) TextInputEditText mTxtinptEtSubject;*/
    @BindView(R.id.txtinptet_signup_city) TextInputEditText mTxtinptEtCity;
    @BindView(R.id.txtinptet_signup_mobileno) TextInputEditText mTxtinptEtMobile;
//    @BindView(R.id.txtinptet_signup_email) TextInputEditText mTxtinptEtEmail;
    @BindView(R.id.txtinptet_signup_password) TextInputEditText mTxtinptEtPaswrd;

    @BindView(R.id.prgrsbr_signup)
    ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.entr_firstname_sml) String mEntrFirstName;
    @BindString(R.string.entr_lastname_sml) String mEntrLastName;
    @BindString(R.string.entr_schoolname_sml) String mEntrSchoolName;
    /*@BindString(R.string.entr_stream_sml) String mEntrStream;
    @BindString(R.string.entr_standr_sml) String mEntrStandr;
    @BindString(R.string.entr_subject_sml) String mEntrSubject;*/
    @BindString(R.string.entr_city_sml) String mEntrCity;
    @BindString(R.string.mobileno_verfd_sucsfly) String mMobileVerifedMsg;
    @BindString(R.string.mobileno_not_verfd) String mMobileNotVerifedMsg;
    @BindString(R.string.sucsfly_regstr_sml) String mSucsflyRegstrnMsg;

    String mFirstNameVal, mLastNameVal, mEmailVal = "", mMobileVal, mSchoolNameVal, mStreamVal = "", mStandrVal = "", mSubjectVal = "", mCityVal;
    UserSessionManager session;
    boolean isVerifyMobileCall = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_signup);
        ButterKnife.bind(this);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(true);

        session = new UserSessionManager(this);

        isVerifyMobileCall = true;
    }

    @OnClick({R.id.appcmptbtn_signup})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.appcmptbtn_signup:

                if(GeneralFunctions.isNetConnected(this))
                    checkAllValidation();
                else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    // Check all validation of fields and call API
    private void checkAllValidation() {

        if (!ValidationUtils.validateEmpty(this, mTxtinptlyotFirstName, mTxtinptEtFirstName, mEntrFirstName)) // FirstName
            return;

        if (!ValidationUtils.validateEmpty(this, mTxtinptlyotLastName, mTxtinptEtLastName, mEntrLastName)) // LastName
            return;

        if (!ValidationUtils.validateEmpty(this, mTxtinptlyotSchoolName, mTxtinptEtSchoolName, mEntrSchoolName)) // SchoolName
            return;

        /*if (!ValidationUtils.validateEmpty(this, mTxtinptlyotStream, mTxtinptEtStream, mEntrStream)) // Stream
            return;

        if (!ValidationUtils.validateEmpty(this, mTxtinptlyotStandr, mTxtinptEtStandr, mEntrStandr)) // Standard
            return;

        if(!validateStandard())
            return;

        if (!ValidationUtils.validateEmpty(this, mTxtinptlyotSubject, mTxtinptEtSubject, mEntrSubject)) // Subject
            return;*/

        if (!ValidationUtils.validateEmpty(this, mTxtinptlyotCity, mTxtinptEtCity, mEntrCity)) // City
            return;

        if (!ValidationUtils.validateMobile(this, mTxtinptlyotMobile, mTxtinptEtMobile)) // Mobile
            return;

        /*if(!TextUtils.isEmpty(mTxtinptEtEmail.getText().toString().trim())) { // Email
            if (!ValidationUtils.validateEmailFormat(this, mTxtinptlyotEmail, mTxtinptEtEmail)) {
                return;
            }
        }*/

        if (!ValidationUtils.validatePassword(this, mTxtinptlyotPaswrd, mTxtinptEtPaswrd)) // Password
            return;

        if(!isVerifyMobileCall) {

            isVerifyMobileCall = true;
            Intent intntVerifyMobileNo = new Intent(this, VerifyMobileNo.class);
            intntVerifyMobileNo.putExtra(AppConstants.KEY_MOBILE, mTxtinptEtMobile.getText().toString().trim());
            startActivityForResult(intntVerifyMobileNo, AppConstants.REQUEST_VERFCTN_MOBILENO);
        } else
            callSignupAPI();
    }

    // Check Standard input digits validation for field
    /*private boolean validateStandard() {

        int standrVal = Integer.parseInt(mTxtinptEtStandr.getText().toString().trim());
        if (standrVal >0 && standrVal <=12) {
            mTxtinptlyotStandr.setError(null);
            mTxtinptlyotStandr.setErrorEnabled(false);
            return true;
        } else {
            mTxtinptlyotStandr.setErrorEnabled(true);
            mTxtinptlyotStandr.setError(getString(R.string.invalid_standr));
            ValidationUtils.requestFocus(this, mTxtinptEtStandr);
            return false;
        }
    }*/

    // Call Retrofit API
    private void callSignupAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        mFirstNameVal =  mTxtinptEtFirstName.getText().toString().trim();
        mFirstNameVal = Character.toUpperCase(mFirstNameVal.charAt(0))+mFirstNameVal.substring(1); // First Character Uppercase
        mLastNameVal = mTxtinptEtLastName.getText().toString().trim();
        mLastNameVal = Character.toUpperCase(mLastNameVal.charAt(0)) + mLastNameVal.substring(1); // First Character Uppercase
        mSchoolNameVal = mTxtinptEtSchoolName.getText().toString().trim();
        /*mStreamVal = mTxtinptEtStream.getText().toString().trim();
        mStandrVal = mTxtinptEtStandr.getText().toString().trim();
        mSubjectVal = mTxtinptEtSubject.getText().toString().trim();*/
        mCityVal = mTxtinptEtCity.getText().toString().trim();
        mCityVal = Character.toUpperCase(mCityVal.charAt(0)) + mCityVal.substring(1); // First Character Uppercase
        mMobileVal = mTxtinptEtMobile.getText().toString().trim();
//        mEmailVal = mTxtinptEtEmail.getText().toString().trim();
        String passwordVal = mTxtinptEtPaswrd.getText().toString().trim();

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder().create(APIGeneral.class);
        Call<MdlRegistrationRes> callMdlRegstrtnRes = objApiGeneral.callRegistration(
                mFirstNameVal, mLastNameVal, mMobileVal, mEmailVal, passwordVal, GeneralFunctions.getDeviceInfo(this));
        callMdlRegstrtnRes.enqueue(new Callback<MdlRegistrationRes>() {
            @Override
            public void onResponse(Call<MdlRegistrationRes> call, Response<MdlRegistrationRes> response) {

//                mPrgrsbrMain.setVisibility(View.GONE);
                MdlRegistrationRes objMdlRegstrRes = response.body();
                if(objMdlRegstrRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

//                    Toast.makeText(SignUp.this, objMdlRegstrRes.getMessage(), Toast.LENGTH_SHORT).show();

                    session.setUserId(objMdlRegstrRes.getUser_id());
                    session.setFirstName(mFirstNameVal);
                    session.setLastName(mLastNameVal);
                    session.setMobile(mMobileVal);
                    session.setEmail(mEmailVal);

                    callTeacherEditAPI();
                } else {

                    mPrgrsbrMain.setVisibility(View.GONE);
                    Snackbar.make(mCrdntrlyot, objMdlRegstrRes.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlRegistrationRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void callTeacherEditAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        ArrayList<MdlTeacherWork> arrylstTeacherWork = new ArrayList<>();
        MdlTeacherWork objMdlTeacherWork = new MdlTeacherWork();
        objMdlTeacherWork.setTiInstituteTitle(mSchoolNameVal);
        objMdlTeacherWork.setTicStream(mStreamVal);
        objMdlTeacherWork.setTicStd(mStandrVal);
        objMdlTeacherWork.setTicSubject(mSubjectVal);
        arrylstTeacherWork.add(objMdlTeacherWork);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String workJSONData = gson.toJson(arrylstTeacherWork);

        APITeacher objApiTeacher = APIRetroBuilder.getRetroBuilder().create(APITeacher.class);
        Call<MdlTeacherEditRes> callMdlLoginRes = objApiTeacher.callTeacherEdit(GeneralFunctions.getDeviceInfo(this),
                session.getUserId(), mFirstNameVal, "", mLastNameVal, mEmailVal, mMobileVal, "", "", "", "", "", "", "", mCityVal, "", "", "", "", workJSONData);
        callMdlLoginRes.enqueue(new Callback<MdlTeacherEditRes>() {
            @Override
            public void onResponse(Call<MdlTeacherEditRes> call, Response<MdlTeacherEditRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                MdlTeacherEditRes objMdlRegstrRes = response.body();
                if(objMdlRegstrRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                    Toast.makeText(SignUp.this, mSucsflyRegstrnMsg, Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(SignUp.this, NavgtnDrwrMain.class));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AppConstants.REQUEST_VERFCTN_MOBILENO) {

            if(resultCode == RESULT_OK)
                Snackbar.make(mCrdntrlyot, mMobileVerifedMsg, Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(mCrdntrlyot, mMobileNotVerifedMsg, Snackbar.LENGTH_SHORT).show();

            callSignupAPI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                break;
            case R.id.action_done:

                if (GeneralFunctions.isNetConnected(this))
                    checkAllValidation();
                else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
