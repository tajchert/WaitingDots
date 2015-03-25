package pl.tajchert.waitingdots;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Marcin on 2015-03-25.
 */
public class DotsDrawable extends Drawable {
    private static final float JUMP_PERIOD = 400.0f;
    private final long startTime;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int color;

    public DotsDrawable(int color) {
        this.color = color;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(color);

        float time = (System.currentTimeMillis()-startTime)/JUMP_PERIOD;

        Rect bounds = getBounds();
        float circleSize = bounds.width() / 4;
        for (int i = 0; i < 3; i++) {
            float x = i * bounds.width() / 3.0f + circleSize / 2;
            float y = (float) (bounds.height() - circleSize / 2 - (bounds.height()-circleSize)*Math.max(0, Math.sin(time+i/3.0f)));
            canvas.drawCircle(x, y, circleSize / 2, paint);
        }
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
