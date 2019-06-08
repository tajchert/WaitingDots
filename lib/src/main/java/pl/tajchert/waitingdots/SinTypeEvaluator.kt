package pl.tajchert.waitingdots

import android.animation.TypeEvaluator

class SinTypeEvaluator : TypeEvaluator<Number> {
  override fun evaluate(
    fraction: Float,
    from: Number,
    to: Number
  ): Number {
    return Math.max(0.0, Math.sin(fraction.toDouble() * Math.PI * 2.0)) * (to.toFloat() - from.toFloat())
  }
}
