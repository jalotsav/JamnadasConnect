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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jalotsav on 4/6/17.
 */

public class ActvtyPreviewImage extends AppCompatActivity {

    @BindView(R.id.imgvw_actvty_prevwimage_preview) ImageView mImgvwPrevwImg;

    @BindDrawable(R.drawable.ic_pictures_flat_128dp) Drawable mDrwblDefaultPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_previewimage);
        ButterKnife.bind(this);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) { e.printStackTrace(); }

        String imgPath = getIntent().getStringExtra(AppConstants.PUT_EXTRA_IMAGE_PATH);
        int imgPathType = getIntent().getIntExtra(AppConstants.PUT_EXTRA_IMAGE_PATH_TYPE, 0);
        if(imgPathType == AppConstants.IMAGE_PATH_TYPE_FILE) {

            Picasso.with(this)
                    .load(new File(imgPath))
                    .placeholder(mDrwblDefaultPicture)
                    .into(mImgvwPrevwImg);
        } else if(imgPathType == AppConstants.IMAGE_PATH_TYPE_SERVER) {

            Picasso.with(this)
                    .load(imgPath)
                    .placeholder(mDrwblDefaultPicture)
                    .into(mImgvwPrevwImg);
        }
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
