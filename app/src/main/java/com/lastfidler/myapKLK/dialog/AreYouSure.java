package com.lastfidler.myapKLK.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AreYouSure extends AppCompatDialogFragment {
    private sure sure;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            sure = (sure) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "error");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are You Sure").setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sure.Sure();
            }
        });
        return builder.create();
    }

    public interface sure {
        void Sure();
    }
}
