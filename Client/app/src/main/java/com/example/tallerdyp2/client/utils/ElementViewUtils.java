package com.example.tallerdyp2.client.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sebastian on 20/3/2017.
 */

public class ElementViewUtils {

    public static void setText(View rootView, int textContentId, String content)
    {
        TextView textContent = (TextView) rootView.findViewById(textContentId);
        textContent.setText(content);
    }

}
