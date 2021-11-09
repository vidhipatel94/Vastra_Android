package com.esolution.vastrabasic.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.esolution.vastrabasic.R;
import com.esolution.vastrabasic.apis.RestUtils;

public class ImageUtils {
    public static void loadImageUrl(ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(null);
            return;
        }
        url = RestUtils.BASE_URL + url;
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.image_placeholder))
                .into(imageView);
    }
}
