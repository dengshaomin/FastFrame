package com.code.runtime.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.fastframe.baseview.BaseViewLayout;
import com.code.fastframe.utils.CompoundDrawableUtils;
import com.code.runtime.R;
import com.code.runtime.contants.ShareContants.Plat;

public class ShareDialogView extends BaseViewLayout {

    private RecyclerView recyclerview;

    private IShareDialogView mIShareDialogView;

    public void setIShareDialogView(IShareDialogView IShareDialogView) {
        mIShareDialogView = IShareDialogView;
    }

    private List<Bean> mBeans = new ArrayList<Bean>() {{
        add(new Bean(Plat.WECHART, "微信好友", R.drawable.ic_share_wechat));
        add(new Bean(Plat.WECHARTZONE, "微信朋友圈", R.drawable.ic_share_moments));
        add(new Bean(Plat.QQ, "QQ好友", R.drawable.ic_share_qq));
        add(new Bean(Plat.QZONE, "QQ空间", R.drawable.ic_share_qzone));
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
        findView(R.id.cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIShareDialogView != null) {
                    mIShareDialogView.shareItemClick(null);
                }
            }
        });
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

        public int plat;

        public int resource;

        public String name;

        public Bean(int plat, String name, int resource) {
            this.plat = plat;
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
