package com.sar.fsdemo.business.mode.http;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sar.fsdemo.R;
import com.sar.fsdemo.app.MDMApp;
import com.sar.fsdemo.app.base.BaseDialogFragment;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2017/4/19
 * @describe
 */

@EFragment(R.layout.comit_error_dialog_layout)
public class TokenErrorDialog extends BaseDialogFragment {

    @ViewById
    TextView error_title;

    @ViewById
    TextView dialog_return;

    int status = 0;  //0需要关闭当前所有  1 直接关闭弹出框  2 服务器停用

    String msg = "";

    @AfterViews
    public void initView() {
        //别名置空
        error_title.setText(R.string.token_error_title);
        dialog_return.setText(R.string.token_error_btn);

        if (msg != null && !msg.equals("")) {
            error_title.setText(msg);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyCustomDialog);
        status = getArguments().getInt("status");
        msg = getArguments().getString("msg");
        this.setCancelable(false);
    }

    public static void show(int status) {
        TokenErrorDialog_ tokenErrorDialog = new TokenErrorDialog_();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        tokenErrorDialog.setArguments(bundle);
        tokenErrorDialog.show(MDMApp.getInstance().getActivity().getFragmentManager(), "tokenErrorDialog");
    }

    public static void show(int status, String msg) {
        TokenErrorDialog_ tokenErrorDialog = new TokenErrorDialog_();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        bundle.putString("msg", msg);
        tokenErrorDialog.setArguments(bundle);
        tokenErrorDialog.show(MDMApp.getInstance().getActivity().getFragmentManager(), "tokenErrorDialog");
    }

    @Click({R.id.dialog_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_return:
                if (status == 1) {
                    dismiss();
                }
//                if (status == 0) {
//                    Intent intent = new Intent(FEApp.getInstance(), LoginActivity_.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    ShareSDK.getPlatform(Wechat.NAME).removeAccount(true);
//                    FEApp.getInstance().startActivity(intent);
//                    FEApp.getInstance().exit();
//                }
//                if (status == 2) {
//                    Intent intent = new Intent(FEApp.getInstance(), LoginActivity_.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    FEApp.getInstance().startActivity(intent);
//                    FEApp.getInstance().exit();
//                }
                break;
        }
    }

}
