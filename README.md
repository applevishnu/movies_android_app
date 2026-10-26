# Movies Android App (`movies_android_app`)

Native Android host application (written in Kotlin with ViewBinding and Material 3) that embeds the `movies_flutter_module` via Gradle and plays movie trailers natively.

---

## 1. Architecture & Features

- **Kotlin & Material 3 (Min SDK 24+)**: Clean native UI built with ViewBinding and modern Android architecture.
- **Pre-warmed Flutter Engine**: `MoviesApplication` pre-warms and caches the `FlutterEngine` (`movies_flutter_engine`) on app launch for instantaneous presentation with zero stutter.
- **"Show List of Movies" Button**: Displayed on `MainActivity` to launch the Flutter movie discovery screen.
- **Bidirectional Native Bridge**:
  - Registers `MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "com.example.movies/bridge")`.
  - Listens for `openTrailer` with payload `{ movieId, title, overview, posterUrl, trailerKey }`.
  - Seamlessly launches `TrailerActivity` with movie data and YouTube video key.
- **Native Trailer Playback**:
  - `TrailerActivity` plays the YouTube trailer natively inside an embedded WebView with hardware acceleration (`https://www.youtube.com/embed/{trailerKey}?autoplay=1&playsinline=1`).
  - Displays movie title, metadata badge, and full scrollable overview beneath the player.
  - Back navigation returns directly to the main screen.

---

## 2. Gradle Integration

The app connects to the sibling `movies_flutter_module` repository using `include_flutter.groovy`:

### `settings.gradle`:
```groovy
rootProject.name = "movies_android_app"
include ':app'

setBinding(new Binding([gradle: this]))
evaluate(new File(settingsDir, '../movies_flutter_module/.android/include_flutter.groovy'))
```

### `app/build.gradle`:
```groovy
dependencies {
    implementation project(':flutter')
    ...
}
```

---

## 3. Setup and Running

### 3.1 Build the Debug APK
From the `movies_android_app` directory:
```bash
./gradlew assembleDebug
```

### 3.2 Install and Run on Device / Emulator
```bash
./gradlew installDebug
```
Or open in Android Studio and click **Run**.

---

## 4. Running UI Tests (Espresso)

Run the native Espresso instrumented test suite:
```bash
./gradlew connectedAndroidTest
```
