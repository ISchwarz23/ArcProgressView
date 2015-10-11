package de.codecrafters.arcprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * A view that will display your progress as an Arc.
 *
 * @author ISchwarz
 */
public class ArcProgressView extends View {

    private static final int DEFAULT_RANGE = 270;
    private static final int DEFAULT_RANGE_PATH_COLOR = 0xFFBBBBBB;
    private static final int DEFAULT_RANGE_PATH_WIDTH = 4;
    private static final float DEFAULT_PROGRESS = 0.25f;
    private static final int DEFAULT_PROGRESS_PATH_COLOR = 0xFFFF0000;
    private static final int DEFAULT_PROGRESS_PATH_WIDTH = 20;
    private static final int DEFAULT_START_ANGLE = 135;
    private static final int DEFAULT_ANIMATION_DURATION = 250;

    private final Paint paint = new Paint();

    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private long animationDuration = DEFAULT_ANIMATION_DURATION;
    private boolean animateProgress = false;
    private Runnable animator;

    private int rangePathColor, progressPathColor;
    private int rangePathWidth, progressPathWidth;
    private int range;
    private int startAngle;
    private float progress;


    /**
     * Creates a new ArcProgressView with the given {@link Context}.
     *
     * @param context
     *         The {@link Context} for which this ArcProgressView shall be created.
     */
    public ArcProgressView(final Context context) {
        this(context, null);
    }

    /**
     * Creates a new ArcProgressView with the given {@link Context} and {@link AttributeSet}.
     *
     * @param context
     *         The {@link Context} for which this ArcProgressView shall be created.
     * @param attributes
     *         The attributes that shall be used in this ArcProgressView.
     */
    public ArcProgressView(final Context context, final AttributeSet attributes) {
        this(context, attributes, 0);
    }

    /**
     * Creates a new ArcProgressView with the given values.
     *
     * @param context
     *         The {@link Context} for which this ArcProgressView shall be created.
     * @param attributes
     *         The attributes that shall be used in this ArcProgressView.
     * @param defStyleAttr
     *         The style attribute that shall be used int this ArcProgressView.
     */
    public ArcProgressView(final Context context, final AttributeSet attributes, final int defStyleAttr) {
        super(context, attributes, defStyleAttr);
        initializeParams(context, attributes);
        initializePaint();
    }

    /**
     * Gives the current range of this {@link ArcProgressView}.
     *
     * @return The current range of this {@link ArcProgressView}.
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the current range of this {@link ArcProgressView}.
     *
     * @param range
     *         The current range of this {@link ArcProgressView}.
     */
    public void setRange(int range) {
        this.range = range;
        invalidate();
    }

    /**
     * Gives the current start angle of this {@link ArcProgressView}.
     *
     * @return The current start angle of this {@link ArcProgressView}.
     */
    public int getStartAngle() {
        return startAngle;
    }

