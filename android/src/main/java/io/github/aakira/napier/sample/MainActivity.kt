package io.github.aakira.napier.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.aakira.napier.mppsample.Sample
import io.github.aakira.napier.sample.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = Sample().hello()

        GlobalScope.launch {
            Sample().suspendHello()
        }

        Sample().handleError()
    }
}
