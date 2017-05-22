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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
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
import android.widget.ViewSwitcher;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.MdlGetStandardsRes;
import com.jalotsav.jamnadasconnect.models.MdlGetStreamsRes;
import com.jalotsav.jamnadasconnect.retrofitapi.APIGeneral;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
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
 * Created by Jalotsav on 5/22/2017.
 */

public class FrgmntBookCorrection extends Fragment {

    private static final String TAG = FrgmntBookCorrection.class.getSimpleName();

    @BindView(R.id.cordntrlyot_frgmnt_bookcorctn) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.txtinputlyot_frgmnt_bookcorctn_bookname) TextInputLayout mTxtinptlyotBookName;
    @BindView(R.id.txtinptet_frgmnt_bookcorctn_bookname) TextInputEditText mTxtinptEtBookName;
    @BindView(R.id.spnr_frgmnt_bookcorctn_stream) Spinner mSpnrStream;
    @BindView(R.id.spnr_frgmnt_bookcorctn_standr) Spinner mSpnrStandr;
    @BindView(R.id.vwswtchr_frgmnt_bookcorctn_steppr) ViewSwitcher mVwswtchrSteppr;
    @BindView(R.id.appcmptbtn_frgmnt_bookcorctn_attchmnt_add) AppCompatButton mAppcmptbtnAttachAdd;
    @BindView(R.id.prgrsbr_frgmnt_bookcorctn) ProgressBar mPrgrsbrMain;

    @BindString(R.string.please_wait_3dots) String mPleaseWait;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;
    @BindString(R.string.no_data_avlbl_refresh) String mNoDataAvilblMsg;
    @BindString(R.string.refresh_sml) String mRefreshStr;
    @BindString(R.string.entr_book_name_sml) String mEntrBookName;
    @BindString(R.string.select_stream_sml) String mSelctStream;
    @BindString(R.string.select_standard_sml) String mSelctStandr;

    UserSessionManager session;
    ProgressDialog mPrgrsDialog;
    boolean mTeacherDataAvaibltyStatus, mIsAttachImage;
    String mBookNameVal;
    ArrayAdapter<String> mArryadptrStream, mArryadptrStandr;
    ArrayList<String> mArrylstStreams, mArrylstStandrs;

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

        getAllDetails();

        return rootView;
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

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder(false).create(APIGeneral.class);
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

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder(false).create(APIGeneral.class);
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

    @OnClick({R.id.fab_frgmnt_bookcorctn_done, R.id.imgvw_frgmnt_bookcorctn_attachimage, R.id.imgvw_frgmnt_bookcorctn_attachaudio,
            R.id.appcmptbtn_frgmnt_bookcorctn_attchmnt_add})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.fab_frgmnt_bookcorctn_done:

                if(mPrgrsbrMain.getVisibility() != View.VISIBLE && mTeacherDataAvaibltyStatus) {
                    if (GeneralFunctions.isNetConnected(getActivity()))
                        checkAllValidation();
                    else
                        Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachimage:

                mVwswtchrSteppr.setInAnimation(getActivity(), R.anim.slide_right_in);
                mVwswtchrSteppr.setOutAnimation(getActivity(), R.anim.slide_left_out);
                mVwswtchrSteppr.showNext();
                mIsAttachImage = true;
                mAppcmptbtnAttachAdd.setVisibility(View.VISIBLE);
                break;
            case R.id.imgvw_frgmnt_bookcorctn_attachaudio:

                mVwswtchrSteppr.setInAnimation(getActivity(), R.anim.slide_right_in);
                mVwswtchrSteppr.setOutAnimation(getActivity(), R.anim.slide_left_out);
                mVwswtchrSteppr.showNext();
                mIsAttachImage = false;
                mAppcmptbtnAttachAdd.setVisibility(View.VISIBLE);
                break;
            case R.id.appcmptbtn_frgmnt_bookcorctn_attchmnt_add:

                mVwswtchrSteppr.setInAnimation(getActivity(), R.anim.slide_left_in);
                mVwswtchrSteppr.setOutAnimation(getActivity(), R.anim.slide_right_out);
                mVwswtchrSteppr.showPrevious();
                mAppcmptbtnAttachAdd.setVisibility(View.INVISIBLE);
                break;
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

        /*if (GeneralFunctions.isNetConnected(getActivity()))
            callBookRequestAddAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();*/

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:

                getAllDetails();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
