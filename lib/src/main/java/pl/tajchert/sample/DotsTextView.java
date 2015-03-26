package pl.tajchert.sample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.tajchert.waitingdots.R;


public class DotsTextView extends LinearLayout {
    private TextView dotOne;
    private TextView dotTwo;
    private TextView dotThree;

    private int textSize;
    private int jumpHeight;
    private boolean autoPlay;
    private boolean isPlaying;
    private int textColor;
    private int period;
    private long startTime;

    private boolean lockDotOne;
    private boolean lockDotTwo;
    private boolean lockDotThree;


    public DotsTextView(Context context) {
        super(context);
        init(context, null);
    }

    public DotsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DotsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        if(attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.WaitingDots);
            textColor = typedArray.getColor(R.styleable.WaitingDots_android_textColor, Color.GRAY);
            period = typedArray.getInt(R.styleable.WaitingDots_period, 175);
            textSize = typedArray.getDimensionPixelSize(R.styleable.WaitingDots_android_textSize, 14);
            jumpHeight = typedArray.getInt(R.styleable.WaitingDots_jumpHeight, (textSize / 3));
            autoPlay = typedArray.getBoolean(R.styleable.WaitingDots_autoplay, true);
            typedArray.recycle();
        }
        resetPosition();
        inflate(getContext(), R.layout.dots_text_view, this);
        dotOne = (TextView) findViewById(R.id.dot1);
        dotTwo = (TextView) findViewById(R.id.dot2);
        dotThree = (TextView) findViewById(R.id.dot3);

        if(autoPlay){
            this.setWillNotDraw(false);
        }
        isPlaying = autoPlay;

        updateStyle();
    }

    private void updateStyle(){
        dotOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        dotTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        dotThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        dotOne.setTextColor(textColor);
        dotTwo.setTextColor(textColor);
        dotThree.setTextColor(textColor);
    }

    public void resetPosition() {
        startTime = System.currentTimeMillis() + period;
    }

    public void start() {
        isPlaying = true;
        lockDotOne = false;
        lockDotTwo = false;
        lockDotThree = false;
        resetPosition();
        this.setWillNotDraw(false);
    }

    public void stop() {
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setDotsColor(int dotsColor) {
        textColor = dotsColor;
        updateStyle();
    }

    public void setDotsSize(int dotsSize) {
        textSize = dotsSize;
        updateStyle();
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float time = (float)(System.currentTimeMillis() - startTime) / period;

        if(isPlaying) {
            for (int i = 3; i >= 0; i--) {
                float y = (float) -(jumpHeight * Math.max(0, Math.sin(time + i / 1.5f)));
                switch (i) {
                    case 2:
                        dotOne.setTranslationY(y);
                        break;
                    case 1:
                        dotTwo.setTranslationY(y);
                        break;
                    case 0:
                        dotThree.setTranslationY(y);
                        break;
                }
            }
        } else {
            for (int i = 3; i >= 0; i--) {
                float y = (float) -(jumpHeight * Math.max(0, Math.sin(time + i / 1.5f)));
                switch (i) {
                    case 2:
                        if(y==0 || lockDotOne) {
                            lockDotOne = true;
                            dotOne.setTranslationY(0);
                        } else {
                            dotOne.setTranslationY(y);
                        }
                        break;
                    case 1:
                        if(y==0 || lockDotTwo) {
                            lockDotTwo = true;
                            dotOne.setTranslationY(0);
                        } else {
                            dotTwo.setTranslationY(y);
                        }
                        break;
                    case 0:
                        if(y==0 || lockDotThree) {
                            lockDotThree = true;
                            dotOne.setTranslationY(0);
                        } else {
                            dotThree.setTranslationY(y);
                        }
                        break;
                }
            }
            if(lockDotOne && lockDotTwo && lockDotThree){
                //all are in bottom position
                this.setWillNotDraw(true);
            }
        }
        invalidate();
    }
}
