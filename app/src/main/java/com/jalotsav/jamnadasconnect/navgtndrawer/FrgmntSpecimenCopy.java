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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.MdlGetStandardsRes;
import com.jalotsav.jamnadasconnect.models.MdlGetStreamsRes;
import com.jalotsav.jamnadasconnect.models.bookrequest.MdlBookReqstAddRes;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherDataAvialbltyRes;
import com.jalotsav.jamnadasconnect.retrofitapi.APIBookRequest;
import com.jalotsav.jamnadasconnect.retrofitapi.APIGeneral;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.jalotsav.jamnadasconnect.retrofitapi.APITeacher;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import java.util.ArrayList;

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
    @BindView(R.id.txtinptet_frgmnt_specimncopy_bookname) TextInputEditText mTxtinptEtBookName;
    @BindView(R.id.spnr_frgmnt_specimncopy_stream) Spinner mSpnrStream;
    @BindView(R.id.spnr_frgmnt_specimncopy_standr) Spinner mSpnrStandr;
    @BindView(R.id.prgrsbr_frgmnt_specimncopy) ProgressBar mPrgrsbrMain;

    @BindString(R.string.please_wait_3dots) String mPleaseWait;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;
    @BindString(R.string.no_data_avlbl_refresh) String mNoDataAvilblMsg;
    @BindString(R.string.refresh_sml) String mRefreshStr;
    @BindString(R.string.entr_book_name_sml) String mEntrBookName;
    @BindString(R.string.select_medium_sml) String mSelctStream;
    @BindString(R.string.select_standard_sml) String mSelctStandr;

    UserSessionManager session;
    ProgressDialog mPrgrsDialog;
    boolean mTeacherDataAvaibltyStatus;
    String mBookNameVal;
    ArrayAdapter<String> mArryadptrStream, mArryadptrStandr;
    ArrayList<String> mArrylstStreams, mArrylstStandrs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_specimen_copy, container, false);
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

        getAllDetails();

        return rootView;
    }

    // Get Teacher Profile, Streams, Standard details
    private void getAllDetails() {

        if (GeneralFunctions.isNetConnected(getActivity())) {
            getTeacherProfileDetails();
            getStreams();
            getStandards();
        } else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Call Retrofit API
    private void getTeacherProfileDetails() {

        mPrgrsDialog = new ProgressDialog(getActivity());
        mPrgrsDialog.setMessage(mPleaseWait);
        mPrgrsDialog.setCancelable(false);
        mPrgrsDialog.show();

        APITeacher objApiTeacher = APIRetroBuilder.getRetroBuilder(getActivity(), false).create(APITeacher.class);
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

    // Check all validation of fields and call API
    private void checkAllValidation() {

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotBookName, mTxtinptEtBookName, mEntrBookName)) // Book Name
            return;

        if(!validateStream())
            return;

        if(!validateStandard())
            return;

        if (GeneralFunctions.isNetConnected(getActivity()))
            callBookRequestAddAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();

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

    // Call Retrofit API
    private void callBookRequestAddAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        mBookNameVal = mTxtinptEtBookName.getText().toString().trim();

        APIBookRequest objApiBookReqst = APIRetroBuilder.getRetroBuilder(getActivity(), false).create(APIBookRequest.class);
        Call<MdlBookReqstAddRes> callMdlBookReqstAddRes = objApiBookReqst.callBookReqstAdd(GeneralFunctions.getDeviceInfo(getActivity()),
                session.getUserId(), mBookNameVal, mSpnrStream.getSelectedItem().toString(), mSpnrStandr.getSelectedItem().toString());
        callMdlBookReqstAddRes.enqueue(new Callback<MdlBookReqstAddRes>() {
            @Override
            public void onResponse(Call<MdlBookReqstAddRes> call, Response<MdlBookReqstAddRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                try {
                    MdlBookReqstAddRes objMdlBookReqstAddRes = response.body();
                    if(objMdlBookReqstAddRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        GeneralFunctions.hideSoftKeyboard(getActivity());
                        GeneralFunctions.showToastSingle(getActivity(), objMdlBookReqstAddRes.getMessage(), Toast.LENGTH_LONG);
//                        Snackbar.make(mCrdntrlyot, objMdlBookReqstAddRes.getMessage(), Snackbar.LENGTH_SHORT).show();
                        mTxtinptEtBookName.setText("");
                        mSpnrStream.setSelection(0);
                        mSpnrStandr.setSelection(0);

                        getActivity().onBackPressed();
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

                if(mPrgrsbrMain.getVisibility() != View.VISIBLE && mTeacherDataAvaibltyStatus) {
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
}
