package com.example.myapplication;



import static com.example.myapplication.R.*;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class WinDialog extends Dialog {

    private final String message;
    private final MainActivity mainActivity;
    public MediaPlayer ckr;
    public WinDialog(@NonNull Context context, String message) {
        super(context);
        this.message = message;
        this.mainActivity = (MainActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.win_dialog_layout);

        final TextView msgTextView = findViewById(id.msgTextView);
        final AppCompatButton startAgainBtn = findViewById(id.startAgainBtn);

        msgTextView.setText(message);

        startAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startMatchAgain();
                dismiss();
            }
        });
    }
}
