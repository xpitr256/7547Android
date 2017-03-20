package com.example.tallerdyp2.client.utils;

import android.content.Context;
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

    public static void setImage(View rootView, int textContentId, String content, Context context)
    {
        ImageView imageContent = (ImageView) rootView.findViewById(textContentId);
        Picasso.with(context).load("http://i.imgur.com/2IiKOVe.jpg").into(imageContent);
    }

}
