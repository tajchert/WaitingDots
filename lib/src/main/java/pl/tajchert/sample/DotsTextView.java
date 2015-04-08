package pl.tajchert.sample;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import pl.tajchert.waitingdots.R;


public class DotsTextView extends TextView {
    private JumpingSpan dotOne;
    private JumpingSpan dotTwo;
    private JumpingSpan dotThree;

    private int showSpeed = 700;

    private int jumpHeight;
    private boolean autoPlay;
    private boolean isPlaying;
    private boolean isHide;
    private int period;
    private long startTime;

    private boolean lockDotOne;
    private boolean lockDotTwo;
    private boolean lockDotThree;

    private Handler handler;


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
        handler = new Handler(Looper.getMainLooper());

        if(attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.WaitingDots);
            period = typedArray.getInt(R.styleable.WaitingDots_period, 175);
            jumpHeight = typedArray.getInt(R.styleable.WaitingDots_jumpHeight, (int)(getTextSize() / 4));
            autoPlay = typedArray.getBoolean(R.styleable.WaitingDots_autoplay, true);
            typedArray.recycle();
        }
        resetPosition();
        dotOne = new JumpingSpan();
        dotTwo = new JumpingSpan();
        dotThree = new JumpingSpan();

        SpannableString spannable = new SpannableString("...");
        spannable.setSpan(dotOne, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannable.setSpan(dotTwo, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannable.setSpan(dotThree, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        setText(spannable);

        if(autoPlay){
            this.setWillNotDraw(false);
        }
        isPlaying = autoPlay;
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

    public void hide() {
        float textWidth = getPaint().measureText(".", 0, 1);
        ObjectAnimator dotThreeMoveRightToLeft = ObjectAnimator.ofFloat(dotThree, "translationX", 0, -(textWidth * 2));
        dotThreeMoveRightToLeft.setDuration(showSpeed);

        dotThreeMoveRightToLeft.start();

        ObjectAnimator dotTwoMoveRightToLeft = ObjectAnimator.ofFloat(dotTwo, "translationX", 0, -(textWidth));
        dotTwoMoveRightToLeft.setDuration(showSpeed);

        dotTwoMoveRightToLeft.start();
        isHide = true;
    }

    public void show() {
        float textWidth = getPaint().measureText(".", 0, 1);
        ObjectAnimator dotThreeMoveRightToLeft = ObjectAnimator.ofFloat(dotThree, "translationX", -(textWidth * 2), 0);
        dotThreeMoveRightToLeft.setDuration(showSpeed);

        dotThreeMoveRightToLeft.start();

        ObjectAnimator dotTwoMoveRightToLeft = ObjectAnimator.ofFloat(dotTwo, "translationX", -(textWidth), 0);
        dotTwoMoveRightToLeft.setDuration(showSpeed);

        dotTwoMoveRightToLeft.start();
        isHide = false;
    }

    public void showAndPlay() {
        show();

        final Runnable r = new Runnable() {
            public void run() {
                start();
            }
        };
        handler.postDelayed(r, showSpeed);
    }

    public void hideAndStop() {
        hide();

        final Runnable r = new Runnable() {
            public void run() {
                stop();
            }
        };
        handler.postDelayed(r, showSpeed);
    }

    public boolean isHide() {
        return isHide;
    }

    public boolean isPlaying() {
        return isPlaying;
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
                            dotTwo.setTranslationY(0);
                        } else {
                            dotTwo.setTranslationY(y);
                        }
                        break;
                    case 0:
                        if(y==0 || lockDotThree) {
                            lockDotThree = true;
                            dotThree.setTranslationY(0);
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
