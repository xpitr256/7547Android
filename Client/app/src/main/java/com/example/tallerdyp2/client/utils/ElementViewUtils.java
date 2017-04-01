package com.example.tallerdyp2.client.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tallerdyp2.client.R;
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
        if(content != null && !content.isEmpty()){
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
        Picasso.with(context).load(R.drawable.image_error).into(imageContent);
    }

}
