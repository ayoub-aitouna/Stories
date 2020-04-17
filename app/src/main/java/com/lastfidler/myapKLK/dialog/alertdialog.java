package com.lastfidler.myapKLK.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class alertdialog extends AppCompatDialogFragment {
    private exite listenr;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listenr = (exite) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "error");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("").setMessage("Are you sure !").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listenr.onyesclick();
            }
        });
        return builder.create();

    }

    public interface exite {
        void onyesclick();
    }
}
