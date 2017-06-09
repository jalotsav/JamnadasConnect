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
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jalotsav.jamnadasconnect.R;
import com.jalotsav.jamnadasconnect.common.GeneralFunctions;
import com.jalotsav.jamnadasconnect.models.teacher.MdlTeacherWork;
import com.jalotsav.jamnadasconnect.navgtndrawer.FrgmntMyProfile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jalotsav on 5/10/2017.
 */

public class RcyclrWorkDtlsAdapter extends RecyclerView.Adapter<RcyclrWorkDtlsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MdlTeacherWork> mArrylstMdlTeacherWork;
    private ArrayList<Integer> mArrylstPrmryclrs;
    private FrgmntMyProfile mFrgmntMyProfile;

    public RcyclrWorkDtlsAdapter(Context context, FrgmntMyProfile frgmntMyProfile, ArrayList<MdlTeacherWork> arrylstMdlTeacherWork) {

        mContext = context;
        mArrylstMdlTeacherWork = new ArrayList<>();
        mArrylstMdlTeacherWork.addAll(arrylstMdlTeacherWork);
        mFrgmntMyProfile = frgmntMyProfile;
        mArrylstPrmryclrs = GeneralFunctions.getPrimaryColorArray(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_workdtls, parent, false);

        ViewHolder viewHolder = new ViewHolder(mView);
        int n = mArrylstPrmryclrs.size();
        Random rndm_no = new Random();
        n = rndm_no.nextInt(n);

        GradientDrawable bgShape = (GradientDrawable)viewHolder.tvFirstChar.getBackground();
        bgShape.setColor(mArrylstPrmryclrs.get(n));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        MdlTeacherWork objMdlTeacherWork = mArrylstMdlTeacherWork.get(position);
        holder.tvFirstChar.setText(objMdlTeacherWork.getTiInstituteTitle().substring(0,1));
        holder.tvSchoolName.setText(objMdlTeacherWork.getTiInstituteTitle());
        holder.tvStandard.setText(objMdlTeacherWork.getTicStd().concat(", "));
        holder.tvSubject.setText(objMdlTeacherWork.getTicSubject());
        holder.tvStream.setText(objMdlTeacherWork.getTicStream());
        holder.imgvwRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrylstMdlTeacherWork.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvFirstChar, tvSchoolName, tvStandard, tvSubject, tvStream;
        ImageView imgvwRemove;

        ViewHolder(View itemView) {
            super(itemView);

            tvFirstChar = (TextView) itemView.findViewById(R.id.tv_recylrvw_item_workdtls_firstchr);
            tvSchoolName = (TextView) itemView.findViewById(R.id.tv_recylrvw_item_workdtls_schoolname);
            tvStandard = (TextView) itemView.findViewById(R.id.tv_recylrvw_item_workdtls_standr);
            tvSubject = (TextView) itemView.findViewById(R.id.tv_recylrvw_item_workdtls_subject);
            tvStream = (TextView) itemView.findViewById(R.id.tv_recylrvw_item_workdtls_stream);
            imgvwRemove = (ImageView) itemView.findViewById(R.id.imgvw_recylrvw_item_workdtls_remove);
        }
    }

    // Remove item at given position
    private void removeAt(int position) {

        MdlTeacherWork objMdlTeacherWork = mArrylstMdlTeacherWork.get(position);
        if(objMdlTeacherWork.getTiId() != 0)
            mFrgmntMyProfile.mArrylstDeletedWorkDtlsIds.add(objMdlTeacherWork.getTiId());

        mArrylstMdlTeacherWork.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mArrylstMdlTeacherWork.size());
        notifyDataSetChanged();

        mFrgmntMyProfile.updateAddWorkDetailsButton();
    }

    // Add new Item at last position
    public void addItem(MdlTeacherWork objMdlTeacherWork) {

        mArrylstMdlTeacherWork.add(objMdlTeacherWork);
        notifyItemInserted(mArrylstMdlTeacherWork.size() - 1);
        notifyDataSetChanged();
        mFrgmntMyProfile.updateAddWorkDetailsButton();
        mFrgmntMyProfile.mRecyclerView.scrollToPosition(mArrylstMdlTeacherWork.size() - 1);
    }

    // Get All Items
    public ArrayList<MdlTeacherWork> getAllItems() {
        return this.mArrylstMdlTeacherWork;
    }

    // Set Filter and Notify
    public void setFilter(ArrayList<MdlTeacherWork> arrylstMdlTeacherWork) {

        mArrylstMdlTeacherWork = new ArrayList<>();
        mArrylstMdlTeacherWork.addAll(arrylstMdlTeacherWork);
        notifyDataSetChanged();
    }
}
