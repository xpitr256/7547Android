package com.example.tallerdyp2.client.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static void setImage(View rootView, int textContentId,String content,final Context context)
    {
        final ImageView imageContent = (ImageView) rootView.findViewById(textContentId);
        if(content != null && !content.isEmpty()){
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    Picasso.with(context).load("http://i.imgur.com/2IiKOVe.jpg").into(imageContent);
                }
            });
            builder.build().load(content).into(imageContent);
        }
        else ElementViewUtils.setDefaultImage(imageContent, context);
    }

    private static void setDefaultImage(ImageView imageContent , final Context context) {
        Picasso.with(context).load("http://i.imgur.com/2IiKOVe.jpg").into(imageContent);
    }

}
