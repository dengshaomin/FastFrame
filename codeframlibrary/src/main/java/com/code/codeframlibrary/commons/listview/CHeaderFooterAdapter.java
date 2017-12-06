/*
 *    Copyright (C) 2016 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.code.codeframlibrary.commons.listview;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.code.codeframlibrary.R;
import com.code.codeframlibrary.commons.ciface.CHeadClickInterface;
import com.h6ah4i.android.widget.advrecyclerview.headerfooter.AbstractHeaderFooterWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

public class CHeaderFooterAdapter
        extends AbstractHeaderFooterWrapperAdapter<CCommonViewHolder, CCommonViewHolder>
        implements View.OnClickListener {


    List<View> mHeaderItems;

    List<View> mFooterItems;

    CHeadClickInterface mCItemClickInterface;


    public CHeaderFooterAdapter(RecyclerView.Adapter adapter, CHeadClickInterface clickListener) {
        setAdapter(adapter);
        this.mCItemClickInterface = clickListener;
        mHeaderItems = new ArrayList<>();
        mFooterItems = new ArrayList<>();
    }

    @Override
    public int getHeaderItemCount() {
        return mHeaderItems.size();
    }

    @Override
    public int getFooterItemCount() {
        return mFooterItems.size();
    }

    @Override
    public int getHeaderItemViewType(int localPosition) {
        return mHeaderItems.get(localPosition).getId();
    }

    @Override
    public int getFooterItemViewType(int localPosition) {
        return mFooterItems.get(localPosition).getId();
    }

    @Override
    public CCommonViewHolder onCreateHeaderItemViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clist_header_container, parent, false);
        v.setOnClickListener(this);
        return new CCommonViewHolder(v);
    }

    @Override
    public CCommonViewHolder onCreateFooterItemViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clist_header_container, parent, false);
        v.setOnClickListener(this);
        return new CCommonViewHolder(v);
    }

    @Override
    public void onBindHeaderItemViewHolder(CCommonViewHolder holder, int localPosition) {
        ((LinearLayout) holder.itemView).removeAllViews();
        if (mHeaderItems.get(localPosition).getParent() != null) {
            ((ViewGroup) mHeaderItems.get(localPosition).getParent()).removeAllViews();
        }
        ((LinearLayout) holder.itemView).addView(mHeaderItems.get(localPosition));
    }

    @Override
    public void onBindFooterItemViewHolder(CCommonViewHolder holder, int localPosition) {
        ((LinearLayout) holder.itemView).removeAllViews();
        if (mFooterItems.get(localPosition).getParent() != null) {
            ((ViewGroup) mFooterItems.get(localPosition).getParent()).removeAllViews();
        }
        ((LinearLayout) holder.itemView).addView(mFooterItems.get(localPosition));
    }


    public void addHeaderItem(View view) {
        mHeaderItems.add(view);
        getHeaderAdapter().notifyItemInserted(mHeaderItems.size() - 1);
    }

    public void removeHeaderItem() {
        if (mHeaderItems.isEmpty()) {
            return;
        }
        mHeaderItems.remove(mHeaderItems.size() - 1);
        getHeaderAdapter().notifyItemRemoved(mHeaderItems.size());
    }

    public void addFooterItem(View view) {
        mFooterItems.add(mFooterItems.size() > 0 ? mFooterItems.size() - 1 : 0, view);
        getFooterAdapter().notifyItemInserted(mFooterItems.size() - 1);
    }

    public void removeFooterItem() {
        if (mFooterItems.isEmpty()) {
            return;
        }
        if (mFooterItems.size() > 1) {
            mFooterItems.remove(mFooterItems.size() -2);
            getFooterAdapter().notifyItemRemoved(mFooterItems.size() -2);
        }
    }

    @Override
    public void onClick(View v) {
        RecyclerView rv = RecyclerViewAdapterUtils.getParentRecyclerView(v);
        RecyclerView.ViewHolder vh = rv.findContainingViewHolder(v);

        int rootPosition = vh.getAdapterPosition();
        if (rootPosition == RecyclerView.NO_POSITION) {
            return;
        }

        // need to determine adapter local position like this:
        RecyclerView.Adapter rootAdapter = rv.getAdapter();
        //总位置，包含footview 以及item的位置，如果是footview点击，需要得到准确第几个位置，需要减去headcount以及itemcount
        int localPosition = WrapperAdapterUtils.unwrapPosition(rootAdapter, this, rootPosition);

        // get segment
        long segmentedPosition = getSegmentedPosition(localPosition);
        int segment = extractSegmentPart(segmentedPosition);
        int offset = extractSegmentOffsetPart(segmentedPosition);

        String message;

        if (segment == SEGMENT_TYPE_HEADER) {
            message = "CLICKED: Header item " + offset;
        } else if (segment == SEGMENT_TYPE_FOOTER) {
            message = "CLICKED: Footer item " + offset;
        } else {
            throw new IllegalStateException("Something wrong.");
        }
        if (mCItemClickInterface != null) {
            mCItemClickInterface.onHeadFootClickLister(v, null, localPosition);
        }
    }

    public boolean hasFootView(View footView) {
        if (mFooterItems != null) {
            for (int i = 0; i < mFooterItems.size(); i++) {
                if (mFooterItems.get(i).getId() == footView.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeFootView(View footView) {
        if (mFooterItems != null) {
            for (int i = 0; i < mFooterItems.size(); i++) {
                if (mFooterItems.get(i).getId() == footView.getId()) {
                    mFooterItems.remove(i);
                    mHeaderItems.remove(mHeaderItems.size() - 1);
                    getHeaderAdapter().notifyItemRemoved(i);
                    break;
                }
            }
        }

    }

    public void removeHeaderView(View footView) {
        if (mFooterItems != null) {
            for (int i = 0; i < mFooterItems.size(); i++) {
                if (mFooterItems.get(i).getId() == footView.getId()) {
                    mHeaderItems.remove(i);
                    getHeaderAdapter().notifyItemRemoved(i);
                    break;
                }
            }
        }
    }
}