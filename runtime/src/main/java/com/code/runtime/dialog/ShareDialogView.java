package com.code.runtime.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.fastframe.baseview.BaseViewLayout;
import com.code.fastframe.utils.CompoundDrawableUtils;
import com.code.runtime.R;
import com.code.runtime.utils.ShareUtils.Plat;

public class ShareDialogView extends BaseViewLayout {

    private RecyclerView recyclerview;

    private IShareDialogView mIShareDialogView;

    public void setIShareDialogView(IShareDialogView IShareDialogView) {
        mIShareDialogView = IShareDialogView;
    }

    private List<Bean> mBeans = new ArrayList<Bean>() {{
        add(new Bean(Plat.WECHART, "微信好友", R.drawable.icon_wechart));
        add(new Bean(Plat.WECHARTZONE, "微信朋友圈", R.drawable.icon_wechart_circle));
        add(new Bean(Plat.QQ, "QQ好友", R.drawable.icon_qq));
        add(new Bean(Plat.QZONE, "QQ空间", R.drawable.icon_qzone));
    }};

    public ShareDialogView(Context context) {
        super(context);
    }

    public ShareDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShareDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setViewData(Object data) {

    }

    @Override
    public int setContentLayout() {
        return R.layout.view_share_dialog;
    }

    @Override
    public void initView() {
        recyclerview = getRootView().findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recyclerview.setAdapter(new ShareAdapter());
    }

    public class ShareAdapter extends Adapter {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new ShareItemView(getContext())) {
            };
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ShareItemView shareItemView = (ShareItemView) holder.itemView;
            shareItemView.setViewData(mBeans.get(position));
            shareItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIShareDialogView != null) {
                        mIShareDialogView.shareItemClick(mBeans.get(position));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mBeans.size();
        }
    }

    public static class Bean {

        public int type;

        public int resource;

        public String name;

        public Bean(int type, String name, int resource) {
            this.type = type;
            this.name = name;
            this.resource = resource;
        }
    }

    public static class ShareItemView extends BaseViewLayout {

        private TextView text;

        public ShareItemView(Context context) {
            super(context);
        }

        public ShareItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ShareItemView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public void setViewData(Object data) {
            Bean bean = (Bean) data;
            CompoundDrawableUtils.setDrawalbes(getContext(), text, bean.resource, Gravity.TOP);
            text.setText(bean.name);
        }

        @Override
        public int setContentLayout() {
            return R.layout.share_item_view;
        }

        @Override
        public void initView() {
            text = getRootView().findViewById(R.id.text);
        }
    }

    public static interface IShareDialogView {

        void shareItemClick(Bean bean);
    }
}
