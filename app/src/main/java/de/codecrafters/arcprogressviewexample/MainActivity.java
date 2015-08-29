package de.codecrafters.arcprogressviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import de.codecrafters.arcprogressview.ArcProgressView;

public class MainActivity extends AppCompatActivity {

    private float progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArcProgressView progressView = (ArcProgressView) findViewById(R.id.progress_view);
        progressView.setProgressAnimationDuration(500);
        progressView.setProgress(progress);
//        progressView.setAnimationInterpolator(new OvershootInterpolator());

        final Button updateProgressButton = (Button) findViewById(R.id.update_progress_button);
        updateProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress += 0.1f; //1.0f / 60.0f;
                progressView.setProgress(progress);
            }
        });
    }

}
