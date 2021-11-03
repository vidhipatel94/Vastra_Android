package com.esolution.vastrabasic.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.esolution.vastrabasic.R;

public class CircleView extends View {

    private int DEFAULT_COLOR = ContextCompat.getColor(getContext(), R.color.primary);

    private Paint circlePaint;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(DEFAULT_COLOR);
    }

    public void setColor(String hex) {
        if (TextUtils.isEmpty(hex)) return;
        try {
            int color = Color.parseColor(hex);
            circlePaint.setColor(color);
            invalidate();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float radius = Math.min(width, height) / 2f;
        canvas.drawCircle(width / 2f, height / 2f, radius, circlePaint);
    }
}
