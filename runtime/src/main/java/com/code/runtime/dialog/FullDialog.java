package com.code.runtime.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.code.runtime.R;

public class FullDialog extends DialogFragment {

    private FragmentManager fragmentManager;

    private int gravity;

    private View contentView;

    private int dialogConentWidthInGravityLeftAndRight;

    private boolean needBackGround;

    public static FullDialog newIntance(FragmentManager fragmentManager) {
        FullDialog fullDialog = new FullDialog();
        fullDialog.setFragmentManager(fragmentManager);
        return fullDialog;
    }

    public FullDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public FullDialog setContentView(View view, int widthOrHeight) {
        this.contentView = view;
        dialogConentWidthInGravityLeftAndRight = widthOrHeight;
        return this;
    }

    public FullDialog setNeedBackGround(boolean need) {
        this.needBackGround = need;
        return this;
    }

    public FullDialog setContentView(View view) {
        this.contentView = view;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (getShowsDialog()) {
            setShowsDialog(false);
        }
        super.onActivityCreated(savedInstanceState);
        setShowsDialog(true);

        View view = getView();
        if (view != null) {
            if (view.getParent() != null) {
                throw new IllegalStateException(
                        "DialogFragment can not be attached to a container view");
            }
            getDialog().setContentView(view);
        }
        final Activity activity = getActivity();
        if (activity != null) {
            getDialog().setOwnerActivity(activity);
        }
        if (savedInstanceState != null) {
            Bundle dialogState = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG);
            if (dialogState != null) {
                getDialog().onRestoreInstanceState(dialogState);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        switch (gravity) {
            case Gravity.LEFT:
                window.setWindowAnimations(R.style.AnimationLeft);
                window.setLayout(dialogConentWidthInGravityLeftAndRight > 0 ?
                                dialogConentWidthInGravityLeftAndRight : ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);//Here!
                break;
            case Gravity.TOP:
                window.setWindowAnimations(R.style.AnimationTop);
                window.setLayout(dialogConentWidthInGravityLeftAndRight > 0 ?
                                dialogConentWidthInGravityLeftAndRight : ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);//Here!
                break;
            case Gravity.RIGHT:
                window.setWindowAnimations(R.style.AnimationRight);
                window.setLayout(dialogConentWidthInGravityLeftAndRight > 0 ?
                        dialogConentWidthInGravityLeftAndRight : ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);//Here!
                break;
            case Gravity.BOTTOM:
                window.setWindowAnimations(R.style.AnimationBottom);
                window.setLayout(dialogConentWidthInGravityLeftAndRight >
                                0 ? dialogConentWidthInGravityLeftAndRight : ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);//Here!
                break;
            case Gravity.CENTER:
//                window.setWindowAnimations(R.style.AnimationBottom);
                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//Here!
                break;
            default:
                break;
        }
        window.setGravity(gravity);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setGravity(LinearLayout.VERTICAL);
//        ViewGroup.LayoutParams layoutParams = null;
//        switch (gravity) {
//            case Gravity.LEFT:
//                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);//Here!
//                break;
//            case Gravity.TOP:
//                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//Here!
//                break;
//            case Gravity.RIGHT:
//                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//Here!
//                break;
//            case Gravity.BOTTOM:
//                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
//                        .MATCH_PARENT);//Here!
//                break;
//            case Gravity.CENTER:
//                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
//                        .MATCH_PARENT);//Here!
//                break;
//        }
        Dialog dialog = new Dialog(getActivity(), needBackGround ? R.style.Dialog_NoTitle : R.style.Dialog_NoTitle_transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
//        linearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        linearLayout.setLayoutParams(layoutParams);
        linearLayout.addView(contentView);
        dialog.setContentView(linearLayout);
        return dialog;
    }


    public void showDidlog() {
        super.show(fragmentManager, this.getClass().getName());
    }
}
