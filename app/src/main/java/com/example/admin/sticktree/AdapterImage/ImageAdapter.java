package com.example.admin.sticktree.AdapterImage;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.sticktree.R;
import com.example.admin.sticktree.online_add;

import java.util.ArrayList;

/**
 * Created by admin on 10/5/2561.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    Context context;
    ArrayList<Uri> imagePaths;
    //ImageController imageController;
    private String TAG = ImageAdapter.class.getSimpleName();

    public ImageAdapter(Context context, ArrayList<Uri> imagePaths) {
        this.context = context;
      //  this.imageController = imageController;
        this.imagePaths = imagePaths;
    }

  /*  public void changePath(ArrayList<Uri> imagePaths) {
        this.imagePaths = imagePaths;
     //   imageController.setImgMain(imagePaths.get(0));
        Log.d(TAG,"Faillll changePath  : " + imagePaths.get(0) );
        notifyDataSetChanged();
    }*/

    public void getPath(ArrayList<Uri> imagePaths) {
        this.imagePaths = imagePaths;
        notifyDataSetChanged();
    }





    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_for_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        final Uri imagePath = imagePaths.get(position);
        Log.d(TAG,"Faillll  Uri imagePath   : " + imagePaths.get(position) );
        Glide
                .with(context)
                .load(imagePath)

                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"Faillll  Uri size  : " + imagePaths.size() );
       /* online_add  online_add = new online_add();
        online_add.setCountSelectPhoto(imagePaths.size());*/
        return imagePaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_item);
        }
    }
}
