package com.sar.fs.app.exception;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sar.fs.R.layout;
import com.sar.fs.R.id;

/**
 * @auth: sarWang
 * @date: 2019-07-05 14:00
 * @describe
 */

public final class FSBaseErrorActivity extends Activity {
    public FSBaseErrorActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_oncrash);
        final Class<? extends Activity> restartActivityClass = FSExceptionUncaught.getRestartActivityClassFromIntent(this.getIntent());
        TextView errorDetailsText = this.findViewById(id.error_details);
        errorDetailsText.setText(FSExceptionUncaught.getStackTraceFromIntent(this.getIntent()));
        Button restartButton = this.findViewById(id.customactivityoncrash_error_activity_restart_button);
        if (restartActivityClass != null) {
            restartButton.setText("重启应用");
            restartButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(FSBaseErrorActivity.this, restartActivityClass);
                    FSExceptionUncaught.restartApplicationWithIntent(FSBaseErrorActivity.this, intent);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FSExceptionUncaught.closeApplication(FSBaseErrorActivity.this);
                }
            });
        }

        Button moreInfoButton = this.findViewById(id.customactivityoncrash_error_activity_more_info_button);
        if (FSExceptionUncaught.isShowErrorDetailsFromIntent(this.getIntent())) {
            moreInfoButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog dialog = (new AlertDialog.Builder(FSBaseErrorActivity.this)).setTitle("异常信息").setMessage(FSExceptionUncaught.getAllErrorDetailsFromIntent(FSBaseErrorActivity.this, FSBaseErrorActivity.this.getIntent())).setPositiveButton("关闭", null).setNeutralButton("复制", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FSBaseErrorActivity.this.copyErrorToClipboard();
                            Toast.makeText(FSBaseErrorActivity.this, "已复制到剪切板", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                    TextView textView = dialog.findViewById(id.error_details);
                    textView.setTextSize(0, 12.0F);
                }
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }

        int defaultErrorActivityDrawableId = FSExceptionUncaught.getDefaultErrorActivityDrawableIdFromIntent(this.getIntent());
        ImageView errorImageView = this.findViewById(id.customactivityoncrash_error_activity_image);
        if (Build.VERSION.SDK_INT >= 21) {
            errorImageView.setImageDrawable(this.getResources().getDrawable(defaultErrorActivityDrawableId, this.getTheme()));
        } else {
            errorImageView.setImageDrawable(this.getResources().getDrawable(defaultErrorActivityDrawableId));
        }

    }

    private void copyErrorToClipboard() {
        String errorInformation = FSExceptionUncaught.getAllErrorDetailsFromIntent(this, this.getIntent());
        if (Build.VERSION.SDK_INT >= 11) {
            ClipboardManager clipboard = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("异常信息", errorInformation);
            clipboard.setPrimaryClip(clip);
        } else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(errorInformation);
        }

    }
}
