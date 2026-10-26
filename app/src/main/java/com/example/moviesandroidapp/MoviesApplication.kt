package com.example.moviesandroidapp

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MoviesApplication : Application() {

    lateinit var flutterEngine: FlutterEngine

    companion object {
        const val ENGINE_ID = "movies_flutter_engine"
        const val CHANNEL_NAME = "com.example.movies/bridge"
    }

    override fun onCreate() {
        super.onCreate()

        // 1. Instantiate FlutterEngine
        flutterEngine = FlutterEngine(this)

        // 2. Start executing Dart code to pre-warm the engine
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // 3. Cache the engine
        FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)
    }
}
