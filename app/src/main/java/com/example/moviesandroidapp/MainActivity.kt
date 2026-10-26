package com.example.moviesandroidapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesandroidapp.databinding.ActivityMainBinding
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var methodChannel: MethodChannel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMethodChannel()

        binding.btnShowMovies.setOnClickListener {
            // Launch the Flutter module with the cached engine
            val intent = FlutterActivity
                .withCachedEngine(MoviesApplication.ENGINE_ID)
                .build(this)
            startActivity(intent)
        }
    }

    private fun setupMethodChannel() {
        val engine = FlutterEngineCache.getInstance().get(MoviesApplication.ENGINE_ID) ?: return

        methodChannel = MethodChannel(engine.dartExecutor.binaryMessenger, MoviesApplication.CHANNEL_NAME)
        methodChannel?.setMethodCallHandler { call, result ->
            when (call.method) {
                "openTrailer" -> {
                    val movieId = call.argument<Int>("movieId") ?: 0
                    val title = call.argument<String>("title") ?: ""
                    val overview = call.argument<String>("overview") ?: ""
                    val posterUrl = call.argument<String>("posterUrl") ?: ""
                    val trailerKey = call.argument<String>("trailerKey") ?: ""

                    result.success(null)

                    // Launch native TrailerActivity
                    val intent = Intent(this, TrailerActivity::class.java).apply {
                        putExtra(TrailerActivity.EXTRA_MOVIE_ID, movieId)
                        putExtra(TrailerActivity.EXTRA_TITLE, title)
                        putExtra(TrailerActivity.EXTRA_OVERVIEW, overview)
                        putExtra(TrailerActivity.EXTRA_POSTER_URL, posterUrl)
                        putExtra(TrailerActivity.EXTRA_TRAILER_KEY, trailerKey)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    startActivity(intent)
                }
                "closeModule" -> {
                    result.success(null)
                    // Flutter closes itself
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }
}
