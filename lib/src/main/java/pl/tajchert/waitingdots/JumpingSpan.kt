package pl.tajchert.waitingdots

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan

class JumpingSpan : ReplacementSpan() {

  private var translationX = 0f
  private var translationY = 0f

  override fun getSize(
    paint: Paint,
    text: CharSequence,
    start: Int,
    end: Int,
    fontMetricsInt: FontMetricsInt?
  ): Int {
    return paint.measureText(text, start, end)
        .toInt()
  }

  override fun draw(
    canvas: Canvas,
    text: CharSequence,
    start: Int,
    end: Int,
    x: Float,
    top: Int,
    y: Int,
    bottom: Int,
    paint: Paint
  ) {
    canvas.drawText(text, start, end, x + translationX, y + translationY, paint)
  }

  fun setTranslationX(translationX: Float) {
    this.translationX = translationX
  }

  fun setTranslationY(translationY: Float) {
    this.translationY = translationY
  }
}
