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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.RecyclerViewEmptySupport;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_news, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }
}
