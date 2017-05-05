package com.demo.tind.dialogbuilder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.tind.dialogbuilder.R;

/**
 * Created by Administrator on 2017/4/28.
 */

public class SherlockDialog {
    public class Builder implements BuilderInterface {
        private Context mContext;
        private final ControllerParams mParams;
        private Controller mController;

        public Builder(Context context) {
            mContext = context;
            mParams = new ControllerParams();
            mController = new Controller(mContext);
        }

        public Builder setContentView(int layoutId) {
            mParams.setLayoutId(layoutId);
            return this;
        }

        public Builder setContentView(View view) {
            mParams.setContentView(view);
            return this;
        }

        public Builder setTitle(String title) {
            mParams.setTitle(title);
            return this;
        }

        public Builder reSetTitle(String title) {
            mParams.setTitle(title);
            mController.setTitle(title);
            return this;
        }

        public Builder reSetMessage(String message) {
            mParams.setMessage(message);
            mController.setMessage(message);
            return this;
        }

        public Builder reSetIcon(int iconId) {
            mParams.setIconId(iconId);
            mController.setIcon(iconId);
            return this;
        }

        public Builder setMessage(String message) {
            mParams.setMessage(message);
            return this;
        }

        public Builder setIcon(int iconId) {
            mParams.setIconId(iconId);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mParams.setCancelable(cancelable);
            return this;
        }


        public Builder setNegativeButton(String text, OnNegativeListener listener) {
            mParams.setNegativeListener(listener);
            mParams.setNegativeText(text);

            return this;
        }

        public Builder setPositiveButton(String text,  OnPositiveListener listener)  {
            mParams.setPositiveListener(listener);
            mParams.setPositiveText(text);
            return this;
        }

        /**
         * 建造dialog
         * @return dialog
         */
        public Dialog create() {
            Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
            //属性交给controller去应用到dialog上
            mParams.apply(mController, dialog);
            return dialog;
        }

        /**
         * 建造一个默认的dialog
         * @param onPositiveListener 确认按键监听
         * @param onNegativeListener 取消按键监听
         * @return dialog
         */
        public Dialog createDefault(OnPositiveListener onPositiveListener, OnNegativeListener onNegativeListener) {
            Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
            build(onPositiveListener, onNegativeListener);
            mParams.apply(mController, dialog);
            return dialog;
        }

        private void build(OnPositiveListener onPositiveListener, OnNegativeListener onNegativeListener) {
            setTitle("温馨提示")
                    .setMessage("确认提交吗")
                    .setCancelable(false)
                    .setPositiveButton("确认", onPositiveListener)
                    .setNegativeButton("取消", onNegativeListener);
        }
    }

    private class Controller implements View.OnClickListener {
        private Context mContext;
        private TextView mTvTitle;
        private TextView mTvMessage;
        private TextView mTvPositive;
        private TextView mTvNegative;
        private ImageView mIvIcon;
        private View mHorizontalLine;
        private View mVerticalLine;
        private View mRootView;
        private OnNegativeListener mOnNegativeListener;
        private OnPositiveListener mOnPositiveListener;
        private Dialog mDialog;
        private View mContentView;
        private final LinearLayout mLlContent;


        private   Controller(Context context) {
            mContext = context;
            mRootView = View.inflate(mContext, R.layout.view_dialog_warm_tip, null);
            mLlContent = (LinearLayout) mRootView.findViewById(R.id.ll_content);
            mHorizontalLine = mRootView.findViewById(R.id.line_horizontal);
        }

        private void setContentView(int layoutResID, Dialog dialog) {
            if (layoutResID == -1) {

            } else {
                mContentView = View.inflate(mContext, layoutResID, null);
                mLlContent.removeAllViews();
                mLlContent.addView(mContentView);
            }
            mDialog = dialog;
            dialog.setContentView(mRootView, getPrams(mContext));
        }

        private void setContentView(View view, Dialog dialog) {
            mLlContent.removeAllViews();
            mLlContent.addView(view);
            mDialog = dialog;
            dialog.setContentView(mRootView, getPrams(mContext));
        }

