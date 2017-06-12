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

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.UserSessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jalotsav on 24/4/17.
 */

public class NavgtnDrwrMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_drwrlyot_appbar_main) Toolbar mToolbar;
    @BindView(R.id.drwrlyot_nvgtndrwr_main) DrawerLayout mDrwrlyot;
    @BindView(R.id.navgtnvw_nvgtndrwr_main) NavigationView mNavgtnVw;

    TextView mTvFullName, mTvEmailMobile;
    MenuItem mMenuItemDashboard, mMenuItemSpecmnCopy, mMenuItemBookCorctn, mMenuItemSugstn, mMenuItemNews, mMenuItemMyProfile;

    UserSessionManager session;
    Bundle mBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_nvgtndrwr_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        session = new UserSessionManager(this);
        if(session.checkLogin()) {

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, mDrwrlyot, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrwrlyot.addDrawerListener(toggle);
            toggle.syncState();

            mNavgtnVw.setNavigationItemSelectedListener(this);

            View mVwHeaderlyot = mNavgtnVw.getHeaderView(0);
            mTvFullName = (TextView) mVwHeaderlyot.findViewById(R.id.tv_drwrlyot_header_fullname);
            mTvEmailMobile = (TextView) mVwHeaderlyot.findViewById(R.id.tv_drwrlyot_header_emailmobile);

            mTvFullName.setText(session.getFirstName().concat(" ").concat(session.getLastName()));
            if(!TextUtils.isEmpty(session.getMobile()))
                mTvEmailMobile.setText(session.getMobile());
            else
                mTvEmailMobile.setText(session.getEmail());

            // Select First option on Launch
//            mNavgtnVw.getMenu().getItem(0).setChecked(true);
//            onNavigationItemSelected(mNavgtnVw.getMenu().getItem(0));
            int navDrwrPostn = getIntent().getIntExtra(AppConstants.PUT_EXTRA_NAVDRWER_POSTN, AppConstants.NAVDRWER_DASHBOARD);
            onNavigationItemSelected(getNavDrwrPostnMenuItem(navDrwrPostn));
        }
    }

    // Get MenuItem from NavDrawer Position, which is get from getIntent()
    private MenuItem getNavDrwrPostnMenuItem(int navDrwrPostn) {

        switch (navDrwrPostn) {
            case AppConstants.NAVDRWER_DASHBOARD:

                return mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_dashboard);
            case AppConstants.NAVDRWER_BOOK_REQUEST:

                return mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_book_corectn);
            case AppConstants.NAVDRWER_NEWS:

                return mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_news);
            default:

                return mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        if(mBundle == null) {

            mBundle = new Bundle();
            mBundle.putInt(AppConstants.PUT_EXTRA_COME_FROM, 0);
        }
        CharSequence toolbarTitle = getString(R.string.app_name);

        switch (item.getItemId()) {
            case R.id.action_nvgtndrwr_dashboard:

                fragment = new FrgmntDashboard();
                toolbarTitle = getString(R.string.dashboard_sml);
                break;
            case R.id.action_nvgtndrwr_specimen_copy:

                fragment = new FrgmntSpecimenCopy();
                toolbarTitle = getString(R.string.specimen_copy_sml);
                break;
            case R.id.action_nvgtndrwr_book_corectn:

                fragment = new FrgmntBookCorrection();
                toolbarTitle = getString(R.string.book_correction_sml);
                break;
            case R.id.action_nvgtndrwr_teacher_sugstn:

                fragment = new FrgmntTeacherSuggestions();
                toolbarTitle = getString(R.string.suggestions_sml);
                break;
            case R.id.action_nvgtndrwr_news:

                fragment = new FrgmntNews();
                toolbarTitle = getString(R.string.news_sml);
                break;
            case R.id.action_nvgtndrwr_teacher_profile:

                fragment = new FrgmntMyProfile();
                toolbarTitle = getString(R.string.myprofile_sml);
                break;
            case R.id.action_nvgtndrwr_teacher_logout:

                confirmLogoutAlertDialog();
                break;
        }

        mDrwrlyot.closeDrawer(GravityCompat.START);

        if (fragment != null) {

            getCurrentCheckedMenuItem().setChecked(false);
            item.setChecked(true);

            fragment.setArguments(mBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framlyot_drwrlyot_contnt_container, fragment).commit();

            setTitle(toolbarTitle);
        }
        return true;
    }

    // Get Current checked MenuItem of NavigationView
    public MenuItem getCurrentCheckedMenuItem() {

        mMenuItemDashboard = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_dashboard);
        mMenuItemSpecmnCopy = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_specimen_copy);
        mMenuItemBookCorctn = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_book_corectn);
        mMenuItemSugstn = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_teacher_sugstn);
        mMenuItemNews = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_news);
        mMenuItemMyProfile = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_teacher_profile);

        MenuItem currntSelctdMenuItem;
        if (mMenuItemDashboard.isChecked())
            currntSelctdMenuItem = mMenuItemDashboard;
        else if (mMenuItemSpecmnCopy.isChecked())
            currntSelctdMenuItem = mMenuItemSpecmnCopy;
        else if (mMenuItemBookCorctn.isChecked())
            currntSelctdMenuItem = mMenuItemBookCorctn;
        else if (mMenuItemSugstn.isChecked())
            currntSelctdMenuItem = mMenuItemSugstn;
        else if (mMenuItemNews.isChecked())
            currntSelctdMenuItem = mMenuItemNews;
        else if (mMenuItemMyProfile.isChecked())
            currntSelctdMenuItem = mMenuItemMyProfile;
        else
            currntSelctdMenuItem = mMenuItemDashboard;

        return currntSelctdMenuItem;
    }

    // Show AlertDialog for confirm to Logout
    private void confirmLogoutAlertDialog() {

        AlertDialog.Builder alrtDlg = new AlertDialog.Builder(this);
        alrtDlg.setTitle(getString(R.string.logout_sml));
        alrtDlg.setMessage(getString(R.string.logout_alrtdlg_msg));
        alrtDlg.setNegativeButton(getString(R.string.no_sml).toUpperCase(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alrtDlg.setPositiveButton(getString(R.string.logout_sml).toUpperCase(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                session.logoutUser();
            }
        });

        alrtDlg.show();
    }

    @Override
    public void setTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filters) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {

        if (mDrwrlyot.isDrawerOpen(GravityCompat.START))
            mDrwrlyot.closeDrawer(GravityCompat.START);
        else if (getCurrentCheckedMenuItem() != mMenuItemDashboard)
            onNavigationItemSelected(mNavgtnVw.getMenu().getItem(0));
        else
            super.onBackPressed();
    }
}
