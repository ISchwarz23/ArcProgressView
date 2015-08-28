package de.codecrafters.arcprogressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * Created by Ingo on 23.08.2015.
 */
public class ArcProgressView extends View {

    private int progressColor, pathColor = 0x88888888;
    private int pathWidth = 4, progressWidth = 20;
    private float progress = 0.25f;
    private int gravity = Gravity.CENTER;

    public ArcProgressView(final Context context) {
        this(context, null);
    }

    public ArcProgressView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgressView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgressColor(int color) {
        this.progressColor = color;
        invalidate();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();
        int spacing = Math.max(pathWidth, progressWidth) / 2;
        float radius = width / 2.0f - spacing;
        int minLength = (width < height) ? width : height;

        int left = (width > minLength) ?  spacing + (width - minLength) / 2 : spacing;
        int top = (height > minLength) ? spacing + (height - minLength) / 2 : spacing;
        int right = (width > minLength) ? width - left : minLength - left;
        int bottom = (height > minLength) ? height - top : minLength - top;

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setTextAlign(Paint.Align.CENTER);

        RectF area = new RectF(left, top, right, bottom);

        // Draw path
        p.setColor(pathColor);
        p.setStrokeWidth(pathWidth);
        canvas.drawArc(area, 135, 270, false, p);

        // Draw actual value
        p.setColor(progressColor);
        p.setStrokeWidth(progressWidth);
        canvas.drawArc(area, 135, progress *270, false, p);

        p.setStrokeWidth(0.0f);
        p.setStyle(Paint.Style.FILL);
        float cx = width/2.0f + radius * (float)Math.cos(Math.toRadians(135));
        float cy = height/2.0f + radius * (float)Math.sin(Math.toRadians(135));
        canvas.drawCircle(cx, cy, progressWidth / 2.0f, p);
        cx = width/2.0f + radius * (float)Math.cos(Math.toRadians(135 + 270*progress));
        cy = height/2.0f + radius * (float)Math.sin(Math.toRadians(135 + 270 * progress));
        canvas.drawCircle(cx, cy, progressWidth / 2.0f, p);
    }

}
