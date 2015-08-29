package de.codecrafters.arcprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by Ingo on 23.08.2015.
 */
public class ArcProgressView extends View {

    private static final int DEFAULT_RANGE = 270;
    private static final int DEFAULT_RANGE_PATH_COLOR= 0xFFBBBBBB;
    private static final int DEFAULT_RANGE_PATH_WIDTH = 4;
    private static final float DEFAULT_PROGRESS = 0.25f;
    private static final int DEFAULT_PROGRESS_PATH_COLOR= 0xFFFF0000;
    private static final int DEFAULT_PROGRESS_PATH_WIDTH = 20;
    private static final int DEFAULT_START_ANGLE = 135;

    private final Paint paint = new Paint();

    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private boolean animateProgress = true;
    private long animationDuration = 250;
    private Runnable animator;

    private int rangePathColor, progressPathColor;
    private int rangePathWidth, progressPathWidth;
    private int range;
    private int startAngle;
    private float progress;


    public ArcProgressView(final Context context) {
        this(context, null);
    }

    public ArcProgressView(final Context context, final AttributeSet attributes) {
        this(context, attributes, 0);
    }

    public ArcProgressView(final Context context, final AttributeSet attributes, final int defStyleAttr) {
        super(context, attributes, defStyleAttr);
        initializeParams(context, attributes);
        initializePaint();
    }

    private void initializePaint(){
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    private void initializeParams(Context context, AttributeSet attributes) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(attributes, R.styleable.ArcProgressView);
        range = styledAttributes.getInt(R.styleable.ArcProgressView_range, DEFAULT_RANGE);
        rangePathColor = styledAttributes.getInt(R.styleable.ArcProgressView_rangePathColor, DEFAULT_RANGE_PATH_COLOR);
        rangePathWidth = styledAttributes.getInt(R.styleable.ArcProgressView_rangePathWidth, DEFAULT_RANGE_PATH_WIDTH);
        progress = styledAttributes.getFloat(R.styleable.ArcProgressView_progress, DEFAULT_PROGRESS);
        progressPathColor = styledAttributes.getInt(R.styleable.ArcProgressView_progressPathColor, DEFAULT_PROGRESS_PATH_COLOR);
        progressPathWidth = styledAttributes.getInt(R.styleable.ArcProgressView_progressPathWidth, DEFAULT_PROGRESS_PATH_WIDTH);
        startAngle = styledAttributes.getInt(R.styleable.ArcProgressView_startAngel, DEFAULT_START_ANGLE);
        styledAttributes.recycle();
    }

    public void setProgressPathColor(int color) {
        this.progressPathColor = color;
        invalidate();
    }

    public void setProgress(float progress) {
        if(animateProgress) {
            setProgressAnimated(progress);
        } else {
            setProgressNotAnimated(progress);
        }
    }

    public void setProgressAnimationEnabled(boolean animated) {
        this.animateProgress = animated;
    }

    public void setProgressAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    private void setProgressAnimated(final float progress) {
        final float startProgress = this.progress;
        final float progressDelta = progress - this.progress;
        removeCallbacks(animator);

        animator = new Runnable() {
            private float animationProgress = 0.0f;
            @Override
            public void run() {
                float newProgress = startProgress + interpolator.getInterpolation(animationProgress) * progressDelta;
                setProgressNotAnimated(newProgress);
                if(animationProgress < 1) {
                    animationProgress += 1.0f / (animationDuration / 16.0f);
                    postDelayed(this, 16);
                }
            }
        };
        post(animator);
    }

    private void setProgressNotAnimated(final float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setAnimationInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();
        int spacing = Math.max(rangePathWidth, progressPathWidth) / 2;
        int minLength = Math.min(width, height);
        float radius = minLength / 2.0f - spacing;

        int left = (width > minLength) ?  spacing + (width - minLength) / 2 : spacing;
        int top = (height > minLength) ? spacing + (height - minLength) / 2 : spacing;
        int right = (width > minLength) ? width - left : minLength - left;
        int bottom = (height > minLength) ? height - top : minLength - top;
        RectF area = new RectF(left, top, right, bottom);

        // Draw path
        paint.setColor(rangePathColor);
        paint.setStrokeWidth(rangePathWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(area, startAngle, range, false, paint);

        paint.setStrokeWidth(0.0f);
        paint.setStyle(Paint.Style.FILL);
        float cx = width/2.0f + radius * (float)Math.cos(Math.toRadians(startAngle));
        float cy = height/2.0f + radius * (float)Math.sin(Math.toRadians(startAngle));
        canvas.drawCircle(cx, cy, rangePathWidth / 2.0f, paint);
        cx = width/2.0f + radius * (float)Math.cos(Math.toRadians(startAngle + range));
        cy = height/2.0f + radius * (float)Math.sin(Math.toRadians(startAngle + range));
        canvas.drawCircle(cx, cy, rangePathWidth / 2.0f, paint);

        // Draw progress
        paint.setColor(progressPathColor);
        paint.setStrokeWidth(progressPathWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(area, startAngle, progress *range, false, paint);

        paint.setStrokeWidth(0.0f);
        paint.setStyle(Paint.Style.FILL);
        cx = width/2.0f + radius * (float)Math.cos(Math.toRadians(startAngle));
        cy = height/2.0f + radius * (float)Math.sin(Math.toRadians(startAngle));
        canvas.drawCircle(cx, cy, progressPathWidth / 2.0f, paint);
        cx = width/2.0f + radius * (float)Math.cos(Math.toRadians(startAngle + range*progress));
        cy = height/2.0f + radius * (float)Math.sin(Math.toRadians(startAngle + range * progress));
        canvas.drawCircle(cx, cy, progressPathWidth / 2.0f, paint);
    }

}
