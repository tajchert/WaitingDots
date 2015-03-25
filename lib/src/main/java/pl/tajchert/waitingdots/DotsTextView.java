package pl.tajchert.waitingdots;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;


public class DotsTextView extends LinearLayout {
    private TextView dotOne;
    private TextView dotTwo;
    private TextView dotThree;
    private int directionSign = 1;
    private int textWidth;
    private int textHeight;

    private static final int JUMP_PERIOD = 150;
    private long startTime;

    public DotsTextView(Context context) {
        super(context);
        init();
    }

    public DotsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        startTime = System.currentTimeMillis();
        inflate(getContext(), R.layout.dots_text_view, this);
        this.dotOne = (TextView)findViewById(R.id.dot1);
        this.dotTwo = (TextView)findViewById(R.id.dot2);
        this.dotThree = (TextView)findViewById(R.id.dot3);

        this.textWidth = dotOne.getWidth();
        this.textHeight = dotOne.getHeight();
        this.setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //float time = (System.currentTimeMillis()-startTime)%JUMP_PERIOD;
        float time = (System.currentTimeMillis()-startTime)/JUMP_PERIOD;

        /*if(time == JUMP_PERIOD - 1) {
            if(directionSign == 1){
                directionSign = (-1);
            } else {
                directionSign = 1;
            }
        }*/

        for (int i = 3; i >= 0; i--) {
            float y = (float) (this.textHeight / 2 - (this.textHeight - 15)*Math.max(0, Math.sin(time+i/3.0f)));
            //int y = Math.round(time * directionSign);
            switch (i){
                case 0:
                    dotOne.setPadding(0, -Math.round(y), 0, 0);
                    break;
                case 1:
                    dotTwo.setPadding(0, -Math.round(y), 0, 0);
                    break;
                case 2:
                    dotThree.setPadding(0, -Math.round(y), 0, 0);
                    break;
            }
        }
        invalidate();
    }
}
