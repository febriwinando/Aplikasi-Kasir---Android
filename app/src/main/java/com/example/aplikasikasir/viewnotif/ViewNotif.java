package com.example.aplikasikasir.viewnotif;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplikasikasir.R;

public class ViewNotif {


    public void viewSukses(Activity context) {
        Dialog dialogSukses = new Dialog(context, R.style.DialogStyle);
        dialogSukses.setContentView(R.layout.view_sukses);
        ImageView ivTutupDialog = dialogSukses.findViewById(R.id.ivTutup);
        ImageView emoticon = dialogSukses.findViewById(R.id.emoticon);

        ivTutupDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSukses.dismiss();
//                context.finish();

            }
        });

//        Glide.with(context).load(R.drawable.sample_gif).into(emoticon);
        dialogSukses.show();
    }

    public void viewError(Activity context) {
        Dialog dialogSukses = new Dialog(context, R.style.DialogStyle);
        dialogSukses.setContentView(R.layout.view_error);
        ImageView tvTutupDialog = dialogSukses.findViewById(R.id.tvTutupDialog);
        ImageView emoticon = dialogSukses.findViewById(R.id.emoticon);

        tvTutupDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSukses.dismiss();
            }
        });

//        Glide.with(context).load(R.drawablele.error).into(emoticon);
        dialogSukses.show();
    }

    public void viewGagal(Activity context, String pesan) {
        Dialog dialogSukses = new Dialog(context, R.style.DialogStyle);
        dialogSukses.setContentView(R.layout.view_error);
        ImageView tvTutupDialog = dialogSukses.findViewById(R.id.tvTutupDialog);
        TextView tvPesanGagal = dialogSukses.findViewById(R.id.tvPesanGagal);
        ImageView emoticon = dialogSukses.findViewById(R.id.emoticon);

        tvPesanGagal.setText(pesan);
        tvTutupDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSukses.dismiss();
            }
        });

//        Glide.with(context).load(R.drawable.gagal).into(emoticon);
        dialogSukses.show();
    }
}
