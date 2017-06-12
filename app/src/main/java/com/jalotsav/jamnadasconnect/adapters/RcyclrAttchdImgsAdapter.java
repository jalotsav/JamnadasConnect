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

package com.jalotsav.jamnadasconnect.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jalotsav.jamnadasconnect.ActvtyPreviewImage;
import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.navgtndrawer.FrgmntBookCorrection;
import com.jalotsav.jamnadasconnect.navgtndrawer.FrgmntTeacherSuggestions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jalotsav on 3/6/17.
 */

public class RcyclrAttchdImgsAdapter extends RecyclerView.Adapter<RcyclrAttchdImgsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mArrylstImgFilePath;
    private FrgmntBookCorrection mFrgmntBookCorctn;
    private FrgmntTeacherSuggestions mFrgmntTchrSuggstns;

    public RcyclrAttchdImgsAdapter(Context context, FrgmntBookCorrection frgmntBookCorctn, FrgmntTeacherSuggestions frgmntTchrSuggstns, ArrayList<String> arrylstImgFilePath) {

        mContext = context;
        mArrylstImgFilePath = new ArrayList<>();
        mArrylstImgFilePath.addAll(arrylstImgFilePath);
        mFrgmntBookCorctn = frgmntBookCorctn;
        mFrgmntTchrSuggstns = frgmntTchrSuggstns;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_bookcorctn_attchdimgs, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String imageFilePath = mArrylstImgFilePath.get(position);

        Picasso.with(mContext)
                .load(new File(imageFilePath))
//                .placeholder(mDrwblDefaultPicture)
                .into(holder.imgvwAttchdImg);

        holder.imgvwAttchdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int holderAdapterPosition = holder.getAdapterPosition();
                showPreviewiewRemovePopupmenu(view, holderAdapterPosition);
            }
        });
    }

    // Show pop-up for preview or remove
    private void showPreviewiewRemovePopupmenu(View view, final int holderAdapterPosition) {

        PopupMenu mPopupmenu = new PopupMenu(mContext, view);
        mPopupmenu.getMenuInflater().inflate(R.menu.menu_popup_previewremove, mPopupmenu.getMenu());
        mPopupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_preview:

                        mContext.startActivity(
                                new Intent(mContext, ActvtyPreviewImage.class)
                                    .putExtra(AppConstants.PUT_EXTRA_IMAGE_PATH, mArrylstImgFilePath.get(holderAdapterPosition))
                                    .putExtra(AppConstants.PUT_EXTRA_IMAGE_PATH_TYPE, AppConstants.IMAGE_PATH_TYPE_FILE));
                        break;
                    case R.id.action_remove:

                        removeAt(holderAdapterPosition);
                        break;
                }
                return false;
            }
        });
        mPopupmenu.show();
    }

    @Override
    public int getItemCount() {
        return mArrylstImgFilePath.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgvwAttchdImg;

        ViewHolder(View itemView) {
            super(itemView);

            imgvwAttchdImg = (ImageView) itemView.findViewById(R.id.imgvw_recylrvw_item_bookcorctn_attchdimg);
        }
    }

    // Remove item at given position
    private void removeAt(int position) {

        mArrylstImgFilePath.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mArrylstImgFilePath.size());
        notifyDataSetChanged();
        if(mFrgmntBookCorctn != null) {
            mFrgmntBookCorctn.mArrlstJsonAttchdImgBase64.remove(position);
            mFrgmntBookCorctn.mImgCount--;
        }
        if(mFrgmntTchrSuggstns != null) {
            mFrgmntTchrSuggstns.mArrlstJsonAttchdImgBase64.remove(position);
            mFrgmntTchrSuggstns.mImgCount--;
        }
    }

    // Add new Item at last position
    public void addItem(String filePath) {

        mArrylstImgFilePath.add(filePath);
        notifyItemInserted(mArrylstImgFilePath.size() - 1);
        notifyDataSetChanged();
    }

    // Get All Items
    public ArrayList<String> getAllItems() {
        return this.mArrylstImgFilePath;
    }

    // Set Filter and Notify
    public void setFilter(ArrayList<String> arrylstImgFilePath) {

        mArrylstImgFilePath = new ArrayList<>();
        mArrylstImgFilePath.addAll(arrylstImgFilePath);
        notifyDataSetChanged();
    }
}
