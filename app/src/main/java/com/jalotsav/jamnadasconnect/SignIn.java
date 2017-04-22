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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.models.login.MdlLoginRes;
import com.jalotsav.jamnadasconnect.retrofitapi.APIGeneral;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.jalotsav.jamnadasconnect.utils.ValidationUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 4/11/2017.
 */

public class SignIn extends AppCompatActivity {

    @BindView(R.id.cordntrlyot_signin) CoordinatorLayout mCrdntrlyot;

    @BindView(R.id.txtinputlyot_signin_mobileno) TextInputLayout mTxtinptlyotMobile;
    @BindView(R.id.txtinputlyot_signin_password) TextInputLayout mTxtinptlyotPaswrd;

    @BindView(R.id.txtinptet_signin_mobileno) TextInputEditText mTxtinptEtMobile;
    @BindView(R.id.txtinptet_signin_password) TextInputEditText mTxtinptEtPaswrd;

    @BindView(R.id.prgrsbr_signin) ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_signin);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.appcmptbtn_signin, R.id.appcmptbtn_signin_signup})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.appcmptbtn_signin:

                checkAllValidation();
                break;
            case R.id.appcmptbtn_signin_signup:

                startActivity(new Intent(this, SignUp.class));
                break;
        }
    }

    // Check all validation of fields and call API
    private void checkAllValidation() {

        if (!ValidationUtils.validateMobile(this, mTxtinptlyotMobile, mTxtinptEtMobile))
            return;

        if (!ValidationUtils.validatePassword(this, mTxtinptlyotPaswrd, mTxtinptEtPaswrd))
            return;

        if(GeneralFunctions.isNetConnected(this))
            callSigninAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Call Retrofit API
    private void callSigninAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        String mobileVal = mTxtinptEtMobile.getText().toString().trim();
        String passwordVal = mTxtinptEtPaswrd.getText().toString().trim();

        APIGeneral objApiGeneral = APIRetroBuilder.getRetroBuilder().create(APIGeneral.class);
        Call<MdlLoginRes> callMdlLoginRes = objApiGeneral.callLogin(mobileVal, passwordVal, GeneralFunctions.getDeviceInfo(this));
        callMdlLoginRes.enqueue(new Callback<MdlLoginRes>() {
            @Override
            public void onResponse(Call<MdlLoginRes> call, Response<MdlLoginRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                if(response.body().getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                    Toast.makeText(SignIn.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignIn.this, ActvtyInDevlpmnt.class));
                } else
                    Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MdlLoginRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
