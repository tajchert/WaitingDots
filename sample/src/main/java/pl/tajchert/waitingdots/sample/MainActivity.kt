package pl.tajchert.waitingdots.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.buttonHide
import kotlinx.android.synthetic.main.activity_main.buttonHideAndStop
import kotlinx.android.synthetic.main.activity_main.buttonPlay
import kotlinx.android.synthetic.main.activity_main.dotsTextView

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    buttonPlay.setOnClickListener {
      if (dotsTextView.isPlaying) {
        dotsTextView.stop()
      } else {
        dotsTextView.start()
      }
    }

    buttonHide.setOnClickListener {
      if (dotsTextView.isHide) {
        dotsTextView.show()
      } else {
        dotsTextView.hide()
      }
    }

    buttonHideAndStop.setOnClickListener {
      if (dotsTextView.isHide) {
        dotsTextView.showAndPlay()
      } else {
        dotsTextView.hideAndStop()
      }
    }
  }
}
