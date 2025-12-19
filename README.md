# SmartSecure

A modern Android smart home security application that provides comprehensive home monitoring and control with advanced authentication features.

## Features

### Authentication & Security
- **Email/Password Authentication** - Traditional login with Firebase Authentication
- **Google Sign-In** - Quick authentication using Google accounts
- **Biometric Verification** - Fingerprint/face authentication for enhanced security
- **Password Recovery** - Email-based password reset functionality

### Smart Home Monitoring
- **Multi-Room Management** - Monitor and control security across different rooms
- **Security Sensors**
  - Motion detection sensors
  - Window sensors
  - Door sensors
- **Room Status** - Arm/disarm individual rooms
- **Temperature Monitoring** - Real-time temperature tracking per room
- **Push Notifications** - Stay informed about security events

## Tech Stack

- **Language**: Java, Kotlin
- **UI Framework**: Jetpack Compose & XML layouts
- **Authentication**: Firebase Authentication
- **Biometric**: AndroidX Biometric API
- **Architecture**: MVVM pattern with RecyclerView adapters
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 33 (Android 13)

## Prerequisites

- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK 33
- Firebase project with Authentication enabled
- Google Services configuration file

## Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd SmartSecure
   ```

2. **Configure Firebase**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Enable Email/Password and Google Sign-In authentication methods
   - Download `google-services.json` and place it in the `app/` directory
   - Update `app/src/main/res/values/strings.xml` with your Google Sign-In client ID

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on device/emulator**
   ```bash
   ./gradlew installDebug
   ```

## Project Structure

```
app/src/main/java/sd/asu/suprad/smartsecure/
├── LoginActivity.java          # Authentication & biometric verification
├── RegistrationActivity.java   # New user registration
├── HomeActivity.java           # Main dashboard with room list
├── RoomActivity.java           # Individual room details
├── Room.java                   # Room data model
└── HomeElementAdapter.java     # RecyclerView adapter for rooms
```

## Configuration

### Firebase Setup
Ensure your `google-services.json` is properly configured with:
- Firebase Authentication enabled
- Google Sign-In provider configured
- OAuth 2.0 client ID for Android

### Biometric Authentication
The app requires devices with:
- Android 9.0 (API 28) or higher for biometric features
- Enrolled fingerprint or face authentication

## Dependencies

Key dependencies include:
- Firebase Authentication & BoM
- Google Play Services Auth
- AndroidX Biometric
- Jetpack Compose
- Material Design Components

See `app/build.gradle.kts` for complete dependency list.

## Usage

1. **First Launch**: Create an account using email/password or Google Sign-In
2. **Login**: Authenticate with credentials and verify using biometrics
3. **Dashboard**: View all rooms and their security status
4. **Room Control**: Tap on any room to view details and control sensors
5. **Security**: Arm/disarm rooms as needed

## Security Features

- Multi-factor authentication (password + biometric)
- Secure Firebase backend
- Encrypted credential storage
- Session management with automatic logout

