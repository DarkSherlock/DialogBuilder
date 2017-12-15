package com.demo.tind.dialogbuilder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    public static class Builder implements BuilderInterface {
        private Context mContext;
        private final ControllerParams mParams;
        private Controller mController;

        public Builder(@NonNull Context context) {
            mContext = context;
            mParams = new ControllerParams();
            mController = new Controller(mContext);
        }

        /**
         * Title 下方的具体内容的布局
         * {@link Controller#setContentView(int, Dialog, LinearLayout.LayoutParams)} 最终调用此方法 将布局渲染出来
         *
         * @param layoutId 布局ID
         */
        public Builder setContentView(@LayoutRes int layoutId) {
            return setContentView(layoutId, null);
        }

        /**
         * Title 下方的具体内容的布局
         * {@link Controller#setContentView(View, Dialog, LinearLayout.LayoutParams)}  最终调用此方法 将布局渲染出来
         *
         * @param view 添加的view
         */
        public Builder setContentView(@NonNull View view) {
            return setContentView(view, null);
        }

        /**
         * Title 下方的具体内容的布局
         * {@link Controller#setContentView(int, Dialog, LinearLayout.LayoutParams)} 最终调用此方法 将布局渲染出来
         *
         * @param layoutId 布局ID
         */
        public Builder setContentView(@LayoutRes int layoutId, LinearLayout.LayoutParams layoutParams) {
            mParams.setLayoutId(layoutId);
            mParams.setLayoutParams(layoutParams);
            return this;
        }

        /**
         * Title 下方的具体内容的布局
         * {@link Controller#setContentView(View, Dialog, LinearLayout.LayoutParams)}  最终调用此方法 将布局渲染出来
         *
         * @param view 添加的view
         */
        public Builder setContentView(@NonNull View view, LinearLayout.LayoutParams layoutParams) {
            mParams.setContentView(view);
            mParams.setLayoutParams(layoutParams);
            return this;
        }

        public Builder setTitle() {
            setTitle("温馨提示");
            return this;
        }

        /**
         * {@link Controller#setTitle(String)} 最终调用此方法 将title赋值
         *
         * @param title 标题
         */
        public Builder setTitle(@Nullable String title) {
            mParams.setTitle(title);
            return this;
        }

        /**
         * {@link Controller#setTitle(String)} 最终调用此方法 将title赋值
         *
         * @param title 标题
         */
        public Builder reSetTitle(@Nullable String title) {
            mParams.setTitle(title);
            mController.setTitle(title);
            return this;
        }

        /**
         * {@link Controller#setMessage(String)} 最终调用此方法 将message赋值
         *
         * @param message
         */
        public Builder setMessage(@Nullable String message) {
            mParams.setMessage(message);
            return this;
        }

        public Builder reSetMessage(@Nullable String message) {
            mParams.setMessage(message);
            mController.setMessage(message);
            return this;
        }

        public Builder reSetIcon(@DrawableRes int iconId) {
            mParams.setIconId(iconId);
            mController.setIcon(iconId);
            return this;
        }

        /**
         * {@link Controller#setIcon(int)}
         *
         * @param iconId
         */
        public Builder setIcon(@DrawableRes int iconId) {
            mParams.setIconId(iconId);
            return this;
        }

        /**
         * {@link Controller#setCancelable(boolean)}
         *
         * @param cancelable
         */
        public Builder setCancelable(boolean cancelable) {
            mParams.setCancelable(cancelable);
            return this;
        }

        /**
         * {@link Controller#setNegativeButton(String, OnNegativeListener)}
         *
         * @param listener
         * @return
         */
        public Builder setNegativeButton(@Nullable OnNegativeListener listener) {
            setNegativeButton("取消", listener);
            return this;
        }

        /**
         * {@link Controller#setPositiveButton(String, OnPositiveListener)}
         *
         * @param listener
         * @return
         */
        public Builder setPositiveButton(@Nullable OnPositiveListener listener) {
            setPositiveButton("确定", listener);
            return this;
        }

        public Builder setNegativeButton(@Nullable String text, @Nullable OnNegativeListener listener) {
            mParams.setNegativeListener(listener);
            mParams.setNegativeText(text);
            return this;
        }

        public Builder setPositiveButton(@Nullable String text, @Nullable OnPositiveListener listener) {
            mParams.setPositiveListener(listener);
            mParams.setPositiveText(text);
            return this;
        }

        /**
         * 建造dialog
         *
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
         *
         * @param onPositiveListener 确认按键监听
         * @param onNegativeListener 取消按键监听
         * @return dialog
         * {@link ControllerParams#apply(Controller, Dialog)} 最终将属性应用于dialog;
         */
        public Dialog createDefault(@Nullable OnPositiveListener onPositiveListener,
                                    @Nullable OnNegativeListener onNegativeListener) {
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

    private static class Controller implements View.OnClickListener, BuilderInterface {
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


        private Controller(Context context) {
            mContext = context;
            mRootView = View.inflate(mContext, R.layout.view_dialog_warm_tip, null);
            mLlContent = (LinearLayout) mRootView.findViewById(R.id.ll_content);
            mHorizontalLine = mRootView.findViewById(R.id.line_horizontal);
        }

        private void setContentView(@LayoutRes int layoutResID, Dialog dialog, LinearLayout.LayoutParams layoutParams) {
            if (layoutResID == -1) {
                //默认状态下
            } else {
                try {
                    mContentView = View.inflate(mContext, layoutResID, null);
                    mLlContent.removeAllViews();
                    if (layoutParams == null) {
                        mLlContent.addView(mContentView);
                    } else {
                        mLlContent.addView(mContentView, layoutParams);
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("非法的布局ID");
                }
            }
            mDialog = dialog;
            dialog.setContentView(mRootView, getPrams(mContext));
        }

        private void setContentView(View view, Dialog dialog, LinearLayout.LayoutParams layoutParams) {
            mLlContent.removeAllViews();
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            if (layoutParams == null) {
                mLlContent.addView(view);
            } else {
                mLlContent.addView(view, layoutParams);
            }

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

        public Controller setTitle(String title) {
            mTvTitle = (TextView) mRootView.findViewById(R.id.title);
            if (mTvTitle != null) {
                mTvTitle.setText(title);
            }
            return this;
        }

        public Controller setMessage(String message) {
            mTvMessage = (TextView) mRootView.findViewById(R.id.message);
            if (mTvMessage != null) {
                mTvMessage.setText(message);
            }
            return this;
        }

        public Controller setIcon(int iconId) {
            mIvIcon = (ImageView) mRootView.findViewById(R.id.icon);
            if (mIvIcon != null) {
                mIvIcon.setImageResource(iconId);
                mIvIcon.setVisibility(View.VISIBLE);
            }
            return this;
        }

        @Override
        public Controller setCancelable(boolean cancelable) {
            return null;
        }


        public Controller setNegativeButton(String negativeText, OnNegativeListener listener) {
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
            return this;
        }

        public Controller setPositiveButton(String positiveText, OnPositiveListener listener) {
            mTvPositive = (TextView) mRootView.findViewById(R.id.dialog_commit);

            if (mTvPositive != null) {
                mTvPositive.setVisibility(View.VISIBLE);
                mTvPositive.setText(positiveText);
                setOnPositiveListener(listener);
                mHorizontalLine.setVisibility(View.VISIBLE);
            }
            return this;
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
                    mDialog.dismiss();
                    break;
                case R.id.dialog_commit:
                    if (mOnPositiveListener != null) {
                        mOnPositiveListener.onPositive(mDialog);
                    }
                    mDialog.dismiss();
                    break;
            }
        }
    }

    public interface BuilderInterface {

        BuilderInterface setTitle(String title);

        BuilderInterface setMessage(String message);

        BuilderInterface setIcon(int incoId);

        BuilderInterface setCancelable(boolean cancelable);

        BuilderInterface setNegativeButton(String negativeText, OnNegativeListener listener);

        BuilderInterface setPositiveButton(String positiveText, OnPositiveListener listener);
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
        private LinearLayout.LayoutParams mLayoutParams;

        private void apply(Controller controller, Dialog dialog) {

            if (mContentView != null) {
                controller.setContentView(mContentView, dialog, mLayoutParams);
            } else {
                controller.setContentView(mLayoutId, dialog, mLayoutParams);
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

        public LinearLayout.LayoutParams getLayoutParams() {
            return mLayoutParams;
        }

        public void setLayoutParams(LinearLayout.LayoutParams layoutParams) {
            mLayoutParams = layoutParams;
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
