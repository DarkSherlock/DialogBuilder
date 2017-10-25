package com.demo.tind.dialogbuilder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.tind.dialogbuilder.dialog.SherlockDialog;

/**
 * Created by Administrator on 2017/5/5.
 */

public class DialogActivity extends AppCompatActivity implements SherlockDialog.OnPositiveListener, SherlockDialog.OnNegativeListener {

    private Dialog mDialog;


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
//        dialog.dismiss();
    }

    @Override
    public void onNegative(Dialog dialog) {
//        dialog.dismiss();
    }





}
