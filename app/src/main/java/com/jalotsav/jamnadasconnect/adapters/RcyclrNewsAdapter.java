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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.AppConstants;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.common.RecyclerViewEmptySupport;
import com.jalotsav.jamnadasconnect.listeners.OnLoadMoreListener;
import com.jalotsav.jamnadasconnect.models.news.MdlTeacherMsg;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jalotsav on 6/9/2017.
 */

public class RcyclrNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<MdlTeacherMsg> mArrylstMdlTeacherMsg;
    private Drawable mDrwblDefaultNtfctn;
    private ArrayList<Integer> mArrylstPrmryclrs;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5, previousTotal = 0, firstVisibleItem, visibleItemCount, totalItemCount/*, lastVisibleItem*/;

    public RcyclrNewsAdapter(Context context, RecyclerViewEmptySupport recyclerView, ArrayList<MdlTeacherMsg> arrylstMdlTeacherMsg, Drawable drwblDefaultNtfctn) {

        mContext = context;
        mArrylstMdlTeacherMsg = new ArrayList<>();
        mArrylstMdlTeacherMsg.addAll(arrylstMdlTeacherMsg);
        mDrwblDefaultNtfctn = drwblDefaultNtfctn;
        mArrylstPrmryclrs = GeneralFunctions.getPrimaryColorArray(mContext);

        final LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                /*
                * Not call once it's reach last Position
                * */
                /*totalItemCount = mLinearLayoutManager.getItemCount();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                    if (mOnLoadMoreListener != null)
                        mOnLoadMoreListener.onLoadMore();

                    isLoading = true;
                }*/

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (isLoading) {
                    if (totalItemCount > previousTotal) {
                        isLoading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

                    if (mOnLoadMoreListener != null)
                        mOnLoadMoreListener.onLoadMore();

                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mArrylstMdlTeacherMsg.get(position) == null ? AppConstants.RECYCLRVIEW_TYPE_LOADING: AppConstants.RECYCLRVIEW_TYPE_DATA;
    }

    // Add new Item at last position
    public void addItem(MdlTeacherMsg objMdlTeacherMsg) {

        mArrylstMdlTeacherMsg.add(objMdlTeacherMsg);
        notifyItemInserted(mArrylstMdlTeacherMsg.size() - 1);
        notifyDataSetChanged();
    }

    // Remove item at given position
    public void removeAt(int position) {

        mArrylstMdlTeacherMsg.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mArrylstMdlTeacherMsg.size());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AppConstants.RECYCLRVIEW_TYPE_DATA) {

            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_news, parent, false);

            ViewHolderData viewHolderData = new ViewHolderData(mView);
            int n = mArrylstPrmryclrs.size();
            Random rndm_no = new Random();
            n = rndm_no.nextInt(n);

            GradientDrawable bgShape = (GradientDrawable)viewHolderData.tvFirstChar.getBackground();
            bgShape.setColor(mArrylstPrmryclrs.get(n));

            return viewHolderData;
        } else if (viewType == AppConstants.RECYCLRVIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_loading, parent, false);
            return new ViewHolderLoading(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderData) {

            ViewHolderData viewHolderData = (ViewHolderData) holder;
            MdlTeacherMsg objMdlTeacherMsg = mArrylstMdlTeacherMsg.get(position);
            if (!TextUtils.isEmpty(objMdlTeacherMsg.getTmImage())) {

                viewHolderData.tvFirstChar.setVisibility(View.GONE);
                viewHolderData.imgvwImage.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(objMdlTeacherMsg.getTmImage())
                        .placeholder(mDrwblDefaultNtfctn)
                        .into(viewHolderData.imgvwImage);
            } else {

                viewHolderData.tvFirstChar.setVisibility(View.VISIBLE);
                viewHolderData.imgvwImage.setVisibility(View.GONE);
                viewHolderData.tvFirstChar.setText(objMdlTeacherMsg.getTmMessage().substring(0, 1));
            }
            viewHolderData.tvTitle.setText(objMdlTeacherMsg.getTmMessage());
            viewHolderData.tvDescrptn.setText(objMdlTeacherMsg.getTmSubject());
        } else if (holder instanceof ViewHolderLoading) {

            ViewHolderLoading viewHolderLoading = (ViewHolderLoading) holder;
            viewHolderLoading.mPrgrsbr.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mArrylstMdlTeacherMsg == null ? 0 : mArrylstMdlTeacherMsg.size();
    }

    private class ViewHolderData extends RecyclerView.ViewHolder{

        TextView tvFirstChar, tvTitle, tvDescrptn;
        ImageView imgvwImage;

        ViewHolderData(View itemView) {
            super(itemView);

            tvFirstChar = (TextView) itemView.findViewById(R.id.tv_recylrvw_item_news_firstchr);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_recylrvw_item_news_title);
            tvDescrptn = (TextView) itemView.findViewById(R.id.tv_recylrvw_item_news_descrptn);
            imgvwImage = (ImageView) itemView.findViewById(R.id.imgvw_frecylrvw_item_news_image);
        }
    }

    private class ViewHolderLoading extends RecyclerView.ViewHolder{

        ProgressBar mPrgrsbr;

        ViewHolderLoading(View itemView) {
            super(itemView);

            mPrgrsbr = (ProgressBar) itemView.findViewById(R.id.prgrsbr_recylrvw_item_loading);
        }
    }

    public void setLoaded() {
        isLoading = false;
    }

    // Get All Items
    public ArrayList<MdlTeacherMsg> getAllItems() {
        return this.mArrylstMdlTeacherMsg;
    }

    // Set Filter and Notify
    public void setFilter(ArrayList<MdlTeacherMsg> arrylstMdlTeacherMsg) {

        mArrylstMdlTeacherMsg = new ArrayList<>();
        mArrylstMdlTeacherMsg.addAll(arrylstMdlTeacherMsg);
        notifyDataSetChanged();
    }
}
