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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jalotsav.jamnadasconnect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jalotsav on 4/24/2017.
 */

public class FrgmntDashboard extends Fragment {

    @BindView(R.id.rltvlyot_frgmnt_dashbrd_specimencopy) RelativeLayout mRltvlyotSpecimenCopy;
    @BindView(R.id.rltvlyot_frgmnt_dashbrd_bookroder) RelativeLayout mRltvlyotBookOrder;
    @BindView(R.id.rltvlyot_frgmnt_dashbrd_suggestion) RelativeLayout mRltvlyotSuggestn;
    @BindView(R.id.rltvlyot_frgmnt_dashbrd_bookcorctn) RelativeLayout mRltvlyotBookCorrectn;
    @BindView(R.id.rltvlyot_frgmnt_dashbrd_myprofile) RelativeLayout mRltvlyotMyProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_dashboard, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick({R.id.rltvlyot_frgmnt_dashbrd_specimencopy, R.id.rltvlyot_frgmnt_dashbrd_bookroder,
            R.id.rltvlyot_frgmnt_dashbrd_suggestion, R.id.rltvlyot_frgmnt_dashbrd_bookcorctn,
            R.id.rltvlyot_frgmnt_dashbrd_myprofile})
    public void onClickView(View view) {

        MenuItem menuItem;

        switch (view.getId()) {
            case R.id.rltvlyot_frgmnt_dashbrd_specimencopy:

                menuItem = ((NavgtnDrwrMain) getActivity()).mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_specimen_copy);
                break;
            case R.id.rltvlyot_frgmnt_dashbrd_bookroder:

                menuItem = ((NavgtnDrwrMain) getActivity()).mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_book_order);
                break;
            case R.id.rltvlyot_frgmnt_dashbrd_suggestion:

                menuItem = ((NavgtnDrwrMain) getActivity()).mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_teacher_sugstn);
                break;
            case R.id.rltvlyot_frgmnt_dashbrd_bookcorctn:

                menuItem = ((NavgtnDrwrMain) getActivity()).mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_book_corectn);
                break;
            case R.id.rltvlyot_frgmnt_dashbrd_myprofile:

                menuItem = ((NavgtnDrwrMain) getActivity()).mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_teacher_profile);
                break;
            default:

                menuItem = ((NavgtnDrwrMain) getActivity()).mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_dashboard);
                break;
        }

        ((NavgtnDrwrMain) getActivity()).onNavigationItemSelected(menuItem);
    }
}