        private FrameLayout.LayoutParams getPrams(Context context) {
            WindowManager windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            Display sDefaultDisplay = windowManager.getDefaultDisplay();

            return new FrameLayout.LayoutParams(
                    (int) (sDefaultDisplay.getWidth() * 0.80f),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

        }


        private void setTitle(String title) {
            mTvTitle = (TextView) mRootView.findViewById(R.id.title);
            if (mTvTitle != null) {
                mTvTitle.setText(title);
            }
        }

        private void setMessage(String message) {
            mTvMessage = (TextView) mRootView.findViewById(R.id.message);
            if (mTvMessage != null) {
                mTvMessage.setText(message);
            }

        }

        private void setIcon(int iconId) {
            mIvIcon = (ImageView) mRootView.findViewById(R.id.icon);
            if (mIvIcon != null) {
                mIvIcon.setImageResource(iconId);
                mIvIcon.setVisibility(View.VISIBLE);
            }

        }


        private void setNegativeButton(String negativeText, OnNegativeListener listener) {
            mTvNegative = (TextView) mRootView.findViewById(R.id.dialog_cancel);
            mVerticalLine = mRootView.findViewById(R.id.line_vertical);
            if (mTvNegative != null) {
                mTvNegative.setVisibility(View.VISIBLE);
                mTvNegative.setText(negativeText);
                setOnNegativeListener(listener);
                mHorizontalLine.setVisibility(View.VISIBLE);
                if (mTvPositive != null) {
                    mVerticalLine.setVisibility(View.VISIBLE);
                }

            }
        }

        private void setPositiveButton(String positiveText, OnPositiveListener listener) {
            mTvPositive = (TextView) mRootView.findViewById(R.id.dialog_commit);

            if (mTvPositive != null) {
                mTvPositive.setVisibility(View.VISIBLE);
                mTvPositive.setText(positiveText);
                setOnPositiveListener(listener);
                mHorizontalLine.setVisibility(View.VISIBLE);
            }
        }

        private void setOnNegativeListener(OnNegativeListener onNegativeListener) {

            mOnNegativeListener = onNegativeListener;
            mTvNegative.setOnClickListener(this);
        }

        private void setOnPositiveListener(OnPositiveListener onPositiveListener) {

            mOnPositiveListener = onPositiveListener;
            mTvPositive.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_cancel:
                    if (mOnNegativeListener != null) {
                        mOnNegativeListener.onNegative(mDialog);
                    }
                    break;
                case R.id.dialog_commit:
                    if (mOnPositiveListener != null) {
                        mOnPositiveListener.onPositive(mDialog);
                    }
                    break;
            }
        }
    }

    public interface BuilderInterface {

        Builder setTitle(String title);

        Builder setMessage(String message);

        Builder setIcon(int incoId);

        Builder setCancelable(boolean cancelable);

        Builder setNegativeButton(String negativeText, OnNegativeListener listener);

        Builder setPositiveButton(String positiveText, OnPositiveListener listener);
    }


    /**
     * 管理dialog所有属性的类
     */
    private static class ControllerParams {
        private String mTitle;
        private String mMessage;
        private int mIconId = -1;
        private int mLayoutId = -1;
        private boolean mCancelable;
        private String mPositiveText;
        private String mNegativeText;
        private OnPositiveListener mPositiveListener;
        private OnNegativeListener mNegativeListener;
        private View mContentView;


        private void apply(Controller controller, Dialog dialog) {

            if (mContentView != null) {
                controller.setContentView(mContentView, dialog);
            }else {
                controller.setContentView(mLayoutId, dialog);
            }
            dialog.setCancelable(mCancelable);
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }

            if (mTitle != null) {
                controller.setTitle(mTitle);
            }
            if (mMessage != null) {
                controller.setMessage(mMessage);
            }
            if (mPositiveText != null && mPositiveListener != null) {
                controller.setPositiveButton(mPositiveText, mPositiveListener);
            }
            if (mNegativeText != null && mNegativeListener != null) {
                controller.setNegativeButton(mNegativeText, mNegativeListener);
            }
            if (mIconId != -1) {
                controller.setIcon(mIconId);
            }


        }


        public int getLayoutId() {
            return mLayoutId;
        }

        private void setLayoutId(int layoutId) {
            mLayoutId = layoutId;
        }

        public String getTitle() {
            return mTitle;
        }

        private void setTitle(String title) {
            mTitle = title;
        }

        public String getMessage() {
            return mMessage;
        }

        private void setMessage(String message) {
            mMessage = message;
        }

        public int getIconId() {
            return mIconId;
        }

        private void setIconId(int iconId) {
            mIconId = iconId;
        }

        public boolean isCancelable() {
            return mCancelable;
        }

        private void setCancelable(boolean cancelable) {
            mCancelable = cancelable;
        }

        public String getPositiveText() {
            return mPositiveText;
        }

        private void setPositiveText(String positiveText) {
            mPositiveText = positiveText;
        }

        public String getNegativeText() {
            return mNegativeText;
        }

        private void setNegativeText(String negativeText) {
            mNegativeText = negativeText;
        }

        public OnPositiveListener getPositiveListener() {
            return mPositiveListener;
        }

        private void setPositiveListener(OnPositiveListener positiveListener) {
            mPositiveListener = positiveListener;
        }

        public OnNegativeListener getNegativeListener() {
            return mNegativeListener;
        }

        private void setNegativeListener(OnNegativeListener negativeListener) {
            mNegativeListener = negativeListener;
        }

        private void setContentView(View contentView) {
            mContentView = contentView;
        }
    }

    /**
     * 确认按键监听
     */
    public interface OnPositiveListener {
        void onPositive(Dialog dialog);

    }

    /**
     * 取消按键监听
     */
    public interface OnNegativeListener {
        void onNegative(Dialog dialog);
    }
}
