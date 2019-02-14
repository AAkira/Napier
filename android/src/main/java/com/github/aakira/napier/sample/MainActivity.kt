package com.github.aakira.napier.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.aakira.napier.Sample
import kotlinx.android.synthetic.main.activity_main.textView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.text = Sample().hello()

        GlobalScope.launch {
            Sample().suspendHello()
            Sample().handleError()
        }
    }
}
