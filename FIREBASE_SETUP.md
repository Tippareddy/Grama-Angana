# Firebase setup for Grama-Angana

The Android project is ready for Firebase. The package/application ID is:

```text
com.gramaangana.app
```

## Finish connection

1. Open the Firebase console.
2. Create or open your Firebase project.
3. Add an Android app with package name `com.gramaangana.app`.
4. Download `google-services.json`.
5. Place it here:

```text
C:\GramaAngana\app\google-services.json
```

6. Sync Gradle in Android Studio.

The Gradle setup applies the Google Services plugin automatically when `app/google-services.json` exists. This keeps the project buildable before the Firebase config file is added.

## Firebase SDKs already added

- Firebase Analytics
- Firebase Authentication
- Cloud Firestore
- Firebase Cloud Messaging

Versions are managed through the Firebase Android BoM in `gradle/libs.versions.toml`.
