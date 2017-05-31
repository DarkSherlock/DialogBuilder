package com.demo.tind.dialogbuilder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.tind.dialogbuilder.dialog.MyDialogUtils;
import com.demo.tind.dialogbuilder.dialog.SherlockDialog;

/**
 * Created by Administrator on 2017/5/5.
 */

public class DialogActivity extends AppCompatActivity implements SherlockDialog.OnPositiveListener, SherlockDialog.OnNegativeListener, View.OnClickListener {

    private Dialog mDialog;
    private AlertDialog mAlertDialog;
    private View mAletView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
//        initDialog();
//        initEditDialog();
        initDefaultDialog();
    }

    private void initDefaultDialog() {
        SherlockDialog.Builder builder = new SherlockDialog.Builder(this);
        mDialog = builder.createDefault(this, this);
        builder.reSetTitle("重新设置Tiltle");
    }

    private void initEditDialog() {
        SherlockDialog.Builder builder = new SherlockDialog.Builder(this);

        EditText editText = new EditText(this);
        builder.setContentView(editText);
        mDialog = builder.createDefault(this, this);

    }

    private void initDialog() {

        SherlockDialog.Builder builder = new SherlockDialog.Builder(this);
        mDialog = builder.setTitle("温馨提示")
                .setMessage("确认提交吗？")
                .setPositiveButton("确认", new SherlockDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(Dialog dialog) {
                        Toast.makeText(DialogActivity.this, "确认提交", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new SherlockDialog.OnNegativeListener() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        Toast.makeText(DialogActivity.this, "取消提交", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setIcon(R.mipmap.ic_launcher_round).create();
    }

    public void button1(View view) {
        mDialog.show();
    }

    @Override
    public void onPositive(Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void onNegative(Dialog dialog) {
        dialog.dismiss();
    }

    public void initTestDialog() {
        mAlertDialog = MyDialogUtils.initDialog(this, R.layout.dialog_gs_plan_view, this);
        mAletView = MyDialogUtils.getView();
        mAlertDialog.setView(mAletView);
    }

    @Override
    public void onClick(View v) {

    }

    private void showEditDialog(Button btn, String message) {
        mDialog.show();
        setDialogWindowView();

    }

    private void setDialogWindowView() {
        Window mWindow = mDialog.getWindow();
        mWindow.setContentView(View.inflate(DialogActivity.this, R.layout.dialog_gs_plan_view, null), MyDialogUtils.getPrams(this));
        EditText mDialogEditText = (EditText) mWindow.findViewById(R.id.edit);
        TextView mDialogTvTitle = (TextView) mWindow.findViewById(R.id.title);
        TextView tvCancel = (TextView) mWindow.findViewById(R.id.dialog_cancel);
        TextView tvCommit = (TextView) mWindow.findViewById(R.id.dialog_commit);
        tvCommit.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }

}
