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

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.bookrequest.MdlBookReqstAddRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherDataAvialbltyRes;
import com.jalotsav.jamnadasconnect.retrofitapi.APIBookRequest;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.jalotsav.jamnadasconnect.retrofitapi.APITeacher;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 4/27/2017.
 */

public class FrgmntSpecimenCopy extends Fragment {

    private static final String TAG = FrgmntSpecimenCopy.class.getSimpleName();

    @BindView(R.id.cordntrlyot_frgmnt_specimncopy) CoordinatorLayout mCrdntrlyot;

    @BindView(R.id.txtinputlyot_frgmnt_specimncopy_bookname) TextInputLayout mTxtinptlyotBookName;
    @BindView(R.id.txtinputlyot_frgmnt_specimncopy_stream) TextInputLayout mTxtinptlyotStream;
    @BindView(R.id.txtinputlyot_frgmnt_specimncopy_standr) TextInputLayout mTxtinptlyotStandr;

    @BindView(R.id.txtinptet_frgmnt_specimncopy_bookname) TextInputEditText mTxtinptEtBookName;
    @BindView(R.id.txtinptet_frgmnt_specimncopy_stream) TextInputEditText mTxtinptEtStream;
    @BindView(R.id.txtinptet_frgmnt_specimncopy_standr) TextInputEditText mTxtinptEtStandr;
    @BindView(R.id.txtinptet_frgmnt_specimncopy_comnts) TextInputEditText mTxtinptEtComnts;

    @BindView(R.id.prgrsbr_frgmnt_specimncopy) ProgressBar mPrgrsbrMain;

    @BindString(R.string.please_wait_3dots) String mPleaseWait;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;
    @BindString(R.string.entr_book_name_sml) String mEntrBookName;
    @BindString(R.string.entr_stream_sml) String mEntrStream;
    @BindString(R.string.entr_standr_sml) String mEntrStandr;
    @BindString(R.string.invalid_standr) String mInvalidStandr;

    UserSessionManager session;
    ProgressDialog mPrgrsDialog;
    boolean mTeacherDataAvaibltyStatus;
    String mBookNameVal, mStreamVal, mStandrVal, mComntsVal = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_specimen_copy, container, false);
        ButterKnife.bind(this, rootView);

        session = new UserSessionManager(getActivity());

        if (GeneralFunctions.isNetConnected(getActivity()))
            getTeacherProfileDetails();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();

        return rootView;
    }

    // Call Retrofit API
    private void getTeacherProfileDetails() {

        mPrgrsDialog = new ProgressDialog(getActivity());
        mPrgrsDialog.setMessage(mPleaseWait);
        mPrgrsDialog.setCancelable(false);
        mPrgrsDialog.show();

        APITeacher objApiTeacher = APIRetroBuilder.getRetroBuilder(false).create(APITeacher.class);
        Call<MdlTeacherDataAvialbltyRes> callMdlTeacherDataAvailbltyRes = objApiTeacher.callTeacherDataAvailability(
                GeneralFunctions.getDeviceInfo(getActivity()), session.getUserId());
        callMdlTeacherDataAvailbltyRes.enqueue(new Callback<MdlTeacherDataAvialbltyRes>() {
            @Override
            public void onResponse(Call<MdlTeacherDataAvialbltyRes> call, Response<MdlTeacherDataAvialbltyRes> response) {

                mPrgrsDialog.dismiss();
                try {

                    MdlTeacherDataAvialbltyRes objMdlTeacherDataAvailbltyRes = response.body();

                    if(objMdlTeacherDataAvailbltyRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_FALSE)) {
                        mTeacherDataAvaibltyStatus = false;
                        showAlertDialogEditProfile();
                    } else mTeacherDataAvaibltyStatus = true;
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Toast.makeText(getActivity(), mInternalPrblmMsg, Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<MdlTeacherDataAvialbltyRes> call, Throwable t) {

                mPrgrsDialog.dismiss();
                Toast.makeText(getActivity(), mServerPrblmMsg, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    // Show AlertDialog for "Go To Profile"
    private void showAlertDialogEditProfile() {

        AlertDialog.Builder alrtDlg = new AlertDialog.Builder(getActivity());
        alrtDlg.setTitle(getString(R.string.edit_profile_sml));
        alrtDlg.setMessage(getString(R.string.edit_profile_miss_data_msg));
        alrtDlg.setCancelable(false);
        alrtDlg.setNegativeButton(getString(R.string.cancel_sml).toUpperCase(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                getActivity().onBackPressed();
            }
        });
        alrtDlg.setPositiveButton(getString(R.string.goto_profile_sml).toUpperCase(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                ((NavgtnDrwrMain) getActivity()).mBundle.putInt(AppConstants.PUT_EXTRA_COME_FROM, AppConstants.COME_FROM_SPECIMEN_COPY);
                MenuItem mMenuItem = ((NavgtnDrwrMain) getActivity()).mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_teacher_profile);
                ((NavgtnDrwrMain) getActivity()).onNavigationItemSelected(mMenuItem);
            }
        });

        alrtDlg.show();
    }

    @OnClick({R.id.fab_frgmnt_specimncopy_done})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.fab_frgmnt_specimncopy_done:

                if(mPrgrsbrMain.getVisibility() != View.VISIBLE && mTeacherDataAvaibltyStatus) {
                    if (GeneralFunctions.isNetConnected(getActivity()))
                        checkAllValidation();
                    else
                        Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    // Check all validation of fields and call API
    private void checkAllValidation() {

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotBookName, mTxtinptEtBookName, mEntrBookName)) // Book Name
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotStream, mTxtinptEtStream, mEntrStream)) // Stream
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotStandr, mTxtinptEtStandr, mEntrStandr)) // Standard
            return;

        if(!validateStandard())
            return;

        if (GeneralFunctions.isNetConnected(getActivity()))
            callBookRequestAddAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();

    }

    // Call Retrofit API
    private void callBookRequestAddAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        mBookNameVal = mTxtinptEtBookName.getText().toString().trim();
        mStreamVal = mTxtinptEtStream.getText().toString().trim();
        mStandrVal = mTxtinptEtStandr.getText().toString().trim();
        mComntsVal = mTxtinptEtComnts.getText().toString().trim();

        APIBookRequest objApiBookReqst = APIRetroBuilder.getRetroBuilder(false).create(APIBookRequest.class);
        Call<MdlBookReqstAddRes> callMdlBookReqstAddRes = objApiBookReqst.callBookReqstAdd(GeneralFunctions.getDeviceInfo(getActivity()),
                session.getUserId(), mBookNameVal, mStreamVal, mStandrVal);
        callMdlBookReqstAddRes.enqueue(new Callback<MdlBookReqstAddRes>() {
            @Override
            public void onResponse(Call<MdlBookReqstAddRes> call, Response<MdlBookReqstAddRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                try {
                    MdlBookReqstAddRes objMdlBookReqstAddRes = response.body();
                    if(objMdlBookReqstAddRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        Snackbar.make(mCrdntrlyot, objMdlBookReqstAddRes.getMessage(), Snackbar.LENGTH_SHORT).show();
                        mTxtinptEtBookName.setText("");
                        mTxtinptEtStream.setText("");
                        mTxtinptEtStandr.setText("");
                    } else
                        Snackbar.make(mCrdntrlyot, objMdlBookReqstAddRes.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlBookReqstAddRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });
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
            mTxtinptlyotStandr.setError(mInvalidStandr);
            ValidationUtils.requestFocus(getActivity(), mTxtinptEtStandr);
            return false;
        }
    }
}
