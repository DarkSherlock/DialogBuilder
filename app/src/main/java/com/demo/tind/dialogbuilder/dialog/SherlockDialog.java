package com.demo.tind.dialogbuilder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
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
        private int mDialogStyle;

        public Builder(@NonNull Context context) {
            mContext = context;
            mParams = new ControllerParams();
            mController = new Controller(mContext);
        }

        /**
         * 如果设置的RootView里需要{@link Builder#setContentView(View)}的话
         * 那么RootView里需要添加一个id为fl_dialog_base_content的FrameLayout
         * 如果RootView不是宽高都是WRAP_CONTENT的话，那么就需要传入一个FrameLayout.LayoutParams
         * 否则会自动设置给它一个宽高都是WRAP_CONTENT的LayoutParams。
         *
         * @param layoutId RootView布局ID
         * @return Builder.this
         * @see Controller#setRootView(Dialog, int, FrameLayout.LayoutParams)
         * @see Builder#setRootView(int, FrameLayout.LayoutParams)
         */
        public Builder setRootView(@LayoutRes int layoutId) {
            return setRootView(layoutId, null);
        }

        /**
         * 如果设置的RootView里需要{@link Builder#setContentView(View)}的话
         * 那么RootView里需要添加一个id为fl_dialog_base_content的FrameLayout
         * 如果RootView不是宽高都是WRAP_CONTENT的话，那么就需要传入一个FrameLayout.LayoutParams
         * 否则会自动设置给它一个宽高都是WRAP_CONTENT的LayoutParams。
         *
         * @param layoutId RootView布局ID
         * @return Builder.this
         * @see Controller#setRootView(Dialog, int, FrameLayout.LayoutParams)
         * @see Builder#setRootView(int)
         */
        public Builder setRootView(@LayoutRes int layoutId, @Nullable FrameLayout.LayoutParams layoutParams) {
            mParams.setRootViewLayoutId(layoutId);
            mParams.setRootViewLayoutParams(layoutParams);
            return this;
        }

        /**
         * 下方的具体内容的布局
         * {@link Controller#setContentView(int, Dialog, LinearLayout.LayoutParams)} 最终调用此方法 将布局渲染出来
         *
         * @param layoutId 布局ID
         */
        public Builder setContentView(@LayoutRes int layoutId) {
            return setContentView(layoutId, null);
        }

        /**
         * 具体内容的布局
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
            mParams.setContentLayoutId(layoutId);
            mParams.setContentLayoutParams(layoutParams);
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
            mParams.setContentLayoutParams(layoutParams);
            return this;
        }

        @Override
        public Builder setOnClickListener(@IdRes int viewId, @Nullable View.OnClickListener listener) {
            mParams.setOnClickListener(viewId, listener);
            return this;
        }

        public Builder setMessage(String text) {
            return setText(R.id.dialog_tv_message, text);
        }

        public Builder setTitle(String text) {
            return setText(R.id.dialog_tv_title, text);
        }

        @Override
        public Builder setText(@IdRes int viewId, String text) {
            mParams.setText(viewId, text);
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
            setNegativeButton(mContext.getString(R.string.call_dialog_cancel), listener);
            return this;
        }

        /**
         * {@link Controller#setPositiveButton(String, OnPositiveListener)}
         *
         * @param listener
         * @return
         */
        public Builder setPositiveButton(@Nullable OnPositiveListener listener) {
            setPositiveButton(mContext.getString(R.string.call_dialog_submit), listener);
            return this;
        }

        /**
         * 如果是自定义RootView{@link Builder#setRootView(int)}的话，还要用这个方法的话
         * 那么RootView的取消的Button 的ID就要设置为btn_dialog_cancel
         *
         * @param text     button 文字
         * @param listener 点击回调
         * @return Builder.this
         */
        public Builder setNegativeButton(@Nullable String text, @Nullable OnNegativeListener listener) {
            mParams.setNegativeListener(listener);
            mParams.setNegativeText(text);
            return this;
        }

        /**
         * 如果是自定义RootView{@link Builder#setRootView(int)}的话，还要用这个方法的话
         * 那么RootView的确认的Button 的ID就要设置为btn_dialog_submit
         *
         * @param text     button 文字
         * @param listener 点击回调
         * @return Builder.this
         */
        public Builder setPositiveButton(@Nullable String text, @Nullable OnPositiveListener listener) {
            mParams.setPositiveListener(listener);
            mParams.setPositiveText(text);
            return this;
        }

        /**
         * 默认Gravity.center
         * 设置dialog gravity
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            return setGravity(gravity, 0, 0);
        }

        /**
         * 根据x，y坐标设置窗口需要显示的位置
         *
         * @param gravity //设置重力
         * @param x       x小于0左移，大于0右移
         * @param y       y小于0上移，大于0下移
         * @return
         */
        public Builder setGravity(int gravity, int x, int y) {
            mParams.setGravity(gravity, x, y);
            return this;
        }

        /**
         * 建造dialog
         *
         * @return dialog
         */
        public Dialog create() {
            if (mDialogStyle == 0) {
                mDialogStyle = R.style.AlertDialogStyle;
            }
            Dialog dialog = new Dialog(mContext, mDialogStyle);
            //属性交给controller去应用到dialog上
            mParams.apply(mController, dialog);
            return dialog;
        }

        public Builder setDialogStyle(@StyleRes int themeResId) {
            mDialogStyle = themeResId;
            return this;
        }

        /**
         * 建造一个 有 取消 和 确认 的 dialog
         *
         * @param onPositiveListener 确认按键监听
         * @param onNegativeListener 取消按键监听
         * @return dialog
         * {@link ControllerParams#apply(Controller, Dialog)} 最终将属性应用于dialog;
         */
        public Dialog createCancelAndSubmit(@Nullable OnPositiveListener onPositiveListener,
                                            @Nullable OnNegativeListener onNegativeListener) {
            setCancelable(false);
            setPositiveButton(onPositiveListener);
            setNegativeButton(onNegativeListener);
            return create();
        }

        public Dialog createSubmit(@Nullable OnPositiveListener onPositiveListener) {
            setCancelable(false).setPositiveButton(onPositiveListener);
            return create();
        }

    }

    private static class Controller implements View.OnClickListener, BuilderInterface {
        private Context mContext;
        private Button mBtnPositive;
        private Button mBtnNegative;
        private View mRootView;
        private FrameLayout.LayoutParams mRootViewLayoutParams;
        private OnNegativeListener mOnNegativeListener;
        private OnPositiveListener mOnPositiveListener;
        private Dialog mDialog;
        private View mContentView;
        private FrameLayout mFlContent;


        private Controller(Context context) {
            mContext = context;
        }

        public void setRootView(Dialog dialog, @LayoutRes int layoutId, FrameLayout.LayoutParams rootViewLayoutParams) {
            if (rootViewLayoutParams == null) {
                mRootViewLayoutParams = getPrams(mContext);
            } else {
                mRootViewLayoutParams = rootViewLayoutParams;
            }
            mDialog = dialog;
            mRootView = View.inflate(mContext, layoutId, null);
        }

        private void setContentView(@LayoutRes int layoutResID, Dialog dialog, LinearLayout.LayoutParams layoutParams) {
            if (layoutResID == -1) {
                //默认状态下
            } else {
                mFlContent = mRootView.findViewById(R.id.fl_dialog_base_content);
                if (mFlContent == null) {
                    Log.e("Controller", "setContentView: mFlContent of mRootView.findViewById(R.id.fl_dialog_base_content) is null");
                    throw new IllegalArgumentException("setContentView 需要在RootView里添加一个id为 fl_dialog_base_content 的FrameLayout");
                }

                mContentView = LayoutInflater.from(mContext).inflate(layoutResID, mFlContent, false);
                mFlContent.removeAllViews();
                if (layoutParams == null) {
                    mFlContent.addView(mContentView);
                } else {
                    mFlContent.addView(mContentView, layoutParams);
                }
            }
            mDialog = dialog;
            dialog.setContentView(mRootView, mRootViewLayoutParams);
        }

        private void setContentView(View view, Dialog dialog, LinearLayout.LayoutParams layoutParams) {
            mFlContent = mRootView.findViewById(R.id.fl_dialog_base_content);
            if (mFlContent == null) {
                Log.e("Controller", "setContentView: mFlContent of mRootView.findViewById(R.id.fl_dialog_base_content) is null");
                throw new IllegalArgumentException("setContentView 需要在RootView里添加一个id为 fl_dialog_base_content 的FrameLayout");
            }

            mFlContent.removeAllViews();
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            if (layoutParams == null) {
                mFlContent.addView(view);
            } else {
                mFlContent.addView(view, layoutParams);
            }

            mDialog = dialog;
            dialog.setContentView(mRootView, mRootViewLayoutParams);
        }

        private FrameLayout.LayoutParams getPrams(Context context) {
            return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
        }

        /**
         * 设置窗口显示
         *
         * @param gravity 位置
         * @param x       x偏移
         * @param y       y偏移
         */
        public void setWindowDeploy(int gravity, int x, int y) {
            Window window = mDialog.getWindow(); //得到对话框
            if (null == window) {
                return;
            }
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.gravity = gravity;//设置重力 Gravity.BOTTOM
            //根据x，y坐标设置窗口需要显示的位置
            wl.x = x; //x小于0左移，大于0右移
            wl.y = y; //y小于0上移，大于0下移
            window.setAttributes(wl);
        }


        @Override
        public BuilderInterface setOnClickListener(@IdRes int viewId, @Nullable View.OnClickListener listener) {
            View view = mRootView.findViewById(viewId);
            if (view != null) {
                view.setOnClickListener(listener);
            } else {
                Log.e("Controller", "setOnClickListener: find view by id is null! ");
            }
            return this;
        }

        @Override
        public BuilderInterface setText(int viewId, String text) {
            View view = mRootView.findViewById(viewId);
            if (view != null && text != null && view instanceof TextView) {
                ((TextView) view).setText(text);
                view.setVisibility(View.VISIBLE);
            } else {
                Log.e("Controller", "view of setText（viewId) is null ? or it is not a textview");
            }

            return this;
        }

        @Override
        public Controller setCancelable(boolean cancelable) {
            mDialog.setCancelable(cancelable);
            mDialog.setCanceledOnTouchOutside(cancelable);
            return this;
        }


        public Controller setNegativeButton(String negativeText, OnNegativeListener listener) {
            mBtnNegative = mRootView.findViewById(R.id.btn_dialog_cancel);
            if (mBtnNegative != null) {
                mBtnNegative.setVisibility(View.VISIBLE);
                mBtnNegative.setText(negativeText);
                setOnNegativeListener(listener);
            } else {
                throw new IllegalArgumentException("the id of Negative Button on RootView  must be R.id.btn_dialog_cancel !");
            }
            return this;
        }

        public Controller setPositiveButton(String positiveText, OnPositiveListener listener) {
            mBtnPositive = mRootView.findViewById(R.id.btn_dialog_submit);
            if (mBtnPositive != null) {
                mBtnPositive.setVisibility(View.VISIBLE);
                mBtnPositive.setText(positiveText);
                setOnPositiveListener(listener);
            } else {
                throw new IllegalArgumentException("the id of Positive Button on RootView must be R.id.btn_dialog_submit !");
            }
            return this;
        }


        private void setOnNegativeListener(OnNegativeListener onNegativeListener) {
            mOnNegativeListener = onNegativeListener;
            mBtnNegative.setOnClickListener(this);
        }

        private void setOnPositiveListener(OnPositiveListener onPositiveListener) {
            mOnPositiveListener = onPositiveListener;
            mBtnPositive.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.btn_dialog_cancel) {
                if (mOnNegativeListener != null) {
                    mOnNegativeListener.onNegative(mDialog);
                }
                mDialog.dismiss();

            } else if (i == R.id.btn_dialog_submit) {
                if (mOnPositiveListener != null) {
                    mOnPositiveListener.onPositive(mDialog);
                }
                mDialog.dismiss();

            }
        }


    }

    private interface BuilderInterface {
        BuilderInterface setOnClickListener(@IdRes int viewId, @Nullable View.OnClickListener listener);

        BuilderInterface setCancelable(boolean cancelable);

        BuilderInterface setNegativeButton(String negativeText, OnNegativeListener listener);

        BuilderInterface setPositiveButton(String positiveText, OnPositiveListener listener);

        BuilderInterface setText(@IdRes int viewId, String text);
    }


    /**
     * 管理dialog所有属性的类
     */
    private static class ControllerParams {
        private int mContentLayoutId = -1;
        private LinearLayout.LayoutParams mContentLayoutParams;
        private int mRootViewLayoutId = -1;
        private FrameLayout.LayoutParams mRootViewLayoutParams;
        private boolean mCancelable;
        private String mPositiveText;
        private String mNegativeText;
        private OnPositiveListener mPositiveListener;
        private OnNegativeListener mNegativeListener;
        private View mContentView;
        private SparseArray<View.OnClickListener> mOnClickListenerSpa;
        private SparseArray<String> mTextSpa;
        private int mGravity = -1;
        private int mX; //x 偏移值
        private int mY; //y 偏移值

        private void apply(Controller controller, Dialog dialog) {
            if (mRootViewLayoutId == -1) {
                mRootViewLayoutId = R.layout.dialog_base_content_view;
            }
            controller.setRootView(dialog, mRootViewLayoutId, mRootViewLayoutParams);

            if (mContentView != null) {
                controller.setContentView(mContentView, dialog, mContentLayoutParams);
            } else {
                controller.setContentView(mContentLayoutId, dialog, mContentLayoutParams);
            }

            if (mGravity != -1) {
                controller.setWindowDeploy(mGravity, mX, mY);
            }

            controller.setCancelable(mCancelable);

            if (mPositiveText != null) {
                controller.setPositiveButton(mPositiveText, mPositiveListener);
            }
            if (mNegativeText != null) {
                controller.setNegativeButton(mNegativeText, mNegativeListener);
            }

            if (mOnClickListenerSpa != null) {
                for (int i = 0; i < mOnClickListenerSpa.size(); i++) {
                    int viewId = mOnClickListenerSpa.keyAt(i);
                    controller.setOnClickListener(viewId, mOnClickListenerSpa.get(viewId, null));
                }
            }

            if (mTextSpa != null) {
                for (int i = 0; i < mTextSpa.size(); i++) {
                    int viewId = mTextSpa.keyAt(i);
                    controller.setText(viewId, mTextSpa.get(viewId));
                }
            }
        }

        public int getRootViewLayoutId() {
            return mRootViewLayoutId;
        }

        public void setRootViewLayoutId(int rootViewLayoutId) {
            mRootViewLayoutId = rootViewLayoutId;
        }

        public FrameLayout.LayoutParams getRootViewLayoutParams() {
            return mRootViewLayoutParams;
        }

        public void setRootViewLayoutParams(FrameLayout.LayoutParams rootViewLayoutParams) {
            mRootViewLayoutParams = rootViewLayoutParams;
        }

        public int getContentLayoutId() {
            return mContentLayoutId;
        }

        private void setContentLayoutId(int contentLayoutId) {
            mContentLayoutId = contentLayoutId;
        }

        public LinearLayout.LayoutParams getContentLayoutParams() {
            return mContentLayoutParams;
        }

        public void setContentLayoutParams(LinearLayout.LayoutParams contentLayoutParams) {
            mContentLayoutParams = contentLayoutParams;
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

        public void setGravity(int gravity, int x, int y) {
            this.mGravity = gravity;
            this.mX = x;
            this.mY = y;
        }

        private void setContentView(View contentView) {
            mContentView = contentView;
        }

        private void setOnClickListener(@IdRes int viewId, @Nullable View.OnClickListener listener) {
            if (mOnClickListenerSpa == null) {
                mOnClickListenerSpa = new SparseArray<>();
            }
            mOnClickListenerSpa.put(viewId, listener);
        }

        private void setText(@IdRes int viewId, String text) {
            if (mTextSpa == null) {
                mTextSpa = new SparseArray<>();
            }
            mTextSpa.put(viewId, text);
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
