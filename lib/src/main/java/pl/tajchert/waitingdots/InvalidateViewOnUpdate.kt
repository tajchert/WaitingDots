package pl.tajchert.waitingdots

import android.animation.ValueAnimator
import android.view.View
import java.lang.ref.WeakReference

class InvalidateViewOnUpdate(view: View) : ValueAnimator.AnimatorUpdateListener {
  private val viewRef: WeakReference<View> = WeakReference(view)

  override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
    val view = viewRef.get() ?: return

    view.invalidate()
  }
}
