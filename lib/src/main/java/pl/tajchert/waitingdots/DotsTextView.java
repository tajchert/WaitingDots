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
    private int textWidth;
    private int textHeight;

    private static final int JUMP_PERIOD = 175;
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
        float time = (float)(System.currentTimeMillis()-startTime)/JUMP_PERIOD;

        for (int i = 3; i >= 0; i--) {
            float y = (float) (this.textHeight / 2 - (this.textHeight - 8)*Math.max(0, Math.sin(time+i/1.5f)));
            switch (i){
                case 2:
                    dotOne.setTranslationY(-y);
                    break;
                case 1:
                    dotTwo.setTranslationY(-y);
                    break;
                case 0:
                    dotThree.setTranslationY(-y);
                    break;
            }
        }
        invalidate();
    }
}
