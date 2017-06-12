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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.models.news.MdlTeacherMsg;
import com.jalotsav.jamnadasconnect.models.news.MdlTeacherMsgViewRes;
import com.jalotsav.jamnadasconnect.retrofitapi.APINews;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;
import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 6/12/2017.
 */

public class ActvtyNewsDetails extends AppCompatActivity {

    private static final String TAG = ActvtyNewsDetails.class.getSimpleName();

    @BindView(R.id.cordntrlyot_actvty_newsdtls) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.tv_actvty_newsdtls_title) TextView mTvTitle;
    @BindView(R.id.tv_actvty_newsdtls_descrptn) TextView mTvDescrptn;
    @BindView(R.id.imgvw_actvty_newsdtls_image) ImageView mImgvwImage;
    @BindView(R.id.prgrsbr_actvty_newsdtls) ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    @BindDrawable(R.drawable.ic_pictures_flat_128dp) Drawable mDrwblDefaultPicture;

    UserSessionManager session;
    int mSlctdTmId;
    String mImgPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_news_details);
        ButterKnife.bind(this);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) { e.printStackTrace(); }

        session = new UserSessionManager(this);

        mSlctdTmId = getIntent().getIntExtra(AppConstants.PUT_EXTRA_TM_ID, 0);

        mTvTitle.setMovementMethod(new ScrollingMovementMethod());
        mTvDescrptn.setMovementMethod(new ScrollingMovementMethod());

        mImgvwImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActvtyNewsDetails.this, ActvtyPreviewImage.class)
                        .putExtra(AppConstants.PUT_EXTRA_IMAGE_PATH, mImgPath)
                        .putExtra(AppConstants.PUT_EXTRA_IMAGE_PATH_TYPE, AppConstants.IMAGE_PATH_TYPE_SERVER));
            }
        });

        if (GeneralFunctions.isNetConnected(this))
            callGetNewsDetailsAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Call Retrofit API
    private void callGetNewsDetailsAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);

        APINews objApiNews = APIRetroBuilder.getRetroBuilder(false).create(APINews.class);
        Call<MdlTeacherMsgViewRes> callMdlTeacherMsgViewRes = objApiNews.callTeacherMsgView(
                GeneralFunctions.getDeviceInfo(this), session.getUserId(), mSlctdTmId);
        callMdlTeacherMsgViewRes.enqueue(new Callback<MdlTeacherMsgViewRes>() {
            @Override
            public void onResponse(Call<MdlTeacherMsgViewRes> call, Response<MdlTeacherMsgViewRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                try {
                    MdlTeacherMsgViewRes objMdlTeacherMsgViewRes = response.body();
                    if(objMdlTeacherMsgViewRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        for (MdlTeacherMsg objMdlTeacherMsg : objMdlTeacherMsgViewRes.getObjMdlTeacherMsg()) {

                            mTvTitle.setText(objMdlTeacherMsg.getTmSubject());
                            mTvDescrptn.setText(objMdlTeacherMsg.getTmMessage());
                            mImgPath = objMdlTeacherMsg.getTmImage();
                            if(!TextUtils.isEmpty(mImgPath)) {

                                mImgvwImage.setVisibility(View.VISIBLE);
                                Picasso.with(ActvtyNewsDetails.this)
                                        .load(objMdlTeacherMsg.getTmImage())
                                        .placeholder(mDrwblDefaultPicture)
                                        .into(mImgvwImage);
                            } else
                                mImgvwImage.setVisibility(View.GONE);
                        }
                    } else
                        Snackbar.make(mCrdntrlyot, objMdlTeacherMsgViewRes.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlTeacherMsgViewRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
