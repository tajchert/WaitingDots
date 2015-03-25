package pl.tajchert.waitingdots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Marcin on 2015-03-25.
 */
public class DotsView extends View {
    private static final float JUMP_PERIOD = 400.0f;
    //private final long startTime;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int color;

    public DotsView(Context context) {
        super(context);
    }

    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void getAttr(Context context, AttributeSet attrs) {

    }


    public void setColor(int color) {
        this.color = color;
        //startTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(color);

        float time = (System.currentTimeMillis() - 0)/JUMP_PERIOD;

        Rect bounds = new Rect();
        float circleSize = bounds.width() / 4;
        for (int i = 0; i < 3; i++) {
            float x = i * bounds.width() / 3.0f + circleSize / 2;
            float y = (float) (bounds.height() - circleSize / 2 - (bounds.height()-circleSize)*Math.max(0, Math.sin(time+i/3.0f)));
            canvas.drawCircle(x, y, circleSize / 2, paint);
        }
        //invalidateSelf();
    }
}
