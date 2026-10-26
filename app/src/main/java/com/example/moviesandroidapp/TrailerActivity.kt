package com.example.moviesandroidapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesandroidapp.databinding.ActivityTrailerBinding

class TrailerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrailerBinding

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_OVERVIEW = "extra_overview"
        const val EXTRA_POSTER_URL = "extra_poster_url"
        const val EXTRA_TRAILER_KEY = "extra_trailer_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        val title = intent.getStringExtra(EXTRA_TITLE) ?: ""
        val overview = intent.getStringExtra(EXTRA_OVERVIEW) ?: ""
        val trailerKey = intent.getStringExtra(EXTRA_TRAILER_KEY) ?: ""

        setupToolbar()
        populateUI(movieId, title, overview, trailerKey)
        setupYouTubePlayer(trailerKey)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun populateUI(movieId: Int, title: String, overview: String, trailerKey: String) {
        binding.tvMovieTitle.text = title
        binding.tvBadge.text = "YouTube Key: $trailerKey • Movie ID: $movieId"
        binding.tvOverview.text = if (overview.isNotEmpty()) overview else "No overview provided."
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupYouTubePlayer(trailerKey: String) {
        binding.webViewTrailer.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()

            val embedHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
                    <style>
                        body { margin: 0; background-color: #000; display: flex; justify-content: center; align-items: center; height: 100vh; }
                        iframe { width: 100%; height: 100%; border: none; }
                    </style>
                </head>
                <body>
                    <iframe src="https://www.youtube.com/embed/$trailerKey?autoplay=1&playsinline=1"
                            allow="autoplay; encrypted-media"
                            allowfullscreen>
                    </iframe>
                </body>
                </html>
            """.trimIndent()

            loadDataWithBaseURL("https://www.youtube.com", embedHtml, "text/html", "UTF-8", null)
        }
    }

    override fun onDestroy() {
        binding.webViewTrailer.destroy()
        super.onDestroy()
    }
}
