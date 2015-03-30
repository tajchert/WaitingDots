package pl.tajchert.waitingdots.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import pl.tajchert.sample.DotsTextView;


public class MainActivity extends ActionBarActivity {
    DotsTextView dotsTextView;
    Button buttonPlay;
    Button buttonHide;
    Button buttonHideAndStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonHide = (Button) findViewById(R.id.buttonHide);
        buttonHideAndStop = (Button) findViewById(R.id.buttonHideAndStop);


        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dotsTextView.isPlaying()) {
                    dotsTextView.stop();
                } else {
                    dotsTextView.start();
                }
            }
        });

        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dotsTextView.isHide()) {
                    dotsTextView.show();
                } else {
                    dotsTextView.hide();
                }
            }
        });

        buttonHideAndStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dotsTextView.isHide()) {
                    dotsTextView.showAndPlay();
                } else {
                    dotsTextView.hideAndStop();
                }
            }
        });
    }
}
