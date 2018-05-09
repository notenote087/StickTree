package com.example.admin.sticktree.AdapterImage;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by admin on 10/5/2561.
 */

public class ImageController {
    Context context;
    ImageView imgMain;

    public ImageController(Context context, ImageView imgMain) {
        this.context = context;
        this.imgMain = imgMain;
    }

    /*void setImgMain(Uri path) {
        Glide
                .with(context)
                .load(path)
                .into(imgMain);
    }*/
}
