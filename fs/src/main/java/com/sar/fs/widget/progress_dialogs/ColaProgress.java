package com.sar.fs.widget.progress_dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.sar.fs.R.id;
import com.sar.fs.R.layout;
import com.sar.fs.R.style;

/**
 * @auth: sarWang
 * @date: 2019-07-05 14:35
 * @describe
 */
public class ColaProgress extends Dialog {
    public ColaProgress(Context context, int theme) {
        super(context, theme);
    }

    public static ColaProgress show(Context context, CharSequence message, boolean cancelable, boolean cancelableOutsite, OnCancelListener onCancelListener) {
        ColaProgress colaProgress = new ColaProgress(context, style.ColaProgress);
        colaProgress.setTitle("");
        colaProgress.setContentView(layout.view_progress_dialog);
        if (message.length() != 0 && message != null) {
            TextView txtMessage = colaProgress.findViewById(id.message);
            txtMessage.setText(message);
        } else {
            colaProgress.findViewById(id.message).setVisibility(View.GONE);
        }

        colaProgress.setCancelable(cancelable);
        colaProgress.setCanceledOnTouchOutside(cancelableOutsite);
        colaProgress.setOnCancelListener(onCancelListener);
        WindowManager.LayoutParams lp = colaProgress.getWindow().getAttributes();
        lp.gravity = 17;
        lp.dimAmount = 0.3F;
        colaProgress.getWindow().setAttributes(lp);
        colaProgress.show();
        return colaProgress;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = this.findViewById(id.imageView);
        AnimationDrawable spinner = (AnimationDrawable)imageView.getBackground();
        spinner.start();
    }

    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            this.findViewById(id.message).setVisibility(View.VISIBLE);
            TextView txt = this.findViewById(id.message);
            txt.setText(message);
            txt.invalidate();
        }

    }
}
