package com.mgsofttech.ddmods;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityBase extends AppCompatActivity {

    AlertDialog dialogProgress;
    public void setupBackButton() {
        ((ImageView) findViewById(R.id.itemBack)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ActivityBase.this.finish();
            }
        });
    }

    public void showProgressBar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.dialog_progress, (ViewGroup) null));
        AlertDialog create = builder.create();
        this.dialogProgress = create;
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialogProgress.show();
    }

    public void hideProgressBar() {
        AlertDialog alertDialog = this.dialogProgress;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.dialogProgress.dismiss();
        }
    }
}