    /**
     * Sets the current start angle of this {@link ArcProgressView}.
     *
     * @param startAngle
     *         The current start angle of this {@link ArcProgressView}.
     */
    public void setStartAngle(final int startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    /**
     * Gives the current color of the range path.
     *
     * @return The current color of the range path.
     */
    public int getRangePathColor() {
        return rangePathColor;
    }

    /**
     * Sets the color of the range path.
     *
     * @param rangePathColor
     *         The color that shall be set to the range path.
     */
    public void setRangePathColor(final int rangePathColor) {
        this.rangePathColor = rangePathColor;
        invalidate();
    }


    /**
     * Sets the path width of the progress path.
     *
     * @return The path width of the progress path.
     */
    public int getProgressPathWidth() {
        return progressPathWidth;
    }

    /**
     * Sets the path width of the progress path.
     *
     * @param progressPathWidth
     *         The path width that shall be set to the progress path.
     */
    public void setProgressPathWidth(final int progressPathWidth) {
        this.progressPathWidth = progressPathWidth;
        invalidate();
    }

    /**
     * Gives the width of the range path.
     *
     * @return The width of the range path.
     */
    public int getRangePathWidth() {
        return rangePathWidth;
    }

    /**
     * Sets the width of the range path.
     *
     * @param rangePathWidth
     *         The width that shall be set to the range path.
     */
    public void setRangePathWidth(final int rangePathWidth) {
        this.rangePathWidth = rangePathWidth;
        invalidate();
    }

    /**
     * Gives the color of the progress path.
     *
     * @return The color of the progress path.
     */
    public int getProgressPathColor() {
        return progressPathColor;
    }

    /**
     * Sets the color of the progress path.
     *
     * @param color
     *         The color that shall be set to the progress path
     */
    public void setProgressPathColor(final int color) {
        this.progressPathColor = color;
        invalidate();
    }

    /**
     * Gives the current progress.
     *
     * @return The current progress.
     */
    public float getProgress() {
        return progress;
    }

    /**
     * Sets the progress to the given value.
     *
     * @param progress
     *         The progress that shall be set.
     */
    public void setProgress(final float progress) {
        float progressToSet = progress;

        if (progressToSet < 0) {
            progressToSet = 0;
        } else if (progressToSet > 1) {
            progressToSet = 1;
        }

        if (animateProgress) {
            setProgressAnimated(progressToSet);
        } else {
            setProgressNotAnimated(progressToSet);
        }
    }

    /**
     * Indicates whether or not the progress animation is enabled.
     *
     * @return Whether or not the progress animation is enabled.
     */
    public boolean isProgressAnimationEnabled() {
        return animateProgress;
    }

    /**
     * Enables or disables the progress animation.
     *
     * @param animated
     *         Whether or not the progress shall be animated.
     */
    public void setProgressAnimationEnabled(final boolean animated) {
        this.animateProgress = animated;
    }

    /**
     * Gives the duration of a progress animations.
     *
     * @return The duration of progress animations.
     */
    public long getProgressAnimationDuration() {
        return animationDuration;
    }

    /**
     * Sets the duration of a progress animations.
     *
     * @param animationDuration
     *         The duration that shall be used for progress animations.
     */
    public void setProgressAnimationDuration(final long animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * Gives the {@link Interpolator} that is be used for progress animations.
     *
     * @return The {@link Interpolator} that is used for animating progress animations.
     */
    public Interpolator getProgressAnimationInterpolator() {
        return interpolator;
    }

    /**
     * Sets the {@link Interpolator} that shall be used for progress animations.
     *
     * @param interpolator
     *         The {@link Interpolator} that shall be used for animating progress animations.
     */
    public void setProgressAnimationInterpolator(final Interpolator interpolator) {
        this.interpolator = interpolator;
    }


    private void initializeParams(Context context, AttributeSet attributes) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(attributes, R.styleable.ArcProgressView);
        range = styledAttributes.getInt(R.styleable.ArcProgressView_range, DEFAULT_RANGE);
        rangePathColor = styledAttributes.getInt(R.styleable.ArcProgressView_rangePathColor, DEFAULT_RANGE_PATH_COLOR);
        rangePathWidth = styledAttributes.getInt(R.styleable.ArcProgressView_rangePathWidth, DEFAULT_RANGE_PATH_WIDTH);
        progress = styledAttributes.getFloat(R.styleable.ArcProgressView_progress, DEFAULT_PROGRESS);
        progressPathColor = styledAttributes.getInt(R.styleable.ArcProgressView_progressPathColor, DEFAULT_PROGRESS_PATH_COLOR);
        progressPathWidth = styledAttributes.getInt(R.styleable.ArcProgressView_progressPathWidth, DEFAULT_PROGRESS_PATH_WIDTH);
        startAngle = styledAttributes.getInt(R.styleable.ArcProgressView_startAngle, DEFAULT_START_ANGLE);
        styledAttributes.recycle();
    }

    private void initializePaint() {
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    private void setProgressNotAnimated(final float progress) {
        this.progress = progress;
        invalidate();
    }

    private void setProgressAnimated(final float progress) {
        final float startProgress = this.progress;
        final float progressDelta = progress - this.progress;
        removeCallbacks(animator);

        animator = new Runnable() {
            private float animationProgress = 0.0f;

            @Override
            public void run() {
                if (animationProgress > 1) {
                    animationProgress = 1;
                }
                float newProgress = startProgress + interpolator.getInterpolation(animationProgress) * progressDelta;
                setProgressNotAnimated(newProgress);
                if (animationProgress < 1) {
                    animationProgress += 1.0f / (animationDuration / 16.0f);
                    postDelayed(this, 16);
                }
            }
        };
        post(animator);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();
        int spacing = Math.max(rangePathWidth, progressPathWidth) / 2;
        int minLength = Math.min(width, height);
        float radius = minLength / 2.0f - spacing;

        int left = (width > minLength) ? spacing + (width - minLength) / 2 : spacing;
        int top = (height > minLength) ? spacing + (height - minLength) / 2 : spacing;
        int right = (width > minLength) ? width - left : minLength - left;
        int bottom = (height > minLength) ? height - top : minLength - top;
        RectF area = new RectF(left, top, right, bottom);

        // draw range path
        paint.setColor(rangePathColor);
        paint.setStrokeWidth(rangePathWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(area, startAngle, range, false, paint);

        // draw start end of range path
        paint.setStrokeWidth(0.0f);
        paint.setStyle(Paint.Style.FILL);
        float cx = width / 2.0f + radius * (float) Math.cos(Math.toRadians(startAngle));
        float cy = height / 2.0f + radius * (float) Math.sin(Math.toRadians(startAngle));
        canvas.drawCircle(cx, cy, rangePathWidth / 2.0f, paint);
        cx = width / 2.0f + radius * (float) Math.cos(Math.toRadians(startAngle + range));
        cy = height / 2.0f + radius * (float) Math.sin(Math.toRadians(startAngle + range));
        canvas.drawCircle(cx, cy, rangePathWidth / 2.0f, paint);

        // draw progress path
        paint.setColor(progressPathColor);
        paint.setStrokeWidth(progressPathWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(area, startAngle, progress * range, false, paint);

        paint.setStrokeWidth(0.0f);
        paint.setStyle(Paint.Style.FILL);
        cx = width / 2.0f + radius * (float) Math.cos(Math.toRadians(startAngle));
        cy = height / 2.0f + radius * (float) Math.sin(Math.toRadians(startAngle));
        canvas.drawCircle(cx, cy, progressPathWidth / 2.0f, paint);
        cx = width / 2.0f + radius * (float) Math.cos(Math.toRadians(startAngle + range * progress));
        cy = height / 2.0f + radius * (float) Math.sin(Math.toRadians(startAngle + range * progress));
        canvas.drawCircle(cx, cy, progressPathWidth / 2.0f, paint);
    }
}
