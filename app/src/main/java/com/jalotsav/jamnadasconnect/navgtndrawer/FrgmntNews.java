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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.adapters.RcyclrNewsAdapter;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.LogHelper;
import com.jalotsav.jamnadasconnect.common.RecyclerViewEmptySupport;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;
import com.jalotsav.jamnadasconnect.listeners.OnLoadMoreListener;
import com.jalotsav.jamnadasconnect.models.news.MdlTeacherMsg;
import com.jalotsav.jamnadasconnect.models.news.MdlTeacherMsgListRes;
import com.jalotsav.jamnadasconnect.retrofitapi.APINews;
import com.jalotsav.jamnadasconnect.retrofitapi.APIRetroBuilder;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 6/8/2017.
 */

public class FrgmntNews extends Fragment {

    private static final String TAG = FrgmntNews.class.getSimpleName();

    @BindView(R.id.cordntrlyot_frgmnt_news) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.rcyclrvw_frgmnt_news) RecyclerViewEmptySupport mRecyclerView;
    @BindView(R.id.prgrsbr_frgmnt_news) ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;
    @BindString(R.string.news_appear_here) String mNewsAppearHere;

    @BindDrawable(R.drawable.ic_notifications_flat_56dp) Drawable mDrwblDefaultNtfctn;

    UserSessionManager session;
    RecyclerView.LayoutManager mLayoutManager;
    RcyclrNewsAdapter mAdapter;
    ArrayList<MdlTeacherMsg> mArrylstMdlTeacherMsg;
    int mNewsMsgLastId;
    String mApiKeyword = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_news, container, false);
        ButterKnife.bind(this, rootView);

        session = new UserSessionManager(getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mLnrlyotAppearHere);

        mTvAppearHere.setText(mNewsAppearHere);

        mArrylstMdlTeacherMsg = new ArrayList<>();
        mAdapter = new RcyclrNewsAdapter(getActivity(), mRecyclerView, mArrylstMdlTeacherMsg, mDrwblDefaultNtfctn);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if(mPrgrsbrMain.getVisibility() != View.VISIBLE)
                    getTeacherMsgs();
            }
        });

        getTeacherMsgs();

        return rootView;
    }

    private void getTeacherMsgs() {

        if (GeneralFunctions.isNetConnected(getActivity()))
            getTeacherMsgsAPI();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Call Retrofit API
    private void getTeacherMsgsAPI() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        /*if(mNewsMsgLastId == 0)
            mPrgrsbrMain.setVisibility(View.VISIBLE);
        else
            mAdapter.addItem(null);*/ // Add loading item

        APINews objApiNews = APIRetroBuilder.getRetroBuilder(false).create(APINews.class);
        Call<MdlTeacherMsgListRes> callMdlTeacherViewRes = objApiNews.callTeacherMsgList(
                GeneralFunctions.getDeviceInfo(getActivity()), session.getUserId(), mNewsMsgLastId, mApiKeyword);
        callMdlTeacherViewRes.enqueue(new Callback<MdlTeacherMsgListRes>() {
            @Override
            public void onResponse(Call<MdlTeacherMsgListRes> call, Response<MdlTeacherMsgListRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);
                /*if(mNewsMsgLastId == 0)
                    mPrgrsbrMain.setVisibility(View.GONE);
                else
                    mAdapter.removeAt(mArrylstMdlTeacherMsg.size() - 1);*/ // Remove loading item

                try {

                    MdlTeacherMsgListRes objMdlTeacherViewRes = response.body();

                    if(objMdlTeacherViewRes.getSuccess().equalsIgnoreCase(AppConstants.VALUES_TRUE)) {

                        if(mNewsMsgLastId == 0) {

                            mArrylstMdlTeacherMsg = new ArrayList<>();
                            mArrylstMdlTeacherMsg.addAll(objMdlTeacherViewRes.getObjMdlTeacherMsg());
                            mAdapter = new RcyclrNewsAdapter(getActivity(), mRecyclerView, mArrylstMdlTeacherMsg, mDrwblDefaultNtfctn);
                            mRecyclerView.setAdapter(mAdapter);
                            if(mArrylstMdlTeacherMsg.size() > 0)
                                mNewsMsgLastId = mArrylstMdlTeacherMsg.get(mArrylstMdlTeacherMsg.size()-1).getTmId();
                        } else {

                            for(MdlTeacherMsg objMdlTeacherMsg : objMdlTeacherViewRes.getObjMdlTeacherMsg()) {
                                mAdapter.addItem(objMdlTeacherMsg);
                            }
                            if(mAdapter.getItemCount() > 0)
                                mNewsMsgLastId = mAdapter.getAllItems().get(mAdapter.getItemCount() - 1).getTmId();
                            mAdapter.setLoaded();
                        }
                    } else {

                        mAdapter.setLoaded();
                        Snackbar.make(mCrdntrlyot, objMdlTeacherViewRes.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    LogHelper.printLog(AppConstants.LOGTYPE_ERROR, TAG, e.getMessage());
                    Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MdlTeacherMsgListRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                /*if(mNewsMsgLastId == 0)
                    mPrgrsbrMain.setVisibility(View.GONE);
                else
                    mAdapter.removeAt(mArrylstMdlTeacherMsg.size() - 1);*/ // Remove loading item
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
