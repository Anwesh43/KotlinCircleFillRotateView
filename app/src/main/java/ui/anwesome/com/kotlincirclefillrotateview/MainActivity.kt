package ui.anwesome.com.kotlincirclefillrotateview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.circlefillrotateview.CircleFillRotateView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CircleFillRotateView.create(this)
    }
}
