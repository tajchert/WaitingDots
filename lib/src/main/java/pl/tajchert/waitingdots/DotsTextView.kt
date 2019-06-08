package pl.tajchert.waitingdots

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.util.AttributeSet
import android.widget.TextView

class DotsTextView : TextView {

  private var dotOne: JumpingSpan = JumpingSpan()
  private var dotTwo: JumpingSpan = JumpingSpan()
  private var dotThree: JumpingSpan = JumpingSpan()

  private val showSpeed = 700

  private var jumpHeight: Int = 0
  private var autoPlay: Boolean = false
  var isPlaying: Boolean = false
    private set
  var isHide: Boolean = false
    private set
  private var period: Int = 0

  private val mAnimatorSet = AnimatorSet()
  private var textWidth: Float = 0.toFloat()

  constructor(context: Context) : super(context) {
    init(context, null)
  }

  constructor(
    context: Context,
    attrs: AttributeSet
  ) : super(context, attrs) {
    init(context, attrs)
  }

  constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr) {
    init(context, attrs)
  }

  private fun init(
    context: Context,
    attrs: AttributeSet?
  ) {
    if (attrs != null) {
      val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaitingDots)
      period = typedArray.getInt(R.styleable.WaitingDots_period, 6000)
      jumpHeight = typedArray.getInt(
          R.styleable.WaitingDots_jumpHeight, (textSize / 4).toInt()
      )
      autoPlay = typedArray.getBoolean(R.styleable.WaitingDots_autoplay, true)
      typedArray.recycle()
    }
    val spannable = SpannableString("...\u200B")
    spannable.setSpan(dotOne, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan(dotTwo, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan(dotThree, 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    setText(spannable, TextView.BufferType.SPANNABLE)

    textWidth = paint.measureText(".", 0, 1)

    val dotOneJumpAnimator = createDotJumpAnimator(dotOne, 0)
    dotOneJumpAnimator.addUpdateListener(InvalidateViewOnUpdate(this))

    mAnimatorSet.playTogether(
        dotOneJumpAnimator, createDotJumpAnimator(
        dotTwo,
        (period / 6).toLong()
    ), createDotJumpAnimator(dotThree, (period * 2 / 6).toLong())
    )

    isPlaying = autoPlay
    if (autoPlay && !isInEditMode) {
      start()
    }
  }

  fun start() {
    isPlaying = true
    setAllAnimationsRepeatCount(ValueAnimator.INFINITE)
    mAnimatorSet.start()
  }

  private fun createDotJumpAnimator(
    jumpingSpan: JumpingSpan,
    delay: Long
  ): ObjectAnimator {
    val jumpAnimator = ObjectAnimator.ofFloat(jumpingSpan, "translationY", 0f, -jumpHeight.toFloat())

    jumpAnimator.setEvaluator(SinTypeEvaluator())

    jumpAnimator.duration = period.toLong()
    jumpAnimator.startDelay = delay
    jumpAnimator.repeatCount = ValueAnimator.INFINITE
    jumpAnimator.repeatMode = ValueAnimator.RESTART
    return jumpAnimator
  }

  fun stop() {
    isPlaying = false
    setAllAnimationsRepeatCount(0)
  }

  private fun setAllAnimationsRepeatCount(repeatCount: Int) {
    for (animator in mAnimatorSet.childAnimations) {
      if (animator is ObjectAnimator) {
        animator.repeatCount = repeatCount
      }
    }
  }

  fun hide() {

    createDotHideAnimator(dotThree, 2f).start()

    val dotTwoMoveRightToLeft = createDotHideAnimator(dotTwo, 1f)
    dotTwoMoveRightToLeft.addUpdateListener(InvalidateViewOnUpdate(this))

    dotTwoMoveRightToLeft.start()
    isHide = true
  }

  fun show() {
    val dotThreeMoveRightToLeft = createDotShowAnimator(dotThree, 2)

    dotThreeMoveRightToLeft.start()

    val dotTwoMoveRightToLeft = createDotShowAnimator(dotTwo, 1)
    dotTwoMoveRightToLeft.addUpdateListener(InvalidateViewOnUpdate(this))

    dotTwoMoveRightToLeft.start()
    isHide = false
  }

  private fun createDotHideAnimator(
    span: JumpingSpan?,
    widthMultiplier: Float
  ): ObjectAnimator {
    return createDotHorizontalAnimator(span, 0f, -textWidth * widthMultiplier)
  }

  private fun createDotShowAnimator(
    span: JumpingSpan?,
    widthMultiplier: Int
  ): ObjectAnimator {
    return createDotHorizontalAnimator(span, -textWidth * widthMultiplier, 0f)
  }

  private fun createDotHorizontalAnimator(
    span: JumpingSpan?,
    from: Float,
    to: Float
  ): ObjectAnimator {
    val dotThreeMoveRightToLeft = ObjectAnimator.ofFloat(span, "translationX", from, to)
    dotThreeMoveRightToLeft.duration = showSpeed.toLong()
    return dotThreeMoveRightToLeft
  }

  fun showAndPlay() {
    show()
    start()
  }

  fun hideAndStop() {
    hide()
    stop()
  }

  fun setJumpHeight(jumpHeight: Int) {
    this.jumpHeight = jumpHeight
  }

  fun setPeriod(period: Int) {
    this.period = period
  }
}
