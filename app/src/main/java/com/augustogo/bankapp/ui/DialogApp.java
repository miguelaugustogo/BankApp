package com.augustogo.bankapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.augustogo.bankapp.R;

public class DialogApp {
    private static Dialog createDialog(Context context, int layout) {
        Dialog dialog = new Dialog(context, R.style.CustomAlertDialog);
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setContentView (layout);
        dialog.setCancelable (false);
        dialog.getWindow ().setSoftInputMode (WindowManager.LayoutParams.
                SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
        return dialog;
    }
    public static void showDialogConnection(Context context) {
        final Dialog dialog = createDialog (context, R.layout.dialog_connection);
        Button buttonConnection = dialog.findViewById (R.id.button_dialog_connection);
        buttonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
