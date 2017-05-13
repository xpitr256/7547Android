package com.example.tallerdyp2.client.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tallerdyp2.client.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Sebastian on 20/3/2017.
 */

public class ElementViewUtils {

    public static void setText(View rootView, int textContentId, String content)
    {
        TextView textContent = (TextView) rootView.findViewById(textContentId);
        textContent.setText(content);
    }

    public static void setImageFromURL(View rootView, int textContentId, final String content, final Context context)
    {
        final ImageView imageContent = (ImageView) rootView.findViewById(textContentId);
        imageContent.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(content != null && !content.isEmpty())
            Picasso.with(context)
                    .load(content)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                    .placeholder(R.drawable.ic_image_load_place_holder)
                    .config(Bitmap.Config.RGB_565)
                    .into(imageContent);
        else ElementViewUtils.setDefaultImage(imageContent, context);
    }

    public static void setImage(View rootView, int textContentId, final int content, final Context context)
    {
        final ImageView imageContent = (ImageView) rootView.findViewById(textContentId);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                Picasso.with(context).load(R.drawable.no_photo).into(imageContent);
            }
        });
        builder.build().load(content).into(imageContent);

    }

    private static void setDefaultImage(ImageView imageContent , final Context context) {
        Picasso.with(context).load(R.drawable.no_photo).into(imageContent);
    }

}
