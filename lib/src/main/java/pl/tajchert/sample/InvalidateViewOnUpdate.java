package pl.tajchert.sample;

import android.animation.ValueAnimator;
import android.view.View;
import java.lang.ref.WeakReference;

public class InvalidateViewOnUpdate implements ValueAnimator.AnimatorUpdateListener {
    private final WeakReference<View> viewRef;

    public InvalidateViewOnUpdate(View view) {
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        final View view = viewRef.get();

        if (view == null) {
            return;
        }

        view.invalidate();
    }
}
