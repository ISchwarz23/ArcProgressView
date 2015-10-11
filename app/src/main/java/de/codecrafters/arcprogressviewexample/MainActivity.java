package de.codecrafters.arcprogressviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.arcprogressview.ArcProgressView;
import de.codecrafters.arcprogressviewexample.utils.InterpolatorSpinnerAdapter;
import de.codecrafters.arcprogressviewexample.utils.OnSeekBarChangeListenerAdapter;

/**
 * The main activity of the ArcProgressView example, that provides some possibilities to
 * manipulate the ArcProgressView.
 *
 * @author ISchwarz
 */
public class MainActivity extends AppCompatActivity {

    private float progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArcProgressView progressView = (ArcProgressView) findViewById(R.id.progress_view);
        progressView.setProgressAnimationDuration(500);
        progressView.setProgress(progress);

        final SeekBar startAngleSeekBar = (SeekBar) findViewById(R.id.start_angle_seekbar);
        startAngleSeekBar.setProgress(progressView.getStartAngle());
        startAngleSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                progressView.setStartAngle(progress);
            }
        });

        final SeekBar sweepAngleSeekBar = (SeekBar) findViewById(R.id.sweep_angle_seekbar);
        sweepAngleSeekBar.setProgress(progressView.getRange());
        sweepAngleSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                progressView.setRange(progress);
            }
        });

        final CheckBox animationCheckBox = (CheckBox) findViewById(R.id.animation_checkbox);
        animationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                progressView.setProgressAnimationEnabled(isChecked);
            }
        });

        final List<Interpolator> interpolators = new ArrayList<>();
        interpolators.add(new LinearInterpolator());
        interpolators.add(new AccelerateInterpolator());
        interpolators.add(new DecelerateInterpolator());
        interpolators.add(new AccelerateDecelerateInterpolator());
        interpolators.add(new AnticipateInterpolator());
        interpolators.add(new OvershootInterpolator());
        interpolators.add(new AnticipateOvershootInterpolator());
        interpolators.add(new BounceInterpolator());
        final Spinner interpolatorSpinner = (Spinner) findViewById(R.id.interpolator_spinner);
        interpolatorSpinner.setAdapter(new InterpolatorSpinnerAdapter(this, interpolators));
        interpolatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                Interpolator interpolator = interpolators.get(position);
                progressView.setProgressAnimationInterpolator(interpolator);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });

        final Button updateProgressButton = (Button) findViewById(R.id.update_progress_button);
        updateProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress += 0.1f;
                progressView.setProgress(progress);
            }
        });

        final Button clearProgressButton = (Button) findViewById(R.id.clear_progress_button);
        clearProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress = 0.0f;
                progressView.setProgress(progress);
            }
        });
    }

}
