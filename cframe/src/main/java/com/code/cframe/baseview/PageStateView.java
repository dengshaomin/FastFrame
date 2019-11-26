package com.code.cframe.baseview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.cframe.R;
import com.code.cframe.baseview.BaseViewLayout;
import com.code.cframe.ciface.IPageStateCb;
import com.code.cframe.ciface.IPageStateView;
import com.code.cframe.utils.NetWorkUtils;

/**
 * Created by dengshaomin on 2017/12/8.
 */

public class PageStateView extends BaseViewLayout implements OnClickListener, IPageStateView {

    public static class State {

        public static final int EMPTY = 0;

        public static final int ERROR = 1;

        public static final int SUCCESS = 2;

        public static final int LOADING = 3;
    }

    private int currentState = State.LOADING;

    private int emptyResource = 0;

    private int errorResource = 0;

    private int netResource = 0;

    private String errorTip = "";

    private String emptyTip = "";

    private String loadingTip = "";

    private String netTip = "";

    private ImageView empty_image, error_image, net_image;

    private View loading_lay, empty_lay, net_lay, error_lay;

    private ViewStub vstub_empty, vstub_error, vstub_net;

    private TextView loading_tip, empty_tip, error_tip, net_tip;

    private IPageStateCb mIPageStateCb;

    public IPageStateCb getIPageStateCb() {
        return mIPageStateCb;
    }

    public void setIPageStateCb(IPageStateCb IPageStateCb) {
        mIPageStateCb = IPageStateCb;
    }


    public PageStateView(Context context, IPageStateCb iPageStateCb) {
        super(context);
        mIPageStateCb = iPageStateCb;
    }

    public PageStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PageStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.state_base_view;
    }

    @Override
    public void initView() {
        initLoadingLay();
    }

    private void initLoadingLay() {
        if (loading_lay == null) {
            loading_lay = findView(R.id.loading_lay);
            loading_tip = (TextView) findView(R.id.loading_tip);
        }
    }

    private void initNetLay() {
        if (vstub_net == null) {
            vstub_net = (ViewStub) findView(R.id.vstub_net);
        }
        if (vstub_net.getParent() != null) {
            View view = vstub_net.inflate();
            net_lay = view.findViewById(R.id.net_lay);
            net_image = view.findViewById(R.id.net_image);
            net_tip = view.findViewById(R.id.net_tip);
            net_lay.setOnClickListener(this);
        }
    }

    private void initErrorLay() {
        if (vstub_error == null) {
            vstub_error = (ViewStub) findView(R.id.vstub_error);
        }
        if (vstub_error.getParent() != null) {
            View view = vstub_error.inflate();
            error_lay = view.findViewById(R.id.error_lay);
            error_image = view.findViewById(R.id.error_image);
            error_tip = view.findViewById(R.id.error_tip);
            error_tip.setOnClickListener(this);
        }
    }

    private void initEmptyLay() {
        if (vstub_empty == null) {
            vstub_empty = (ViewStub) findView(R.id.vstub_empty);
        }
        if (vstub_empty.getParent() != null) {
            View view = vstub_empty.inflate();
            empty_lay = view.findViewById(R.id.empty_lay);
            empty_image = view.findViewById(R.id.empty_image);
            empty_tip = view.findViewById(R.id.empty_tip);
            empty_tip.setOnClickListener(this);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void setViewData(Object data) {
        currentState = (int) data;
        setVisibility(currentState == State.SUCCESS ? GONE : VISIBLE);
        if (State.EMPTY == currentState) {
            initEmptyLay();
            if (empty_lay != null) {
                empty_lay.setVisibility(VISIBLE);
            }
            if (loading_lay != null) {
                loading_lay.setVisibility(GONE);
            }
            if (net_lay != null) {
                net_lay.setVisibility(GONE);
            }
            if (error_lay != null) {
                error_lay.setVisibility(GONE);
            }
        } else if (State.ERROR == currentState) {
            if (NetWorkUtils.connected(getContext())) {
                initErrorLay();
                if (net_lay != null) {
                    net_lay.setVisibility(GONE);
                }
                if (error_lay != null) {
                    error_lay.setVisibility(VISIBLE);
                }
            }else{
                initNetLay();
                if (net_lay != null) {
                    net_lay.setVisibility(VISIBLE);
                }
                if (error_lay != null) {
                    error_lay.setVisibility(GONE);
                }
            }
            if (loading_lay != null) {
                loading_lay.setVisibility(GONE);
            }
            if (empty_lay != null) {
                empty_lay.setVisibility(GONE);
            }
        } else if (State.SUCCESS == currentState) {
            setVisibility(GONE);
        } else if (State.LOADING == currentState) {
            initLoadingLay();
            if (loading_lay != null) {
                loading_lay.setVisibility(VISIBLE);
            }
            if (empty_lay != null) {
                empty_lay.setVisibility(GONE);
            }
            if (net_lay != null) {
                net_lay.setVisibility(GONE);
            }
            if (error_lay != null) {
                error_lay.setVisibility(GONE);
            }
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    public void setEmptyResource(int emptyResource) {
        this.emptyResource = emptyResource;
        if (emptyResource > 0) {
            empty_image.setBackgroundResource(this.emptyResource);
        }
    }

    @Override
    public void setErrorResource(int errorResource) {
        this.errorResource = errorResource;
        if (errorResource > 0) {
            error_image.setBackgroundResource(this.errorResource);
        }
    }

    @Override
    public void setNetResource(int netResource) {
        this.netResource = netResource;
        if (netResource > 0) {
            empty_image.setBackgroundResource(this.netResource);
        }
    }

    @Override
    public void setErrorTip(String errorTip) {
        this.errorTip = errorTip;
        if (!TextUtils.isEmpty(errorTip)) {
            error_tip.setText(this.errorTip);
        }
    }

    @Override
    public void setEmptyTip(String emptyTip) {
        this.emptyTip = emptyTip;
        if (!TextUtils.isEmpty(emptyTip)) {
            empty_tip.setText(this.emptyTip);
        }
    }

    @Override
    public void setLoadingTip(String loadingTip) {
        this.loadingTip = loadingTip;
        if (!TextUtils.isEmpty(loadingTip)) {
            loading_tip.setText(this.loadingTip);
        }
    }

    @Override
    public void setNetTip(String netTip) {
        this.netTip = netTip;
        if (!TextUtils.isEmpty(netTip)) {
            net_tip.setText(this.netTip);
        }
    }

    @Override
    public void onClick(View v) {
        if (mIPageStateCb != null) {
            mIPageStateCb.pageStateViewClick(currentState);
        }
    }

}
